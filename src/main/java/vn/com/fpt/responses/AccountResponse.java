package vn.com.fpt.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.beans.BeanUtils;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.model.AccountDTO;
import vn.com.fpt.common.utils.DateUtils;

import java.util.Set;
import java.util.stream.Collectors;

import static vn.com.fpt.common.utils.DateUtils.DATETIME_FORMAT_CUSTOM;

@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountResponse {

    private Long accountId;

    private String userName;

    private String password;

    private String phoneNumber;

    private String fullName;

    private Boolean gender;

    private Long addressId;

    private String addressCity;

    private String addressDistrict;

    private String addressWards;

    private String addressMoreDetail;

    private Boolean isOwner;

    private Boolean isDeactivate;

    private String createdAt;

    private String token;

    private Set<String> roles;

    private String roleName;


    public static AccountResponse of(Account account, Set<String> roles, String token) {
        var response = AccountResponse.builder()
                .accountId(account.getId())
                .userName(account.getUserName())
                .token(token)
                .roles(roles)
                .build();
        BeanUtils.copyProperties(account, response);
        return response;
    }

    public static AccountResponse of(Account account) {
        AccountResponse response = new AccountResponse();
        BeanUtils.copyProperties(account, response);
        response.setAccountId(account.getId());
        response.setFullName(account.getFullName());
        response.setRoles(account.getRoles().stream().map(e -> e.getName().name()).collect(Collectors.toSet()));
        response.setAddressId(account.getAddress().getId());
        response.setAddressCity(account.getAddress().getAddressCity());
        response.setAddressDistrict(account.getAddress().getAddressDistrict());
        response.setAddressWards(account.getAddress().getAddressWards());
        response.setAddressMoreDetail(account.getAddress().getAddressMoreDetails());
        response.setIsDeactivate(account.isDeactivate());

        return response;
    }

    public static AccountResponse of(AccountDTO accountDTO) {
        AccountResponse response = new AccountResponse();
        BeanUtils.copyProperties(accountDTO, response);
        response.setCreatedAt(DateUtils.format(accountDTO.getCreatedAt(), DATETIME_FORMAT_CUSTOM));
        response.setAccountId(accountDTO.getAccountId().longValue());
        response.setAddressId(accountDTO.getAddressId().longValue());
        return response;
    }

}
