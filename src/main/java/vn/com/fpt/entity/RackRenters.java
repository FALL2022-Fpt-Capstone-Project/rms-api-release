package vn.com.fpt.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.configs.AppConfigs;

import javax.persistence.*;

import static vn.com.fpt.common.utils.DateUtils.now;

@Entity
@Table(name = RackRenters.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "rack_renter_id"))
public class RackRenters extends BaseEntity{

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "rack_renters";

    @Column(name = "rack_renter_full_name")
    private String rackRenterFullName;

    @Column(name = "rack_renter_gender")
    private Boolean gender;

    @Column(name = "rack_renter_phone_number")
    private String phoneNumber;

    @Column(name = "rack_renter_identity_number")
    private String identityNumber;

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    public static RackRenters of(String name,
                                 Boolean gender,
                                 String phone,
                                 String identity,
                                 Address address) {
        return RackRenters.builder().
                rackRenterFullName(name).
                phoneNumber(phone).
                gender(gender).
                identityNumber(identity).
                address(address).build();
    }

    public static RackRenters add(String name,
                                  Boolean gender,
                                  String phone,
                                  String identity,
                                  Address address,
                                  Long operator) {        var rackRenter = of(name, gender, phone, identity, address);
        rackRenter.setCreatedAt(now());
        rackRenter.setCreatedBy(operator);

        return rackRenter;
    }
}
