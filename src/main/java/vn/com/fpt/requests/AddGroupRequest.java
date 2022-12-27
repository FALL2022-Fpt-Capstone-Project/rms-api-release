package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddGroupRequest {
    @NotBlank(message = "Tên chung cư mini/ nhóm căn hộ không được để trống")
    private String groupName;

    @Min(value = 1, message = "Số tầng phải là số dương")
    @Max(value = 10, message = "Số tầng không được lớn hơn 10")
    private Integer totalFloor;

    @Max(value = 10, message = "Số phòng mỗi tầng không được lớn hơn 10")
    @Min(value = 1, message = "Số phòng mỗi tầng phải là số duơng")
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
