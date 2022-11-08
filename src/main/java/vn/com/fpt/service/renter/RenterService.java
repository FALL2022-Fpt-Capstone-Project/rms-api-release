package vn.com.fpt.service.renter;

import vn.com.fpt.entity.Renters;
import vn.com.fpt.requests.RenterRequest;
import vn.com.fpt.responses.RentersResponse;

import java.util.List;

public interface RenterService {
    List<RentersResponse> listRenter(Long groupId, Boolean gender, String name, String phone, Long room);

    List<RentersResponse> listRenter(Long roomId);

    RentersResponse renter(Long id);

    RentersResponse addRenter(RenterRequest addRenterRequest, Long operator);

    Renters addRenter(Renters renters);

    RentersResponse updateRenter(Long id, RenterRequest addRenterRequest, Long operator);

    RentersResponse updateRenter(Long id, Renters addRenterRequest, Long operator);

    String deleteRenter(Long id);

    RentersResponse removeFromRoom(Long id, Long operator);

    Renters findRenter(Long id);

    Renters findRenter(String identity);



}
