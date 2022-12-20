package vn.com.fpt.service.statistical;

import vn.com.fpt.responses.*;

import java.util.List;

public interface StatisticalService {

    StatisticalRoomContractResponse statisticalRoomContract(Long groupId, Long duration);

    StatisticalBillResponse billStatus(Long groupId, Integer paymentCircle, String time);

    Integer totalRoomNotBilled(Long groupId, Integer paymentCircle);

    StatisticalBillStatusResponse totalMoneyBillStatus(String time, Long groupId, Integer paymentCircle);

    List<ListBilledRoomResponse> listBilledRoom(Long groupId, String createdTime);

    StatisticalRoomStatusResponse statisticalRoomStatus(Long groupId);

    List<StatisticalChartRevenueResponse> chartRevenue(Integer year);

    StatisticalChartContractResponse chartContract(Integer year);
}
