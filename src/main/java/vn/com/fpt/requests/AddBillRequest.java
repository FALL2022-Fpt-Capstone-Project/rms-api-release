package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.fpt.model.GeneralServiceDTO;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AddBillRequest {
    @NotBlank(message = "room_id không được để trống")
    private Long roomId;
    private Double totalRoomMoney;
    private Double totalServiceMoney;
    private String description;
    @NotBlank(message = "payment_term không được để trống")
    private String paymentTerm;
    @NotBlank(message = "created_time không được để trống")
    private String createdTime;
    @NotBlank(message = "service_bill không được để trống")
    private List<ServiceBill> serviceBill;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ServiceBill {
        @NotBlank(message = "service_id không được để trống")
        private Long serviceId;
        @NotBlank(message = "service_type không được để trống")
        private Long serviceType;
        @NotBlank(message = "service_price không được để trống")
        private Double servicePrice;
        @NotBlank(message = "service_index không được để trống")
        private Integer serviceIndex;
        @NotBlank(message = "service_total_money không được để trống")
        private Double serviceTotalMoney;
    }
}
