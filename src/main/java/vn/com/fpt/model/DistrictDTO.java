package vn.com.fpt.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@Entity
@SqlResultSetMapping(name = DistrictDTO.SQL_RESULT_SET_MAPPING,
        classes = @ConstructorResult(
                targetClass = DistrictDTO.class,
                columns = {
                        @ColumnResult(name = "address_city", type = String.class)
                }))
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DistrictDTO {

    public static final String SQL_RESULT_SET_MAPPING = "DistrictDTO";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;

    public DistrictDTO(String city) {
        this.city = city;
    }
}
