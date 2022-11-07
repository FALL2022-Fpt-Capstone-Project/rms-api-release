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
import vn.com.fpt.requests.RenterRequest;
import vn.com.fpt.responses.RentersResponse;
import vn.com.fpt.service.renter.RenterService;

import java.util.List;

import static vn.com.fpt.configs.AppConfigs.V1_PATH;
import static vn.com.fpt.configs.AppConfigs.MANAGER_PATH;
import static vn.com.fpt.configs.AppConfigs.RENTER_PATH;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Renter Manager", description = "Quản lý khách thuê trong chung cư")
@RestController
@RequiredArgsConstructor
@RequestMapping(RenterController.PATH)
public class RenterController {
    public static final String PATH = V1_PATH + MANAGER_PATH + RENTER_PATH;

    private final RenterService renterService;

    @GetMapping("")
    @Operation(description = "Danh sách thông tin khách thuê")
    public ResponseEntity<BaseResponse<List<RentersResponse>>> listRenter(@RequestParam(required = false) Long group,
                                                                          @RequestParam(required = false) Long room,
                                                                          @RequestParam(required = false) Boolean gender,
                                                                          @RequestParam(required = false) String name,
                                                                          @RequestParam(required = false) String phone) {
        return AppResponse.success(renterService.listRenter(group, gender, name, phone, room));
    }

    @GetMapping("/{renterId}")
    @Operation(description = "Lấy thông tin khách thuê")
    public ResponseEntity<BaseResponse<RentersResponse>> renter(@PathVariable Long renterId) {
        return AppResponse.success(renterService.renter(renterId));
    }

    @PostMapping("/add")
    @Operation(description = "Thêm mới khách thuê")
    public ResponseEntity<BaseResponse<RentersResponse>> addRenter(@RequestBody RenterRequest request) {
        return AppResponse.success(renterService.addRenter(request, Operator.operator()));
    }

    @PutMapping("/{renterId}")
    @Operation(description = "Cập nhập thông tin khách khách thuê")
    public ResponseEntity<BaseResponse<RentersResponse>> updateRenter(@RequestBody RenterRequest request,
                                                                      @PathVariable Long renterId) {
        return AppResponse.success(renterService.updateRenter(renterId, request, Operator.operator()));
    }

    @DeleteMapping("/{renterId}")
    @Operation(description = "Xóa thông tin khách thuê")
    public ResponseEntity<BaseResponse<String>> deleteRenter(@PathVariable Long renterId) {
        return AppResponse.success(renterService.deleteRenter(renterId));
    }

    @GetMapping("/remove/{renterId}")
    @Operation(description = "Xóa khách thuê ra khỏi phòng")
    public ResponseEntity<BaseResponse<RentersResponse>> removeFromRoom(@PathVariable Long renterId) {
        return AppResponse.success(renterService.removeFromRoom(renterId, Operator.operator()));
    }
}
