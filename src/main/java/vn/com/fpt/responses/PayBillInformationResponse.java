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
public class PayBillInformationResponse {
    private Long roomId;
    private String roomName;
    private Integer roomFloor;

    private Integer roomLimitPeople;

    private Long groupId;
    private Long contractId;
    private Long groupContractId;
    private Double roomPrice;
    private Integer totalRenter;

    private Integer contractPaymentCycle;
    private Boolean isInPaymentCycle;

    private String createdTime;

    List<HandOverGeneralServiceDTO> listGeneralService;
}
