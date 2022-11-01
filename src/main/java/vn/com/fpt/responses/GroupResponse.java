package vn.com.fpt.responses;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.RoomGroups;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.model.GeneralServiceDTO;
import vn.com.fpt.model.HandOverAssetsDTO;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GroupResponse {
    private Long groupId;

    private String groupName;

    private Integer totalRoom;

    private Integer totalFloor;

    private Address address;

    private List<RoomsResponse> listRooms;

    private List<GeneralServiceDTO> listGeneralService;

    private List<HandOverAssetsDTO> listHandOverAssets;

    public static GroupResponse of(RoomGroups roomGroups,
                                   Address address,
                                   List<RoomsResponse> rooms,
                                   List<GeneralServiceDTO> listGeneralService,
                                   List<HandOverAssetsDTO> listHandOverAssets,
                                   Integer totalFloor,
                                   Integer totalRoom) {
        return GroupResponse.builder()
                .groupId(roomGroups.getId())
                .groupName(roomGroups.getGroupName())
                .address(address)
                .listRooms(rooms)
                .listGeneralService(listGeneralService)
                .listHandOverAssets(listHandOverAssets)
                .totalFloor(totalFloor)
                .totalRoom(totalRoom)
                .build();
    }
}
