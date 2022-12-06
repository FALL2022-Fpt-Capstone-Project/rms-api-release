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

import static vn.com.fpt.common.utils.DateUtils.now;

@Entity
@Table(name = RoomAssets.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "room_asset_id"))
public class RoomAssets extends BaseEntity {
    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "room_assets";

    private String assetName;

    private Integer assetQuantity;

    private Long assetTypeId;

    private Long roomId;

    public static RoomAssets of(String assetName,
                                Integer assetQuantity,
                                Long assetTypeId,
                                Long roomId) {
        return RoomAssets.builder()
                .assetName(assetName)
                .assetQuantity(assetQuantity)
                .assetTypeId(assetTypeId)
                .roomId(roomId).build();
    }

    public static RoomAssets add(String assetName,
                                 Integer assetQuantity,
                                 Long assetTypeId,
                                 Long roomId,
                                 Long operator) {
        var add = of(assetName, assetQuantity, assetTypeId, roomId);
        add.setCreatedAt(now());
        add.setCreatedBy(operator);
        return add;
    }

    public static RoomAssets modify(RoomAssets old,
                                    String assetName,
                                    Integer assetQuantity,
                                    Long assetTypeId,
                                    Long roomId,
                                    Long operator) {
        var modify = of(assetName, assetQuantity, assetTypeId, roomId);
        modify.setId(old.getId());
        modify.setCreatedAt(old.getCreatedAt());
        modify.setCreatedBy(old.getCreatedBy());

        modify.setModifiedAt(now());
        modify.setCreatedBy(operator);
        return modify;
    }
}
