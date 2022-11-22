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
@Table(name = Rooms.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "room_id"))
public class Rooms extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "rooms";

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "room_floor")
    private Integer roomFloor;

    @Column(name = "room_limit_people")
    private Integer roomLimitPeople;

    @Column(name = "room_current_water_index")
    private Integer roomCurrentWaterIndex;

    @Column(name = "room_current_electric_index")
    private Integer roomCurrentElectricIndex;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "group_contract_id")
    private Long groupContractId;

    @Column(name = "room_price")
    private Double roomPrice;

    @Column(name = "room_area")
    private Double roomArea;

    @Column(name = "is_disable")
    private Boolean isDisable;

    public static Rooms modify(Rooms old, Rooms neww, Long operator) {
        neww.setCreatedAt(old.getCreatedAt());
        neww.setCreatedBy(old.getCreatedBy());
        neww.setId(old.getId());

        neww.setModifiedBy(operator);
        neww.setModifiedAt(DateUtils.now());
        return neww;
    }

    public static Rooms of(String name,
                    Integer floor,
                    Integer limit,
                    Long groupId,
                    Long groupContractId,
                    Double area,
                    Double price) {
        return Rooms.builder()
                .roomName(name)
                .roomFloor(floor)
                .roomLimitPeople(limit)
                .groupId(groupId)
                .groupContractId(groupContractId)
                .roomPrice(price)
                .roomArea(area)
                .build();
    }

    public static Rooms add(String name,
                     Integer floor,
                     Integer limit,
                     Long groupId,
                     Long groupContractId,
                     Double price,
                     Double area,
                     Long operator) {
        var room = of(name, floor, limit, groupId, groupContractId, area, price);
        room.setCreatedAt(DateUtils.now());
        room.setCreatedBy(operator);
        room.setIsDisable(false);
        return room;
    }

    public static Rooms modify(Rooms old,
                        String name,
                        Integer floor,
                        Integer limit,
                        Long groupId,
                        Double price,
                        Double area,
                        Long operator) {
        var room = of(name, floor, limit, groupId, old.getGroupContractId(), area, price);

        //fetch
        room.setId(old.getId());
        room.setCreatedAt(old.getCreatedAt());
        room.setCreatedBy(old.getCreatedBy());
        room.setIsDisable(old.getIsDisable());

        room.setModifiedAt(DateUtils.now());
        room.setCreatedBy(operator);

        return room;
    }

    public static Rooms delete(Rooms old, Long operator){
        var delete = modify(old,
                old.getRoomName(),
                old.getRoomFloor(),
                old.getRoomLimitPeople(),
                old.getGroupId(),
                old.getRoomPrice(),
                old.getRoomArea(),
                operator
                );
        delete.setIsDisable(true);
        return delete;
    }

}
