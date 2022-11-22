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
    private Long groupId;

    private String contractName;
    private Double contractPrice;
    private Double contractDeposit;
    private Integer contractBillCycle;
    private Integer contractPaymentCycle;

    private String contractStartDate;
    private String contractEndDate;

    private String contractNote;

    private List<Long> listRoom;
    private List<HandOverAssetsRequest> listHandOverAsset;

}
