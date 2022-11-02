package vn.com.fpt.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.configs.AppConfigs;
import vn.com.fpt.requests.RenterRequest;

import javax.persistence.*;

@Entity
@Table(name = Renters.TABLE_NAME)
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "renter_id"))
public class Renters extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_MANAGER + "renters";

    @Column(name = "renter_full_name")
    private String renterFullName;

    @Column(name = "renter_gender")
    private Boolean gender;

    @Column(name = "renter_phone_number")
    private String phoneNumber;

    @Column(name = "renter_email")
    private String email;

    @Column(name = "renter_identity_number")
    private String identityNumber;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "represent")
    private Boolean represent;

    @Column(name = "license_plates")
    private String licensePlates;

    @JoinColumn(name = "identity_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Identity renterIdentity;

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    public static Renters of(RenterRequest renter, Address address) {
        return Renters.builder()
                .renterFullName(renter.getName())
                .gender(renter.getGender())
                .phoneNumber(renter.getPhoneNumber())
                .email(renter.getEmail())
                .roomId(renter.getRoomId())
                .licensePlates(renter.getLicensePlates())
                .identityNumber(renter.getIdentityCard())
                .address(address)
                .represent(renter.getRepresent())
                .renterIdentity(new Identity())
                .build();
    }

    public static Renters add(RenterRequest request, Address address, Long operator) {
        var renter = of(request, address);
        renter.setCreatedBy(operator);
        renter.setCreatedAt(DateUtils.now());
        return renter;
    }

    public static Renters modify(Renters old, RenterRequest neww, Address newAddress, Long operator) {
        var renter = of(neww, newAddress);

        //fetch
        renter.setCreatedAt(old.getCreatedAt());
        renter.setCreatedBy(old.getCreatedBy());

        renter.setModifiedAt(DateUtils.now());
        renter.setModifiedBy(operator);

        return renter;
    }

}
