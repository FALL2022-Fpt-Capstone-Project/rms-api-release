package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GeneralServiceRequest {
    @NotBlank(message = "Chung cư mini/nhóm căn hộ không được để trống")
    private Long groupId;

    @NotBlank(message = "Dịch vụ chung không được để trống")
    private Long serviceId;

    @NotBlank(message = "Giá tiền dịch vụ chung không được để trống")
    private Double generalServicePrice;

    @NotBlank(message = "Cách tính dịch vụ tiền dịch vụ chung không được để trống")
    private Long generalServiceType;

    private String note;

    @Schema(hidden = true)
    private Long contractId;
}
