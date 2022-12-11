package vn.com.fpt.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import vn.com.fpt.model.GeneralServiceDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BillRoomStatusResponse {
    private Long roomId;
    private String roomName;
    private Integer roomFloor;

    private Integer roomLimitPeople;
    private Integer roomCurrentWaterIndex;
    private Integer roomCurrentElectricIndex;
    private Integer roomOldWaterIndex;
    private Integer roomOldElectricIndex;

    private Long groupId;
    private Long contractId;
    private Long groupContractId;
    private Double roomPrice;
    private Integer totalRenter;

    private Double totalMoneyRoomPrice;
    private Double totalMoneyServicePrice;
    private Double totalMoney;

    private Integer contractPaymentCycle;
    private Boolean isInBillCycle;
    private Integer billCycle;
    private Boolean isBilled;

    List<GeneralServiceDTO> listGeneralService;
}
