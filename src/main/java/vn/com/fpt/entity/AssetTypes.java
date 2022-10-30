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

@Entity
@Table(name = AssetTypes.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "asset_types_id"))
public class AssetTypes extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "asset_types";

    @Column(name = "asset_type_name")
    private String assetTypeName;

    @Column(name = "asset_type_show_name")
    private String assetTypeShowName;

}
