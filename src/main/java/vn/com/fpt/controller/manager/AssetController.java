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
import vn.com.fpt.entity.AssetTypes;
import vn.com.fpt.entity.BasicAssets;
import vn.com.fpt.entity.HandOverAssets;
import vn.com.fpt.model.BasicAssetDTO;
import vn.com.fpt.requests.BasicAssetsRequest;
import vn.com.fpt.requests.HandOverAssetsRequest;
import vn.com.fpt.service.assets.AssetService;

import java.util.List;

import static vn.com.fpt.common.constants.ManagerConstants.SUBLEASE_CONTRACT;
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
    @Operation(summary = "Danh sách các loại trang thiết bị cơ bản")
    public ResponseEntity<BaseResponse<List<AssetTypes>>> listType() {
        return AppResponse.success(assetService.listAssetType());
    }

    @GetMapping("/")
    @Operation(summary = "Danh sách các trang thiết bị cơ bản, thiết yếu")
    public ResponseEntity<BaseResponse<List<BasicAssetDTO>>> list() {
        return AppResponse.success(assetService.listBasicAsset());
    }

    @GetMapping("/{assetId}")
    @Operation(summary = "Lấy thông tin trang thiết bị cơ bản, thiết yếu")
    public ResponseEntity<BaseResponse<BasicAssets>> basicAsset(@PathVariable Long assetId) {
        return AppResponse.success(assetService.basicAssets(assetId));
    }

    @PostMapping("/add")
    @Operation(summary = "Thêm trang thiết bị cơ bản, thiết yếu")
    public ResponseEntity<BaseResponse<BasicAssets>> add(@RequestBody BasicAssetsRequest request) {
        return AppResponse.success(assetService.add(request, Operator.operator()));
    }

    @PostMapping("/hand-over/add/{contractId}")
    @Operation(summary = "Thêm trang thiết bị bàn giao cho hợp đồng")
    public ResponseEntity<BaseResponse<HandOverAssets>> add(@RequestBody HandOverAssetsRequest request,
                                                            @PathVariable Long contractId) {
        return AppResponse.success(assetService.addAdditionalAsset(request, contractId, SUBLEASE_CONTRACT, Operator.operator()));
    }

//    @PutMapping("/hand-over/update/{id}")
//    @Operation(summary = "Cập nhập trang thiết bị bàn gia")

    @PutMapping("/update/{assetId}")
    @Operation(summary = "Cập nhập trang thiết bị cơ bản, thiết yếu")
    public ResponseEntity<BaseResponse<BasicAssets>> update(@PathVariable Long assetId,
                                                            @RequestBody BasicAssetsRequest request) {
        return AppResponse.success(assetService.update(assetId, request, Operator.operator()));
    }

    @DeleteMapping("/delete/{assetId}")
    @Operation(summary = "Xóa trang thiết bị cơ bản, thiết yếu")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable Long assetId) {
        // TODO
        return null;
    }

}
