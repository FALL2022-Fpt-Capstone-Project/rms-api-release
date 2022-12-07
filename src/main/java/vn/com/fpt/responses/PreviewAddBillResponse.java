package vn.com.fpt.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.com.fpt.requests.AddBillRequest;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PreviewAddBillResponse {
    private Integer key;
    private Long roomId;

    private Double totalRoomMoney;
    private Double totalServiceMoney;
    private String description;
    private String paymentTerm;
    private String createdTime;
    private List<AddBillRequest.ServiceBill> serviceBill;

}
