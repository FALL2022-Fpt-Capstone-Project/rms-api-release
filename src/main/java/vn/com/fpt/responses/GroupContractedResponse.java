package vn.com.fpt.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.model.GeneralServiceDTO;
import vn.com.fpt.model.HandOverAssetsDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GroupContractedResponse {
    private Long groupId;

    private String groupName;

    private Integer totalRoom;

    private Integer totalFloor;

    private String description;

    private Address address;

    private List<RoomsResponse> listRooms;

    private List<RoomLeaseContracted> listRoomLeaseContracted;

    private List<RoomNonLeaseContracted> listRoomNonLeaseContracted;

    private List<GeneralServiceDTO> listGeneralService;

    private Boolean groupContracted;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RoomLeaseContracted {
        private Long groupId;

        private Long groupContractId;

        private List<Rooms> listRoom;

        private List<HandOverAssetsDTO> listHandOverAssets;

        private Integer totalRoomLeaseContracted;

        private Integer totalFloorLeaseContracted;

        public static RoomLeaseContracted of(Long groupContractId,
                                             Long groupId,
                                             List<Rooms> listRoomsLeaseContracted,
                                             List<HandOverAssetsDTO> listHandOverAssets,
                                             Integer totalRoomLeaseContracted,
                                             Integer totalFloorLeaseContracted) {
            return RoomLeaseContracted.builder()
                    .groupContractId(groupContractId)
                    .groupId(groupId)
                    .listRoom(listRoomsLeaseContracted)
                    .listHandOverAssets(listHandOverAssets)
                    .totalRoomLeaseContracted(totalRoomLeaseContracted)
                    .totalFloorLeaseContracted(totalFloorLeaseContracted)
                    .build();
        }

    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RoomNonLeaseContracted {

        private Long groupId;

        private List<Rooms> listRoom;

        private Integer totalRoomNonLeaseContracted;

        private Integer totalFloorNonLeaseContracted;

        public static RoomNonLeaseContracted of(Long groupId,
                                                List<Rooms> listRoomsLeaseContracted,
                                                Integer totalRoomNonLeaseContracted,
                                                Integer totalFloor) {
            return RoomNonLeaseContracted.builder()
                    .groupId(groupId)
                    .listRoom(listRoomsLeaseContracted)
                    .totalRoomNonLeaseContracted(totalRoomNonLeaseContracted)
                    .totalFloorNonLeaseContracted(totalFloor)
                    .build();
        }
    }

}
