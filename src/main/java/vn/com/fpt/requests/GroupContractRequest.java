package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GroupContractRequest {
    private String contractName;
    private String groupName;

    private Double contractPrice;
    private Double contractDeposit;
    private Integer contractBillCycle;
    private Integer contractPaymentCycle;

    private String contractStartDate;
    private String contractEndDate;
    private Integer contractTerm;

    private String contractNote;

    private String addressCity;
    private String addressDistrict;
    private String addressWards;
    private String addressMoreDetails;

    private Double generalPrice;
    private Double generalArea;
    private Integer generalLimitPeople;

    private List<FloorAndRoomRequest> listFloorAndRoom;
    private List<HandOverAssetsRequest> listHandOverAsset;
    private List<GeneralServiceRequest> listGeneralService;

}
