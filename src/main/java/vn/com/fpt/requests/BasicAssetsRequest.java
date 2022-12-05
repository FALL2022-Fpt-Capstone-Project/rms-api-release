package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
public class BasicAssetsRequest {
    private Long id;

    @NotBlank(message = "Tên trang thiết bị cơ bản không được để trống")
    private String assetName;

    @NotBlank(message = "Vị trí trang thiết bị cơ bản không được để trống")
    private Long assetTypeId;


}
