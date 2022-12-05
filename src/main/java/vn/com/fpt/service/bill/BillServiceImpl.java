package vn.com.fpt.service.bill;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.model.RoomContractDTO;
import vn.com.fpt.repositories.*;
import vn.com.fpt.requests.GenerateBillRequest;
import vn.com.fpt.responses.NotBilledRoomResponse;
import vn.com.fpt.responses.PreviewGenerateBillResponse;
import vn.com.fpt.responses.RoomsResponse;
import vn.com.fpt.service.TableLogComponent;
import vn.com.fpt.service.contract.ContractService;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.renter.RenterService;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.services.ServicesService;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
    public List<PreviewGenerateBillResponse> generatePreview(GenerateBillRequest request) {

        return null;
    }

    @Override
    public List<NotBilledRoomResponse> listNotBilledRoom(Long groupContractId, Integer billCircle) {

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
        List<NotBilledRoomResponse> responses = new LinkedList<>(Collections.emptyList());
        for (RoomContractDTO rcd : listRoomContract) {
            if (rcd.getContractBillCycle().equals(billCircle)
                    &&
                    ObjectUtils.isEmpty(recurringBillRepo.findByContractIdAndCreatedAt(
                            rcd.getContractId(),
                            toLocalDate(now()).getMonth().getValue(),
                            toLocalDate(now()).getYear()))) {
                var room = roomService.room(rcd.getRoomId());
                var renter = renterService.listRenter(room.getId());
                var generalService = servicesService.listGeneralServiceByGroupId(rcd.getGroupId());
                NotBilledRoomResponse response = new NotBilledRoomResponse();
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
                if (DateUtils.monthsBetween(now(), parse(rcd.getContractStartDate())) / rcd.getContractPaymentCycle() == 0) {
                    response.setIsInPaymentCycle(true);
                    response.setTotalMoneyRoomPrice(room.getRoomPrice() * rcd.getContractPaymentCycle());
                } else {
                    response.setIsInPaymentCycle(false);
                    response.setTotalMoneyRoomPrice((double) 0);
                }
                responses.add(response);
            }
        }
        return responses;
    }
}
