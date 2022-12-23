package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.entity.RecurringBill;
import vn.com.fpt.requests.AddBillRequest;
import vn.com.fpt.requests.AddMoneySourceRequest;
import vn.com.fpt.requests.PreviewAddBillRequest;
import vn.com.fpt.responses.*;
import vn.com.fpt.service.bill.BillService;

import javax.validation.Valid;
import java.util.List;
import java.util.regex.Pattern;

import static vn.com.fpt.configs.AppConfigs.*;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Bill Manager", description = "Quản lý hóa đơn")
@RestController
@RequiredArgsConstructor
@RequestMapping(BillController.PATH)
@Validated
public class BillController {
    public static final String PATH = V1_PATH + MANAGER_PATH + BILL_PATH;

    private final BillService billService;

    @Operation(summary = "Trạng thái hóa đơn các phòng trong tháng và theo kỳ")
    @GetMapping("/room/bill-status")
    public ResponseEntity<BaseResponse<List<BillRoomStatusResponse>>> listNotBilled(@RequestParam Integer paymentCycle,
                                                                                    @RequestParam Long groupId) {
        if (paymentCycle != null) {
            Pattern pattern = Pattern.compile("(0|15|30)", Pattern.CASE_INSENSITIVE);
            if (!pattern.matcher(paymentCycle.toString()).matches())
                throw new BusinessException("Kỳ hạn thanh toán hóa đơn không hợp lệ");
        }
        return AppResponse.success(billService.listBillRoomStatus(groupId, paymentCycle));
    }

    @Operation(summary = "Chi tiết tính toán hóa đơn của phòng")
    @GetMapping("/room/information/{roomId}")
    public ResponseEntity<BaseResponse<PayBillInformationResponse>> payBillInformation(@PathVariable Long roomId) {
        return AppResponse.success(billService.payBillInformation(roomId));
    }

    @Operation(summary = "Danh trang thái hóa đơn của các phòng theo tòa")
    @GetMapping("/room/list")
    public ResponseEntity<BaseResponse<List<ListRoomWithBillStatusResponse>>> listRoomWithBill(@RequestParam(required = false) Long groupId,
                                                                                               @RequestParam(required = false, defaultValue = "0") Integer paymentCycle) {
        if (paymentCycle != null) {
            Pattern pattern = Pattern.compile("(0|15|30)", Pattern.CASE_INSENSITIVE);
            if (!pattern.matcher(paymentCycle.toString()).matches())
                throw new BusinessException("Kỳ hạn thanh toán hóa đơn không hợp lệ");
        }
        return AppResponse.success(billService.listRoomWithBillStatus(groupId, paymentCycle));
    }

    @PostMapping("/room/create/preview")
    @Operation(summary = "Xem trước list hóa đơn tạo cho nhiều phòng")
    public ResponseEntity<BaseResponse<List<PreviewAddBillResponse>>> preview(@RequestBody List<PreviewAddBillRequest> requests) {
        return AppResponse.success(billService.addBillPreview(requests));
    }

    @Operation(summary = "Tạo một hoặc nhiều hóa đơn cho phòng")
    @PostMapping("/room/create")
    public ResponseEntity<BaseResponse<List<AddBillRequest>>> createBill(@Valid @RequestBody List<AddBillRequest> requests) {
        return AppResponse.success(billService.addBill(requests));
    }

    @Operation(summary = "Xem lịch sử hóa đơn của phòng")
    @GetMapping("/room/history/{roomId}")
    public ResponseEntity<BaseResponse<List<RecurringBill>>> roomBillHistory(@PathVariable Long roomId,
                                                                             @RequestParam(required = false) String time) {
        return AppResponse.success(billService.roomBillHistory(roomId, time));
    }

    @Operation(summary = "Chi trả một hoặc nhiều hóa đơn định kỳ ")
    @PutMapping("/room/pay/{billId}")
    public ResponseEntity<BaseResponse<String>> payBill(@PathVariable Long billId) {
        billService.payRoomBill(List.of(billId));
        return AppResponse.success("Chi trả thành công");
    }

    @Operation(summary = "Xóa một hoặc nhiều hóa đơn định định kỳ")
    @DeleteMapping("/room/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> deleteBill(@RequestParam List<Long> billId) {
        billService.deleteRoomBill(billId);
        return AppResponse.success("Xóa hóa đơn thành công");
    }

    @Operation(summary = "Kiểm tra nhóm phòng đấy đã thanh toán hết hóa đơn chưa")
    @GetMapping("/group/bill-check")
    public ResponseEntity<BaseResponse<Boolean>> groupBillCheck(@RequestParam Long groupContractId) {
        return AppResponse.success(billService.groupBillCheck(groupContractId));
    }

    @Operation(summary = "Kiểm tra  phòng đấy đã thanh toán hết hóa đơn chưa")
    @GetMapping("/room/bill-check")
    public ResponseEntity<BaseResponse<Boolean>> roomBillCheck(@RequestParam Long contractId) {
        return AppResponse.success(billService.roomBillCheck(contractId));
    }

    @Operation(summary = "Hiển thị chi tết hóa đơn ")
    @GetMapping("/room/detail/{recurringBillId}")
    public ResponseEntity<BaseResponse<BillDetailResponse>> recurringBill(@PathVariable Long recurringBillId) {
        return AppResponse.success(billService.billDetail(recurringBillId));
    }

    @Operation(summary = "Hiển thị chi tiết lịch sử hóa đơn của các phòng")
    @GetMapping("/room/histories")
    public ResponseEntity<BaseResponse<List<RecurringBill>>> listRoomBillHistory(@RequestParam(required = false) Long groupId) {
        return AppResponse.success(billService.listRoomBillHistory(groupId));
    }

    @Operation(summary = "Hiển thị tất cả số tiền chi trong tòa")
    @GetMapping("/money-source/out")
    public ResponseEntity<BaseResponse<List<MoneyOutResponse>>> listMoneyOut(@RequestParam(required = false) List<Long> groupId,
                                                                             @RequestParam(required = false) String time) {
        return AppResponse.success(billService.listMoneySourceOut(groupId, time));
    }

    @Operation(summary = "Thêm tiền chi trong tháng cho nhóm phòng")
    @PostMapping("/money-source/out/add")
    public ResponseEntity<BaseResponse<AddMoneySourceRequest>> addMoneyOut(@RequestBody AddMoneySourceRequest request) {
        return AppResponse.success(billService.addMoneyOut(request));
    }

    @Operation(summary = "Xóa tiền chi")
    @DeleteMapping("/money-source/out/delete/{id}")
    public ResponseEntity<BaseResponse<String>> deleteMoneyOut(@PathVariable Long id) {
        billService.deleteMoneyOut(id);
        return AppResponse.success("Xóa thành công!!");
    }
}
