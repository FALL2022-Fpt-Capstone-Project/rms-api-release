package vn.com.fpt.service.assets;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.entity.authentication.Role;
import vn.com.fpt.repositories.AccountRepository;
import vn.com.fpt.repositories.PermissionDetailRepository;
import vn.com.fpt.repositories.PermissionRepository;
import vn.com.fpt.repositories.RoleRepository;
import vn.com.fpt.requests.LoginRequest;
import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.responses.AccountResponse;
import vn.com.fpt.security.ERole;
import vn.com.fpt.security.configs.JwtUtils;
import vn.com.fpt.security.domain.AccountAuthenticationDetail;
import vn.com.fpt.service.authentication.AuthenticationService;
import vn.com.fpt.service.authentication.AuthenticationServiceImpl;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthenticationServiceImplTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtUtils jwtUtils;

    private AuthenticationService authenticationService;

    @Mock
    private PermissionRepository permissionRepository;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationServiceImpl(authenticationManager, accountRepository, permissionRepository, roleRepository, jwtUtils);
    }

    @Test
    void testLogin() {
        //mock result
        LoginRequest loginRequest = mock(LoginRequest.class);
        when(loginRequest.getUserName()).thenReturn("username");
        when(loginRequest.getPassword()).thenReturn("password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        AccountAuthenticationDetail account = new AccountAuthenticationDetail(1l, "username", "password", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        account.setDeactivate(false);
        when(authentication.getPrincipal()).thenReturn(account);
        ResponseCookie responseCookie = mock(ResponseCookie.class);
        when(responseCookie.getValue()).thenReturn("token");
        when(jwtUtils.generateJwtCookie(account)).thenReturn(responseCookie);
        //run test
        AccountResponse result = authenticationService.login(loginRequest);
        //verify
        Assertions.assertEquals("token", result.getToken());
    }

    @Test
    void testLoginException() {
        //mock result
        LoginRequest loginRequest = mock(LoginRequest.class);
        when(loginRequest.getUserName()).thenReturn("username");
        when(loginRequest.getPassword()).thenReturn("password");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        AccountAuthenticationDetail account = new AccountAuthenticationDetail(1l, "username", "password", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        account.setDeactivate(true);
        when(authentication.getPrincipal()).thenReturn(account);
        //run test
        DisabledException disabledException = Assertions.assertThrows(DisabledException.class, () -> authenticationService.login(loginRequest));
        //verify
        Assertions.assertEquals("Tài khoản: " + account.getUserName(), disabledException.getMessage());
    }

    @Test
    void testRegister() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("Thanh");
        registerRequest.setAddressCity("Ha Noi");
        registerRequest.setAddressDistrict("Cau giay");
        registerRequest.setAddressWards("Mai Dich");
        registerRequest.setAddressMoreDetail("Ngo 32");
        registerRequest.setRoles("admin");
        registerRequest.setPassword("password");
        Long operator = 1L;
        //mock result
        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(Role.builder().name(ERole.ROLE_ADMIN).build()));
        when(accountRepository.findAccountByUserName(registerRequest.getUserName())).thenReturn(Optional.ofNullable(null));
        Account account = new Account();
        account.setId(1L);
        account.setFullName("fullName");
        account.setPhoneNumber("phone");
        account.setDeactivate(true);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(Role.builder().name(ERole.ROLE_ADMIN).build());
        account.setRoles(roleSet);
        Address address = Address.builder().addressCity("Ha Noi")
                .addressDistrict("Cau Giay")
                .addressWards("Mai dich")
                .addressMoreDetails("Ngo 32")
                .build();
        account.setAddress(address);
        address.setCreatedAt(new Date());

        when(accountRepository.save(any(Account.class))).thenReturn(account);
        //run test
        AccountResponse result = authenticationService.register(registerRequest, operator);
        //veryfi
        Assertions.assertEquals(1L, result.getAccountId());
    }

    @Test
    void testRegisterException() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("Thanh");
        Long operator = 1L;
        //mock result
        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(Role.builder().name(ERole.ROLE_ADMIN).build()));
        when(accountRepository.findAccountByUserName(registerRequest.getUserName())).thenReturn(Optional.ofNullable(new Account()));
        //run test
        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> authenticationService.register(registerRequest, operator));
        //verify
        Assertions.assertEquals("Tài khoản: " + registerRequest.getUserName(), businessException.getMessage());
    }

    @Test
    void testLogout() {
        Assertions.assertEquals("Đăng xuất thành công", authenticationService.logout());
    }

    @Test
    void testRoleChecker() {
        String strRoles = "admin";
        when(roleRepository.findByName(ERole.ROLE_ADMIN)).thenReturn(Optional.of(Role.builder().name(ERole.ROLE_ADMIN).build())).thenReturn(Optional.ofNullable(null));
        var result = authenticationService.roleChecker(strRoles);
        Assertions.assertNotNull(result);

        String strRoles2 = "staff";
        when(roleRepository.findByName(ERole.ROLE_STAFF)).thenReturn(Optional.of(Role.builder().name(ERole.ROLE_STAFF).build()));
        var result2 = authenticationService.roleChecker(strRoles2);
        Assertions.assertNotNull(result2);

        String strRoles3 = "default";
        var result3 = authenticationService.roleChecker(strRoles3);
        Assertions.assertNotNull(result3);

        Assertions.assertThrows(BusinessException.class, () -> authenticationService.roleChecker(strRoles));
    }
}
