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
public class AdjustRoomPriceRequest {
    @Schema(description = "Tăng hoặc giảm giá phòng", example = "100000")
    private Double number;

    @Schema(description = "Tăng hoặc giảm giá phòng: true | false", example = "true")
    private Boolean increase;

    @NotBlank(message = "Nhóm phòng thuê lại không được để trống")
    private Long groupContractId;
}
