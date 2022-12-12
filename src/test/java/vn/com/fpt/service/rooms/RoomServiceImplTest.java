package vn.com.fpt.service.rooms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.entity.RoomAssets;
import vn.com.fpt.entity.RoomGroups;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.repositories.ContractRepository;
import vn.com.fpt.repositories.RackRenterRepository;
import vn.com.fpt.repositories.RoomsRepository;
import vn.com.fpt.requests.AddRoomsRequest;
import vn.com.fpt.requests.AdjustRoomPriceRequest;
import vn.com.fpt.requests.RoomsPreviewRequest;
import vn.com.fpt.requests.UpdateRoomRequest;
import vn.com.fpt.responses.AdjustRoomPriceResponse;
import vn.com.fpt.responses.RentersResponse;
import vn.com.fpt.responses.RoomsPreviewResponse;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.contract.ContractService;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.renter.RenterService;
import vn.com.fpt.service.services.ServicesService;

@ContextConfiguration(classes = {RoomServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RoomServiceImplTest {
    @MockBean
    private AssetService assetService;

    @MockBean
    private ContractRepository contractRepository;

    @MockBean
    private ContractService contractService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private RackRenterRepository rackRenterRepository;

    @MockBean
    private RenterService renterService;

    @Autowired
    private RoomServiceImpl roomServiceImpl;

    @MockBean
    private RoomsRepository roomsRepository;

    @MockBean
    private ServicesService servicesService;

    /**
     * Method under test: {@link RoomServiceImpl#listRoom(Long, Long, Integer, Integer, String)}
     */
    @Test
    void testListRoom() {
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(new ArrayList<>());
        assertTrue(roomServiceImpl.listRoom(123L, 123L, 1, 1, "Name").isEmpty());
        verify(roomsRepository).findAll((Specification<Rooms>) any(), (Sort) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#listRoom(Long, Long, Integer, Integer, String)}
     */
    @Test
    void testListRoom2() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("isDisable");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(roomsList);
        when(assetService.listRoomAsset((Long) any())).thenReturn(new ArrayList<>());
        assertEquals(1, roomServiceImpl.listRoom(123L, 123L, 1, 1, "Name").size());
        verify(roomsRepository).findAll((Specification<Rooms>) any(), (Sort) any());
        verify(assetService).listRoomAsset((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#listRoom(Long, Long, Integer, Integer, String)}
     */
    @Test
    void testListRoom3() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("isDisable");
        rooms.setRoomPrice(10.0d);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("isDisable");
        rooms1.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms1);
        roomsList.add(rooms);
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(roomsList);
        when(assetService.listRoomAsset((Long) any())).thenReturn(new ArrayList<>());
        assertEquals(2, roomServiceImpl.listRoom(123L, 123L, 1, 1, "Name").size());
        verify(roomsRepository).findAll((Specification<Rooms>) any(), (Sort) any());
        verify(assetService, atLeast(1)).listRoomAsset((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#listRoom(Long, Long, Integer, Integer, String)}
     */
    @Test
    void testListRoom4() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("isDisable");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(roomsList);
        when(assetService.listRoomAsset((Long) any())).thenReturn(new ArrayList<>());
        assertEquals(1, roomServiceImpl.listRoom(123L, 123L, 1, 0, "Name").size());
        verify(roomsRepository).findAll((Specification<Rooms>) any(), (Sort) any());
        verify(assetService).listRoomAsset((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#listRoom(Long, Long, Integer, Integer, String)}
     */
    @Test
    void testListRoom5() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("isDisable");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(roomsList);
        when(assetService.listRoomAsset((Long) any())).thenReturn(new ArrayList<>());
        assertEquals(1, roomServiceImpl.listRoom(123L, 123L, 1, 1, "").size());
        verify(roomsRepository).findAll((Specification<Rooms>) any(), (Sort) any());
        verify(assetService).listRoomAsset((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#listRoom(Long, Long, Integer, Integer, String)}
     */
    @Test
    void testListRoom6() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("isDisable");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(roomsList);
        when(assetService.listRoomAsset((Long) any())).thenThrow(new BusinessException("isDisable"));
        assertThrows(BusinessException.class, () -> roomServiceImpl.listRoom(123L, 123L, 1, 1, "Name"));
        verify(roomsRepository).findAll((Specification<Rooms>) any(), (Sort) any());
        verify(assetService).listRoomAsset((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#listRoom(List)}
     */
    @Test
    void testListRoom7() {
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.findAllById((Iterable<Long>) any())).thenReturn(roomsList);
        List<Rooms> actualListRoomResult = roomServiceImpl.listRoom(new ArrayList<>());
        assertSame(roomsList, actualListRoomResult);
        assertTrue(actualListRoomResult.isEmpty());
        verify(roomsRepository).findAllById((Iterable<Long>) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#listRoom(List)}
     */
    @Test
    void testListRoom8() {
        when(roomsRepository.findAllById((Iterable<Long>) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> roomServiceImpl.listRoom(new ArrayList<>()));
        verify(roomsRepository).findAllById((Iterable<Long>) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#room(Long)}
     */
    @Test
    void testRoom() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(rooms, roomServiceImpl.room(123L));
        verify(roomsRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#room(Long)}
     */
    @Test
    void testRoom2() {
        when(roomsRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> roomServiceImpl.room(123L));
        verify(roomsRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#room(Long)}
     */
    @Test
    void testRoom3() {
        when(roomsRepository.findById((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> roomServiceImpl.room(123L));
        verify(roomsRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#listRoomLeaseContracted(Long)}
     */
    @Test
    void testListRoomLeaseContracted() {
        when(contractService.listGroupContract((Long) any())).thenReturn(new ArrayList<>());

        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("Group Description");
        roomGroups.setGroupName("Group Name");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);
        when(groupService.getGroup((Long) any())).thenReturn(roomGroups);
        assertTrue(roomServiceImpl.listRoomLeaseContracted(123L).isEmpty());
        verify(contractService).listGroupContract((Long) any());
        verify(groupService).getGroup((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#listRoomLeaseContracted(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListRoomLeaseContracted2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   vn.com.fpt.common.BusinessException: Msg
        //       at jdk.proxy4.$Proxy96.getGroup(null)
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.listRoomLeaseContracted(RoomServiceImpl.java:127)
        //   See https://diff.blue/R013 to resolve this issue.

        when(contractService.listGroupContract((Long) any())).thenReturn(new ArrayList<>());
        when(groupService.getGroup((Long) any())).thenThrow(new BusinessException("Msg"));
        roomServiceImpl.listRoomLeaseContracted(123L);
    }

    /**
     * Method under test: {@link RoomServiceImpl#listRoomLeaseContracted(Long)}
     */
    @Test
    void testListRoomLeaseContracted3() {
        when(roomsRepository.findAllByGroupContractIdAndGroupId((Long) any(), (Long) any()))
                .thenReturn(new ArrayList<>());

        Contracts contracts = new Contracts();
        contracts.setAddressId(123L);
        contracts.setContractBillCycle(1);
        contracts.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractEndDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractIsDisable(true);
        contracts.setContractName("Contract Name");
        contracts.setContractPaymentCycle(1);
        contracts.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractStartDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractTerm(1);
        contracts.setContractType(1);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setCreatedBy(1L);
        contracts.setGroupId(123L);
        contracts.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setModifiedBy(1L);
        contracts.setNote("Note");
        contracts.setRackRenters(1L);
        contracts.setRenters(1L);
        contracts.setRoomId(123L);

        ArrayList<Contracts> contractsList = new ArrayList<>();
        contractsList.add(contracts);
        when(contractService.listGroupContract((Long) any())).thenReturn(contractsList);

        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("Group Description");
        roomGroups.setGroupName("Group Name");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);
        when(groupService.getGroup((Long) any())).thenReturn(roomGroups);
        when(rackRenterRepository.findById((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> roomServiceImpl.listRoomLeaseContracted(123L));
        verify(roomsRepository).findAllByGroupContractIdAndGroupId((Long) any(), (Long) any());
        verify(contractService).listGroupContract((Long) any());
        verify(groupService).getGroup((Long) any());
        verify(rackRenterRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#listRoomLeaseNonContracted(Long)}
     */
    @Test
    void testListRoomLeaseNonContracted() {
        when(roomsRepository.findAllFloorByGroupNonContractAndGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByGroupContractIdNullAndGroupIdAndIsDisableIsFalse((Long) any()))
                .thenReturn(new ArrayList<>());

        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("Group Description");
        roomGroups.setGroupName("Group Name");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);
        when(groupService.getGroup((Long) any())).thenReturn(roomGroups);
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        assertEquals(1, roomServiceImpl.listRoomLeaseNonContracted(123L).size());
        verify(roomsRepository).findAllByGroupContractIdNullAndGroupIdAndIsDisableIsFalse((Long) any());
        verify(roomsRepository).findAllFloorByGroupNonContractAndGroupId((Long) any());
        verify(groupService).getGroup((Long) any());
        verify(servicesService).listGeneralServiceByGroupId((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#listRoomLeaseNonContracted(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListRoomLeaseNonContracted2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   vn.com.fpt.common.BusinessException: Msg
        //       at jdk.proxy4.$Proxy97.listGeneralServiceByGroupId(null)
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.listRoomLeaseNonContracted(RoomServiceImpl.java:163)
        //   See https://diff.blue/R013 to resolve this issue.

        when(roomsRepository.findAllFloorByGroupNonContractAndGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByGroupContractIdNullAndGroupIdAndIsDisableIsFalse((Long) any()))
                .thenReturn(new ArrayList<>());

        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("Group Description");
        roomGroups.setGroupName("Group Name");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);
        when(groupService.getGroup((Long) any())).thenReturn(roomGroups);
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenThrow(new BusinessException("Msg"));
        roomServiceImpl.listRoomLeaseNonContracted(123L);
    }

    /**
     * Method under test: {@link RoomServiceImpl#add(List)}
     */
    @Test
    void testAdd() {
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        List<Rooms> actualAddResult = roomServiceImpl.add(new ArrayList<>());
        assertSame(roomsList, actualAddResult);
        assertTrue(actualAddResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#add(List)}
     */
    @Test
    void testAdd2() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList1 = new ArrayList<>();
        roomsList1.add(rooms);
        List<Rooms> actualAddResult = roomServiceImpl.add(roomsList1);
        assertSame(roomsList, actualAddResult);
        assertTrue(actualAddResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByGroupIdAndIsDisableIsFalse((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#add(List)}
     */
    @Test
    void testAdd3() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(roomsList);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList1 = new ArrayList<>();
        roomsList1.add(rooms1);
        assertThrows(BusinessException.class, () -> roomServiceImpl.add(roomsList1));
        verify(roomsRepository).findAllByGroupIdAndIsDisableIsFalse((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#add(List)}
     */
    @Test
    void testAdd4() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList1 = new ArrayList<>();
        roomsList1.add(rooms1);
        roomsList1.add(rooms);
        List<Rooms> actualAddResult = roomServiceImpl.add(roomsList1);
        assertSame(roomsList, actualAddResult);
        assertTrue(actualAddResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository, atLeast(1)).findAllByGroupIdAndIsDisableIsFalse((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#add(List)}
     */
    @Test
    void testAdd5() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        Rooms rooms = mock(Rooms.class);
        when(rooms.getGroupId()).thenReturn(123L);
        when(rooms.getRoomName()).thenReturn("Room Name");
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList1 = new ArrayList<>();
        roomsList1.add(rooms);
        List<Rooms> actualAddResult = roomServiceImpl.add(roomsList1);
        assertSame(roomsList, actualAddResult);
        assertTrue(actualAddResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByGroupIdAndIsDisableIsFalse((Long) any());
        verify(rooms).getGroupId();
        verify(rooms).getRoomName();
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#add(List)}
     */
    @Test
    void testAdd6() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(roomsList);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        Rooms rooms1 = mock(Rooms.class);
        when(rooms1.getGroupId()).thenReturn(123L);
        when(rooms1.getRoomName()).thenReturn("Room Name");
        doNothing().when(rooms1).setCreatedAt((Date) any());
        doNothing().when(rooms1).setCreatedBy((Long) any());
        doNothing().when(rooms1).setId((Long) any());
        doNothing().when(rooms1).setModifiedAt((Date) any());
        doNothing().when(rooms1).setModifiedBy((Long) any());
        doNothing().when(rooms1).setContractId((Long) any());
        doNothing().when(rooms1).setGroupContractId((Long) any());
        doNothing().when(rooms1).setGroupId((Long) any());
        doNothing().when(rooms1).setIsDisable((Boolean) any());
        doNothing().when(rooms1).setRoomArea((Double) any());
        doNothing().when(rooms1).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms1).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms1).setRoomFloor((Integer) any());
        doNothing().when(rooms1).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms1).setRoomName((String) any());
        doNothing().when(rooms1).setRoomPrice((Double) any());
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList1 = new ArrayList<>();
        roomsList1.add(rooms1);
        assertThrows(BusinessException.class, () -> roomServiceImpl.add(roomsList1));
        verify(roomsRepository).findAllByGroupIdAndIsDisableIsFalse((Long) any());
        verify(rooms1).getGroupId();
        verify(rooms1, atLeast(1)).getRoomName();
        verify(rooms1).setCreatedAt((Date) any());
        verify(rooms1).setCreatedBy((Long) any());
        verify(rooms1).setId((Long) any());
        verify(rooms1).setModifiedAt((Date) any());
        verify(rooms1).setModifiedBy((Long) any());
        verify(rooms1).setContractId((Long) any());
        verify(rooms1).setGroupContractId((Long) any());
        verify(rooms1).setGroupId((Long) any());
        verify(rooms1).setIsDisable((Boolean) any());
        verify(rooms1).setRoomArea((Double) any());
        verify(rooms1).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms1).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms1).setRoomFloor((Integer) any());
        verify(rooms1).setRoomLimitPeople((Integer) any());
        verify(rooms1).setRoomName((String) any());
        verify(rooms1).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#add(Rooms)}
     */
    @Test
    void testAdd7() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        when(roomsRepository.save((Rooms) any())).thenReturn(rooms);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        assertSame(rooms, roomServiceImpl.add(rooms1));
        verify(roomsRepository).save((Rooms) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#removeRoom(Long, Long)}
     */
    @Test
    void testRemoveRoom() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        doNothing().when(assetService).deleteRoomAsset((Long) any());
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        assertThrows(BusinessException.class, () -> roomServiceImpl.removeRoom(123L, 1L));
        verify(roomsRepository, atLeast(1)).findById((Long) any());
        verify(assetService).deleteRoomAsset((Long) any());
        verify(renterService).listRenter((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#removeRoom(Long, Long)}
     */
    @Test
    void testRemoveRoom2() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        doNothing().when(assetService).deleteRoomAsset((Long) any());
        when(renterService.listRenter((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> roomServiceImpl.removeRoom(123L, 1L));
        verify(assetService).deleteRoomAsset((Long) any());
        verify(renterService).listRenter((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#removeRoom(Long, Long)}
     */
    @Test
    void testRemoveRoom3() {
        Rooms rooms = mock(Rooms.class);
        when(rooms.getContractId()).thenReturn(123L);
        when(rooms.getRoomName()).thenReturn("Room Name");
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        doNothing().when(assetService).deleteRoomAsset((Long) any());
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        assertThrows(BusinessException.class, () -> roomServiceImpl.removeRoom(123L, 1L));
        verify(roomsRepository, atLeast(1)).findById((Long) any());
        verify(rooms).getContractId();
        verify(rooms).getRoomName();
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
        verify(assetService).deleteRoomAsset((Long) any());
        verify(renterService).listRenter((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#removeRoom(Long, Long)}
     */
    @Test
    void testRemoveRoom4() {
        when(roomsRepository.findById((Long) any())).thenReturn(Optional.empty());
        Rooms rooms = mock(Rooms.class);
        when(rooms.getContractId()).thenReturn(123L);
        when(rooms.getRoomName()).thenReturn("Room Name");
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        doNothing().when(assetService).deleteRoomAsset((Long) any());
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        assertThrows(BusinessException.class, () -> roomServiceImpl.removeRoom(123L, 1L));
        verify(roomsRepository).findById((Long) any());
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
        verify(assetService).deleteRoomAsset((Long) any());
        verify(renterService).listRenter((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#removeRoom(Long, Long)}
     */
    @Test
    void testRemoveRoom5() {
        Rooms rooms = mock(Rooms.class);
        when(rooms.getContractId()).thenReturn(123L);
        when(rooms.getRoomName()).thenReturn("Room Name");
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        doNothing().when(assetService).deleteRoomAsset((Long) any());

        ArrayList<RentersResponse> rentersResponseList = new ArrayList<>();
        rentersResponseList.add(new RentersResponse());
        when(renterService.removeFromRoom((Long) any(), (Long) any())).thenReturn(new RentersResponse());
        when(renterService.listRenter((Long) any())).thenReturn(rentersResponseList);
        assertThrows(BusinessException.class, () -> roomServiceImpl.removeRoom(123L, 1L));
        verify(roomsRepository, atLeast(1)).findById((Long) any());
        verify(rooms).getContractId();
        verify(rooms).getRoomName();
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
        verify(assetService).deleteRoomAsset((Long) any());
        verify(renterService).listRenter((Long) any());
        verify(renterService).removeFromRoom((Long) any(), (Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#removeRoom(List, Long)}
     */
    @Test
    void testRemoveRoom6() {
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByIdInAndContractIdNotNull((List<Long>) any())).thenReturn(new ArrayList<>());
        List<Rooms> actualRemoveRoomResult = roomServiceImpl.removeRoom(new ArrayList<>(), 1L);
        assertSame(roomsList, actualRemoveRoomResult);
        assertTrue(actualRemoveRoomResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByIdInAndContractIdNotNull((List<Long>) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#removeRoom(List, Long)}
     */
    @Test
    void testRemoveRoom7() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByIdInAndContractIdNotNull((List<Long>) any())).thenReturn(roomsList);
        assertThrows(BusinessException.class, () -> roomServiceImpl.removeRoom(new ArrayList<>(), 1L));
        verify(roomsRepository).findAllByIdInAndContractIdNotNull((List<Long>) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#removeRoom(List, Long)}
     */
    @Test
    void testRemoveRoom8() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName(", ");
        rooms1.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms1);
        roomsList.add(rooms);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByIdInAndContractIdNotNull((List<Long>) any())).thenReturn(roomsList);
        assertThrows(BusinessException.class, () -> roomServiceImpl.removeRoom(new ArrayList<>(), 1L));
        verify(roomsRepository).findAllByIdInAndContractIdNotNull((List<Long>) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#removeRoom(List, Long)}
     */
    @Test
    void testRemoveRoom9() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByIdInAndContractIdNotNull((List<Long>) any())).thenReturn(new ArrayList<>());
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);
        List<Rooms> actualRemoveRoomResult = roomServiceImpl.removeRoom(resultLongList, 1L);
        assertSame(roomsList, actualRemoveRoomResult);
        assertTrue(actualRemoveRoomResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByIdInAndContractIdNotNull((List<Long>) any());
        verify(roomsRepository).findById((Long) any());
        verify(renterService).listRenter((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#removeRoom(List, Long)}
     */
    @Test
    void testRemoveRoom10() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByIdInAndContractIdNotNull((List<Long>) any())).thenReturn(new ArrayList<>());
        when(renterService.listRenter((Long) any())).thenThrow(new BusinessException("Msg"));

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);
        assertThrows(BusinessException.class, () -> roomServiceImpl.removeRoom(resultLongList, 1L));
        verify(roomsRepository).findAllByIdInAndContractIdNotNull((List<Long>) any());
        verify(roomsRepository).findById((Long) any());
        verify(renterService).listRenter((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#removeRoom(List, Long)}
     */
    @Test
    void testRemoveRoom11() {
        Rooms rooms = mock(Rooms.class);
        when(rooms.getIsDisable()).thenReturn(true);
        when(rooms.getRoomArea()).thenReturn(10.0d);
        when(rooms.getRoomPrice()).thenReturn(10.0d);
        when(rooms.getRoomFloor()).thenReturn(1);
        when(rooms.getRoomLimitPeople()).thenReturn(1);
        when(rooms.getCreatedBy()).thenReturn(1L);
        when(rooms.getId()).thenReturn(123L);
        when(rooms.getContractId()).thenReturn(123L);
        when(rooms.getGroupContractId()).thenReturn(123L);
        when(rooms.getGroupId()).thenReturn(123L);
        when(rooms.getRoomName()).thenReturn("Room Name");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(rooms.getCreatedAt()).thenReturn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByIdInAndContractIdNotNull((List<Long>) any())).thenReturn(new ArrayList<>());
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);
        List<Rooms> actualRemoveRoomResult = roomServiceImpl.removeRoom(resultLongList, 1L);
        assertSame(roomsList, actualRemoveRoomResult);
        assertTrue(actualRemoveRoomResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByIdInAndContractIdNotNull((List<Long>) any());
        verify(roomsRepository).findById((Long) any());
        verify(rooms).getIsDisable();
        verify(rooms).getRoomArea();
        verify(rooms).getRoomPrice();
        verify(rooms).getRoomFloor();
        verify(rooms).getRoomLimitPeople();
        verify(rooms).getCreatedBy();
        verify(rooms).getId();
        verify(rooms).getContractId();
        verify(rooms).getGroupContractId();
        verify(rooms).getGroupId();
        verify(rooms).getRoomName();
        verify(rooms).getCreatedAt();
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
        verify(renterService).listRenter((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#removeRoom(List, Long)}
     */
    @Test
    void testRemoveRoom12() {
        when(roomsRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByIdInAndContractIdNotNull((List<Long>) any())).thenReturn(new ArrayList<>());
        Rooms rooms = mock(Rooms.class);
        when(rooms.getIsDisable()).thenReturn(true);
        when(rooms.getRoomArea()).thenReturn(10.0d);
        when(rooms.getRoomPrice()).thenReturn(10.0d);
        when(rooms.getRoomFloor()).thenReturn(1);
        when(rooms.getRoomLimitPeople()).thenReturn(1);
        when(rooms.getCreatedBy()).thenReturn(1L);
        when(rooms.getId()).thenReturn(123L);
        when(rooms.getContractId()).thenReturn(123L);
        when(rooms.getGroupContractId()).thenReturn(123L);
        when(rooms.getGroupId()).thenReturn(123L);
        when(rooms.getRoomName()).thenReturn("Room Name");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(rooms.getCreatedAt()).thenReturn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);
        assertThrows(BusinessException.class, () -> roomServiceImpl.removeRoom(resultLongList, 1L));
        verify(roomsRepository).findAllByIdInAndContractIdNotNull((List<Long>) any());
        verify(roomsRepository).findById((Long) any());
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#removeRoom(List, Long)}
     */
    @Test
    void testRemoveRoom13() {
        Rooms rooms = mock(Rooms.class);
        when(rooms.getIsDisable()).thenReturn(true);
        when(rooms.getRoomArea()).thenReturn(10.0d);
        when(rooms.getRoomPrice()).thenReturn(10.0d);
        when(rooms.getRoomFloor()).thenReturn(1);
        when(rooms.getRoomLimitPeople()).thenReturn(1);
        when(rooms.getCreatedBy()).thenReturn(1L);
        when(rooms.getId()).thenReturn(123L);
        when(rooms.getContractId()).thenReturn(123L);
        when(rooms.getGroupContractId()).thenReturn(123L);
        when(rooms.getGroupId()).thenReturn(123L);
        when(rooms.getRoomName()).thenReturn("Room Name");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(rooms.getCreatedAt()).thenReturn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByIdInAndContractIdNotNull((List<Long>) any())).thenReturn(new ArrayList<>());

        ArrayList<RentersResponse> rentersResponseList = new ArrayList<>();
        rentersResponseList.add(new RentersResponse());
        when(renterService.removeFromRoom((Long) any(), (Long) any())).thenReturn(new RentersResponse());
        when(renterService.listRenter((Long) any())).thenReturn(rentersResponseList);

        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);
        List<Rooms> actualRemoveRoomResult = roomServiceImpl.removeRoom(resultLongList, 1L);
        assertSame(roomsList, actualRemoveRoomResult);
        assertTrue(actualRemoveRoomResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByIdInAndContractIdNotNull((List<Long>) any());
        verify(roomsRepository).findById((Long) any());
        verify(rooms).getIsDisable();
        verify(rooms).getRoomArea();
        verify(rooms).getRoomPrice();
        verify(rooms).getRoomFloor();
        verify(rooms).getRoomLimitPeople();
        verify(rooms).getCreatedBy();
        verify(rooms).getId();
        verify(rooms).getContractId();
        verify(rooms).getGroupContractId();
        verify(rooms).getGroupId();
        verify(rooms).getRoomName();
        verify(rooms).getCreatedAt();
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
        verify(renterService).listRenter((Long) any());
        verify(renterService).removeFromRoom((Long) any(), (Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoom(Long, AddRoomsRequest)}
     */
    @Test
    void testUpdateRoom() {
        assertNull(roomServiceImpl.updateRoom(123L, new AddRoomsRequest()));
        assertNull(roomServiceImpl.updateRoom(123L, mock(AddRoomsRequest.class)));
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoom(List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateRoom2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        //       at jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        //       at jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        //       at jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
        //       at java.util.Objects.checkIndex(Objects.java:359)
        //       at java.util.ArrayList.get(ArrayList.java:427)
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.updateRoom(RoomServiceImpl.java:282)
        //   See https://diff.blue/R013 to resolve this issue.

        roomServiceImpl.updateRoom(new ArrayList<>());
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoom(List)}
     */
    @Test
    void testUpdateRoom3() {
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any()))
                .thenReturn(new ArrayList<>());

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList1 = new ArrayList<>();
        roomsList1.add(rooms);
        List<Rooms> actualUpdateRoomResult = roomServiceImpl.updateRoom(roomsList1);
        assertSame(roomsList, actualUpdateRoomResult);
        assertTrue(actualUpdateRoomResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoom(List)}
     */
    @Test
    void testUpdateRoom4() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any()))
                .thenReturn(roomsList);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList1 = new ArrayList<>();
        roomsList1.add(rooms1);
        assertThrows(BusinessException.class, () -> roomServiceImpl.updateRoom(roomsList1));
        verify(roomsRepository).findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoom(List)}
     */
    @Test
    void testUpdateRoom5() {
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any()))
                .thenReturn(new ArrayList<>());

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList1 = new ArrayList<>();
        roomsList1.add(rooms1);
        roomsList1.add(rooms);
        List<Rooms> actualUpdateRoomResult = roomServiceImpl.updateRoom(roomsList1);
        assertSame(roomsList, actualUpdateRoomResult);
        assertTrue(actualUpdateRoomResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoom(List)}
     */
    @Test
    void testUpdateRoom6() {
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any()))
                .thenReturn(new ArrayList<>());
        Rooms rooms = mock(Rooms.class);
        when(rooms.getId()).thenReturn(123L);
        when(rooms.getGroupId()).thenReturn(123L);
        when(rooms.getRoomName()).thenReturn("Room Name");
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList1 = new ArrayList<>();
        roomsList1.add(rooms);
        List<Rooms> actualUpdateRoomResult = roomServiceImpl.updateRoom(roomsList1);
        assertSame(roomsList, actualUpdateRoomResult);
        assertTrue(actualUpdateRoomResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any());
        verify(rooms).getId();
        verify(rooms).getGroupId();
        verify(rooms).getRoomName();
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoom(List)}
     */
    @Test
    void testUpdateRoom7() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any()))
                .thenReturn(roomsList);
        Rooms rooms1 = mock(Rooms.class);
        when(rooms1.getId()).thenReturn(123L);
        when(rooms1.getGroupId()).thenReturn(123L);
        when(rooms1.getRoomName()).thenReturn("Room Name");
        doNothing().when(rooms1).setCreatedAt((Date) any());
        doNothing().when(rooms1).setCreatedBy((Long) any());
        doNothing().when(rooms1).setId((Long) any());
        doNothing().when(rooms1).setModifiedAt((Date) any());
        doNothing().when(rooms1).setModifiedBy((Long) any());
        doNothing().when(rooms1).setContractId((Long) any());
        doNothing().when(rooms1).setGroupContractId((Long) any());
        doNothing().when(rooms1).setGroupId((Long) any());
        doNothing().when(rooms1).setIsDisable((Boolean) any());
        doNothing().when(rooms1).setRoomArea((Double) any());
        doNothing().when(rooms1).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms1).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms1).setRoomFloor((Integer) any());
        doNothing().when(rooms1).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms1).setRoomName((String) any());
        doNothing().when(rooms1).setRoomPrice((Double) any());
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList1 = new ArrayList<>();
        roomsList1.add(rooms1);
        assertThrows(BusinessException.class, () -> roomServiceImpl.updateRoom(roomsList1));
        verify(roomsRepository).findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any());
        verify(rooms1).getId();
        verify(rooms1).getGroupId();
        verify(rooms1, atLeast(1)).getRoomName();
        verify(rooms1).setCreatedAt((Date) any());
        verify(rooms1).setCreatedBy((Long) any());
        verify(rooms1).setId((Long) any());
        verify(rooms1).setModifiedAt((Date) any());
        verify(rooms1).setModifiedBy((Long) any());
        verify(rooms1).setContractId((Long) any());
        verify(rooms1).setGroupContractId((Long) any());
        verify(rooms1).setGroupId((Long) any());
        verify(rooms1).setIsDisable((Boolean) any());
        verify(rooms1).setRoomArea((Double) any());
        verify(rooms1).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms1).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms1).setRoomFloor((Integer) any());
        verify(rooms1).setRoomLimitPeople((Integer) any());
        verify(rooms1).setRoomName((String) any());
        verify(rooms1).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoom(Rooms)}
     */
    @Test
    void testUpdateRoom8() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        when(roomsRepository.save((Rooms) any())).thenReturn(rooms1);
        when(roomsRepository.findByGroupIdAndIdNotAndIsDisableIsFalse((Long) any(), (Long) any()))
                .thenReturn(new ArrayList<>());
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);

        Rooms rooms2 = new Rooms();
        rooms2.setContractId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms2.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms2.setCreatedBy(1L);
        rooms2.setGroupContractId(123L);
        rooms2.setGroupId(123L);
        rooms2.setId(123L);
        rooms2.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms2.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rooms2.setModifiedBy(1L);
        rooms2.setRoomArea(10.0d);
        rooms2.setRoomCurrentElectricIndex(1);
        rooms2.setRoomCurrentWaterIndex(1);
        rooms2.setRoomFloor(1);
        rooms2.setRoomLimitPeople(1);
        rooms2.setRoomName("Room Name");
        rooms2.setRoomPrice(10.0d);
        assertSame(rooms1, roomServiceImpl.updateRoom(rooms2));
        verify(roomsRepository).save((Rooms) any());
        verify(roomsRepository).findByGroupIdAndIdNotAndIsDisableIsFalse((Long) any(), (Long) any());
        verify(roomsRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoom(Rooms)}
     */
    @Test
    void testUpdateRoom9() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.save((Rooms) any())).thenThrow(new BusinessException("Msg"));
        when(roomsRepository.findByGroupIdAndIdNotAndIsDisableIsFalse((Long) any(), (Long) any()))
                .thenThrow(new BusinessException("Msg"));
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        assertThrows(BusinessException.class, () -> roomServiceImpl.updateRoom(rooms1));
        verify(roomsRepository).findByGroupIdAndIdNotAndIsDisableIsFalse((Long) any(), (Long) any());
        verify(roomsRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoom(Rooms)}
     */
    @Test
    void testUpdateRoom10() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);

        Rooms rooms2 = new Rooms();
        rooms2.setContractId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms2.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms2.setCreatedBy(1L);
        rooms2.setGroupContractId(123L);
        rooms2.setGroupId(123L);
        rooms2.setId(123L);
        rooms2.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms2.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rooms2.setModifiedBy(1L);
        rooms2.setRoomArea(10.0d);
        rooms2.setRoomCurrentElectricIndex(1);
        rooms2.setRoomCurrentWaterIndex(1);
        rooms2.setRoomFloor(1);
        rooms2.setRoomLimitPeople(1);
        rooms2.setRoomName("Room Name");
        rooms2.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms2);
        when(roomsRepository.save((Rooms) any())).thenReturn(rooms1);
        when(roomsRepository.findByGroupIdAndIdNotAndIsDisableIsFalse((Long) any(), (Long) any())).thenReturn(roomsList);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);

        Rooms rooms3 = new Rooms();
        rooms3.setContractId(123L);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms3.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        rooms3.setCreatedBy(1L);
        rooms3.setGroupContractId(123L);
        rooms3.setGroupId(123L);
        rooms3.setId(123L);
        rooms3.setIsDisable(true);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms3.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        rooms3.setModifiedBy(1L);
        rooms3.setRoomArea(10.0d);
        rooms3.setRoomCurrentElectricIndex(1);
        rooms3.setRoomCurrentWaterIndex(1);
        rooms3.setRoomFloor(1);
        rooms3.setRoomLimitPeople(1);
        rooms3.setRoomName("Room Name");
        rooms3.setRoomPrice(10.0d);
        assertThrows(BusinessException.class, () -> roomServiceImpl.updateRoom(rooms3));
        verify(roomsRepository).findByGroupIdAndIdNotAndIsDisableIsFalse((Long) any(), (Long) any());
        verify(roomsRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoom(Rooms)}
     */
    @Test
    void testUpdateRoom11() {
        Rooms rooms = mock(Rooms.class);
        when(rooms.getGroupId()).thenReturn(123L);
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        when(roomsRepository.save((Rooms) any())).thenReturn(rooms1);
        when(roomsRepository.findByGroupIdAndIdNotAndIsDisableIsFalse((Long) any(), (Long) any()))
                .thenReturn(new ArrayList<>());
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);

        Rooms rooms2 = new Rooms();
        rooms2.setContractId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms2.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms2.setCreatedBy(1L);
        rooms2.setGroupContractId(123L);
        rooms2.setGroupId(123L);
        rooms2.setId(123L);
        rooms2.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms2.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rooms2.setModifiedBy(1L);
        rooms2.setRoomArea(10.0d);
        rooms2.setRoomCurrentElectricIndex(1);
        rooms2.setRoomCurrentWaterIndex(1);
        rooms2.setRoomFloor(1);
        rooms2.setRoomLimitPeople(1);
        rooms2.setRoomName("Room Name");
        rooms2.setRoomPrice(10.0d);
        assertSame(rooms1, roomServiceImpl.updateRoom(rooms2));
        verify(roomsRepository).save((Rooms) any());
        verify(roomsRepository).findByGroupIdAndIdNotAndIsDisableIsFalse((Long) any(), (Long) any());
        verify(roomsRepository).findById((Long) any());
        verify(rooms).getGroupId();
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#setServiceIndex(Long, Integer, Integer, Long)}
     */
    @Test
    void testSetServiceIndex() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        when(roomsRepository.save((Rooms) any())).thenReturn(rooms1);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(rooms1, roomServiceImpl.setServiceIndex(123L, 1, 1, 1L));
        verify(roomsRepository).save((Rooms) any());
        verify(roomsRepository, atLeast(1)).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#setServiceIndex(Long, Integer, Integer, Long)}
     */
    @Test
    void testSetServiceIndex2() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.save((Rooms) any())).thenThrow(new BusinessException("Msg"));
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(BusinessException.class, () -> roomServiceImpl.setServiceIndex(123L, 1, 1, 1L));
        verify(roomsRepository).save((Rooms) any());
        verify(roomsRepository, atLeast(1)).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#setServiceIndex(Long, Integer, Integer, Long)}
     */
    @Test
    void testSetServiceIndex3() {
        Rooms rooms = mock(Rooms.class);
        when(rooms.getCreatedBy()).thenReturn(1L);
        when(rooms.getId()).thenReturn(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(rooms.getCreatedAt()).thenReturn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        when(roomsRepository.save((Rooms) any())).thenReturn(rooms1);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(rooms1, roomServiceImpl.setServiceIndex(123L, 1, 1, 1L));
        verify(roomsRepository).save((Rooms) any());
        verify(roomsRepository, atLeast(1)).findById((Long) any());
        verify(rooms).getCreatedBy();
        verify(rooms).getId();
        verify(rooms).getCreatedAt();
        verify(rooms, atLeast(1)).setCreatedAt((Date) any());
        verify(rooms, atLeast(1)).setCreatedBy((Long) any());
        verify(rooms, atLeast(1)).setId((Long) any());
        verify(rooms, atLeast(1)).setModifiedAt((Date) any());
        verify(rooms, atLeast(1)).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms, atLeast(1)).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms, atLeast(1)).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#roomChecker(Long)}
     */
    @Test
    void testRoomChecker() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(rooms, roomServiceImpl.roomChecker(123L));
        verify(roomsRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#roomChecker(Long)}
     */
    @Test
    void testRoomChecker2() {
        when(roomsRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> roomServiceImpl.roomChecker(123L));
        verify(roomsRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#roomChecker(Long)}
     */
    @Test
    void testRoomChecker3() {
        when(roomsRepository.findById((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> roomServiceImpl.roomChecker(123L));
        verify(roomsRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#generateRoom(Integer, Integer, Integer, Double, Double, String, Long, Long)}
     */
    @Test
    void testGenerateRoom() {
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        List<Rooms> actualGenerateRoomResult = roomServiceImpl.generateRoom(1, 1, 1, 10.0d, 10.0d, "Name Convention",
                123L, 1L);
        assertSame(roomsList, actualGenerateRoomResult);
        assertTrue(actualGenerateRoomResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByGroupIdAndIsDisableIsFalse((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#generateRoom(Integer, Integer, Integer, Double, Double, String, Long, Long)}
     */
    @Test
    void testGenerateRoom2() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("%02d");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        ArrayList<Rooms> roomsList1 = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList1);
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(roomsList);
        List<Rooms> actualGenerateRoomResult = roomServiceImpl.generateRoom(1, 1, 1, 10.0d, 10.0d, "Name Convention",
                123L, 1L);
        assertSame(roomsList1, actualGenerateRoomResult);
        assertTrue(actualGenerateRoomResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByGroupIdAndIsDisableIsFalse((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#generateRoom(Integer, Integer, Integer, Double, Double, String, Long, Long)}
     */
    @Test
    void testGenerateRoom3() {
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        List<Rooms> actualGenerateRoomResult = roomServiceImpl.generateRoom(2, 1, 1, 10.0d, 10.0d, "Name Convention",
                123L, 1L);
        assertSame(roomsList, actualGenerateRoomResult);
        assertTrue(actualGenerateRoomResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository, atLeast(1)).findAllByGroupIdAndIsDisableIsFalse((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#generateRoom(Integer, Integer, Integer, Double, Double, String, Long, Long)}
     */
    @Test
    void testGenerateRoom4() {
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        List<Rooms> actualGenerateRoomResult = roomServiceImpl.generateRoom(0, 1, 1, 10.0d, 10.0d, "Name Convention",
                123L, 1L);
        assertSame(roomsList, actualGenerateRoomResult);
        assertTrue(actualGenerateRoomResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#generateRoom(Integer, Integer, Integer, Double, Double, String, Long, Long)}
     */
    @Test
    void testGenerateRoom5() {
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenThrow(new BusinessException("%02d"));
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenThrow(new BusinessException("%02d"));
        assertThrows(BusinessException.class,
                () -> roomServiceImpl.generateRoom(1, 1, 1, 10.0d, 10.0d, "Name Convention", 123L, 1L));
        verify(roomsRepository).findAllByGroupIdAndIsDisableIsFalse((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#generateRoom(Integer, Integer, Integer, Double, Double, String, Long, Long)}
     */
    @Test
    void testGenerateRoom6() {
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenThrow(new BusinessException("%02d"));
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenThrow(new BusinessException("%02d"));
        assertThrows(BusinessException.class,
                () -> roomServiceImpl.generateRoom(0, 1, 1, 10.0d, 10.0d, "Name Convention", 123L, 1L));
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#generateRoom(Integer, Integer, Integer, Double, Double, String, Long, Long)}
     */
    @Test
    void testGenerateRoom7() {
        Rooms rooms = mock(Rooms.class);
        when(rooms.getRoomName()).thenReturn("Room Name");
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("%02d");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        ArrayList<Rooms> roomsList1 = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList1);
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(roomsList);
        List<Rooms> actualGenerateRoomResult = roomServiceImpl.generateRoom(1, 1, 1, 10.0d, 10.0d, "Name Convention",
                123L, 1L);
        assertSame(roomsList1, actualGenerateRoomResult);
        assertTrue(actualGenerateRoomResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByGroupIdAndIsDisableIsFalse((Long) any());
        verify(rooms).getRoomName();
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#previewGenerateRoom(Integer, Integer, Integer, Double, Double, String, Long, Long)}
     */
    @Test
    void testPreviewGenerateRoom() {
        assertEquals(1, roomServiceImpl.previewGenerateRoom(1, 1, 1, 10.0d, 10.0d, "Name Convention", 123L, 1L).size());
    }

    /**
     * Method under test: {@link RoomServiceImpl#emptyRoom(Long)}
     */
    @Test
    void testEmptyRoom() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(BusinessException.class, () -> roomServiceImpl.emptyRoom(123L));
        verify(roomsRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#emptyRoom(Long)}
     */
    @Test
    void testEmptyRoom2() {
        Rooms rooms = mock(Rooms.class);
        when(rooms.getContractId()).thenReturn(123L);
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(BusinessException.class, () -> roomServiceImpl.emptyRoom(123L));
        verify(roomsRepository).findById((Long) any());
        verify(rooms).getContractId();
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#emptyRoom(Long)}
     */
    @Test
    void testEmptyRoom3() {
        when(roomsRepository.findById((Long) any())).thenReturn(Optional.empty());
        Rooms rooms = mock(Rooms.class);
        when(rooms.getContractId()).thenReturn(123L);
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        assertThrows(BusinessException.class, () -> roomServiceImpl.emptyRoom(123L));
        verify(roomsRepository).findById((Long) any());
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoomStatus(Long, Long, Long)}
     */
    @Test
    void testUpdateRoomStatus() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        Rooms actualUpdateRoomStatusResult = roomServiceImpl.updateRoomStatus(123L, 123L, 1L);
        assertSame(rooms, actualUpdateRoomStatusResult);
        assertEquals(123L, actualUpdateRoomStatusResult.getContractId().longValue());
        assertEquals(1L, actualUpdateRoomStatusResult.getModifiedBy().longValue());
        verify(roomsRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoomStatus(Long, Long, Long)}
     */
    @Test
    void testUpdateRoomStatus2() {
        Rooms rooms = mock(Rooms.class);
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        roomServiceImpl.updateRoomStatus(123L, 123L, 1L);
        verify(roomsRepository).findById((Long) any());
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms, atLeast(1)).setModifiedAt((Date) any());
        verify(rooms, atLeast(1)).setModifiedBy((Long) any());
        verify(rooms, atLeast(1)).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#updateRoomStatus(Long, Long, Long)}
     */
    @Test
    void testUpdateRoomStatus3() {
        when(roomsRepository.findById((Long) any())).thenReturn(Optional.empty());
        Rooms rooms = mock(Rooms.class);
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        assertThrows(BusinessException.class, () -> roomServiceImpl.updateRoomStatus(123L, 123L, 1L));
        verify(roomsRepository).findById((Long) any());
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#update(List, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdate() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        //       at jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        //       at jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        //       at jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
        //       at java.util.Objects.checkIndex(Objects.java:359)
        //       at java.util.ArrayList.get(ArrayList.java:427)
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.update(RoomServiceImpl.java:333)
        //   See https://diff.blue/R013 to resolve this issue.

        roomServiceImpl.update(new ArrayList<>(), 1L);
    }

    /**
     * Method under test: {@link RoomServiceImpl#update(List, Long)}
     */
    @Test
    void testUpdate2() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any()))
                .thenReturn(new ArrayList<>());
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);

        ArrayList<UpdateRoomRequest> updateRoomRequestList = new ArrayList<>();
        updateRoomRequestList.add(new UpdateRoomRequest());
        List<Rooms> actualUpdateResult = roomServiceImpl.update(updateRoomRequestList, 1L);
        assertSame(roomsList, actualUpdateResult);
        assertTrue(actualUpdateResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any());
        verify(roomsRepository, atLeast(1)).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#update(List, Long)}
     */
    @Test
    void testUpdate3() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenThrow(new BusinessException("Msg"));
        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any()))
                .thenThrow(new BusinessException("Msg"));
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);

        ArrayList<UpdateRoomRequest> updateRoomRequestList = new ArrayList<>();
        updateRoomRequestList.add(new UpdateRoomRequest());
        assertThrows(BusinessException.class, () -> roomServiceImpl.update(updateRoomRequestList, 1L));
        verify(roomsRepository).findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any());
        verify(roomsRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#update(List, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdate4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.equalsIgnoreCase(String)" because "roomName" is null
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.checkDuplicateRoomName(RoomServiceImpl.java:579)
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.lambda$update$7(RoomServiceImpl.java:337)
        //       at java.util.ArrayList.forEach(ArrayList.java:1511)
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.update(RoomServiceImpl.java:336)
        //   See https://diff.blue/R013 to resolve this issue.

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms1);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any()))
                .thenReturn(roomsList);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);

        ArrayList<UpdateRoomRequest> updateRoomRequestList = new ArrayList<>();
        updateRoomRequestList.add(new UpdateRoomRequest());
        roomServiceImpl.update(updateRoomRequestList, 1L);
    }

    /**
     * Method under test: {@link RoomServiceImpl#update(List, Long)}
     */
    @Test
    void testUpdate5() {
        Rooms rooms = mock(Rooms.class);
        when(rooms.getIsDisable()).thenReturn(true);
        when(rooms.getCreatedBy()).thenReturn(1L);
        when(rooms.getId()).thenReturn(123L);
        when(rooms.getContractId()).thenReturn(123L);
        when(rooms.getGroupContractId()).thenReturn(123L);
        when(rooms.getGroupId()).thenReturn(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(rooms.getCreatedAt()).thenReturn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any()))
                .thenReturn(new ArrayList<>());
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);

        ArrayList<UpdateRoomRequest> updateRoomRequestList = new ArrayList<>();
        updateRoomRequestList.add(new UpdateRoomRequest());
        List<Rooms> actualUpdateResult = roomServiceImpl.update(updateRoomRequestList, 1L);
        assertSame(roomsList, actualUpdateResult);
        assertTrue(actualUpdateResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any());
        verify(roomsRepository, atLeast(1)).findById((Long) any());
        verify(rooms).getIsDisable();
        verify(rooms).getCreatedBy();
        verify(rooms).getId();
        verify(rooms).getContractId();
        verify(rooms).getGroupContractId();
        verify(rooms, atLeast(1)).getGroupId();
        verify(rooms).getCreatedAt();
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#update(List, Long)}
     */
    @Test
    void testUpdate6() {
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any()))
                .thenReturn(new ArrayList<>());
        when(roomsRepository.findById((Long) any())).thenReturn(Optional.empty());
        Rooms rooms = mock(Rooms.class);
        when(rooms.getIsDisable()).thenReturn(true);
        when(rooms.getCreatedBy()).thenReturn(1L);
        when(rooms.getId()).thenReturn(123L);
        when(rooms.getContractId()).thenReturn(123L);
        when(rooms.getGroupContractId()).thenReturn(123L);
        when(rooms.getGroupId()).thenReturn(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(rooms.getCreatedAt()).thenReturn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        ArrayList<UpdateRoomRequest> updateRoomRequestList = new ArrayList<>();
        updateRoomRequestList.add(new UpdateRoomRequest());
        assertThrows(BusinessException.class, () -> roomServiceImpl.update(updateRoomRequestList, 1L));
        verify(roomsRepository).findById((Long) any());
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#update(List, Long)}
     */
    @Test
    void testUpdate7() {
        Rooms rooms = mock(Rooms.class);
        when(rooms.getIsDisable()).thenReturn(true);
        when(rooms.getCreatedBy()).thenReturn(1L);
        when(rooms.getId()).thenReturn(123L);
        when(rooms.getContractId()).thenReturn(123L);
        when(rooms.getGroupContractId()).thenReturn(123L);
        when(rooms.getGroupId()).thenReturn(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(rooms.getCreatedAt()).thenReturn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        ArrayList<Rooms> roomsList = new ArrayList<>();
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any()))
                .thenReturn(new ArrayList<>());
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);

        ArrayList<UpdateRoomRequest> updateRoomRequestList = new ArrayList<>();
        updateRoomRequestList.add(new UpdateRoomRequest());
        updateRoomRequestList.add(new UpdateRoomRequest());
        List<Rooms> actualUpdateResult = roomServiceImpl.update(updateRoomRequestList, 1L);
        assertSame(roomsList, actualUpdateResult);
        assertTrue(actualUpdateResult.isEmpty());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any());
        verify(roomsRepository, atLeast(1)).findById((Long) any());
        verify(rooms, atLeast(1)).getIsDisable();
        verify(rooms, atLeast(1)).getCreatedBy();
        verify(rooms, atLeast(1)).getId();
        verify(rooms, atLeast(1)).getContractId();
        verify(rooms, atLeast(1)).getGroupContractId();
        verify(rooms, atLeast(1)).getGroupId();
        verify(rooms, atLeast(1)).getCreatedAt();
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#update(List, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdate8() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.requests.UpdateRoomRequest.getRoomId()" because the return value of "com.diffblue.cover.agent.readwrite.RuntimeWrappers.list$get(java.util.List, int)" is null
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.update(RoomServiceImpl.java:333)
        //   See https://diff.blue/R013 to resolve this issue.

        Rooms rooms = mock(Rooms.class);
        when(rooms.getIsDisable()).thenReturn(true);
        when(rooms.getCreatedBy()).thenReturn(1L);
        when(rooms.getId()).thenReturn(123L);
        when(rooms.getContractId()).thenReturn(123L);
        when(rooms.getGroupContractId()).thenReturn(123L);
        when(rooms.getGroupId()).thenReturn(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(rooms.getCreatedAt()).thenReturn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByGroupIdAndIdNotInAndIsDisableIsFalse((Long) any(), (List<Long>) any()))
                .thenReturn(new ArrayList<>());
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);

        ArrayList<UpdateRoomRequest> updateRoomRequestList = new ArrayList<>();
        updateRoomRequestList.add(null);
        roomServiceImpl.update(updateRoomRequestList, 1L);
    }

    /**
     * Method under test: {@link RoomServiceImpl#preview(RoomsPreviewRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testPreview() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Iterable.iterator()" because "iterable" is null
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.preview(RoomServiceImpl.java:359)
        //   See https://diff.blue/R013 to resolve this issue.

        roomServiceImpl.preview(new RoomsPreviewRequest());
    }

    /**
     * Method under test: {@link RoomServiceImpl#preview(RoomsPreviewRequest)}
     */
    @Test
    void testPreview2() {
        when(roomsRepository.findAllByRoomFloorInAndGroupIdAndIsDisableIs((List<Integer>) any(), (Long) any(),
                (Boolean) any())).thenReturn(new ArrayList<>());

        RoomsPreviewRequest roomsPreviewRequest = new RoomsPreviewRequest();
        ArrayList<Integer> integerList = new ArrayList<>();
        roomsPreviewRequest.setListFloor(integerList);
        RoomsPreviewResponse.SeparationRoomPreview actualPreviewResult = roomServiceImpl.preview(roomsPreviewRequest);
        assertEquals(integerList, actualPreviewResult.getListGenerateRoom());
        assertEquals(integerList, actualPreviewResult.getListOldRoom());
        verify(roomsRepository).findAllByRoomFloorInAndGroupIdAndIsDisableIs((List<Integer>) any(), (Long) any(),
                (Boolean) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#preview(RoomsPreviewRequest)}
     */
    @Test
    void testPreview3() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        when(roomsRepository.findAllByRoomFloorInAndGroupIdAndIsDisableIs((List<Integer>) any(), (Long) any(),
                (Boolean) any())).thenReturn(roomsList);

        RoomsPreviewRequest roomsPreviewRequest = new RoomsPreviewRequest();
        ArrayList<Integer> integerList = new ArrayList<>();
        roomsPreviewRequest.setListFloor(integerList);
        RoomsPreviewResponse.SeparationRoomPreview actualPreviewResult = roomServiceImpl.preview(roomsPreviewRequest);
        assertEquals(integerList, actualPreviewResult.getListGenerateRoom());
        assertEquals(1, actualPreviewResult.getListOldRoom().size());
        verify(roomsRepository).findAllByRoomFloorInAndGroupIdAndIsDisableIs((List<Integer>) any(), (Long) any(),
                (Boolean) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#preview(RoomsPreviewRequest)}
     */
    @Test
    void testPreview4() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms1);
        roomsList.add(rooms);
        when(roomsRepository.findAllByRoomFloorInAndGroupIdAndIsDisableIs((List<Integer>) any(), (Long) any(),
                (Boolean) any())).thenReturn(roomsList);

        RoomsPreviewRequest roomsPreviewRequest = new RoomsPreviewRequest();
        ArrayList<Integer> integerList = new ArrayList<>();
        roomsPreviewRequest.setListFloor(integerList);
        RoomsPreviewResponse.SeparationRoomPreview actualPreviewResult = roomServiceImpl.preview(roomsPreviewRequest);
        assertEquals(integerList, actualPreviewResult.getListGenerateRoom());
        assertEquals(2, actualPreviewResult.getListOldRoom().size());
        verify(roomsRepository).findAllByRoomFloorInAndGroupIdAndIsDisableIs((List<Integer>) any(), (Long) any(),
                (Boolean) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#preview(RoomsPreviewRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testPreview5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "vn.com.fpt.requests.RoomsPreviewRequest.getTotalRoomPerFloor()" is null
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.preview(RoomServiceImpl.java:400)
        //   See https://diff.blue/R013 to resolve this issue.

        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByRoomFloorInAndGroupIdAndIsDisableIs((List<Integer>) any(), (Long) any(),
                (Boolean) any())).thenReturn(new ArrayList<>());

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);

        RoomsPreviewRequest roomsPreviewRequest = new RoomsPreviewRequest();
        roomsPreviewRequest.setListFloor(integerList);
        roomServiceImpl.preview(roomsPreviewRequest);
    }

    /**
     * Method under test: {@link RoomServiceImpl#preview(RoomsPreviewRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testPreview6() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "vn.com.fpt.requests.RoomsPreviewRequest.getTotalRoomPerFloor()" is null
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.preview(RoomServiceImpl.java:421)
        //   See https://diff.blue/R013 to resolve this issue.

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("isDisable");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByRoomFloorInAndGroupIdAndIsDisableIs((List<Integer>) any(), (Long) any(),
                (Boolean) any())).thenReturn(new ArrayList<>());
        when(assetService.listRoomAsset((Long) any())).thenReturn(new ArrayList<>());

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);

        RoomsPreviewRequest roomsPreviewRequest = new RoomsPreviewRequest();
        roomsPreviewRequest.setListFloor(integerList);
        roomServiceImpl.preview(roomsPreviewRequest);
    }

    /**
     * Method under test: {@link RoomServiceImpl#preview(RoomsPreviewRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testPreview7() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.ArrayIndexOutOfBoundsException: Index 99 out of bounds for length 99
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.preview(RoomServiceImpl.java:372)
        //   See https://diff.blue/R013 to resolve this issue.

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("isDisable");
        rooms.setRoomPrice(10.0d);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("999");
        rooms1.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms1);
        roomsList.add(rooms);
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByRoomFloorInAndGroupIdAndIsDisableIs((List<Integer>) any(), (Long) any(),
                (Boolean) any())).thenReturn(new ArrayList<>());
        when(assetService.listRoomAsset((Long) any())).thenReturn(new ArrayList<>());

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);

        RoomsPreviewRequest roomsPreviewRequest = new RoomsPreviewRequest();
        roomsPreviewRequest.setListFloor(integerList);
        roomServiceImpl.preview(roomsPreviewRequest);
    }

    /**
     * Method under test: {@link RoomServiceImpl#preview(RoomsPreviewRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testPreview8() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "vn.com.fpt.requests.RoomsPreviewRequest.getTotalRoomPerFloor()" is null
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.preview(RoomServiceImpl.java:421)
        //   See https://diff.blue/R013 to resolve this issue.

        Rooms rooms = mock(Rooms.class);
        when(rooms.getIsDisable()).thenReturn(true);
        when(rooms.getRoomArea()).thenReturn(10.0d);
        when(rooms.getRoomPrice()).thenReturn(10.0d);
        when(rooms.getRoomCurrentElectricIndex()).thenReturn(1);
        when(rooms.getRoomCurrentWaterIndex()).thenReturn(1);
        when(rooms.getRoomFloor()).thenReturn(1);
        when(rooms.getRoomLimitPeople()).thenReturn(1);
        when(rooms.getId()).thenReturn(123L);
        when(rooms.getContractId()).thenReturn(123L);
        when(rooms.getGroupContractId()).thenReturn(123L);
        when(rooms.getGroupId()).thenReturn(123L);
        when(rooms.getRoomName()).thenReturn("Room Name");
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("isDisable");
        rooms.setRoomPrice(10.0d);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms1);
        roomsList.add(rooms);
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByRoomFloorInAndGroupIdAndIsDisableIs((List<Integer>) any(), (Long) any(),
                (Boolean) any())).thenReturn(new ArrayList<>());
        when(assetService.listRoomAsset((Long) any())).thenReturn(new ArrayList<>());

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);

        RoomsPreviewRequest roomsPreviewRequest = new RoomsPreviewRequest();
        roomsPreviewRequest.setListFloor(integerList);
        roomServiceImpl.preview(roomsPreviewRequest);
    }

    /**
     * Method under test: {@link RoomServiceImpl#preview(RoomsPreviewRequest)}
     */
    @Test
    void testPreview9() {
        Rooms rooms = mock(Rooms.class);
        when(rooms.getIsDisable()).thenReturn(true);
        when(rooms.getRoomArea()).thenReturn(10.0d);
        when(rooms.getRoomPrice()).thenReturn(10.0d);
        when(rooms.getRoomCurrentElectricIndex()).thenReturn(1);
        when(rooms.getRoomCurrentWaterIndex()).thenReturn(1);
        when(rooms.getRoomFloor()).thenReturn(1);
        when(rooms.getRoomLimitPeople()).thenReturn(1);
        when(rooms.getId()).thenReturn(123L);
        when(rooms.getContractId()).thenReturn(123L);
        when(rooms.getGroupContractId()).thenReturn(123L);
        when(rooms.getGroupId()).thenReturn(123L);
        when(rooms.getRoomName()).thenReturn("Room Name");
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("isDisable");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(roomsList);
        when(roomsRepository.findAllByRoomFloorInAndGroupIdAndIsDisableIs((List<Integer>) any(), (Long) any(),
                (Boolean) any())).thenReturn(new ArrayList<>());
        ArrayList<RoomAssets> roomAssetsList = new ArrayList<>();
        when(assetService.listRoomAsset((Long) any())).thenReturn(roomAssetsList);

        ArrayList<Integer> integerList = new ArrayList<>();
        integerList.add(2);

        RoomsPreviewRequest roomsPreviewRequest = new RoomsPreviewRequest(123L, 1, "Room Name Convention", 1, 10.0d,
                10.0d, new ArrayList<>());
        roomsPreviewRequest.setListFloor(integerList);
        RoomsPreviewResponse.SeparationRoomPreview actualPreviewResult = roomServiceImpl.preview(roomsPreviewRequest);
        assertEquals(1, actualPreviewResult.getListGenerateRoom().size());
        assertEquals(roomAssetsList, actualPreviewResult.getListOldRoom());
        verify(roomsRepository, atLeast(1)).findAll((Specification<Rooms>) any(), (Sort) any());
        verify(roomsRepository).findAllByRoomFloorInAndGroupIdAndIsDisableIs((List<Integer>) any(), (Long) any(),
                (Boolean) any());
        verify(rooms, atLeast(1)).getIsDisable();
        verify(rooms, atLeast(1)).getRoomArea();
        verify(rooms, atLeast(1)).getRoomPrice();
        verify(rooms, atLeast(1)).getRoomCurrentElectricIndex();
        verify(rooms, atLeast(1)).getRoomCurrentWaterIndex();
        verify(rooms, atLeast(1)).getRoomFloor();
        verify(rooms, atLeast(1)).getRoomLimitPeople();
        verify(rooms, atLeast(1)).getId();
        verify(rooms, atLeast(1)).getContractId();
        verify(rooms, atLeast(1)).getGroupContractId();
        verify(rooms, atLeast(1)).getGroupId();
        verify(rooms, atLeast(1)).getRoomName();
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
        verify(assetService, atLeast(1)).listRoomAsset((Long) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#adjustRoomPrice(AdjustRoomPriceRequest, Long)}
     */
    @Test
    void testAdjustRoomPrice() {
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(new ArrayList<>());

        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("Group Description");
        roomGroups.setGroupName("Group Name");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);
        when(groupService.getGroup((Long) any())).thenReturn(roomGroups);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        AdjustRoomPriceResponse actualAdjustRoomPriceResult = roomServiceImpl
                .adjustRoomPrice(new AdjustRoomPriceRequest(), 1L);
        assertEquals(123L, actualAdjustRoomPriceResult.getGroupId().longValue());
        assertNull(actualAdjustRoomPriceResult.getNumber());
        assertEquals("", actualAdjustRoomPriceResult.getListRoomNameAdjust());
        assertTrue(actualAdjustRoomPriceResult.getListRoomAdjust().isEmpty());
        assertNull(actualAdjustRoomPriceResult.getIncrease());
        assertEquals("Group Name", actualAdjustRoomPriceResult.getGroupName());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAll((Specification<Rooms>) any(), (Sort) any());
        verify(groupService).getGroup((Long) any());
        verify(contractRepository).findAllByGroupIdAndContractType((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#adjustRoomPrice(AdjustRoomPriceRequest, Long)}
     */
    @Test
    void testAdjustRoomPrice2() {
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(new ArrayList<>());

        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("Group Description");
        roomGroups.setGroupName("Group Name");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);
        when(groupService.getGroup((Long) any())).thenReturn(roomGroups);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any()))
                .thenThrow(new BusinessException("isDisable"));
        assertThrows(BusinessException.class, () -> roomServiceImpl.adjustRoomPrice(new AdjustRoomPriceRequest(), 1L));
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAll((Specification<Rooms>) any(), (Sort) any());
        verify(contractRepository).findAllByGroupIdAndContractType((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#adjustRoomPrice(AdjustRoomPriceRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAdjustRoomPrice3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Boolean.equals(Object)" because the return value of "vn.com.fpt.requests.AdjustRoomPriceRequest.getIncrease()" is null
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.adjustRoomPrice(RoomServiceImpl.java:522)
        //   See https://diff.blue/R013 to resolve this issue.

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("isDisable");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(roomsList);
        when(assetService.listRoomAsset((Long) any())).thenReturn(new ArrayList<>());

        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("Group Description");
        roomGroups.setGroupName("Group Name");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);
        when(groupService.getGroup((Long) any())).thenReturn(roomGroups);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        roomServiceImpl.adjustRoomPrice(new AdjustRoomPriceRequest(), 1L);
    }

    /**
     * Method under test: {@link RoomServiceImpl#adjustRoomPrice(AdjustRoomPriceRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAdjustRoomPrice4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Boolean.equals(Object)" because the return value of "vn.com.fpt.requests.AdjustRoomPriceRequest.getIncrease()" is null
        //       at vn.com.fpt.service.rooms.RoomServiceImpl.adjustRoomPrice(RoomServiceImpl.java:522)
        //   See https://diff.blue/R013 to resolve this issue.

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("isDisable");
        rooms.setRoomPrice(10.0d);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("isDisable");
        rooms1.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms1);
        roomsList.add(rooms);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(roomsList);
        when(assetService.listRoomAsset((Long) any())).thenReturn(new ArrayList<>());

        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("Group Description");
        roomGroups.setGroupName("Group Name");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);
        when(groupService.getGroup((Long) any())).thenReturn(roomGroups);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        roomServiceImpl.adjustRoomPrice(new AdjustRoomPriceRequest(), 1L);
    }

    /**
     * Method under test: {@link RoomServiceImpl#adjustRoomPrice(AdjustRoomPriceRequest, Long)}
     */
    @Test
    void testAdjustRoomPrice5() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("isDisable");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);

        Rooms rooms1 = new Rooms();
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms1);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(roomsList);
        when(assetService.listRoomAsset((Long) any())).thenReturn(new ArrayList<>());

        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("Group Description");
        roomGroups.setGroupName("Group Name");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);
        when(groupService.getGroup((Long) any())).thenReturn(roomGroups);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        AdjustRoomPriceResponse actualAdjustRoomPriceResult = roomServiceImpl
                .adjustRoomPrice(new AdjustRoomPriceRequest(10.0d, true, 123L), 1L);
        assertEquals(123L, actualAdjustRoomPriceResult.getGroupId().longValue());
        assertEquals(10.0d, actualAdjustRoomPriceResult.getNumber().doubleValue());
        assertEquals("isDisable", actualAdjustRoomPriceResult.getListRoomNameAdjust());
        assertEquals(1, actualAdjustRoomPriceResult.getListRoomAdjust().size());
        assertTrue(actualAdjustRoomPriceResult.getIncrease());
        assertEquals("Group Name", actualAdjustRoomPriceResult.getGroupName());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAll((Specification<Rooms>) any(), (Sort) any());
        verify(roomsRepository).findById((Long) any());
        verify(assetService).listRoomAsset((Long) any());
        verify(groupService).getGroup((Long) any());
        verify(contractRepository).findAllByGroupIdAndContractType((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#adjustRoomPrice(AdjustRoomPriceRequest, Long)}
     */
    @Test
    void testAdjustRoomPrice6() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("isDisable");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        Rooms rooms1 = mock(Rooms.class);
        when(rooms1.getIsDisable()).thenReturn(true);
        when(rooms1.getCreatedBy()).thenReturn(1L);
        when(rooms1.getId()).thenReturn(123L);
        when(rooms1.getContractId()).thenReturn(123L);
        when(rooms1.getGroupContractId()).thenReturn(123L);
        when(rooms1.getGroupId()).thenReturn(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(rooms1.getCreatedAt()).thenReturn(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        doNothing().when(rooms1).setCreatedAt((Date) any());
        doNothing().when(rooms1).setCreatedBy((Long) any());
        doNothing().when(rooms1).setId((Long) any());
        doNothing().when(rooms1).setModifiedAt((Date) any());
        doNothing().when(rooms1).setModifiedBy((Long) any());
        doNothing().when(rooms1).setContractId((Long) any());
        doNothing().when(rooms1).setGroupContractId((Long) any());
        doNothing().when(rooms1).setGroupId((Long) any());
        doNothing().when(rooms1).setIsDisable((Boolean) any());
        doNothing().when(rooms1).setRoomArea((Double) any());
        doNothing().when(rooms1).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms1).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms1).setRoomFloor((Integer) any());
        doNothing().when(rooms1).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms1).setRoomName((String) any());
        doNothing().when(rooms1).setRoomPrice((Double) any());
        rooms1.setContractId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        Optional<Rooms> ofResult = Optional.of(rooms1);
        when(roomsRepository.findById((Long) any())).thenReturn(ofResult);
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAll((Specification<Rooms>) any(), (Sort) any())).thenReturn(roomsList);
        when(assetService.listRoomAsset((Long) any())).thenReturn(new ArrayList<>());

        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("Group Description");
        roomGroups.setGroupName("Group Name");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);
        when(groupService.getGroup((Long) any())).thenReturn(roomGroups);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        AdjustRoomPriceResponse actualAdjustRoomPriceResult = roomServiceImpl
                .adjustRoomPrice(new AdjustRoomPriceRequest(10.0d, true, 123L), 1L);
        assertEquals(123L, actualAdjustRoomPriceResult.getGroupId().longValue());
        assertEquals(10.0d, actualAdjustRoomPriceResult.getNumber().doubleValue());
        assertEquals("isDisable", actualAdjustRoomPriceResult.getListRoomNameAdjust());
        assertEquals(1, actualAdjustRoomPriceResult.getListRoomAdjust().size());
        assertTrue(actualAdjustRoomPriceResult.getIncrease());
        assertEquals("Group Name", actualAdjustRoomPriceResult.getGroupName());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAll((Specification<Rooms>) any(), (Sort) any());
        verify(roomsRepository).findById((Long) any());
        verify(rooms1).getIsDisable();
        verify(rooms1).getCreatedBy();
        verify(rooms1).getId();
        verify(rooms1).getContractId();
        verify(rooms1).getGroupContractId();
        verify(rooms1).getGroupId();
        verify(rooms1).getCreatedAt();
        verify(rooms1).setCreatedAt((Date) any());
        verify(rooms1).setCreatedBy((Long) any());
        verify(rooms1).setId((Long) any());
        verify(rooms1).setModifiedAt((Date) any());
        verify(rooms1).setModifiedBy((Long) any());
        verify(rooms1).setContractId((Long) any());
        verify(rooms1).setGroupContractId((Long) any());
        verify(rooms1).setGroupId((Long) any());
        verify(rooms1).setIsDisable((Boolean) any());
        verify(rooms1).setRoomArea((Double) any());
        verify(rooms1).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms1).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms1).setRoomFloor((Integer) any());
        verify(rooms1).setRoomLimitPeople((Integer) any());
        verify(rooms1).setRoomName((String) any());
        verify(rooms1).setRoomPrice((Double) any());
        verify(assetService).listRoomAsset((Long) any());
        verify(groupService).getGroup((Long) any());
        verify(contractRepository).findAllByGroupIdAndContractType((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link RoomServiceImpl#checkNoobRoomName(String)}
     */
    @Test
    void testCheckNoobRoomName() {
        assertFalse(roomServiceImpl.checkNoobRoomName("Room Name"));
        assertTrue(roomServiceImpl.checkNoobRoomName("999"));
        assertTrue(roomServiceImpl.checkNoobRoomName("9999"));
    }

    /**
     * Method under test: {@link RoomServiceImpl#checkDuplicateRoomName(List, String)}
     */
    @Test
    void testCheckDuplicateRoomName() {
        assertFalse(roomServiceImpl.checkDuplicateRoomName(new ArrayList<>(), "Room Name"));
    }

    /**
     * Method under test: {@link RoomServiceImpl#checkDuplicateRoomName(List, String)}
     */
    @Test
    void testCheckDuplicateRoomName2() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        assertTrue(roomServiceImpl.checkDuplicateRoomName(roomsList, "Room Name"));
    }

    /**
     * Method under test: {@link RoomServiceImpl#checkDuplicateRoomName(List, String)}
     */
    @Test
    void testCheckDuplicateRoomName3() {
        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("42");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        assertFalse(roomServiceImpl.checkDuplicateRoomName(roomsList, "Room Name"));
    }

    /**
     * Method under test: {@link RoomServiceImpl#checkDuplicateRoomName(List, String)}
     */
    @Test
    void testCheckDuplicateRoomName4() {
        Rooms rooms = mock(Rooms.class);
        when(rooms.getRoomName()).thenReturn("Room Name");
        doNothing().when(rooms).setCreatedAt((Date) any());
        doNothing().when(rooms).setCreatedBy((Long) any());
        doNothing().when(rooms).setId((Long) any());
        doNothing().when(rooms).setModifiedAt((Date) any());
        doNothing().when(rooms).setModifiedBy((Long) any());
        doNothing().when(rooms).setContractId((Long) any());
        doNothing().when(rooms).setGroupContractId((Long) any());
        doNothing().when(rooms).setGroupId((Long) any());
        doNothing().when(rooms).setIsDisable((Boolean) any());
        doNothing().when(rooms).setRoomArea((Double) any());
        doNothing().when(rooms).setRoomCurrentElectricIndex((Integer) any());
        doNothing().when(rooms).setRoomCurrentWaterIndex((Integer) any());
        doNothing().when(rooms).setRoomFloor((Integer) any());
        doNothing().when(rooms).setRoomLimitPeople((Integer) any());
        doNothing().when(rooms).setRoomName((String) any());
        doNothing().when(rooms).setRoomPrice((Double) any());
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);

        ArrayList<Rooms> roomsList = new ArrayList<>();
        roomsList.add(rooms);
        assertTrue(roomServiceImpl.checkDuplicateRoomName(roomsList, "Room Name"));
        verify(rooms).getRoomName();
        verify(rooms).setCreatedAt((Date) any());
        verify(rooms).setCreatedBy((Long) any());
        verify(rooms).setId((Long) any());
        verify(rooms).setModifiedAt((Date) any());
        verify(rooms).setModifiedBy((Long) any());
        verify(rooms).setContractId((Long) any());
        verify(rooms).setGroupContractId((Long) any());
        verify(rooms).setGroupId((Long) any());
        verify(rooms).setIsDisable((Boolean) any());
        verify(rooms).setRoomArea((Double) any());
        verify(rooms).setRoomCurrentElectricIndex((Integer) any());
        verify(rooms).setRoomCurrentWaterIndex((Integer) any());
        verify(rooms).setRoomFloor((Integer) any());
        verify(rooms).setRoomLimitPeople((Integer) any());
        verify(rooms).setRoomName((String) any());
        verify(rooms).setRoomPrice((Double) any());
    }
}

