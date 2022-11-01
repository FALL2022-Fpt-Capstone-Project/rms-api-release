package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterRequest {

    @NotBlank(message = "Thiếu thông tin tên tài khoản")
    private String userName;

    private String password;

    @NotBlank(message = "Thiếu thông tin quyền")
    private String roles;

    private String fullName;

    private String phoneNumber;

    private Boolean gender;

    private String addressCity;

    private String addressDistrict;

    private String addressWards;

    private String addressMoreDetail;

    private Boolean deactivate;

}
