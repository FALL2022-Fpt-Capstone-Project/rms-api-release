package vn.com.fpt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.entity.config.Month;
import vn.com.fpt.service.config_value.ConfigService;

import java.util.List;

import static vn.com.fpt.configs.AppConfigs.*;

@Tag(name = "Config", description = "Lấy các giá trị thiết lập")
@RestController
@RequiredArgsConstructor
@RequestMapping(ConfigController.PATH)
public class ConfigController {
    public static final String PATH = V1_PATH + CONFIG_PATH;

    private final ConfigService configService;

    @Operation(summary = "Lấy số lượng tháng")
    @GetMapping("/month")
    public ResponseEntity<BaseResponse<List<Month>>> getConfigMonth(){
        return AppResponse.success(configService.listConfigMonth());
    }
}
