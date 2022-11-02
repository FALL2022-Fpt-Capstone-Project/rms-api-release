package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.entity.AssetTypes;
import vn.com.fpt.entity.BasicAssets;
import vn.com.fpt.requests.BasicAssetsRequest;
import vn.com.fpt.service.assets.AssetService;

import java.util.List;

import static vn.com.fpt.configs.AppConfigs.*;


@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Asset Manager", description = "Quản lý tài sản")
@RestController
@RequiredArgsConstructor
@RequestMapping(AssetController.PATH)
public class AssetController {
    public static final String PATH = V1_PATH + MANAGER_PATH + ASSET_PATH;

    private final AssetService assetService;

    @GetMapping("/type")
    public ResponseEntity<BaseResponse<List<AssetTypes>>> listType() {
        return AppResponse.success(assetService.listAssetType());
    }

    @GetMapping("/")
    public ResponseEntity<BaseResponse<List<BasicAssets>>> list() {
        return AppResponse.success(assetService.listBasicAsset());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<BasicAssets>> basicAsset(@PathVariable Long id) {
        return AppResponse.success(assetService.basicAssets(id));
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse<BasicAssets>> add(@RequestBody BasicAssetsRequest request) {
        return AppResponse.success(assetService.add(request, Operator.operator()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse<BasicAssets>> update(@PathVariable Long id,
                                                            @RequestBody BasicAssetsRequest request) {
        return AppResponse.success(assetService.update(id, request, Operator.operator()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable Long id) {
        // TODO
        return null;
    }

}
