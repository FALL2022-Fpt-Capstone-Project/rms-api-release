package vn.com.fpt.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdjustRoomPriceResponse {

    private String groupContractName;

    private Long groupContractId;
    private Double number;
    private Boolean increase;

    private List<Long> listRoomAdjust;
    private String listRoomNameAdjust;


}
