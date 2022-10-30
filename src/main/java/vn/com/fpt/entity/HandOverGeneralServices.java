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

}
