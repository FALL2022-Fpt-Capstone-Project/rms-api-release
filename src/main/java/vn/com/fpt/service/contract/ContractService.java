package vn.com.fpt.service.contract;

import vn.com.fpt.requests.AddContractRequest;

public interface ContractService {
    AddContractRequest addContract(AddContractRequest request, Long operator);
}
