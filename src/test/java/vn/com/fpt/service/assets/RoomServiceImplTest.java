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
import vn.com.fpt.repositories.ContractRepository;
import vn.com.fpt.repositories.RackRenterRepository;
import vn.com.fpt.repositories.RoomsRepository;
import vn.com.fpt.requests.AddRoomsRequest;
import vn.com.fpt.responses.RentersResponse;
import vn.com.fpt.service.contract.ContractService;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.renter.RenterService;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.rooms.RoomServiceImpl;
import vn.com.fpt.service.services.ServicesService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static vn.com.fpt.common.constants.ErrorStatusConstants.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RoomServiceImplTest {
    @Mock
    private RoomsRepository roomsRepository;

    @Mock
    private AssetService assetService;

    @Mock
    private ContractService contractService;

    @Mock
    private GroupService groupService;

    @Mock
    private RackRenterRepository renterRepository;

    @Mock
    private ServicesService servicesService;

    @Mock
    private ContractRepository contractRepo;

    @Mock
    private RoomService roomServiceTest;

    @Mock
    private RenterService renterService;


    @BeforeEach
    void setUp() {
        roomServiceTest = new RoomServiceImpl(roomsRepository, assetService, contractService, groupService, servicesService, renterRepository, contractRepo, renterService);
    }

//    @Test
//    void testListRoom() {
//        Long groupId = 1l;
//        Long floor = 2l;
//        Integer status = 1;
//        String name = "name";
//
//        Rooms rooms = Rooms.builder().roomName("rooms").build();
//        List<Rooms> list = new ArrayList<>();
//        list.add(rooms);
//        when(roomsRepository.findAll(any(BaseSpecification.class))).thenReturn(list).thenReturn(list);
//        //run test
//        List<RoomsResponse> result = roomServiceTest.listRoom(groupId, floor, status, name);
//        //verify
//        Assertions.assertEquals("rooms", result.get(0).getRoomName());
//
//        //run test
//        List<RoomsResponse> result2 = roomServiceTest.listRoom(groupId, floor, 2, name);
//        //verify
//        Assertions.assertEquals("rooms", result2.get(0).getRoomName());
//    }

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
    void test_remove_room_success() {
        List<RentersResponse> responseList = new ArrayList<>();
        responseList.add(RentersResponse.builder().renterId(1l).build());
        //mock result
        when(renterService.listRenter(1l)).thenReturn(responseList);
        when(renterService.removeFromRoom(1l, 2l)).thenReturn(RentersResponse.builder().build());
        when(roomsRepository.findById(1l)).thenReturn(Optional.of(Rooms.builder().build()));
        when(roomsRepository.save(any(Rooms.class))).thenReturn(new Rooms());
        //run test
        Rooms result = roomServiceTest.removeRoom(1l , 2l);
        //verify
        Assertions.assertNotNull(result);
    }

    @Test
    void test_remove_room_when_exist_room_then_throws_exception() {
        List<RentersResponse> responseList = new ArrayList<>();
        responseList.add(RentersResponse.builder().renterId(1l).build());
        //mock result
        when(renterService.listRenter(1l)).thenReturn(responseList);
        when(renterService.removeFromRoom(1l, 2l)).thenReturn(RentersResponse.builder().build());
        when(roomsRepository.findById(1l)).thenReturn(Optional.of(Rooms.builder().contractId(1l).build()));
        when(roomsRepository.save(any(Rooms.class))).thenReturn(new Rooms());
        //run test
        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> roomServiceTest.removeRoom(1l, 2l));
        //verify
        Assertions.assertEquals(ROOM_NOT_AVAILABLE, businessException.getErrorStatus());
    }

    @Test
    void testUpdateRoom() {
        long id = 1l;
        AddRoomsRequest roomsRequest = new AddRoomsRequest();
        //verify
        Assertions.assertEquals(null, roomServiceTest.updateRoom(id, roomsRequest));
    }

    @Test
    void test_update_room_suceess() {
        Rooms roomsRequest = Rooms.builder().roomName("rooms").roomName("ABCD").build();
        roomsRequest.setId(1l);

        Rooms roomsFindById = Rooms.builder().groupId(1l).build();
        when(roomsRepository.findById(anyLong())).thenReturn(Optional.of(roomsFindById));
        List<Rooms> roomsList = new ArrayList<>();
        roomsList.add(Rooms.builder().roomName("ABC").build());
        when(roomsRepository.findByGroupIdAndIdNotAndIsDisableIsFalse(1l, 1l))
                .thenReturn(roomsList);
        when(roomsRepository.save(roomsRequest)).thenReturn(roomsRequest);
        //run test
        Rooms result = roomServiceTest.updateRoom(roomsRequest);
        //verify
        Assertions.assertNotNull(roomsRequest);
    }

    @Test
    void test_update_romm_when_duplicate_rooms_throws_Exception() {
        Rooms roomsRequest = Rooms.builder().roomName("rooms").roomName("ABC").build();
        roomsRequest.setId(1l);

        Rooms roomsFindById = Rooms.builder().groupId(1l).build();
        when(roomsRepository.findById(anyLong())).thenReturn(Optional.of(roomsFindById));
        List<Rooms> roomsList = new ArrayList<>();
        roomsList.add(Rooms.builder().roomName("ABC").build());
        when(roomsRepository.findByGroupIdAndIdNotAndIsDisableIsFalse(1l, 1l))
                .thenReturn(roomsList);
        when(roomsRepository.save(roomsRequest)).thenReturn(roomsRequest);
        //run test
        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> roomServiceTest.updateRoom(roomsRequest));
        //verify
        Assertions.assertNotNull(businessException);
        Assertions.assertEquals(DUPLICATE_NAME, businessException.getErrorStatus());
    }

    @Test
    void test_update_list_rooms_success() {
        List<Rooms> roomsList = new ArrayList<>();
        Rooms rooms = Rooms.builder().groupId(1l).roomName("ABCD").build();
        rooms.setId(1l);
        roomsList.add(rooms);

        List<Rooms> listCheckDuplicateRoomName = new ArrayList<>();
        listCheckDuplicateRoomName.add(Rooms.builder().roomName("ABC").build());

        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse(anyLong(), anyList()))
                .thenReturn(listCheckDuplicateRoomName);
        when(roomsRepository.saveAll(anyList())).thenReturn(Arrays.asList(new Rooms()));
        //run test
        List<Rooms> result = roomServiceTest.updateRoom(roomsList);
        //verify
        Assertions.assertNotNull(result);
    }

    @Test
    void test_update_list_rooms_when_duplicate_list_room_then_throws_exception() {
        List<Rooms> roomsList = new ArrayList<>();
        Rooms rooms = Rooms.builder().groupId(1l).roomName("ABC").build();
        rooms.setId(1l);
        roomsList.add(rooms);

        List<Rooms> listCheckDuplicateRoomName = new ArrayList<>();
        listCheckDuplicateRoomName.add(Rooms.builder().roomName("ABC").build());

        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse(anyLong(), anyList()))
                .thenReturn(listCheckDuplicateRoomName);
        when(roomsRepository.saveAll(anyList())).thenReturn(Arrays.asList(new Rooms()));
        //run test
        BusinessException businessException = Assertions.assertThrows(BusinessException.class, () -> roomServiceTest.updateRoom(roomsList));
        //verify
        Assertions.assertNotNull(businessException);
        Assertions.assertEquals(DUPLICATE_NAME, businessException.getErrorStatus());
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
        Rooms result = roomServiceTest.room(id);
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
