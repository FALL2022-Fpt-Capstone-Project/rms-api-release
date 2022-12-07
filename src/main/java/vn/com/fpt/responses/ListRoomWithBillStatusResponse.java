package vn.com.fpt.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ListRoomWithBillStatusResponse {
    private String groupName;

    private Long roomId;

    private String roomName;

    private Long contractId;

    private String representRenterName;

    private Double roomPrice;

    private Integer currentElectricIndex;

    private Integer currentWaterIndex;

    private Integer totalRenter;

    private Integer paymentCircle;

    private Boolean isAllPaid;

}
