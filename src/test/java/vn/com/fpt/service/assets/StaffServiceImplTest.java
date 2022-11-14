package vn.com.fpt.service.assets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.entity.authentication.Role;
import vn.com.fpt.model.AccountDTO;
import vn.com.fpt.repositories.AccountRepository;
import vn.com.fpt.repositories.AddressRepository;
import vn.com.fpt.repositories.RoleRepository;
import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.responses.AccountResponse;
import vn.com.fpt.security.ERole;
import vn.com.fpt.service.authentication.AuthenticationService;
import vn.com.fpt.service.staff.StaffService;
import vn.com.fpt.service.staff.StaffServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class StaffServiceImplTest {
    @Mock
    private AuthenticationService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private RoleRepository roleRepository;

    private StaffService staffServiceTest;

    @BeforeEach
    void setUp() {
        staffServiceTest = new StaffServiceImpl(accountService, accountRepository, addressRepository, entityManager, roleRepository);
    }

    @Test
    void testAddStaff() {
        //set up
        RegisterRequest registerRequest = new RegisterRequest();
        Long operator = 1l;

        //mock result
        AccountResponse mockAccountResponse = AccountResponse.builder().accountId(1l).build();
        when(accountService.register(registerRequest, operator)).thenReturn(mockAccountResponse);
        //run test
        AccountResponse result = staffServiceTest.addStaff(registerRequest, operator);
        //verify
        Assertions.assertEquals(1l, result.getAccountId());
    }

    @Test
    void testUpdateStaff() {
        Long id = 1l;
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("name");
        registerRequest.setRoles("user");
        registerRequest.setPassword("password");
        registerRequest.setAddressCity("Ha Noi");
        registerRequest.setAddressDistrict("Cau giay");
        registerRequest.setAddressWards("Mai Dich");
        registerRequest.setAddressMoreDetail("Ngo 32");
        Long modifyBy = 1l;
        Date modifyAt = new Date();

        //mock result
        var mockAccount = new Account();
        mockAccount.setId(1l);

        when(accountRepository.findById(id)).thenReturn(Optional.of(mockAccount));
        when(accountRepository.findAccountByUserNameAndIdNot(registerRequest.getUserName(), id))
                .thenReturn(Optional.ofNullable(null));
        Address address = new Address();
        address.setId(1l);
        when(addressRepository.findById(mockAccount.getId())).thenReturn(Optional.of(address));
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(new Role());
        when(accountService.roleChecker(registerRequest.getRoles())).thenReturn(roleSet);
        Account account = new Account();
        account.setUserName("Thanh");
        account.setAddress(new Address());
        account.setCreatedAt(new Date());
        when(accountRepository.save(any(Account.class))).thenReturn(account);
        AccountResponse result = staffServiceTest.updateStaff(id, registerRequest, modifyBy, modifyAt);
        //verify
        Assertions.assertEquals("Thanh", result.getUserName());
    }

    @Test
    void testListStaff() {
        String role = "role";
        String order = "user, admin";
        String startDate = "2022-10-11";
        String endDate = "2022-11-11";
        Boolean deactivate = true;
        String name = "name";
        String userName = "userName";

        //mock result
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString(), anyString())).thenReturn(query);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setCreatedAt(new Date());
        accountDTO.setAccountId(BigInteger.valueOf(1));
        accountDTO.setAddressId(BigInteger.valueOf(2));
        accountDTO.setFullName("Thanh");
        List<AccountDTO> queryResult = new ArrayList<>();
        queryResult.add(accountDTO);
        when(query.getResultList()).thenReturn(queryResult);
        List<AccountResponse> result = staffServiceTest.listStaff(role, order, startDate, endDate, deactivate, name, userName);
        //verify
        Assertions.assertEquals("Thanh", result.get(0).getFullName());
    }

    @Test
    void testStaff() {
        Long id = 1l;
        //mock result
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString(), anyString())).thenReturn(query);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setCreatedAt(new Date());
        accountDTO.setAccountId(BigInteger.valueOf(1));
        accountDTO.setAddressId(BigInteger.valueOf(2));
        accountDTO.setFullName("Thanh");
        when(query.getSingleResult()).thenReturn(accountDTO);
        AccountResponse result = staffServiceTest.staff(id);
        //verify
        Assertions.assertEquals("Thanh", result.getFullName());
    }

    @Test
    void testStaffException() {
        Long id = 1l;
        //mock result
        Query query = mock(Query.class);
        when(entityManager.createNativeQuery(anyString(), anyString())).thenReturn(query);
        when(query.getSingleResult()).thenThrow(NoResultException.class);
        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> staffServiceTest.staff(id));
        //verify
        Assertions.assertEquals("Không tìm thấy tài khoản: account_id :" + id, businessException.getMessage());
    }

    @Test
    void testRoles() {
        List<Role> rolesName = new ArrayList<>();
        rolesName.add(Role.builder().name(ERole.ROLE_ADMIN).build());
        rolesName.add(Role.builder().name(ERole.ROLE_STAFF).build());

        when(roleRepository.getAll()).thenReturn(rolesName);
        //run test
        List<String> result = staffServiceTest.roles();
        //verify
        Assertions.assertEquals("ADMIN", result.get(0));
        Assertions.assertEquals("STAFF", result.get(1));
    }
}
