package vn.com.fpt.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.configs.AppConfigs;

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

}
