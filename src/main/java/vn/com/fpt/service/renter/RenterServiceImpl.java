package vn.com.fpt.service.renter;

import lombok.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.Renters;
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
    private final RenterRepository renterRepository;

    private final RoomService roomService;

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

        return renterRepository.findAll(specification).stream().map(RentersResponse::of).toList();
    }

    @Override
    public List<RentersResponse> listRenter(Long roomId) {
        return renterRepository.findAllByRoomId(roomId).stream().map(RentersResponse::of).toList();
    }

    @Override
    public List<RentersResponse> listMember(Long roomId) {
        return renterRepository.findAllByRoomIdAndRepresent(roomId, MEMBER).stream().map(RentersResponse::of).toList();
    }

    @Override
    public RentersResponse representRenter(Long roomId) {
        return RentersResponse.of(renterRepository.findByRoomIdAndRepresent(roomId, REPRESENT));
    }

    @Override
    public RentersResponse renter(Long id) {
        return RentersResponse.of(findRenter(id));
    }

    @Override
    @Transactional
    public RentersResponse addRenter(RenterRequest request, Long operator) {
        roomService.roomChecker(request.getRoomId());
        if(renterRepository.findByIdentityNumberAndRoomId(request.getIdentityCard(), request.getRoomId()).isPresent()){
            throw new BusinessException(RENTER_EXISTED, "CCMND/CCCD : " + request.getIdentityCard());
        }
        var address = Address.add(
                request.getAddressCity(),
                request.getAddressDistrict(),
                request.getAddressWards(),
                request.getAddressMoreDetail(),
                operator);

        // add thành viên
        request.setRepresent(false);
        return RentersResponse.of(renterRepository.save(Renters.add(request, address, operator)));
    }

    @Override
    public Renters addRenter(Renters renters) {
        return renterRepository.save(renters);
    }

    @Override
    @Transactional
    public RentersResponse updateRenter(Long id, RenterRequest request, Long operator) {
        var renter = findRenter(id);
        var address = Address.modify(
                renter.getAddress(),
                renter.getAddress().getAddressCity(),
                renter.getAddress().getAddressDistrict(),
                renter.getAddress().getAddressWards(),
                renter.getAddress().getAddressMoreDetails(),
                operator);

        //update thành viên
        return RentersResponse.of(renterRepository.save(Renters.modify(renter, request, address, operator)));
    }

    @Override
    public RentersResponse updateRenter(Long id, Renters addRenterRequest, Long operator) {
        return null;
    }

    @Override
    @Transactional
    public String deleteRenter(Long id) {
        try {
            renterRepository.delete(findRenter(id));
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
        return RentersResponse.of(renterRepository.save(renter));
    }

    @Override
    public Renters findRenter(Long id) {
        return renterRepository.findById(id).orElseThrow(() -> new BusinessException(RENTER_NOT_FOUND, "Không tìm thấy khác thuê renter_id : " + id));
    }

    @Override
    public Renters findRenter(String identity) {
        return renterRepository.findByIdentityNumber(identity);
    }
}
