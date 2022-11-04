package vn.com.fpt.controller.manager;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.responses.GroupResponse;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.group.GroupServiceImpl;

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

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin của nhóm phòng theo id")
    public ResponseEntity<BaseResponse<GroupResponse>> group(@PathVariable Long id) {
        return AppResponse.success(groupService.group(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Lấy thông tin của tất cả group")
    public ResponseEntity<BaseResponse<List<GroupResponse>>> list() {
        return AppResponse.success(groupService.list());
    }
}
