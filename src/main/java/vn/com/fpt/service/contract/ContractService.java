package vn.com.fpt.service.contract;

import org.springframework.http.ResponseEntity;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.requests.AddContractRequest;

public interface ContractService {
    AddContractRequest addContract(AddContractRequest request, Long operator);
}
