package vn.com.fpt.service.staff;

import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.responses.AccountResponse;

import java.util.Date;
import java.util.List;

public interface StaffService {
    AccountResponse addStaff(RegisterRequest registerRequest, Long operator);

    AccountResponse updateStaff(Long id, RegisterRequest registerRequest, Long modifyBy, Date modifyAt);

    List<AccountResponse> listStaff(String role, String order, String startDate, String endDate, Boolean deactivate, String name, String userName);

    AccountResponse staff(Long id);
}