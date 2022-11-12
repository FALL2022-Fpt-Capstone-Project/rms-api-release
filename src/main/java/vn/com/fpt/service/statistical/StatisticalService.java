package vn.com.fpt.service.statistical;

import vn.com.fpt.responses.StatisticalRoomContractResponse;

public interface StatisticalService {

    StatisticalRoomContractResponse statisticalRoomContract(Long groupId, Long duration);
}
