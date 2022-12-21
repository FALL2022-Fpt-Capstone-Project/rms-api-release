package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateRoomRequest {
    private Long roomId;

    private String roomName;

    private Integer roomFloor;

    private Integer roomLimitPeople;

    private Integer roomCurrentWaterIndex;

    private Integer roomCurrentElectricIndex;

    private Double roomPrice;

    private Double roomArea;

    private Boolean isDisable;
}
