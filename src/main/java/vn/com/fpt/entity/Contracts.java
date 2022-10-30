package vn.com.fpt.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.configs.AppConfigs;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = Contracts.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "contract_id"))
public class Contracts {

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "contracts";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contract_name")
    private String contractName;

    @Column(name = "contract_price")
    private Double contractPrice;

    @Column(name = "contract_deposit")
    private Double contractDeposit;

    @Column(name = "contract_bill_cycle")
    private Integer contractBillCycle;

    @Column(name = "contract_payment_cycle")
    private Integer contractPaymentCycle;

    @Column(name = "contract_start_date")
    private Date contractStartDate;

    @Column(name = "contract_end_date")
    private Date contractEndDate;

    @Column(name = "contract_note")
    private String note;

    @Column(name = "contract_term")
    private Integer contractTerm;

    @Column(name = "contract_is_disable")
    @ColumnDefault("FALSE")
    private Boolean contractIsDisable;

    @Column(name = "renter_id")
    private Long renters;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "contract_type")
    private Integer contractType;
}
