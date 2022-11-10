package vn.com.fpt.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.configs.AppConfigs;
import vn.com.fpt.requests.GroupContractRequest;
import vn.com.fpt.requests.RoomContractRequest;

import javax.persistence.*;
import java.util.Date;

import static vn.com.fpt.common.constants.ManagerConstants.LEASE_CONTRACT;
import static vn.com.fpt.common.constants.ManagerConstants.SUBLEASE_CONTRACT;


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
public class Contracts extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "contracts";

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

    public static Contracts of(RoomContractRequest request) {
        return Contracts.builder()
                .contractName(request.getContractName())
                .contractPrice(request.getContractPrice())
                .contractDeposit(request.getContractDeposit())
                .contractBillCycle(request.getContractBillCycle())
                .contractPaymentCycle(request.getContractPaymentCycle())
                .contractStartDate(DateUtils.parse(request.getContractStartDate(), DateUtils.DATE_FORMAT_3))
                .contractEndDate(DateUtils.parse(request.getContractEndDate(), DateUtils.DATE_FORMAT_3))
                .note(request.getContractNote())
                .contractTerm(request.getContractTerm())
                .roomId(request.getRoomId())
                .groupId(request.getGroupId()).build();
    }

    public static Contracts of(GroupContractRequest request, Long groupId) {
        return Contracts.builder()
                .contractName(request.getContractName())
                .contractPrice(request.getContractPrice())
                .contractDeposit(request.getContractDeposit())
                .contractBillCycle(request.getContractBillCycle())
                .contractPaymentCycle(request.getContractPaymentCycle())
                .contractStartDate(DateUtils.parse(request.getContractStartDate(), DateUtils.DATE_FORMAT_3))
                .contractEndDate(DateUtils.parse(request.getContractEndDate(), DateUtils.DATE_FORMAT_3))
                .note(request.getContractNote())
                .contractTerm(request.getContractTerm())
                .groupId(groupId).build();
    }

    public static Contracts addForGroup(GroupContractRequest request, Long groupId, Long operator) {
        var renterContract = of(request, groupId);
        renterContract.setContractType(LEASE_CONTRACT);
        renterContract.setCreatedBy(operator);
        renterContract.setCreatedAt(DateUtils.now());
        return renterContract;
    }

    public static Contracts modifyForSublease(Contracts old, GroupContractRequest neww, Long operator) {
        var groupContract = of(neww, old.getGroupId());
        groupContract.setId(old.getId());

        //fetch
        groupContract.setCreatedBy(old.getCreatedBy());
        groupContract.setCreatedAt(old.getCreatedAt());


        groupContract.setModifiedAt(DateUtils.now());
        groupContract.setModifiedBy(operator);

        return groupContract;
    }


    public static Contracts addForLease(RoomContractRequest request, Long operator) {
        var renterContract = of(request);
        renterContract.setContractType(SUBLEASE_CONTRACT);
        renterContract.setContractIsDisable(false);
        renterContract.setCreatedBy(operator);
        renterContract.setCreatedAt(DateUtils.now());
        return renterContract;
    }

    public static Contracts modifyForSublease(Contracts old, RoomContractRequest neww, Long operator) {
        var renterContract = of(neww);

        renterContract.setId(old.getId());
        renterContract.setContractType(SUBLEASE_CONTRACT);
        renterContract.setRenters(old.getRenters());
        renterContract.setContractIsDisable(false);
        renterContract.setCreatedBy(old.getCreatedBy());
        renterContract.setCreatedAt(old.getCreatedAt());

        //fetch
        renterContract.setModifiedAt(DateUtils.now());
        renterContract.setModifiedBy(operator);

        return renterContract;
    }
}
