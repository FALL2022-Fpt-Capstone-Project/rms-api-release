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

    @Column(name = "group_description")
    private String groupDescription;

    @Column(name = "is_disable")
    private Boolean isDisable;

    @Column(name = "address_id")
    private Long address;

    public static RoomGroups of(String name,
                                String description,
                                Long address) {
        return RoomGroups.builder()
                .groupName(name)
                .address(address)
                .groupDescription(description)
                .build();
    }

    public static RoomGroups add(String name,
                                 String description,
                                 Long address,
                                 Long operator) {
        var group = of(name, description, address);
        group.setCreatedAt(now());
        group.setIsDisable(false);
        group.setCreatedBy(operator);
        return group;
    }

    public static RoomGroups delete(RoomGroups old, Long operator) {
        var delete = modify(
                old,
                old.getGroupName(),
                old.getGroupDescription(),
                operator);
        delete.setIsDisable(true);
        return delete;
    }

    public static RoomGroups modify(RoomGroups old,
                                    String name,
                                    String description,
                                    Long operator) {
        var group = of(name, description, old.getAddress());

        //fetch
        group.setCreatedAt(old.getCreatedAt());
        group.setCreatedBy(old.getCreatedBy());
        group.setIsDisable(false);

        group.setId(old.getId());
        group.setModifiedAt(now());
        group.setCreatedBy(operator);
        return group;
    }
}
