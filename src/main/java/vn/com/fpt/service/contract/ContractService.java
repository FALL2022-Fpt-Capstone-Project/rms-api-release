package vn.com.fpt.service.contract;

import vn.com.fpt.entity.Contracts;
import vn.com.fpt.model.RoomContractDTO;
import vn.com.fpt.requests.RoomContractRequest;

public interface ContractService {
    RoomContractRequest addContract(RoomContractRequest request, Long operator);

    RoomContractRequest updateContract(Long id, RoomContractRequest request, Long operator);

    Contracts groupContract(Long groupId);

    Contracts contract(Long id);

    RoomContractDTO roomContract(Long id);
}
