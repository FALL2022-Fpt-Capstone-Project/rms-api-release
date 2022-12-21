package vn.com.fpt.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GroupContractRequest {
    private Long groupId;

    private String contractName;

    @Min(value = 0, message = "Giá tiền không hợp lệ")

    private Double contractPrice;
    @Min(value = 0, message = "Giá tiền không hợp lệ")

    private Double contractDeposit;

    private Integer contractPaymentCycle;
    @NotBlank(message = "Thời gian bắt đầu lập hợp đồng không được để trống")
    private String contractStartDate;
    @NotBlank(message = "Thời gian kết thúc hợp đồng không được để trống")
    private String contractEndDate;

    private String contractNote;

    @NotBlank(message = "Tên chủ chung cư mini/nhóm căn hộ không được để trống")
    private String rackRenterName;
    @NotBlank(message = "Giới tính chủ chung cư mini/nhóm căn hộ không được để trống")
    private Boolean rackRenterGender;
    @NotBlank(message = "Số điện thoại chủ chung cư mini/nhóm căn hộ không được để trống")
    private String rackRenterPhone;
    @Email(message = "Email không hợp lệ")
    private String rackRenterEmail;
    @NotBlank(message = "Tên chủ chung cư mini/nhóm căn hộ không được để trống")
    private String rackRenterIdentity;
    private String rackRenterAddress;
    private String rackRenterNote;

    @Schema(description = "List id của phòng để lập hợp đồng", required = true, example = "[1,2,3,4,5]")
    @Min(value = 1, message = "Số phòng thuê lại ít nhất phải là là 1")
    @NotBlank(message = "Phòng thuê lại không được để trống")
    private List<Long> listRoom;
}
