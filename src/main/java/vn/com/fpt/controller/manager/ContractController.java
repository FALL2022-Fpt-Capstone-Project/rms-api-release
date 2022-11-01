package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.requests.AddContractRequest;
import vn.com.fpt.service.contract.ContractService;

import static vn.com.fpt.configs.AppConfigs.*;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Contract Manager", description = "Quản lý hợp đồng chung cư, phòng")
@RestController
@RequiredArgsConstructor
@RequestMapping(ContractController.PATH)
public class ContractController {

    public static final String PATH = V1_PATH + MANAGER_PATH + CONTRACT_PATH;

    private final ContractService contractService;

    @Operation(summary = "Thêm mới hợp đồng")
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<AddContractRequest>> addContract(@RequestBody AddContractRequest request) {
        return AppResponse.success(contractService.addContract(request,  Operator.operator()));
    }

}
