package vn.com.fpt.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.com.fpt.entity.Rooms;

import javax.persistence.Column;


@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoomsResponse {
    private Long roomId;

    private String roomName;

    private Integer roomFloor;

    private Integer roomLimitPeople;

    private Integer roomCurrentWaterIndex;

    private Integer roomCurrentElectricIndex;

    private Long groupId;

    private Long contractId;

    private Long groupContractId;

    private Double roomPrice;

    private Double roomArea;

    public static RoomsResponse of(Rooms rooms) {
        return RoomsResponse.builder()
                .roomId(rooms.getId())
                .roomName(rooms.getRoomName())
                .roomFloor(rooms.getRoomFloor())
                .roomLimitPeople(rooms.getRoomLimitPeople())
                .roomCurrentWaterIndex(rooms.getRoomCurrentWaterIndex())
                .roomCurrentElectricIndex(rooms.getRoomCurrentElectricIndex())
                .groupId(rooms.getGroupId())
                .roomPrice(rooms.getRoomPrice())
                .roomArea(rooms.getRoomArea())
                .contractId(rooms.getContractId())
                .groupContractId(rooms.getGroupContractId())
                .build();
    }
}
