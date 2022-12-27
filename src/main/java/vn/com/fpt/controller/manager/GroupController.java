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
import vn.com.fpt.requests.AddGroupRequest;
import vn.com.fpt.requests.UpdateGroupRequest;
import vn.com.fpt.responses.GroupAllResponse;
import vn.com.fpt.responses.GroupContractedResponse;
import vn.com.fpt.responses.GroupNonContractedResponse;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.rooms.RoomService;

import javax.validation.Valid;
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
    @Operation(summary = "Lấy thông tin của chung cư mini theo id")
    public ResponseEntity<BaseResponse<Object>> group(@PathVariable Long groupId) {
        return AppResponse.success(groupService.group(groupId));
    }

    @GetMapping("/all/contracted")
    @Operation(summary = "Lấy thông tin của tất cả chung cư mini đã có hợp đồng")
    public ResponseEntity<BaseResponse<List<GroupContractedResponse>>> listGroupContracted(@RequestBody(required = false) String city) {
        return AppResponse.success(groupService.listContracted(city));
    }

    @GetMapping("/all/non-contracted")
    @Operation(summary = "Lấy thông tin của tất cả chung cư mini đã có hợp đồng")
    public ResponseEntity<BaseResponse<List<GroupNonContractedResponse>>> listGroupNonContracted(@RequestBody(required = false) String city) {
        return AppResponse.success(groupService.listNonContracted(city));
    }

    @GetMapping("/all")
    @Operation(summary = "Lấy thông tin của tất cả chung cu mini bao gồm có và chưa có hợp đồng")
    public ResponseEntity<BaseResponse<GroupAllResponse>> listGroup(@RequestParam(required = false) String city) {
        return AppResponse.success(groupService.listGroup(city));
    }

    @PostMapping("/add")
    @Operation(summary = "Tạo mới một chung cư mini")
    public ResponseEntity<BaseResponse<Object>> add(@Valid @RequestBody AddGroupRequest request) {
        return AppResponse.success(groupService.add(request, Operator.operator()));
    }


    @PostMapping("/update/{groupId}")
    @Operation(summary = "Cập nhập thông tin chung cư mini")
    public ResponseEntity<BaseResponse<String>> update(@RequestBody UpdateGroupRequest request,
                                                       @PathVariable Long groupId) {
        return AppResponse.success(groupService.update(groupId, request, Operator.operator()));
    }

    @DeleteMapping("/delete/{groupId}")
    @Operation(summary = "Xóa một chung cư mini")
    public ResponseEntity<BaseResponse<String>> delete(@PathVariable Long groupId){
        return AppResponse.success(groupService.delete(groupId, Operator.operator()));
    }

//    @PostMapping("/preview")
//    @Operation(summary = "Xem trước kết quả tự động tạo mới các phòng")
//    public ResponseEntity<BaseResponse<List<Rooms>>> preview(@RequestBody @NotNull AddGroupRequest request) {
//        return AppResponse.success(roomService.previewGenerateRoom(
//                request.getTotalRoomPerFloor(),
//                request.getTotalFloor(),
//                request.getRoomLimitedPeople(),
//                request.getRoomPrice(),
//                request.getRoomArea(),
//                request.getRoomNameConvention(),
//                Operator.operator())
//        );
//    }

}
