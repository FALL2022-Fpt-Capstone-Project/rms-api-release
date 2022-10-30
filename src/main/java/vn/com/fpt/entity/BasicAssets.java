package vn.com.fpt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = BasicAssets.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "asset_id"))
public class BasicAssets extends BaseEntity{

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "basic_assets";

    @Column(name = "asset_name")
    private String assetName;

    @Column(name = "asset_type_id")
    @JsonProperty("asset_type_id")
    private Long assetTypeId;

}
