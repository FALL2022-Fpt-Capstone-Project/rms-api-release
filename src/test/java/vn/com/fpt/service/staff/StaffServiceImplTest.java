package vn.com.fpt.service.staff;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import vn.com.fpt.entity.authentication.Role;
import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.responses.AccountResponse;
import vn.com.fpt.security.ERole;
import vn.com.fpt.service.authentication.AuthenticationService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.postgresql.hostchooser.HostRequirement.any;

@ExtendWith(MockitoExtension.class)
class StaffServiceImplTest {


    @InjectMocks
    private StaffServiceImpl staffService;

    @Mock
    private AuthenticationService accountService;

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

        assertEquals(accountResponse.getAccountId() ,result.getAccountId());
        assertEquals(accountResponse.getAddressCity() ,result.getAddressCity());
        assertEquals(accountResponse.getUserName() ,result.getUserName());
        assertEquals(accountResponse.getPassword() ,result.getPassword());
    }
}