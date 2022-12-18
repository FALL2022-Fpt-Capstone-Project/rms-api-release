package vn.com.fpt.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MoneyOutResponse {
    private Long id;
    private Long groupId;
    private String groupName;
    private String time;

    private Double roomGroupMoney;
    private Double serviceMoney;
    private Double otherMoney;
    private Double totalMoney;
}
