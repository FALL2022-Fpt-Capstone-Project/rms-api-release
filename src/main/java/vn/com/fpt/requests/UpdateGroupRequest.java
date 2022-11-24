package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateGroupRequest {
    private String groupName;

    private Integer roomLimitedPeople;

    private Double roomPrice;

    private Double roomArea;

    private String addressCity;

    private String addressDistrict;

    private String addressWard;

    private String addressMoreDetail;

    private String description;

}
