package vn.com.fpt.service.staff;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.engine.spi.SessionDelegatorBaseImpl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Permission;
import vn.com.fpt.entity.authentication.Role;
import vn.com.fpt.repositories.AccountRepository;
import vn.com.fpt.repositories.AddressRepository;
import vn.com.fpt.repositories.PermissionRepository;
import vn.com.fpt.repositories.RoleRepository;
import vn.com.fpt.requests.AddPermission;
import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.responses.AccountResponse;
import vn.com.fpt.security.ERole;
import vn.com.fpt.security.configs.JwtUtils;
import vn.com.fpt.service.authentication.AuthenticationService;
import vn.com.fpt.service.authentication.AuthenticationServiceImpl;

@ContextConfiguration(classes = {StaffServiceImpl.class})
@ExtendWith(SpringExtension.class)
class StaffServiceImplTest {
    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private PermissionRepository permissionRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private StaffServiceImpl staffServiceImpl;

    /**
     * Method under test: {@link StaffServiceImpl#addStaff(RegisterRequest, Long)}
     */
    @Test
    void testAddStaff() {
        AccountResponse accountResponse = new AccountResponse();
        when(authenticationService.register((RegisterRequest) any(), (Long) any())).thenReturn(accountResponse);

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
        assertSame(accountResponse, staffServiceImpl.addStaff(registerRequest, 1L));
        verify(authenticationService).register((RegisterRequest) any(), (Long) any());
    }

    /**
     * Method under test: {@link StaffServiceImpl#addStaff(RegisterRequest, Long)}
     */
    @Test
    void testAddStaff2() {
        when(authenticationService.register((RegisterRequest) any(), (Long) any()))
                .thenThrow(new BusinessException("Msg"));

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
        assertThrows(BusinessException.class, () -> staffServiceImpl.addStaff(registerRequest, 1L));
        verify(authenticationService).register((RegisterRequest) any(), (Long) any());
    }

