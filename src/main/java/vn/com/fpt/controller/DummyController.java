package vn.com.fpt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.model.GeneralServiceDTO;
import vn.com.fpt.model.HandOverAssetsDTO;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.services.ServicesService;

import java.util.List;

@RestController
@RequestMapping("v1/api/manager/dummy")
@RequiredArgsConstructor
public class DummyController {
    private final ServicesService servicesService;
    private final AssetService assetService;

    @GetMapping("/services/{contractId}")
    public ResponseEntity<BaseResponse<List<GeneralServiceDTO>>> services(@PathVariable Long contractId) {
        return AppResponse.success(servicesService.listGeneralService(contractId));
    }
}
