package vn.com.fpt.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.configs.AppConfigs;
import vn.com.fpt.requests.HandOverAssetsRequest;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = HandOverAssets.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "hand_over_asset_id"))
public class HandOverAssets extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "hand_over_assets";

    @Column(name = "hand_over_asset_quantity")
    private Integer quantity;

    @Column(name = "asset_id")
    private Long assetId;

    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "hand_over_asset_status")
    private Boolean handOverAssetStatus;

    @Column(name = "hand_over_asset_date_delivery")
    private Date handOverDateDelivery;

    public static HandOverAssets of(HandOverAssetsRequest request,
                                    Long contractId,
                                    Date dateDelivery) {
        return HandOverAssets.builder()
                .contractId(contractId)
                .assetId(request.getAssetId())
                .quantity(request.getHandOverAssetQuantity())
                .handOverAssetStatus(request.getHandOverAssetStatus())
                .handOverDateDelivery(dateDelivery)
                .build();
    }

    public static HandOverAssets add(HandOverAssetsRequest request,
                                     Long contractId,
                                     Date dateDelivery,
                                     Long operator) {
        var handOverAsset = of(request, contractId, dateDelivery);
        handOverAsset.setCreatedBy(operator);
        handOverAsset.setCreatedAt(DateUtils.now());

        return handOverAsset;
    }

    public static HandOverAssets modify(HandOverAssets old, HandOverAssetsRequest neww,
                                        Long contractId,
                                        Date dateDelivery,
                                        Long operator) {
        var handOverAsset = of(neww, contractId, dateDelivery);

        //fetch
        handOverAsset.setId(old.getId());
        handOverAsset.setCreatedBy(old.getCreatedBy());
        handOverAsset.setCreatedAt(old.getCreatedAt());

        handOverAsset.setModifiedBy(operator);
        handOverAsset.setModifiedAt(DateUtils.now());

        return handOverAsset;
    }
}
