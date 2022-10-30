package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static vn.com.fpt.configs.AppConfigs.V1_PATH;
import static vn.com.fpt.configs.AppConfigs.MANAGER_PATH;
import static vn.com.fpt.configs.AppConfigs.SERVICE_PATH;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Service Manager", description = "Quản lý dịch vụ chung")
@RestController
@RequiredArgsConstructor
@RequestMapping(ServiceController.PATH)
public class ServiceController {
    public static final String PATH = V1_PATH + MANAGER_PATH + SERVICE_PATH;


}
