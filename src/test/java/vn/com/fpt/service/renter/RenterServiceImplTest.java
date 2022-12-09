package vn.com.fpt.service.renter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.Renters;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.repositories.RackRenterRepository;
import vn.com.fpt.repositories.RenterRepository;
import vn.com.fpt.requests.RenterRequest;
import vn.com.fpt.responses.RentersResponse;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.specification.BaseSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static vn.com.fpt.common.constants.ErrorStatusConstants.*;

@ExtendWith(MockitoExtension.class)
class RenterServiceImplTest {

    @InjectMocks
    RenterServiceImpl renterService;

    @Mock
    private RenterRepository renterRepository;

    @Mock
    private RackRenterRepository rackRenterRepository;

    @Mock
    private RoomService roomService;

    @Test
    void GivenExactValue_Then_listRenter_ResultListRentersResponseExact() {
        //input
        Long groupId = 1L;
        Boolean gender = false;
        String name = "abc";
        String phone = "0123";
        Long room = 1L;
        //mock renterRepository.findAll
        List<Renters> listRenters = new ArrayList<>();
        Renters renter = new Renters();
        renter.setRoomId(1L);
        renter.setPhoneNumber("0123");
        renter.setRenterFullName("abc");
        renter.setGender(false);
        listRenters.add(renter);
        when(renterRepository.findAll(any(BaseSpecification.class))).thenReturn(listRenters);

        List<RentersResponse> result = renterService.listRenter(groupId, gender, name, phone, room);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getRoomId(), 1L);
        assertEquals(result.get(0).getRenterFullName(), "abc");
        assertEquals(result.get(0).getGender(), false);
        assertEquals(result.get(0).getPhoneNumber(), "0123");
    }

    @Test
    void GivenNullValue_Then_listRenter_ResultListRentersResponseExactEmpty() {
        //input
        Long groupId = null;
        Boolean gender = false;
        String name = null;
        String phone = null;
        Long room = null;
        //mock renterRepository.findAll
        List<Renters> listRenters = new ArrayList<>();
        Renters renter = new Renters();
        listRenters.add(renter);
        when(renterRepository.findAll(any(BaseSpecification.class))).thenReturn(listRenters);

        List<RentersResponse> result = renterService.listRenter(groupId, gender, name, phone, room);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getAddress(), null);
        assertEquals(result.get(0).getRenterId(), null);
        assertEquals(result.get(0).getEmail(), null);
        assertEquals(result.get(0).getPhoneNumber(), null);
    }

    @Test
    void GivenRoomIdValue_Then_listRenter_ResultListRentersResponseExact() {
        Long groupId = 1L;

        //mock renterRepository.findAllByRoomId
        List<Renters> listRenters = new ArrayList<>();
        Renters renter = new Renters();
        renter.setRoomId(1L);
        renter.setPhoneNumber("0123");
        renter.setRenterFullName("abc");
        renter.setGender(false);
        listRenters.add(renter);
        when(renterRepository.findAllByRoomId(anyLong())).thenReturn(listRenters);

        List<RentersResponse> result = renterService.listRenter(groupId);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getRoomId(), 1L);
        assertEquals(result.get(0).getRenterFullName(), "abc");
        assertEquals(result.get(0).getGender(), false);
        assertEquals(result.get(0).getPhoneNumber(), "0123");
    }

    @Test
    void GivenExactValue_Then_listMember_ResultListRentersResponseExact() {
        Long groupId = 1L;

        //mock renterRepository.findAllByRoomIdAndRepresent
        List<Renters> listRenters = new ArrayList<>();
        Renters renter = new Renters();
        renter.setRoomId(1L);
        renter.setPhoneNumber("0123");
        renter.setRenterFullName("abc");
        renter.setGender(false);
        listRenters.add(renter);
        when(renterRepository.findAllByRoomIdAndRepresent(anyLong(), any())).thenReturn(listRenters);

        List<RentersResponse> result = renterService.listMember(groupId);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getRoomId(), 1L);
        assertEquals(result.get(0).getRenterFullName(), "abc");
        assertEquals(result.get(0).getGender(), false);
        assertEquals(result.get(0).getPhoneNumber(), "0123");
    }

    @Test
    void GivenExactValue_Then_representRenter_ResultRentersResponseExact() {
        Long groupId = 1L;

        //mock renterRepository.findByRoomIdAndRepresent
        Renters renter = new Renters();
        renter.setRoomId(1L);
        renter.setPhoneNumber("0123");
        renter.setRenterFullName("abc");
        renter.setGender(false);
        when(renterRepository.findByRoomIdAndRepresent(anyLong(), any())).thenReturn(renter);

        RentersResponse result = renterService.representRenter(groupId);

        assertEquals(result.getRoomId(), 1L);
        assertEquals(result.getRenterFullName(), "abc");
        assertEquals(result.getGender(), false);
        assertEquals(result.getPhoneNumber(), "0123");
    }

    @Test
    void GivenExactValue_Then_renter_ResultRentersResponseExact() {
        Long groupId = 1L;

        //mock renterRepository.findById
        Renters renter = new Renters();
        renter.setRoomId(1L);
        renter.setPhoneNumber("0123");
        renter.setRenterFullName("abc");
        renter.setGender(false);
        Optional<Renters> optionalRenters = Optional.of(renter);
        when(renterRepository.findById(anyLong())).thenReturn(optionalRenters);

        RentersResponse result = renterService.renter(groupId);

        assertEquals(result.getRoomId(), 1L);
        assertEquals(result.getRenterFullName(), "abc");
        assertEquals(result.getGender(), false);
        assertEquals(result.getPhoneNumber(), "0123");
    }

    @Test
    void GivenWrongValue_Then_renter_ThrowBusinessException() {
        Long groupId = 1L;

        //mock renterRepository.findById
        when(renterRepository.findById(anyLong())).thenThrow(new BusinessException(RENTER_NOT_FOUND, "Không tìm thấy khác thuê renter_id : 1"));

        String messageError = "Không tìm thấy khác thuê renter_id : 1";

        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> renterService.renter(groupId)
        );
        assertEquals(thrown.getMessage(), messageError);
        assertEquals(thrown.getErrorStatus(), RENTER_NOT_FOUND);
    }

    @Test
    void GivenExactValue_Then_addRenter_ReturnRentersResponse() {
        //input
        RenterRequest request = new RenterRequest();
        request.setRoomId(1L);
        request.setIdentityCard("abc");
        Long operator = 1L;

        //mock roomService.roomChecker
        Rooms room = new Rooms();
        when(roomService.roomChecker(anyLong())).thenReturn(room);

        //mock renterRepository.findByIdentityNumberAndRoomId
        Optional<Renters> optionalRenters = Optional.empty();
        when(renterRepository.findByIdentityNumberAndRoomId(anyString(), anyLong())).thenReturn(optionalRenters);

        //renterRepository.save
        Renters renter = new Renters();
        renter.setRenterFullName("abc");
        renter.setPhoneNumber("123");
        renter.setEmail("abc@gmail.com");
        when(renterRepository.save(any())).thenReturn(renter);

        RentersResponse result = renterService.addRenter(request, operator);

        assertEquals(result.getRenterFullName(), "abc");
        assertEquals(result.getPhoneNumber(), "123");
        assertEquals(result.getEmail(), "abc@gmail.com");
    }

    @Test
    void GivenWrongValue_Then_addRenter_ThrowBusinessException() {
        //input
        RenterRequest request = new RenterRequest();
        request.setRoomId(1L);
        request.setIdentityCard("abc");
        Long operator = 1L;

        //mock renterRepository.findByIdentityNumberAndRoomId
        Renters renter = new Renters();
        Optional<Renters> optionalRenters = Optional.of(renter);
        when(renterRepository.findByIdentityNumberAndRoomId(anyString(), anyLong())).thenReturn(optionalRenters);

        String messageError = "CMND/CCCD : abc";

        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> renterService.addRenter(request, operator)
        );
        assertEquals(thrown.getMessage(), messageError);
        assertEquals(thrown.getErrorStatus(), RENTER_EXISTED);
    }

    @Test
    void GivenExactValue_Then_addRenter_ReturnRenters() {
        //input
        Renters renter = new Renters();

        //mock renterRepository.save
        Renters renterMock = new Renters();
        renterMock.setEmail("abc@gmail");
        renterMock.setId(1L);
        when(renterRepository.save(any())).thenReturn(renterMock);

        Renters result = renterService.addRenter(renter);

        assertEquals(result.getId(), 1L);
        assertEquals(result.getEmail(), "abc@gmail");
    }

    @Test
    void GivenExactValue_Then_updateRenter_ReturnRentersResponse() {
        //input
        RenterRequest request = new RenterRequest();
        request.setRoomId(1L);
        request.setIdentityCard("abc");
        Long operator = 1L;
        Long id = 1L;

        //mock renterRepository.findById
        Address address = new Address();
        Renters renter = new Renters();
        renter.setRoomId(1L);
        renter.setPhoneNumber("0123");
        renter.setRenterFullName("abc");
        renter.setGender(false);
        renter.setAddress(address);
        Optional<Renters> optionalRenters = Optional.of(renter);
        when(renterRepository.findById(anyLong())).thenReturn(optionalRenters);

        //mock renterRepository.save
        Renters renterMock = new Renters();
        renterMock.setEmail("abc@gmail");
        renterMock.setId(1L);
        when(renterRepository.save(any())).thenReturn(renterMock);

        RentersResponse result = renterService.updateRenter(id, request, operator);

        assertEquals(result.getRenterId(), 1L);
        assertEquals(result.getEmail(), "abc@gmail");
    }

    @Test
    void GivenExactValue_Then_updateRenter_ReturnNull() {
        //input
        Renters request = new Renters();
        request.setRoomId(1L);
        Long operator = 1L;
        Long id = 1L;

        RentersResponse result = renterService.updateRenter(id, request, operator);

        assertEquals(result, null);
    }

    @Test
    void GivenExactValue_Then_deleteRenter_ReturnMessage() {
        //input
        Long id = 1L;

        //mock renterRepository.findById
        Address address = new Address();
        Renters renter = new Renters();
        renter.setRoomId(1L);
        renter.setPhoneNumber("0123");
        renter.setRenterFullName("abc");
        renter.setGender(false);
        renter.setAddress(address);
        Optional<Renters> optionalRenters = Optional.of(renter);
        when(renterRepository.findById(anyLong())).thenReturn(optionalRenters);

        String result = renterService.deleteRenter(id);
        String message = "Xóa khách thuê renter_id: 1 thành công";

        assertEquals(result, message);
    }

    @Test
    void GivenException_Then_deleteRenter_BusinessException() {
        //input
        Long id = 1L;

        //mock renterRepository.findById
        Address address = new Address();
        Renters renter = new Renters();
        renter.setRoomId(1L);
        renter.setPhoneNumber("0123");
        renter.setRenterFullName("abc");
        renter.setGender(false);
        renter.setAddress(address);
        Optional<Renters> optionalRenters = Optional.of(renter);
        when(renterRepository.findById(anyLong())).thenReturn(optionalRenters);

        doThrow(new BusinessException("Xóa khách thuê renter_id: 1 thất bại")).when(renterRepository).delete(any());

        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> renterService.deleteRenter(id)
        );

        String message = "Xóa khách thuê renter_id: 1 thất bại";

        assertEquals(thrown.getMessage(), message);
    }

    @Test
    void GivenExactValue_Then_removeFromRoom_ReturnRentersResponse() {
        //input
        Long id = 1L;
        Long operator = 1L;

        //mock renterRepository.findById
        Address address = new Address();
        Renters renter = new Renters();
        renter.setRoomId(1L);
        renter.setPhoneNumber("0123");
        renter.setRenterFullName("abc");
        renter.setGender(false);
        renter.setAddress(address);
        Optional<Renters> optionalRenters = Optional.of(renter);
        when(renterRepository.findById(anyLong())).thenReturn(optionalRenters);

        //mock renterRepository.save
        Renters renterMock = new Renters();
        renterMock.setEmail("abc@gmail");
        renterMock.setId(1L);
        when(renterRepository.save(any())).thenReturn(renterMock);

        RentersResponse result = renterService.removeFromRoom(id, operator);

        assertEquals(result.getRenterId(), 1L);
        assertEquals(result.getEmail(), "abc@gmail");
    }

    @Test
    void GivenExactValue_Then_findRenter_ReturnRenters() {
        //input
        String identity = "demo";

        //mock renterRepository.findByIdentityNumberAndRoomId
        Renters renters = new Renters();
        renters.setEmail("abc@gmail.com");
        renters.setPhoneNumber("0123");
        when(renterRepository.findByIdentityNumber(anyString())).thenReturn(renters);

        Renters result = renterService.findRenter(identity);

        assertEquals(result.getPhoneNumber(), "0123");
        assertEquals(result.getEmail(), "abc@gmail.com");
    }
}