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
import java.util.Date;


@Entity
@Table(name = HandOverGeneralServices.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "hand_over_general_service_id"))
public class HandOverGeneralServices extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "hand_over_general_services";

    @Column(name = "general_service_id")
    private Long generalServiceId;

    @Column(name = "hand_over_general_service_index")
    private Integer handOverGeneralServiceIndex;

    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "hand_over_general_service_date_delivery")
    private Date dateDelivery;

    public static HandOverGeneralServices of(Long contractId,
                                             Integer handOverGeneralServiceIndex,
                                             Long generalServiceId,
                                             Date dateDelivery) {
        return HandOverGeneralServices.builder()
                .handOverGeneralServiceIndex(handOverGeneralServiceIndex)
                .contractId(contractId)
                .generalServiceId(generalServiceId)
                .dateDelivery(dateDelivery)
                .build();
    }

    public static HandOverGeneralServices add(Long contractId,
                                              Integer handOverGeneralServiceIndex,
                                              Long generalServiceId,
                                              Date dateDelivery,
                                              Long operator) {
        var handOverGeneralServices = of(
                contractId,
                handOverGeneralServiceIndex,
                generalServiceId,
                dateDelivery);

        handOverGeneralServices.setCreatedAt(DateUtils.now());
        handOverGeneralServices.setCreatedBy(operator);
        return handOverGeneralServices;
    }

    public static HandOverGeneralServices modify(HandOverGeneralServices old,
                                                 Long contractId,
                                                 Integer handOverGeneralServiceIndex,
                                                 Long generalServiceId,
                                                 Date dateDelivery,
                                                 Long operator) {
        var handOverGeneralServices = of(
                contractId,
                handOverGeneralServiceIndex,
                generalServiceId,
                dateDelivery);

        handOverGeneralServices.setId(old.getId());
        handOverGeneralServices.setModifiedAt(DateUtils.now());
        handOverGeneralServices.setModifiedBy(operator);
        return handOverGeneralServices;
    }
}
