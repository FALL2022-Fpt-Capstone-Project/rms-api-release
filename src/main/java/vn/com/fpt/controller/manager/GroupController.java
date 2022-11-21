package vn.com.fpt.controller.manager;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.requests.AddGroupRequest;
import vn.com.fpt.responses.GroupAllResponse;
import vn.com.fpt.responses.GroupContractedResponse;
import vn.com.fpt.responses.GroupNonContractedResponse;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.rooms.RoomService;

import java.util.List;

import static vn.com.fpt.configs.AppConfigs.*;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Group Manager", description = "Quản lý nhóm phòng")
@RestController
@RequiredArgsConstructor
@RequestMapping(GroupController.PATH)
public class GroupController {
    public static final String PATH = V1_PATH + MANAGER_PATH + GROUP_PATH;

    private final GroupService groupService;

    private final RoomService roomService;

    @GetMapping("/{groupId}")
    @Operation(summary = "Lấy thông tin của nhóm phòng theo id")
    public ResponseEntity<BaseResponse<Object>> group(@PathVariable Long groupId) {
        return AppResponse.success(groupService.group(groupId));
    }

    @GetMapping("/all/contracted")
    @Operation(summary = "Lấy thông tin của tất cả nhóm phòng đã có hợp đồng")
    public ResponseEntity<BaseResponse<List<GroupContractedResponse>>> listGroupContracted() {
        return AppResponse.success(groupService.listContracted());
    }

    @GetMapping("/all/non-contracted")
    @Operation(summary = "Lấy thông tin của tất cả nhóm phòng đã có hợp đồng")
    public ResponseEntity<BaseResponse<List<GroupNonContractedResponse>>> listGroupNonContracted() {
        return AppResponse.success(groupService.listNonContracted());
    }

    @GetMapping("/all")
    @Operation(summary = "Lấy thông tin của tất cả nhóm phòng bao gồm có và chưa có hợp đồng")
    public ResponseEntity<BaseResponse<GroupAllResponse>> listGroup() {
        return AppResponse.success(groupService.listGroup());
    }

    @PostMapping("/add")
    @Operation(summary = "Tạo mới một nhóm phòng")
    public ResponseEntity<BaseResponse<Object>> add(@RequestBody AddGroupRequest request) {
        return AppResponse.success(groupService.add(request, Operator.operator()));
    }

    @DeleteMapping("/delete/{groupId}")
    @Operation(summary = "Xóa một nhóm phòng")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable Long groupId){
        return AppResponse.success(groupService.delete(groupId, Operator.operator()));
    }

    @PostMapping("/preview")
    @Operation(summary = "Xem trước kết quả tự động tạo mới các phòng")
    public ResponseEntity<BaseResponse<List<Rooms>>> preview(@RequestBody @NotNull AddGroupRequest request) {
        return AppResponse.success(roomService.previewGenerateRoom(
                request.getTotalRoomPerFloor(),
                request.getTotalFloor(),
                request.getRoomLimitedPeople(),
                request.getRoomPrice(),
                request.getRoomArea(),
                request.getRoomNameConvention(),
                Operator.operator())
        );
    }

}
