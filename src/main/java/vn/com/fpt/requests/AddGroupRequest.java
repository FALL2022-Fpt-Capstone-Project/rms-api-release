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
public class AddGroupRequest {
    private String groupName;

    private Integer totalFloor;
    private Integer totalRoomPerFloor;
    private String roomNameConvention;
    private Integer roomLimitedPeople;
    private Double roomPrice;
    private Double roomArea;
    private List<RoomAssetsRequest> listAsset;
    private List<RoomAssetsRequest> listAdditionalAsset;

    private String addressCity;

    private String addressDistrict;

    private String addressWard;

    private String addressMoreDetail;

    private String description;

    private List<GeneralServiceRequest> listGeneralService;


}
