package vn.com.fpt.controller.manager;

import io.sentry.protocol.App;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.entity.Contracts;
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

    @Operation(summary = "Cập nhập hợp đồng cho phòng", tags = "Room")
    @PutMapping("/room/update/{id}")
    public ResponseEntity<BaseResponse<RoomContractRequest>> updateContract(@PathVariable Long id,
                                                                            @RequestBody RoomContractRequest request) {
        return AppResponse.success(contractService.updateContract(id, request, Operator.operator()));
    }

    @Operation(summary = "Xem hợp đồng của phòng", tags = "Room")
    @GetMapping("/room/{id}")
    public ResponseEntity<BaseResponse<RoomContractDTO>> roomContract(@PathVariable Long id) {

        return AppResponse.success(contractService.roomContract(id));
    }

    @Operation(summary = "Xem tất cả hợp đồng phòng trong nhóm phòng", tags = "Room")
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<RoomContractDTO>>> listRoomContract(@RequestParam(required = false) Long groupId,
                                                                                @RequestParam(required = false) String phoneNumber,
                                                                                @RequestParam(required = false) String identity,
                                                                                @RequestParam(required = false) String renterName,
                                                                                @RequestParam(required = false) String startDate,
                                                                                @RequestParam(required = false) String endDate) {
        return AppResponse.success(contractService.listRoomContract(groupId,
                                                                    phoneNumber,
                                                                    identity,
                                                                    renterName,
                                                                    startDate,
                                                                    endDate));
    }


    @Operation(summary = "Thêm mới một hợp đồng cho nhóm phòng", tags = "Group")
    @PostMapping("/group/add")
    public ResponseEntity<BaseResponse<GroupContractRequest>> addContract(@RequestBody GroupContractRequest request) {
        return AppResponse.success(contractService.addContract(request, Operator.operator()));
    }

    @Operation(summary = "Danh sách tất cả hợp đồng của nhóm phòng", tags = "Group")
    @GetMapping("/group")
    public ResponseEntity<BaseResponse<List<GroupContractDTO>>> listGroupContract() {
        return AppResponse.success(contractService.listGroupContract());
    }

    @Operation(summary = "Thông tin hợp đồng của nhóm phòng", tags = "Group")
    @GetMapping("/group/{id}")
    public ResponseEntity<BaseResponse<Contracts>> groupContract(@PathVariable Long id) {
        return AppResponse.success(contractService.groupContract(id));
    }

}
