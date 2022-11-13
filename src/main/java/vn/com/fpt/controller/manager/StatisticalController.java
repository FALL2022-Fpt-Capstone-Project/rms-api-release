package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.responses.StatisticalRoomContractResponse;
import vn.com.fpt.service.statistical.StatisticalService;

import static vn.com.fpt.configs.AppConfigs.*;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Statistical", description = "Hiện thị các số liệu thống kê")
@RestController
@RequiredArgsConstructor
@RequestMapping(StatisticalController.PATH)
public class StatisticalController {
    public static final String PATH = V1_PATH + MANAGER_PATH + STATISTICAL_PATH;

    private final StatisticalService statisticalService;

    @GetMapping("/contract/room")
    @Operation(summary = "Hiện thị số liệu thống kê hợp đồng của phòng")
    public ResponseEntity<BaseResponse<StatisticalRoomContractResponse>> roomContract(@RequestParam(required = false, defaultValue = "1") Long duration,
                                                                                      @RequestParam(required = false) Long groupId) {
        return AppResponse.success(statisticalService.statisticalRoomContract(groupId, duration));
    }
}
