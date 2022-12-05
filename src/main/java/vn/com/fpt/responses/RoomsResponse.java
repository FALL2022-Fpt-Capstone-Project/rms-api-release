package vn.com.fpt.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.com.fpt.entity.RoomAssets;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.rooms.RoomService;

import javax.annotation.PostConstruct;
import javax.persistence.Column;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Component
public class RoomsResponse {

//    private static AssetService roomService;
//
////    @Autowired
////    private AssetService autowireComponent;
////
////    @PostConstruct
////    private void init(){
////        roomService = this.autowireComponent;
////    }

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

    private Boolean isDisable;

    private List<RoomAssets> roomAssetsList;

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
                .isDisable(rooms.getIsDisable())
//                .roomAssetsList(roomService.listRoomAsset(rooms.getId()))
                .build();
    }
}
