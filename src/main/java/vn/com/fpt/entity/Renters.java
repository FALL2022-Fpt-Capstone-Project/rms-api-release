package vn.com.fpt.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import vn.com.fpt.configs.AppConfigs;
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

    @JoinColumn(name = "identity_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Identity renterIdentity;

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

}
