package vn.com.fpt.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.com.fpt.entity.Rooms;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoomsPreviewResponse {
    private String roomId;

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

    private Boolean isDisable;

    private Boolean isOld;

    private Boolean isDuplicate;

    public static RoomsPreviewResponse of(RoomsResponse room) {
        return RoomsPreviewResponse.builder()
                .roomName(room.getRoomName())
                .roomFloor(room.getRoomFloor())
                .roomLimitPeople(room.getRoomLimitPeople())
                .roomCurrentWaterIndex(room.getRoomCurrentWaterIndex())
                .roomCurrentElectricIndex(room.getRoomCurrentElectricIndex())
                .groupId(room.getGroupId())
                .contractId(room.getContractId())
                .groupContractId(room.getGroupContractId())
                .roomPrice(room.getRoomPrice())
                .roomArea(room.getRoomArea())
                .isDisable(room.getIsDisable())
                .build();
    }

    public static RoomsPreviewResponse of(Rooms room) {
        return RoomsPreviewResponse.builder()
                .roomName(room.getRoomName())
                .roomFloor(room.getRoomFloor())
                .roomLimitPeople(room.getRoomLimitPeople())
                .roomCurrentWaterIndex(room.getRoomCurrentWaterIndex())
                .roomCurrentElectricIndex(room.getRoomCurrentElectricIndex())
                .groupId(room.getGroupId())
                .contractId(room.getContractId())
                .groupContractId(room.getGroupContractId())
                .roomPrice(room.getRoomPrice())
                .roomArea(room.getRoomArea())
                .isDisable(room.getIsDisable())
                .build();
    }

    public static RoomsPreviewResponse old(RoomsResponse room) {
        var old = of(room);
        old.setIsOld(true);
        old.setIsDuplicate(false);
        return old;
    }

    public static RoomsPreviewResponse old(Rooms room) {
        var old = of(room);
        old.setIsOld(true);
        old.setIsDuplicate(false);
        return old;
    }

    public static RoomsPreviewResponse gen(RoomsResponse room, Boolean isDuplicate) {
        var gen = of(room);
        gen.setIsOld(false);
        gen.setIsDuplicate(isDuplicate);
        gen.setIsDisable(false);
        return gen;
    }
}
