package vn.com.fpt.service.contract;

import vn.com.fpt.requests.RoomContractRequest;

public interface ContractService {
    RoomContractRequest addContract(RoomContractRequest request, Long operator);
}
