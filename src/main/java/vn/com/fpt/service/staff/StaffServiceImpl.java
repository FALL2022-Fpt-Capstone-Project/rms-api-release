package vn.com.fpt.service.staff;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.model.AccountDTO;
import vn.com.fpt.repositories.AccountRepository;
import vn.com.fpt.repositories.AddressRepository;
import vn.com.fpt.repositories.RoleRepository;
import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.responses.AccountResponse;
import vn.com.fpt.service.authentication.AuthenticationService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

import static vn.com.fpt.constants.ErrorStatusConstants.*;
import static vn.com.fpt.model.AccountDTO.SQL_RESULT_SET_MAPPING;
import static vn.com.fpt.common.utils.DateUtils.checkFormat;

@RequiredArgsConstructor
@Service
public class StaffServiceImpl implements StaffService {

    private final AuthenticationService accountService;

    private final AccountRepository accountRepository;

    private final AddressRepository addressRepository;

    private final EntityManager entityManager;

    private final RoleRepository roleRepository;

    @Override
    public AccountResponse addStaff(RegisterRequest registerRequest, Long operator) {
        return accountService.register(registerRequest, operator);
    }

    @Transactional
    public AccountResponse updateStaff(Long id,
                                       RegisterRequest registerRequest,
                                       Long modifyBy,
                                       Date modifyAt) {
        var account = accountRepository.findById(id).orElseThrow(() -> new BusinessException(USER_NOT_FOUND, "Không tìm thấy tài khoản: account_id" + id));
        if (accountRepository.findAccountByUserName(registerRequest.getUserName()).isPresent())
            throw new BusinessException(EXISTED_ACCOUNT, "Tên tài khoản: " + registerRequest.getUserName());
        var address = addressRepository.findById(account.getId()).orElse(new Address());
        var role = accountService.roleChecker(registerRequest.getRoles());

        if (Objects.isNull(registerRequest.getPassword())) {
            registerRequest.setPassword(account.getPassword());
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            registerRequest.setPassword(encoder.encode(registerRequest.getPassword()));
        }
        return AccountResponse.of(accountRepository.save(
                Account.modify(
                        account,
                        registerRequest,
                        address,
                        role,
                        modifyBy,
                        modifyAt)
        ));
    }

    @Override
    public List<AccountResponse> listStaff(String role,
                                           String order,
                                           String startDate,
                                           String endDate,
                                           Boolean deactivate,
                                           String name,
                                           String userName) {
        Boolean onlyStaff = Boolean.FALSE;

        StringBuilder selectBuild = new StringBuilder("SELECT ");
        selectBuild.append("acc.account_id,");
        selectBuild.append("acc.user_name, ");
        selectBuild.append("acc.full_name, ");
        selectBuild.append("acc.gender, ");
        selectBuild.append("acc.phone_number, ");
        selectBuild.append("acc.deactivate, ");
        selectBuild.append("acc.created_at, ");
        selectBuild.append("address.address_id, ");
        selectBuild.append("address.address_city, ");
        selectBuild.append("address.address_district, ");
        selectBuild.append("address.address_wards, ");
        selectBuild.append("address.address_more_detail, ");
        selectBuild.append("ar.name as role_name ");

        StringBuilder fromBuild = new StringBuilder("FROM ");
        fromBuild.append("authentication_account acc ");
        fromBuild.append("JOIN authentication_user_role ur on acc.account_id = ur.account_id ");
        fromBuild.append("JOIN authentication_role ar on ur.role_id = ar.role_id ");
        fromBuild.append("JOIN manager_address address on acc.address_id = address.address_id ");

        Map<String, Object> params = new HashMap<>();

        StringBuilder whereBuild = new StringBuilder("WHERE 1=1 ");
        //only select staff
        whereBuild.append("AND acc.owner = :isOwner ");

        params.put("isOwner", onlyStaff);

        if (StringUtils.isNotBlank(role)) {
            if (!role.contains(",")) {
                whereBuild.append("AND ar.name = :role ");
                params.put("role", "ROLE_" + role.toUpperCase());
            }
        }
        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            if (Boolean.TRUE.equals(!checkFormat(startDate)) || Boolean.TRUE.equals(!checkFormat(endDate)))
                throw new BusinessException(BAD_REQUEST, "Ngày tìm kiếm không hợp lệ");

            whereBuild.append("AND acc.created_at BETWEEN cast(:startDate AS timestamp) AND cast(:endDate AS timestamp) ");
            params.put("startDate", startDate);
            params.put("endDate", endDate);
        }
        if (StringUtils.isNotBlank(name)) {
            whereBuild.append("AND acc.full_name LIKE concat('%',:name,'%') ");
            params.put("name", name);
        }
        if (StringUtils.isNotBlank(userName)) {
            whereBuild.append("AND acc.user_name LIKE concat('%',:userName,'%') ");
            params.put("userName", userName);
        }
        if (Objects.isNull(deactivate)) {
            //default: show list active account
            whereBuild.append("AND acc.deactivate = TRUE ");
        } else {
            if (Boolean.FALSE.equals(deactivate)) {
                whereBuild.append("AND acc.deactivate = FALSE ");
            } else {
                whereBuild.append("AND acc.deactivate = TRUE ");
            }
        }

