package vn.com.fpt.service.rooms;

import vn.com.fpt.entity.Rooms;
import vn.com.fpt.requests.RoomsRequest;
import vn.com.fpt.responses.RoomsResponse;

import java.util.List;

public interface RoomService {
    List<RoomsResponse> listRoom(Long groupId, Long floor, Integer status, String name);

    RoomsResponse room (Long id);

    List<Rooms> add(List<Rooms> rooms);

    Rooms add(Rooms rooms);

    RoomsResponse removeRoom(Long id);

    Rooms updateRoom(Long id, RoomsRequest roomsRequest);

    Rooms roomChecker(Long id);

    Rooms getRoom(Long id);

    Rooms emptyRoom(Long id);

    Rooms updateRoomStatus(Long id, Long contractId, Long operator);
}
