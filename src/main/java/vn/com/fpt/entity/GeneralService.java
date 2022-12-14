package vn.com.fpt.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.configs.AppConfigs;
import vn.com.fpt.requests.GeneralServiceRequest;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = GeneralService.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "general_service_id"))
public class GeneralService extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "general_services";

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "contract_id")
    private Long contractId;

    @Column(name = "service_type_id")
    private Long serviceTypeId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "service_price")
    private Double servicePrice;

    @Column(name = "note")
    private String note;

    public GeneralService(Long serviceId,
                          Long groupId,
                          Long serviceTypeMeter,
                          Double servicePrice) {
        this.serviceId = serviceId;
        this.groupId = groupId;
        this.serviceTypeId = serviceTypeMeter;
        this.servicePrice = servicePrice;
    }

    public static GeneralService of(GeneralServiceRequest request) {
        return GeneralService.builder()
                .serviceId(request.getServiceId())
                .contractId(request.getContractId())
                .serviceTypeId(request.getGeneralServiceType())
                .servicePrice(request.getGeneralServicePrice())
                .groupId(request.getGroupId())
                .note(request.getNote()).build();
    }

    public static GeneralService add(GeneralServiceRequest request, Long operator) {
        var generalService = of(request);
        generalService.setCreatedBy(operator);
        generalService.setCreatedAt(DateUtils.now());
        return generalService;
    }

    public static GeneralService modify(GeneralService old, GeneralServiceRequest neww, Long operator) {
        var generalService = of(neww);
        generalService.setId(old.getId());
        generalService.setCreatedAt(old.getCreatedAt());
        generalService.setCreatedBy(old.getCreatedBy());

        generalService.setModifiedBy(operator);
        generalService.setModifiedAt(DateUtils.now());
        return generalService;
    }

}
