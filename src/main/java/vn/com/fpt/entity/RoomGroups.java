package vn.com.fpt.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.configs.AppConfigs;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = RoomGroups.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "group_id"))
public class RoomGroups extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "room_groups";

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_current_water_index")
    private Integer currentWaterIndex;

    @Column(name = "group_current_electric_index")
    private Integer currentElectricIndex;

    @Column(name = "address_id")
    private Long address;

    public static RoomGroups of(String name,
                         Long address) {
        return RoomGroups.builder()
                .groupName(name)
                .address(address)
                .build();
    }

    public static RoomGroups add(String name,
                          Long address,
                          Long operator) {
        var group = of(name, address);
        group.setCreatedAt(DateUtils.now());
        group.setCreatedBy(operator);
        return group;
    }

    public static RoomGroups modify(RoomGroups old,
                             String name,
                             Long address,
                             Long operator) {
        var group = of(name, address);

        //fetch
        group.setCreatedAt(old.getCreatedAt());
        group.setCreatedBy(old.getCreatedBy());

        group.setId(old.getId());
        group.setModifiedAt(DateUtils.now());
        group.setCreatedBy(operator);
        return group;
    }
}
