package vn.com.fpt.controller.manager;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.response.AppResponse;
import vn.com.fpt.common.response.BaseResponse;
import vn.com.fpt.responses.*;
import vn.com.fpt.responses.StatisticalChartRevenueResponse;
import vn.com.fpt.service.statistical.StatisticalService;

import java.util.List;
import java.util.regex.Pattern;

import static vn.com.fpt.common.utils.DateUtils.now;
import static vn.com.fpt.common.utils.DateUtils.toLocalDate;
import static vn.com.fpt.configs.AppConfigs.*;

@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Statistical", description = "Hiện thị các số liệu thống kê")
@RestController
@RequiredArgsConstructor
@RequestMapping(StatisticalController.PATH)
public class StatisticalController {
    public static final String PATH = V1_PATH + MANAGER_PATH + STATISTICAL_PATH;

    private final StatisticalService statisticalService;

    @GetMapping("/contract/room")
    @Operation(summary = "Hiện thị số liệu thống kê hợp đồng của phòng")
    public ResponseEntity<BaseResponse<StatisticalRoomContractResponse>> roomContract(@RequestParam(required = false, defaultValue = "1") Long duration,
                                                                                      @RequestParam(required = false) Long groupId) {
        return AppResponse.success(statisticalService.statisticalRoomContract(groupId, duration));
    }

    @GetMapping("/room/status")
    @Operation(summary = "Hiển thị số liện thống kê tình trạng của phòng")
    public ResponseEntity<BaseResponse<StatisticalRoomStatusResponse>> roomStatus(@RequestParam(required = false) Long groupId) {
        return AppResponse.success(statisticalService.statisticalRoomStatus(groupId));
    }

    @GetMapping("/bill/total-money")
    @Operation(summary = "Hiện thị tổng số tiền của các trạng thái hóa đơn")
    public ResponseEntity<BaseResponse<StatisticalBillResponse>> billStatus(@RequestParam(required = false) String time,
                                                                            @RequestParam(required = false) Long groupId,
                                                                            @RequestParam(required = false) Integer paymentCircle) {
        Pattern pattern = Pattern.compile("(0|15|30)", Pattern.CASE_INSENSITIVE);
        if (!pattern.matcher(paymentCircle.toString()).matches())
            throw new BusinessException("Kỳ hạn thanh toán hóa đơn không hợp lệ");
        return AppResponse.success(statisticalService.billStatus(groupId, paymentCircle, time));
    }

    @GetMapping("/bill/status")
    @Operation(summary = "Hiện thị tiền của các trạng hóa đơn trong tháng")
    public ResponseEntity<BaseResponse<StatisticalBillStatusResponse>> totalMoneyBillStatus(@RequestParam(required = false) String time,
                                                                                            @RequestParam(required = false) Long groupId,
                                                                                            @RequestParam(required = false) Integer paymentCircle) {
        Pattern pattern = Pattern.compile("(0|15|30)", Pattern.CASE_INSENSITIVE);
        if (!pattern.matcher(paymentCircle.toString()).matches())
            throw new BusinessException("Kỳ hạn thanh toán hóa đơn không hợp lệ");
        return AppResponse.success(statisticalService.totalMoneyBillStatus(time, groupId, paymentCircle));
    }

    @GetMapping("/bill/room-not-billed")
    @Operation(summary = "Hiển thị tổng số phòng đến kỳ mà chưa lập hóa đơn trong tháng")
    public ResponseEntity<BaseResponse<Integer>> totalRoomNotBilled(@RequestParam(required = false) Long groupId,
                                                                    @RequestParam(required = false) Integer paymentCircle) {
        Pattern pattern = Pattern.compile("(0|15|30)", Pattern.CASE_INSENSITIVE);
        if (!pattern.matcher(paymentCircle.toString()).matches())
            throw new BusinessException("Kỳ hạn thanh toán hóa đơn không hợp lệ");
        return AppResponse.success(statisticalService.totalRoomNotBilled(groupId, paymentCircle));
    }

    @GetMapping("/bill/list-room-billed")
    @Operation(summary = "Hiển thị tổng số phòng đã có hóa đơn")
    public ResponseEntity<BaseResponse<List<ListBilledRoomResponse>>> listBilledRoom(@RequestParam(required = false) Long groupId,
                                                                                     @RequestParam(required = false) String createdTime) {
        return AppResponse.success(statisticalService.listBilledRoom(groupId, createdTime));
    }

    @GetMapping("/chart/revenue")
    @Operation(summary = "Dữ liệu cho biểu đồ thống kê doanh thu theo tháng")
    public ResponseEntity<BaseResponse<List<StatisticalChartRevenueResponse>>> chartRevenue(@RequestParam(required = false) Integer year) {
        if (year == null) {
            year = toLocalDate(now()).getYear();
        }
        return AppResponse.success(statisticalService.chartRevenue(year));
    }

    @GetMapping("/chart/room-contract")
    @Operation(summary = "Dữ liệu cho biểu đồ thống kê hợp đồng phòng theo tháng")
    public ResponseEntity<BaseResponse<StatisticalChartContractResponse>> chartRoomContract(@RequestParam(required = false) Integer year) {
        if (year == null) {
            year = toLocalDate(now()).getYear();
        }
        return AppResponse.success(statisticalService.chartContract(year));
    }
}
