package vn.com.fpt.service.assets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.repositories.RoomsRepository;
import vn.com.fpt.requests.RoomsRequest;
import vn.com.fpt.responses.RoomsResponse;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.rooms.RoomServiceImpl;
import vn.com.fpt.specification.BaseSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static vn.com.fpt.common.constants.ErrorStatusConstants.ROOM_NOT_AVAILABLE;
import static vn.com.fpt.common.constants.ErrorStatusConstants.ROOM_NOT_FOUND;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RoomServiceImplTest {
    @Mock
    private RoomsRepository roomsRepository;

    private RoomService roomServiceTest;

    @BeforeEach
    void setUp() {
        roomServiceTest = new RoomServiceImpl(roomsRepository);
    }

    @Test
    void testListRoom() {
        Long groupId = 1l;
        Long floor = 2l;
        Integer status = 1;
        String name = "name";

        Rooms rooms = Rooms.builder().roomName("rooms").build();
        List<Rooms> list = new ArrayList<>();
        list.add(rooms);
        when(roomsRepository.findAll(any(BaseSpecification.class))).thenReturn(list).thenReturn(list);
        //run test
        List<RoomsResponse> result = roomServiceTest.listRoom(groupId, floor, status, name);
        //verify
        Assertions.assertEquals("rooms", result.get(0).getRoomName());

        //run test
        List<RoomsResponse> result2 = roomServiceTest.listRoom(groupId, floor, 2, name);
        //verify
        Assertions.assertEquals("rooms", result2.get(0).getRoomName());
    }

    @Test
    void testRoom() {
        Long id = 1l;
        when(roomsRepository.findById(id)).thenReturn(Optional.of(Rooms.builder().roomName("rooms").build()));
        //run test
        Rooms result = roomServiceTest.room(id);
        //verify
        Assertions.assertEquals("rooms", result.getRoomName());
    }

    @Test
    void testAdd() {
        List<Rooms> rooms = new ArrayList<>();
        rooms.add(Rooms.builder().roomName("rooms").build());

        //mock result
        when(roomsRepository.saveAll(rooms)).thenReturn(rooms);
        //run test
        List<Rooms> result = roomServiceTest.add(rooms);
        //verify
        Assertions.assertEquals("rooms", result.get(0).getRoomName());
    }

    @Test
    void testAddRooms() {
        Rooms rooms = Rooms.builder().roomName("rooms").build();
        //mock result
        when(roomsRepository.save(rooms)).thenReturn(rooms);
        //run test
        Rooms result = roomServiceTest.add(rooms);
        //verify
        Assertions.assertEquals("rooms", result.getRoomName());
    }

    @Test
    void testRemoveRoom() {
        Long id = 1l;
        Rooms rooms = Rooms.builder().roomName("rooms").build();
        //mock result
        when(roomsRepository.findById(id)).thenReturn(Optional.of(rooms));
        //run test
        RoomsResponse result = roomServiceTest.removeRoom(id);
        //vefiry
        Assertions.assertEquals("rooms", result.getRoomName());
    }

    @Test
    void testUpdateRoom() {
        long id = 1l;
        RoomsRequest roomsRequest = new RoomsRequest();
        //verify
        Assertions.assertEquals(null, roomServiceTest.updateRoom(id, roomsRequest));
    }

    @Test
    void testUpdateRoom2() {
        Rooms roomsRequest = Rooms.builder().roomName("rooms").build();
        when(roomsRepository.save(roomsRequest)).thenReturn(roomsRequest);
        Assertions.assertEquals("rooms", roomServiceTest.updateRoom(roomsRequest).getRoomName());
    }

    @Test
    void testSetServiceIndex() {
        Long id = 1l;
        Integer electric = 1;
        Integer water = 1;
        Long operator = 1l;

        //mock result
        Rooms rooms = Rooms.builder().roomName("rooms").build();
        when(roomsRepository.findById(id)).thenReturn(Optional.of(rooms));
        when(roomsRepository.save(any(Rooms.class))).thenReturn(rooms);
        //verify
        Assertions.assertEquals("rooms", roomServiceTest.setServiceIndex(id, electric, water, operator).getRoomName());
    }

    @Test
    void testRoomChecker() {
        Long id = 1l;
        //mock result
        when(roomsRepository.findById(id)).thenReturn(Optional.of(Rooms.builder().roomName("rooms").build()));
        //verify
        Assertions.assertEquals("rooms", roomServiceTest.roomChecker(id).getRoomName());
    }

    @Test
    void testRoomCheckerException() {
        Long id = 1l;
        //mock result
        when(roomsRepository.findById(id)).thenReturn(Optional.ofNullable(null));
        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> roomServiceTest.roomChecker(id));
        //verify
        Assertions.assertEquals(ROOM_NOT_FOUND, businessException.getErrorStatus());
    }

    @Test
    void testGetRoom() {
        Long id = 1l;
        Rooms rooms = Rooms.builder().roomName("rooms").build();
        when(roomsRepository.findById(id)).thenReturn(Optional.of(rooms));
        Rooms result = roomServiceTest.getRoom(id);
        //verify
        Assertions.assertEquals("rooms", result.getRoomName());
    }

    @Test
    void testEmptyRoom() {
        Long id = 1l;
        //mock result
        when(roomsRepository.findById(id)).thenReturn(Optional.of(Rooms.builder().roomName("rooms").build()));
        Rooms result = roomServiceTest.emptyRoom(id);
        //verify
        Assertions.assertEquals("rooms", result.getRoomName());
    }

    @Test
    void testEmptyRoomException() {
        Long id = 1l;
        //mock result
        when(roomsRepository.findById(id)).thenReturn(Optional.of(Rooms.builder().roomName("rooms").contractId(1l).build()));
        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> roomServiceTest.emptyRoom(id));
        //verify
        Assertions.assertEquals(ROOM_NOT_AVAILABLE, businessException.getErrorStatus());
    }

    @Test
    void testUpdateRoomStatus() {
        Long id = 1l;
        Long contractId = 1l;
        Long operator = 1l;
        when(roomsRepository.findById(id)).thenReturn(Optional.of(Rooms.builder().roomName("rooms").contractId(1l).build()));
        Rooms result = roomServiceTest.updateRoomStatus(id, contractId, operator);
        //verify
        Assertions.assertEquals("rooms", result.getRoomName());
    }
}
