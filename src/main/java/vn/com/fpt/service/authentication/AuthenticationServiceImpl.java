package vn.com.fpt.service.authentication;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.entity.authentication.Role;
import vn.com.fpt.repositories.AccountRepository;
import vn.com.fpt.repositories.RoleRepository;
import vn.com.fpt.requests.LoginRequest;
import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.responses.AccountResponse;
import vn.com.fpt.security.ERole;
import vn.com.fpt.security.configs.JwtUtils;
import vn.com.fpt.security.domain.AccountAuthenticationDetail;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static vn.com.fpt.constants.ErrorStatusConstants.EXISTED_ACCOUNT;
import static vn.com.fpt.constants.ErrorStatusConstants.INVALID_ROLE;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;

    private final AccountRepository accountRepository;

    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;

    @Override
    public AccountResponse login(LoginRequest loginRequest) {
        jwtUtils.getCleanJwtCookie();
        var authenticationInfo = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
        var authentication = authenticationManager.authenticate(authenticationInfo);
        var account = (AccountAuthenticationDetail) authentication.getPrincipal();

        if (account.isDeactivate()) throw new DisabledException("Tài khoản: " + account.getUserName());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return AccountResponse.of(account
                , authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet())
                , jwtUtils.generateJwtCookie(account).getValue());
    }

    @Override
    @Transactional
    public AccountResponse register(RegisterRequest registerRequest, Long operator) {
        if (accountRepository.findAccountByUserName(registerRequest.getUserName()).isPresent())
            throw new BusinessException(EXISTED_ACCOUNT, "Tài khoản: " + registerRequest.getUserName());
        Address address = Address.of(registerRequest);
        Set<Role> roles = roleChecker(registerRequest.getRoles());
        return AccountResponse.of(accountRepository.save(Account.add(registerRequest, address, roles, operator)));
    }

    @Override
    public String logout() {
        return "Đăng xuất thành công";
    }

    @SneakyThrows
    @Override
    public Set<Role> roleChecker(String strRoles) {
        Set<Role> roles = new HashSet<>();
            switch (strRoles) {
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElse(null);
                    if (!Objects.isNull(adminRole)) roles.add(adminRole);
                    break;
                case "staff":
                    Role staffRole = roleRepository.findByName(ERole.ROLE_STAFF).orElse(null);
                    if (!Objects.isNull(staffRole)) roles.add(staffRole);
                    break;
                default:
                    roles.add(new Role());
            }
        if (roles.isEmpty()) throw new BusinessException(INVALID_ROLE, "Quyền: " + strRoles);
        return roles;
    }

}
