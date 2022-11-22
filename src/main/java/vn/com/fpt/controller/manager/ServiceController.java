package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.requests.GeneralServiceRequest;
import vn.com.fpt.entity.BasicServices;
import vn.com.fpt.entity.GeneralService;
import vn.com.fpt.entity.ServiceTypes;
import vn.com.fpt.model.GeneralServiceDTO;
import vn.com.fpt.service.services.ServicesService;

import java.util.List;

import static vn.com.fpt.configs.AppConfigs.*;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Service Manager", description = "Quản lý dịch vụ chung")
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceController.PATH)
public class ServiceController {
    public static final String PATH = V1_PATH + MANAGER_PATH + SERVICE_PATH;

    private final ServicesService servicesService;

    @Operation(summary = "Danh sách các dịch vụ chung phổ biến")
    @GetMapping("/basics")
    public ResponseEntity<BaseResponse<List<BasicServices>>> basicServices() {
        return AppResponse.success(servicesService.basicServices());
    }

    @Operation(summary = "Danh sách các cách tính dịch vụ chung")
    @GetMapping("/types")
    public ResponseEntity<BaseResponse<List<ServiceTypes>>> serviceTypes() {
        return AppResponse.success(servicesService.serviceTypes());
    }

    @Operation(summary = "Danh sách thông tin dịch vụ chung của tòa")
    @GetMapping("/general")
    public ResponseEntity<BaseResponse<List<GeneralServiceDTO>>> generalServices(@RequestParam(required = false) Long groupId) {
        if (ObjectUtils.isEmpty(groupId)) throw new RuntimeException("group_id không được trống");
        return AppResponse.success(servicesService.listGeneralServiceByGroupId(groupId));
    }

    @Operation(summary = "Xem thông tin chi tiết của dich vụ chung")
    @GetMapping("/general/{generalServiceId}")
    public ResponseEntity<BaseResponse<GeneralServiceDTO>> generalService(@PathVariable Long generalServiceId) {
        return AppResponse.success(servicesService.generalService(generalServiceId));
    }

    @Operation(summary = "Thêm mới dịch vụ chung cho tòa")
    @PostMapping("/general/add")
    public ResponseEntity<BaseResponse<GeneralService>> addGeneralService(@RequestBody GeneralServiceRequest request) {
        return AppResponse.success(servicesService.addGeneralService(request, Operator.operator()));
    }

    @Operation(summary = "Thêm nhanh các loại dịch vụ phổ biến vào tòa")
    @PostMapping("/general/quick-add/{contractId}")
    public ResponseEntity<BaseResponse<List<GeneralService>>> quickAddGeneralService(@PathVariable Long contractId) {
        return AppResponse.success(servicesService.quickAddGeneralService(contractId, Operator.operator()));
    }

    @Operation(summary = "Update thông tin của dịch vụ chung theo id")
    @PutMapping("/general/update/{generalServiceId}")
    public ResponseEntity<BaseResponse<GeneralService>> updateGeneralService(@PathVariable Long generalServiceId,
                                                                             @RequestBody GeneralServiceRequest request) {
        return AppResponse.success(servicesService.updateGeneralService(generalServiceId, request, Operator.operator()));
    }

    @Operation(summary = "Xóa dịch vụ chung ra khỏi tòa")
    @DeleteMapping("/general/remove/{generalServiceId}")
    public ResponseEntity<BaseResponse<String>> removeGeneralService(@PathVariable Long generalServiceId) {
        return AppResponse.success(servicesService.removeGeneralService(generalServiceId));
    }


}
