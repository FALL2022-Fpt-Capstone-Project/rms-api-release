package vn.com.fpt.service.rooms;

import lombok.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.repositories.RoomsRepository;
import vn.com.fpt.requests.RoomsRequest;
import vn.com.fpt.responses.RoomsResponse;
import vn.com.fpt.specification.BaseSpecification;
import vn.com.fpt.specification.SearchCriteria;

import java.util.List;
import java.util.Objects;

import static vn.com.fpt.constants.ErrorStatusConstants.ROOM_NOT_AVAILABLE;
import static vn.com.fpt.constants.ErrorStatusConstants.ROOM_NOT_FOUND;
import static vn.com.fpt.constants.SearchOperation.*;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomsRepository roomsRepository;

    @Override
    public List<RoomsResponse> listRoom(Long groupId, Long floor, Boolean status, String name) {
        BaseSpecification<Rooms> specification = new BaseSpecification<>();
        if (ObjectUtils.allNotNull(groupId)) {
            specification.add(new SearchCriteria("groupId", groupId, EQUAL));
        }
        if (ObjectUtils.isNotEmpty(floor)) {
            specification.add(new SearchCriteria("roomFloor", floor, EQUAL));
        }
        if (ObjectUtils.allNotNull(status)) {
            if(status){
                specification.add(new SearchCriteria("contractId", null, EQUAL));
            }
            else {
                specification.add(new SearchCriteria("contractId", null, NOT_EQUAL));
            }
        }
        if (StringUtils.isNoneBlank(name)) {
            specification.add(new SearchCriteria("roomName", name, MATCH));
        }
        return roomsRepository.findAll(specification).stream().map(RoomsResponse::of).toList();
    }

    @Override
    public RoomsResponse room(Long id) {
        // TODO
        return null;
    }

    @Override
    public RoomsResponse removeRoom(Long id) {
        // TODO
        return null;
    }

    @Override
    public Rooms updateRoom(Long id, RoomsRequest roomsRequest) {
        // TODO
        return null;
    }

    @Override
    public Rooms roomChecker(Long id) {
        return roomsRepository.findById(id).orElseThrow(() ->
                new BusinessException(ROOM_NOT_FOUND, "Không tìm thấy phòng room_id: " + id));
    }

    @Override
    public Rooms getRoom(Long id) {
        return roomsRepository.findById(id).get();
    }

    @Override
    public Rooms emptyRoom(Long id) {
        var room = roomChecker(id);
        if (Objects.nonNull(room.getContractId()))
            throw new BusinessException(ROOM_NOT_AVAILABLE, "Phòng room_id" + id);
        return room;
    }

    @Override
    public Rooms updateRoomStatus(Long id, Long contractId, Long operator) {
        var room = roomChecker(id);

        room.setContractId(contractId);

        room.setModifiedAt(DateUtils.now());
        room.setModifiedBy(operator);
        return room;
    }

}
