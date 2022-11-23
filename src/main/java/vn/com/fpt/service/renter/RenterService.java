package vn.com.fpt.service.renter;

import org.springframework.data.jpa.domain.Specification;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.RackRenters;
import vn.com.fpt.entity.Renters;
import vn.com.fpt.requests.Operator;
import vn.com.fpt.requests.RenterRequest;
import vn.com.fpt.responses.RentersResponse;

import java.util.List;

public interface RenterService {
    List<RentersResponse> listRenter(Long groupId, Boolean gender, String name, String phone, Long room);

    List<RentersResponse> listRenter(Long roomId);

    List<RentersResponse> listMember(Long roomId);

    RentersResponse representRenter(Long roomId);

    RentersResponse renter(Long id);

    RentersResponse addRenter(RenterRequest addRenterRequest, Long operator);

    Renters addRenter(Renters renters);

    RackRenters addRackRenter(String name,
                              Boolean gender,
                              String phone,
                              String identity,
                              Address address,
                              Long operator);

    RentersResponse updateRenter(Long id, RenterRequest addRenterRequest, Long operator);

    RentersResponse updateRenter(Long id, Renters addRenterRequest, Long operator);

    String deleteRenter(Long id);

    RentersResponse removeFromRoom(Long id, Long operator);

    Renters findRenter(Long id);

    Renters findRenter(String identity);

    RackRenters rackRenter(Long id);

    List<RackRenters> listRackRenter();

    List<RackRenters> listRackRenter(Specification<RackRenters> filter);



}
