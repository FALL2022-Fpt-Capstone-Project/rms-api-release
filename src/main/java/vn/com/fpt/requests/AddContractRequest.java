package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddContractRequest {
    private String contractName;

    private Double contractPrice;

    private Double contractDeposit;

    private Integer contractBillCycle;

    private Integer contractPaymentCycle;

    private String contractStartDate;

    private String contractEndDate;

    private String contractNote;

    private Integer contractTerm;

    private List<String> contractImg;
    //--------------------------

    // renter information
    private Long renterOldId;

    private String renterName;

    private String renterPhoneNumber;

    private String renterEmail;

    private Boolean renterGender;

    private String renterIdentityCard;
    //--------------------------

    // room information
    private Long roomId;

    private Integer roomFloor;

    private Long groupId;
    //--------------------------

    private List<HandOverAssetsRequest> listHandOverAssets;

    private List<HandOverGeneralService> listGeneralService;

    private List<Renter> listRenter;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class HandOverAssetsRequest {
        private Long handOverAssetId;

        private Long assetsId;

        private String assetsAdditionalName;

        private Long assetsAdditionalType;

        private int handOverAssetQuantity;

        private Boolean handOverAssetStatus;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class HandOverGeneralService {
        private Long generalServiceId;

        private Integer handOverServiceIndex;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Renter {
        private String name;

        private Boolean gender;

        private String phoneNumber;

        private String identityCard;

        private String licensePlates;

        private String address;
    }
}
