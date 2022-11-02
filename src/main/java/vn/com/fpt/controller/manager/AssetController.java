package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.entity.AssetTypes;

import java.util.List;

import static vn.com.fpt.configs.AppConfigs.*;


@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Asset Manager", description = "Quản lý tài sản")
@RestController
@RequiredArgsConstructor
@RequestMapping(AssetController.PATH)
public class AssetController {
    public static final String PATH = V1_PATH + MANAGER_PATH + ASSET_PATH;

    @GetMapping("/type")
    public ResponseEntity<BaseResponse<List<AssetTypes>>> listType(){
        //TODO
        return null;
    }

}
