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
import java.util.Date;

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

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_contract_id")
    private Long groupContractId;

    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "total_money")
    private Double totalMoney;

    @Column(name = "is_paid")
    private Boolean isPaid;

    @Column(name = "is_debt")
    private Boolean isDebt;

    @Column(name = "bill_type")
    private String billType;

    @Column(name = "payment_term")
    private Date paymentTerm;

    @Column(name = "service_bill_id")
    private String serviceBillId;
}
