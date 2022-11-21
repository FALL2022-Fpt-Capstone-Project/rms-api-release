package vn.com.fpt.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.com.fpt.entity.Address;
import vn.com.fpt.model.GeneralServiceDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GroupNonContractedResponse {
    private Long groupId;

    private String groupName;

    private Integer totalRoom;

    private Integer totalFloor;

    private String description;

    private Address address;

    private List<RoomsResponse> listRooms;

    private List<GeneralServiceDTO> listGeneralService;

}
