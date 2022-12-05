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
@Table(name = RecurringBill.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "room_bill_id"))
public class RoomBill extends BaseEntity {
    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "room_bill";

    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "group_contract_id")
    private Long groupContractId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "room_total_money")
    private Double contractTotalMoney;

    @Column(name = "bill_cycle")
    private Integer billCycle;

    @Column(name = "note")
    private String note;

}
