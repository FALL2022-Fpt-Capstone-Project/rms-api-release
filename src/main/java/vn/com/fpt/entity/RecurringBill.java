package vn.com.fpt.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.configs.AppConfigs;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Optional;

import static vn.com.fpt.common.utils.DateUtils.DATE_FORMAT_3;
import static vn.com.fpt.common.utils.DateUtils.now;

@Entity
@Table(name = RecurringBill.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "recurring_bill_id"))
public class RecurringBill extends BaseEntity {
    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "recurring_bill";

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_contract_id")
    private Long groupContractId;

    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "total_money")
    private Double totalMoney;

    @Column(name = "description")
    private String description;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "is_debt")
    private Boolean isDebt;

    @Column(name = "bill_type")
    private String billType;

    @Column(name = "payment_term")
    private Date paymentTerm;

    @Column(name = "bill_created_time")
    private Date billCreatedTime;

    @Column(name = "service_bill_id")
    private Long serviceBillId;

    @Column(name = "room_bill_id")
    private Long roomBillId;

    @Column(name = "is_in_bill_circle")
    private Boolean isInBillCircle;

    @JsonGetter("payment_term")
    public String getPaymentTerm1() {
        return DateUtils.format(this.paymentTerm, DATE_FORMAT_3);
    }

    @JsonGetter("bill_created_time")
    public String getBillCreatedTime1() {
        return DateUtils.format(this.billCreatedTime, DATE_FORMAT_3);
    }

    public static RecurringBill of(Long roomId,
                                   String roomName,
                                   Long groupId,
                                   Long groupContractId,
                                   Long contractId,
                                   Double totalMoney,
                                   String description,
                                   Boolean isPaid,
                                   Boolean isDebt,
                                   String billType,
                                   Date paymentTerm,
                                   Date billCreatedTime,
                                   Boolean isInBillCircle
    ) {
        return RecurringBill.builder()
                .roomId(roomId)
                .roomName(roomName)
                .groupId(groupId)
                .groupContractId(groupContractId)
                .contractId(contractId)
                .totalMoney(totalMoney)
                .description(description)
                .isPaid(isPaid)
                .isDebt(isDebt)
                .billType(billType)
                .paymentTerm(paymentTerm)
                .billCreatedTime(billCreatedTime)
                .billCreatedTime(billCreatedTime)
                .isInBillCircle(isInBillCircle)
                .build();
    }

    public static RecurringBill add(Long roomId,
                                    String roomName,
                                    Long groupId,
                                    Long groupContractId,
                                    Long contractId,
                                    Double totalMoney,
                                    String description,
                                    Boolean isPaid,
                                    Boolean isDebt,
                                    String billType,
                                    Date paymentTerm,
                                    Date billCreatedTime,
                                    Boolean isInBillCircle
    ) {
        var add = of(
                roomId,
                roomName,
                groupId,
                groupContractId,
                contractId,
                totalMoney,
                description,
                isPaid,
                isDebt,
                billType,
                paymentTerm,
                billCreatedTime,
                isInBillCircle
        );
        add.setCreatedAt(now());
        add.setCreatedBy(Operator.operator());
        return add;
    }
}
