package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static vn.com.fpt.configs.AppConfigs.*;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Asset Manager", description = "Quản lý hóa đớn")
@RestController
@RequiredArgsConstructor
@RequestMapping(BillController.PATH)
public class BillController {
    public static final String PATH = V1_PATH + MANAGER_PATH + BILL_PATH;
}
