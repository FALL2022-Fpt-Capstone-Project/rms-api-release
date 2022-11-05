package vn.com.fpt.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import vn.com.fpt.common.utils.DateUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SqlResultSetMapping(name = HandOverAssetsDTO.SQL_RESULT_SET_MAPPING,
        classes = @ConstructorResult(
                targetClass = HandOverAssetsDTO.class,
                columns = {
                        @ColumnResult(name = "hand_over_asset_id", type = BigInteger.class),
                        @ColumnResult(name = "hand_over_asset_quantity", type = Integer.class),
                        @ColumnResult(name = "hand_over_asset_status", type = Boolean.class),
                        @ColumnResult(name = "hand_over_asset_date_delivery", type = Date.class),
                        @ColumnResult(name = "asset_id", type = BigInteger.class),
                        @ColumnResult(name = "asset_name", type = String.class),
                        @ColumnResult(name = "asset_type_id", type = BigInteger.class),
                        @ColumnResult(name = "asset_type_name", type = String.class),
                        @ColumnResult(name = "asset_type_show_name", type = String.class)
                }))
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class HandOverAssetsDTO implements Serializable {

    public static final String SQL_RESULT_SET_MAPPING = "HandOverAssetsDTO";
    @Id
    private BigInteger handOverAssetId;

    private Integer handOverAssetQuantity;

    private Boolean handOverAssetStatus;

    private String handOverDateDelivery;

    private BigInteger assetId;

    private String assetName;

    private BigInteger assetTypeId;

    private String assetTypeName;

    private String assetTypeShowName;

    public HandOverAssetsDTO(BigInteger handOverAssetId, Integer quantity, Boolean handOverAssetStatus, Date handOverDateDelivery, BigInteger assetId, String assetName, BigInteger assetTypeId, String assetTypeName, String assetTypeShowName) {
        this.handOverAssetId = handOverAssetId;
        this.handOverAssetQuantity = quantity;
        this.handOverAssetStatus = handOverAssetStatus;
        this.handOverDateDelivery = DateUtils.format(handOverDateDelivery, DateUtils.DATE_FORMAT_1);
        this.assetId = assetId;
        this.assetName = assetName;
        this.assetTypeId = assetTypeId;
        this.assetTypeName = assetTypeName;
        this.assetTypeShowName = assetTypeShowName;
    }
}
