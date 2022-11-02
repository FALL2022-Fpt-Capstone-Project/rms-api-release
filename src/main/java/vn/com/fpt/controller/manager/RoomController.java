package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.responses.RoomsResponse;

import static vn.com.fpt.configs.AppConfigs.*;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Room Manager", description = "Quản lý phòng ")
@RestController
@RequiredArgsConstructor
@RequestMapping(RoomController.PATH)
public class RoomController {
    public static final String PATH = V1_PATH + MANAGER_PATH + ROOM_PATH;

    @GetMapping("")
    public ResponseEntity<BaseResponse<RoomsResponse>> room(){
        // TODO
        return null;
    }
}
