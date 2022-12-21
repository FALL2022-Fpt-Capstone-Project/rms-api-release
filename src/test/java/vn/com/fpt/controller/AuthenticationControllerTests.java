package vn.com.fpt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import vn.com.fpt.controller.auth.AuthenticationController;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.entity.authentication.Role;
import vn.com.fpt.repositories.AccountRepository;
import vn.com.fpt.repositories.RoleRepository;
import vn.com.fpt.requests.LoginRequest;
import vn.com.fpt.requests.RegisterRequest;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static vn.com.fpt.security.ERole.ROLE_ADMIN;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthenticationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    AccountRepository accountRepository;

    public final String BASE_URL = AuthenticationController.PATH;

    @Test
    @DisplayName("api /login with full require field but then fail authentication")
    void api_login_with_full_require_field() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUserName("thanhpd");
        request.setPassword("123456");

        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }


    @Test
    @DisplayName("api /login with missing username then error")
    void api_login_with_missing_username() throws Exception {
        LoginRequest request = new LoginRequest();
        mockMvc.perform(post(BASE_URL + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.data.password").value("Mật khẩu không được để trống"));
    }

    @Test
    @DisplayName("api /register with full require field then return success")
    void register_with_full_require_field() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUserName("thanhdang123");
        request.setPassword("123123123");
        request.setRoles("admin");
        request.setFullName("Phạm Đặng Thành");
        request.setPhoneNumber("0944808998");
        request.setGender(true);
        request.setAddressCity("abc");
        request.setAddressDistrict("abc");
        request.setAddressWards("abc");
        request.setAddressMoreDetail("abc");
        request.setDeactivate(false);
        request.setPermission(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L));

        when(roleRepository.findByName(ROLE_ADMIN)).thenReturn(Optional.of(new Role(ROLE_ADMIN)));
        Account response = new Account();
        response.setUserName("thanhdang123");
        response.setPassword("123456");
        response.setFullName("Pham Dang Thanh");
        response.setGender(true);
        response.setPhoneNumber("0944808998");
        response.setRoles(Set.of(new Role(ROLE_ADMIN)));
        response.setAddress(new Address("abc", "abc", "abc", "abc"));
        when(accountRepository.save(any(Account.class))).thenReturn(response);
//        when()


        mockMvc.perform(post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("api /register with missing one require filed: username then return error")
    void register_with_missing_one_require_field() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setPassword("123123123");
        request.setRoles("admin");
        request.setFullName("Phạm Đặng Thành");
        request.setPhoneNumber("0944808998");
        request.setGender(true);
        request.setAddressCity("abc");
        request.setAddressDistrict("abc");
        request.setAddressWards("abc");
        request.setAddressMoreDetail("abc");
        request.setDeactivate(false);
        request.setPermission(Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L));

        when(roleRepository.findByName(ROLE_ADMIN)).thenReturn(Optional.of(new Role(ROLE_ADMIN)));


        mockMvc.perform(post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("api /logout success")
    void logout_success() throws Exception {

        mockMvc.perform(post(BASE_URL + "/logout")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

}
