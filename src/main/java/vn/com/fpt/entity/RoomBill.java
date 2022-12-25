package vn.com.fpt.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.configs.AppConfigs;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Date;

import static vn.com.fpt.common.utils.DateUtils.now;

@Entity
@Table(name = RoomBill.TABLE_NAME)
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
    private Double roomTotalMoney;

    @Column(name = "payment_cycle")
    private Integer paymentCycle;

    @Column(name = "bill_created_time")
    private Date billCreatedTime;

    @Column(name = "note")
    private String note;

    public static RoomBill of(Long contractId,
                              Long groupContractId,
                              Long groupId,
                              Long roomId,
                              Double roomTotalMoney,
                              Integer paymentCycle,
                              Date billCreatedTime,
                              String note) {
        return RoomBill.builder()
                .contractId(contractId)
                .groupContractId(groupContractId)
                .groupId(groupId)
                .roomId(roomId)
                .roomTotalMoney(roomTotalMoney)
                .paymentCycle(paymentCycle)
                .billCreatedTime(billCreatedTime)
                .note(note).build();
    }

    public static RoomBill add(Long contractId,
                               Long groupContractId,
                               Long groupId,
                               Long roomId,
                               Double roomTotalMoney,
                               Integer paymentCycle,
                               Date billCreatedTime,
                               String note) {
        var add = of(
                contractId,
                groupContractId,
                groupId,
                roomId,
                roomTotalMoney,
                paymentCycle,
                billCreatedTime,
                note);
        add.setCreatedAt(now());
        add.setCreatedBy(Operator.operator());
        return add;
    }
}
