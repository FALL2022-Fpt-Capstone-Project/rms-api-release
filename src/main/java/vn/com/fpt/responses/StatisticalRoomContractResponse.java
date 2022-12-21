package vn.com.fpt.responses;


import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StatisticalRoomContractResponse {
    private Long duration;

    private Integer expiredContract;

    private Integer almostExpiredContract;

    private Integer latestContract;

    private Integer totalContract;

    public static StatisticalRoomContractResponse of(Long duration,
                                                     Integer almostExpiredContract,
                                                     Integer latestContract,
                                                     Integer expiredContract,
                                                     Integer totalContract) {
        return StatisticalRoomContractResponse
                .builder()
                .duration(duration)
                .almostExpiredContract(almostExpiredContract)
                .expiredContract(expiredContract)
                .latestContract(latestContract)
                .totalContract(totalContract)
                .build();
    }
}
