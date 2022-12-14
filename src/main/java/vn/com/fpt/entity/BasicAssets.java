package vn.com.fpt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.configs.AppConfigs;
import vn.com.fpt.requests.BasicAssetsRequest;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

import static vn.com.fpt.common.utils.DateUtils.now;

@Entity
@Table(name = BasicAssets.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "asset_id"))
public class BasicAssets extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "basic_assets";

    public BasicAssets(String assetName, Long assetType){
        this.assetName = assetName;
        this.assetTypeId = assetType;
    }

    @Column(name = "asset_name")
    private String assetName;

    @Column(name = "asset_type_id")
    private Long assetTypeId;

    @Column(name = "default_quantity")
    private Integer defaultQuantity;

    public static BasicAssets of(BasicAssetsRequest request) {
        return BasicAssets.builder()
                .assetName(request.getAssetName())
                .assetTypeId(request.getAssetTypeId())
                .build();
    }

    public static BasicAssets of(BasicAssets request) {
        return BasicAssets.builder()
                .assetName(request.getAssetName())
                .assetTypeId(request.getAssetTypeId())
                .build();
    }

    public static BasicAssets of(String assetName, Long assetTypeId) {
        return BasicAssets.builder()
                .assetName(assetName)
                .assetTypeId(assetTypeId)
                .build();
    }

    public static BasicAssets add(BasicAssetsRequest request, Long operator) {
        var asset = of(request);

        asset.setCreatedAt(now());
        asset.setCreatedBy(operator);

        return asset;
    }

    public static BasicAssets add(BasicAssets request) {
        var asset = of(request);

        asset.setCreatedAt(request.getCreatedAt());
        asset.setCreatedBy(request.getCreatedBy());

        return asset;
    }

    public static BasicAssets add(String assetName, Long assetTypeId, Long operator) {
        var asset = of(assetName, assetTypeId);

        asset.setCreatedBy(operator);
        asset.setCreatedAt(now());

        return asset;
    }

    public static BasicAssets modify(BasicAssets old, BasicAssetsRequest neww, Long operator) {
        var asset = of(neww);
        //fetch
        asset.setCreatedBy(old.getCreatedBy());
        asset.setCreatedAt(old.getCreatedAt());
        asset.setId(old.getId());

        asset.setModifiedAt(now());
        asset.setModifiedBy(operator);

        return asset;
    }

}
