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

import static vn.com.fpt.common.constants.ErrorStatusConstants.ROOM_NOT_AVAILABLE;
import static vn.com.fpt.common.constants.ErrorStatusConstants.ROOM_NOT_FOUND;
import static vn.com.fpt.common.constants.SearchOperation.*;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomsRepository roomsRepository;

    @Override
    public List<RoomsResponse> listRoom(Long groupId, Long floor, Integer status, String name) {
        BaseSpecification<Rooms> specification = new BaseSpecification<>();
        if (ObjectUtils.isNotEmpty(groupId)) {
            specification.add(new SearchCriteria("groupId", groupId, EQUAL));
        }
        if (ObjectUtils.isNotEmpty(floor)) {
            specification.add(new SearchCriteria("roomFloor", floor, EQUAL));
        }
        if (ObjectUtils.isNotEmpty(status)) {
            if(status == 1){
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
    public Rooms room(Long id) {
        return roomChecker(id);
    }

    @Override
    public List<Rooms> add(List<Rooms> rooms) {
        return roomsRepository.saveAll(rooms);
    }

    @Override
    public Rooms add(Rooms rooms) {
        return roomsRepository.save(rooms);
    }

    @Override
    public RoomsResponse removeRoom(Long id) {
        return RoomsResponse.of(roomsRepository.findById(id).get());
    }

    @Override
    public Rooms updateRoom(Long id, RoomsRequest roomsRequest) {
        // TODO
        return null;
    }

    @Override
    public Rooms updateRoom(Rooms roomsRequest) {
        return roomsRepository.save(roomsRequest);
    }

    @Override
    public Rooms setServiceIndex(Long id, Integer electric, Integer water, Long operator) {
        var roomToSet = room(id);
        roomToSet.setRoomCurrentElectricIndex(electric);
        roomToSet.setRoomCurrentWaterIndex(water);

        return roomsRepository.save(Rooms.modify(room(id), roomToSet, operator));
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
