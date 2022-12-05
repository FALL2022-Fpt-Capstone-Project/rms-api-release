package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RenterRequest {
    private Long id;

    @NotBlank(message = "Tên khách thuê không được để trống")
    private String name;

    @NotBlank(message = "Giới tính khách thuê không được để tróng")
    private Boolean gender;

    @Email(message = "Email không hợp lệ")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại không hợp lệ")
    private String phoneNumber;

    @NotBlank(message = "CCCD/CMND của khách không được để trống")
    private String identityCard;

    private String licensePlates;

    private Long roomId;

    private String addressCity;

    private String addressDistrict;

    private String addressWards;

    private String addressMoreDetail;

    private String address;

    private Boolean represent;

    //build RenterRequest form RoomContractRequest
    public static RenterRequest contractOf(RoomContractRequest request){
        return RenterRequest.builder()
                .name(request.getRenterName())
                .phoneNumber(request.getRenterPhoneNumber())
                .email(request.getRenterEmail())
                .gender(request.getRenterGender())
                .identityCard(request.getRenterIdentityCard())
                .licensePlates(request.getLicensePlates())
                .addressMoreDetail(request.getAddressMoreDetail())
                // khách thuê đại diện ký
                .represent(true)
                .roomId(request.getRoomId())
                .build();
    }

}
