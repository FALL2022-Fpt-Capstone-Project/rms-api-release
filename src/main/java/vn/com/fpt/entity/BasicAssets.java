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

    @Column(name = "asset_name")
    private String assetName;

    @Column(name = "asset_type_id")
    private Long assetTypeId;

    public static BasicAssets of(BasicAssetsRequest request) {
        return BasicAssets.builder()
                .assetName(request.getAssetName())
                .assetTypeId(request.getAssetTypeId())
                .build();
    }

    public static BasicAssets add(BasicAssetsRequest request, Long operator) {
        var asset = of(request);
        asset.setCreatedAt(DateUtils.now());
        asset.setCreatedBy(operator);
        return asset;
    }

    public static BasicAssets modify(BasicAssets old, BasicAssetsRequest neww, Long operator) {
        var asset = of(neww);
        //fetch
        asset.setCreatedBy(old.getCreatedBy());
        asset.setCreatedAt(old.getCreatedAt());
        asset.setId(old.getId());

        asset.setModifiedAt(DateUtils.now());
        asset.setModifiedBy(operator);

        return asset;
    }

}
