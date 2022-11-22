package vn.com.fpt.responses;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GroupAllResponse {
    private List<GroupContractedResponse> listGroupContracted;

    private List<GroupNonContractedResponse> listGroupNonContracted;

    public static GroupAllResponse of(List<GroupContractedResponse> listGroupContracted,
                                      List<GroupNonContractedResponse> listGroupNonContracted) {
        return GroupAllResponse.builder()
                .listGroupContracted(listGroupContracted)
                .listGroupNonContracted(listGroupNonContracted)
                .build();
    }
}
