package vn.com.fpt.service.bill;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.entity.*;
import vn.com.fpt.model.HandOverGeneralServiceDTO;
import vn.com.fpt.model.RoomContractDTO;
import vn.com.fpt.repositories.*;
import vn.com.fpt.requests.AddBillRequest;
import vn.com.fpt.requests.GenerateBillRequest;
import vn.com.fpt.requests.PreviewAddBillRequest;
import vn.com.fpt.responses.*;
import vn.com.fpt.service.DeleteLog;
import vn.com.fpt.service.TableLogComponent;
import vn.com.fpt.service.UpdateLog;
import vn.com.fpt.service.contract.ContractService;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.renter.RenterService;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.services.ServicesService;

import java.math.BigInteger;
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
    private final MoneySourceRepository moneySourceRepo;
    private final ServiceBillRepository serviceBillRepo;
    private final RoomBillRepository roomBillRepo;
    private final TableLogComponent tableLogComponent;


    @Override
    public List<BillRoomStatusResponse> listBillRoomStatus(Long groupId, Integer paymentCircle) {
        // Lấy các phòng đã có hợp đồng
        var listRoom = roomService.listRoom(
                groupId,
                null,
                null,
                null,
                null);
        var roomsResponses = listRoom.stream().filter(e -> e.getContractId() != null).toList();
        var listRoomId = roomsResponses.stream().map(RoomsResponse::getRoomId).toList();
        if (listRoomId.isEmpty()) return Collections.emptyList();
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
            response.setRoomId(rcd.getRoomId());
            response.setGroupId(room.getGroupId());
            response.setContractId(rcd.getContractId());
            response.setRoomName(room.getRoomName());
            response.setGroupContractId(room.getGroupContractId());
            response.setRoomFloor(room.getRoomFloor());
            response.setRoomLimitPeople(room.getRoomLimitPeople());
            response.setRoomCurrentWaterIndex(room.getRoomCurrentWaterIndex() == null ? 0 : room.getRoomCurrentWaterIndex());
            response.setRoomCurrentElectricIndex(room.getRoomCurrentElectricIndex() == null ? 0 : room.getRoomCurrentElectricIndex());
            response.setRoomPrice(room.getRoomPrice());
            response.setTotalRenter(renter.isEmpty() ? 1 : renter.size());
            response.setListGeneralService(generalService);
            response.setContractPaymentCycle(rcd.getContractPaymentCycle());
            response.setBillCycle(rcd.getContractBillCycle());

            if (DateUtils.monthsBetween(now(), parse(rcd.getContractStartDate())) % rcd.getContractBillCycle() == 0) {
                response.setIsInBillCycle(true);
                response.setTotalMoneyRoomPrice(room.getRoomPrice() * rcd.getContractBillCycle());
            } else {
                response.setIsInBillCycle(false);
                response.setTotalMoneyRoomPrice((double) 0);
            }
            var recurringBills = recurringBillRepo.findAllByRoomId(rcd.getRoomId());
            var check = recurringBills.stream().filter(e -> toLocalDate(e.getBillCreatedTime()).getMonthValue() == currentMonth && toLocalDate(e.getBillCreatedTime()).getYear() == currentYear).toList();
            if (ObjectUtils.isNotEmpty(check)) {
                var recurringBill = recurringBills.stream().filter(e -> toLocalDate(e.getBillCreatedTime()).getMonthValue() == currentMonth && toLocalDate(e.getBillCreatedTime()).getYear() == currentYear).findFirst().get();
                var electric = serviceBillRepo.findAllByRoomIdAndServiceIdAndServiceTypeId(recurringBill.getRoomId(), SERVICE_ELECTRIC, SERVICE_TYPE_METER).stream().filter(e -> toLocalDate(e.getBillCreatedTime()).getMonthValue() == currentMonth && toLocalDate(e.getBillCreatedTime()).getYear() == currentYear).findFirst().orElse(null);
                var water = serviceBillRepo.findAllByRoomIdAndServiceIdAndServiceTypeId(recurringBill.getRoomId(), SERVICE_WATER, SERVICE_TYPE_METER).stream().filter(e -> toLocalDate(e.getBillCreatedTime()).getMonthValue() == currentMonth && toLocalDate(e.getBillCreatedTime()).getYear() == currentYear).findFirst().orElse(null);

                response.setRoomOldWaterIndex(ObjectUtils.isEmpty(water.getServiceIndex()) ? 0 : water.getServiceIndex());
                response.setRoomOldElectricIndex(ObjectUtils.isEmpty(electric.getServiceIndex()) ? 0 : electric.getServiceIndex());
                response.setTotalMoney(recurringBill.getTotalMoney());
                response.setIsBilled(true);
            } else {
                response.setRoomOldWaterIndex(room.getRoomCurrentWaterIndex() == null ? 0 : room.getRoomCurrentWaterIndex());
                response.setRoomOldElectricIndex(room.getRoomCurrentElectricIndex() == null ? 0 : room.getRoomCurrentElectricIndex());
                response.setTotalMoney(0.0);
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
                var var1 = roomBillRepo.save(RoomBill.add(
                                roomInfor.getContractId(),
                                roomInfor.getGroupContractId(),
                                roomInfor.getGroupId(),
                                abr.getRoomId(),
                                abr.getTotalRoomMoney(),
                                contractInfor.getContractPaymentCycle(),
                                parse(abr.getCreatedTime()),
                                "Tiền phòng " + roomInfor.getRoomName()
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
                    int newElectricIndex = 0;
                    int newWaterIndex = 0;
                    if (sbr.getServiceId() == SERVICE_ELECTRIC) {
                        newElectricIndex = roomInfor.getRoomCurrentElectricIndex() + sbr.getServiceIndex();
                        serviceTotalMoney = sbr.getServiceIndex() * sbr.getServicePrice();
                    } else {
                        serviceTotalMoney = sbr.getServiceTotalMoney();
                    }
                    if (sbr.getServiceId() == SERVICE_WATER){
                            newWaterIndex = roomInfor.getRoomCurrentWaterIndex() + sbr.getServiceIndex();
                            serviceTotalMoney = sbr.getServiceIndex() * sbr.getServicePrice();
                        
                    } else {
                        serviceTotalMoney = sbr.getServiceTotalMoney();
                    }
                        roomService.setServiceIndex(roomInfor.getContractId(), newElectricIndex, newWaterIndex, Operator.operator());
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
                            roomInfor.getRoomName(),
                            roomInfor.getGroupId(),
                            roomInfor.getGroupContractId(),
                            roomInfor.getContractId(),
                            totalMoneyService + abr.getTotalRoomMoney(),
                            "Hóa đơn phòng " + roomInfor.getRoomName() + " tháng " + currentMonth + "/" + currentYear,
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
        String remindAlert = null;
        if (10 <= now().getDate() && now().getDate() <= 15) {
            remindAlert = "Đã đến kỳ lập hóa đơn (Kỳ 15)";
        } else if (25 <= now().getDate() && now().getDate() <= 30) {
            remindAlert = "Đã đến kỳ lập hóa đơn (Kỳ 30)";
        }
        List<RoomsResponse> listRentedRoom = roomService.listRoom(
                groupId,
                null,
                null,
                null,
                null
        ).stream().filter(e -> e.getContractId() != null).toList();
        RoomGroups roomGroups = groupService.getGroup(groupId);
        String finalRemindAlert = remindAlert;
        listRentedRoom.forEach(e -> {
            RoomContractDTO contract = contractService.roomContract(e.getContractId());
            if (ObjectUtils.isNotEmpty(contract.getContractIsDisable())) {
//                RentersResponse representRenter = renterService.representRenter(contract.getRoomId());
                responses.add(
                        new ListRoomWithBillStatusResponse(
                                roomGroups.getGroupName(),
                                e.getRoomId(),
                                e.getRoomName(),
                                e.getContractId(),
                                "",
                                e.getRoomPrice(),
                                e.getRoomCurrentElectricIndex(),
                                e.getRoomCurrentWaterIndex(),
                                renterService.listRenter(e.getRoomId()).size(),
                                contract.getContractPaymentCycle(),
                                ObjectUtils.isEmpty(recurringBillRepo.findAllByRoomIdAndIsPaid(e.getRoomId(), false))
                                , finalRemindAlert
                        )
                );
            }
        });
        return responses;
    }

    @Override
    public List<PreviewAddBillResponse> addBillPreview(List<PreviewAddBillRequest> requests) {
        AtomicInteger key = new AtomicInteger(0);
        return requests.stream().map(e -> {
            PreviewAddBillResponse response = new PreviewAddBillResponse();
            var room = roomService.room(e.getRoomId());
            var contract = contractService.contract(room.getContractId());
            var renter = renterService.listRenter(e.getRoomId());

            response.setRoomCurrentElectricIndex(e.getRoomCurrentElectricIndex());
            response.setRoomCurrentWaterIndex(e.getRoomCurrentElectricIndex());
            response.setKey(key.getAndAdd(1));
            response.setRoomId(e.getRoomId());
            response.setRoomName(room.getRoomName());
            response.setRoomFloor(room.getRoomFloor());
            response.setRoomLimitPeople(room.getRoomLimitPeople());
            response.setRoomOldWaterIndex(ObjectUtils.isEmpty(room.getRoomCurrentWaterIndex()) ? 0 : room.getRoomCurrentWaterIndex());
            response.setRoomOldElectricIndex(ObjectUtils.isEmpty(room.getRoomCurrentElectricIndex()) ? 0 : room.getRoomCurrentElectricIndex());
            response.setGroupId(room.getGroupId());
            response.setContractId(room.getContractId());
            response.setGroupContractId(room.getGroupContractId());
            response.setRoomPrice(room.getRoomPrice());
            response.setTotalRenter(renter.size());
            response.setTotalMoneyServicePrice(e.getTotalMoneyServicePrice());
            response.setTotalMoney(e.getTotalMoneyRoomPrice() + (ObjectUtils.isEmpty(e.getTotalMoneyServicePrice()) ? 0 : e.getTotalMoneyServicePrice()));
            response.setContractPaymentCycle(contract.getContractPaymentCycle());
            response.setIsBilled(false);
            response.setServiceBill(e.getServiceBill());

            if (DateUtils.monthsBetween(now(), contract.getContractStartDate()) % contract.getContractBillCycle() == 0) {
                response.setIsInBillCycle(true);
                response.setTotalMoneyRoomPrice(room.getRoomPrice() * contract.getContractBillCycle());
            } else {
                response.setIsInBillCycle(false);
                response.setTotalMoneyRoomPrice((double) 0);
            }
            return response;
        }).toList();
    }

    @Override
    public List<RecurringBill> roomBillHistory(Long roomId) {
        return recurringBillRepo.findAllByRoomId(roomId);
    }

    @Override
    @SneakyThrows
    @Transactional
    public void payRoomBill(List<Long> billId) {
        var listRecurringBill = recurringBillRepo.findAllByIdIn(billId);

        List<UpdateLog> updateLogs = new ArrayList<>(Collections.emptyList());
        List<MoneySource> moneySources = new ArrayList<>(Collections.emptyList());
        listRecurringBill.forEach(e -> {
            int month = toLocalDate(e.getBillCreatedTime()).getMonthValue();
            int year = toLocalDate(e.getBillCreatedTime()).getYear();
            e.setIsPaid(true);
            e.setIsDebt(false);
            updateLogs.add(new UpdateLog(
                    RecurringBill.TABLE_NAME,
                    e.getId(),
                    "is_paid",
                    Operator.operatorName(),
                    String.valueOf(e.getIsPaid()),
                    String.valueOf(true)
            ));
            updateLogs.add(new UpdateLog(
                    RecurringBill.TABLE_NAME,
                    e.getId(),
                    "is_debt",
                    Operator.operatorName(),
                    String.valueOf(e.getIsDebt()),
                    String.valueOf(false)
            ));
            moneySources.add(MoneySource.of(
                    "Tiền hóa đơn tháng " + month + "/" + year,
                    e.getTotalMoney(),
                    IN_MONEY,
                    e.getBillCreatedTime(),
                    e.getId(),
                    RECURRING_BILL));
        });
        recurringBillRepo.saveAll(listRecurringBill);
        tableLogComponent.updateEvent(updateLogs);

        // thêm nguồn tiền
        tableLogComponent.saveMoneySourceHistory(moneySourceRepo.saveAll(moneySources));

    }

    @Override
    @Transactional
    @SneakyThrows
    public void deleteRoomBill(List<Long> billId) {
        var listRecurringBill = recurringBillRepo.findAllByIdIn(billId);
        List<DeleteLog> deleteLogs = new ArrayList<>(Collections.emptyList());
        listRecurringBill.forEach(e -> {

            var listServiceBill = new ArrayList<ServiceBill>(Collections.emptyList());
            if (!ObjectUtils.isEmpty(e.getServiceBillId())) {
                listServiceBill.addAll(serviceBillRepo.findAllById(Collections.singleton(e.getServiceBillId())));
            }
            var listRoomBill = new ArrayList<RoomBill>(Collections.emptyList());

            if (!ObjectUtils.isEmpty(e.getRoomBillId())) {
                listRoomBill.addAll(roomBillRepo.findAllById(Collections.singleton(e.getServiceBillId())));
            }
            if (!listRoomBill.isEmpty()) {
                roomBillRepo.deleteAll(listRoomBill);
                deleteLogs.add(new DeleteLog(RoomBill.TABLE_NAME, e.getRoomBillId(), Operator.operatorName()));
            }
            if (!listServiceBill.isEmpty()) serviceBillRepo.deleteAll(listServiceBill);
            recurringBillRepo.delete(e);
        });
    }

    @Override
    public PayBillInformationResponse payBillInformation(Long roomId) {
        var room = roomService.room(roomId);
        var contract = contractService.contract(room.getContractId());
        var renter = renterService.listRenter(roomId);
        var generalService = servicesService.listGeneralServiceByGroupId(room.getGroupId());
        List<HandOverGeneralServiceDTO> list = new ArrayList<>();
        generalService.forEach(e -> {
            HandOverGeneralServiceDTO service = new HandOverGeneralServiceDTO();
            service.setServiceId(e.getServiceId());
            service.setServiceName(e.getServiceName());
            service.setServiceTypeId(e.getServiceTypeId());
            service.setServiceTypeName(e.getServiceTypeName());
            service.setServiceShowName(e.getServiceShowName());
            service.setServicePrice(e.getServicePrice());
            service.setHandOverGeneralServiceIndex(1);
            if (e.getServiceId().equals(BigInteger.valueOf(SERVICE_ELECTRIC))) {
                service.setHandOverGeneralServiceIndex(room.getRoomCurrentElectricIndex());
            }
            if (e.getServiceId().equals(BigInteger.valueOf(SERVICE_WATER))) {
                service.setHandOverGeneralServiceIndex(room.getRoomCurrentWaterIndex());
            }
            if (e.getServiceTypeId().equals(BigInteger.valueOf(SERVICE_TYPE_PERSON))) {
                service.setHandOverGeneralServiceIndex(renter.size());
            }
            list.add(service);
        });
        PayBillInformationResponse response = new PayBillInformationResponse();
        response.setRoomId(roomId);
        response.setRoomName(room.getRoomName());
        response.setRoomFloor(room.getRoomFloor());
        response.setRoomLimitPeople(room.getRoomLimitPeople());
        response.setGroupId(room.getGroupId());
        response.setContractId(room.getContractId());
        response.setGroupContractId(room.getGroupContractId());
        response.setRoomPrice(room.getRoomPrice());
        response.setTotalRenter(renter.size());
        response.setContractPaymentCycle(contract.getContractPaymentCycle());
        response.setListGeneralService(list);
        return response;
    }

    @Override
    public Boolean groupBillCheck(Long groupContractId) {
        return !recurringBillRepo.findAllByGroupContractIdAndIsPaidIsFalseOrIsDebtIsTrue(groupContractId).isEmpty();
    }

    @Override
    public Boolean roomBillCheck(Long contractId) {
        return !recurringBillRepo.findAllByRoomIdAndIsPaidIsFalseOrIsDebtIsTrue(contractId).isEmpty();
    }

    @Override
    public List<RecurringBill> listRecurringBillByGroupId(Long groupId) {
        return recurringBillRepo.findAllByGroupContractId(groupId);
    }


}
