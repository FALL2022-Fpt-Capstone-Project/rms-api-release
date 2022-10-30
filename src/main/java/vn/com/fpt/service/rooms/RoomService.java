package vn.com.fpt.service.rooms;

import vn.com.fpt.requests.RoomsRequest;
import vn.com.fpt.responses.RoomsResponse;

import java.util.List;

public interface RoomService {
    List<RoomsResponse> listRoom(Long groupId, Long floor, Boolean status, String name);

    RoomsResponse room (Long id);

    RoomsResponse removeRoom(Long id);

    RoomsResponse updateRoom(Long id, RoomsRequest roomsRequest);
}
