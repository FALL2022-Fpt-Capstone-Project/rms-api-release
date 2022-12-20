package vn.com.fpt.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StatisticalChartContractResponse {
    private int totalAllCreated;
    private int totalAllEnded;
    private List<StatisticalContract> listByMonth;

    @Getter
    @Setter
    @AllArgsConstructor
    @RequiredArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class StatisticalContract {
        private int year;
        private int month;
        private int totalCreated;
        private int totalEnded;
    }
}
