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
import vn.com.fpt.requests.EndGroupContractRequest;
import vn.com.fpt.requests.EndRoomContractRequest;
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
    @PutMapping("/room/update/{contractId}")
    public ResponseEntity<BaseResponse<RoomContractRequest>> updateContract(@PathVariable Long contractId,
                                                                            @RequestBody RoomContractRequest request) {
        return AppResponse.success(contractService.updateContract(contractId, request, Operator.operator()));
    }

    @Operation(summary = "Kết thúc hợp đồng của phòng")
    @PostMapping("/room/end")
    public ResponseEntity<BaseResponse<EndRoomContractRequest>> endContract(@RequestBody EndRoomContractRequest request) {
        return AppResponse.success(contractService.endRoomContract(request, Operator.operator()));
    }

    @Operation(summary = "Kết thúc hợp đồng của nhóm phòng")
    @PostMapping("/group/end")
    public ResponseEntity<BaseResponse<EndGroupContractRequest>> endGroupContract(@RequestBody EndGroupContractRequest request){
        return AppResponse.success(contractService.endGroupContract(request, Operator.operator()));
    }

    @Operation(summary = "Xem hợp đồng của phòng")
    @GetMapping("/room/{contractId}")
    public ResponseEntity<BaseResponse<RoomContractDTO>> roomContract(@PathVariable Long contractId) {

        return AppResponse.success(contractService.roomContract(contractId));
    }

    @Operation(summary = "Xem tất cả hợp đồng phòng trong nhóm phòng")
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<RoomContractDTO>>> listRoomContract(@RequestParam(required = false) Long groupId,
                                                                                @RequestParam(required = false) String phoneNumber,
                                                                                @RequestParam(required = false) String identity,
                                                                                @RequestParam(required = false) String renterName,
                                                                                @RequestParam(required = false) String startDate,
                                                                                @RequestParam(required = false) String endDate,
                                                                                @RequestParam(required = false) Integer status,
                                                                                @RequestParam(required = false, defaultValue = "1") Long duration,
                                                                                @RequestParam(required = false, defaultValue = "false") Boolean isDisable,
                                                                                @RequestParam(required = false) List<Long> roomId) {
        return AppResponse.success(contractService.listRoomContract(groupId,
                                                                    phoneNumber,
                                                                    identity,
                                                                    renterName,
                                                                    isDisable,
                                                                    startDate,
                                                                    endDate,
                                                                    status,
                                                                    duration,
                                                                    roomId));
    }


    @Operation(summary = "Thêm mới một hợp đồng cho nhóm phòng")
    @PostMapping("/group/add")
    public ResponseEntity<BaseResponse<GroupContractRequest>> addLeaseContract(@RequestBody GroupContractRequest request) {
        return AppResponse.success(contractService.addContract(request, Operator.operator()));
    }

    @Operation(summary = "Cập nhập hợp đồng cho nhóm phòng")
    @PutMapping("/group/update/{contractId}")
    public ResponseEntity<BaseResponse<GroupContractRequest>> updateLeaseContract(@RequestBody GroupContractRequest request,
                                                                                  @PathVariable Long contractId){
        return AppResponse.success(contractService.updateContract(contractId, request, Operator.operator()));
    }

//    @Operation(summary = "Cập nhập hợp đồng cho nhóm phòng")
//    @PostMapping("/group/update/{groupContractId}")
//    public ResponseEntity<BaseResponse<GroupContractRequest>> updateLeaseContract(@RequestBody GroupContractRequest request,
//                                                                                  @PathVariable Long groupContractId) {
//        return AppResponse.success(contractService.updateContract(groupContractId, request, Operator.operator()));
//    }

    @Operation(summary = "Danh sách tất cả hợp đồng của nhóm phòng")
    @GetMapping("/group")
    public ResponseEntity<BaseResponse<List<GroupContractDTO>>> listGroupContract(@RequestParam(required = false) String phoneNumber,
                                                                                  @RequestParam(required = false) String identity,
                                                                                  @RequestParam(required = false) Long groupId,
                                                                                  @RequestParam(required = false) String startDate,
                                                                                  @RequestParam(required = false) String endDate,
                                                                                  @RequestParam(required = false, defaultValue = "false") Boolean isDisable) {
        return AppResponse.success(contractService.listGroupContract(phoneNumber,
                                                                     identity,
                                                                     null,
                                                                     groupId,
                                                                     null,
                                                                     startDate,
                                                                     endDate,
                                                                     isDisable));
    }

    @Operation(summary = "Thông tin hợp đồng của nhóm phòng")
    @GetMapping("/group/{contractId}")
    public ResponseEntity<BaseResponse<GroupContractDTO>> groupContract(@PathVariable Long contractId) {
        return AppResponse.success(contractService.groupContract(contractId));
    }

}
