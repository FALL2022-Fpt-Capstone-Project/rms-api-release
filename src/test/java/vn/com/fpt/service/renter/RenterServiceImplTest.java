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

    /**
     * Method under test: {@link RenterServiceImpl#listRenter(Long)}
     */
    @Test
    void testListRenter() {
        when(renterRepository.findAllByRoomId((Long) any())).thenReturn(new ArrayList<>());
        assertTrue(renterServiceImpl.listRenter(123L).isEmpty());
        verify(renterRepository).findAllByRoomId((Long) any());
    }

    /**
     * Method under test: {@link RenterServiceImpl#listRenter(Long)}
     */
    @Test
    void testListRenter2() {
        Address address = new Address();
        address.setAccount(new Account());
        address.setAddressCity("42 Main St");
        address.setAddressDistrict("42 Main St");
        address.setAddressMoreDetails("42 Main St");
        address.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        address.setCreatedBy(1L);
        address.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        address.setModifiedBy(1L);
        address.setRackRenters(new RackRenters());
        address.setRenters(new Renters());

        Account account = new Account();
        account.setAddress(address);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        account.setCreatedBy(1L);
        account.setDeactivate(true);
        account.setFullName("Dr Jane Doe");
        account.setGender(true);
        account.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        account.setModifiedBy(1L);
        account.setOwner(true);
        account.setPassword("iloveyou");
        account.setPhoneNumber("4105551212");
        account.setRoles(new HashSet<>());
        account.setUserName("janedoe");

        Address address1 = new Address();
        address1.setAccount(new Account());
        address1.setAddressCity("42 Main St");
        address1.setAddressDistrict("42 Main St");
        address1.setAddressMoreDetails("42 Main St");
        address1.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setCreatedBy(1L);
        address1.setId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setModifiedBy(1L);
        address1.setRackRenters(new RackRenters());
        address1.setRenters(new Renters());

        RackRenters rackRenters = new RackRenters();
        rackRenters.setAddress(address1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setCreatedBy(1L);
        rackRenters.setEmail("jane.doe@example.org");
        rackRenters.setGender(true);
        rackRenters.setId(123L);
        rackRenters.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setModifiedBy(1L);
        rackRenters.setNote("Note");
        rackRenters.setPhoneNumber("4105551212");
        rackRenters.setRackRenterFullName("Dr Jane Doe");

        Address address2 = new Address();
        address2.setAccount(new Account());
        address2.setAddressCity("42 Main St");
        address2.setAddressDistrict("42 Main St");
        address2.setAddressMoreDetails("42 Main St");
        address2.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setCreatedBy(1L);
        address2.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setModifiedBy(1L);
        address2.setRackRenters(new RackRenters());
        address2.setRenters(new Renters());

        Identity identity = new Identity();
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setCreatedBy(1L);
        identity.setId(123L);
        identity.setIdentityBackImg("Identity Back Img");
        identity.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setModifiedBy(1L);
        identity.setRenters(new Renters());

        Renters renters = new Renters();
        renters.setAddress(address2);
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setCreatedBy(1L);
        renters.setEmail("jane.doe@example.org");
        renters.setGender(true);
        renters.setId(123L);
        renters.setIdentityNumber("42");
        renters.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setModifiedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setModifiedBy(1L);
        renters.setPhoneNumber("4105551212");
        renters.setRenterFullName("Dr Jane Doe");
        renters.setRenterIdentity(identity);
        renters.setRepresent(true);
        renters.setRoomId(123L);

        Address address3 = new Address();
        address3.setAccount(account);
        address3.setAddressCity("42 Main St");
        address3.setAddressDistrict("42 Main St");
        address3.setAddressMoreDetails("42 Main St");
        address3.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setCreatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setCreatedBy(1L);
        address3.setId(123L);
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setModifiedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setModifiedBy(1L);
        address3.setRackRenters(rackRenters);
        address3.setRenters(renters);

        Address address4 = new Address();
        address4.setAccount(new Account());
        address4.setAddressCity("42 Main St");
        address4.setAddressDistrict("42 Main St");
        address4.setAddressMoreDetails("42 Main St");
        address4.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setCreatedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setCreatedBy(1L);
        address4.setId(123L);
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setModifiedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setModifiedBy(1L);
        address4.setRackRenters(new RackRenters());
        address4.setRenters(new Renters());

        Identity identity1 = new Identity();
        LocalDateTime atStartOfDayResult18 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setCreatedAt(Date.from(atStartOfDayResult18.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setCreatedBy(1L);
        identity1.setId(123L);
        identity1.setIdentityBackImg("Identity Back Img");
        identity1.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult19 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setModifiedAt(Date.from(atStartOfDayResult19.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setModifiedBy(1L);
        identity1.setRenters(new Renters());

        Renters renters1 = new Renters();
        renters1.setAddress(address4);
        LocalDateTime atStartOfDayResult20 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setCreatedAt(Date.from(atStartOfDayResult20.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setCreatedBy(1L);
        renters1.setEmail("jane.doe@example.org");
        renters1.setGender(true);
        renters1.setId(123L);
        renters1.setIdentityNumber("42");
        renters1.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult21 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setModifiedAt(Date.from(atStartOfDayResult21.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setModifiedBy(1L);
        renters1.setPhoneNumber("4105551212");
        renters1.setRenterFullName("Dr Jane Doe");
        renters1.setRenterIdentity(identity1);
        renters1.setRepresent(true);
        renters1.setRoomId(123L);

        Identity identity2 = new Identity();
        LocalDateTime atStartOfDayResult22 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setCreatedAt(Date.from(atStartOfDayResult22.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setCreatedBy(1L);
        identity2.setId(123L);
        identity2.setIdentityBackImg("Identity Back Img");
        identity2.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult23 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setModifiedAt(Date.from(atStartOfDayResult23.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setModifiedBy(1L);
        identity2.setRenters(renters1);

        Renters renters2 = new Renters();
        renters2.setAddress(address3);
        LocalDateTime atStartOfDayResult24 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setCreatedAt(Date.from(atStartOfDayResult24.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setCreatedBy(1L);
        renters2.setEmail("jane.doe@example.org");
        renters2.setGender(true);
        renters2.setId(123L);
        renters2.setIdentityNumber("42");
        renters2.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult25 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setModifiedAt(Date.from(atStartOfDayResult25.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setModifiedBy(1L);
        renters2.setPhoneNumber("4105551212");
        renters2.setRenterFullName("Dr Jane Doe");
        renters2.setRenterIdentity(identity2);
        renters2.setRepresent(true);
        renters2.setRoomId(123L);

        ArrayList<Renters> rentersList = new ArrayList<>();
        rentersList.add(renters2);
        when(renterRepository.findAllByRoomId((Long) any())).thenReturn(rentersList);
        assertEquals(1, renterServiceImpl.listRenter(123L).size());
        verify(renterRepository).findAllByRoomId((Long) any());
    }

    /**
     * Method under test: {@link RenterServiceImpl#listRenter(Long)}
     */
    @Test
    void testListRenter3() {
        when(renterRepository.findAllByRoomId((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> renterServiceImpl.listRenter(123L));
        verify(renterRepository).findAllByRoomId((Long) any());
    }

    /**
     * Method under test: {@link RenterServiceImpl#listRenter(Long, Boolean, String, String, Long)}
     */
    @Test
    void testListRenter4() {
        when(renterRepository.findAll((Specification<Renters>) any())).thenReturn(new ArrayList<>());
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        assertTrue(renterServiceImpl.listRenter(123L, true, "Name", "4105551212", 1L).isEmpty());
        verify(renterRepository).findAll((Specification<Renters>) any());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
    }

    /**
     * Method under test: {@link RenterServiceImpl#listRenter(Long, Boolean, String, String, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListRenter5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   vn.com.fpt.common.BusinessException: roomId
        //       at jdk.proxy4.$Proxy93.listRoom(null)
        //       at vn.com.fpt.service.renter.RenterServiceImpl.listRenter(RenterServiceImpl.java:50)
        //   See https://diff.blue/R013 to resolve this issue.

        when(renterRepository.findAll((Specification<Renters>) any())).thenReturn(new ArrayList<>());
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenThrow(new BusinessException("roomId"));
        renterServiceImpl.listRenter(123L, true, "Name", "4105551212", 1L);
    }

    /**
     * Method under test: {@link RenterServiceImpl#listRenter(Long, Boolean, String, String, Long)}
     */
    @Test
    void testListRenter6() {
        Address address = new Address();
        address.setAccount(new Account());
        address.setAddressCity("42 Main St");
        address.setAddressDistrict("42 Main St");
        address.setAddressMoreDetails("42 Main St");
        address.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        address.setCreatedBy(1L);
        address.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        address.setModifiedBy(1L);
        address.setRackRenters(new RackRenters());
        address.setRenters(new Renters());

        Account account = new Account();
        account.setAddress(address);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        account.setCreatedBy(1L);
        account.setDeactivate(true);
        account.setFullName("Dr Jane Doe");
        account.setGender(true);
        account.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        account.setModifiedBy(1L);
        account.setOwner(true);
        account.setPassword("iloveyou");
        account.setPhoneNumber("4105551212");
        account.setRoles(new HashSet<>());
        account.setUserName("janedoe");

        Address address1 = new Address();
        address1.setAccount(new Account());
        address1.setAddressCity("42 Main St");
        address1.setAddressDistrict("42 Main St");
        address1.setAddressMoreDetails("42 Main St");
        address1.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setCreatedBy(1L);
        address1.setId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setModifiedBy(1L);
        address1.setRackRenters(new RackRenters());
        address1.setRenters(new Renters());

        RackRenters rackRenters = new RackRenters();
        rackRenters.setAddress(address1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setCreatedBy(1L);
        rackRenters.setEmail("jane.doe@example.org");
        rackRenters.setGender(true);
        rackRenters.setId(123L);
        rackRenters.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setModifiedBy(1L);
        rackRenters.setNote("roomId");
        rackRenters.setPhoneNumber("4105551212");
        rackRenters.setRackRenterFullName("Dr Jane Doe");

        Address address2 = new Address();
        address2.setAccount(new Account());
        address2.setAddressCity("42 Main St");
        address2.setAddressDistrict("42 Main St");
        address2.setAddressMoreDetails("42 Main St");
        address2.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setCreatedBy(1L);
        address2.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setModifiedBy(1L);
        address2.setRackRenters(new RackRenters());
        address2.setRenters(new Renters());

        Identity identity = new Identity();
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setCreatedBy(1L);
        identity.setId(123L);
        identity.setIdentityBackImg("roomId");
        identity.setIdentityFrontImg("roomId");
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setModifiedBy(1L);
        identity.setRenters(new Renters());

        Renters renters = new Renters();
        renters.setAddress(address2);
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setCreatedBy(1L);
        renters.setEmail("jane.doe@example.org");
        renters.setGender(true);
        renters.setId(123L);
        renters.setIdentityNumber("42");
        renters.setLicensePlates("roomId");
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setModifiedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setModifiedBy(1L);
        renters.setPhoneNumber("4105551212");
        renters.setRenterFullName("Dr Jane Doe");
        renters.setRenterIdentity(identity);
        renters.setRepresent(true);
        renters.setRoomId(123L);

        Address address3 = new Address();
        address3.setAccount(account);
        address3.setAddressCity("42 Main St");
        address3.setAddressDistrict("42 Main St");
        address3.setAddressMoreDetails("42 Main St");
        address3.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setCreatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setCreatedBy(1L);
        address3.setId(123L);
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setModifiedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setModifiedBy(1L);
        address3.setRackRenters(rackRenters);
        address3.setRenters(renters);

        Address address4 = new Address();
        address4.setAccount(new Account());
        address4.setAddressCity("42 Main St");
        address4.setAddressDistrict("42 Main St");
        address4.setAddressMoreDetails("42 Main St");
        address4.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setCreatedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setCreatedBy(1L);
        address4.setId(123L);
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setModifiedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setModifiedBy(1L);
        address4.setRackRenters(new RackRenters());
        address4.setRenters(new Renters());

        Identity identity1 = new Identity();
        LocalDateTime atStartOfDayResult18 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setCreatedAt(Date.from(atStartOfDayResult18.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setCreatedBy(1L);
        identity1.setId(123L);
        identity1.setIdentityBackImg("roomId");
        identity1.setIdentityFrontImg("roomId");
        LocalDateTime atStartOfDayResult19 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setModifiedAt(Date.from(atStartOfDayResult19.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setModifiedBy(1L);
        identity1.setRenters(new Renters());

        Renters renters1 = new Renters();
        renters1.setAddress(address4);
        LocalDateTime atStartOfDayResult20 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setCreatedAt(Date.from(atStartOfDayResult20.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setCreatedBy(1L);
        renters1.setEmail("jane.doe@example.org");
        renters1.setGender(true);
        renters1.setId(123L);
        renters1.setIdentityNumber("42");
        renters1.setLicensePlates("roomId");
        LocalDateTime atStartOfDayResult21 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setModifiedAt(Date.from(atStartOfDayResult21.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setModifiedBy(1L);
        renters1.setPhoneNumber("4105551212");
        renters1.setRenterFullName("Dr Jane Doe");
        renters1.setRenterIdentity(identity1);
        renters1.setRepresent(true);
        renters1.setRoomId(123L);

        Identity identity2 = new Identity();
        LocalDateTime atStartOfDayResult22 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setCreatedAt(Date.from(atStartOfDayResult22.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setCreatedBy(1L);
        identity2.setId(123L);
        identity2.setIdentityBackImg("roomId");
        identity2.setIdentityFrontImg("roomId");
        LocalDateTime atStartOfDayResult23 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setModifiedAt(Date.from(atStartOfDayResult23.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setModifiedBy(1L);
        identity2.setRenters(renters1);

        Renters renters2 = new Renters();
        renters2.setAddress(address3);
        LocalDateTime atStartOfDayResult24 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setCreatedAt(Date.from(atStartOfDayResult24.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setCreatedBy(1L);
        renters2.setEmail("jane.doe@example.org");
        renters2.setGender(true);
        renters2.setId(123L);
        renters2.setIdentityNumber("42");
        renters2.setLicensePlates("roomId");
        LocalDateTime atStartOfDayResult25 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setModifiedAt(Date.from(atStartOfDayResult25.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setModifiedBy(1L);
        renters2.setPhoneNumber("4105551212");
        renters2.setRenterFullName("Dr Jane Doe");
        renters2.setRenterIdentity(identity2);
        renters2.setRepresent(true);
        renters2.setRoomId(123L);

        ArrayList<Renters> rentersList = new ArrayList<>();
        rentersList.add(renters2);
        when(renterRepository.findAll((Specification<Renters>) any())).thenReturn(rentersList);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        assertEquals(1, renterServiceImpl.listRenter(123L, true, "Name", "4105551212", 1L).size());
        verify(renterRepository).findAll((Specification<Renters>) any());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
    }

    /**
     * Method under test: {@link RenterServiceImpl#listRenter(Long, Boolean, String, String, Long)}
     */
    @Test
    void testListRenter7() {
        when(renterRepository.findAll((Specification<Renters>) any())).thenReturn(new ArrayList<>());
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        assertTrue(renterServiceImpl.listRenter(123L, true, "", "4105551212", 1L).isEmpty());
        verify(renterRepository).findAll((Specification<Renters>) any());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
    }

    /**
     * Method under test: {@link RenterServiceImpl#listRenter(Long, Boolean, String, String, Long)}
     */
    @Test
    void testListRenter8() {
        when(renterRepository.findAll((Specification<Renters>) any())).thenReturn(new ArrayList<>());
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        assertTrue(renterServiceImpl.listRenter(123L, true, "Name", "", 1L).isEmpty());
        verify(renterRepository).findAll((Specification<Renters>) any());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
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

    /**
     * Method under test: {@link RenterServiceImpl#listMember(Long)}
     */
    @Test
    void testListMember() {
        when(renterRepository.findAllByRoomIdAndRepresent((Long) any(), (Boolean) any())).thenReturn(new ArrayList<>());
        assertTrue(renterServiceImpl.listMember(123L).isEmpty());
        verify(renterRepository).findAllByRoomIdAndRepresent((Long) any(), (Boolean) any());
    }

    /**
     * Method under test: {@link RenterServiceImpl#listMember(Long)}
     */
    @Test
    void testListMember2() {
        Address address = new Address();
        address.setAccount(new Account());
        address.setAddressCity("42 Main St");
        address.setAddressDistrict("42 Main St");
        address.setAddressMoreDetails("42 Main St");
        address.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        address.setCreatedBy(1L);
        address.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        address.setModifiedBy(1L);
        address.setRackRenters(new RackRenters());
        address.setRenters(new Renters());

        Account account = new Account();
        account.setAddress(address);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        account.setCreatedBy(1L);
        account.setDeactivate(true);
        account.setFullName("Dr Jane Doe");
        account.setGender(true);
        account.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        account.setModifiedBy(1L);
        account.setOwner(true);
        account.setPassword("iloveyou");
        account.setPhoneNumber("4105551212");
        account.setRoles(new HashSet<>());
        account.setUserName("janedoe");

        Address address1 = new Address();
        address1.setAccount(new Account());
        address1.setAddressCity("42 Main St");
        address1.setAddressDistrict("42 Main St");
        address1.setAddressMoreDetails("42 Main St");
        address1.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setCreatedBy(1L);
        address1.setId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setModifiedBy(1L);
        address1.setRackRenters(new RackRenters());
        address1.setRenters(new Renters());

        RackRenters rackRenters = new RackRenters();
        rackRenters.setAddress(address1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setCreatedBy(1L);
        rackRenters.setEmail("jane.doe@example.org");
        rackRenters.setGender(true);
        rackRenters.setId(123L);
        rackRenters.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setModifiedBy(1L);
        rackRenters.setNote("Note");
        rackRenters.setPhoneNumber("4105551212");
        rackRenters.setRackRenterFullName("Dr Jane Doe");

        Address address2 = new Address();
        address2.setAccount(new Account());
        address2.setAddressCity("42 Main St");
        address2.setAddressDistrict("42 Main St");
        address2.setAddressMoreDetails("42 Main St");
        address2.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setCreatedBy(1L);
        address2.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setModifiedBy(1L);
        address2.setRackRenters(new RackRenters());
        address2.setRenters(new Renters());

        Identity identity = new Identity();
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setCreatedBy(1L);
        identity.setId(123L);
        identity.setIdentityBackImg("Identity Back Img");
        identity.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setModifiedBy(1L);
        identity.setRenters(new Renters());

        Renters renters = new Renters();
        renters.setAddress(address2);
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setCreatedBy(1L);
        renters.setEmail("jane.doe@example.org");
        renters.setGender(true);
        renters.setId(123L);
        renters.setIdentityNumber("42");
        renters.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setModifiedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setModifiedBy(1L);
        renters.setPhoneNumber("4105551212");
        renters.setRenterFullName("Dr Jane Doe");
        renters.setRenterIdentity(identity);
        renters.setRepresent(true);
        renters.setRoomId(123L);

        Address address3 = new Address();
        address3.setAccount(account);
        address3.setAddressCity("42 Main St");
        address3.setAddressDistrict("42 Main St");
        address3.setAddressMoreDetails("42 Main St");
        address3.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setCreatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setCreatedBy(1L);
        address3.setId(123L);
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setModifiedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setModifiedBy(1L);
        address3.setRackRenters(rackRenters);
        address3.setRenters(renters);

        Address address4 = new Address();
        address4.setAccount(new Account());
        address4.setAddressCity("42 Main St");
        address4.setAddressDistrict("42 Main St");
        address4.setAddressMoreDetails("42 Main St");
        address4.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setCreatedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setCreatedBy(1L);
        address4.setId(123L);
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setModifiedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setModifiedBy(1L);
        address4.setRackRenters(new RackRenters());
        address4.setRenters(new Renters());

        Identity identity1 = new Identity();
        LocalDateTime atStartOfDayResult18 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setCreatedAt(Date.from(atStartOfDayResult18.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setCreatedBy(1L);
        identity1.setId(123L);
        identity1.setIdentityBackImg("Identity Back Img");
        identity1.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult19 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setModifiedAt(Date.from(atStartOfDayResult19.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setModifiedBy(1L);
        identity1.setRenters(new Renters());

        Renters renters1 = new Renters();
        renters1.setAddress(address4);
        LocalDateTime atStartOfDayResult20 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setCreatedAt(Date.from(atStartOfDayResult20.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setCreatedBy(1L);
        renters1.setEmail("jane.doe@example.org");
        renters1.setGender(true);
        renters1.setId(123L);
        renters1.setIdentityNumber("42");
        renters1.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult21 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setModifiedAt(Date.from(atStartOfDayResult21.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setModifiedBy(1L);
        renters1.setPhoneNumber("4105551212");
        renters1.setRenterFullName("Dr Jane Doe");
        renters1.setRenterIdentity(identity1);
        renters1.setRepresent(true);
        renters1.setRoomId(123L);

        Identity identity2 = new Identity();
        LocalDateTime atStartOfDayResult22 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setCreatedAt(Date.from(atStartOfDayResult22.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setCreatedBy(1L);
        identity2.setId(123L);
        identity2.setIdentityBackImg("Identity Back Img");
        identity2.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult23 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setModifiedAt(Date.from(atStartOfDayResult23.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setModifiedBy(1L);
        identity2.setRenters(renters1);

        Renters renters2 = new Renters();
        renters2.setAddress(address3);
        LocalDateTime atStartOfDayResult24 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setCreatedAt(Date.from(atStartOfDayResult24.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setCreatedBy(1L);
        renters2.setEmail("jane.doe@example.org");
        renters2.setGender(true);
        renters2.setId(123L);
        renters2.setIdentityNumber("42");
        renters2.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult25 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setModifiedAt(Date.from(atStartOfDayResult25.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setModifiedBy(1L);
        renters2.setPhoneNumber("4105551212");
        renters2.setRenterFullName("Dr Jane Doe");
        renters2.setRenterIdentity(identity2);
        renters2.setRepresent(true);
        renters2.setRoomId(123L);

        ArrayList<Renters> rentersList = new ArrayList<>();
        rentersList.add(renters2);
        when(renterRepository.findAllByRoomIdAndRepresent((Long) any(), (Boolean) any())).thenReturn(rentersList);
        assertEquals(1, renterServiceImpl.listMember(123L).size());
        verify(renterRepository).findAllByRoomIdAndRepresent((Long) any(), (Boolean) any());
    }

    /**
     * Method under test: {@link RenterServiceImpl#listMember(Long)}
     */
    @Test
    void testListMember3() {
        when(renterRepository.findAllByRoomIdAndRepresent((Long) any(), (Boolean) any()))
                .thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> renterServiceImpl.listMember(123L));
        verify(renterRepository).findAllByRoomIdAndRepresent((Long) any(), (Boolean) any());
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

    /**
     * Method under test: {@link RenterServiceImpl#representRenter(Long)}
     */
    @Test
    void testRepresentRenter() {
        Account account = new Account();
        account.setAddress(new Address());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        account.setCreatedBy(1L);
        account.setDeactivate(true);
        account.setFullName("Dr Jane Doe");
        account.setGender(true);
        account.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        account.setModifiedBy(1L);
        account.setOwner(true);
        account.setPassword("iloveyou");
        account.setPhoneNumber("4105551212");
        account.setRoles(new HashSet<>());
        account.setUserName("janedoe");

        RackRenters rackRenters = new RackRenters();
        rackRenters.setAddress(new Address());
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setCreatedBy(1L);
        rackRenters.setEmail("jane.doe@example.org");
        rackRenters.setGender(true);
        rackRenters.setId(123L);
        rackRenters.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setModifiedBy(1L);
        rackRenters.setNote("Note");
        rackRenters.setPhoneNumber("4105551212");
        rackRenters.setRackRenterFullName("Dr Jane Doe");

        Renters renters = new Renters();
        renters.setAddress(new Address());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setCreatedBy(1L);
        renters.setEmail("jane.doe@example.org");
        renters.setGender(true);
        renters.setId(123L);
        renters.setIdentityNumber("42");
        renters.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setModifiedBy(1L);
        renters.setPhoneNumber("4105551212");
        renters.setRenterFullName("Dr Jane Doe");
        renters.setRenterIdentity(new Identity());
        renters.setRepresent(true);
        renters.setRoomId(123L);

        Address address = new Address();
        address.setAccount(account);
        address.setAddressCity("42 Main St");
        address.setAddressDistrict("42 Main St");
        address.setAddressMoreDetails("42 Main St");
        address.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        address.setCreatedBy(1L);
        address.setId(123L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        address.setModifiedBy(1L);
        address.setRackRenters(rackRenters);
        address.setRenters(renters);

        Account account1 = new Account();
        account1.setAddress(address);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account1.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        account1.setCreatedBy(1L);
        account1.setDeactivate(true);
        account1.setFullName("Dr Jane Doe");
        account1.setGender(true);
        account1.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account1.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        account1.setModifiedBy(1L);
        account1.setOwner(true);
        account1.setPassword("iloveyou");
        account1.setPhoneNumber("4105551212");
        account1.setRoles(new HashSet<>());
        account1.setUserName("janedoe");

        Account account2 = new Account();
        account2.setAddress(new Address());
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account2.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        account2.setCreatedBy(1L);
        account2.setDeactivate(true);
        account2.setFullName("Dr Jane Doe");
        account2.setGender(true);
        account2.setId(123L);
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account2.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        account2.setModifiedBy(1L);
        account2.setOwner(true);
        account2.setPassword("iloveyou");
        account2.setPhoneNumber("4105551212");
        account2.setRoles(new HashSet<>());
        account2.setUserName("janedoe");

        RackRenters rackRenters1 = new RackRenters();
        rackRenters1.setAddress(new Address());
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setCreatedBy(1L);
        rackRenters1.setEmail("jane.doe@example.org");
        rackRenters1.setGender(true);
        rackRenters1.setId(123L);
        rackRenters1.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setModifiedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setModifiedBy(1L);
        rackRenters1.setNote("Note");
        rackRenters1.setPhoneNumber("4105551212");
        rackRenters1.setRackRenterFullName("Dr Jane Doe");

        Renters renters1 = new Renters();
        renters1.setAddress(new Address());
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setCreatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setCreatedBy(1L);
        renters1.setEmail("jane.doe@example.org");
        renters1.setGender(true);
        renters1.setId(123L);
        renters1.setIdentityNumber("42");
        renters1.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setModifiedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setModifiedBy(1L);
        renters1.setPhoneNumber("4105551212");
        renters1.setRenterFullName("Dr Jane Doe");
        renters1.setRenterIdentity(new Identity());
        renters1.setRepresent(true);
        renters1.setRoomId(123L);

        Address address1 = new Address();
        address1.setAccount(account2);
        address1.setAddressCity("42 Main St");
        address1.setAddressDistrict("42 Main St");
        address1.setAddressMoreDetails("42 Main St");
        address1.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setCreatedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setCreatedBy(1L);
        address1.setId(123L);
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setModifiedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setModifiedBy(1L);
        address1.setRackRenters(rackRenters1);
        address1.setRenters(renters1);

        RackRenters rackRenters2 = new RackRenters();
        rackRenters2.setAddress(address1);
        LocalDateTime atStartOfDayResult18 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters2.setCreatedAt(Date.from(atStartOfDayResult18.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters2.setCreatedBy(1L);
        rackRenters2.setEmail("jane.doe@example.org");
        rackRenters2.setGender(true);
        rackRenters2.setId(123L);
        rackRenters2.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult19 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters2.setModifiedAt(Date.from(atStartOfDayResult19.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters2.setModifiedBy(1L);
        rackRenters2.setNote("Note");
        rackRenters2.setPhoneNumber("4105551212");
        rackRenters2.setRackRenterFullName("Dr Jane Doe");

        Account account3 = new Account();
        account3.setAddress(new Address());
        LocalDateTime atStartOfDayResult20 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account3.setCreatedAt(Date.from(atStartOfDayResult20.atZone(ZoneId.of("UTC")).toInstant()));
        account3.setCreatedBy(1L);
        account3.setDeactivate(true);
        account3.setFullName("Dr Jane Doe");
        account3.setGender(true);
        account3.setId(123L);
        LocalDateTime atStartOfDayResult21 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account3.setModifiedAt(Date.from(atStartOfDayResult21.atZone(ZoneId.of("UTC")).toInstant()));
        account3.setModifiedBy(1L);
        account3.setOwner(true);
        account3.setPassword("iloveyou");
        account3.setPhoneNumber("4105551212");
        account3.setRoles(new HashSet<>());
        account3.setUserName("janedoe");

        RackRenters rackRenters3 = new RackRenters();
        rackRenters3.setAddress(new Address());
        LocalDateTime atStartOfDayResult22 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters3.setCreatedAt(Date.from(atStartOfDayResult22.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters3.setCreatedBy(1L);
        rackRenters3.setEmail("jane.doe@example.org");
        rackRenters3.setGender(true);
        rackRenters3.setId(123L);
        rackRenters3.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult23 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters3.setModifiedAt(Date.from(atStartOfDayResult23.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters3.setModifiedBy(1L);
        rackRenters3.setNote("Note");
        rackRenters3.setPhoneNumber("4105551212");
        rackRenters3.setRackRenterFullName("Dr Jane Doe");

        Renters renters2 = new Renters();
        renters2.setAddress(new Address());
        LocalDateTime atStartOfDayResult24 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setCreatedAt(Date.from(atStartOfDayResult24.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setCreatedBy(1L);
        renters2.setEmail("jane.doe@example.org");
        renters2.setGender(true);
        renters2.setId(123L);
        renters2.setIdentityNumber("42");
        renters2.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult25 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setModifiedAt(Date.from(atStartOfDayResult25.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setModifiedBy(1L);
        renters2.setPhoneNumber("4105551212");
        renters2.setRenterFullName("Dr Jane Doe");
        renters2.setRenterIdentity(new Identity());
        renters2.setRepresent(true);
        renters2.setRoomId(123L);

        Address address2 = new Address();
        address2.setAccount(account3);
        address2.setAddressCity("42 Main St");
        address2.setAddressDistrict("42 Main St");
        address2.setAddressMoreDetails("42 Main St");
        address2.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult26 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setCreatedAt(Date.from(atStartOfDayResult26.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setCreatedBy(1L);
        address2.setId(123L);
        LocalDateTime atStartOfDayResult27 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setModifiedAt(Date.from(atStartOfDayResult27.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setModifiedBy(1L);
        address2.setRackRenters(rackRenters3);
        address2.setRenters(renters2);

        Renters renters3 = new Renters();
        renters3.setAddress(new Address());
        LocalDateTime atStartOfDayResult28 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters3.setCreatedAt(Date.from(atStartOfDayResult28.atZone(ZoneId.of("UTC")).toInstant()));
        renters3.setCreatedBy(1L);
        renters3.setEmail("jane.doe@example.org");
        renters3.setGender(true);
        renters3.setId(123L);
        renters3.setIdentityNumber("42");
        renters3.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult29 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters3.setModifiedAt(Date.from(atStartOfDayResult29.atZone(ZoneId.of("UTC")).toInstant()));
        renters3.setModifiedBy(1L);
        renters3.setPhoneNumber("4105551212");
        renters3.setRenterFullName("Dr Jane Doe");
        renters3.setRenterIdentity(new Identity());
        renters3.setRepresent(true);
        renters3.setRoomId(123L);

        Identity identity = new Identity();
        LocalDateTime atStartOfDayResult30 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setCreatedAt(Date.from(atStartOfDayResult30.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setCreatedBy(1L);
        identity.setId(123L);
        identity.setIdentityBackImg("Identity Back Img");
        identity.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult31 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setModifiedAt(Date.from(atStartOfDayResult31.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setModifiedBy(1L);
        identity.setRenters(renters3);

        Renters renters4 = new Renters();
        renters4.setAddress(address2);
        LocalDateTime atStartOfDayResult32 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters4.setCreatedAt(Date.from(atStartOfDayResult32.atZone(ZoneId.of("UTC")).toInstant()));
        renters4.setCreatedBy(1L);
        renters4.setEmail("jane.doe@example.org");
        renters4.setGender(true);
        renters4.setId(123L);
        renters4.setIdentityNumber("42");
        renters4.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult33 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters4.setModifiedAt(Date.from(atStartOfDayResult33.atZone(ZoneId.of("UTC")).toInstant()));
        renters4.setModifiedBy(1L);
        renters4.setPhoneNumber("4105551212");
        renters4.setRenterFullName("Dr Jane Doe");
        renters4.setRenterIdentity(identity);
        renters4.setRepresent(true);
        renters4.setRoomId(123L);

        Address address3 = new Address();
        address3.setAccount(account1);
        address3.setAddressCity("42 Main St");
        address3.setAddressDistrict("42 Main St");
        address3.setAddressMoreDetails("42 Main St");
        address3.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult34 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setCreatedAt(Date.from(atStartOfDayResult34.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setCreatedBy(1L);
        address3.setId(123L);
        LocalDateTime atStartOfDayResult35 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setModifiedAt(Date.from(atStartOfDayResult35.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setModifiedBy(1L);
        address3.setRackRenters(rackRenters2);
        address3.setRenters(renters4);

        Account account4 = new Account();
        account4.setAddress(new Address());
        LocalDateTime atStartOfDayResult36 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account4.setCreatedAt(Date.from(atStartOfDayResult36.atZone(ZoneId.of("UTC")).toInstant()));
        account4.setCreatedBy(1L);
        account4.setDeactivate(true);
        account4.setFullName("Dr Jane Doe");
        account4.setGender(true);
        account4.setId(123L);
        LocalDateTime atStartOfDayResult37 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account4.setModifiedAt(Date.from(atStartOfDayResult37.atZone(ZoneId.of("UTC")).toInstant()));
        account4.setModifiedBy(1L);
        account4.setOwner(true);
        account4.setPassword("iloveyou");
        account4.setPhoneNumber("4105551212");
        account4.setRoles(new HashSet<>());
        account4.setUserName("janedoe");

        RackRenters rackRenters4 = new RackRenters();
        rackRenters4.setAddress(new Address());
        LocalDateTime atStartOfDayResult38 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters4.setCreatedAt(Date.from(atStartOfDayResult38.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters4.setCreatedBy(1L);
        rackRenters4.setEmail("jane.doe@example.org");
        rackRenters4.setGender(true);
        rackRenters4.setId(123L);
        rackRenters4.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult39 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters4.setModifiedAt(Date.from(atStartOfDayResult39.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters4.setModifiedBy(1L);
        rackRenters4.setNote("Note");
        rackRenters4.setPhoneNumber("4105551212");
        rackRenters4.setRackRenterFullName("Dr Jane Doe");

        Renters renters5 = new Renters();
        renters5.setAddress(new Address());
        LocalDateTime atStartOfDayResult40 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters5.setCreatedAt(Date.from(atStartOfDayResult40.atZone(ZoneId.of("UTC")).toInstant()));
        renters5.setCreatedBy(1L);
        renters5.setEmail("jane.doe@example.org");
        renters5.setGender(true);
        renters5.setId(123L);
        renters5.setIdentityNumber("42");
        renters5.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult41 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters5.setModifiedAt(Date.from(atStartOfDayResult41.atZone(ZoneId.of("UTC")).toInstant()));
        renters5.setModifiedBy(1L);
        renters5.setPhoneNumber("4105551212");
        renters5.setRenterFullName("Dr Jane Doe");
        renters5.setRenterIdentity(new Identity());
        renters5.setRepresent(true);
        renters5.setRoomId(123L);

        Address address4 = new Address();
        address4.setAccount(account4);
        address4.setAddressCity("42 Main St");
        address4.setAddressDistrict("42 Main St");
        address4.setAddressMoreDetails("42 Main St");
        address4.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult42 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setCreatedAt(Date.from(atStartOfDayResult42.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setCreatedBy(1L);
        address4.setId(123L);
        LocalDateTime atStartOfDayResult43 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setModifiedAt(Date.from(atStartOfDayResult43.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setModifiedBy(1L);
        address4.setRackRenters(rackRenters4);
        address4.setRenters(renters5);

        Renters renters6 = new Renters();
        renters6.setAddress(new Address());
        LocalDateTime atStartOfDayResult44 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters6.setCreatedAt(Date.from(atStartOfDayResult44.atZone(ZoneId.of("UTC")).toInstant()));
        renters6.setCreatedBy(1L);
        renters6.setEmail("jane.doe@example.org");
        renters6.setGender(true);
        renters6.setId(123L);
        renters6.setIdentityNumber("42");
        renters6.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult45 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters6.setModifiedAt(Date.from(atStartOfDayResult45.atZone(ZoneId.of("UTC")).toInstant()));
        renters6.setModifiedBy(1L);
        renters6.setPhoneNumber("4105551212");
        renters6.setRenterFullName("Dr Jane Doe");
        renters6.setRenterIdentity(new Identity());
        renters6.setRepresent(true);
        renters6.setRoomId(123L);

        Identity identity1 = new Identity();
        LocalDateTime atStartOfDayResult46 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setCreatedAt(Date.from(atStartOfDayResult46.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setCreatedBy(1L);
        identity1.setId(123L);
        identity1.setIdentityBackImg("Identity Back Img");
        identity1.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult47 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setModifiedAt(Date.from(atStartOfDayResult47.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setModifiedBy(1L);
        identity1.setRenters(renters6);

        Renters renters7 = new Renters();
        renters7.setAddress(address4);
        LocalDateTime atStartOfDayResult48 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters7.setCreatedAt(Date.from(atStartOfDayResult48.atZone(ZoneId.of("UTC")).toInstant()));
        renters7.setCreatedBy(1L);
        renters7.setEmail("jane.doe@example.org");
        renters7.setGender(true);
        renters7.setId(123L);
        renters7.setIdentityNumber("42");
        renters7.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult49 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters7.setModifiedAt(Date.from(atStartOfDayResult49.atZone(ZoneId.of("UTC")).toInstant()));
        renters7.setModifiedBy(1L);
        renters7.setPhoneNumber("4105551212");
        renters7.setRenterFullName("Dr Jane Doe");
        renters7.setRenterIdentity(identity1);
        renters7.setRepresent(true);
        renters7.setRoomId(123L);

        Identity identity2 = new Identity();
        LocalDateTime atStartOfDayResult50 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setCreatedAt(Date.from(atStartOfDayResult50.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setCreatedBy(1L);
        identity2.setId(123L);
        identity2.setIdentityBackImg("Identity Back Img");
        identity2.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult51 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setModifiedAt(Date.from(atStartOfDayResult51.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setModifiedBy(1L);
        identity2.setRenters(renters7);

        Renters renters8 = new Renters();
        renters8.setAddress(address3);
        LocalDateTime atStartOfDayResult52 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters8.setCreatedAt(Date.from(atStartOfDayResult52.atZone(ZoneId.of("UTC")).toInstant()));
        renters8.setCreatedBy(1L);
        renters8.setEmail("jane.doe@example.org");
        renters8.setGender(true);
        renters8.setId(123L);
        renters8.setIdentityNumber("42");
        renters8.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult53 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters8.setModifiedAt(Date.from(atStartOfDayResult53.atZone(ZoneId.of("UTC")).toInstant()));
        renters8.setModifiedBy(1L);
        renters8.setPhoneNumber("4105551212");
        renters8.setRenterFullName("Dr Jane Doe");
        renters8.setRenterIdentity(identity2);
        renters8.setRepresent(true);
        renters8.setRoomId(123L);
        when(renterRepository.findByRoomIdAndRepresent((Long) any(), (Boolean) any())).thenReturn(renters8);
        RentersResponse actualRepresentRenterResult = renterServiceImpl.representRenter(123L);
        assertSame(address3, actualRepresentRenterResult.getAddress());
        assertEquals(123L, actualRepresentRenterResult.getRoomId().longValue());
        assertEquals("jane.doe@example.org", actualRepresentRenterResult.getEmail());
        assertEquals("42", actualRepresentRenterResult.getIdentityNumber());
        assertEquals(123L, actualRepresentRenterResult.getRenterId().longValue());
        assertSame(identity2, actualRepresentRenterResult.getRenterIdentity());
        assertEquals("License Plates", actualRepresentRenterResult.getLicensePlates());
        assertTrue(actualRepresentRenterResult.getRepresent());
        assertTrue(actualRepresentRenterResult.getGender());
        assertEquals("4105551212", actualRepresentRenterResult.getPhoneNumber());
        assertEquals("Dr Jane Doe", actualRepresentRenterResult.getRenterFullName());
        verify(renterRepository).findByRoomIdAndRepresent((Long) any(), (Boolean) any());
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

    /**
     * Method under test: {@link RenterServiceImpl#renter(Long)}
     */
    @Test
    void testRenter() {
        Address address = new Address();
        address.setAccount(new Account());
        address.setAddressCity("42 Main St");
        address.setAddressDistrict("42 Main St");
        address.setAddressMoreDetails("42 Main St");
        address.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        address.setCreatedBy(1L);
        address.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        address.setModifiedBy(1L);
        address.setRackRenters(new RackRenters());
        address.setRenters(new Renters());

        Account account = new Account();
        account.setAddress(address);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        account.setCreatedBy(1L);
        account.setDeactivate(true);
        account.setFullName("Dr Jane Doe");
        account.setGender(true);
        account.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        account.setModifiedBy(1L);
        account.setOwner(true);
        account.setPassword("iloveyou");
        account.setPhoneNumber("4105551212");
        account.setRoles(new HashSet<>());
        account.setUserName("janedoe");

        Address address1 = new Address();
        address1.setAccount(new Account());
        address1.setAddressCity("42 Main St");
        address1.setAddressDistrict("42 Main St");
        address1.setAddressMoreDetails("42 Main St");
        address1.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setCreatedBy(1L);
        address1.setId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setModifiedBy(1L);
        address1.setRackRenters(new RackRenters());
        address1.setRenters(new Renters());

        RackRenters rackRenters = new RackRenters();
        rackRenters.setAddress(address1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setCreatedBy(1L);
        rackRenters.setEmail("jane.doe@example.org");
        rackRenters.setGender(true);
        rackRenters.setId(123L);
        rackRenters.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setModifiedBy(1L);
        rackRenters.setNote("Note");
        rackRenters.setPhoneNumber("4105551212");
        rackRenters.setRackRenterFullName("Dr Jane Doe");

        Address address2 = new Address();
        address2.setAccount(new Account());
        address2.setAddressCity("42 Main St");
        address2.setAddressDistrict("42 Main St");
        address2.setAddressMoreDetails("42 Main St");
        address2.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setCreatedBy(1L);
        address2.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setModifiedBy(1L);
        address2.setRackRenters(new RackRenters());
        address2.setRenters(new Renters());

        Identity identity = new Identity();
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setCreatedBy(1L);
        identity.setId(123L);
        identity.setIdentityBackImg("Identity Back Img");
        identity.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setModifiedBy(1L);
        identity.setRenters(new Renters());

        Renters renters = new Renters();
        renters.setAddress(address2);
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setCreatedBy(1L);
        renters.setEmail("jane.doe@example.org");
        renters.setGender(true);
        renters.setId(123L);
        renters.setIdentityNumber("42");
        renters.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setModifiedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setModifiedBy(1L);
        renters.setPhoneNumber("4105551212");
        renters.setRenterFullName("Dr Jane Doe");
        renters.setRenterIdentity(identity);
        renters.setRepresent(true);
        renters.setRoomId(123L);

        Address address3 = new Address();
        address3.setAccount(account);
        address3.setAddressCity("42 Main St");
        address3.setAddressDistrict("42 Main St");
        address3.setAddressMoreDetails("42 Main St");
        address3.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setCreatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setCreatedBy(1L);
        address3.setId(123L);
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setModifiedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setModifiedBy(1L);
        address3.setRackRenters(rackRenters);
        address3.setRenters(renters);

        Address address4 = new Address();
        address4.setAccount(new Account());
        address4.setAddressCity("42 Main St");
        address4.setAddressDistrict("42 Main St");
        address4.setAddressMoreDetails("42 Main St");
        address4.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setCreatedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setCreatedBy(1L);
        address4.setId(123L);
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setModifiedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setModifiedBy(1L);
        address4.setRackRenters(new RackRenters());
        address4.setRenters(new Renters());

        Identity identity1 = new Identity();
        LocalDateTime atStartOfDayResult18 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setCreatedAt(Date.from(atStartOfDayResult18.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setCreatedBy(1L);
        identity1.setId(123L);
        identity1.setIdentityBackImg("Identity Back Img");
        identity1.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult19 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setModifiedAt(Date.from(atStartOfDayResult19.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setModifiedBy(1L);
        identity1.setRenters(new Renters());

        Renters renters1 = new Renters();
        renters1.setAddress(address4);
        LocalDateTime atStartOfDayResult20 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setCreatedAt(Date.from(atStartOfDayResult20.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setCreatedBy(1L);
        renters1.setEmail("jane.doe@example.org");
        renters1.setGender(true);
        renters1.setId(123L);
        renters1.setIdentityNumber("42");
        renters1.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult21 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setModifiedAt(Date.from(atStartOfDayResult21.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setModifiedBy(1L);
        renters1.setPhoneNumber("4105551212");
        renters1.setRenterFullName("Dr Jane Doe");
        renters1.setRenterIdentity(identity1);
        renters1.setRepresent(true);
        renters1.setRoomId(123L);

        Identity identity2 = new Identity();
        LocalDateTime atStartOfDayResult22 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setCreatedAt(Date.from(atStartOfDayResult22.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setCreatedBy(1L);
        identity2.setId(123L);
        identity2.setIdentityBackImg("Identity Back Img");
        identity2.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult23 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setModifiedAt(Date.from(atStartOfDayResult23.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setModifiedBy(1L);
        identity2.setRenters(renters1);

        Renters renters2 = new Renters();
        renters2.setAddress(address3);
        LocalDateTime atStartOfDayResult24 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setCreatedAt(Date.from(atStartOfDayResult24.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setCreatedBy(1L);
        renters2.setEmail("jane.doe@example.org");
        renters2.setGender(true);
        renters2.setId(123L);
        renters2.setIdentityNumber("42");
        renters2.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult25 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setModifiedAt(Date.from(atStartOfDayResult25.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setModifiedBy(1L);
        renters2.setPhoneNumber("4105551212");
        renters2.setRenterFullName("Dr Jane Doe");
        renters2.setRenterIdentity(identity2);
        renters2.setRepresent(true);
        renters2.setRoomId(123L);
        Optional<Renters> ofResult = Optional.of(renters2);
        when(renterRepository.findById((Long) any())).thenReturn(ofResult);
        RentersResponse actualRenterResult = renterServiceImpl.renter(123L);
        assertSame(address3, actualRenterResult.getAddress());
        assertEquals(123L, actualRenterResult.getRoomId().longValue());
        assertEquals("jane.doe@example.org", actualRenterResult.getEmail());
        assertEquals("42", actualRenterResult.getIdentityNumber());
        assertEquals(123L, actualRenterResult.getRenterId().longValue());
        assertSame(identity2, actualRenterResult.getRenterIdentity());
        assertEquals("License Plates", actualRenterResult.getLicensePlates());
        assertTrue(actualRenterResult.getRepresent());
        assertTrue(actualRenterResult.getGender());
        assertEquals("4105551212", actualRenterResult.getPhoneNumber());
        assertEquals("Dr Jane Doe", actualRenterResult.getRenterFullName());
        verify(renterRepository).findById((Long) any());
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

    /**
     * Method under test: {@link RenterServiceImpl#addRenter(Renters)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddRenter() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of RenterServiceImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        Renters renters = null;

        // Act
        Renters actualAddRenterResult = this.renterServiceImpl.addRenter(renters);

        // Assert
        // TODO: Add assertions on result
    }

    /**
     * Method under test: {@link RenterServiceImpl#addRenter(RenterRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddRenter2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   vn.com.fpt.common.BusinessException: CMND/CCCD : null
        //       at vn.com.fpt.service.renter.RenterServiceImpl.addRenter(RenterServiceImpl.java:94)
        //   See https://diff.blue/R013 to resolve this issue.

        RenterRepository renterRepo = mock(RenterRepository.class);
        RoomsRepository roomsRepository = mock(RoomsRepository.class);
        SessionDelegatorBaseImpl delegate = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl entityManager = new SessionDelegatorBaseImpl(delegate,
                new SessionDelegatorBaseImpl(null, null));

        AssetTypesRepository assetTypesRepository = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository = mock(BasicAssetRepository.class);
        ContractRepository contractRepository = mock(ContractRepository.class);
        AssetServiceImpl assetService = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        RoomServiceImpl roomService = new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null);

        RenterServiceImpl renterService = new RenterServiceImpl(mock(RenterRepository.class), null,
                mock(RackRenterRepository.class));

        ServicesServiceImpl servicesService = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        AddressRepository addressRepository = mock(AddressRepository.class);
        GroupServiceImpl groupService = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        RenterRepository renterRepository = mock(RenterRepository.class);
        RackRenterRepository rackRenters = mock(RackRenterRepository.class);
        MoneySourceRepository moneySourceRepository = mock(MoneySourceRepository.class);
        TableChangeLogRepository billChangeLogRepo = mock(TableChangeLogRepository.class);
        AssetServiceImpl assetService1 = new AssetServiceImpl(entityManager, assetTypesRepository, basicAssetRepository,
                new ContractServiceImpl(contractRepository, assetService, roomService, renterService, servicesService,
                        addressRepository, groupService, renterRepository, rackRenters, moneySourceRepository,
                        new TableLogComponent(billChangeLogRepo, new ObjectMapper()), mock(RoomBillRepository.class),
                        mock(RoomsRepository.class)),
                mock(RoomAssetRepository.class));

        ContractRepository contractRepository1 = mock(ContractRepository.class);
        SessionDelegatorBaseImpl entityManager1 = new SessionDelegatorBaseImpl(null, null);

        AssetTypesRepository assetTypesRepository1 = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository1 = mock(BasicAssetRepository.class);
        AssetServiceImpl assetService2 = new AssetServiceImpl(entityManager1, assetTypesRepository1,
                basicAssetRepository1,
                new ContractServiceImpl(mock(ContractRepository.class), null, null, null, null, mock(AddressRepository.class),
                        null, mock(RenterRepository.class), mock(RackRenterRepository.class), mock(MoneySourceRepository.class),
                        null, mock(RoomBillRepository.class), mock(RoomsRepository.class)),
                mock(RoomAssetRepository.class));

        RoomsRepository roomsRepository1 = mock(RoomsRepository.class);
        AssetServiceImpl assetService3 = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        ContractServiceImpl contractService = new ContractServiceImpl(mock(ContractRepository.class), null, null, null,
                null, mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class));

        GroupServiceImpl service = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        ServicesServiceImpl servicesService1 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        ContractRepository contractRepo = mock(ContractRepository.class);
        RoomServiceImpl roomService1 = new RoomServiceImpl(roomsRepository1, assetService3, contractService, service,
                servicesService1, mock(RackRenterRepository.class), contractRepo,
                new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class)));

        RenterRepository renterRepo1 = mock(RenterRepository.class);
        RenterServiceImpl renterService1 = new RenterServiceImpl(
                renterRepo1, new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null),
                mock(RackRenterRepository.class));

        ServicesServiceImpl servicesService2 = new ServicesServiceImpl(new SessionDelegatorBaseImpl(null, null),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        AddressRepository addressRepository1 = mock(AddressRepository.class);
        RoomsRepository roomsRepository2 = mock(RoomsRepository.class);
        RoomServiceImpl roomService2 = new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null);

        ServicesServiceImpl servicesService3 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        GroupRepository groupRepository = mock(GroupRepository.class);
        ContractRepository contractRepository2 = mock(ContractRepository.class);
        GroupServiceImpl groupService1 = new GroupServiceImpl(roomsRepository2, roomService2, servicesService3,
                groupRepository, contractRepository2, new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class)),
                mock(RackRenterRepository.class), mock(AddressRepository.class));

        RenterRepository renterRepository1 = mock(RenterRepository.class);
        RackRenterRepository rackRenters1 = mock(RackRenterRepository.class);
        MoneySourceRepository moneySourceRepository1 = mock(MoneySourceRepository.class);
        TableChangeLogRepository billChangeLogRepo1 = mock(TableChangeLogRepository.class);
        ContractServiceImpl contractService1 = new ContractServiceImpl(contractRepository1, assetService2, roomService1,
                renterService1, servicesService2, addressRepository1, groupService1, renterRepository1, rackRenters1,
                moneySourceRepository1, new TableLogComponent(billChangeLogRepo1, new ObjectMapper()),
                mock(RoomBillRepository.class), mock(RoomsRepository.class));

        RoomsRepository roomsRepository3 = mock(RoomsRepository.class);
        RoomsRepository roomsRepository4 = mock(RoomsRepository.class);
        AssetServiceImpl assetService4 = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        ContractServiceImpl contractService2 = new ContractServiceImpl(mock(ContractRepository.class), null, null, null,
                null, mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class));

        GroupServiceImpl service1 = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        ServicesServiceImpl servicesService4 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        ContractRepository contractRepo1 = mock(ContractRepository.class);
        RoomServiceImpl roomService3 = new RoomServiceImpl(roomsRepository4, assetService4, contractService2, service1,
                servicesService4, mock(RackRenterRepository.class), contractRepo1,
                new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class)));

        ServicesServiceImpl servicesService5 = new ServicesServiceImpl(new SessionDelegatorBaseImpl(null, null),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        GroupRepository groupRepository1 = mock(GroupRepository.class);
        ContractRepository contractRepository3 = mock(ContractRepository.class);
        SessionDelegatorBaseImpl entityManager2 = new SessionDelegatorBaseImpl(null, null);

        AssetTypesRepository assetTypesRepository2 = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository2 = mock(BasicAssetRepository.class);
        GroupServiceImpl service2 = new GroupServiceImpl(roomsRepository3, roomService3, servicesService5,
                groupRepository1, contractRepository3,
                new AssetServiceImpl(entityManager2, assetTypesRepository2, basicAssetRepository2,
                        new ContractServiceImpl(mock(ContractRepository.class), null, null, null, null,
                                mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class)),
                        mock(RoomAssetRepository.class)),
                mock(RackRenterRepository.class), mock(AddressRepository.class));

        SessionDelegatorBaseImpl delegate1 = new SessionDelegatorBaseImpl(null, null);

        ServicesServiceImpl servicesService6 = new ServicesServiceImpl(
                new SessionDelegatorBaseImpl(delegate1, new SessionDelegatorBaseImpl(null, null)),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        RackRenterRepository rackRenters2 = mock(RackRenterRepository.class);
        ContractRepository contractRepo2 = mock(ContractRepository.class);
        RenterRepository renterRepo2 = mock(RenterRepository.class);
        RoomsRepository roomsRepository5 = mock(RoomsRepository.class);
        AssetServiceImpl assetService5 = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        ContractServiceImpl contractService3 = new ContractServiceImpl(mock(ContractRepository.class), null, null, null,
                null, mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class));

        GroupServiceImpl service3 = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        ServicesServiceImpl servicesService7 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        ContractRepository contractRepo3 = mock(ContractRepository.class);
        RenterServiceImpl renterServiceImpl = new RenterServiceImpl(renterRepo,
                new RoomServiceImpl(roomsRepository, assetService1, contractService1, service2, servicesService6,
                        rackRenters2, contractRepo2,
                        new RenterServiceImpl(renterRepo2,
                                new RoomServiceImpl(roomsRepository5, assetService5, contractService3, service3, servicesService7,
                                        mock(RackRenterRepository.class), contractRepo3,
                                        new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class))),
                                mock(RackRenterRepository.class))),
                mock(RackRenterRepository.class));
        renterServiceImpl.addRenter(new RenterRequest(), 1L);
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

    /**
     * Method under test: {@link RenterServiceImpl#addRackRenter(String, Boolean, String, String, String, Address, String, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddRackRenter() {
        // TODO: Complete this test.
        //   Reason: R008 Failed to instantiate class under test.
        //   Diffblue Cover was unable to construct an instance of RenterServiceImpl.
        //   Ensure there is a package-visible constructor or factory method that does not
        //   throw for the class under test.
        //   If such a method is already present but Diffblue Cover does not find it, it can
        //   be specified using custom rules for inputs:
        //   https://docs.diffblue.com/knowledge-base/cli/custom-inputs/
        //   This can happen because the factory method takes arguments, throws, returns null
        //   or returns a subtype.
        //   See https://diff.blue/R008 for further troubleshooting of this issue.

        // Arrange
        // TODO: Populate arranged inputs
        String name = "";
        Boolean gender = null;
        String phone = "";
        String email = "";
        String identity = "";
        Address address = null;
        String note = "";
        Long operator = null;

        // Act
        RackRenters actualAddRackRenterResult = this.renterServiceImpl.addRackRenter(name, gender, phone, email, identity,
                address, note, operator);

        // Assert
        // TODO: Add assertions on result
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

    /**
     * Method under test: {@link RenterServiceImpl#updateRenter(Long, Renters, Long)}
     */
    @Test
    void testUpdateRenter() {
        Address address = new Address();
        address.setAccount(new Account());
        address.setAddressCity("42 Main St");
        address.setAddressDistrict("42 Main St");
        address.setAddressMoreDetails("42 Main St");
        address.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        address.setCreatedBy(1L);
        address.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        address.setModifiedBy(1L);
        address.setRackRenters(new RackRenters());
        address.setRenters(new Renters());

        Account account = new Account();
        account.setAddress(address);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        account.setCreatedBy(1L);
        account.setDeactivate(true);
        account.setFullName("Dr Jane Doe");
        account.setGender(true);
        account.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        account.setModifiedBy(1L);
        account.setOwner(true);
        account.setPassword("iloveyou");
        account.setPhoneNumber("4105551212");
        account.setRoles(new HashSet<>());
        account.setUserName("janedoe");

        Address address1 = new Address();
        address1.setAccount(new Account());
        address1.setAddressCity("42 Main St");
        address1.setAddressDistrict("42 Main St");
        address1.setAddressMoreDetails("42 Main St");
        address1.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setCreatedBy(1L);
        address1.setId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setModifiedBy(1L);
        address1.setRackRenters(new RackRenters());
        address1.setRenters(new Renters());

        RackRenters rackRenters = new RackRenters();
        rackRenters.setAddress(address1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setCreatedBy(1L);
        rackRenters.setEmail("jane.doe@example.org");
        rackRenters.setGender(true);
        rackRenters.setId(123L);
        rackRenters.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setModifiedBy(1L);
        rackRenters.setNote("Note");
        rackRenters.setPhoneNumber("4105551212");
        rackRenters.setRackRenterFullName("Dr Jane Doe");

        Address address2 = new Address();
        address2.setAccount(new Account());
        address2.setAddressCity("42 Main St");
        address2.setAddressDistrict("42 Main St");
        address2.setAddressMoreDetails("42 Main St");
        address2.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setCreatedBy(1L);
        address2.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setModifiedBy(1L);
        address2.setRackRenters(new RackRenters());
        address2.setRenters(new Renters());

        Identity identity = new Identity();
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setCreatedBy(1L);
        identity.setId(123L);
        identity.setIdentityBackImg("Identity Back Img");
        identity.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setModifiedBy(1L);
        identity.setRenters(new Renters());

        Renters renters = new Renters();
        renters.setAddress(address2);
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setCreatedBy(1L);
        renters.setEmail("jane.doe@example.org");
        renters.setGender(true);
        renters.setId(123L);
        renters.setIdentityNumber("42");
        renters.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setModifiedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setModifiedBy(1L);
        renters.setPhoneNumber("4105551212");
        renters.setRenterFullName("Dr Jane Doe");
        renters.setRenterIdentity(identity);
        renters.setRepresent(true);
        renters.setRoomId(123L);

        Address address3 = new Address();
        address3.setAccount(account);
        address3.setAddressCity("42 Main St");
        address3.setAddressDistrict("42 Main St");
        address3.setAddressMoreDetails("42 Main St");
        address3.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setCreatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setCreatedBy(1L);
        address3.setId(123L);
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setModifiedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setModifiedBy(1L);
        address3.setRackRenters(rackRenters);
        address3.setRenters(renters);

        Address address4 = new Address();
        address4.setAccount(new Account());
        address4.setAddressCity("42 Main St");
        address4.setAddressDistrict("42 Main St");
        address4.setAddressMoreDetails("42 Main St");
        address4.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setCreatedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setCreatedBy(1L);
        address4.setId(123L);
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setModifiedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setModifiedBy(1L);
        address4.setRackRenters(new RackRenters());
        address4.setRenters(new Renters());

        Identity identity1 = new Identity();
        LocalDateTime atStartOfDayResult18 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setCreatedAt(Date.from(atStartOfDayResult18.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setCreatedBy(1L);
        identity1.setId(123L);
        identity1.setIdentityBackImg("Identity Back Img");
        identity1.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult19 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setModifiedAt(Date.from(atStartOfDayResult19.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setModifiedBy(1L);
        identity1.setRenters(new Renters());

        Renters renters1 = new Renters();
        renters1.setAddress(address4);
        LocalDateTime atStartOfDayResult20 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setCreatedAt(Date.from(atStartOfDayResult20.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setCreatedBy(1L);
        renters1.setEmail("jane.doe@example.org");
        renters1.setGender(true);
        renters1.setId(123L);
        renters1.setIdentityNumber("42");
        renters1.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult21 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setModifiedAt(Date.from(atStartOfDayResult21.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setModifiedBy(1L);
        renters1.setPhoneNumber("4105551212");
        renters1.setRenterFullName("Dr Jane Doe");
        renters1.setRenterIdentity(identity1);
        renters1.setRepresent(true);
        renters1.setRoomId(123L);

        Identity identity2 = new Identity();
        LocalDateTime atStartOfDayResult22 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setCreatedAt(Date.from(atStartOfDayResult22.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setCreatedBy(1L);
        identity2.setId(123L);
        identity2.setIdentityBackImg("Identity Back Img");
        identity2.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult23 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setModifiedAt(Date.from(atStartOfDayResult23.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setModifiedBy(1L);
        identity2.setRenters(renters1);

        Renters renters2 = new Renters();
        renters2.setAddress(address3);
        LocalDateTime atStartOfDayResult24 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setCreatedAt(Date.from(atStartOfDayResult24.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setCreatedBy(1L);
        renters2.setEmail("jane.doe@example.org");
        renters2.setGender(true);
        renters2.setId(123L);
        renters2.setIdentityNumber("42");
        renters2.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult25 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setModifiedAt(Date.from(atStartOfDayResult25.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setModifiedBy(1L);
        renters2.setPhoneNumber("4105551212");
        renters2.setRenterFullName("Dr Jane Doe");
        renters2.setRenterIdentity(identity2);
        renters2.setRepresent(true);
        renters2.setRoomId(123L);
        assertNull(renterServiceImpl.updateRenter(123L, renters2, 1L));
    }

    /**
     * Method under test: {@link RenterServiceImpl#updateRenter(Long, RenterRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateRenter2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.entity.Renters.getId()" because "renters" is null
        //       at vn.com.fpt.responses.RentersResponse.of(RentersResponse.java:42)
        //       at vn.com.fpt.service.renter.RenterServiceImpl.updateRenter(RenterServiceImpl.java:147)
        //   See https://diff.blue/R013 to resolve this issue.

        RenterRepository renterRepo = mock(RenterRepository.class);
        RoomsRepository roomsRepository = mock(RoomsRepository.class);
        SessionDelegatorBaseImpl delegate = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl entityManager = new SessionDelegatorBaseImpl(delegate,
                new SessionDelegatorBaseImpl(null, null));

        AssetTypesRepository assetTypesRepository = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository = mock(BasicAssetRepository.class);
        ContractRepository contractRepository = mock(ContractRepository.class);
        AssetServiceImpl assetService = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        RoomServiceImpl roomService = new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null);

        RenterServiceImpl renterService = new RenterServiceImpl(mock(RenterRepository.class), null,
                mock(RackRenterRepository.class));

        ServicesServiceImpl servicesService = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        AddressRepository addressRepository = mock(AddressRepository.class);
        GroupServiceImpl groupService = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        RenterRepository renterRepository = mock(RenterRepository.class);
        RackRenterRepository rackRenters = mock(RackRenterRepository.class);
        MoneySourceRepository moneySourceRepository = mock(MoneySourceRepository.class);
        TableChangeLogRepository billChangeLogRepo = mock(TableChangeLogRepository.class);
        AssetServiceImpl assetService1 = new AssetServiceImpl(entityManager, assetTypesRepository, basicAssetRepository,
                new ContractServiceImpl(contractRepository, assetService, roomService, renterService, servicesService,
                        addressRepository, groupService, renterRepository, rackRenters, moneySourceRepository,
                        new TableLogComponent(billChangeLogRepo, new ObjectMapper()), mock(RoomBillRepository.class),
                        mock(RoomsRepository.class)),
                mock(RoomAssetRepository.class));

        ContractRepository contractRepository1 = mock(ContractRepository.class);
        SessionDelegatorBaseImpl entityManager1 = new SessionDelegatorBaseImpl(null, null);

        AssetTypesRepository assetTypesRepository1 = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository1 = mock(BasicAssetRepository.class);
        AssetServiceImpl assetService2 = new AssetServiceImpl(entityManager1, assetTypesRepository1,
                basicAssetRepository1,
                new ContractServiceImpl(mock(ContractRepository.class), null, null, null, null, mock(AddressRepository.class),
                        null, mock(RenterRepository.class), mock(RackRenterRepository.class), mock(MoneySourceRepository.class),
                        null, mock(RoomBillRepository.class), mock(RoomsRepository.class)),
                mock(RoomAssetRepository.class));

        RoomsRepository roomsRepository1 = mock(RoomsRepository.class);
        AssetServiceImpl assetService3 = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        ContractServiceImpl contractService = new ContractServiceImpl(mock(ContractRepository.class), null, null, null,
                null, mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class));

        GroupServiceImpl service = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        ServicesServiceImpl servicesService1 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        ContractRepository contractRepo = mock(ContractRepository.class);
        RoomServiceImpl roomService1 = new RoomServiceImpl(roomsRepository1, assetService3, contractService, service,
                servicesService1, mock(RackRenterRepository.class), contractRepo,
                new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class)));

        RenterRepository renterRepo1 = mock(RenterRepository.class);
        RenterServiceImpl renterService1 = new RenterServiceImpl(
                renterRepo1, new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null),
                mock(RackRenterRepository.class));

        ServicesServiceImpl servicesService2 = new ServicesServiceImpl(new SessionDelegatorBaseImpl(null, null),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        AddressRepository addressRepository1 = mock(AddressRepository.class);
        RoomsRepository roomsRepository2 = mock(RoomsRepository.class);
        RoomServiceImpl roomService2 = new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null);

        ServicesServiceImpl servicesService3 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        GroupRepository groupRepository = mock(GroupRepository.class);
        ContractRepository contractRepository2 = mock(ContractRepository.class);
        GroupServiceImpl groupService1 = new GroupServiceImpl(roomsRepository2, roomService2, servicesService3,
                groupRepository, contractRepository2, new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class)),
                mock(RackRenterRepository.class), mock(AddressRepository.class));

        RenterRepository renterRepository1 = mock(RenterRepository.class);
        RackRenterRepository rackRenters1 = mock(RackRenterRepository.class);
        MoneySourceRepository moneySourceRepository1 = mock(MoneySourceRepository.class);
        TableChangeLogRepository billChangeLogRepo1 = mock(TableChangeLogRepository.class);
        ContractServiceImpl contractService1 = new ContractServiceImpl(contractRepository1, assetService2, roomService1,
                renterService1, servicesService2, addressRepository1, groupService1, renterRepository1, rackRenters1,
                moneySourceRepository1, new TableLogComponent(billChangeLogRepo1, new ObjectMapper()),
                mock(RoomBillRepository.class), mock(RoomsRepository.class));

        RoomsRepository roomsRepository3 = mock(RoomsRepository.class);
        RoomsRepository roomsRepository4 = mock(RoomsRepository.class);
        AssetServiceImpl assetService4 = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        ContractServiceImpl contractService2 = new ContractServiceImpl(mock(ContractRepository.class), null, null, null,
                null, mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class));

        GroupServiceImpl service1 = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        ServicesServiceImpl servicesService4 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        ContractRepository contractRepo1 = mock(ContractRepository.class);
        RoomServiceImpl roomService3 = new RoomServiceImpl(roomsRepository4, assetService4, contractService2, service1,
                servicesService4, mock(RackRenterRepository.class), contractRepo1,
                new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class)));

        ServicesServiceImpl servicesService5 = new ServicesServiceImpl(new SessionDelegatorBaseImpl(null, null),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        GroupRepository groupRepository1 = mock(GroupRepository.class);
        ContractRepository contractRepository3 = mock(ContractRepository.class);
        SessionDelegatorBaseImpl entityManager2 = new SessionDelegatorBaseImpl(null, null);

        AssetTypesRepository assetTypesRepository2 = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository2 = mock(BasicAssetRepository.class);
        GroupServiceImpl service2 = new GroupServiceImpl(roomsRepository3, roomService3, servicesService5,
                groupRepository1, contractRepository3,
                new AssetServiceImpl(entityManager2, assetTypesRepository2, basicAssetRepository2,
                        new ContractServiceImpl(mock(ContractRepository.class), null, null, null, null,
                                mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class)),
                        mock(RoomAssetRepository.class)),
                mock(RackRenterRepository.class), mock(AddressRepository.class));

        SessionDelegatorBaseImpl delegate1 = new SessionDelegatorBaseImpl(null, null);

        ServicesServiceImpl servicesService6 = new ServicesServiceImpl(
                new SessionDelegatorBaseImpl(delegate1, new SessionDelegatorBaseImpl(null, null)),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        RackRenterRepository rackRenters2 = mock(RackRenterRepository.class);
        ContractRepository contractRepo2 = mock(ContractRepository.class);
        RenterRepository renterRepo2 = mock(RenterRepository.class);
        RoomsRepository roomsRepository5 = mock(RoomsRepository.class);
        AssetServiceImpl assetService5 = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        ContractServiceImpl contractService3 = new ContractServiceImpl(mock(ContractRepository.class), null, null, null,
                null, mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class));

        GroupServiceImpl service3 = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        ServicesServiceImpl servicesService7 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        ContractRepository contractRepo3 = mock(ContractRepository.class);
        RenterServiceImpl renterServiceImpl = new RenterServiceImpl(renterRepo,
                new RoomServiceImpl(roomsRepository, assetService1, contractService1, service2, servicesService6,
                        rackRenters2, contractRepo2,
                        new RenterServiceImpl(renterRepo2,
                                new RoomServiceImpl(roomsRepository5, assetService5, contractService3, service3, servicesService7,
                                        mock(RackRenterRepository.class), contractRepo3,
                                        new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class))),
                                mock(RackRenterRepository.class))),
                mock(RackRenterRepository.class));
        renterServiceImpl.updateRenter(123L, new RenterRequest(), 1L);
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

    /**
     * Method under test: {@link RenterServiceImpl#deleteRenter(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDeleteRenter() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   vn.com.fpt.common.BusinessException: Xóa khách thuê renter_id: 123 thất bại
        //       at vn.com.fpt.service.renter.RenterServiceImpl.deleteRenter(RenterServiceImpl.java:162)
        //   See https://diff.blue/R013 to resolve this issue.

        RenterRepository renterRepo = mock(RenterRepository.class);
        RoomsRepository roomsRepository = mock(RoomsRepository.class);
        SessionDelegatorBaseImpl delegate = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl entityManager = new SessionDelegatorBaseImpl(delegate,
                new SessionDelegatorBaseImpl(null, null));

        AssetTypesRepository assetTypesRepository = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository = mock(BasicAssetRepository.class);
        ContractRepository contractRepository = mock(ContractRepository.class);
        AssetServiceImpl assetService = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        RoomServiceImpl roomService = new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null);

        RenterServiceImpl renterService = new RenterServiceImpl(mock(RenterRepository.class), null,
                mock(RackRenterRepository.class));

        ServicesServiceImpl servicesService = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        AddressRepository addressRepository = mock(AddressRepository.class);
        GroupServiceImpl groupService = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        RenterRepository renterRepository = mock(RenterRepository.class);
        RackRenterRepository rackRenters = mock(RackRenterRepository.class);
        MoneySourceRepository moneySourceRepository = mock(MoneySourceRepository.class);
        TableChangeLogRepository billChangeLogRepo = mock(TableChangeLogRepository.class);
        AssetServiceImpl assetService1 = new AssetServiceImpl(entityManager, assetTypesRepository, basicAssetRepository,
                new ContractServiceImpl(contractRepository, assetService, roomService, renterService, servicesService,
                        addressRepository, groupService, renterRepository, rackRenters, moneySourceRepository,
                        new TableLogComponent(billChangeLogRepo, new ObjectMapper()), mock(RoomBillRepository.class),
                        mock(RoomsRepository.class)),
                mock(RoomAssetRepository.class));

        ContractRepository contractRepository1 = mock(ContractRepository.class);
        SessionDelegatorBaseImpl entityManager1 = new SessionDelegatorBaseImpl(null, null);

        AssetTypesRepository assetTypesRepository1 = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository1 = mock(BasicAssetRepository.class);
        AssetServiceImpl assetService2 = new AssetServiceImpl(entityManager1, assetTypesRepository1,
                basicAssetRepository1,
                new ContractServiceImpl(mock(ContractRepository.class), null, null, null, null, mock(AddressRepository.class),
                        null, mock(RenterRepository.class), mock(RackRenterRepository.class), mock(MoneySourceRepository.class),
                        null, mock(RoomBillRepository.class), mock(RoomsRepository.class)),
                mock(RoomAssetRepository.class));

        RoomsRepository roomsRepository1 = mock(RoomsRepository.class);
        AssetServiceImpl assetService3 = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        ContractServiceImpl contractService = new ContractServiceImpl(mock(ContractRepository.class), null, null, null,
                null, mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class));

        GroupServiceImpl service = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        ServicesServiceImpl servicesService1 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        ContractRepository contractRepo = mock(ContractRepository.class);
        RoomServiceImpl roomService1 = new RoomServiceImpl(roomsRepository1, assetService3, contractService, service,
                servicesService1, mock(RackRenterRepository.class), contractRepo,
                new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class)));

        RenterRepository renterRepo1 = mock(RenterRepository.class);
        RenterServiceImpl renterService1 = new RenterServiceImpl(
                renterRepo1, new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null),
                mock(RackRenterRepository.class));

        ServicesServiceImpl servicesService2 = new ServicesServiceImpl(new SessionDelegatorBaseImpl(null, null),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        AddressRepository addressRepository1 = mock(AddressRepository.class);
        RoomsRepository roomsRepository2 = mock(RoomsRepository.class);
        RoomServiceImpl roomService2 = new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null);

        ServicesServiceImpl servicesService3 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        GroupRepository groupRepository = mock(GroupRepository.class);
        ContractRepository contractRepository2 = mock(ContractRepository.class);
        GroupServiceImpl groupService1 = new GroupServiceImpl(roomsRepository2, roomService2, servicesService3,
                groupRepository, contractRepository2, new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class)),
                mock(RackRenterRepository.class), mock(AddressRepository.class));

        RenterRepository renterRepository1 = mock(RenterRepository.class);
        RackRenterRepository rackRenters1 = mock(RackRenterRepository.class);
        MoneySourceRepository moneySourceRepository1 = mock(MoneySourceRepository.class);
        TableChangeLogRepository billChangeLogRepo1 = mock(TableChangeLogRepository.class);
        ContractServiceImpl contractService1 = new ContractServiceImpl(contractRepository1, assetService2, roomService1,
                renterService1, servicesService2, addressRepository1, groupService1, renterRepository1, rackRenters1,
                moneySourceRepository1, new TableLogComponent(billChangeLogRepo1, new ObjectMapper()),
                mock(RoomBillRepository.class), mock(RoomsRepository.class));

        RoomsRepository roomsRepository3 = mock(RoomsRepository.class);
        RoomsRepository roomsRepository4 = mock(RoomsRepository.class);
        AssetServiceImpl assetService4 = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        ContractServiceImpl contractService2 = new ContractServiceImpl(mock(ContractRepository.class), null, null, null,
                null, mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class));

        GroupServiceImpl service1 = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        ServicesServiceImpl servicesService4 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        ContractRepository contractRepo1 = mock(ContractRepository.class);
        RoomServiceImpl roomService3 = new RoomServiceImpl(roomsRepository4, assetService4, contractService2, service1,
                servicesService4, mock(RackRenterRepository.class), contractRepo1,
                new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class)));

        ServicesServiceImpl servicesService5 = new ServicesServiceImpl(new SessionDelegatorBaseImpl(null, null),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        GroupRepository groupRepository1 = mock(GroupRepository.class);
        ContractRepository contractRepository3 = mock(ContractRepository.class);
        SessionDelegatorBaseImpl entityManager2 = new SessionDelegatorBaseImpl(null, null);

        AssetTypesRepository assetTypesRepository2 = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository2 = mock(BasicAssetRepository.class);
        GroupServiceImpl service2 = new GroupServiceImpl(roomsRepository3, roomService3, servicesService5,
                groupRepository1, contractRepository3,
                new AssetServiceImpl(entityManager2, assetTypesRepository2, basicAssetRepository2,
                        new ContractServiceImpl(mock(ContractRepository.class), null, null, null, null,
                                mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class)),
                        mock(RoomAssetRepository.class)),
                mock(RackRenterRepository.class), mock(AddressRepository.class));

        SessionDelegatorBaseImpl delegate1 = new SessionDelegatorBaseImpl(null, null);

        ServicesServiceImpl servicesService6 = new ServicesServiceImpl(
                new SessionDelegatorBaseImpl(delegate1, new SessionDelegatorBaseImpl(null, null)),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        RackRenterRepository rackRenters2 = mock(RackRenterRepository.class);
        ContractRepository contractRepo2 = mock(ContractRepository.class);
        RenterRepository renterRepo2 = mock(RenterRepository.class);
        RoomsRepository roomsRepository5 = mock(RoomsRepository.class);
        AssetServiceImpl assetService5 = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        ContractServiceImpl contractService3 = new ContractServiceImpl(mock(ContractRepository.class), null, null, null,
                null, mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class));

        GroupServiceImpl service3 = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        ServicesServiceImpl servicesService7 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        ContractRepository contractRepo3 = mock(ContractRepository.class);
        (new RenterServiceImpl(renterRepo,
                new RoomServiceImpl(roomsRepository, assetService1, contractService1, service2, servicesService6,
                        rackRenters2, contractRepo2,
                        new RenterServiceImpl(renterRepo2,
                                new RoomServiceImpl(roomsRepository5, assetService5, contractService3, service3, servicesService7,
                                        mock(RackRenterRepository.class), contractRepo3,
                                        new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class))),
                                mock(RackRenterRepository.class))),
                mock(RackRenterRepository.class))).deleteRenter(123L);
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

    /**
     * Method under test: {@link RenterServiceImpl#removeFromRoom(Long, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRemoveFromRoom() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.entity.Renters.getId()" because "renters" is null
        //       at vn.com.fpt.responses.RentersResponse.of(RentersResponse.java:42)
        //       at vn.com.fpt.service.renter.RenterServiceImpl.removeFromRoom(RenterServiceImpl.java:172)
        //   See https://diff.blue/R013 to resolve this issue.

        RenterRepository renterRepo = mock(RenterRepository.class);
        RoomsRepository roomsRepository = mock(RoomsRepository.class);
        SessionDelegatorBaseImpl delegate = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl entityManager = new SessionDelegatorBaseImpl(delegate,
                new SessionDelegatorBaseImpl(null, null));

        AssetTypesRepository assetTypesRepository = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository = mock(BasicAssetRepository.class);
        ContractRepository contractRepository = mock(ContractRepository.class);
        AssetServiceImpl assetService = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        RoomServiceImpl roomService = new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null);

        RenterServiceImpl renterService = new RenterServiceImpl(mock(RenterRepository.class), null,
                mock(RackRenterRepository.class));

        ServicesServiceImpl servicesService = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        AddressRepository addressRepository = mock(AddressRepository.class);
        GroupServiceImpl groupService = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        RenterRepository renterRepository = mock(RenterRepository.class);
        RackRenterRepository rackRenters = mock(RackRenterRepository.class);
        MoneySourceRepository moneySourceRepository = mock(MoneySourceRepository.class);
        TableChangeLogRepository billChangeLogRepo = mock(TableChangeLogRepository.class);
        AssetServiceImpl assetService1 = new AssetServiceImpl(entityManager, assetTypesRepository, basicAssetRepository,
                new ContractServiceImpl(contractRepository, assetService, roomService, renterService, servicesService,
                        addressRepository, groupService, renterRepository, rackRenters, moneySourceRepository,
                        new TableLogComponent(billChangeLogRepo, new ObjectMapper()), mock(RoomBillRepository.class),
                        mock(RoomsRepository.class)),
                mock(RoomAssetRepository.class));

        ContractRepository contractRepository1 = mock(ContractRepository.class);
        SessionDelegatorBaseImpl entityManager1 = new SessionDelegatorBaseImpl(null, null);

        AssetTypesRepository assetTypesRepository1 = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository1 = mock(BasicAssetRepository.class);
        AssetServiceImpl assetService2 = new AssetServiceImpl(entityManager1, assetTypesRepository1,
                basicAssetRepository1,
                new ContractServiceImpl(mock(ContractRepository.class), null, null, null, null, mock(AddressRepository.class),
                        null, mock(RenterRepository.class), mock(RackRenterRepository.class), mock(MoneySourceRepository.class),
                        null, mock(RoomBillRepository.class), mock(RoomsRepository.class)),
                mock(RoomAssetRepository.class));

        RoomsRepository roomsRepository1 = mock(RoomsRepository.class);
        AssetServiceImpl assetService3 = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        ContractServiceImpl contractService = new ContractServiceImpl(mock(ContractRepository.class), null, null, null,
                null, mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class));

        GroupServiceImpl service = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        ServicesServiceImpl servicesService1 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        ContractRepository contractRepo = mock(ContractRepository.class);
        RoomServiceImpl roomService1 = new RoomServiceImpl(roomsRepository1, assetService3, contractService, service,
                servicesService1, mock(RackRenterRepository.class), contractRepo,
                new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class)));

        RenterRepository renterRepo1 = mock(RenterRepository.class);
        RenterServiceImpl renterService1 = new RenterServiceImpl(
                renterRepo1, new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null),
                mock(RackRenterRepository.class));

        ServicesServiceImpl servicesService2 = new ServicesServiceImpl(new SessionDelegatorBaseImpl(null, null),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        AddressRepository addressRepository1 = mock(AddressRepository.class);
        RoomsRepository roomsRepository2 = mock(RoomsRepository.class);
        RoomServiceImpl roomService2 = new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null);

        ServicesServiceImpl servicesService3 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        GroupRepository groupRepository = mock(GroupRepository.class);
        ContractRepository contractRepository2 = mock(ContractRepository.class);
        GroupServiceImpl groupService1 = new GroupServiceImpl(roomsRepository2, roomService2, servicesService3,
                groupRepository, contractRepository2, new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class)),
                mock(RackRenterRepository.class), mock(AddressRepository.class));

        RenterRepository renterRepository1 = mock(RenterRepository.class);
        RackRenterRepository rackRenters1 = mock(RackRenterRepository.class);
        MoneySourceRepository moneySourceRepository1 = mock(MoneySourceRepository.class);
        TableChangeLogRepository billChangeLogRepo1 = mock(TableChangeLogRepository.class);
        ContractServiceImpl contractService1 = new ContractServiceImpl(contractRepository1, assetService2, roomService1,
                renterService1, servicesService2, addressRepository1, groupService1, renterRepository1, rackRenters1,
                moneySourceRepository1, new TableLogComponent(billChangeLogRepo1, new ObjectMapper()),
                mock(RoomBillRepository.class), mock(RoomsRepository.class));

        RoomsRepository roomsRepository3 = mock(RoomsRepository.class);
        RoomsRepository roomsRepository4 = mock(RoomsRepository.class);
        AssetServiceImpl assetService4 = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        ContractServiceImpl contractService2 = new ContractServiceImpl(mock(ContractRepository.class), null, null, null,
                null, mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class));

        GroupServiceImpl service1 = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        ServicesServiceImpl servicesService4 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        ContractRepository contractRepo1 = mock(ContractRepository.class);
        RoomServiceImpl roomService3 = new RoomServiceImpl(roomsRepository4, assetService4, contractService2, service1,
                servicesService4, mock(RackRenterRepository.class), contractRepo1,
                new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class)));

        ServicesServiceImpl servicesService5 = new ServicesServiceImpl(new SessionDelegatorBaseImpl(null, null),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        GroupRepository groupRepository1 = mock(GroupRepository.class);
        ContractRepository contractRepository3 = mock(ContractRepository.class);
        SessionDelegatorBaseImpl entityManager2 = new SessionDelegatorBaseImpl(null, null);

        AssetTypesRepository assetTypesRepository2 = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository2 = mock(BasicAssetRepository.class);
        GroupServiceImpl service2 = new GroupServiceImpl(roomsRepository3, roomService3, servicesService5,
                groupRepository1, contractRepository3,
                new AssetServiceImpl(entityManager2, assetTypesRepository2, basicAssetRepository2,
                        new ContractServiceImpl(mock(ContractRepository.class), null, null, null, null,
                                mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class)),
                        mock(RoomAssetRepository.class)),
                mock(RackRenterRepository.class), mock(AddressRepository.class));

        SessionDelegatorBaseImpl delegate1 = new SessionDelegatorBaseImpl(null, null);

        ServicesServiceImpl servicesService6 = new ServicesServiceImpl(
                new SessionDelegatorBaseImpl(delegate1, new SessionDelegatorBaseImpl(null, null)),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        RackRenterRepository rackRenters2 = mock(RackRenterRepository.class);
        ContractRepository contractRepo2 = mock(ContractRepository.class);
        RenterRepository renterRepo2 = mock(RenterRepository.class);
        RoomsRepository roomsRepository5 = mock(RoomsRepository.class);
        AssetServiceImpl assetService5 = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        ContractServiceImpl contractService3 = new ContractServiceImpl(mock(ContractRepository.class), null, null, null,
                null, mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class));

        GroupServiceImpl service3 = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        ServicesServiceImpl servicesService7 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        ContractRepository contractRepo3 = mock(ContractRepository.class);
        (new RenterServiceImpl(renterRepo,
                new RoomServiceImpl(roomsRepository, assetService1, contractService1, service2, servicesService6,
                        rackRenters2, contractRepo2,
                        new RenterServiceImpl(renterRepo2,
                                new RoomServiceImpl(roomsRepository5, assetService5, contractService3, service3, servicesService7,
                                        mock(RackRenterRepository.class), contractRepo3,
                                        new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class))),
                                mock(RackRenterRepository.class))),
                mock(RackRenterRepository.class))).removeFromRoom(123L, 1L);
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

    /**
     * Method under test: {@link RenterServiceImpl#findRenter(Long)}
     */
    @Test
    void testFindRenter() {
        Address address = new Address();
        address.setAccount(new Account());
        address.setAddressCity("42 Main St");
        address.setAddressDistrict("42 Main St");
        address.setAddressMoreDetails("42 Main St");
        address.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        address.setCreatedBy(1L);
        address.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        address.setModifiedBy(1L);
        address.setRackRenters(new RackRenters());
        address.setRenters(new Renters());

        Account account = new Account();
        account.setAddress(address);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        account.setCreatedBy(1L);
        account.setDeactivate(true);
        account.setFullName("Dr Jane Doe");
        account.setGender(true);
        account.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        account.setModifiedBy(1L);
        account.setOwner(true);
        account.setPassword("iloveyou");
        account.setPhoneNumber("4105551212");
        account.setRoles(new HashSet<>());
        account.setUserName("janedoe");

        Address address1 = new Address();
        address1.setAccount(new Account());
        address1.setAddressCity("42 Main St");
        address1.setAddressDistrict("42 Main St");
        address1.setAddressMoreDetails("42 Main St");
        address1.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setCreatedBy(1L);
        address1.setId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setModifiedBy(1L);
        address1.setRackRenters(new RackRenters());
        address1.setRenters(new Renters());

        RackRenters rackRenters = new RackRenters();
        rackRenters.setAddress(address1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setCreatedBy(1L);
        rackRenters.setEmail("jane.doe@example.org");
        rackRenters.setGender(true);
        rackRenters.setId(123L);
        rackRenters.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setModifiedBy(1L);
        rackRenters.setNote("Note");
        rackRenters.setPhoneNumber("4105551212");
        rackRenters.setRackRenterFullName("Dr Jane Doe");

        Address address2 = new Address();
        address2.setAccount(new Account());
        address2.setAddressCity("42 Main St");
        address2.setAddressDistrict("42 Main St");
        address2.setAddressMoreDetails("42 Main St");
        address2.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setCreatedBy(1L);
        address2.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setModifiedBy(1L);
        address2.setRackRenters(new RackRenters());
        address2.setRenters(new Renters());

        Identity identity = new Identity();
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setCreatedBy(1L);
        identity.setId(123L);
        identity.setIdentityBackImg("Identity Back Img");
        identity.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setModifiedBy(1L);
        identity.setRenters(new Renters());

        Renters renters = new Renters();
        renters.setAddress(address2);
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setCreatedBy(1L);
        renters.setEmail("jane.doe@example.org");
        renters.setGender(true);
        renters.setId(123L);
        renters.setIdentityNumber("42");
        renters.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setModifiedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setModifiedBy(1L);
        renters.setPhoneNumber("4105551212");
        renters.setRenterFullName("Dr Jane Doe");
        renters.setRenterIdentity(identity);
        renters.setRepresent(true);
        renters.setRoomId(123L);

        Address address3 = new Address();
        address3.setAccount(account);
        address3.setAddressCity("42 Main St");
        address3.setAddressDistrict("42 Main St");
        address3.setAddressMoreDetails("42 Main St");
        address3.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setCreatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setCreatedBy(1L);
        address3.setId(123L);
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setModifiedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setModifiedBy(1L);
        address3.setRackRenters(rackRenters);
        address3.setRenters(renters);

        Address address4 = new Address();
        address4.setAccount(new Account());
        address4.setAddressCity("42 Main St");
        address4.setAddressDistrict("42 Main St");
        address4.setAddressMoreDetails("42 Main St");
        address4.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setCreatedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setCreatedBy(1L);
        address4.setId(123L);
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setModifiedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setModifiedBy(1L);
        address4.setRackRenters(new RackRenters());
        address4.setRenters(new Renters());

        Identity identity1 = new Identity();
        LocalDateTime atStartOfDayResult18 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setCreatedAt(Date.from(atStartOfDayResult18.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setCreatedBy(1L);
        identity1.setId(123L);
        identity1.setIdentityBackImg("Identity Back Img");
        identity1.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult19 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setModifiedAt(Date.from(atStartOfDayResult19.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setModifiedBy(1L);
        identity1.setRenters(new Renters());

        Renters renters1 = new Renters();
        renters1.setAddress(address4);
        LocalDateTime atStartOfDayResult20 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setCreatedAt(Date.from(atStartOfDayResult20.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setCreatedBy(1L);
        renters1.setEmail("jane.doe@example.org");
        renters1.setGender(true);
        renters1.setId(123L);
        renters1.setIdentityNumber("42");
        renters1.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult21 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setModifiedAt(Date.from(atStartOfDayResult21.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setModifiedBy(1L);
        renters1.setPhoneNumber("4105551212");
        renters1.setRenterFullName("Dr Jane Doe");
        renters1.setRenterIdentity(identity1);
        renters1.setRepresent(true);
        renters1.setRoomId(123L);

        Identity identity2 = new Identity();
        LocalDateTime atStartOfDayResult22 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setCreatedAt(Date.from(atStartOfDayResult22.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setCreatedBy(1L);
        identity2.setId(123L);
        identity2.setIdentityBackImg("Identity Back Img");
        identity2.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult23 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setModifiedAt(Date.from(atStartOfDayResult23.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setModifiedBy(1L);
        identity2.setRenters(renters1);

        Renters renters2 = new Renters();
        renters2.setAddress(address3);
        LocalDateTime atStartOfDayResult24 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setCreatedAt(Date.from(atStartOfDayResult24.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setCreatedBy(1L);
        renters2.setEmail("jane.doe@example.org");
        renters2.setGender(true);
        renters2.setId(123L);
        renters2.setIdentityNumber("42");
        renters2.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult25 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setModifiedAt(Date.from(atStartOfDayResult25.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setModifiedBy(1L);
        renters2.setPhoneNumber("4105551212");
        renters2.setRenterFullName("Dr Jane Doe");
        renters2.setRenterIdentity(identity2);
        renters2.setRepresent(true);
        renters2.setRoomId(123L);
        Optional<Renters> ofResult = Optional.of(renters2);
        when(renterRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(renters2, renterServiceImpl.findRenter(123L));
        verify(renterRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RenterServiceImpl#findRenter(String)}
     */
    @Test
    void testFindRenter2() {
        Account account = new Account();
        account.setAddress(new Address());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        account.setCreatedBy(1L);
        account.setDeactivate(true);
        account.setFullName("Dr Jane Doe");
        account.setGender(true);
        account.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        account.setModifiedBy(1L);
        account.setOwner(true);
        account.setPassword("iloveyou");
        account.setPhoneNumber("4105551212");
        account.setRoles(new HashSet<>());
        account.setUserName("janedoe");

        RackRenters rackRenters = new RackRenters();
        rackRenters.setAddress(new Address());
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setCreatedBy(1L);
        rackRenters.setEmail("jane.doe@example.org");
        rackRenters.setGender(true);
        rackRenters.setId(123L);
        rackRenters.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setModifiedBy(1L);
        rackRenters.setNote("Note");
        rackRenters.setPhoneNumber("4105551212");
        rackRenters.setRackRenterFullName("Dr Jane Doe");

        Renters renters = new Renters();
        renters.setAddress(new Address());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setCreatedBy(1L);
        renters.setEmail("jane.doe@example.org");
        renters.setGender(true);
        renters.setId(123L);
        renters.setIdentityNumber("42");
        renters.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setModifiedBy(1L);
        renters.setPhoneNumber("4105551212");
        renters.setRenterFullName("Dr Jane Doe");
        renters.setRenterIdentity(new Identity());
        renters.setRepresent(true);
        renters.setRoomId(123L);

        Address address = new Address();
        address.setAccount(account);
        address.setAddressCity("42 Main St");
        address.setAddressDistrict("42 Main St");
        address.setAddressMoreDetails("42 Main St");
        address.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        address.setCreatedBy(1L);
        address.setId(123L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        address.setModifiedBy(1L);
        address.setRackRenters(rackRenters);
        address.setRenters(renters);

        Account account1 = new Account();
        account1.setAddress(address);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account1.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        account1.setCreatedBy(1L);
        account1.setDeactivate(true);
        account1.setFullName("Dr Jane Doe");
        account1.setGender(true);
        account1.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account1.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        account1.setModifiedBy(1L);
        account1.setOwner(true);
        account1.setPassword("iloveyou");
        account1.setPhoneNumber("4105551212");
        account1.setRoles(new HashSet<>());
        account1.setUserName("janedoe");

        Account account2 = new Account();
        account2.setAddress(new Address());
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account2.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        account2.setCreatedBy(1L);
        account2.setDeactivate(true);
        account2.setFullName("Dr Jane Doe");
        account2.setGender(true);
        account2.setId(123L);
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account2.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        account2.setModifiedBy(1L);
        account2.setOwner(true);
        account2.setPassword("iloveyou");
        account2.setPhoneNumber("4105551212");
        account2.setRoles(new HashSet<>());
        account2.setUserName("janedoe");

        RackRenters rackRenters1 = new RackRenters();
        rackRenters1.setAddress(new Address());
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setCreatedBy(1L);
        rackRenters1.setEmail("jane.doe@example.org");
        rackRenters1.setGender(true);
        rackRenters1.setId(123L);
        rackRenters1.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setModifiedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setModifiedBy(1L);
        rackRenters1.setNote("Note");
        rackRenters1.setPhoneNumber("4105551212");
        rackRenters1.setRackRenterFullName("Dr Jane Doe");

        Renters renters1 = new Renters();
        renters1.setAddress(new Address());
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setCreatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setCreatedBy(1L);
        renters1.setEmail("jane.doe@example.org");
        renters1.setGender(true);
        renters1.setId(123L);
        renters1.setIdentityNumber("42");
        renters1.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setModifiedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setModifiedBy(1L);
        renters1.setPhoneNumber("4105551212");
        renters1.setRenterFullName("Dr Jane Doe");
        renters1.setRenterIdentity(new Identity());
        renters1.setRepresent(true);
        renters1.setRoomId(123L);

        Address address1 = new Address();
        address1.setAccount(account2);
        address1.setAddressCity("42 Main St");
        address1.setAddressDistrict("42 Main St");
        address1.setAddressMoreDetails("42 Main St");
        address1.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setCreatedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setCreatedBy(1L);
        address1.setId(123L);
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setModifiedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setModifiedBy(1L);
        address1.setRackRenters(rackRenters1);
        address1.setRenters(renters1);

        RackRenters rackRenters2 = new RackRenters();
        rackRenters2.setAddress(address1);
        LocalDateTime atStartOfDayResult18 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters2.setCreatedAt(Date.from(atStartOfDayResult18.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters2.setCreatedBy(1L);
        rackRenters2.setEmail("jane.doe@example.org");
        rackRenters2.setGender(true);
        rackRenters2.setId(123L);
        rackRenters2.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult19 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters2.setModifiedAt(Date.from(atStartOfDayResult19.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters2.setModifiedBy(1L);
        rackRenters2.setNote("Note");
        rackRenters2.setPhoneNumber("4105551212");
        rackRenters2.setRackRenterFullName("Dr Jane Doe");

        Account account3 = new Account();
        account3.setAddress(new Address());
        LocalDateTime atStartOfDayResult20 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account3.setCreatedAt(Date.from(atStartOfDayResult20.atZone(ZoneId.of("UTC")).toInstant()));
        account3.setCreatedBy(1L);
        account3.setDeactivate(true);
        account3.setFullName("Dr Jane Doe");
        account3.setGender(true);
        account3.setId(123L);
        LocalDateTime atStartOfDayResult21 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account3.setModifiedAt(Date.from(atStartOfDayResult21.atZone(ZoneId.of("UTC")).toInstant()));
        account3.setModifiedBy(1L);
        account3.setOwner(true);
        account3.setPassword("iloveyou");
        account3.setPhoneNumber("4105551212");
        account3.setRoles(new HashSet<>());
        account3.setUserName("janedoe");

        RackRenters rackRenters3 = new RackRenters();
        rackRenters3.setAddress(new Address());
        LocalDateTime atStartOfDayResult22 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters3.setCreatedAt(Date.from(atStartOfDayResult22.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters3.setCreatedBy(1L);
        rackRenters3.setEmail("jane.doe@example.org");
        rackRenters3.setGender(true);
        rackRenters3.setId(123L);
        rackRenters3.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult23 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters3.setModifiedAt(Date.from(atStartOfDayResult23.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters3.setModifiedBy(1L);
        rackRenters3.setNote("Note");
        rackRenters3.setPhoneNumber("4105551212");
        rackRenters3.setRackRenterFullName("Dr Jane Doe");

        Renters renters2 = new Renters();
        renters2.setAddress(new Address());
        LocalDateTime atStartOfDayResult24 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setCreatedAt(Date.from(atStartOfDayResult24.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setCreatedBy(1L);
        renters2.setEmail("jane.doe@example.org");
        renters2.setGender(true);
        renters2.setId(123L);
        renters2.setIdentityNumber("42");
        renters2.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult25 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setModifiedAt(Date.from(atStartOfDayResult25.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setModifiedBy(1L);
        renters2.setPhoneNumber("4105551212");
        renters2.setRenterFullName("Dr Jane Doe");
        renters2.setRenterIdentity(new Identity());
        renters2.setRepresent(true);
        renters2.setRoomId(123L);

        Address address2 = new Address();
        address2.setAccount(account3);
        address2.setAddressCity("42 Main St");
        address2.setAddressDistrict("42 Main St");
        address2.setAddressMoreDetails("42 Main St");
        address2.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult26 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setCreatedAt(Date.from(atStartOfDayResult26.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setCreatedBy(1L);
        address2.setId(123L);
        LocalDateTime atStartOfDayResult27 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setModifiedAt(Date.from(atStartOfDayResult27.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setModifiedBy(1L);
        address2.setRackRenters(rackRenters3);
        address2.setRenters(renters2);

        Renters renters3 = new Renters();
        renters3.setAddress(new Address());
        LocalDateTime atStartOfDayResult28 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters3.setCreatedAt(Date.from(atStartOfDayResult28.atZone(ZoneId.of("UTC")).toInstant()));
        renters3.setCreatedBy(1L);
        renters3.setEmail("jane.doe@example.org");
        renters3.setGender(true);
        renters3.setId(123L);
        renters3.setIdentityNumber("42");
        renters3.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult29 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters3.setModifiedAt(Date.from(atStartOfDayResult29.atZone(ZoneId.of("UTC")).toInstant()));
        renters3.setModifiedBy(1L);
        renters3.setPhoneNumber("4105551212");
        renters3.setRenterFullName("Dr Jane Doe");
        renters3.setRenterIdentity(new Identity());
        renters3.setRepresent(true);
        renters3.setRoomId(123L);

        Identity identity = new Identity();
        LocalDateTime atStartOfDayResult30 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setCreatedAt(Date.from(atStartOfDayResult30.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setCreatedBy(1L);
        identity.setId(123L);
        identity.setIdentityBackImg("Identity Back Img");
        identity.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult31 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setModifiedAt(Date.from(atStartOfDayResult31.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setModifiedBy(1L);
        identity.setRenters(renters3);

        Renters renters4 = new Renters();
        renters4.setAddress(address2);
        LocalDateTime atStartOfDayResult32 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters4.setCreatedAt(Date.from(atStartOfDayResult32.atZone(ZoneId.of("UTC")).toInstant()));
        renters4.setCreatedBy(1L);
        renters4.setEmail("jane.doe@example.org");
        renters4.setGender(true);
        renters4.setId(123L);
        renters4.setIdentityNumber("42");
        renters4.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult33 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters4.setModifiedAt(Date.from(atStartOfDayResult33.atZone(ZoneId.of("UTC")).toInstant()));
        renters4.setModifiedBy(1L);
        renters4.setPhoneNumber("4105551212");
        renters4.setRenterFullName("Dr Jane Doe");
        renters4.setRenterIdentity(identity);
        renters4.setRepresent(true);
        renters4.setRoomId(123L);

        Address address3 = new Address();
        address3.setAccount(account1);
        address3.setAddressCity("42 Main St");
        address3.setAddressDistrict("42 Main St");
        address3.setAddressMoreDetails("42 Main St");
        address3.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult34 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setCreatedAt(Date.from(atStartOfDayResult34.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setCreatedBy(1L);
        address3.setId(123L);
        LocalDateTime atStartOfDayResult35 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setModifiedAt(Date.from(atStartOfDayResult35.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setModifiedBy(1L);
        address3.setRackRenters(rackRenters2);
        address3.setRenters(renters4);

        Account account4 = new Account();
        account4.setAddress(new Address());
        LocalDateTime atStartOfDayResult36 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account4.setCreatedAt(Date.from(atStartOfDayResult36.atZone(ZoneId.of("UTC")).toInstant()));
        account4.setCreatedBy(1L);
        account4.setDeactivate(true);
        account4.setFullName("Dr Jane Doe");
        account4.setGender(true);
        account4.setId(123L);
        LocalDateTime atStartOfDayResult37 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account4.setModifiedAt(Date.from(atStartOfDayResult37.atZone(ZoneId.of("UTC")).toInstant()));
        account4.setModifiedBy(1L);
        account4.setOwner(true);
        account4.setPassword("iloveyou");
        account4.setPhoneNumber("4105551212");
        account4.setRoles(new HashSet<>());
        account4.setUserName("janedoe");

        RackRenters rackRenters4 = new RackRenters();
        rackRenters4.setAddress(new Address());
        LocalDateTime atStartOfDayResult38 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters4.setCreatedAt(Date.from(atStartOfDayResult38.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters4.setCreatedBy(1L);
        rackRenters4.setEmail("jane.doe@example.org");
        rackRenters4.setGender(true);
        rackRenters4.setId(123L);
        rackRenters4.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult39 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters4.setModifiedAt(Date.from(atStartOfDayResult39.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters4.setModifiedBy(1L);
        rackRenters4.setNote("Note");
        rackRenters4.setPhoneNumber("4105551212");
        rackRenters4.setRackRenterFullName("Dr Jane Doe");

        Renters renters5 = new Renters();
        renters5.setAddress(new Address());
        LocalDateTime atStartOfDayResult40 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters5.setCreatedAt(Date.from(atStartOfDayResult40.atZone(ZoneId.of("UTC")).toInstant()));
        renters5.setCreatedBy(1L);
        renters5.setEmail("jane.doe@example.org");
        renters5.setGender(true);
        renters5.setId(123L);
        renters5.setIdentityNumber("42");
        renters5.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult41 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters5.setModifiedAt(Date.from(atStartOfDayResult41.atZone(ZoneId.of("UTC")).toInstant()));
        renters5.setModifiedBy(1L);
        renters5.setPhoneNumber("4105551212");
        renters5.setRenterFullName("Dr Jane Doe");
        renters5.setRenterIdentity(new Identity());
        renters5.setRepresent(true);
        renters5.setRoomId(123L);

        Address address4 = new Address();
        address4.setAccount(account4);
        address4.setAddressCity("42 Main St");
        address4.setAddressDistrict("42 Main St");
        address4.setAddressMoreDetails("42 Main St");
        address4.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult42 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setCreatedAt(Date.from(atStartOfDayResult42.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setCreatedBy(1L);
        address4.setId(123L);
        LocalDateTime atStartOfDayResult43 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setModifiedAt(Date.from(atStartOfDayResult43.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setModifiedBy(1L);
        address4.setRackRenters(rackRenters4);
        address4.setRenters(renters5);

        Renters renters6 = new Renters();
        renters6.setAddress(new Address());
        LocalDateTime atStartOfDayResult44 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters6.setCreatedAt(Date.from(atStartOfDayResult44.atZone(ZoneId.of("UTC")).toInstant()));
        renters6.setCreatedBy(1L);
        renters6.setEmail("jane.doe@example.org");
        renters6.setGender(true);
        renters6.setId(123L);
        renters6.setIdentityNumber("42");
        renters6.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult45 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters6.setModifiedAt(Date.from(atStartOfDayResult45.atZone(ZoneId.of("UTC")).toInstant()));
        renters6.setModifiedBy(1L);
        renters6.setPhoneNumber("4105551212");
        renters6.setRenterFullName("Dr Jane Doe");
        renters6.setRenterIdentity(new Identity());
        renters6.setRepresent(true);
        renters6.setRoomId(123L);

        Identity identity1 = new Identity();
        LocalDateTime atStartOfDayResult46 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setCreatedAt(Date.from(atStartOfDayResult46.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setCreatedBy(1L);
        identity1.setId(123L);
        identity1.setIdentityBackImg("Identity Back Img");
        identity1.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult47 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setModifiedAt(Date.from(atStartOfDayResult47.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setModifiedBy(1L);
        identity1.setRenters(renters6);

        Renters renters7 = new Renters();
        renters7.setAddress(address4);
        LocalDateTime atStartOfDayResult48 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters7.setCreatedAt(Date.from(atStartOfDayResult48.atZone(ZoneId.of("UTC")).toInstant()));
        renters7.setCreatedBy(1L);
        renters7.setEmail("jane.doe@example.org");
        renters7.setGender(true);
        renters7.setId(123L);
        renters7.setIdentityNumber("42");
        renters7.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult49 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters7.setModifiedAt(Date.from(atStartOfDayResult49.atZone(ZoneId.of("UTC")).toInstant()));
        renters7.setModifiedBy(1L);
        renters7.setPhoneNumber("4105551212");
        renters7.setRenterFullName("Dr Jane Doe");
        renters7.setRenterIdentity(identity1);
        renters7.setRepresent(true);
        renters7.setRoomId(123L);

        Identity identity2 = new Identity();
        LocalDateTime atStartOfDayResult50 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setCreatedAt(Date.from(atStartOfDayResult50.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setCreatedBy(1L);
        identity2.setId(123L);
        identity2.setIdentityBackImg("Identity Back Img");
        identity2.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult51 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity2.setModifiedAt(Date.from(atStartOfDayResult51.atZone(ZoneId.of("UTC")).toInstant()));
        identity2.setModifiedBy(1L);
        identity2.setRenters(renters7);

        Renters renters8 = new Renters();
        renters8.setAddress(address3);
        LocalDateTime atStartOfDayResult52 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters8.setCreatedAt(Date.from(atStartOfDayResult52.atZone(ZoneId.of("UTC")).toInstant()));
        renters8.setCreatedBy(1L);
        renters8.setEmail("jane.doe@example.org");
        renters8.setGender(true);
        renters8.setId(123L);
        renters8.setIdentityNumber("42");
        renters8.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult53 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters8.setModifiedAt(Date.from(atStartOfDayResult53.atZone(ZoneId.of("UTC")).toInstant()));
        renters8.setModifiedBy(1L);
        renters8.setPhoneNumber("4105551212");
        renters8.setRenterFullName("Dr Jane Doe");
        renters8.setRenterIdentity(identity2);
        renters8.setRepresent(true);
        renters8.setRoomId(123L);
        when(renterRepository.findByIdentityNumber((String) any())).thenReturn(renters8);
        assertSame(renters8, renterServiceImpl.findRenter("Identity"));
        verify(renterRepository).findByIdentityNumber((String) any());
    }

    /**
     * Method under test: {@link RenterServiceImpl#rackRenter(Long)}
     */
    @Test
    void testRackRenter() {
        Address address = new Address();
        address.setAccount(new Account());
        address.setAddressCity("42 Main St");
        address.setAddressDistrict("42 Main St");
        address.setAddressMoreDetails("42 Main St");
        address.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        address.setCreatedBy(1L);
        address.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        address.setModifiedBy(1L);
        address.setRackRenters(new RackRenters());
        address.setRenters(new Renters());

        Account account = new Account();
        account.setAddress(address);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        account.setCreatedBy(1L);
        account.setDeactivate(true);
        account.setFullName("Dr Jane Doe");
        account.setGender(true);
        account.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        account.setModifiedBy(1L);
        account.setOwner(true);
        account.setPassword("iloveyou");
        account.setPhoneNumber("4105551212");
        account.setRoles(new HashSet<>());
        account.setUserName("janedoe");

        Address address1 = new Address();
        address1.setAccount(new Account());
        address1.setAddressCity("42 Main St");
        address1.setAddressDistrict("42 Main St");
        address1.setAddressMoreDetails("42 Main St");
        address1.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setCreatedBy(1L);
        address1.setId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setModifiedBy(1L);
        address1.setRackRenters(new RackRenters());
        address1.setRenters(new Renters());

        RackRenters rackRenters = new RackRenters();
        rackRenters.setAddress(address1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setCreatedBy(1L);
        rackRenters.setEmail("jane.doe@example.org");
        rackRenters.setGender(true);
        rackRenters.setId(123L);
        rackRenters.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setModifiedBy(1L);
        rackRenters.setNote("Note");
        rackRenters.setPhoneNumber("4105551212");
        rackRenters.setRackRenterFullName("Dr Jane Doe");

        Address address2 = new Address();
        address2.setAccount(new Account());
        address2.setAddressCity("42 Main St");
        address2.setAddressDistrict("42 Main St");
        address2.setAddressMoreDetails("42 Main St");
        address2.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setCreatedBy(1L);
        address2.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setModifiedBy(1L);
        address2.setRackRenters(new RackRenters());
        address2.setRenters(new Renters());

        Identity identity = new Identity();
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setCreatedBy(1L);
        identity.setId(123L);
        identity.setIdentityBackImg("Identity Back Img");
        identity.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setModifiedBy(1L);
        identity.setRenters(new Renters());

        Renters renters = new Renters();
        renters.setAddress(address2);
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setCreatedBy(1L);
        renters.setEmail("jane.doe@example.org");
        renters.setGender(true);
        renters.setId(123L);
        renters.setIdentityNumber("42");
        renters.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setModifiedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setModifiedBy(1L);
        renters.setPhoneNumber("4105551212");
        renters.setRenterFullName("Dr Jane Doe");
        renters.setRenterIdentity(identity);
        renters.setRepresent(true);
        renters.setRoomId(123L);

        Address address3 = new Address();
        address3.setAccount(account);
        address3.setAddressCity("42 Main St");
        address3.setAddressDistrict("42 Main St");
        address3.setAddressMoreDetails("42 Main St");
        address3.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setCreatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setCreatedBy(1L);
        address3.setId(123L);
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setModifiedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setModifiedBy(1L);
        address3.setRackRenters(rackRenters);
        address3.setRenters(renters);

        RackRenters rackRenters1 = new RackRenters();
        rackRenters1.setAddress(address3);
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setCreatedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setCreatedBy(1L);
        rackRenters1.setEmail("jane.doe@example.org");
        rackRenters1.setGender(true);
        rackRenters1.setId(123L);
        rackRenters1.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setModifiedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setModifiedBy(1L);
        rackRenters1.setNote("Note");
        rackRenters1.setPhoneNumber("4105551212");
        rackRenters1.setRackRenterFullName("Dr Jane Doe");
        Optional<RackRenters> ofResult = Optional.of(rackRenters1);
        when(rackRenterRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(rackRenters1, renterServiceImpl.rackRenter(123L));
        verify(rackRenterRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RenterServiceImpl#listRackRenter()}
     */
    @Test
    void testListRackRenter() {
        ArrayList<RackRenters> rackRentersList = new ArrayList<>();
        when(rackRenterRepository.findAll()).thenReturn(rackRentersList);
        List<RackRenters> actualListRackRenterResult = renterServiceImpl.listRackRenter();
        assertSame(rackRentersList, actualListRackRenterResult);
        assertTrue(actualListRackRenterResult.isEmpty());
        verify(rackRenterRepository).findAll();
    }

    /**
     * Method under test: {@link RenterServiceImpl#listRackRenter()}
     */
    @Test
    void testListRackRenter2() {
        when(rackRenterRepository.findAll()).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> renterServiceImpl.listRackRenter());
        verify(rackRenterRepository).findAll();
    }

    /**
     * Method under test: {@link RenterServiceImpl#listRackRenter(Specification)}
     */
    @Test
    void testListRackRenter3() {
        ArrayList<RackRenters> rackRentersList = new ArrayList<>();
        when(rackRenterRepository.findAll((Specification<RackRenters>) any())).thenReturn(rackRentersList);
        List<RackRenters> actualListRackRenterResult = renterServiceImpl.listRackRenter(new BaseSpecification<>());
        assertSame(rackRentersList, actualListRackRenterResult);
        assertTrue(actualListRackRenterResult.isEmpty());
        verify(rackRenterRepository).findAll((Specification<RackRenters>) any());
    }

    /**
     * Method under test: {@link RenterServiceImpl#listRackRenter(Specification)}
     */
    @Test
    void testListRackRenter4() {
        when(rackRenterRepository.findAll((Specification<RackRenters>) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> renterServiceImpl.listRackRenter(new BaseSpecification<>()));
        verify(rackRenterRepository).findAll((Specification<RackRenters>) any());
    }

    /**
     * Method under test: {@link RenterServiceImpl#findRackRenter(String)}
     */
    @Test
    void testFindRackRenter() {
        Account account = new Account();
        account.setAddress(new Address());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        account.setCreatedBy(1L);
        account.setDeactivate(true);
        account.setFullName("Dr Jane Doe");
        account.setGender(true);
        account.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        account.setModifiedBy(1L);
        account.setOwner(true);
        account.setPassword("iloveyou");
        account.setPhoneNumber("4105551212");
        account.setRoles(new HashSet<>());
        account.setUserName("janedoe");

        RackRenters rackRenters = new RackRenters();
        rackRenters.setAddress(new Address());
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setCreatedBy(1L);
        rackRenters.setEmail("jane.doe@example.org");
        rackRenters.setGender(true);
        rackRenters.setId(123L);
        rackRenters.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setModifiedBy(1L);
        rackRenters.setNote("Note");
        rackRenters.setPhoneNumber("4105551212");
        rackRenters.setRackRenterFullName("Dr Jane Doe");

        Renters renters = new Renters();
        renters.setAddress(new Address());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setCreatedBy(1L);
        renters.setEmail("jane.doe@example.org");
        renters.setGender(true);
        renters.setId(123L);
        renters.setIdentityNumber("42");
        renters.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setModifiedBy(1L);
        renters.setPhoneNumber("4105551212");
        renters.setRenterFullName("Dr Jane Doe");
        renters.setRenterIdentity(new Identity());
        renters.setRepresent(true);
        renters.setRoomId(123L);

        Address address = new Address();
        address.setAccount(account);
        address.setAddressCity("42 Main St");
        address.setAddressDistrict("42 Main St");
        address.setAddressMoreDetails("42 Main St");
        address.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        address.setCreatedBy(1L);
        address.setId(123L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        address.setModifiedBy(1L);
        address.setRackRenters(rackRenters);
        address.setRenters(renters);

        Account account1 = new Account();
        account1.setAddress(address);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account1.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        account1.setCreatedBy(1L);
        account1.setDeactivate(true);
        account1.setFullName("Dr Jane Doe");
        account1.setGender(true);
        account1.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account1.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        account1.setModifiedBy(1L);
        account1.setOwner(true);
        account1.setPassword("iloveyou");
        account1.setPhoneNumber("4105551212");
        account1.setRoles(new HashSet<>());
        account1.setUserName("janedoe");

        Account account2 = new Account();
        account2.setAddress(new Address());
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account2.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        account2.setCreatedBy(1L);
        account2.setDeactivate(true);
        account2.setFullName("Dr Jane Doe");
        account2.setGender(true);
        account2.setId(123L);
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account2.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        account2.setModifiedBy(1L);
        account2.setOwner(true);
        account2.setPassword("iloveyou");
        account2.setPhoneNumber("4105551212");
        account2.setRoles(new HashSet<>());
        account2.setUserName("janedoe");

        RackRenters rackRenters1 = new RackRenters();
        rackRenters1.setAddress(new Address());
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setCreatedBy(1L);
        rackRenters1.setEmail("jane.doe@example.org");
        rackRenters1.setGender(true);
        rackRenters1.setId(123L);
        rackRenters1.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setModifiedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setModifiedBy(1L);
        rackRenters1.setNote("Note");
        rackRenters1.setPhoneNumber("4105551212");
        rackRenters1.setRackRenterFullName("Dr Jane Doe");

        Renters renters1 = new Renters();
        renters1.setAddress(new Address());
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setCreatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setCreatedBy(1L);
        renters1.setEmail("jane.doe@example.org");
        renters1.setGender(true);
        renters1.setId(123L);
        renters1.setIdentityNumber("42");
        renters1.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setModifiedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setModifiedBy(1L);
        renters1.setPhoneNumber("4105551212");
        renters1.setRenterFullName("Dr Jane Doe");
        renters1.setRenterIdentity(new Identity());
        renters1.setRepresent(true);
        renters1.setRoomId(123L);

        Address address1 = new Address();
        address1.setAccount(account2);
        address1.setAddressCity("42 Main St");
        address1.setAddressDistrict("42 Main St");
        address1.setAddressMoreDetails("42 Main St");
        address1.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setCreatedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setCreatedBy(1L);
        address1.setId(123L);
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setModifiedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setModifiedBy(1L);
        address1.setRackRenters(rackRenters1);
        address1.setRenters(renters1);

        RackRenters rackRenters2 = new RackRenters();
        rackRenters2.setAddress(address1);
        LocalDateTime atStartOfDayResult18 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters2.setCreatedAt(Date.from(atStartOfDayResult18.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters2.setCreatedBy(1L);
        rackRenters2.setEmail("jane.doe@example.org");
        rackRenters2.setGender(true);
        rackRenters2.setId(123L);
        rackRenters2.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult19 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters2.setModifiedAt(Date.from(atStartOfDayResult19.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters2.setModifiedBy(1L);
        rackRenters2.setNote("Note");
        rackRenters2.setPhoneNumber("4105551212");
        rackRenters2.setRackRenterFullName("Dr Jane Doe");

        Account account3 = new Account();
        account3.setAddress(new Address());
        LocalDateTime atStartOfDayResult20 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account3.setCreatedAt(Date.from(atStartOfDayResult20.atZone(ZoneId.of("UTC")).toInstant()));
        account3.setCreatedBy(1L);
        account3.setDeactivate(true);
        account3.setFullName("Dr Jane Doe");
        account3.setGender(true);
        account3.setId(123L);
        LocalDateTime atStartOfDayResult21 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account3.setModifiedAt(Date.from(atStartOfDayResult21.atZone(ZoneId.of("UTC")).toInstant()));
        account3.setModifiedBy(1L);
        account3.setOwner(true);
        account3.setPassword("iloveyou");
        account3.setPhoneNumber("4105551212");
        account3.setRoles(new HashSet<>());
        account3.setUserName("janedoe");

        RackRenters rackRenters3 = new RackRenters();
        rackRenters3.setAddress(new Address());
        LocalDateTime atStartOfDayResult22 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters3.setCreatedAt(Date.from(atStartOfDayResult22.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters3.setCreatedBy(1L);
        rackRenters3.setEmail("jane.doe@example.org");
        rackRenters3.setGender(true);
        rackRenters3.setId(123L);
        rackRenters3.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult23 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters3.setModifiedAt(Date.from(atStartOfDayResult23.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters3.setModifiedBy(1L);
        rackRenters3.setNote("Note");
        rackRenters3.setPhoneNumber("4105551212");
        rackRenters3.setRackRenterFullName("Dr Jane Doe");

        Renters renters2 = new Renters();
        renters2.setAddress(new Address());
        LocalDateTime atStartOfDayResult24 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setCreatedAt(Date.from(atStartOfDayResult24.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setCreatedBy(1L);
        renters2.setEmail("jane.doe@example.org");
        renters2.setGender(true);
        renters2.setId(123L);
        renters2.setIdentityNumber("42");
        renters2.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult25 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setModifiedAt(Date.from(atStartOfDayResult25.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setModifiedBy(1L);
        renters2.setPhoneNumber("4105551212");
        renters2.setRenterFullName("Dr Jane Doe");
        renters2.setRenterIdentity(new Identity());
        renters2.setRepresent(true);
        renters2.setRoomId(123L);

        Address address2 = new Address();
        address2.setAccount(account3);
        address2.setAddressCity("42 Main St");
        address2.setAddressDistrict("42 Main St");
        address2.setAddressMoreDetails("42 Main St");
        address2.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult26 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setCreatedAt(Date.from(atStartOfDayResult26.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setCreatedBy(1L);
        address2.setId(123L);
        LocalDateTime atStartOfDayResult27 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setModifiedAt(Date.from(atStartOfDayResult27.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setModifiedBy(1L);
        address2.setRackRenters(rackRenters3);
        address2.setRenters(renters2);

        Renters renters3 = new Renters();
        renters3.setAddress(new Address());
        LocalDateTime atStartOfDayResult28 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters3.setCreatedAt(Date.from(atStartOfDayResult28.atZone(ZoneId.of("UTC")).toInstant()));
        renters3.setCreatedBy(1L);
        renters3.setEmail("jane.doe@example.org");
        renters3.setGender(true);
        renters3.setId(123L);
        renters3.setIdentityNumber("42");
        renters3.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult29 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters3.setModifiedAt(Date.from(atStartOfDayResult29.atZone(ZoneId.of("UTC")).toInstant()));
        renters3.setModifiedBy(1L);
        renters3.setPhoneNumber("4105551212");
        renters3.setRenterFullName("Dr Jane Doe");
        renters3.setRenterIdentity(new Identity());
        renters3.setRepresent(true);
        renters3.setRoomId(123L);

        Identity identity = new Identity();
        LocalDateTime atStartOfDayResult30 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setCreatedAt(Date.from(atStartOfDayResult30.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setCreatedBy(1L);
        identity.setId(123L);
        identity.setIdentityBackImg("Identity Back Img");
        identity.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult31 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setModifiedAt(Date.from(atStartOfDayResult31.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setModifiedBy(1L);
        identity.setRenters(renters3);

        Renters renters4 = new Renters();
        renters4.setAddress(address2);
        LocalDateTime atStartOfDayResult32 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters4.setCreatedAt(Date.from(atStartOfDayResult32.atZone(ZoneId.of("UTC")).toInstant()));
        renters4.setCreatedBy(1L);
        renters4.setEmail("jane.doe@example.org");
        renters4.setGender(true);
        renters4.setId(123L);
        renters4.setIdentityNumber("42");
        renters4.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult33 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters4.setModifiedAt(Date.from(atStartOfDayResult33.atZone(ZoneId.of("UTC")).toInstant()));
        renters4.setModifiedBy(1L);
        renters4.setPhoneNumber("4105551212");
        renters4.setRenterFullName("Dr Jane Doe");
        renters4.setRenterIdentity(identity);
        renters4.setRepresent(true);
        renters4.setRoomId(123L);

        Address address3 = new Address();
        address3.setAccount(account1);
        address3.setAddressCity("42 Main St");
        address3.setAddressDistrict("42 Main St");
        address3.setAddressMoreDetails("42 Main St");
        address3.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult34 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setCreatedAt(Date.from(atStartOfDayResult34.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setCreatedBy(1L);
        address3.setId(123L);
        LocalDateTime atStartOfDayResult35 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setModifiedAt(Date.from(atStartOfDayResult35.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setModifiedBy(1L);
        address3.setRackRenters(rackRenters2);
        address3.setRenters(renters4);

        RackRenters rackRenters4 = new RackRenters();
        rackRenters4.setAddress(address3);
        LocalDateTime atStartOfDayResult36 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters4.setCreatedAt(Date.from(atStartOfDayResult36.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters4.setCreatedBy(1L);
        rackRenters4.setEmail("jane.doe@example.org");
        rackRenters4.setGender(true);
        rackRenters4.setId(123L);
        rackRenters4.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult37 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters4.setModifiedAt(Date.from(atStartOfDayResult37.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters4.setModifiedBy(1L);
        rackRenters4.setNote("Note");
        rackRenters4.setPhoneNumber("4105551212");
        rackRenters4.setRackRenterFullName("Dr Jane Doe");
        when(rackRenterRepository.findByIdentityNumber((String) any())).thenReturn(rackRenters4);
        assertSame(rackRenters4, renterServiceImpl.findRackRenter("Identity"));
        verify(rackRenterRepository).findByIdentityNumber((String) any());
    }
}