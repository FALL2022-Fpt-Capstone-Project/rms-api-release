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
import vn.com.fpt.requests.UpdateRoomRequest;
import vn.com.fpt.responses.RoomsResponse;
import vn.com.fpt.service.rooms.RoomService;

import java.util.List;

import static vn.com.fpt.configs.AppConfigs.*;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Room Manager", description = "Quản lý phòng ")
@RestController
@RequiredArgsConstructor
@RequestMapping(RoomController.PATH)
public class RoomController {
    public static final String PATH = V1_PATH + MANAGER_PATH + ROOM_PATH;

    private final RoomService roomService;

    @GetMapping("/")
    @Operation(summary = "Danh sách các phòng")
    public ResponseEntity<BaseResponse<List<RoomsResponse>>> room(@RequestParam(required = false) Long groupId,
                                                                  @RequestParam(required = false) Long groupContractId,
                                                                  @RequestParam(required = false) String name,
                                                                  @RequestParam(required = false) Long floor,
                                                                  @RequestParam(required = false) Integer available){
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

    @DeleteMapping("/delete/list/{roomId}")
    @Operation(summary = "Xóa nhiều phòng")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable Long[] roomId) {
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

}
