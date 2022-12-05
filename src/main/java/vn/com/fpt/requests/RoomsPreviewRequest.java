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
public class RoomsPreviewRequest {
    private Long groupId;

    @NotBlank(message = "Số phòng mỗi tầng không được để trống")
    @Max(value = 0, message = "Số phòng mỗi tầng không được lớn hơn 10")
    @Min(value = 0, message = "Số phòng mỗi tầng phải là số duơng")
    private Integer totalRoomPerFloor;
    private String roomNameConvention;
    private Integer roomLimitedPeople;
    private Double roomPrice;
    private Double roomArea;

    @NotBlank(message = "Tầng không được để trống")
    private List<Integer> listFloor;


}
