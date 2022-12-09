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
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.requests.*;
import vn.com.fpt.responses.AdjustRoomPriceResponse;
import vn.com.fpt.responses.RoomsPreviewResponse;
import vn.com.fpt.responses.RoomsResponse;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.rooms.RoomService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static vn.com.fpt.common.constants.ManagerConstants.NOT_RENTED_YET;
import static vn.com.fpt.configs.AppConfigs.*;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Room Manager", description = "Quản lý phòng ")
@RestController
@RequiredArgsConstructor
@RequestMapping(RoomController.PATH)
public class RoomController {
    public static final String PATH = V1_PATH + MANAGER_PATH + ROOM_PATH;

    private final RoomService roomService;

    private final AssetService assetService;

    @GetMapping("/")
    @Operation(summary = "Danh sách các phòng")
    public ResponseEntity<BaseResponse<List<RoomsResponse>>> room(@RequestParam(required = false) Long groupId,
                                                                  @RequestParam(required = false) Long groupContractId,
                                                                  @RequestParam(required = false) String name,
                                                                  @RequestParam(required = false) Integer floor,
                                                                  @RequestParam(required = false) Integer available) {
        return AppResponse.success(roomService.listRoom(groupId,
                groupContractId,
                floor,
                available,
                name));
    }

    @DeleteMapping("/delete/{roomId}")
    @Operation(summary = "Xóa một phòng")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable Long roomId) {
        var deletedRoom = roomService.removeRoom(roomId, Operator.operator());
        return AppResponse.success(String.format("Xóa phòng %s thành công", deletedRoom.getRoomName()));
    }

    @DeleteMapping("/delete/list")
    @Operation(summary = "Xóa nhiều phòng")
    public ResponseEntity<BaseResponse<String>> delete(@RequestParam List<Long> roomId) {
        var deletedRoom = roomService.removeRoom(roomId, Operator.operator());
        String roomNameDeleted = String.join(", ", deletedRoom.stream().map(Rooms::getRoomName).toList());
        return AppResponse.success(String.format("Xóa phòng %s thành công", roomNameDeleted));
    }

    @PutMapping("/update")
    @Operation(summary = "Cập nhập một hoặc nhiều phòng")
    public ResponseEntity<BaseResponse<String>> update(@RequestBody List<UpdateRoomRequest> requests) {
        var updatedRoom = roomService.update(requests, Operator.operator());
        String roomNameUpdated = String.join(", ", updatedRoom.stream().map(Rooms::getRoomName).toList());
        return AppResponse.success(String.format("Cập nhập %s thành công", roomNameUpdated));
    }

    @PostMapping("/room-price-adjust")
    public ResponseEntity<BaseResponse<AdjustRoomPriceResponse>> adjust(@RequestBody AdjustRoomPriceRequest request){
        return AppResponse.success(roomService.adjustRoomPrice(request, Operator.operator()));
    }

    @PostMapping("/generate/preview")
    @Operation(summary = "Xem trước list phòng trước khi gen tự động")
    public ResponseEntity<BaseResponse<RoomsPreviewResponse.SeparationRoomPreview>> previewGenerate(@RequestBody RoomsPreviewRequest request) {
        return AppResponse.success(roomService.preview(request));
    }

    @PostMapping("/add")
    @Operation(summary = "Thêm một hoặc nhiều phòng")
    public ResponseEntity<BaseResponse<List<Rooms>>> add(@RequestBody List<AddRoomsRequest> requests) {
        List<Rooms> roomToAdd = new ArrayList<>(Collections.emptyList());
        requests.forEach(e -> {
                    if (e.getIsOld() != null && !e.getIsOld()) {
                        var addedRoom = roomService.add(Rooms.add(
                                e.getRoomName(),
                                e.getRoomFloor(),
                                e.getRoomLimitPeople(),
                                e.getGroupId(),
                                NOT_RENTED_YET,
                                e.getRoomPrice(),
                                e.getRoomArea(),
                                Operator.operator()
                        ));
                        roomToAdd.add(addedRoom);
                        e.getRoomAsset().forEach(x -> x.setRoomId(addedRoom.getId()));
                        assetService.roomAdd(e.getRoomAsset(), Operator.operator());
                    }

                }
        );
        return AppResponse.success(roomToAdd);
    }

}
