package vn.com.fpt.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.fpt.requests.RoomsRequest;
import vn.com.fpt.responses.RoomsResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    @Override
    public List<RoomsResponse> listRoom(Long groupId, Long floor, Boolean status, String name) {
        return null;
    }

    @Override
    public RoomsResponse room(Long id) {
        return null;
    }

    @Override
    public RoomsResponse removeRoom(Long id) {
        return null;
    }

    @Override
    public RoomsResponse updateRoom(Long id, RoomsRequest roomsRequest) {
        return null;
    }
}
