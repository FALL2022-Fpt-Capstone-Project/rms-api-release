package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoomAssetsRequest {

    private Long roomAssetId;

    @NotBlank(message = "Tên tài sản không được để trống")
    private String assetName;

    @NotBlank(message = "Vị trí tài sản không được để trống")
    private Long assetTypeId;

    private Integer assetQuantity;

    @NotBlank(message = "Phòng không được để trống")
    private Long roomId;

}