        if (StringUtils.isNotBlank(order)) {
            whereBuild.append("ORDER BY acc.created_at ASC ");
        } else {
            //default: show latest list account
            whereBuild.append("ORDER BY acc.created_at DESC ");
        }

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .append(whereBuild)
                .toString();

        Query query = entityManager.createNativeQuery(queryBuild, SQL_RESULT_SET_MAPPING);
        params.forEach(query::setParameter);

        List<AccountDTO> queryResult = query.getResultList();
        return queryResult.stream().map(AccountResponse::of).collect(Collectors.toList());
    }

    @Override
    public AccountResponse staff(Long id) {
        StringBuilder selectBuild = new StringBuilder("SELECT ");
        selectBuild.append("acc.account_id,");
        selectBuild.append("acc.user_name, ");
        selectBuild.append("acc.full_name, ");
        selectBuild.append("acc.gender, ");
        selectBuild.append("acc.phone_number, ");
        selectBuild.append("acc.deactivate, ");
        selectBuild.append("acc.created_at, ");
        selectBuild.append("address.address_id, ");
        selectBuild.append("address.address_city, ");
        selectBuild.append("address.address_district, ");
        selectBuild.append("address.address_wards, ");
        selectBuild.append("address.address_more_detail, ");
        selectBuild.append("ar.name as role_name ");

        StringBuilder fromBuild = new StringBuilder("FROM ");
        fromBuild.append("authentication_account acc ");
        fromBuild.append("JOIN authentication_user_role ur on acc.account_id = ur.account_id ");
        fromBuild.append("JOIN authentication_role ar on ur.role_id = ar.role_id ");
        fromBuild.append("JOIN manager_address address on acc.address_id = address.address_id ");

        Map<String, Object> params = new HashMap<>();

        StringBuilder whereBuild = new StringBuilder("WHERE 1=1 ");
        //only select staff
        Boolean onlyStaff = Boolean.FALSE;
        whereBuild.append("AND acc.owner = :isOwner ");
        params.put("isOwner", onlyStaff);

        whereBuild.append("AND acc.account_id = :accountId ");
        params.put("accountId", id);

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .append(whereBuild)
                .toString();

        Query query = entityManager.createNativeQuery(queryBuild, SQL_RESULT_SET_MAPPING);
        params.forEach(query::setParameter);
        try {
            return AccountResponse.of((AccountDTO) query.getSingleResult());
        } catch (NoResultException queryResult) {
            throw new BusinessException(USER_NOT_FOUND, "Không tìm thấy tài khoản: account_id :" + id);
        }
    }

    @Override
    public List<String> roles() {
        List<String> rolesName = new ArrayList<>();
        var roles = roleRepository.getAll();
        roles.forEach(e -> {
            rolesName.add(e.getName().name().replace("ROLE_", ""));
        });
        return rolesName;
    }
}
