package vn.com.fpt.service.bill;

import vn.com.fpt.requests.PreviewAddBillRequest;
import vn.com.fpt.responses.PayBillInformationResponse;
import vn.com.fpt.entity.RecurringBill;
import vn.com.fpt.requests.AddBillRequest;
import vn.com.fpt.responses.*;

import java.util.List;

public interface BillService {
    List<BillRoomStatusResponse> listBillRoomStatus( Long groupId, Integer billCircle);

    List<AddBillRequest> addBill(List<AddBillRequest> addBillRequests);

    List<ListRoomWithBillStatusResponse> listRoomWithBillStatus(Long groupId);

    List<PreviewAddBillResponse> addBillPreview(List<PreviewAddBillRequest> requests);

    List<RecurringBill> roomBillHistory(Long roomId);

    void payRoomBill(List<Long> billId);

    void deleteRoomBill(List<Long> billId);

    PayBillInformationResponse payBillInformation(Long roomId);

    Boolean groupBillCheck(Long groupContractId);

    Boolean roomBillCheck(Long contractId);

    List<RecurringBill> listRecurringBillByGroupId(Long groupId);
}
