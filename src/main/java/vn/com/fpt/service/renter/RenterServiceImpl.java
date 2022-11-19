package vn.com.fpt.service.renter;

import lombok.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.RackRenters;
import vn.com.fpt.entity.Renters;
import vn.com.fpt.repositories.RackRenterRepository;
import vn.com.fpt.repositories.RenterRepository;
import vn.com.fpt.requests.RenterRequest;
import vn.com.fpt.responses.RentersResponse;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.specification.BaseSpecification;
import vn.com.fpt.specification.SearchCriteria;

import java.util.List;

import static vn.com.fpt.common.constants.ErrorStatusConstants.*;
import static vn.com.fpt.common.constants.ManagerConstants.MEMBER;
import static vn.com.fpt.common.constants.ManagerConstants.REPRESENT;
import static vn.com.fpt.common.constants.SearchOperation.*;

@Service
@RequiredArgsConstructor
public class RenterServiceImpl implements RenterService {
    private final RenterRepository renterRepo;

    private final RoomService roomService;

    private final RackRenterRepository rackRenterRepo;

    @Override
    public List<RentersResponse> listRenter(Long groupId, Boolean gender, String name, String phone, Long room) {
        BaseSpecification<Renters> specification = new BaseSpecification<>();

        if (ObjectUtils.allNotNull(groupId)) {
            specification.add(new SearchCriteria("groupId", groupId, EQUAL));
        }
        if (ObjectUtils.isNotEmpty(room)) {
            specification.add(new SearchCriteria("roomId", room, EQUAL));
        }
        if (ObjectUtils.allNotNull(gender)) {
            specification.add(new SearchCriteria("gender", gender, EQUAL));
        }
        if (StringUtils.isNoneBlank(name)) {
            specification.add(new SearchCriteria("renterFullName", name, MATCH));
        }
        if (StringUtils.isNotBlank(phone)) {
            specification.add(new SearchCriteria("phoneNumber", phone, MATCH));
        }

        return renterRepo.findAll(specification).stream().map(RentersResponse::of).toList();
    }

    @Override
    public List<RentersResponse> listRenter(Long roomId) {
        return renterRepo.findAllByRoomId(roomId).stream().map(RentersResponse::of).toList();
    }

    @Override
    public List<RentersResponse> listMember(Long roomId) {
        return renterRepo.findAllByRoomIdAndRepresent(roomId, MEMBER).stream().map(RentersResponse::of).toList();
    }

    @Override
    public RentersResponse representRenter(Long roomId) {
        return RentersResponse.of(renterRepo.findByRoomIdAndRepresent(roomId, REPRESENT));
    }

    @Override
    public RentersResponse renter(Long id) {
        return RentersResponse.of(findRenter(id));
    }

    @Override
    @Transactional
    public RentersResponse addRenter(RenterRequest request, Long operator) {
        roomService.roomChecker(request.getRoomId());
        if(renterRepo.findByIdentityNumberAndRoomId(request.getIdentityCard(), request.getRoomId()).isPresent()){
            throw new BusinessException(RENTER_EXISTED, "CMND/CCCD : " + request.getIdentityCard());
        }
        var address = Address.add(
                request.getAddressCity(),
                request.getAddressDistrict(),
                request.getAddressWards(),
                request.getAddressMoreDetail(),
                operator);

        // add thành viên
        request.setRepresent(false);
        return RentersResponse.of(renterRepo.save(Renters.add(request, address, operator)));
    }

    @Override
    public Renters addRenter(Renters renters) {
        return renterRepo.save(renters);
    }

    @Override
    @Transactional
    public RentersResponse updateRenter(Long id, RenterRequest request, Long operator) {
        var renter = findRenter(id);
        var address = new Address().modify(
                renter.getAddress(),
                request.getAddressCity(),
                request.getAddressDistrict(),
                request.getAddressWards(),
                request.getAddressMoreDetail(),
                operator);

        //update thành viên
        return RentersResponse.of(renterRepo.save(Renters.modify(renter, request, address, operator)));
    }

    @Override
    public RentersResponse updateRenter(Long id, Renters addRenterRequest, Long operator) {
        return null;
    }

    @Override
    @Transactional
    public String deleteRenter(Long id) {
        try {
            renterRepo.delete(findRenter(id));
            return "Xóa khách thuê renter_id: " + id + " thành công";
        } catch (BusinessException e) {
            throw new BusinessException("Xóa khách thuê renter_id: " + id + " thất bại");
        }
    }

    @Override
    public RentersResponse removeFromRoom(Long id, Long operator) {
        var renter = findRenter(id);
        renter.setRoomId(null);
        renter.setModifiedAt(DateUtils.now());
        renter.setModifiedBy(operator);
        return RentersResponse.of(renterRepo.save(renter));
    }

    @Override
    public Renters findRenter(Long id) {
        return renterRepo.findById(id).orElseThrow(() -> new BusinessException(RENTER_NOT_FOUND, "Không tìm thấy khác thuê renter_id : " + id));
    }

    @Override
    public Renters findRenter(String identity) {
        return renterRepo.findByIdentityNumber(identity);
    }

    @Override
    public RackRenters rackRenter(Long id) {
        return rackRenterRepo.findById(id).orElseThrow(()-> new BusinessException(RACK_RENTER_NOT_FOUND, "Không tìm thấy chủ thuê rack_renter_id : " + id));
    }

    @Override
    public List<RackRenters> listRackRenter() {
        return rackRenterRepo.findAll();
    }
}
