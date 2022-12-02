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

    @Column(name = "rack_renter_email")
    private String email;

    @Column(name = "rack_renter_phone_number")
    private String phoneNumber;

    @Column(name = "rack_renter_identity_number")
    private String identityNumber;

    @Column(name = "note")
    private String note;

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    public static RackRenters of(String name,
                                 Boolean gender,
                                 String phone,
                                 String email,
                                 String identity,
                                 String note,
                                 Address address) {
        return RackRenters.builder().
                rackRenterFullName(name).
                phoneNumber(phone).
                email(email).
                gender(gender).
                identityNumber(identity).
                note(note).
                address(address).build();
    }

    public static RackRenters add(String name,
                                  Boolean gender,
                                  String phone,
                                  String email,
                                  String identity,
                                  Address address,
                                  String note,
                                  Long operator) {
        var rackRenter = of(name, gender, phone, identity, email, note, address);
        rackRenter.setCreatedAt(now());
        rackRenter.setCreatedBy(operator);

        return rackRenter;
    }

    public static RackRenters modify(RackRenters old,
                                     String name,
                                     Boolean gender,
                                     String phone,
                                     String email,
                                     Address address,
                                     String note,
                                     Long operator) {
        var rackRenter = of(
                name,
                gender,
                phone,
                old.getIdentityNumber(),
                email,
                note,
                address);
        rackRenter.setCreatedAt(old.getCreatedAt());
        rackRenter.setCreatedBy(old.getCreatedBy());

        rackRenter.setModifiedAt(now());
        rackRenter.setModifiedBy(operator);

        return rackRenter;
    }
}
