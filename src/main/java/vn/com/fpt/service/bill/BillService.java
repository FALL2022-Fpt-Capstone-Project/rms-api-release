package vn.com.fpt.service.bill;

import vn.com.fpt.requests.GenerateBillRequest;
import vn.com.fpt.responses.NotBilledRoomResponse;
import vn.com.fpt.responses.PreviewGenerateBillResponse;

import java.util.List;

public interface BillService {

    List<PreviewGenerateBillResponse> generatePreview(GenerateBillRequest request);

    List<NotBilledRoomResponse> listNotBilledRoom(Long groupContractId, Integer billCircle);
}
