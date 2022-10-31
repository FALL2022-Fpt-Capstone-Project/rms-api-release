package vn.com.fpt.controller.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.configs.AppConfigs;
import vn.com.fpt.requests.LoginRequest;
import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.responses.AccountResponse;
import vn.com.fpt.service.authentication.AuthenticationService;
import vn.com.fpt.common.utils.Operator;

@Tag(name = "Đăng nhập vào hệ thống", description = "Đăng nhập vào hệ thống")
@RestController
@RequiredArgsConstructor
@RequestMapping(AuthenticationController.PATH)
public class AuthenticationController {
    public static final String PATH = AppConfigs.V1_PATH + AppConfigs.AUTHEN_PATH;

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AccountResponse>> login(@RequestBody LoginRequest request) {
        return AppResponse.success(authenticationService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<AccountResponse>> add(@RequestBody RegisterRequest request) {
        return AppResponse.success(authenticationService.register(request, 1L));
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponse<String>> logout() {
        return AppResponse.success(authenticationService.logout());
    }

}
