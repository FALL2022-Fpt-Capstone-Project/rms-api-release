package vn.com.fpt.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.configs.AppConfigs;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.requests.AddContractRequest;
import vn.com.fpt.requests.RegisterRequest;

import javax.persistence.*;

@Entity
@Table(name = Address.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "address_id"))
public class Address extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "address";

    @Column(name = "address_city")
    private String addressCity;

    @Column(name = "address_district")
    private String addressDistrict;

    @Column(name = "address_wards")
    private String addressWards;

    @Column(name = "address_more_detail")
    private String addressMoreDetails;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Account account;

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private Renters renters;

    public static Address of(RegisterRequest registerRequest) {
        var address = Address.builder()
                .addressCity(registerRequest.getAddressCity())
                .addressDistrict(registerRequest.getAddressDistrict())
                .addressWards(registerRequest.getAddressWards())
                .addressMoreDetails(registerRequest.getAddressMoreDetail())
                .build();
        return address;
    }

    public static Address add() {
        return null;
    }

}
