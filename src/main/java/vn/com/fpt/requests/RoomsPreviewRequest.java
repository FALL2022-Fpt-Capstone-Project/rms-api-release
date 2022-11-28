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
public class RoomsPreviewRequest {
    private Long groupId;

    private Integer totalRoomPerFloor;
    private String roomNameConvention;
    private Integer roomLimitedPeople;
    private Double roomPrice;
    private Double roomArea;

    private List<Integer> listFloor;



}
