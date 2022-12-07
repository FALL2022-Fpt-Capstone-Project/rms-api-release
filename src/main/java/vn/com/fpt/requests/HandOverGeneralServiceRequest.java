package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class HandOverGeneralServiceRequest {
    private Long handOverGeneralServiceId;

    private Long generalServiceId;

    private Integer handOverGeneralServiceIndex;

    private Long serviceId;

}
