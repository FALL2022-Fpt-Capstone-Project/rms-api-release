package vn.com.fpt.service.bill;

import vn.com.fpt.requests.GenerateBillRequest;
import vn.com.fpt.responses.BillRoomStatusResponse;
import vn.com.fpt.responses.PreviewGenerateBillResponse;

import java.util.List;

public interface BillService {

    List<PreviewGenerateBillResponse> generatePreview(GenerateBillRequest request);

    List<BillRoomStatusResponse> listBillRoomStatus(Long groupContractId, Integer billCircle);
}
