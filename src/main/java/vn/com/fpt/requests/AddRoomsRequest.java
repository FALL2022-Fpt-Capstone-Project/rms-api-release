package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddRoomsRequest {
    private String roomName;

    private Integer roomFloor;

    private Integer roomLimitPeople;

    private Long groupId;

    private Long contractId;

    private Long groupContractId;

    private Double roomPrice;

    private Double roomArea;

    private Boolean isOld;

}