    /**
     * Method under test: {@link StaffServiceImpl#updateStaff(Long, RegisterRequest, Long, Date)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateStaff() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: Source must not be null
        //       at vn.com.fpt.responses.AccountResponse.of(AccountResponse.java:82)
        //       at vn.com.fpt.service.staff.StaffServiceImpl.updateStaff(StaffServiceImpl.java:91)
        //   See https://diff.blue/R013 to resolve this issue.

        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
        AccountRepository accountRepository = mock(AccountRepository.class);
        PermissionRepository permissionRepository = mock(PermissionRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        AuthenticationServiceImpl accountService = new AuthenticationServiceImpl(authenticationManager, accountRepository,
                permissionRepository, roleRepository, new JwtUtils());

        AccountRepository accountRepository1 = mock(AccountRepository.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        SessionDelegatorBaseImpl delegate = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate1 = new SessionDelegatorBaseImpl(delegate,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate2 = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate3 = new SessionDelegatorBaseImpl(delegate1,
                new SessionDelegatorBaseImpl(delegate2, new SessionDelegatorBaseImpl(null, null)));

        SessionDelegatorBaseImpl delegate4 = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate5 = new SessionDelegatorBaseImpl(delegate4,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate6 = new SessionDelegatorBaseImpl(null, null);

        StaffServiceImpl staffServiceImpl = new StaffServiceImpl(accountService, accountRepository1, addressRepository,
                new SessionDelegatorBaseImpl(delegate3,
                        new SessionDelegatorBaseImpl(delegate5,
                                new SessionDelegatorBaseImpl(delegate6, new SessionDelegatorBaseImpl(null, null)))),
                mock(RoleRepository.class), mock(PermissionRepository.class));

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
        ZoneId zone = ZoneId.of("UTC");
        staffServiceImpl.updateStaff(123L, registerRequest, 1L,
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(zone).toInstant()));
    }

    /**
     * Method under test: {@link StaffServiceImpl#listStaff(String, String, String, String, Boolean, String, String, List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListStaff() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at java.util.Objects.requireNonNull(Objects.java:208)
        //       at vn.com.fpt.service.staff.StaffServiceImpl.listStaff(StaffServiceImpl.java:189)
        //   See https://diff.blue/R013 to resolve this issue.

        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
        AccountRepository accountRepository = mock(AccountRepository.class);
        PermissionRepository permissionRepository = mock(PermissionRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        AuthenticationServiceImpl accountService = new AuthenticationServiceImpl(authenticationManager, accountRepository,
                permissionRepository, roleRepository, new JwtUtils());

        AccountRepository accountRepository1 = mock(AccountRepository.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        SessionDelegatorBaseImpl delegate = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate1 = new SessionDelegatorBaseImpl(delegate,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate2 = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate3 = new SessionDelegatorBaseImpl(delegate1,
                new SessionDelegatorBaseImpl(delegate2, new SessionDelegatorBaseImpl(null, null)));

        SessionDelegatorBaseImpl delegate4 = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate5 = new SessionDelegatorBaseImpl(delegate4,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate6 = new SessionDelegatorBaseImpl(null, null);

        StaffServiceImpl staffServiceImpl = new StaffServiceImpl(accountService, accountRepository1, addressRepository,
                new SessionDelegatorBaseImpl(delegate3,
                        new SessionDelegatorBaseImpl(delegate5,
                                new SessionDelegatorBaseImpl(delegate6, new SessionDelegatorBaseImpl(null, null)))),
                mock(RoleRepository.class), mock(PermissionRepository.class));
        staffServiceImpl.listStaff("Role", "Order", "2020-03-01", "2020-03-01", true, "Name", "janedoe",
                new ArrayList<>());
    }

    /**
     * Method under test: {@link StaffServiceImpl#staff(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testStaff() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at java.util.Objects.requireNonNull(Objects.java:208)
        //       at vn.com.fpt.service.staff.StaffServiceImpl.staff(StaffServiceImpl.java:236)
        //   See https://diff.blue/R013 to resolve this issue.

        ProviderManager authenticationManager = new ProviderManager(new ArrayList<>());
        AccountRepository accountRepository = mock(AccountRepository.class);
        PermissionRepository permissionRepository = mock(PermissionRepository.class);
        RoleRepository roleRepository = mock(RoleRepository.class);
        AuthenticationServiceImpl accountService = new AuthenticationServiceImpl(authenticationManager, accountRepository,
                permissionRepository, roleRepository, new JwtUtils());

        AccountRepository accountRepository1 = mock(AccountRepository.class);
        AddressRepository addressRepository = mock(AddressRepository.class);
        SessionDelegatorBaseImpl delegate = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate1 = new SessionDelegatorBaseImpl(delegate,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate2 = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate3 = new SessionDelegatorBaseImpl(delegate1,
                new SessionDelegatorBaseImpl(delegate2, new SessionDelegatorBaseImpl(null, null)));

        SessionDelegatorBaseImpl delegate4 = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate5 = new SessionDelegatorBaseImpl(delegate4,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate6 = new SessionDelegatorBaseImpl(null, null);

        (new StaffServiceImpl(accountService, accountRepository1, addressRepository,
                new SessionDelegatorBaseImpl(delegate3,
                        new SessionDelegatorBaseImpl(delegate5,
                                new SessionDelegatorBaseImpl(delegate6, new SessionDelegatorBaseImpl(null, null)))),
                mock(RoleRepository.class), mock(PermissionRepository.class))).staff(123L);
    }

    /**
     * Method under test: {@link StaffServiceImpl#roles()}
     */
    @Test
    void testRoles() {
        when(roleRepository.getAll()).thenReturn(new ArrayList<>());
        assertTrue(staffServiceImpl.roles().isEmpty());
        verify(roleRepository).getAll();
    }

    /**
     * Method under test: {@link StaffServiceImpl#roles()}
     */
    @Test
    void testRoles2() {
        Role role = new Role();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        role.setCreatedBy(1L);
        role.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        role.setModifiedBy(1L);
        role.setName(ERole.ROLE_STAFF);

        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(role);
        when(roleRepository.getAll()).thenReturn(roleList);
        List<String> actualRolesResult = staffServiceImpl.roles();
        assertEquals(1, actualRolesResult.size());
        assertEquals("STAFF", actualRolesResult.get(0));
        verify(roleRepository).getAll();
    }

    /**
     * Method under test: {@link StaffServiceImpl#roles()}
     */
    @Test
    void testRoles3() {
        Role role = new Role();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        role.setCreatedBy(1L);
        role.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        role.setModifiedBy(1L);
        role.setName(ERole.ROLE_STAFF);

        Role role1 = new Role();
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        role1.setCreatedBy(1L);
        role1.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        role1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        role1.setModifiedBy(1L);
        role1.setName(ERole.ROLE_STAFF);

        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(role1);
        roleList.add(role);
        when(roleRepository.getAll()).thenReturn(roleList);
        List<String> actualRolesResult = staffServiceImpl.roles();
        assertEquals(2, actualRolesResult.size());
        assertEquals("STAFF", actualRolesResult.get(0));
        assertEquals("STAFF", actualRolesResult.get(1));
        verify(roleRepository).getAll();
    }

