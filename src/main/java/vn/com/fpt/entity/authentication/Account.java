package vn.com.fpt.entity.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vn.com.fpt.configs.AppConfigs;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.BaseEntity;
import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.common.utils.DateUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = Account.TABLE_NAME)
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@AttributeOverride(name = "id", column = @Column(name = "account_id"))
public class Account extends BaseEntity {

    public static final String TABLE_NAME = AppConfigs.TABLE_AUTHENTICATION + "account";


    public Account(Long id, String username, String ePassword) {
        super(id);
        this.userName = username;
        this.password = ePassword;
    }

    @Column(name = "user_name")
    private String userName;

    @Column(name = "encrypted_password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "owner", columnDefinition = "BOOL")
    @ColumnDefault("FALSE")
    private boolean isOwner;

    @Column(name = "deactivate", columnDefinition = "BOOL")
    @ColumnDefault("FALSE")
    private boolean isDeactivate;

    @JoinTable(name = AppConfigs.TABLE_AUTHENTICATION + "user_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonProperty("roles")
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @JoinColumn(name = "address_id")
    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("address_id")
    private Address address;

    public static Account add(RegisterRequest registerRequest, Address address, Set<Role> roles, Long operator) {
        registerRequest.setDeactivate(false);
        var account = of(registerRequest, address, roles);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        account.setPassword(encoder.encode(registerRequest.getPassword()));
        account.setAddress(Address.of(registerRequest));
        account.setCreatedAt(DateUtils.now());
        account.setCreatedBy(operator);
        return account;
    }

    public static Account of(RegisterRequest registerRequest, Address address, Set<Role> roles) {
        return Account.builder()
                .userName(registerRequest.getUserName())
                .fullName(registerRequest.getFullName())
                .password(registerRequest.getPassword())
                .fullName(registerRequest.getFullName())
                .gender(registerRequest.getGender())
                .phoneNumber(registerRequest.getPhoneNumber())
                .isDeactivate(registerRequest.getDeactivate())
                .address(address)
                .roles(roles)
                .build();
    }

    public static Account modify(Account old,
                                 RegisterRequest registerRequest,
                                 Address address,
                                 Set<Role> roles,
                                 Long operator,
                                 Date time) {
        if (registerRequest.getDeactivate() == null) registerRequest.setDeactivate(false);
        else registerRequest.setDeactivate(true);
        var account = of(registerRequest, address, roles);
        var addressToUpdate = Address.of(registerRequest);
        addressToUpdate.setId(address.getId());
        //find account to modify
        account.setId(old.getId());

        //fetch
        account.setCreatedAt(old.getCreatedAt());
        account.setCreatedBy(old.getCreatedBy());

        account.setModifiedAt(time);
        account.setModifiedBy(operator);

        //set address
        account.setAddress(addressToUpdate);
        return account;
    }


}
