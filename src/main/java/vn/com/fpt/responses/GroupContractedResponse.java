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

    private String rackRenterName;

    private Boolean rackRenterGender;

    private String rackRenterPhone;

    private String rackRenterIdentity;

    private String groupName;

    private String rackRenterEmail;

    private String rackRenterNote;

    private Integer totalRoom;

    private Integer totalFloor;

    private String description;

    private Address address;

    private List<RoomsResponse> listRooms;

    private List<RoomLeaseContracted> listRoomLeaseContracted;

    private List<RoomNonLeaseContracted> listRoomNonLeaseContracted;

    private List<GeneralServiceDTO> listGeneralService;

    private Boolean groupContracted;

    private Integer totalRoomLeaseContracted;

    private Integer totalFloorLeaseContracted;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RoomLeaseContracted {
        private Long groupId;

        private Long groupContractId;

        private String groupName;

        private String rackRenterName;

        private Boolean rackRenterGender;

        private String rackRenterPhone;

        private String rackRenterIdentity;

        private Double contractPrice;

        private Double contractDeposit;

        private List<Rooms> listRoom;

        private List<GeneralServiceDTO> listGeneralService;

        private List<HandOverAssetsDTO> listHandOverAssets;

        private Integer totalRoomLeaseContracted;

        private Integer totalFloorLeaseContracted;

        public static RoomLeaseContracted of(Long groupContractId,
                                             Long groupId,
                                             String groupName,
                                             String rackRenterName,
                                             Boolean rackRenterGender,
                                             String rackRenterPhone,
                                             String rackRenterIdentity,
                                             Double contractPrice,
                                             Double contractDeposit,
                                             List<Rooms> listRoomsLeaseContracted,
                                             List<GeneralServiceDTO> listGeneralService,
                                             List<HandOverAssetsDTO> listHandOverAssets,
                                             Integer totalRoomLeaseContracted,
                                             Integer totalFloorLeaseContracted) {
            return RoomLeaseContracted.builder()
                    .groupContractId(groupContractId)
                    .groupId(groupId)
                    .groupName(groupName)
                    .rackRenterName(rackRenterName)
                    .rackRenterGender(rackRenterGender)
                    .rackRenterPhone(rackRenterPhone)
                    .rackRenterIdentity(rackRenterIdentity)
                    .contractPrice(contractPrice)
                    .contractDeposit(contractDeposit)
                    .listRoom(listRoomsLeaseContracted)
                    .listGeneralService(listGeneralService)
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

        private String groupName;

        private List<Rooms> listRoom;

        private List<GeneralServiceDTO> listGeneralService;

        private Integer totalRoomNonLeaseContracted;

        private Integer totalFloorNonLeaseContracted;

        public static RoomNonLeaseContracted of(Long groupId,
                                                String groupName,
                                                List<Rooms> listRoomsLeaseContracted,
                                                List<GeneralServiceDTO> listGeneralService,
                                                Integer totalRoomNonLeaseContracted,
                                                Integer totalFloor) {
            return RoomNonLeaseContracted.builder()
                    .groupId(groupId)
                    .groupName(groupName)
                    .listRoom(listRoomsLeaseContracted)
                    .listGeneralService(listGeneralService)
                    .totalRoomNonLeaseContracted(totalRoomNonLeaseContracted)
                    .totalFloorNonLeaseContracted(totalFloor)
                    .build();
        }
    }

}
