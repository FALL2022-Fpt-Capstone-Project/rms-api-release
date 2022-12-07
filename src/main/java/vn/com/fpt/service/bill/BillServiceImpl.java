package vn.com.fpt.service.bill;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.entity.*;
import vn.com.fpt.model.RoomContractDTO;
import vn.com.fpt.repositories.*;
import vn.com.fpt.requests.AddBillRequest;
import vn.com.fpt.requests.GenerateBillRequest;
import vn.com.fpt.responses.*;
import vn.com.fpt.service.TableLogComponent;
import vn.com.fpt.service.contract.ContractService;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.renter.RenterService;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.services.ServicesService;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static vn.com.fpt.common.constants.ManagerConstants.*;
import static vn.com.fpt.common.utils.DateUtils.*;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final ContractService contractService;
    private final ContractRepository contractRepository;
    private final RoomService roomService;
    private final GroupService groupService;
    private final ServicesService servicesService;

    private final RenterService renterService;
    private final RecurringBillRepository recurringBillRepo;
    private final ServiceBillRepository serviceBillRepo;
    private final RoomBillRepository roomBillRepo;
    private final TableLogComponent tableLogComponent;


    @Override
    public List<BillRoomStatusResponse> listBillRoomStatus(Long groupContractId, Integer paymentCircle) {

        // Lấy các phòng đã có hợp đồng
        var listRoom = roomService.listRoom(
                null,
                groupContractId,
                null,
                0,
                null);
        var listRoomId = listRoom.stream().map(RoomsResponse::getRoomId).toList();

        // Lấy hợp đồng của các phòng
        var listRoomContract = contractService.listRoomContract(
                null,
                null,
                null,
                null,
                false,
                null,
                null,
                null,
                null,
                listRoomId);
        List<RoomContractDTO> var1;
        if (paymentCircle.equals(0)) {
            var1 = listRoomContract;
        } else {
            var1 = listRoomContract.stream().filter(e -> e.getContractPaymentCycle().equals(paymentCircle)).toList();
        }
        if (var1.isEmpty()) return Collections.emptyList();
        var currentMonth = toLocalDate(now()).getMonth().getValue();
        var currentYear = toLocalDate(now()).getYear();
        List<BillRoomStatusResponse> responses = new ArrayList<>(Collections.emptyList());
        for (RoomContractDTO rcd : var1) {
            BillRoomStatusResponse response = new BillRoomStatusResponse();

            var room = roomService.room(rcd.getRoomId());
            var renter = renterService.listRenter(room.getId());
            var generalService = servicesService.listGeneralServiceByGroupId(rcd.getGroupId());
            response.setGroupId(room.getGroupId());
            response.setContractId(rcd.getContractId());
            response.setRoomName(room.getRoomName());
            response.setGroupContractId(room.getGroupContractId());
            response.setRoomFloor(room.getRoomFloor());
            response.setRoomLimitPeople(room.getRoomLimitPeople());
            response.setRoomCurrentWaterIndex(room.getRoomCurrentWaterIndex() == null ? 0 : room.getRoomCurrentWaterIndex());
            response.setRoomCurrentElectricIndex(room.getRoomCurrentElectricIndex() == null ? 0 : room.getRoomCurrentElectricIndex());
            response.setRoomPrice(room.getRoomPrice());
            response.setTotalRenter(renter.size());
            response.setListGeneralService(generalService);
            response.setContractPaymentCycle(rcd.getContractPaymentCycle());

            if (DateUtils.monthsBetween(now(), parse(rcd.getContractStartDate())) % rcd.getContractBillCycle() == 0) {
                response.setIsInBillCycle(true);
                response.setTotalMoneyRoomPrice(room.getRoomPrice() * rcd.getContractBillCycle());
            } else {
                response.setIsInBillCycle(false);
                response.setTotalMoneyRoomPrice((double) 0);
            }

            if (ObjectUtils.isNotEmpty(recurringBillRepo.findByContractIdAndCreatedAt(rcd.getContractId(), currentMonth, currentYear))) {
                var recurringBill = recurringBillRepo.findByContractIdAndCreatedAt(rcd.getContractId(), currentMonth, currentYear);
                var electric = serviceBillRepo.findAllByRoomIdAndByServiceIdAndCreatedBy(recurringBill.getRoomId(), SERVICE_ELECTRIC, SERVICE_TYPE_METER, currentMonth, currentYear);
                var water = serviceBillRepo.findAllByRoomIdAndByServiceIdAndCreatedBy(recurringBill.getRoomBillId(), SERVICE_WATER, SERVICE_TYPE_METER, currentMonth, currentYear);

                response.setRoomOldWaterIndex(water.getServiceIndex());
                response.setRoomOldElectricIndex(electric.getServiceIndex());
                response.setIsBilled(true);
            } else {
                response.setRoomOldWaterIndex(room.getRoomCurrentWaterIndex() == null ? 0 : room.getRoomCurrentWaterIndex());
                response.setRoomOldElectricIndex(room.getRoomCurrentElectricIndex() == null ? 0 : room.getRoomCurrentElectricIndex());
                response.setIsBilled(false);
            }
            responses.add(response);
        }
        return responses;
    }

    @Override
    @SneakyThrows
    public List<AddBillRequest> addBill(List<AddBillRequest> addBillRequests) {
        var currentMonth = toLocalDate(now()).getMonth().getValue();
        var currentYear = toLocalDate(now()).getYear();
        for (AddBillRequest abr : addBillRequests) {
            //tạo hóa đơn cho tiền phòng
            var roomInfor = roomService.room(abr.getRoomId());
            var contractInfor = contractService.contract(roomInfor.getContractId());
            if (abr.getTotalRoomMoney() > 0) {
                var var1 = roomBillRepo.save(RoomBill.of(
                                roomInfor.getContractId(),
                                roomInfor.getGroupContractId(),
                                roomInfor.getGroupId(),
                                abr.getRoomId(),
                                abr.getTotalRoomMoney(),
                                contractInfor.getContractPaymentCycle(), "Tiền phòng " + roomInfor.getRoomName()
                        )
                );
                //lưu vết
                tableLogComponent.saveRoomBillHistory(var1);
            }
            // tạo hóa đơn dịch vụ
            Double totalMoneyService = 0.0;
            if (!abr.getServiceBill().isEmpty()) {
                List<ServiceBill> serviceBills = new ArrayList<>(Collections.emptyList());
                for (AddBillRequest.ServiceBill sbr : abr.getServiceBill()) {
                    Double serviceTotalMoney;
                    if (ObjectUtils.isEmpty(sbr.getServiceTotalMoney()) && Objects.equals(sbr.getServiceId(), SERVICE_ELECTRIC)) {
                        serviceTotalMoney = sbr.getServiceIndex() - (ObjectUtils.isEmpty(roomInfor.getRoomCurrentElectricIndex()) ? 0 : roomInfor.getRoomCurrentElectricIndex()) * sbr.getServicePrice();
                    } else {
                        serviceTotalMoney = sbr.getServiceTotalMoney();
                    }

                    if (ObjectUtils.isEmpty(sbr.getServiceTotalMoney()) && Objects.equals(sbr.getServiceId(), SERVICE_WATER)) {
                        if (sbr.getServiceType().equals(SERVICE_TYPE_METER)) {
                            serviceTotalMoney = sbr.getServiceIndex() - (ObjectUtils.isEmpty(roomInfor.getRoomCurrentElectricIndex()) ? 0 : roomInfor.getRoomCurrentElectricIndex()) * sbr.getServicePrice();
                        } else {
                            serviceTotalMoney = sbr.getServiceIndex() * sbr.getServicePrice();
                        }
                    } else {
                        serviceTotalMoney = sbr.getServiceTotalMoney();
                    }
                    serviceBills.add(ServiceBill.add(
                                    sbr.getServiceId(),
                                    sbr.getServiceType(),
                                    sbr.getServicePrice(),
                                    "Tiền " + servicesService.basicService(sbr.getServiceId()).getServiceShowName() + "  " + toLocalDate(parse(abr.getCreatedTime())).getMonth().getValue() + "-" + toLocalDate(parse(abr.getCreatedTime())).getYear(),
                                    sbr.getServiceIndex(),
                                    abr.getRoomId(),
                                    roomInfor.getGroupContractId(),
                                    roomInfor.getContractId(),
                                    serviceTotalMoney,
                                    parse(abr.getCreatedTime()),
                                    Operator.operator()
                            )
                    );
                    totalMoneyService += serviceTotalMoney;
                }
                var var1 = serviceBillRepo.saveAll(serviceBills);
                // lưu vết
                tableLogComponent.saveServiceBillSourceHistory(var1);
            }
            // tạo hóa đơn định kì
            var var2 = recurringBillRepo.save(
                    RecurringBill.add(
                            abr.getRoomId(),
                            roomInfor.getGroupId(),
                            roomInfor.getGroupContractId(),
                            roomInfor.getContractId(),
                            totalMoneyService + abr.getTotalRoomMoney(),
                            "Hóa đơn phòng " + roomInfor.getRoomName() + "tháng " + currentMonth + "/" + currentYear,
                            false,
                            true,
                            "IN",
                            parse(abr.getPaymentTerm()),
                            parse(abr.getCreatedTime()),
                            DateUtils.monthsBetween(now(), contractInfor.getContractStartDate()) % contractInfor.getContractBillCycle() == 0
                    )
            );
            //lưu vết
            tableLogComponent.saveRecurringBillHistory(List.of(var2));
        }
        return addBillRequests;
    }

    @Override
    public List<ListRoomWithBillStatusResponse> listRoomWithBillStatus(Long groupId) {
        List<ListRoomWithBillStatusResponse> responses = new ArrayList<>(Collections.emptyList());
        List<RoomsResponse> listRentedRoom = roomService.listRoom(
                groupId,
                null,
                null,
                0,
                null
        );
        RoomGroups roomGroups = groupService.getGroup(groupId);
        listRentedRoom.forEach(e -> {
            RoomContractDTO contract = contractService.roomContract(e.getContractId());
            RentersResponse representRenter = renterService.representRenter(e.getRoomId());
            responses.add(
                    new ListRoomWithBillStatusResponse(
                            roomGroups.getGroupName(),
                            e.getRoomId(),
                            e.getRoomName(),
                            e.getContractId(),
                            representRenter.getRenterFullName(),
                            e.getRoomPrice(),
                            e.getRoomCurrentElectricIndex(),
                            e.getRoomCurrentWaterIndex(),
                            renterService.listRenter(e.getRoomId()).size(),
                            contract.getContractPaymentCycle(),
                            ObjectUtils.isEmpty(recurringBillRepo.findAllByRoomIdAndIsPaid(e.getRoomId(), false))
                    )
            );
        });
        return responses;
    }

    @Override
    public List<PreviewAddBillResponse> addBillPreview(List<AddBillRequest> requests) {
        AtomicInteger key = new AtomicInteger(0);
        return requests.stream().map(e ->
                new PreviewAddBillResponse(
                        key.getAndAdd(1),
                        e.getRoomId(),
                        e.getTotalRoomMoney(),
                        e.getTotalServiceMoney(),
                        e.getDescription(),
                        e.getPaymentTerm(),
                        e.getCreatedTime(),
                        e.getServiceBill()
                )
        ).toList();
    }

    @Override
    public List<RoomBillHistory> roomBillHistory(Long roomId) {
        return null;
    }
}
