package vn.com.fpt.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import vn.com.fpt.model.GeneralServiceDTO;
import vn.com.fpt.model.HandOverGeneralServiceDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NotBilledRoomResponse {
    private String roomName;
    private Integer roomFloor;

    private Integer roomLimitPeople;
    private Integer roomCurrentWaterIndex;
    private Integer roomCurrentElectricIndex;

    private Long groupId;
    private Long contractId;
    private Long groupContractId;
    private Double roomPrice;
    private Integer totalRenter;

    private Double totalMoneyRoomPrice;
    private Double totalMoneyServicePrice;

    private Integer contractPaymentCycle;
    private Boolean isInPaymentCycle;

    List<GeneralServiceDTO> listGeneralService;
}