    /**
     * Method under test: {@link StaffServiceImpl#roles()}
     */
    @Test
    void testRoles4() {
        when(roleRepository.getAll()).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> staffServiceImpl.roles());
        verify(roleRepository).getAll();
    }

    /**
     * Method under test: {@link StaffServiceImpl#addPermission(AddPermission, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddPermission() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Collection.stream()" because "that" is null
        //       at vn.com.fpt.service.staff.StaffServiceImpl.addPermission(StaffServiceImpl.java:256)
        //   See https://diff.blue/R013 to resolve this issue.

        staffServiceImpl.addPermission(new AddPermission(), 1L);
    }

    /**
     * Method under test: {@link StaffServiceImpl#addPermission(AddPermission, Long)}
     */
    @Test
    void testAddPermission2() {
        ArrayList<Permission> permissionList = new ArrayList<>();
        when(permissionRepository.saveAll((Iterable<Permission>) any())).thenReturn(permissionList);

        AddPermission addPermission = new AddPermission();
        addPermission.setPermissionId(new ArrayList<>());
        List<Permission> actualAddPermissionResult = staffServiceImpl.addPermission(addPermission, 1L);
        assertSame(permissionList, actualAddPermissionResult);
        assertTrue(actualAddPermissionResult.isEmpty());
        verify(permissionRepository).saveAll((Iterable<Permission>) any());
    }

    /**
     * Method under test: {@link StaffServiceImpl#addPermission(AddPermission, Long)}
     */
    @Test
    void testAddPermission3() {
        ArrayList<Permission> permissionList = new ArrayList<>();
        when(permissionRepository.saveAll((Iterable<Permission>) any())).thenReturn(permissionList);

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);

        AddPermission addPermission = new AddPermission();
        addPermission.setPermissionId(resultLongList);
        List<Permission> actualAddPermissionResult = staffServiceImpl.addPermission(addPermission, 1L);
        assertSame(permissionList, actualAddPermissionResult);
        assertTrue(actualAddPermissionResult.isEmpty());
        verify(permissionRepository).saveAll((Iterable<Permission>) any());
    }

    /**
     * Method under test: {@link StaffServiceImpl#addPermission(AddPermission, Long)}
     */
    @Test
    void testAddPermission4() {
        ArrayList<Permission> permissionList = new ArrayList<>();
        when(permissionRepository.saveAll((Iterable<Permission>) any())).thenReturn(permissionList);

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);
        resultLongList.add(1L);

        AddPermission addPermission = new AddPermission();
        addPermission.setPermissionId(resultLongList);
        List<Permission> actualAddPermissionResult = staffServiceImpl.addPermission(addPermission, 1L);
        assertSame(permissionList, actualAddPermissionResult);
        assertTrue(actualAddPermissionResult.isEmpty());
        verify(permissionRepository).saveAll((Iterable<Permission>) any());
    }

    /**
     * Method under test: {@link StaffServiceImpl#addPermission(AddPermission, Long)}
     */
    @Test
    void testAddPermission5() {
        when(permissionRepository.saveAll((Iterable<Permission>) any())).thenThrow(new BusinessException("Msg"));

        AddPermission addPermission = new AddPermission();
        addPermission.setPermissionId(new ArrayList<>());
        assertThrows(BusinessException.class, () -> staffServiceImpl.addPermission(addPermission, 1L));
        verify(permissionRepository).saveAll((Iterable<Permission>) any());
    }

    /**
     * Method under test: {@link StaffServiceImpl#removePermission(Long, List)}
     */
    @Test
    void testRemovePermission() {
        when(permissionRepository.findAllByAccountIdAndPermissionIdIn((Long) any(), (List<Long>) any()))
                .thenReturn(new ArrayList<>());
        doNothing().when(permissionRepository).deleteAll((Iterable<Permission>) any());
        staffServiceImpl.removePermission(1234567890L, new ArrayList<>());
        verify(permissionRepository).findAllByAccountIdAndPermissionIdIn((Long) any(), (List<Long>) any());
        verify(permissionRepository).deleteAll((Iterable<Permission>) any());
    }

    /**
     * Method under test: {@link StaffServiceImpl#removePermission(Long, List)}
     */
    @Test
    void testRemovePermission2() {
        when(permissionRepository.findAllByAccountIdAndPermissionIdIn((Long) any(), (List<Long>) any()))
                .thenThrow(new NoResultException("An error occurred"));
        doThrow(new NoResultException("An error occurred")).when(permissionRepository)
                .deleteAll((Iterable<Permission>) any());
        assertThrows(NoResultException.class, () -> staffServiceImpl.removePermission(1234567890L, new ArrayList<>()));
        verify(permissionRepository).findAllByAccountIdAndPermissionIdIn((Long) any(), (List<Long>) any());
    }
}

