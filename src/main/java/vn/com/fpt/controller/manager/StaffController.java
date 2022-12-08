package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.responses.AccountResponse;
import vn.com.fpt.service.staff.StaffService;
import vn.com.fpt.common.utils.Operator;

import javax.validation.Valid;
import java.util.List;

import static vn.com.fpt.configs.AppConfigs.V1_PATH;
import static vn.com.fpt.configs.AppConfigs.MANAGER_PATH;
import static vn.com.fpt.configs.AppConfigs.STAFF_PATH;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Staff manager", description = "Quản lý tài khoản cho nhân viên")
@RestController
@RequiredArgsConstructor
@RequestMapping(StaffController.PATH)
public class StaffController {
    public static final String PATH = V1_PATH + MANAGER_PATH + STAFF_PATH;

    private final StaffService staffService;


    @GetMapping
    @Operation(summary = "Danh sách tài khoản nhân viên")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<List<AccountResponse>>> list(@RequestParam(required = false) String role,
                                                                    @RequestParam(required = false) String order,
                                                                    @RequestParam(required = false) String startDate,
                                                                    @RequestParam(required = false) String endDate,
                                                                    @RequestParam(required = false, defaultValue = "false") boolean deactivate,
                                                                    @RequestParam(required = false) String name,
                                                                    @RequestParam(required = false) String userName) {
        return AppResponse.success(staffService.listStaff(role, order, startDate, endDate, deactivate, name, userName));
    }

    @GetMapping("/{staffId}")
    @Operation(summary = "Thông tin tài khoản nhân viên theo id")
    public ResponseEntity<BaseResponse<AccountResponse>> staff(@PathVariable Long staffId) {
        return AppResponse.success(staffService.staff(staffId));
    }

    @GetMapping("/roles")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Danh sách các quyền của nhân viên")
    public ResponseEntity<BaseResponse<List<String>>> roles() {
        return AppResponse.success(staffService.roles());
    }


    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Thêm tài khoản cho nhân viên")
    public ResponseEntity<BaseResponse<AccountResponse>> add(@Valid @RequestBody RegisterRequest request) {
        return AppResponse.success(staffService.addStaff(request, Operator.operator()));
    }

    @PutMapping("/update/{staffId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update tài khoản cho nhân viên theo id")
    public ResponseEntity<BaseResponse<AccountResponse>> update(@RequestBody RegisterRequest request,
                                                                @PathVariable Long staffId) {
        return AppResponse.success(staffService.updateStaff(staffId, request, Operator.operator(), Operator.time()));
    }


}
