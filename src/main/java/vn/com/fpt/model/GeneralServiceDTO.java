package vn.com.fpt.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Getter
@Setter
@Entity
@SqlResultSetMapping(name = GeneralServiceDTO.SQL_RESULT_SET_MAPPING,
        classes = @ConstructorResult(
                targetClass = GeneralServiceDTO.class,
                columns = {
                        @ColumnResult(name = "general_service_id", type = BigInteger.class),
                        @ColumnResult(name = "service_price", type = Double.class),
                        @ColumnResult(name = "note", type = String.class),
                        @ColumnResult(name = "service_type_id", type = BigInteger.class),
                        @ColumnResult(name = "service_type_name", type = String.class),
                        @ColumnResult(name = "service_id", type = BigInteger.class),
                        @ColumnResult(name = "service_name", type = String.class),
                        @ColumnResult(name = "service_show_name", type = String.class),
                }))
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GeneralServiceDTO implements Serializable {
    public static final String SQL_RESULT_SET_MAPPING = "GeneralServiceDTO";

    @Id
    private BigInteger generalServiceId;

    private Double servicePrice;

    private String note;

    private BigInteger serviceTypeId;

    private String serviceTypeName;

    private BigInteger serviceId;

    private String serviceName;

    private String serviceShowName;

    public GeneralServiceDTO(BigInteger generalServiceId, Double servicePrice, String note, BigInteger serviceTypeId, String serviceTypeName, BigInteger serviceId, String serviceName, String serviceShowName) {
        this.generalServiceId = generalServiceId;
        this.servicePrice = servicePrice;
        this.note = note;
        this.serviceTypeId = serviceTypeId;
        this.serviceTypeName = serviceTypeName;
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.serviceShowName = serviceShowName;
    }
}
