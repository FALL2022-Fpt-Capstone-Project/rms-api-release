package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.fpt.model.GeneralServiceDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "payment_term không được để trống")
    private String paymentTerm;
    @NotBlank(message = "created_time không được để trống")
    @NotEmpty(message = "created_time không được để trống")
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
