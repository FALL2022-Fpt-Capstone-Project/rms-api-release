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
public class PreviewGenerateBillResponse {
    private Long roomId;

    private Long contractId;

    private Long groupId;

    private Long groupContractId;

    private Double roomMoney;

    private Double totalMoney;

    private String paymentTerm;

}
