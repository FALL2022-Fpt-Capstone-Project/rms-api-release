package vn.com.fpt.service.contract;

import vn.com.fpt.requests.ContractRequest;

public interface ContractService {
    ContractRequest addContract(ContractRequest request, Long operator);
}
