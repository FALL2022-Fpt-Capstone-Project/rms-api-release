package vn.com.fpt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.entity.config.Month;
import vn.com.fpt.entity.config.TotalFloor;
import vn.com.fpt.entity.config.TotalRoom;
import vn.com.fpt.model.DistrictDTO;
import vn.com.fpt.service.configValue.ConfigService;

import java.util.List;

import static vn.com.fpt.configs.AppConfigs.*;

@Tag(name = "Config", description = "Lấy các giá trị thiết lập")
@RestController
@RequestMapping(ConfigController.PATH)
public class ConfigController {
    public static final String PATH = V1_PATH + CONFIG_PATH;
    @Autowired
    private ConfigService configService;

    @Operation(summary = "Lấy số lượng tháng")
    @GetMapping("/month")
    public ResponseEntity<BaseResponse<List<Month>>> getConfigMonth(){
        return AppResponse.success(configService.listConfigMonth());
    }

    @Operation(summary = "Lấy số lượng tầng")
    @GetMapping("/floor")
    public ResponseEntity<BaseResponse<List<TotalFloor>>> getConfigFloor(){
        return AppResponse.success(configService.listConfigFloor());
    }

    @Operation(summary = "Lấy số lượng phòng")
    @GetMapping("/room")
    public ResponseEntity<BaseResponse<List<TotalRoom>>> getConfigRoom(){
        return AppResponse.success(configService.listConfigRoom());
    }

    @Operation(summary = "Lấy thông tin địa chỉ thành phố đã được thêm")
    @GetMapping("/city")
    public ResponseEntity<BaseResponse<List<DistrictDTO>>> getAddedCity(){
        return AppResponse.success(configService.listAddedCity());
    }
}
