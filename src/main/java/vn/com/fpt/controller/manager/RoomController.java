package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
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
                                                                  @RequestParam(required = false) String name,
                                                                  @RequestParam(required = false) Long floor,
                                                                  @RequestParam(required = false) Integer available){
        return AppResponse.success(roomService.listRoom(groupId, floor, available, name));
    }
}
