package vn.com.fpt.service.renter;

import vn.com.fpt.requests.RenterRequest;
import vn.com.fpt.responses.RentersResponse;

import java.util.List;

public interface RenterService {
    List<RentersResponse> listRenter(Long groupId, Boolean gender, String name, String phone, Long room);

    RentersResponse renter(Long id);

    RentersResponse addRenter(RenterRequest addRenterRequest);

    RentersResponse updateRenter(Long id, RenterRequest addRenterRequest);

    RentersResponse removeRenter(Long id);



}
