package vn.com.fpt.service.statistical;

import vn.com.fpt.responses.StatisticalBillResponse;
import vn.com.fpt.responses.StatisticalBillStatusResponse;
import vn.com.fpt.responses.StatisticalRoomContractResponse;
import vn.com.fpt.responses.StatisticalTotalNeedToPaid;

public interface StatisticalService {

    StatisticalRoomContractResponse statisticalRoomContract(Long groupId, Long duration);

    StatisticalBillResponse billStatus(Long groupId, Integer paymentCircle, String time);

    Integer totalRoomNotBilled(Long groupId, Integer paymentCircle);

    StatisticalBillStatusResponse totalMoneyBillStatus(String time, Long groupId, Integer paymentCircle);
}
