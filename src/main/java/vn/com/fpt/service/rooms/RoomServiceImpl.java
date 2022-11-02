package vn.com.fpt.service.rooms;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.constants.ErrorStatusConstants;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.repositories.RoomsRepository;
import vn.com.fpt.requests.RoomsRequest;
import vn.com.fpt.responses.RoomsResponse;

import java.util.List;
import java.util.Objects;

import static vn.com.fpt.constants.ErrorStatusConstants.ROOM_NOT_AVAILABLE;
import static vn.com.fpt.constants.ErrorStatusConstants.ROOM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomsRepository roomsRepository;

    @Override
    public List<RoomsResponse> listRoom(Long groupId, Long floor, Boolean status, String name) {
        // TODO
        return null;
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
