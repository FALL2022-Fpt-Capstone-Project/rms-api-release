package vn.com.fpt.service.staff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.BaseEntity;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.entity.authentication.Role;
import vn.com.fpt.repositories.AccountRepository;
import vn.com.fpt.repositories.AddressRepository;
import vn.com.fpt.repositories.RoleRepository;
import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.responses.AccountResponse;
import vn.com.fpt.security.ERole;
import vn.com.fpt.service.authentication.AuthenticationService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.postgresql.hostchooser.HostRequirement.any;
import static vn.com.fpt.security.ERole.ROLE_ADMIN;
import static vn.com.fpt.security.ERole.ROLE_STAFF;

@ExtendWith(MockitoExtension.class)
class StaffServiceImplTest {


    @InjectMocks
    private StaffServiceImpl staffService;

    @Mock
    private AuthenticationService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private  RoleRepository roleRepository;

    @Test
    void GivenExactValue_Then_addStaff_ResultAccountResponseExact() {
        //mock request
        RegisterRequest request = new RegisterRequest();
        request.setUserName("abc-demo");
        request.setAddressCity("Viet-Nam");
        request.setPassword("123");

        Long operator = 1L;

        //mock result
        AccountResponse accountResponse = AccountResponse.builder()
                .accountId(1L)
                .addressCity("Viet-nam")
                .userName("abc-demo")
                .password("123")
                .build();

        //mock function accountService.register
        when(accountService.register(any(RegisterRequest.class), anyLong())).thenReturn(accountResponse);

        //result
        AccountResponse result = staffService.addStaff(request, operator);

        assertEquals(accountResponse.getAccountId(), result.getAccountId());
        assertEquals(accountResponse.getAddressCity(), result.getAddressCity());
        assertEquals(accountResponse.getUserName(), result.getUserName());
        assertEquals(accountResponse.getPassword(), result.getPassword());
    }

    @Test
    void GivenWrongValue_Then_addStaff_ThrowBusinessException() {
        //mock request
        RegisterRequest request = new RegisterRequest();
        request.setUserName("value-wrong");
        request.setAddressCity("value-wrong");
        request.setPassword("value-wrong");

        Long operator = 1L;

        //mock result
        AccountResponse accountResponse = AccountResponse.builder()
                .accountId(1L)
                .addressCity("Viet-nam")
                .userName("abc-demo")
                .password("123")
                .build();

        //mock function accountService.register
        when(accountService.register(any(RegisterRequest.class), anyLong())).thenReturn(accountResponse);

        //result
        AccountResponse result = staffService.addStaff(request, operator);

        assertEquals(accountResponse.getAccountId(), result.getAccountId());
        assertEquals(accountResponse.getAddressCity(), result.getAddressCity());
        assertEquals(accountResponse.getUserName(), result.getUserName());
        assertEquals(accountResponse.getPassword(), result.getPassword());
    }

    @Test
    void GivenExactValue_Then_updateStaff_ResultAccountResponseExact() {
        //mock request
        RegisterRequest request = new RegisterRequest();
        request.setUserName("abc-demo");
        request.setAddressCity("Viet-Nam");
        request.setPassword("123");
        request.setRoles("demoRole");

        Long id = 1L;
        Long modifyBy = 1L;
        Date modifyAt = new Date();

        //mock result
        AccountResponse accountResponse = AccountResponse.builder()
                .accountId(1L)
                .addressCity("Viet-nam")
                .userName("abc-demo")
                .password("123")
                .build();

        //mock function accountRepository.findById
        Address address = new Address();
        address.setId(1L);
        address.setAddressCity("Viet-nam");

        Account account = new Account();
        account.setId(1L);
        account.setAddress(address);
        account.setUserName("abc-demo");
        account.setPassword("123");
        Optional<Account> optionalAccount = Optional.of(account);
        when(accountRepository.findById(anyLong())).thenReturn(optionalAccount);

        //mock function accountRepository.findAccountByUserNameAndIdNot
        Optional<Account> optionalAccountRes = Optional.empty();
        when(accountRepository.findAccountByUserNameAndIdNot(anyString(), anyLong())).thenReturn(optionalAccountRes);

        //mock function addressRepository.findById
        Optional<Address> optionalAddress = Optional.of(address);
        when(addressRepository.findById(anyLong())).thenReturn(optionalAddress);

        //mock function accountService.roleChecker
        Set<Role> roleSet = new HashSet<Role>();
        when(accountService.roleChecker(anyString())).thenReturn(roleSet);

        //mock function accountRepository.save
        when(accountRepository.save(any(Account.class))).thenReturn(account);


        //result
        AccountResponse result = staffService.updateStaff(id, request, modifyBy, modifyAt);

        assertEquals(accountResponse.getAccountId(), result.getAccountId());
        assertEquals(accountResponse.getAddressCity(), result.getAddressCity());
        assertEquals(accountResponse.getUserName(), result.getUserName());
        assertEquals(accountResponse.getPassword(), result.getPassword());
    }

    @Test
    void GivenExactValue_Then_listStaff_ResultAccountResponseExact() {
        //mock request
        String role = "role";
        String order = "order";
        String startDate = "";
        String endDate = "";
        Boolean deactivate = true;
        String name = "name";
        String userName = "userName";

        //mock result
        AccountResponse accountResponse = AccountResponse.builder()
                .accountId(1L)
                .addressCity("Viet-nam")
                .userName("abc-demo")
                .password("123")
                .build();

        //result
        List<AccountResponse> result = staffService.listStaff( role, order,startDate, endDate, deactivate, name, userName);

        assertEquals(accountResponse.getAccountId(), result.size());

    }
    
    @Test
    void GivenExactValue_Then_staff_ResultAccountResponseExact() {
        //mock request
        Long id = 1L;


        //mock result
        AccountResponse accountResponse = AccountResponse.builder()
                .accountId(1L)
                .addressCity("Viet-nam")
                .userName("abc-demo")
                .password("123")
                .build();

        //result
        AccountResponse result = staffService.staff( 1L);

        assertEquals(1L, result.getAccountId());

    }

    @Test
    void GivenNOValue_Then_roles_ResultListExact() {

        //mock result
        AccountResponse accountResponse = AccountResponse.builder()
                .accountId(1L)
                .addressCity("Viet-nam")
                .userName("abc-demo")
                .password("123")
                .build();

        //mock roleRepository.getAll
        List<Role> listRole = new ArrayList<>();
        listRole.add(new Role(ROLE_STAFF));
        listRole.add(new Role(ROLE_ADMIN));
        when(roleRepository.getAll()).thenReturn(listRole);

        //result
        List<String> result = staffService.roles();

        assertEquals(2, result.size());
        assertEquals("STAFF", result.get(0));
        assertEquals("ADMIN", result.get(1));

    }
}