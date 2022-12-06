package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.requests.GenerateBillRequest;
import vn.com.fpt.requests.RoomContractRequest;
import vn.com.fpt.responses.BillRoomStatusResponse;
import vn.com.fpt.service.bill.BillService;

import java.util.List;
import java.util.regex.Pattern;

import static vn.com.fpt.configs.AppConfigs.*;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Bill Manager", description = "Quản lý hóa đơn")
@RestController
@RequiredArgsConstructor
@RequestMapping(BillController.PATH)
public class BillController {
    public static final String PATH = V1_PATH + MANAGER_PATH + BILL_PATH;

    private final BillService billService;

    @Operation(summary = "Tạo tự động hợp đồng theo kỳ")
    @PostMapping("/room/generate")
    public ResponseEntity<BaseResponse<RoomContractRequest>> addContract(@RequestBody GenerateBillRequest request) {
//        return AppResponse.success(contractService.addContract(request, Operator.operator()));
        return null;
    }

    @Operation(summary = "Trang thái hóa đơn các phòng trong tháng và theo kỳ")
    @GetMapping("/room/not-billed")
    public ResponseEntity<BaseResponse<List<BillRoomStatusResponse>>> listNotBilled(@RequestParam Long groupContractId,
                                                                                    @RequestParam Integer billCircle){
        Pattern pattern = Pattern.compile("(0|15|30)", Pattern.CASE_INSENSITIVE);
        if (!pattern.matcher(billCircle.toString()).matches()) throw new BusinessException("Kỳ hạn thanh toán hóa đơn không hợp lệ");
        return AppResponse.success(billService.listBillRoomStatus(groupContractId, billCircle));
    }


}
