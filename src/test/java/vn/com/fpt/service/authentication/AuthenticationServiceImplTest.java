package vn.com.fpt.service.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.Identity;
import vn.com.fpt.entity.RackRenters;
import vn.com.fpt.entity.Renters;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.entity.authentication.Role;
import vn.com.fpt.repositories.AccountRepository;
import vn.com.fpt.repositories.PermissionRepository;
import vn.com.fpt.repositories.RoleRepository;
import vn.com.fpt.requests.LoginRequest;
import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.security.ERole;
import vn.com.fpt.security.configs.JwtUtils;
import vn.com.fpt.security.domain.AccountAuthenticationDetail;

@ContextConfiguration(classes = {AuthenticationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AuthenticationServiceImplTest {
    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationServiceImpl authenticationServiceImpl;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private PermissionRepository permissionRepository;

    @MockBean
    private RoleRepository roleRepository;

    /**
     * Method under test: {@link AuthenticationServiceImpl#login(LoginRequest)}
     */
    @Test
    void testLogin() throws AuthenticationException {
        when(jwtUtils.getCleanJwtCookie()).thenReturn(null);
        when(authenticationManager.authenticate((Authentication) any())).thenThrow(new DisabledException("Msg"));
        assertThrows(DisabledException.class, () -> authenticationServiceImpl.login(new LoginRequest()));
        verify(jwtUtils).getCleanJwtCookie();
        verify(authenticationManager).authenticate((Authentication) any());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#login(LoginRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testLogin2() throws AuthenticationException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.http.ResponseCookie.getValue()" because the return value of "vn.com.fpt.security.configs.JwtUtils.generateJwtCookie(vn.com.fpt.security.domain.AccountAuthenticationDetail)" is null
        //       at vn.com.fpt.service.authentication.AuthenticationServiceImpl.login(AuthenticationServiceImpl.java:61)
        //   See https://diff.blue/R013 to resolve this issue.

        when(permissionRepository.findAllByAccountId((Long) any())).thenReturn(new ArrayList<>());
        when(jwtUtils.generateJwtCookie((AccountAuthenticationDetail) any())).thenReturn(null);
        when(jwtUtils.getCleanJwtCookie()).thenReturn(null);
        when(authenticationManager.authenticate((Authentication) any())).thenReturn(new TestingAuthenticationToken(
                new AccountAuthenticationDetail(123L, "janedoe", "iloveyou", new ArrayList<>()), "Credentials"));
        authenticationServiceImpl.login(new LoginRequest());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#login(LoginRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testLogin3() throws AuthenticationException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.security.authentication.DisabledException: Msg
        //       at vn.com.fpt.security.configs.JwtUtils.generateJwtCookie(JwtUtils.java:48)
        //       at vn.com.fpt.service.authentication.AuthenticationServiceImpl.login(AuthenticationServiceImpl.java:61)
        //   See https://diff.blue/R013 to resolve this issue.

        when(permissionRepository.findAllByAccountId((Long) any())).thenReturn(new ArrayList<>());
        when(jwtUtils.generateJwtCookie((AccountAuthenticationDetail) any())).thenThrow(new DisabledException("Msg"));
        when(jwtUtils.getCleanJwtCookie()).thenReturn(null);
        when(authenticationManager.authenticate((Authentication) any())).thenReturn(new TestingAuthenticationToken(
                new AccountAuthenticationDetail(123L, "janedoe", "iloveyou", new ArrayList<>()), "Credentials"));
        authenticationServiceImpl.login(new LoginRequest());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#login(LoginRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testLogin4() throws AuthenticationException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.security.domain.AccountAuthenticationDetail.isDeactivate()" because "account" is null
        //       at vn.com.fpt.service.authentication.AuthenticationServiceImpl.login(AuthenticationServiceImpl.java:54)
        //   See https://diff.blue/R013 to resolve this issue.

        when(permissionRepository.findAllByAccountId((Long) any())).thenReturn(new ArrayList<>());
        when(jwtUtils.generateJwtCookie((AccountAuthenticationDetail) any())).thenReturn(null);
        when(jwtUtils.getCleanJwtCookie()).thenReturn(null);
        when(authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken(null, "Credentials"));
        authenticationServiceImpl.login(new LoginRequest());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#login(LoginRequest)}
     */
    @Test
    void testLogin5() throws AuthenticationException {
        when(permissionRepository.findAllByAccountId((Long) any())).thenReturn(new ArrayList<>());
        when(jwtUtils.generateJwtCookie((AccountAuthenticationDetail) any())).thenReturn(null);
        when(jwtUtils.getCleanJwtCookie()).thenReturn(null);
        AccountAuthenticationDetail accountAuthenticationDetail = mock(AccountAuthenticationDetail.class);
        when(accountAuthenticationDetail.getUserName()).thenReturn("janedoe");
        when(accountAuthenticationDetail.isDeactivate()).thenReturn(true);
        when(accountAuthenticationDetail.getId()).thenReturn(123L);
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(
                accountAuthenticationDetail, "Credentials");

        when(authenticationManager.authenticate((Authentication) any())).thenReturn(testingAuthenticationToken);
        assertThrows(DisabledException.class, () -> authenticationServiceImpl.login(new LoginRequest()));
        verify(jwtUtils).getCleanJwtCookie();
        verify(authenticationManager).authenticate((Authentication) any());
        verify(accountAuthenticationDetail).isDeactivate();
        verify(accountAuthenticationDetail).getUserName();
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#login(LoginRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testLogin6() throws AuthenticationException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.security.authentication.DisabledException: Msg
        //       at vn.com.fpt.entity.authentication.Account.getUserName(Account.java:45)
        //       at vn.com.fpt.service.authentication.AuthenticationServiceImpl.login(AuthenticationServiceImpl.java:54)
        //   See https://diff.blue/R013 to resolve this issue.

        when(permissionRepository.findAllByAccountId((Long) any())).thenReturn(new ArrayList<>());
        when(jwtUtils.generateJwtCookie((AccountAuthenticationDetail) any())).thenReturn(null);
        when(jwtUtils.getCleanJwtCookie()).thenReturn(null);
        AccountAuthenticationDetail accountAuthenticationDetail = mock(AccountAuthenticationDetail.class);
        when(accountAuthenticationDetail.getUserName()).thenThrow(new DisabledException("Msg"));
        when(accountAuthenticationDetail.isDeactivate()).thenReturn(true);
        when(accountAuthenticationDetail.getId()).thenReturn(123L);
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(
                accountAuthenticationDetail, "Credentials");

        when(authenticationManager.authenticate((Authentication) any())).thenReturn(testingAuthenticationToken);
        authenticationServiceImpl.login(new LoginRequest());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#login(LoginRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testLogin7() throws AuthenticationException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getPrincipal()" because "authentication" is null
        //       at vn.com.fpt.service.authentication.AuthenticationServiceImpl.login(AuthenticationServiceImpl.java:52)
        //   See https://diff.blue/R013 to resolve this issue.

        when(permissionRepository.findAllByAccountId((Long) any())).thenReturn(new ArrayList<>());
        when(jwtUtils.generateJwtCookie((AccountAuthenticationDetail) any())).thenReturn(null);
        when(jwtUtils.getCleanJwtCookie()).thenReturn(null);
        when(authenticationManager.authenticate((Authentication) any())).thenReturn(null);
        authenticationServiceImpl.login(new LoginRequest());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#login(LoginRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testLogin8() throws AuthenticationException {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.requests.LoginRequest.getUserName()" because "loginRequest" is null
        //       at vn.com.fpt.service.authentication.AuthenticationServiceImpl.login(AuthenticationServiceImpl.java:50)
        //   See https://diff.blue/R013 to resolve this issue.

        when(permissionRepository.findAllByAccountId((Long) any())).thenReturn(new ArrayList<>());
        when(jwtUtils.generateJwtCookie((AccountAuthenticationDetail) any())).thenReturn(null);
        when(jwtUtils.getCleanJwtCookie()).thenReturn(null);
        AccountAuthenticationDetail accountAuthenticationDetail = mock(AccountAuthenticationDetail.class);
        when(accountAuthenticationDetail.getUserName()).thenReturn("janedoe");
        when(accountAuthenticationDetail.isDeactivate()).thenReturn(true);
        when(accountAuthenticationDetail.getId()).thenReturn(123L);
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(
                accountAuthenticationDetail, "Credentials");

        when(authenticationManager.authenticate((Authentication) any())).thenReturn(testingAuthenticationToken);
        authenticationServiceImpl.login(null);
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#register(RegisterRequest, Long)}
     */
    @Test
    void testRegister() {
        Account account = new Account();
        account.setAddress(new Address());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        account.setCreatedBy(1L);
        account.setDeactivate(true);
        account.setFullName("Dr Jane Doe");
        account.setGender(true);
        account.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        account.setModifiedBy(1L);
        account.setOwner(true);
        account.setPassword("iloveyou");
        account.setPhoneNumber("4105551212");
        account.setRoles(new HashSet<>());
        account.setUserName("janedoe");

        RackRenters rackRenters = new RackRenters();
        rackRenters.setAddress(new Address());
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setCreatedBy(1L);
        rackRenters.setEmail("jane.doe@example.org");
        rackRenters.setGender(true);
        rackRenters.setId(123L);
        rackRenters.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setModifiedBy(1L);
        rackRenters.setNote("Note");
        rackRenters.setPhoneNumber("4105551212");
        rackRenters.setRackRenterFullName("Dr Jane Doe");

        Renters renters = new Renters();
        renters.setAddress(new Address());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setCreatedBy(1L);
        renters.setEmail("jane.doe@example.org");
        renters.setGender(true);
        renters.setId(123L);
        renters.setIdentityNumber("42");
        renters.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setModifiedBy(1L);
        renters.setPhoneNumber("4105551212");
        renters.setRenterFullName("Dr Jane Doe");
        renters.setRenterIdentity(new Identity());
        renters.setRepresent(true);
        renters.setRoomId(123L);

        Address address = new Address();
        address.setAccount(account);
        address.setAddressCity("42 Main St");
        address.setAddressDistrict("42 Main St");
        address.setAddressMoreDetails("42 Main St");
        address.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        address.setCreatedBy(1L);
        address.setId(123L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        address.setModifiedBy(1L);
        address.setRackRenters(rackRenters);
        address.setRenters(renters);

        Account account1 = new Account();
        account1.setAddress(address);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account1.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        account1.setCreatedBy(1L);
        account1.setDeactivate(true);
        account1.setFullName("Dr Jane Doe");
        account1.setGender(true);
        account1.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account1.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        account1.setModifiedBy(1L);
        account1.setOwner(true);
        account1.setPassword("iloveyou");
        account1.setPhoneNumber("4105551212");
        account1.setRoles(new HashSet<>());
        account1.setUserName("janedoe");

        Account account2 = new Account();
        account2.setAddress(new Address());
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account2.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        account2.setCreatedBy(1L);
        account2.setDeactivate(true);
        account2.setFullName("Dr Jane Doe");
        account2.setGender(true);
        account2.setId(123L);
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account2.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        account2.setModifiedBy(1L);
        account2.setOwner(true);
        account2.setPassword("iloveyou");
        account2.setPhoneNumber("4105551212");
        account2.setRoles(new HashSet<>());
        account2.setUserName("janedoe");

        RackRenters rackRenters1 = new RackRenters();
        rackRenters1.setAddress(new Address());
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setCreatedBy(1L);
        rackRenters1.setEmail("jane.doe@example.org");
        rackRenters1.setGender(true);
        rackRenters1.setId(123L);
        rackRenters1.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setModifiedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setModifiedBy(1L);
        rackRenters1.setNote("Note");
        rackRenters1.setPhoneNumber("4105551212");
        rackRenters1.setRackRenterFullName("Dr Jane Doe");

        Renters renters1 = new Renters();
        renters1.setAddress(new Address());
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setCreatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setCreatedBy(1L);
        renters1.setEmail("jane.doe@example.org");
        renters1.setGender(true);
        renters1.setId(123L);
        renters1.setIdentityNumber("42");
        renters1.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setModifiedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setModifiedBy(1L);
        renters1.setPhoneNumber("4105551212");
        renters1.setRenterFullName("Dr Jane Doe");
        renters1.setRenterIdentity(new Identity());
        renters1.setRepresent(true);
        renters1.setRoomId(123L);

        Address address1 = new Address();
        address1.setAccount(account2);
        address1.setAddressCity("42 Main St");
        address1.setAddressDistrict("42 Main St");
        address1.setAddressMoreDetails("42 Main St");
        address1.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setCreatedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setCreatedBy(1L);
        address1.setId(123L);
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setModifiedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setModifiedBy(1L);
        address1.setRackRenters(rackRenters1);
        address1.setRenters(renters1);

        RackRenters rackRenters2 = new RackRenters();
        rackRenters2.setAddress(address1);
        LocalDateTime atStartOfDayResult18 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters2.setCreatedAt(Date.from(atStartOfDayResult18.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters2.setCreatedBy(1L);
        rackRenters2.setEmail("jane.doe@example.org");
        rackRenters2.setGender(true);
        rackRenters2.setId(123L);
        rackRenters2.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult19 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters2.setModifiedAt(Date.from(atStartOfDayResult19.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters2.setModifiedBy(1L);
        rackRenters2.setNote("Note");
        rackRenters2.setPhoneNumber("4105551212");
        rackRenters2.setRackRenterFullName("Dr Jane Doe");

        Account account3 = new Account();
        account3.setAddress(new Address());
        LocalDateTime atStartOfDayResult20 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account3.setCreatedAt(Date.from(atStartOfDayResult20.atZone(ZoneId.of("UTC")).toInstant()));
        account3.setCreatedBy(1L);
        account3.setDeactivate(true);
        account3.setFullName("Dr Jane Doe");
        account3.setGender(true);
        account3.setId(123L);
        LocalDateTime atStartOfDayResult21 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account3.setModifiedAt(Date.from(atStartOfDayResult21.atZone(ZoneId.of("UTC")).toInstant()));
        account3.setModifiedBy(1L);
        account3.setOwner(true);
        account3.setPassword("iloveyou");
        account3.setPhoneNumber("4105551212");
        account3.setRoles(new HashSet<>());
        account3.setUserName("janedoe");

        RackRenters rackRenters3 = new RackRenters();
        rackRenters3.setAddress(new Address());
        LocalDateTime atStartOfDayResult22 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters3.setCreatedAt(Date.from(atStartOfDayResult22.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters3.setCreatedBy(1L);
        rackRenters3.setEmail("jane.doe@example.org");
        rackRenters3.setGender(true);
        rackRenters3.setId(123L);
        rackRenters3.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult23 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters3.setModifiedAt(Date.from(atStartOfDayResult23.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters3.setModifiedBy(1L);
        rackRenters3.setNote("Note");
        rackRenters3.setPhoneNumber("4105551212");
        rackRenters3.setRackRenterFullName("Dr Jane Doe");

        Renters renters2 = new Renters();
        renters2.setAddress(new Address());
        LocalDateTime atStartOfDayResult24 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setCreatedAt(Date.from(atStartOfDayResult24.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setCreatedBy(1L);
        renters2.setEmail("jane.doe@example.org");
        renters2.setGender(true);
        renters2.setId(123L);
        renters2.setIdentityNumber("42");
        renters2.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult25 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setModifiedAt(Date.from(atStartOfDayResult25.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setModifiedBy(1L);
        renters2.setPhoneNumber("4105551212");
        renters2.setRenterFullName("Dr Jane Doe");
        renters2.setRenterIdentity(new Identity());
        renters2.setRepresent(true);
        renters2.setRoomId(123L);

        Address address2 = new Address();
        address2.setAccount(account3);
        address2.setAddressCity("42 Main St");
        address2.setAddressDistrict("42 Main St");
        address2.setAddressMoreDetails("42 Main St");
        address2.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult26 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setCreatedAt(Date.from(atStartOfDayResult26.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setCreatedBy(1L);
        address2.setId(123L);
        LocalDateTime atStartOfDayResult27 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setModifiedAt(Date.from(atStartOfDayResult27.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setModifiedBy(1L);
        address2.setRackRenters(rackRenters3);
        address2.setRenters(renters2);

        Renters renters3 = new Renters();
        renters3.setAddress(new Address());
        LocalDateTime atStartOfDayResult28 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters3.setCreatedAt(Date.from(atStartOfDayResult28.atZone(ZoneId.of("UTC")).toInstant()));
        renters3.setCreatedBy(1L);
        renters3.setEmail("jane.doe@example.org");
        renters3.setGender(true);
        renters3.setId(123L);
        renters3.setIdentityNumber("42");
        renters3.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult29 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters3.setModifiedAt(Date.from(atStartOfDayResult29.atZone(ZoneId.of("UTC")).toInstant()));
        renters3.setModifiedBy(1L);
        renters3.setPhoneNumber("4105551212");
        renters3.setRenterFullName("Dr Jane Doe");
        renters3.setRenterIdentity(new Identity());
        renters3.setRepresent(true);
        renters3.setRoomId(123L);

        Identity identity = new Identity();
        LocalDateTime atStartOfDayResult30 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setCreatedAt(Date.from(atStartOfDayResult30.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setCreatedBy(1L);
        identity.setId(123L);
        identity.setIdentityBackImg("Identity Back Img");
        identity.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult31 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setModifiedAt(Date.from(atStartOfDayResult31.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setModifiedBy(1L);
        identity.setRenters(renters3);

        Renters renters4 = new Renters();
        renters4.setAddress(address2);
        LocalDateTime atStartOfDayResult32 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters4.setCreatedAt(Date.from(atStartOfDayResult32.atZone(ZoneId.of("UTC")).toInstant()));
        renters4.setCreatedBy(1L);
        renters4.setEmail("jane.doe@example.org");
        renters4.setGender(true);
        renters4.setId(123L);
        renters4.setIdentityNumber("42");
        renters4.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult33 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters4.setModifiedAt(Date.from(atStartOfDayResult33.atZone(ZoneId.of("UTC")).toInstant()));
        renters4.setModifiedBy(1L);
        renters4.setPhoneNumber("4105551212");
        renters4.setRenterFullName("Dr Jane Doe");
        renters4.setRenterIdentity(identity);
        renters4.setRepresent(true);
        renters4.setRoomId(123L);

        Address address3 = new Address();
        address3.setAccount(account1);
        address3.setAddressCity("42 Main St");
        address3.setAddressDistrict("42 Main St");
        address3.setAddressMoreDetails("42 Main St");
        address3.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult34 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setCreatedAt(Date.from(atStartOfDayResult34.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setCreatedBy(1L);
        address3.setId(123L);
        LocalDateTime atStartOfDayResult35 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setModifiedAt(Date.from(atStartOfDayResult35.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setModifiedBy(1L);
        address3.setRackRenters(rackRenters2);
        address3.setRenters(renters4);

        Account account4 = new Account();
        account4.setAddress(address3);
        LocalDateTime atStartOfDayResult36 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account4.setCreatedAt(Date.from(atStartOfDayResult36.atZone(ZoneId.of("UTC")).toInstant()));
        account4.setCreatedBy(1L);
        account4.setDeactivate(true);
        account4.setFullName("Dr Jane Doe");
        account4.setGender(true);
        account4.setId(123L);
        LocalDateTime atStartOfDayResult37 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account4.setModifiedAt(Date.from(atStartOfDayResult37.atZone(ZoneId.of("UTC")).toInstant()));
        account4.setModifiedBy(1L);
        account4.setOwner(true);
        account4.setPassword("iloveyou");
        account4.setPhoneNumber("4105551212");
        account4.setRoles(new HashSet<>());
        account4.setUserName("janedoe");

        Address address4 = new Address();
        address4.setAccount(new Account());
        address4.setAddressCity("42 Main St");
        address4.setAddressDistrict("42 Main St");
        address4.setAddressMoreDetails("42 Main St");
        address4.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult38 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setCreatedAt(Date.from(atStartOfDayResult38.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setCreatedBy(1L);
        address4.setId(123L);
        LocalDateTime atStartOfDayResult39 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setModifiedAt(Date.from(atStartOfDayResult39.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setModifiedBy(1L);
        address4.setRackRenters(new RackRenters());
        address4.setRenters(new Renters());

        Account account5 = new Account();
        account5.setAddress(address4);
        LocalDateTime atStartOfDayResult40 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account5.setCreatedAt(Date.from(atStartOfDayResult40.atZone(ZoneId.of("UTC")).toInstant()));
        account5.setCreatedBy(1L);
        account5.setDeactivate(true);
        account5.setFullName("Dr Jane Doe");
        account5.setGender(true);
        account5.setId(123L);
        LocalDateTime atStartOfDayResult41 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account5.setModifiedAt(Date.from(atStartOfDayResult41.atZone(ZoneId.of("UTC")).toInstant()));
        account5.setModifiedBy(1L);
        account5.setOwner(true);
        account5.setPassword("iloveyou");
        account5.setPhoneNumber("4105551212");
        account5.setRoles(new HashSet<>());
        account5.setUserName("janedoe");

        Address address5 = new Address();
        address5.setAccount(new Account());
        address5.setAddressCity("42 Main St");
        address5.setAddressDistrict("42 Main St");
        address5.setAddressMoreDetails("42 Main St");
        address5.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult42 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address5.setCreatedAt(Date.from(atStartOfDayResult42.atZone(ZoneId.of("UTC")).toInstant()));
        address5.setCreatedBy(1L);
        address5.setId(123L);
        LocalDateTime atStartOfDayResult43 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address5.setModifiedAt(Date.from(atStartOfDayResult43.atZone(ZoneId.of("UTC")).toInstant()));
        address5.setModifiedBy(1L);
        address5.setRackRenters(new RackRenters());
        address5.setRenters(new Renters());

        RackRenters rackRenters4 = new RackRenters();
        rackRenters4.setAddress(address5);
        LocalDateTime atStartOfDayResult44 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters4.setCreatedAt(Date.from(atStartOfDayResult44.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters4.setCreatedBy(1L);
        rackRenters4.setEmail("jane.doe@example.org");
        rackRenters4.setGender(true);
        rackRenters4.setId(123L);
        rackRenters4.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult45 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters4.setModifiedAt(Date.from(atStartOfDayResult45.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters4.setModifiedBy(1L);
        rackRenters4.setNote("Note");
        rackRenters4.setPhoneNumber("4105551212");
        rackRenters4.setRackRenterFullName("Dr Jane Doe");

        Address address6 = new Address();
        address6.setAccount(new Account());
        address6.setAddressCity("42 Main St");
        address6.setAddressDistrict("42 Main St");
        address6.setAddressMoreDetails("42 Main St");
        address6.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult46 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address6.setCreatedAt(Date.from(atStartOfDayResult46.atZone(ZoneId.of("UTC")).toInstant()));
        address6.setCreatedBy(1L);
        address6.setId(123L);
        LocalDateTime atStartOfDayResult47 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address6.setModifiedAt(Date.from(atStartOfDayResult47.atZone(ZoneId.of("UTC")).toInstant()));
        address6.setModifiedBy(1L);
        address6.setRackRenters(new RackRenters());
        address6.setRenters(new Renters());

        Identity identity1 = new Identity();
        LocalDateTime atStartOfDayResult48 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setCreatedAt(Date.from(atStartOfDayResult48.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setCreatedBy(1L);
        identity1.setId(123L);
        identity1.setIdentityBackImg("Identity Back Img");
        identity1.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult49 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setModifiedAt(Date.from(atStartOfDayResult49.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setModifiedBy(1L);
        identity1.setRenters(new Renters());

        Renters renters5 = new Renters();
        renters5.setAddress(address6);
        LocalDateTime atStartOfDayResult50 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters5.setCreatedAt(Date.from(atStartOfDayResult50.atZone(ZoneId.of("UTC")).toInstant()));
        renters5.setCreatedBy(1L);
        renters5.setEmail("jane.doe@example.org");
        renters5.setGender(true);
        renters5.setId(123L);
        renters5.setIdentityNumber("42");
        renters5.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult51 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters5.setModifiedAt(Date.from(atStartOfDayResult51.atZone(ZoneId.of("UTC")).toInstant()));
        renters5.setModifiedBy(1L);
        renters5.setPhoneNumber("4105551212");
        renters5.setRenterFullName("Dr Jane Doe");
        renters5.setRenterIdentity(identity1);
        renters5.setRepresent(true);
        renters5.setRoomId(123L);

        Address address7 = new Address();
        address7.setAccount(account5);
        address7.setAddressCity("42 Main St");
        address7.setAddressDistrict("42 Main St");
        address7.setAddressMoreDetails("42 Main St");
        address7.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult52 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address7.setCreatedAt(Date.from(atStartOfDayResult52.atZone(ZoneId.of("UTC")).toInstant()));
        address7.setCreatedBy(1L);
        address7.setId(123L);
        LocalDateTime atStartOfDayResult53 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address7.setModifiedAt(Date.from(atStartOfDayResult53.atZone(ZoneId.of("UTC")).toInstant()));
        address7.setModifiedBy(1L);
        address7.setRackRenters(rackRenters4);
        address7.setRenters(renters5);

        Account account6 = new Account();
        account6.setAddress(address7);
        LocalDateTime atStartOfDayResult54 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account6.setCreatedAt(Date.from(atStartOfDayResult54.atZone(ZoneId.of("UTC")).toInstant()));
        account6.setCreatedBy(1L);
        account6.setDeactivate(true);
        account6.setFullName("Dr Jane Doe");
        account6.setGender(true);
        account6.setId(123L);
        LocalDateTime atStartOfDayResult55 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account6.setModifiedAt(Date.from(atStartOfDayResult55.atZone(ZoneId.of("UTC")).toInstant()));
        account6.setModifiedBy(1L);
        account6.setOwner(true);
        account6.setPassword("iloveyou");
        account6.setPhoneNumber("4105551212");
        account6.setRoles(new HashSet<>());
        account6.setUserName("janedoe");
        Optional<Account> ofResult = Optional.of(account6);
        when(accountRepository.save((Account) any())).thenReturn(account4);
        when(accountRepository.findAccountByUserName((String) any())).thenReturn(ofResult);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setAddressCity("42 Main St");
        registerRequest.setAddressDistrict("42 Main St");
        registerRequest.setAddressMoreDetail("42 Main St");
        registerRequest.setAddressWards("42 Main St");
        registerRequest.setDeactivate(true);
        registerRequest.setFullName("Dr Jane Doe");
        registerRequest.setGender(true);
        registerRequest.setPassword("iloveyou");
        registerRequest.setPermission(new ArrayList<>());
        registerRequest.setPhoneNumber("4105551212");
        registerRequest.setRoles("Roles");
        registerRequest.setUserName("janedoe");
        assertThrows(BusinessException.class, () -> authenticationServiceImpl.register(registerRequest, 1L));
        verify(accountRepository).findAccountByUserName((String) any());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#logout()}
     */
    @Test
    void testLogout() {
        assertEquals("Đăng xuất thành công", authenticationServiceImpl.logout());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#roleChecker(String)}
     */
    @Test
    void testRoleChecker() {
        assertEquals(1, authenticationServiceImpl.roleChecker("Str Roles").size());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#roleChecker(String)}
     */
    @Test
    void testRoleChecker2() {
        Role role = new Role();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        role.setCreatedBy(1L);
        role.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        role.setModifiedBy(1L);
        role.setName(ERole.ROLE_STAFF);
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepository.findByName((ERole) any())).thenReturn(ofResult);
        assertEquals(1, authenticationServiceImpl.roleChecker("admin").size());
        verify(roleRepository).findByName((ERole) any());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#roleChecker(String)}
     */
    @Test
    void testRoleChecker3() {
        when(roleRepository.findByName((ERole) any())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> authenticationServiceImpl.roleChecker("admin"));
        verify(roleRepository).findByName((ERole) any());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#roleChecker(String)}
     */
    @Test
    void testRoleChecker4() {
        Role role = new Role();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        role.setCreatedBy(1L);
        role.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        role.setModifiedBy(1L);
        role.setName(ERole.ROLE_STAFF);
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepository.findByName((ERole) any())).thenReturn(ofResult);
        assertEquals(1, authenticationServiceImpl.roleChecker("staff").size());
        verify(roleRepository).findByName((ERole) any());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#roleChecker(String)}
     */
    @Test
    void testRoleChecker5() {
        when(roleRepository.findByName((ERole) any())).thenThrow(new DisabledException("admin"));
        assertThrows(DisabledException.class, () -> authenticationServiceImpl.roleChecker("admin"));
        verify(roleRepository).findByName((ERole) any());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#roleChecker(String)}
     */
    @Test
    void testRoleChecker6() {
        when(roleRepository.findByName((ERole) any())).thenThrow(new DisabledException("admin"));
        assertThrows(DisabledException.class, () -> authenticationServiceImpl.roleChecker("staff"));
        verify(roleRepository).findByName((ERole) any());
    }

    /**
     * Method under test: {@link AuthenticationServiceImpl#roleChecker(String)}
     */
    @Test
    void testRoleChecker7() {
        when(roleRepository.findByName((ERole) any())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> authenticationServiceImpl.roleChecker("staff"));
        verify(roleRepository).findByName((ERole) any());
    }
}

