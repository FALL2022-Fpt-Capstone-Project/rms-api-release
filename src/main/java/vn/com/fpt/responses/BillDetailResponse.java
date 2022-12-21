package vn.com.fpt.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.com.fpt.entity.Renters;
import vn.com.fpt.entity.RoomBill;
import vn.com.fpt.entity.ServiceBill;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BillDetailResponse {
    private Long roomId;
    private String roomName;

    private Double roomPrice;

    private Long groupId;
    private String groupName;

    private Long contractId;
    private Long groupContractId;

    private String billCreatedTime;
    private String paymentTerm;
    private String description;

    private Double totalServiceMoney;
    private Double totalRoomMoney;
    private Double totalMoney;

    private List<ServiceBill> serviceBill;
    private RoomBill roomBill;

    private RentersResponse renter;

}
