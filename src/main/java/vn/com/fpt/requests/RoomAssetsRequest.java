package vn.com.fpt.requests;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoomAssetsRequest {

    private Long roomAssetId;

    @NotBlank(message = "Tên trang thiết bị không được để trống")
    private String assetName;

    private Long assetTypeId;

    @Min(value = 0, message = "Số lượng trang thiết bị không được để trống")
    private Integer assetQuantity;

    private Long roomId;

}
