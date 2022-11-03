package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.model.GroupContractDTO;
import vn.com.fpt.model.RoomContractDTO;
import vn.com.fpt.requests.GroupContractRequest;
import vn.com.fpt.requests.RoomContractRequest;
import vn.com.fpt.service.contract.ContractService;

import java.util.List;

import static vn.com.fpt.configs.AppConfigs.*;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Contract Manager", description = "Quản lý hợp đồng chung cư, phòng")
@RestController
@RequiredArgsConstructor
@RequestMapping(ContractController.PATH)
public class ContractController {

    public static final String PATH = V1_PATH + MANAGER_PATH + CONTRACT_PATH;

    private final ContractService contractService;



    @Operation(summary = "Thêm mới hợp đồng cho phòng")
    @PostMapping("/room/add")
    public ResponseEntity<BaseResponse<RoomContractRequest>> addContract(@RequestBody RoomContractRequest request) {
        return AppResponse.success(contractService.addContract(request, Operator.operator()));
    }

    @Operation(summary = "Cập nhập hợp đồng cho phòng")
    @PutMapping("/room/update/{id}")
    public ResponseEntity<BaseResponse<RoomContractRequest>> updateContract(@PathVariable Long id,
                                                                            @RequestBody RoomContractRequest request) {
        return AppResponse.success(contractService.updateContract(id, request, Operator.operator()));
    }

    @Operation(summary = "Xem hợp đồng của phòng")
    @GetMapping("/room/{id}")
    public ResponseEntity<BaseResponse<RoomContractDTO>> roomContract(@PathVariable Long id) {

        return AppResponse.success(contractService.roomContract(id));
    }


    @PostMapping("/group/add")
    public ResponseEntity<BaseResponse<GroupContractRequest>> addContract(@RequestBody GroupContractRequest request) {
        // TODO
        return null;
    }

    @GetMapping("/group")
    public ResponseEntity<BaseResponse<GroupContractDTO>> listGroupContract() {
        // TODO
        return null;
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<BaseResponse<GroupContractDTO>> groupContract(@PathVariable Long id) {
        // TODO
        return null;
    }

    @GetMapping("/room")
    public ResponseEntity<BaseResponse<List<RoomContractDTO>>> listRoomContract() {
        // TODO
        return null;
    }



}
