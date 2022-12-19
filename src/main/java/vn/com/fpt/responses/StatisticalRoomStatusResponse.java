package vn.com.fpt.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StatisticalRoomStatusResponse {
    private Long groupId;
    private String groupName;
    private int totalRoom;
    private int totalEmptyRoom;
    private int totalRentedRoom;
}
