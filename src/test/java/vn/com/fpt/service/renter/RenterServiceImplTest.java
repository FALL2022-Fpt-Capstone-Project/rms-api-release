package vn.com.fpt.service.renter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;

import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.Identity;
import vn.com.fpt.entity.RackRenters;
import vn.com.fpt.entity.Renters;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.repositories.AddressRepository;
import vn.com.fpt.repositories.AssetTypesRepository;
import vn.com.fpt.repositories.BasicAssetRepository;
import vn.com.fpt.repositories.BasicServicesRepository;
import vn.com.fpt.repositories.ContractRepository;
import vn.com.fpt.repositories.GeneralServiceRepository;
import vn.com.fpt.repositories.GroupRepository;
import vn.com.fpt.repositories.HandOverGeneralServicesRepository;
import vn.com.fpt.repositories.MoneySourceRepository;
import vn.com.fpt.repositories.RackRenterRepository;
import vn.com.fpt.repositories.RenterRepository;
import vn.com.fpt.repositories.RoomAssetRepository;
import vn.com.fpt.repositories.RoomBillRepository;
import vn.com.fpt.repositories.RoomsRepository;
import vn.com.fpt.repositories.ServiceTypesRepository;
import vn.com.fpt.repositories.TableChangeLogRepository;
import vn.com.fpt.requests.RenterRequest;
import vn.com.fpt.responses.RentersResponse;
import vn.com.fpt.service.TableLogComponent;
import vn.com.fpt.service.assets.AssetServiceImpl;
import vn.com.fpt.service.contract.ContractServiceImpl;
import vn.com.fpt.service.group.GroupServiceImpl;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.rooms.RoomServiceImpl;
import vn.com.fpt.service.services.ServicesServiceImpl;
import vn.com.fpt.specification.BaseSpecification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static vn.com.fpt.common.constants.ErrorStatusConstants.*;

@ContextConfiguration(classes = {RenterServiceImpl.class})
@ExtendWith(MockitoExtension.class)
class RenterServiceImplTest {

    @Autowired
    private RenterServiceImpl renterServiceImpl;

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
}