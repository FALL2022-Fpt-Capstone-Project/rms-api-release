package vn.com.fpt.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.fpt.common.utils.DateUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SqlResultSetMapping(name = HandOverGeneralServiceDTO.SQL_RESULT_SETS_MAPPING,
        classes = @ConstructorResult(
                targetClass = HandOverGeneralServiceDTO.class,
                columns = {
                        @ColumnResult(name = "hand_over_general_service_id", type = BigInteger.class),
                        @ColumnResult(name = "general_service_id", type = BigInteger.class),
                        @ColumnResult(name = "hand_over_general_service_index", type = Integer.class),
                        @ColumnResult(name = "hand_over_general_service_date_delivery", type = Date.class),
                        @ColumnResult(name = "service_price", type = Double.class),
                        @ColumnResult(name = "service_id", type = BigInteger.class),
                        @ColumnResult(name = "service_name", type = String.class),
                        @ColumnResult(name = "service_show_name", type = String.class),
                        @ColumnResult(name = "service_type_id", type = BigInteger.class),
                        @ColumnResult(name = "service_type_name", type = String.class)
                }))
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class HandOverGeneralServiceDTO implements Serializable {

    public static final String SQL_RESULT_SETS_MAPPING = "HandOverGeneralServiceDTO";
    @Id
    private BigInteger handOverGeneralServiceId;

    private BigInteger generalServiceId;

    private Integer handOverGeneralServiceIndex;

    private String handOverGeneralServiceDateDelivery;

    private Double servicePrice;

    private BigInteger serviceId;

    private String serviceName;

    private String serviceShowName;

    private BigInteger serviceTypeId;

    private String serviceTypeName;

    public HandOverGeneralServiceDTO(BigInteger handOverGeneralServiceId,
                                     BigInteger generalServiceId,
                                     Integer handOverGeneralServiceIndex,
                                     Date handOverGeneralServiceDateDelivery,
                                     Double servicePrice,
                                     BigInteger serviceId,
                                     String serviceName,
                                     String serviceShowName,
                                     BigInteger serviceTypeId,
                                     String serviceTypeName) {
        this.handOverGeneralServiceId = handOverGeneralServiceId;
        this.generalServiceId = generalServiceId;
        this.handOverGeneralServiceIndex = handOverGeneralServiceIndex;
        this.handOverGeneralServiceDateDelivery = DateUtils.format(handOverGeneralServiceDateDelivery, DateUtils.DATE_FORMAT_3);
        this.servicePrice = servicePrice;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceShowName = serviceShowName;
        this.serviceTypeId = serviceTypeId;
        this.serviceTypeName = serviceTypeName;
    }
}
