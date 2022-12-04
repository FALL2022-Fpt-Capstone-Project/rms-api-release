package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.entity.AssetTypes;
import vn.com.fpt.entity.BasicAssets;
import vn.com.fpt.entity.RoomAssets;
import vn.com.fpt.model.BasicAssetDTO;
import vn.com.fpt.model.RoomAssetDTO;
import vn.com.fpt.requests.BasicAssetsRequest;
import vn.com.fpt.requests.RoomAssetsRequest;
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

    @GetMapping("/room")
    @Operation(summary = "Tất cả các trang thiết bị theo filter")
    public ResponseEntity<BaseResponse<List<RoomAssetDTO>>> roomAssets(@RequestParam(required = false) Long assetType,
                                                                       @RequestParam(required = false) Long roomId) {
        return AppResponse.success(assetService.listRoomAsset(roomId, assetType));
    }


    @GetMapping("/{roomId}")
    @Operation(summary = "Xem các trang thiết bị trong phòng")
    public ResponseEntity<BaseResponse<List<RoomAssetDTO>>> roomAsset(@PathVariable Long roomId) {
        return AppResponse.success(assetService.listRoomAsset(roomId, null));
    }

    @PostMapping("/room/add")
    @Operation(summary = "Thêm một hoặc nhiều trang thiết bị cho phòng")
    public ResponseEntity<BaseResponse<List<RoomAssets>>> roomAdd(@Validated @RequestBody List<RoomAssetsRequest> request) {
        return AppResponse.success(assetService.roomAdd(request, Operator.operator()));
    }

    @PutMapping("/room/update")
    @Operation(summary = "Cập nhập một hoặc nhiều trang thiết bị trong phòng")
    public ResponseEntity<BaseResponse<List<RoomAssets>>> roomUpdate(@Validated @RequestBody List<RoomAssetsRequest> requests) {
        return AppResponse.success(assetService.updateRoomAsset(requests, Operator.operator()));
    }

    @DeleteMapping("/room/delete")
    @Operation(summary = "Xóa một hoặc nhiều trang thiết bị trong phòng")
    public ResponseEntity<BaseResponse<List<RoomAssets>>> roomDelete(@RequestParam(required = false) List<Long> roomAssetId) {
        if (roomAssetId.isEmpty()) throw new BusinessException("Id của trang thiết bị không được để trống");
        return AppResponse.success(assetService.deleteRoomAsset(roomAssetId));
    }

//    @PostMapping("/hand-over/add/{contractId}")
//    @Operation(summary = "Thêm trang thiết bị bàn giao cho hợp đồng")
//    public ResponseEntity<BaseResponse<HandOverAssets>> add(@RequestBody HandOverAssetsRequest request,
//                                                            @PathVariable Long contractId) {
//        return AppResponse.success(assetService.addAdditionalAsset(request, contractId, SUBLEASE_CONTRACT, Operator.operator()));
//    }

//    @PutMapping("/hand-over/update/{id}")
//    @Operation(summary = "Cập nhập trang thiết bị bàn gia")

    @PutMapping("/update/{assetId}")
    @Operation(summary = "Cập nhập trang thiết bị cơ bản, thiết yếu")
    public ResponseEntity<BaseResponse<BasicAssets>> update(@PathVariable Long assetId,
                                                            @RequestBody BasicAssetsRequest request) {
        return AppResponse.success(assetService.update(assetId, request, Operator.operator()));
    }

}
