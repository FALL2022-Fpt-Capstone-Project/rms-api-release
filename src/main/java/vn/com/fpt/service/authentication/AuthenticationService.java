package vn.com.fpt.service.authentication;

import vn.com.fpt.entity.authentication.Role;
import vn.com.fpt.requests.LoginRequest;
import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.responses.AccountResponse;

import java.util.Set;

public interface AuthenticationService {
    AccountResponse login(LoginRequest loginRequest);

    AccountResponse register(RegisterRequest registerRequest, Long operator);

    String logout();

    Set<Role> roleChecker(String strRoles);
}
