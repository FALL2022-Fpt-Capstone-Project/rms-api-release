package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoomContractRequest {
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

    private String licensePlates;

    private String addressMoreDetail;
    //--------------------------

    // room information
    private Long roomId;

    private Integer roomFloor;

    private Long groupId;
    //--------------------------

    private List<HandOverAssetsRequest> listHandOverAssets;

    private List<HandOverGeneralServiceRequest> listGeneralService;

    private List<RenterRequest> listRenter;


}
