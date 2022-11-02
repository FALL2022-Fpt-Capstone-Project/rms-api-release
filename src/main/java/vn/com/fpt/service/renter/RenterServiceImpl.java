package vn.com.fpt.service.renter;

import lombok.*;
import org.springframework.stereotype.Service;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.repositories.RenterRepository;
import vn.com.fpt.requests.RenterRequest;
import vn.com.fpt.responses.RentersResponse;

import java.util.List;
import java.util.stream.Collectors;

import static vn.com.fpt.constants.ErrorStatusConstants.STAFF_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RenterServiceImpl implements RenterService {
    private final RenterRepository renterRepository;

    @Override
    public List<RentersResponse> listRenter(Long groupId, Boolean gender, String name, String phone, Long room) {
        return renterRepository.findAll().stream().map(RentersResponse::of).collect(Collectors.toList());
    }

    @Override
    public RentersResponse renter(Long id) {
        var renter = renterRepository.findById(id)
                .orElseThrow(() -> new BusinessException(STAFF_NOT_FOUND, "Không tìm thấy khác thuê staff_id : " + id));
        return RentersResponse.of(renter);
    }

    @Override
    public RentersResponse addRenter(RenterRequest addRenterRequest) {
        return null;
    }

    @Override
    public RentersResponse updateRenter(Long id, RenterRequest addRenterRequest) {
        return null;
    }

    @Override
    public RentersResponse removeRenter(Long id) {
        return null;
    }
}
