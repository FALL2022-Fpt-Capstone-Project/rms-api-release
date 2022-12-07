package vn.com.fpt.service.bill;

import vn.com.fpt.requests.AddBillRequest;
import vn.com.fpt.requests.GenerateBillRequest;
import vn.com.fpt.responses.*;

import java.util.List;

public interface BillService {
    List<BillRoomStatusResponse> listBillRoomStatus(Long groupContractId, Integer billCircle);

    List<AddBillRequest> addBill(List<AddBillRequest> addBillRequests);

    List<ListRoomWithBillStatusResponse> listRoomWithBillStatus(Long groupId);

    List<PreviewAddBillResponse> addBillPreview(List<AddBillRequest> requests);

    List<RoomBillHistory> roomBillHistory(Long roomId);
}
