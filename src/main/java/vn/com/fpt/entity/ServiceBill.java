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

import java.beans.BeanInfo;
import java.util.Date;

import static vn.com.fpt.common.utils.DateUtils.now;

@Entity
@Table(name = ServiceBill.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "service_bill_id"))
public class ServiceBill extends BaseEntity {
    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "service_bill";

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "service_type_id")
    private Long serviceTypeId;

    @Column(name = "service_price")
    private Double servicePrice;

    @Column(name = "description")
    private String description;

    @Column(name = "service_index")
    private Integer serviceIndex;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "group_contract_id")
    private Long groupContractId;

    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "service_bill_total_money")
    private Double serviceBillTotalMoney;

    @Column(name = "bill_created_time")
    private Date billCreatedTime;

    public static ServiceBill of(Long serviceId,
                                 Long serviceTypeId,
                                 Double servicePrice,
                                 String description,
                                 Integer serviceIndex,
                                 Long roomId,
                                 Long groupContractId,
                                 Long contractId,
                                 Double serviceBillTotalMoney,
                                 Date billCreatedTime) {
        return ServiceBill.builder()
                .serviceId(serviceId)
                .serviceTypeId(serviceTypeId)
                .servicePrice(servicePrice)
                .description(description)
                .serviceIndex(serviceIndex)
                .roomId(roomId)
                .groupContractId(groupContractId)
                .contractId(contractId)
                .serviceBillTotalMoney(serviceBillTotalMoney)
                .billCreatedTime(billCreatedTime)
                .build();
    }

    public static ServiceBill add(Long serviceId,
                                  Long serviceTypeId,
                                  Double servicePrice,
                                  String description,
                                  Integer serviceIndex,
                                  Long roomId,
                                  Long groupContractId,
                                  Long contractId,
                                  Double serviceBillTotalMoney,
                                  Date billCreatedTime,
                                  Long operator) {
        var add = of(
                serviceId,
                serviceTypeId,
                servicePrice,
                description,
                serviceIndex,
                roomId,
                groupContractId,
                contractId,
                serviceBillTotalMoney,
                billCreatedTime);
        add.setCreatedAt(now());
        add.setCreatedBy(operator);
        return add;
    }

    public static ServiceBill modify(ServiceBill old,
                                     Long serviceId,
                                     Long serviceTypeId,
                                     Double servicePrice,
                                     String description,
                                     Integer serviceIndex,
                                     Long roomId,
                                     Long groupContractId,
                                     Long contractId,
                                     Double serviceBillTotalMoney,
                                     Date billCreatedTime,
                                     Long operator) {
        var modify = of(
                serviceId,
                serviceTypeId,
                servicePrice,
                description,
                serviceIndex,
                roomId,
                groupContractId,
                contractId,
                serviceBillTotalMoney,
                billCreatedTime
        );

        //fetch
        modify.setCreatedBy(old.getCreatedBy());
        modify.setCreatedAt(old.getCreatedAt());

        modify.setModifiedBy(operator);
        modify.setModifiedAt(now());
        return modify;
    }
}
