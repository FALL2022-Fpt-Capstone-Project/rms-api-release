package vn.com.fpt.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@SqlResultSetMapping(name = AccountDTO.SQL_RESULT_SET_MAPPING,
        classes = @ConstructorResult(
                targetClass = AccountDTO.class,
                columns = {
                        @ColumnResult(name = "account_id", type = BigInteger.class),
                        @ColumnResult(name = "full_name", type = String.class),
                        @ColumnResult(name = "gender", type = Boolean.class),
                        @ColumnResult(name = "phone_number", type = String.class),
                        @ColumnResult(name = "user_name", type = String.class),
                        @ColumnResult(name = "deactivate", type = Boolean.class),
                        @ColumnResult(name = "created_at", type = Date.class),
                        @ColumnResult(name = "address_id", type = BigInteger.class),
                        @ColumnResult(name = "address_district", type = String.class),
                        @ColumnResult(name = "address_city", type = String.class),
                        @ColumnResult(name = "address_wards", type = String.class),
                        @ColumnResult(name = "address_more_detail", type = String.class),
                        @ColumnResult(name = "role_name", type = String.class)
                }))
public class AccountDTO implements Serializable {
    public static final String SQL_RESULT_SET_MAPPING = "AccountDTO";

    @Id
    private BigInteger accountId;

    private Boolean gender;

    private String fullName;

    private String phoneNumber;

    private String userName;

    private Boolean isDeactivate;

    private Date createdAt;

    private BigInteger addressId;

    private String addressDistrict;

    private String addressCity;

    private String addressWards;

    private String addressMoreDetail;

    private String roleName;


    public AccountDTO(BigInteger accountId,
                      String fullName,
                      Boolean gender,
                      String phoneNumber,
                      String userName,
                      Boolean deactivate,
                      Date createdAt,
                      BigInteger addressId,
                      String addressDistrict,
                      String addressCity,
                      String addressWards,
                      String addressMoreDetail,
                      String roleName) {
        this.accountId = accountId;
        this.fullName = fullName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.isDeactivate = deactivate;
        this.createdAt = createdAt;
        this.addressId = addressId;
        this.addressDistrict = addressDistrict;
        this.addressCity = addressCity;
        this.addressWards = addressWards;
        this.addressMoreDetail = addressMoreDetail;
        this.roleName = roleName;
    }
}
