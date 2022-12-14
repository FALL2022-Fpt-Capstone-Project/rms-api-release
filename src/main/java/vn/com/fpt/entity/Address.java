package vn.com.fpt.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.configs.AppConfigs;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.requests.RenterRequest;

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

    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private RackRenters rackRenters;

    public Address(String addressCity,
                   String addressDistrict,
                   String addressWards,
                   String addressMoreDetails) {
        this.addressCity = addressCity;
        this.addressDistrict = addressDistrict;
        this.addressWards = addressWards;
        this.addressMoreDetails = addressMoreDetails;
    }

    public static Address of(RegisterRequest registerRequest) {
        return Address.builder()
                .addressCity(registerRequest.getAddressCity())
                .addressDistrict(registerRequest.getAddressDistrict())
                .addressWards(registerRequest.getAddressWards())
                .addressMoreDetails(registerRequest.getAddressMoreDetail())
                .build();
    }

    public static Address of(RenterRequest request) {
        return Address.builder()
                .addressCity(request.getAddressCity())
                .addressDistrict(request.getAddressDistrict())
                .addressWards(request.getAddressWards())
                .addressMoreDetails(request.getAddressMoreDetail())
                .build();
    }

    public static Address of(String addressCity,
                             String addressDistrict,
                             String addressWards,
                             String addressMoreDetails) {
        return Address.builder()
                .addressCity(addressCity)
                .addressDistrict(addressDistrict)
                .addressWards(addressWards)
                .addressMoreDetails(addressMoreDetails)
                .build();
    }

    public static Address add(String newCity,
                              String newDistrict,
                              String newWards,
                              String newMoreDetails,
                              Long operator) {
        var address = of(newCity, newDistrict, newWards, newMoreDetails);
        address.setCreatedAt(DateUtils.now());
        address.setCreatedBy(operator);

        return address;
    }

    public static Address add(RenterRequest renterRequest, Long operator) {
        var address = of(renterRequest);
        address.setCreatedAt(DateUtils.now());
        address.setCreatedBy(operator);

        return address;
    }

    public Address modify(Address old,
                          String newCity,
                          String newDistrict,
                          String newWards,
                          String newMoreDetails,
                          Long operator) {
        var address = of(newCity, newDistrict, newWards, newMoreDetails);
        address.setId(old.getId());
        //fetch
        address.setCreatedBy(old.getCreatedBy());
        address.setCreatedAt(old.getCreatedAt());

        address.setModifiedAt(DateUtils.now());
        address.setModifiedBy(operator);
        return address;
    }

    public static Address modify(Address old,
                                 RenterRequest neww,
                                 Long operator) {
        var address = of(neww);
        address.setId(old.getId());
        //fetch
        address.setCreatedBy(old.getCreatedBy());
        address.setCreatedAt(old.getCreatedAt());

        address.setModifiedAt(DateUtils.now());
        address.setModifiedBy(operator);
        return address;
    }

}
