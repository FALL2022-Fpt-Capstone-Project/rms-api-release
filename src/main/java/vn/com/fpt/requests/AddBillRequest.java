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
public class AddBillRequest {
    private Long roomId;
    private Double totalRoomMoney;
    private Double totalServiceMoney;
    private String description;
    private String paymentTerm;
    private String createdTime;
    private List<ServiceBill> serviceBill;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ServiceBill {
        private Long serviceId;
        private Long serviceType;
        private Double servicePrice;
        private Integer serviceIndex;
        private Double serviceTotalMoney;
    }
}
