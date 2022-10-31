package vn.com.fpt.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.configs.AppConfigs;
import vn.com.fpt.requests.AddGeneralServiceRequest;

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

    @Column(name = "service_price")
    private Double servicePrice;

    @Column(name = "note")
    private String note;

    public GeneralService(Long serviceId, Long contractId, Long serviceTypeMeter, Double servicePrice) {
        this.serviceId = serviceId;
        this.contractId = contractId;
        this.serviceTypeId = serviceTypeMeter;
        this.servicePrice = servicePrice;
    }

    public static GeneralService of(AddGeneralServiceRequest request) {
        return GeneralService.builder()
                .serviceId(request.getServiceId())
                .contractId(request.getContractId())
                .serviceTypeId(request.getGeneralServiceType())
                .servicePrice(request.getGeneralServicePrice())
                .note(request.getNote()).build();
    }

    public static GeneralService add(AddGeneralServiceRequest request, Long operator) {
        var generalService = of(request);
        generalService.setCreatedBy(operator);
        generalService.setCreatedAt(DateUtils.now());
        return generalService;
    }

    public static GeneralService modify(AddGeneralServiceRequest request, Long operator) {
        var generalService = of(request);
        generalService.setId(request.getServiceId());
        generalService.setModifiedBy(operator);
        generalService.setModifiedAt(DateUtils.now());
        return generalService;
    }

}
