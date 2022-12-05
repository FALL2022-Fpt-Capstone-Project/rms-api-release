package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoomContractRequest {
    private String contractName;

    @Min(value = 0, message = "Giá tiền không hợp lệ")
    @NotBlank(message = "Giá trị hợp đồng không được để trống")
    private Double contractPrice;

    @Min(value = 0, message = "Giá tiền không hợp lệ")
    @NotBlank(message = "Tiền cọc không được để trống")
    private Double contractDeposit;

    @Min(value = 0, message = "Chu kỳ hóa đơn không hợp lệ")
    @NotBlank(message = "Chu kỳ hóa đơn không được để trống")
    private Integer contractBillCycle;

    @Min(value = 0, message = "Chu kỳ thanh toán không hợp lệ")
    @NotBlank(message = "Chu kỳ thanh toán tiền phòng không được để trống")
    private Integer contractPaymentCycle;

    @NotBlank(message = "Thời gian bắt đầu lập hợp đồng không được để trống")
    private String contractStartDate;

    @NotBlank(message = "Thời gian kết thúc hợp đồng không được để trống")
    private String contractEndDate;

    private String contractNote;

    private Integer contractTerm;

    //--------------------------

    // renter information
    private Long renterOldId;

    @NotBlank(message = "Tên khách ký hợp đồng không được để trống")
    private String renterName;

    private String renterPhoneNumber;

    @Email(message = "Email không hợp lệ")
    private String renterEmail;

    @NotBlank(message = "Giới tính của khách không được để trống")
    private Boolean renterGender;

    @NotBlank(message = "CCCD/CMND của khách không được để trống")
    private String renterIdentityCard;

    private String licensePlates;

    private String addressMoreDetail;
    //--------------------------

    // room information
    @NotBlank(message = "Phòng không được để trống")
    private Long roomId;

    @NotBlank(message = "Taầng không được để trống")
    private Integer roomFloor;

    private Long groupId;
    //--------------------------

    private List<HandOverGeneralServiceRequest> listGeneralService;

    private List<RenterRequest> listRenter;


}
