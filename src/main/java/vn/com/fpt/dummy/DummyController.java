package vn.com.fpt.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.entity.ServiceBill;
import vn.com.fpt.model.GeneralServiceDTO;
import vn.com.fpt.model.HandOverAssetsDTO;
import vn.com.fpt.repositories.ServiceBillRepository;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.services.ServicesService;

import java.util.List;

@RestController
@RequestMapping("v1/api/manager/dummy")
@RequiredArgsConstructor
public class DummyController {
    private final ServicesService servicesService;
    private final AssetService assetService;
    private final ServiceBillRepository serviceBillRepository;

    @GetMapping("/services/{contractId}")
    public ResponseEntity<BaseResponse<List<GeneralServiceDTO>>> services(@PathVariable Long contractId) {
        return AppResponse.success(servicesService.listGeneralService(contractId));
    }

    @GetMapping("/service-bill")
    public ResponseEntity<BaseResponse<List<ServiceBill>>> abc(@RequestParam(required = false) Long service,
                                                               @RequestParam(required = false) Long room){
        return AppResponse.success(serviceBillRepository.findAllByRoomIdAndServiceId(room, service));
    }
}
