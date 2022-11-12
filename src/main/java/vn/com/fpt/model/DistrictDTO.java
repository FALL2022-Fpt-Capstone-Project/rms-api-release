package vn.com.fpt.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DistrictDTO {

    public static final String SQL_RESULT_SET_MAPPING = "DistrictDTO";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String addressDistrict;

    public DistrictDTO(String addressDistrict) {
        this.addressDistrict = addressDistrict;
    }
}
