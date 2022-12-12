package vn.com.fpt.service.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
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
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.entity.Identity;
import vn.com.fpt.entity.RackRenters;
import vn.com.fpt.entity.Renters;
import vn.com.fpt.entity.RoomGroups;
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
import vn.com.fpt.requests.AddGroupRequest;
import vn.com.fpt.requests.UpdateGroupRequest;
import vn.com.fpt.responses.GroupAllResponse;
import vn.com.fpt.responses.RoomsResponse;
import vn.com.fpt.service.TableLogComponent;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.assets.AssetServiceImpl;
import vn.com.fpt.service.contract.ContractServiceImpl;
import vn.com.fpt.service.renter.RenterServiceImpl;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.rooms.RoomServiceImpl;
import vn.com.fpt.service.services.ServicesService;
import vn.com.fpt.service.services.ServicesServiceImpl;

@ContextConfiguration(classes = {GroupServiceImpl.class})
@ExtendWith(SpringExtension.class)
class GroupServiceImplTest {
    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private AssetService assetService;

    @MockBean
    private ContractRepository contractRepository;

    @MockBean
    private GroupRepository groupRepository;

    @Autowired
    private GroupServiceImpl groupServiceImpl;

    @MockBean
    private RackRenterRepository rackRenterRepository;

    @MockBean
    private RoomService roomService;

    @MockBean
    private RoomsRepository roomsRepository;

    @MockBean
    private ServicesService servicesService;

    /**
     * Method under test: {@link GroupServiceImpl#group(Long)}
     */
    @Test
    void testGroup() {
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(new ArrayList<>());
        assertThrows(BusinessException.class, () -> groupServiceImpl.group(123L));
        verify(groupRepository, atLeast(1)).findAll((Specification<RoomGroups>) any(), (Sort) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#group(Long)}
     */
    @Test
    void testGroup2() {
        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        assertThrows(BusinessException.class, () -> groupServiceImpl.group(123L));
        verify(groupRepository, atLeast(1)).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#group(Long)}
     */
    @Test
    void testGroup3() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(true);
        when(roomGroups.getId()).thenReturn(123L);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        assertThrows(BusinessException.class, () -> groupServiceImpl.group(123L));
        verify(groupRepository, atLeast(1)).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups, atLeast(1)).getIsDisable();
        verify(roomGroups).getId();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#group(Long)}
     */
    @Test
    void testGroup4() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(false);
        when(roomGroups.getId()).thenReturn(123L);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        when(addressRepository.findById((Long) any())).thenThrow(new BusinessException("id"));
        assertThrows(BusinessException.class, () -> groupServiceImpl.group(123L));
        verify(roomsRepository).findAllByGroupIdAndIsDisableIsFalse((Long) any());
        verify(roomsRepository).findAllFloorByGroupId((Long) any());
        verify(groupRepository, atLeast(1)).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups, atLeast(1)).getIsDisable();
        verify(roomGroups, atLeast(1)).getId();
        verify(roomGroups).getAddress();
        verify(roomGroups).getGroupDescription();
        verify(roomGroups).getGroupName();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findAllByGroupIdAndContractType((Long) any(), (Integer) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
        verify(addressRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#group(Long)}
     */
    @Test
    void testGroup5() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(true);
        when(roomGroups.getId()).thenReturn(123L);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);

        Contracts contracts = new Contracts();
        contracts.setAddressId(123L);
        contracts.setContractBillCycle(1);
        contracts.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractEndDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractIsDisable(true);
        contracts.setContractName("Contract Name");
        contracts.setContractPaymentCycle(1);
        contracts.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractStartDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractTerm(1);
        contracts.setContractType(1);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setCreatedBy(1L);
        contracts.setGroupId(123L);
        contracts.setId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setModifiedBy(1L);
        contracts.setNote("Note");
        contracts.setRackRenters(1L);
        contracts.setRenters(1L);
        contracts.setRoomId(123L);

        ArrayList<Contracts> contractsList = new ArrayList<>();
        contractsList.add(contracts);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(contractsList);
        when(addressRepository.findById((Long) any())).thenThrow(new BusinessException("id"));
        assertThrows(BusinessException.class, () -> groupServiceImpl.group(123L));
        verify(groupRepository, atLeast(1)).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups).getIsDisable();
        verify(roomGroups).getId();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#getGroup(Long)}
     */
    @Test
    void testGetGroup() {
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
        Optional<RoomGroups> ofResult = Optional.of(roomGroups);
        when(groupRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(roomGroups, groupServiceImpl.getGroup(123L));
        verify(groupRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#getGroup(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetGroup2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:143)
        //       at vn.com.fpt.service.group.GroupServiceImpl.getGroup(GroupServiceImpl.java:70)
        //   See https://diff.blue/R013 to resolve this issue.

        when(groupRepository.findById((Long) any())).thenReturn(Optional.empty());
        groupServiceImpl.getGroup(123L);
    }

    /**
     * Method under test: {@link GroupServiceImpl#getGroup(Long)}
     */
    @Test
    void testGetGroup3() {
        when(groupRepository.findById((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> groupServiceImpl.getGroup(123L));
        verify(groupRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listContracted(String)}
     */
    @Test
    void testListContracted() {
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(new ArrayList<>());
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertTrue(groupServiceImpl.listContracted("Oxford").isEmpty());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listContracted(String)}
     */
    @Test
    void testListContracted2() {
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(new ArrayList<>());
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any()))
                .thenThrow(new BusinessException("id"));
        assertThrows(BusinessException.class, () -> groupServiceImpl.listContracted("Oxford"));
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listContracted(String)}
     */
    @Test
    void testListContracted3() {
        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertTrue(groupServiceImpl.listContracted("Oxford").isEmpty());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listContracted(String)}
     */
    @Test
    void testListContracted4() {
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(new ArrayList<>());

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
        rackRenters.setNote("id");
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
        renters.setLicensePlates("id");
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
        rackRenters1.setNote("id");
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
        renters1.setLicensePlates("id");
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
        rackRenters2.setNote("id");
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
        rackRenters3.setNote("id");
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
        renters2.setLicensePlates("id");
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
        renters3.setLicensePlates("id");
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
        identity.setIdentityBackImg("id");
        identity.setIdentityFrontImg("id");
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
        renters4.setLicensePlates("id");
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

        ArrayList<Address> addressList = new ArrayList<>();
        addressList.add(address3);
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(addressList);
        assertTrue(groupServiceImpl.listContracted("Oxford").isEmpty());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listContracted(String)}
     */
    @Test
    void testListContracted5() {
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(new ArrayList<>());
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertTrue(groupServiceImpl.listContracted("").isEmpty());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listContracted(String)}
     */
    @Test
    void testListContracted6() {
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(true);
        when(roomGroups.getId()).thenReturn(123L);
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertTrue(groupServiceImpl.listContracted("Oxford").isEmpty());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups).getIsDisable();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listContracted(String)}
     */
    @Test
    void testListContracted7() {
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(false);
        when(roomGroups.getId()).thenReturn(123L);
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertTrue(groupServiceImpl.listContracted("Oxford").isEmpty());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups).getIsDisable();
        verify(roomGroups).getId();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findAllByGroupIdAndContractType((Long) any(), (Integer) any());
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listContracted(String)}
     */
    @Test
    void testListContracted8() {
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllRoomsByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(roomService.listRoomLeaseContracted((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        when(roomGroups.getIsDisable()).thenReturn(false);
        when(roomGroups.getId()).thenReturn(123L);
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);

        Contracts contracts = new Contracts();
        contracts.setAddressId(123L);
        contracts.setContractBillCycle(1);
        contracts.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractEndDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractIsDisable(true);
        contracts.setContractName("id");
        contracts.setContractPaymentCycle(1);
        contracts.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractStartDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractTerm(1);
        contracts.setContractType(1);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setCreatedBy(1L);
        contracts.setGroupId(123L);
        contracts.setId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setModifiedBy(1L);
        contracts.setNote("id");
        contracts.setRackRenters(1L);
        contracts.setRenters(1L);
        contracts.setRoomId(123L);

        ArrayList<Contracts> contractsList = new ArrayList<>();
        contractsList.add(contracts);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any())).thenReturn(contractsList);
        when(addressRepository.findById((Long) any())).thenThrow(new BusinessException("id"));
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertThrows(BusinessException.class, () -> groupServiceImpl.listContracted("Oxford"));
        verify(roomsRepository).findAllFloorByGroupId((Long) any());
        verify(roomsRepository).findAllRoomsByGroupId((Long) any());
        verify(roomService).listRoomLeaseContracted((Long) any());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups).getIsDisable();
        verify(roomGroups, atLeast(1)).getId();
        verify(roomGroups).getAddress();
        verify(roomGroups).getGroupDescription();
        verify(roomGroups).getGroupName();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findAllByGroupIdAndContractType((Long) any(), (Integer) any());
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
        verify(addressRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listNonContracted(String)}
     */
    @Test
    void testListNonContracted() {
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(new ArrayList<>());
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertTrue(groupServiceImpl.listNonContracted("Oxford").isEmpty());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listNonContracted(String)}
     */
    @Test
    void testListNonContracted2() {
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(new ArrayList<>());
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any()))
                .thenThrow(new BusinessException("id"));
        assertThrows(BusinessException.class, () -> groupServiceImpl.listNonContracted("Oxford"));
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listNonContracted(String)}
     */
    @Test
    void testListNonContracted3() {
        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertTrue(groupServiceImpl.listNonContracted("Oxford").isEmpty());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listNonContracted(String)}
     */
    @Test
    void testListNonContracted4() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(true);
        when(roomGroups.getId()).thenReturn(123L);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertTrue(groupServiceImpl.listNonContracted("Oxford").isEmpty());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups).getIsDisable();
        verify(roomGroups).getId();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listNonContracted(String)}
     */
    @Test
    void testListNonContracted5() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(false);
        when(roomGroups.getId()).thenReturn(123L);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        when(addressRepository.findById((Long) any())).thenThrow(new BusinessException("id"));
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertThrows(BusinessException.class, () -> groupServiceImpl.listNonContracted("Oxford"));
        verify(roomsRepository).findAllByGroupIdAndIsDisableIsFalse((Long) any());
        verify(roomsRepository).findAllFloorByGroupId((Long) any());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups).getIsDisable();
        verify(roomGroups, atLeast(1)).getId();
        verify(roomGroups).getAddress();
        verify(roomGroups).getGroupDescription();
        verify(roomGroups).getGroupName();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
        verify(addressRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listNonContracted(String)}
     */
    @Test
    void testListNonContracted6() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(true);
        when(roomGroups.getId()).thenReturn(123L);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);

        Contracts contracts = new Contracts();
        contracts.setAddressId(123L);
        contracts.setContractBillCycle(1);
        contracts.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractEndDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractIsDisable(true);
        contracts.setContractName("Contract Name");
        contracts.setContractPaymentCycle(1);
        contracts.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractStartDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractTerm(1);
        contracts.setContractType(1);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setCreatedBy(1L);
        contracts.setGroupId(123L);
        contracts.setId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setModifiedBy(1L);
        contracts.setNote("Note");
        contracts.setRackRenters(1L);
        contracts.setRenters(1L);
        contracts.setRoomId(123L);

        ArrayList<Contracts> contractsList = new ArrayList<>();
        contractsList.add(contracts);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(contractsList);
        when(addressRepository.findById((Long) any())).thenThrow(new BusinessException("id"));
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertTrue(groupServiceImpl.listNonContracted("Oxford").isEmpty());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups).getId();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listNonContracted(String)}
     */
    @Test
    void testListNonContracted7() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(true);
        when(roomGroups.getId()).thenReturn(123L);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());

        Account account = new Account();
        account.setAddress(new Address());
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

        RackRenters rackRenters = new RackRenters();
        rackRenters.setAddress(new Address());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setCreatedBy(1L);
        rackRenters.setEmail("jane.doe@example.org");
        rackRenters.setGender(true);
        rackRenters.setId(123L);
        rackRenters.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setModifiedBy(1L);
        rackRenters.setNote("Note");
        rackRenters.setPhoneNumber("4105551212");
        rackRenters.setRackRenterFullName("Dr Jane Doe");

        Renters renters = new Renters();
        renters.setAddress(new Address());
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setCreatedBy(1L);
        renters.setEmail("jane.doe@example.org");
        renters.setGender(true);
        renters.setId(123L);
        renters.setIdentityNumber("42");
        renters.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        address.setCreatedBy(1L);
        address.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        address.setModifiedBy(1L);
        address.setRackRenters(rackRenters);
        address.setRenters(renters);

        Account account1 = new Account();
        account1.setAddress(address);
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account1.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        account1.setCreatedBy(1L);
        account1.setDeactivate(true);
        account1.setFullName("Dr Jane Doe");
        account1.setGender(true);
        account1.setId(123L);
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account1.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        account1.setModifiedBy(1L);
        account1.setOwner(true);
        account1.setPassword("iloveyou");
        account1.setPhoneNumber("4105551212");
        account1.setRoles(new HashSet<>());
        account1.setUserName("janedoe");

        Account account2 = new Account();
        account2.setAddress(new Address());
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account2.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        account2.setCreatedBy(1L);
        account2.setDeactivate(true);
        account2.setFullName("Dr Jane Doe");
        account2.setGender(true);
        account2.setId(123L);
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account2.setModifiedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        account2.setModifiedBy(1L);
        account2.setOwner(true);
        account2.setPassword("iloveyou");
        account2.setPhoneNumber("4105551212");
        account2.setRoles(new HashSet<>());
        account2.setUserName("janedoe");

        RackRenters rackRenters1 = new RackRenters();
        rackRenters1.setAddress(new Address());
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setCreatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setCreatedBy(1L);
        rackRenters1.setEmail("jane.doe@example.org");
        rackRenters1.setGender(true);
        rackRenters1.setId(123L);
        rackRenters1.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setModifiedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setModifiedBy(1L);
        rackRenters1.setNote("Note");
        rackRenters1.setPhoneNumber("4105551212");
        rackRenters1.setRackRenterFullName("Dr Jane Doe");

        Renters renters1 = new Renters();
        renters1.setAddress(new Address());
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setCreatedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setCreatedBy(1L);
        renters1.setEmail("jane.doe@example.org");
        renters1.setGender(true);
        renters1.setId(123L);
        renters1.setIdentityNumber("42");
        renters1.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setModifiedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult18 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setCreatedAt(Date.from(atStartOfDayResult18.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setCreatedBy(1L);
        address1.setId(123L);
        LocalDateTime atStartOfDayResult19 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setModifiedAt(Date.from(atStartOfDayResult19.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setModifiedBy(1L);
        address1.setRackRenters(rackRenters1);
        address1.setRenters(renters1);

        RackRenters rackRenters2 = new RackRenters();
        rackRenters2.setAddress(address1);
        LocalDateTime atStartOfDayResult20 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters2.setCreatedAt(Date.from(atStartOfDayResult20.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters2.setCreatedBy(1L);
        rackRenters2.setEmail("jane.doe@example.org");
        rackRenters2.setGender(true);
        rackRenters2.setId(123L);
        rackRenters2.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult21 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters2.setModifiedAt(Date.from(atStartOfDayResult21.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters2.setModifiedBy(1L);
        rackRenters2.setNote("Note");
        rackRenters2.setPhoneNumber("4105551212");
        rackRenters2.setRackRenterFullName("Dr Jane Doe");

        Account account3 = new Account();
        account3.setAddress(new Address());
        LocalDateTime atStartOfDayResult22 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account3.setCreatedAt(Date.from(atStartOfDayResult22.atZone(ZoneId.of("UTC")).toInstant()));
        account3.setCreatedBy(1L);
        account3.setDeactivate(true);
        account3.setFullName("Dr Jane Doe");
        account3.setGender(true);
        account3.setId(123L);
        LocalDateTime atStartOfDayResult23 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account3.setModifiedAt(Date.from(atStartOfDayResult23.atZone(ZoneId.of("UTC")).toInstant()));
        account3.setModifiedBy(1L);
        account3.setOwner(true);
        account3.setPassword("iloveyou");
        account3.setPhoneNumber("4105551212");
        account3.setRoles(new HashSet<>());
        account3.setUserName("janedoe");

        RackRenters rackRenters3 = new RackRenters();
        rackRenters3.setAddress(new Address());
        LocalDateTime atStartOfDayResult24 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters3.setCreatedAt(Date.from(atStartOfDayResult24.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters3.setCreatedBy(1L);
        rackRenters3.setEmail("jane.doe@example.org");
        rackRenters3.setGender(true);
        rackRenters3.setId(123L);
        rackRenters3.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult25 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters3.setModifiedAt(Date.from(atStartOfDayResult25.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters3.setModifiedBy(1L);
        rackRenters3.setNote("Note");
        rackRenters3.setPhoneNumber("4105551212");
        rackRenters3.setRackRenterFullName("Dr Jane Doe");

        Renters renters2 = new Renters();
        renters2.setAddress(new Address());
        LocalDateTime atStartOfDayResult26 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setCreatedAt(Date.from(atStartOfDayResult26.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setCreatedBy(1L);
        renters2.setEmail("jane.doe@example.org");
        renters2.setGender(true);
        renters2.setId(123L);
        renters2.setIdentityNumber("42");
        renters2.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult27 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setModifiedAt(Date.from(atStartOfDayResult27.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult28 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setCreatedAt(Date.from(atStartOfDayResult28.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setCreatedBy(1L);
        address2.setId(123L);
        LocalDateTime atStartOfDayResult29 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setModifiedAt(Date.from(atStartOfDayResult29.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setModifiedBy(1L);
        address2.setRackRenters(rackRenters3);
        address2.setRenters(renters2);

        Renters renters3 = new Renters();
        renters3.setAddress(new Address());
        LocalDateTime atStartOfDayResult30 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters3.setCreatedAt(Date.from(atStartOfDayResult30.atZone(ZoneId.of("UTC")).toInstant()));
        renters3.setCreatedBy(1L);
        renters3.setEmail("jane.doe@example.org");
        renters3.setGender(true);
        renters3.setId(123L);
        renters3.setIdentityNumber("42");
        renters3.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult31 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters3.setModifiedAt(Date.from(atStartOfDayResult31.atZone(ZoneId.of("UTC")).toInstant()));
        renters3.setModifiedBy(1L);
        renters3.setPhoneNumber("4105551212");
        renters3.setRenterFullName("Dr Jane Doe");
        renters3.setRenterIdentity(new Identity());
        renters3.setRepresent(true);
        renters3.setRoomId(123L);

        Identity identity = new Identity();
        LocalDateTime atStartOfDayResult32 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setCreatedAt(Date.from(atStartOfDayResult32.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setCreatedBy(1L);
        identity.setId(123L);
        identity.setIdentityBackImg("Identity Back Img");
        identity.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult33 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setModifiedAt(Date.from(atStartOfDayResult33.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setModifiedBy(1L);
        identity.setRenters(renters3);

        Renters renters4 = new Renters();
        renters4.setAddress(address2);
        LocalDateTime atStartOfDayResult34 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters4.setCreatedAt(Date.from(atStartOfDayResult34.atZone(ZoneId.of("UTC")).toInstant()));
        renters4.setCreatedBy(1L);
        renters4.setEmail("jane.doe@example.org");
        renters4.setGender(true);
        renters4.setId(123L);
        renters4.setIdentityNumber("42");
        renters4.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult35 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters4.setModifiedAt(Date.from(atStartOfDayResult35.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult36 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setCreatedAt(Date.from(atStartOfDayResult36.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setCreatedBy(1L);
        address3.setId(123L);
        LocalDateTime atStartOfDayResult37 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setModifiedAt(Date.from(atStartOfDayResult37.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setModifiedBy(1L);
        address3.setRackRenters(rackRenters2);
        address3.setRenters(renters4);

        ArrayList<Address> addressList = new ArrayList<>();
        addressList.add(address3);
        when(addressRepository.findById((Long) any())).thenThrow(new BusinessException("id"));
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(addressList);
        assertTrue(groupServiceImpl.listNonContracted("Oxford").isEmpty());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups).getIsDisable();
        verify(roomGroups).getId();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listNonContracted(String)}
     */
    @Test
    void testListNonContracted8() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(true);
        when(roomGroups.getId()).thenReturn(123L);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        when(addressRepository.findById((Long) any())).thenThrow(new BusinessException("id"));
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertTrue(groupServiceImpl.listNonContracted("").isEmpty());
        verify(groupRepository).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups).getIsDisable();
        verify(roomGroups).getId();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listGroup(String)}
     */
    @Test
    void testListGroup() {
        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        GroupAllResponse actualListGroupResult = groupServiceImpl.listGroup("Oxford");
        assertEquals(roomGroupsList, actualListGroupResult.getListGroupContracted());
        assertEquals(roomGroupsList, actualListGroupResult.getListGroupNonContracted());
        verify(groupRepository, atLeast(1)).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(addressRepository, atLeast(1)).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listGroup(String)}
     */
    @Test
    void testListGroup2() {
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(new ArrayList<>());
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any()))
                .thenThrow(new BusinessException("id"));
        assertThrows(BusinessException.class, () -> groupServiceImpl.listGroup("Oxford"));
        verify(addressRepository).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listGroup(String)}
     */
    @Test
    void testListGroup3() {
        RoomGroups roomGroups = new RoomGroups();
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        ArrayList<Address> addressList = new ArrayList<>();
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(addressList);
        GroupAllResponse actualListGroupResult = groupServiceImpl.listGroup("Oxford");
        assertEquals(addressList, actualListGroupResult.getListGroupContracted());
        assertEquals(addressList, actualListGroupResult.getListGroupNonContracted());
        verify(groupRepository, atLeast(1)).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
        verify(addressRepository, atLeast(1)).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listGroup(String)}
     */
    @Test
    void testListGroup4() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(true);
        when(roomGroups.getId()).thenReturn(123L);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        ArrayList<Address> addressList = new ArrayList<>();
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(addressList);
        GroupAllResponse actualListGroupResult = groupServiceImpl.listGroup("Oxford");
        assertEquals(addressList, actualListGroupResult.getListGroupContracted());
        assertEquals(addressList, actualListGroupResult.getListGroupNonContracted());
        verify(groupRepository, atLeast(1)).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups, atLeast(1)).getIsDisable();
        verify(roomGroups).getId();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
        verify(addressRepository, atLeast(1)).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listGroup(String)}
     */
    @Test
    void testListGroup5() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(false);
        when(roomGroups.getId()).thenReturn(123L);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        when(addressRepository.findById((Long) any())).thenThrow(new BusinessException("id"));
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(new ArrayList<>());
        assertThrows(BusinessException.class, () -> groupServiceImpl.listGroup("Oxford"));
        verify(roomsRepository).findAllByGroupIdAndIsDisableIsFalse((Long) any());
        verify(roomsRepository).findAllFloorByGroupId((Long) any());
        verify(groupRepository, atLeast(1)).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups, atLeast(1)).getIsDisable();
        verify(roomGroups, atLeast(1)).getId();
        verify(roomGroups).getAddress();
        verify(roomGroups).getGroupDescription();
        verify(roomGroups).getGroupName();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findAllByGroupIdAndContractType((Long) any(), (Integer) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
        verify(addressRepository, atLeast(1)).findAllByAddressCityEqualsIgnoreCase((String) any());
        verify(addressRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listGroup(String)}
     */
    @Test
    void testListGroup6() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(true);
        when(roomGroups.getId()).thenReturn(123L);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);

        Contracts contracts = new Contracts();
        contracts.setAddressId(123L);
        contracts.setContractBillCycle(1);
        contracts.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractEndDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractIsDisable(true);
        contracts.setContractName("Contract Name");
        contracts.setContractPaymentCycle(1);
        contracts.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractStartDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractTerm(1);
        contracts.setContractType(1);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setCreatedBy(1L);
        contracts.setGroupId(123L);
        contracts.setId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setModifiedBy(1L);
        contracts.setNote("Note");
        contracts.setRackRenters(1L);
        contracts.setRenters(1L);
        contracts.setRoomId(123L);

        ArrayList<Contracts> contractsList = new ArrayList<>();
        contractsList.add(contracts);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(contractsList);
        when(addressRepository.findById((Long) any())).thenThrow(new BusinessException("id"));
        ArrayList<Address> addressList = new ArrayList<>();
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(addressList);
        GroupAllResponse actualListGroupResult = groupServiceImpl.listGroup("Oxford");
        assertEquals(addressList, actualListGroupResult.getListGroupContracted());
        assertEquals(addressList, actualListGroupResult.getListGroupNonContracted());
        verify(groupRepository, atLeast(1)).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups).getIsDisable();
        verify(roomGroups).getId();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
        verify(addressRepository, atLeast(1)).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listGroup(String)}
     */
    @Test
    void testListGroup7() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(true);
        when(roomGroups.getId()).thenReturn(123L);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        ArrayList<Contracts> contractsList = new ArrayList<>();
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(contractsList);

        Account account = new Account();
        account.setAddress(new Address());
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

        RackRenters rackRenters = new RackRenters();
        rackRenters.setAddress(new Address());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setCreatedBy(1L);
        rackRenters.setEmail("jane.doe@example.org");
        rackRenters.setGender(true);
        rackRenters.setId(123L);
        rackRenters.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters.setModifiedBy(1L);
        rackRenters.setNote("Note");
        rackRenters.setPhoneNumber("4105551212");
        rackRenters.setRackRenterFullName("Dr Jane Doe");

        Renters renters = new Renters();
        renters.setAddress(new Address());
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        renters.setCreatedBy(1L);
        renters.setEmail("jane.doe@example.org");
        renters.setGender(true);
        renters.setId(123L);
        renters.setIdentityNumber("42");
        renters.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        address.setCreatedBy(1L);
        address.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        address.setModifiedBy(1L);
        address.setRackRenters(rackRenters);
        address.setRenters(renters);

        Account account1 = new Account();
        account1.setAddress(address);
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account1.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        account1.setCreatedBy(1L);
        account1.setDeactivate(true);
        account1.setFullName("Dr Jane Doe");
        account1.setGender(true);
        account1.setId(123L);
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account1.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        account1.setModifiedBy(1L);
        account1.setOwner(true);
        account1.setPassword("iloveyou");
        account1.setPhoneNumber("4105551212");
        account1.setRoles(new HashSet<>());
        account1.setUserName("janedoe");

        Account account2 = new Account();
        account2.setAddress(new Address());
        LocalDateTime atStartOfDayResult12 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account2.setCreatedAt(Date.from(atStartOfDayResult12.atZone(ZoneId.of("UTC")).toInstant()));
        account2.setCreatedBy(1L);
        account2.setDeactivate(true);
        account2.setFullName("Dr Jane Doe");
        account2.setGender(true);
        account2.setId(123L);
        LocalDateTime atStartOfDayResult13 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account2.setModifiedAt(Date.from(atStartOfDayResult13.atZone(ZoneId.of("UTC")).toInstant()));
        account2.setModifiedBy(1L);
        account2.setOwner(true);
        account2.setPassword("iloveyou");
        account2.setPhoneNumber("4105551212");
        account2.setRoles(new HashSet<>());
        account2.setUserName("janedoe");

        RackRenters rackRenters1 = new RackRenters();
        rackRenters1.setAddress(new Address());
        LocalDateTime atStartOfDayResult14 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setCreatedAt(Date.from(atStartOfDayResult14.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setCreatedBy(1L);
        rackRenters1.setEmail("jane.doe@example.org");
        rackRenters1.setGender(true);
        rackRenters1.setId(123L);
        rackRenters1.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult15 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters1.setModifiedAt(Date.from(atStartOfDayResult15.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters1.setModifiedBy(1L);
        rackRenters1.setNote("Note");
        rackRenters1.setPhoneNumber("4105551212");
        rackRenters1.setRackRenterFullName("Dr Jane Doe");

        Renters renters1 = new Renters();
        renters1.setAddress(new Address());
        LocalDateTime atStartOfDayResult16 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setCreatedAt(Date.from(atStartOfDayResult16.atZone(ZoneId.of("UTC")).toInstant()));
        renters1.setCreatedBy(1L);
        renters1.setEmail("jane.doe@example.org");
        renters1.setGender(true);
        renters1.setId(123L);
        renters1.setIdentityNumber("42");
        renters1.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult17 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters1.setModifiedAt(Date.from(atStartOfDayResult17.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult18 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setCreatedAt(Date.from(atStartOfDayResult18.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setCreatedBy(1L);
        address1.setId(123L);
        LocalDateTime atStartOfDayResult19 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address1.setModifiedAt(Date.from(atStartOfDayResult19.atZone(ZoneId.of("UTC")).toInstant()));
        address1.setModifiedBy(1L);
        address1.setRackRenters(rackRenters1);
        address1.setRenters(renters1);

        RackRenters rackRenters2 = new RackRenters();
        rackRenters2.setAddress(address1);
        LocalDateTime atStartOfDayResult20 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters2.setCreatedAt(Date.from(atStartOfDayResult20.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters2.setCreatedBy(1L);
        rackRenters2.setEmail("jane.doe@example.org");
        rackRenters2.setGender(true);
        rackRenters2.setId(123L);
        rackRenters2.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult21 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters2.setModifiedAt(Date.from(atStartOfDayResult21.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters2.setModifiedBy(1L);
        rackRenters2.setNote("Note");
        rackRenters2.setPhoneNumber("4105551212");
        rackRenters2.setRackRenterFullName("Dr Jane Doe");

        Account account3 = new Account();
        account3.setAddress(new Address());
        LocalDateTime atStartOfDayResult22 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account3.setCreatedAt(Date.from(atStartOfDayResult22.atZone(ZoneId.of("UTC")).toInstant()));
        account3.setCreatedBy(1L);
        account3.setDeactivate(true);
        account3.setFullName("Dr Jane Doe");
        account3.setGender(true);
        account3.setId(123L);
        LocalDateTime atStartOfDayResult23 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account3.setModifiedAt(Date.from(atStartOfDayResult23.atZone(ZoneId.of("UTC")).toInstant()));
        account3.setModifiedBy(1L);
        account3.setOwner(true);
        account3.setPassword("iloveyou");
        account3.setPhoneNumber("4105551212");
        account3.setRoles(new HashSet<>());
        account3.setUserName("janedoe");

        RackRenters rackRenters3 = new RackRenters();
        rackRenters3.setAddress(new Address());
        LocalDateTime atStartOfDayResult24 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters3.setCreatedAt(Date.from(atStartOfDayResult24.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters3.setCreatedBy(1L);
        rackRenters3.setEmail("jane.doe@example.org");
        rackRenters3.setGender(true);
        rackRenters3.setId(123L);
        rackRenters3.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult25 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters3.setModifiedAt(Date.from(atStartOfDayResult25.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters3.setModifiedBy(1L);
        rackRenters3.setNote("Note");
        rackRenters3.setPhoneNumber("4105551212");
        rackRenters3.setRackRenterFullName("Dr Jane Doe");

        Renters renters2 = new Renters();
        renters2.setAddress(new Address());
        LocalDateTime atStartOfDayResult26 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setCreatedAt(Date.from(atStartOfDayResult26.atZone(ZoneId.of("UTC")).toInstant()));
        renters2.setCreatedBy(1L);
        renters2.setEmail("jane.doe@example.org");
        renters2.setGender(true);
        renters2.setId(123L);
        renters2.setIdentityNumber("42");
        renters2.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult27 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters2.setModifiedAt(Date.from(atStartOfDayResult27.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult28 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setCreatedAt(Date.from(atStartOfDayResult28.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setCreatedBy(1L);
        address2.setId(123L);
        LocalDateTime atStartOfDayResult29 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address2.setModifiedAt(Date.from(atStartOfDayResult29.atZone(ZoneId.of("UTC")).toInstant()));
        address2.setModifiedBy(1L);
        address2.setRackRenters(rackRenters3);
        address2.setRenters(renters2);

        Renters renters3 = new Renters();
        renters3.setAddress(new Address());
        LocalDateTime atStartOfDayResult30 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters3.setCreatedAt(Date.from(atStartOfDayResult30.atZone(ZoneId.of("UTC")).toInstant()));
        renters3.setCreatedBy(1L);
        renters3.setEmail("jane.doe@example.org");
        renters3.setGender(true);
        renters3.setId(123L);
        renters3.setIdentityNumber("42");
        renters3.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult31 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters3.setModifiedAt(Date.from(atStartOfDayResult31.atZone(ZoneId.of("UTC")).toInstant()));
        renters3.setModifiedBy(1L);
        renters3.setPhoneNumber("4105551212");
        renters3.setRenterFullName("Dr Jane Doe");
        renters3.setRenterIdentity(new Identity());
        renters3.setRepresent(true);
        renters3.setRoomId(123L);

        Identity identity = new Identity();
        LocalDateTime atStartOfDayResult32 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setCreatedAt(Date.from(atStartOfDayResult32.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setCreatedBy(1L);
        identity.setId(123L);
        identity.setIdentityBackImg("Identity Back Img");
        identity.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult33 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity.setModifiedAt(Date.from(atStartOfDayResult33.atZone(ZoneId.of("UTC")).toInstant()));
        identity.setModifiedBy(1L);
        identity.setRenters(renters3);

        Renters renters4 = new Renters();
        renters4.setAddress(address2);
        LocalDateTime atStartOfDayResult34 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters4.setCreatedAt(Date.from(atStartOfDayResult34.atZone(ZoneId.of("UTC")).toInstant()));
        renters4.setCreatedBy(1L);
        renters4.setEmail("jane.doe@example.org");
        renters4.setGender(true);
        renters4.setId(123L);
        renters4.setIdentityNumber("42");
        renters4.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult35 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters4.setModifiedAt(Date.from(atStartOfDayResult35.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult36 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setCreatedAt(Date.from(atStartOfDayResult36.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setCreatedBy(1L);
        address3.setId(123L);
        LocalDateTime atStartOfDayResult37 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address3.setModifiedAt(Date.from(atStartOfDayResult37.atZone(ZoneId.of("UTC")).toInstant()));
        address3.setModifiedBy(1L);
        address3.setRackRenters(rackRenters2);
        address3.setRenters(renters4);

        ArrayList<Address> addressList = new ArrayList<>();
        addressList.add(address3);
        when(addressRepository.findById((Long) any())).thenThrow(new BusinessException("id"));
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(addressList);
        GroupAllResponse actualListGroupResult = groupServiceImpl.listGroup("Oxford");
        assertEquals(contractsList, actualListGroupResult.getListGroupContracted());
        assertEquals(contractsList, actualListGroupResult.getListGroupNonContracted());
        verify(groupRepository, atLeast(1)).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups, atLeast(1)).getIsDisable();
        verify(roomGroups).getId();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
        verify(addressRepository, atLeast(1)).findAllByAddressCityEqualsIgnoreCase((String) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#listGroup(String)}
     */
    @Test
    void testListGroup8() {
        when(roomsRepository.findAllByGroupIdAndIsDisableIsFalse((Long) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllFloorByGroupId((Long) any())).thenReturn(new ArrayList<>());
        RoomGroups roomGroups = mock(RoomGroups.class);
        when(roomGroups.getIsDisable()).thenReturn(true);
        when(roomGroups.getId()).thenReturn(123L);
        when(roomGroups.getAddress()).thenReturn(1L);
        when(roomGroups.getGroupDescription()).thenReturn("Group Description");
        when(roomGroups.getGroupName()).thenReturn("Group Name");
        doNothing().when(roomGroups).setCreatedAt((Date) any());
        doNothing().when(roomGroups).setCreatedBy((Long) any());
        doNothing().when(roomGroups).setId((Long) any());
        doNothing().when(roomGroups).setModifiedAt((Date) any());
        doNothing().when(roomGroups).setModifiedBy((Long) any());
        doNothing().when(roomGroups).setAddress((Long) any());
        doNothing().when(roomGroups).setGroupDescription((String) any());
        doNothing().when(roomGroups).setGroupName((String) any());
        doNothing().when(roomGroups).setIsDisable((Boolean) any());
        roomGroups.setAddress(1L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setCreatedBy(1L);
        roomGroups.setGroupDescription("id");
        roomGroups.setGroupName("id");
        roomGroups.setId(123L);
        roomGroups.setIsDisable(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups.setModifiedBy(1L);

        ArrayList<RoomGroups> roomGroupsList = new ArrayList<>();
        roomGroupsList.add(roomGroups);
        when(groupRepository.findAll((Specification<RoomGroups>) any(), (Sort) any())).thenReturn(roomGroupsList);
        when(contractRepository.findAllByGroupIdAndContractType((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        when(addressRepository.findById((Long) any())).thenThrow(new BusinessException("id"));
        ArrayList<Address> addressList = new ArrayList<>();
        when(addressRepository.findAllByAddressCityEqualsIgnoreCase((String) any())).thenReturn(addressList);
        GroupAllResponse actualListGroupResult = groupServiceImpl.listGroup("");
        assertEquals(addressList, actualListGroupResult.getListGroupContracted());
        assertEquals(addressList, actualListGroupResult.getListGroupNonContracted());
        verify(groupRepository, atLeast(1)).findAll((Specification<RoomGroups>) any(), (Sort) any());
        verify(roomGroups, atLeast(1)).getIsDisable();
        verify(roomGroups).getId();
        verify(roomGroups).setCreatedAt((Date) any());
        verify(roomGroups).setCreatedBy((Long) any());
        verify(roomGroups).setId((Long) any());
        verify(roomGroups).setModifiedAt((Date) any());
        verify(roomGroups).setModifiedBy((Long) any());
        verify(roomGroups).setAddress((Long) any());
        verify(roomGroups).setGroupDescription((String) any());
        verify(roomGroups).setGroupName((String) any());
        verify(roomGroups).setIsDisable((Boolean) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#delete(Long, Long)}
     */
    @Test
    void testDelete() {
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        when(servicesService.removeGroupGeneralService((Long) any())).thenReturn("Remove Group General Service");

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
        Optional<RoomGroups> ofResult = Optional.of(roomGroups);

        RoomGroups roomGroups1 = new RoomGroups();
        roomGroups1.setAddress(1L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups1.setCreatedBy(1L);
        roomGroups1.setGroupDescription("Group Description");
        roomGroups1.setGroupName("Group Name");
        roomGroups1.setId(123L);
        roomGroups1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups1.setModifiedBy(1L);
        when(groupRepository.save((RoomGroups) any())).thenReturn(roomGroups1);
        when(groupRepository.findById((Long) any())).thenReturn(ofResult);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        assertEquals("Xóa thành công", groupServiceImpl.delete(123L, 1L));
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
        verify(servicesService).removeGroupGeneralService((Long) any());
        verify(groupRepository).save((RoomGroups) any());
        verify(groupRepository).findById((Long) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#delete(Long, Long)}
     */
    @Test
    void testDelete2() {
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        when(servicesService.removeGroupGeneralService((Long) any())).thenReturn("Remove Group General Service");

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
        Optional<RoomGroups> ofResult = Optional.of(roomGroups);

        RoomGroups roomGroups1 = new RoomGroups();
        roomGroups1.setAddress(1L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups1.setCreatedBy(1L);
        roomGroups1.setGroupDescription("Group Description");
        roomGroups1.setGroupName("Group Name");
        roomGroups1.setId(123L);
        roomGroups1.setIsDisable(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups1.setModifiedBy(1L);
        when(groupRepository.save((RoomGroups) any())).thenReturn(roomGroups1);
        when(groupRepository.findById((Long) any())).thenReturn(ofResult);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenThrow(new BusinessException("Xóa thành công"));
        assertThrows(BusinessException.class, () -> groupServiceImpl.delete(123L, 1L));
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#delete(Long, Long)}
     */
    @Test
    void testDelete3() {
        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(new RoomsResponse());

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
        when(roomService.removeRoom((Long) any(), (Long) any())).thenReturn(rooms);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        when(servicesService.removeGroupGeneralService((Long) any())).thenReturn("Remove Group General Service");

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
        Optional<RoomGroups> ofResult = Optional.of(roomGroups);

        RoomGroups roomGroups1 = new RoomGroups();
        roomGroups1.setAddress(1L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups1.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups1.setCreatedBy(1L);
        roomGroups1.setGroupDescription("Group Description");
        roomGroups1.setGroupName("Group Name");
        roomGroups1.setId(123L);
        roomGroups1.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups1.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups1.setModifiedBy(1L);
        when(groupRepository.save((RoomGroups) any())).thenReturn(roomGroups1);
        when(groupRepository.findById((Long) any())).thenReturn(ofResult);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        assertEquals("Xóa thành công", groupServiceImpl.delete(123L, 1L));
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
        verify(roomService).removeRoom((Long) any(), (Long) any());
        verify(servicesService).removeGroupGeneralService((Long) any());
        verify(groupRepository).save((RoomGroups) any());
        verify(groupRepository).findById((Long) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#delete(Long, Long)}
     */
    @Test
    void testDelete4() {
        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(new RoomsResponse());
        roomsResponseList.add(new RoomsResponse());

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
        when(roomService.removeRoom((Long) any(), (Long) any())).thenReturn(rooms);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        when(servicesService.removeGroupGeneralService((Long) any())).thenReturn("Remove Group General Service");

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
        Optional<RoomGroups> ofResult = Optional.of(roomGroups);

        RoomGroups roomGroups1 = new RoomGroups();
        roomGroups1.setAddress(1L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups1.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups1.setCreatedBy(1L);
        roomGroups1.setGroupDescription("Group Description");
        roomGroups1.setGroupName("Group Name");
        roomGroups1.setId(123L);
        roomGroups1.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups1.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups1.setModifiedBy(1L);
        when(groupRepository.save((RoomGroups) any())).thenReturn(roomGroups1);
        when(groupRepository.findById((Long) any())).thenReturn(ofResult);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        assertEquals("Xóa thành công", groupServiceImpl.delete(123L, 1L));
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
        verify(roomService, atLeast(1)).removeRoom((Long) any(), (Long) any());
        verify(servicesService).removeGroupGeneralService((Long) any());
        verify(groupRepository).save((RoomGroups) any());
        verify(groupRepository).findById((Long) any());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link GroupServiceImpl#delete(Long, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDelete5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.responses.RoomsResponse.getRoomId()" because "e" is null
        //       at vn.com.fpt.service.group.GroupServiceImpl.lambda$delete$0(GroupServiceImpl.java:158)
        //       at java.util.ArrayList.forEach(ArrayList.java:1511)
        //       at vn.com.fpt.service.group.GroupServiceImpl.delete(GroupServiceImpl.java:158)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(null);

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
        when(roomService.removeRoom((Long) any(), (Long) any())).thenReturn(rooms);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        when(servicesService.removeGroupGeneralService((Long) any())).thenReturn("Remove Group General Service");

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
        Optional<RoomGroups> ofResult = Optional.of(roomGroups);

        RoomGroups roomGroups1 = new RoomGroups();
        roomGroups1.setAddress(1L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups1.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups1.setCreatedBy(1L);
        roomGroups1.setGroupDescription("Group Description");
        roomGroups1.setGroupName("Group Name");
        roomGroups1.setId(123L);
        roomGroups1.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        roomGroups1.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        roomGroups1.setModifiedBy(1L);
        when(groupRepository.save((RoomGroups) any())).thenReturn(roomGroups1);
        when(groupRepository.findById((Long) any())).thenReturn(ofResult);
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(new ArrayList<>());
        groupServiceImpl.delete(123L, 1L);
    }

    /**
     * Method under test: {@link GroupServiceImpl#add(AddGroupRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAdd() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.entity.RoomGroups.getId()" because "group" is null
        //       at vn.com.fpt.service.group.GroupServiceImpl.add(GroupServiceImpl.java:195)
        //   See https://diff.blue/R013 to resolve this issue.

        RoomsRepository roomsRepository = mock(RoomsRepository.class);
        RoomsRepository roomsRepository1 = mock(RoomsRepository.class);
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
        AssetServiceImpl assetService2 = new AssetServiceImpl(entityManager1, assetTypesRepository1, basicAssetRepository1,
                new ContractServiceImpl(mock(ContractRepository.class), null, null, null, null, mock(AddressRepository.class),
                        null, mock(RenterRepository.class), mock(RackRenterRepository.class), mock(MoneySourceRepository.class),
                        null, mock(RoomBillRepository.class), mock(RoomsRepository.class)),
                mock(RoomAssetRepository.class));

        RoomsRepository roomsRepository2 = mock(RoomsRepository.class);
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
        RoomServiceImpl roomService1 = new RoomServiceImpl(roomsRepository2, assetService3, contractService, service,
                servicesService1, mock(RackRenterRepository.class), contractRepo,
                new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class)));

        RenterRepository renterRepo = mock(RenterRepository.class);
        RenterServiceImpl renterService1 = new RenterServiceImpl(renterRepo,
                new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null, mock(RackRenterRepository.class),
                        mock(ContractRepository.class), null),
                mock(RackRenterRepository.class));

        ServicesServiceImpl servicesService2 = new ServicesServiceImpl(new SessionDelegatorBaseImpl(null, null),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        AddressRepository addressRepository1 = mock(AddressRepository.class);
        RoomsRepository roomsRepository3 = mock(RoomsRepository.class);
        RoomServiceImpl roomService2 = new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null);

        ServicesServiceImpl servicesService3 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        GroupRepository groupRepository = mock(GroupRepository.class);
        ContractRepository contractRepository2 = mock(ContractRepository.class);
        GroupServiceImpl groupService1 = new GroupServiceImpl(roomsRepository3, roomService2, servicesService3,
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

        RoomsRepository roomsRepository4 = mock(RoomsRepository.class);
        RoomsRepository roomsRepository5 = mock(RoomsRepository.class);
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
        RoomServiceImpl roomService3 = new RoomServiceImpl(roomsRepository5, assetService4, contractService2, service1,
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
        GroupServiceImpl service2 = new GroupServiceImpl(roomsRepository4, roomService3, servicesService5, groupRepository1,
                contractRepository3,
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
        RenterRepository renterRepo1 = mock(RenterRepository.class);
        RoomsRepository roomsRepository6 = mock(RoomsRepository.class);
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
        RoomServiceImpl roomService4 = new RoomServiceImpl(roomsRepository1, assetService1, contractService1, service2,
                servicesService6, rackRenters2, contractRepo2,
                new RenterServiceImpl(renterRepo1,
                        new RoomServiceImpl(roomsRepository6, assetService5, contractService3, service3, servicesService7,
                                mock(RackRenterRepository.class), contractRepo3,
                                new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class))),
                        mock(RackRenterRepository.class)));

        SessionDelegatorBaseImpl delegate2 = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate3 = new SessionDelegatorBaseImpl(delegate2,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate4 = new SessionDelegatorBaseImpl(null, null);

        ServicesServiceImpl servicesService8 = new ServicesServiceImpl(
                new SessionDelegatorBaseImpl(delegate3,
                        new SessionDelegatorBaseImpl(delegate4, new SessionDelegatorBaseImpl(null, null))),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        GroupRepository groupRepository2 = mock(GroupRepository.class);
        ContractRepository contractRepository4 = mock(ContractRepository.class);
        SessionDelegatorBaseImpl delegate5 = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate6 = new SessionDelegatorBaseImpl(delegate5,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate7 = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl entityManager3 = new SessionDelegatorBaseImpl(delegate6,
                new SessionDelegatorBaseImpl(delegate7, new SessionDelegatorBaseImpl(null, null)));

        AssetTypesRepository assetTypesRepository3 = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository3 = mock(BasicAssetRepository.class);
        ContractRepository contractRepository5 = mock(ContractRepository.class);
        SessionDelegatorBaseImpl entityManager4 = new SessionDelegatorBaseImpl(null, null);

        AssetTypesRepository assetTypesRepository4 = mock(AssetTypesRepository.class);
        BasicAssetRepository basicAssetRepository4 = mock(BasicAssetRepository.class);
        AssetServiceImpl assetService6 = new AssetServiceImpl(entityManager4, assetTypesRepository4, basicAssetRepository4,
                new ContractServiceImpl(mock(ContractRepository.class), null, null, null, null, mock(AddressRepository.class),
                        null, mock(RenterRepository.class), mock(RackRenterRepository.class), mock(MoneySourceRepository.class),
                        null, mock(RoomBillRepository.class), mock(RoomsRepository.class)),
                mock(RoomAssetRepository.class));

        RoomsRepository roomsRepository7 = mock(RoomsRepository.class);
        AssetServiceImpl assetService7 = new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class));

        ContractServiceImpl contractService4 = new ContractServiceImpl(mock(ContractRepository.class), null, null, null,
                null, mock(AddressRepository.class), null, mock(RenterRepository.class), mock(RackRenterRepository.class),
                mock(MoneySourceRepository.class), null, mock(RoomBillRepository.class), mock(RoomsRepository.class));

        GroupServiceImpl service4 = new GroupServiceImpl(mock(RoomsRepository.class), null, null,
                mock(GroupRepository.class), mock(ContractRepository.class), null, mock(RackRenterRepository.class),
                mock(AddressRepository.class));

        ServicesServiceImpl servicesService9 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        ContractRepository contractRepo4 = mock(ContractRepository.class);
        RoomServiceImpl roomService5 = new RoomServiceImpl(roomsRepository7, assetService7, contractService4, service4,
                servicesService9, mock(RackRenterRepository.class), contractRepo4,
                new RenterServiceImpl(mock(RenterRepository.class), null, mock(RackRenterRepository.class)));

        RenterRepository renterRepo2 = mock(RenterRepository.class);
        RenterServiceImpl renterService2 = new RenterServiceImpl(renterRepo2,
                new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null, mock(RackRenterRepository.class),
                        mock(ContractRepository.class), null),
                mock(RackRenterRepository.class));

        ServicesServiceImpl servicesService10 = new ServicesServiceImpl(new SessionDelegatorBaseImpl(null, null),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        AddressRepository addressRepository2 = mock(AddressRepository.class);
        RoomsRepository roomsRepository8 = mock(RoomsRepository.class);
        RoomServiceImpl roomService6 = new RoomServiceImpl(mock(RoomsRepository.class), null, null, null, null,
                mock(RackRenterRepository.class), mock(ContractRepository.class), null);

        ServicesServiceImpl servicesService11 = new ServicesServiceImpl(null, mock(BasicServicesRepository.class),
                mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class));

        GroupRepository groupRepository3 = mock(GroupRepository.class);
        ContractRepository contractRepository6 = mock(ContractRepository.class);
        GroupServiceImpl groupService2 = new GroupServiceImpl(roomsRepository8, roomService6, servicesService11,
                groupRepository3, contractRepository6, new AssetServiceImpl(null, mock(AssetTypesRepository.class),
                mock(BasicAssetRepository.class), null, mock(RoomAssetRepository.class)),
                mock(RackRenterRepository.class), mock(AddressRepository.class));

        RenterRepository renterRepository2 = mock(RenterRepository.class);
        RackRenterRepository rackRenters3 = mock(RackRenterRepository.class);
        MoneySourceRepository moneySourceRepository2 = mock(MoneySourceRepository.class);
        TableChangeLogRepository billChangeLogRepo2 = mock(TableChangeLogRepository.class);
        GroupServiceImpl groupServiceImpl = new GroupServiceImpl(roomsRepository, roomService4, servicesService8,
                groupRepository2, contractRepository4,
                new AssetServiceImpl(entityManager3, assetTypesRepository3, basicAssetRepository3,
                        new ContractServiceImpl(contractRepository5, assetService6, roomService5, renterService2, servicesService10,
                                addressRepository2, groupService2, renterRepository2, rackRenters3, moneySourceRepository2,
                                new TableLogComponent(billChangeLogRepo2, new ObjectMapper()), mock(RoomBillRepository.class),
                                mock(RoomsRepository.class)),
                        mock(RoomAssetRepository.class)),
                mock(RackRenterRepository.class), mock(AddressRepository.class));
        groupServiceImpl.add(new AddGroupRequest(), 1L);
    }

    /**
     * Method under test: {@link GroupServiceImpl#update(Long, UpdateGroupRequest, Long)}
     */
    @Test
    void testUpdate() {
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
        Optional<RoomGroups> ofResult = Optional.of(roomGroups);
        when(groupRepository.findById((Long) any())).thenReturn(ofResult);
        when(addressRepository.findById((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> groupServiceImpl.update(123L, new UpdateGroupRequest(), 1L));
        verify(groupRepository).findById((Long) any());
        verify(addressRepository).findById((Long) any());
    }
}//package vn.com.fpt.service.group;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import vn.com.fpt.common.BusinessException;
//import vn.com.fpt.entity.Address;
//import vn.com.fpt.entity.Contracts;
//import vn.com.fpt.entity.RoomGroups;
//import vn.com.fpt.entity.Rooms;
//import vn.com.fpt.model.GeneralServiceDTO;
//import vn.com.fpt.model.HandOverAssetsDTO;
//import vn.com.fpt.repositories.AddressRepository;
//import vn.com.fpt.repositories.ContractRepository;
//import vn.com.fpt.repositories.GroupRepository;
//import vn.com.fpt.repositories.RoomsRepository;
//import vn.com.fpt.responses.GroupAllResponse;
//import vn.com.fpt.service.assets.AssetService;
//import vn.com.fpt.service.services.ServicesService;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.when;
//import static vn.com.fpt.common.constants.ErrorStatusConstants.USER_NOT_FOUND;
//
//
//@ExtendWith(MockitoExtension.class)
//class GroupServiceImplTest {
//
//    @InjectMocks //test cái gì  ,cái class nào cần test
//    private GroupServiceImpl groupService;
//
//    @Mock
//    private ContractRepository contractRepository;
//
//    @Mock
//    private GroupRepository groupRepository;
//
//    @Mock
//    private RoomsRepository roomsRepository;
//
//    @Mock
//    private AddressRepository addressRepository;
//
//    @Mock
//    private ServicesService servicesService;
//
//    @Mock
//    private AssetService assetService;
//
//    @Test
//    void GivenExactValue_Then_group_ResultGroupResponseExact() {
//        //input
//        Long groupId = 1L;
//
//        //mock contractRepository.findByGroupIdAndContractType
//        Contracts contract = new Contracts();
//        contract.setId(1L);
//        contract.setContractName("demo123");
//        when(contractRepository.findByGroupIdAndContractType(anyLong(), anyInt())).thenReturn(contract);
//
//        //mock groupRepository.findById
//        RoomGroups roomGroups = new RoomGroups();
//        roomGroups.setAddress(1L);
//        roomGroups.setId(1L);
//        roomGroups.setGroupName("group-name");
//        Optional<RoomGroups> optionalRoomGroups = Optional.of(roomGroups);
//        when(groupRepository.findById(contract.getGroupId())).thenReturn(optionalRoomGroups);
//
//        //mock roomsRepository.findAllByGroupId
//        List<Rooms> listRoom = new ArrayList<>();
//        when(roomsRepository.findAllByGroupId(anyLong())).thenReturn(listRoom);
//
//        //mock addressRepository.findById
//        Address address = new Address();
//        Optional<Address> optionalAddress = Optional.of(address);
//        when(addressRepository.findById(anyLong())).thenReturn(optionalAddress);
//
//        // mock servicesService.listGeneralService
//        List<GeneralServiceDTO> listGeneralService = new ArrayList<>();
//        GeneralServiceDTO generalServiceDTO = new GeneralServiceDTO();
//        listGeneralService.add(generalServiceDTO);
//        when(servicesService.listGeneralService(anyLong())).thenReturn(listGeneralService);
//
//        // mock  assetService.listHandOverAsset
//        List<HandOverAssetsDTO> listHandOverAsset = new ArrayList<>();
//        HandOverAssetsDTO handOverAsset = new HandOverAssetsDTO();
//        listHandOverAsset.add(handOverAsset);
//        when(assetService.listHandOverAsset(anyLong())).thenReturn(listHandOverAsset);
//
//        //mock  roomsRepository.findAllFloorByGroupId
//        List<Integer> listInt = new ArrayList<>();
//        listInt.add(1);
//        when(roomsRepository.findAllFloorByGroupId(anyLong())).thenReturn(listInt);
//
//        //mock roomsRepository.findAllRoomsByGroupId
//        List<String> listString = new ArrayList<>();
//        listString.add("abc");
//        when(roomsRepository.findAllRoomsByGroupId(anyLong())).thenReturn(listString);
//
//        //output mock
////        GroupAllResponse groupAllResponse = new GroupAllResponse();
////        groupAllResponse.setGroupId(1L);
////        groupAllResponse.setGroupName("group-name");
////
////        //result
////        GroupAllResponse result = groupService.group(groupId);
////
////        assertEquals(result.getGroupId(), groupAllResponse.getGroupId());
////        assertEquals(result.getGroupName(), groupAllResponse.getGroupName());
//    }
//
//    @Test
//    void GivenGroupIdWrongValue_Then_group_ContractRepositoryThrowBusinessException() {
//        //input
//        Long groupId = 1L;
//
//        //mock function accountRepository.findById
//        when(contractRepository.findByGroupIdAndContractType(anyLong(), anyInt())).thenThrow(new BusinessException(USER_NOT_FOUND, "Không tìm thấy contract"));
//
//        //result
//        String messageError = "Không tìm thấy contract";
//
//        BusinessException thrown = assertThrows(
//                BusinessException.class,
//                () -> groupService.group(groupId)
//        );
//        assertEquals(thrown.getMessage(), messageError);
//        assertEquals(thrown.getErrorStatus(), USER_NOT_FOUND);
//    }
//
//    @Test
//    void GivenGroupIdWrongValue_Then_group_GroupRepositoryThrowBusinessException() {
//        //input
//        Long groupId = 1L;
//
//        //mock contractRepository.findByGroupIdAndContractType
//        Contracts contract = new Contracts();
//        contract.setId(1L);
//        contract.setGroupId(1L);
//        contract.setContractName("demo123");
//        when(contractRepository.findByGroupIdAndContractType(anyLong(), anyInt())).thenReturn(contract);
//
//        //mock function accountRepository.findById
//        when(groupRepository.findById(anyLong())).thenThrow(new BusinessException(USER_NOT_FOUND, "Không tìm thấy group"));
//
//        //result
//        String messageError = "Không tìm thấy group";
//
//        BusinessException thrown = assertThrows(
//                BusinessException.class,
//                () -> groupService.group(groupId)
//        );
//        assertEquals(thrown.getMessage(), messageError);
//        assertEquals(thrown.getErrorStatus(), USER_NOT_FOUND);
//    }
//
//    @Test
//    void GivenExactValue_Then_list_ResultListGroupResponseExact() {
//        //mock groupRepository.findAll()
//        List<RoomGroups> listGroups = new ArrayList<RoomGroups>();
//        RoomGroups roomGroups = new RoomGroups();
//        roomGroups.setId(1L);
//        roomGroups.setAddress(1L);
//        roomGroups.setGroupName("group-name");
//        listGroups.add(roomGroups);
//        when(groupRepository.findAll()).thenReturn(listGroups);
//
//        //mock contractRepository.findByGroupIdAndContractType
//        Contracts contract = new Contracts();
//        contract.setId(1L);
//        contract.setGroupId(1L);
//        contract.setContractName("demo123");
//        when(contractRepository.findByGroupIdAndContractType(anyLong(), anyInt())).thenReturn(contract);
//
//        //mock addressRepository.findById
//        Address address = new Address();
//        Optional<Address> optionalAddress = Optional.of(address);
//        when(addressRepository.findById(anyLong())).thenReturn(optionalAddress);
//
//        //mock roomsRepository.findAllByGroupId
//        List<Rooms> listRoom = new ArrayList<>();
//        when(roomsRepository.findAllByGroupId(anyLong())).thenReturn(listRoom);
//
//        // mock servicesService.listGeneralService
//        List<GeneralServiceDTO> listGeneralService = new ArrayList<>();
//        GeneralServiceDTO generalServiceDTO = new GeneralServiceDTO();
//        listGeneralService.add(generalServiceDTO);
//        when(servicesService.listGeneralService(anyLong())).thenReturn(listGeneralService);
//
//        // mock  assetService.listHandOverAsset
//        List<HandOverAssetsDTO> listHandOverAsset = new ArrayList<>();
//        HandOverAssetsDTO handOverAsset = new HandOverAssetsDTO();
//        listHandOverAsset.add(handOverAsset);
//        when(assetService.listHandOverAsset(anyLong())).thenReturn(listHandOverAsset);
//
//        //mock  roomsRepository.findAllFloorByGroupId
//        List<Integer> listInt = new ArrayList<>();
//        listInt.add(1);
//        when(roomsRepository.findAllFloorByGroupId(anyLong())).thenReturn(listInt);
//
//        //mock roomsRepository.findAllRoomsByGroupId
//        List<String> listString = new ArrayList<>();
//        listString.add("abc");
//        when(roomsRepository.findAllRoomsByGroupId(anyLong())).thenReturn(listString);
//
//        //output mock
//        List<GroupAllResponse> groupAllResponseList = new ArrayList<>();
//        GroupAllResponse groupAllResponse = new GroupAllResponse();
//        groupAllResponse.setGroupId(1L);
//        groupAllResponse.setGroupName("group-name");
//        groupAllResponseList.add(groupAllResponse);
//
//        //result
//        List<GroupAllResponse> result = groupService.list();
//
//        assertEquals(result.size(), groupAllResponseList.size());
//        assertEquals(result.get(0).getGroupName(), groupAllResponseList.get(0).getGroupName());
//        assertEquals(result.get(0).getGroupId(), groupAllResponseList.get(0).getGroupId());
//
//    }
//
//    @Test
//    void GivenWrongValue_Then_list_GroupRepositoryReturnEmpty() {
//        //mock groupRepository.findAll()
//        when(groupRepository.findAll()).thenReturn(new ArrayList<>());
//
//        //result
//        List<GroupAllResponse> result = groupService.list();
//
//        assertEquals(result.size(), 0);
//    }
//
//    @Test
//    void GivenWrongValue_Then_list_GroupRepositoryThrowBusinessException() {
//        //mock groupRepository.findAll()
//        when(groupRepository.findAll()).thenThrow(new BusinessException(USER_NOT_FOUND, "Không tìm thấy group"));
//
//        //result
//        String messageError = "Không tìm thấy group";
//
//        BusinessException thrown = assertThrows(
//                BusinessException.class,
//                () -> groupService.list()
//        );
//        assertEquals(thrown.getMessage(), messageError);
//        assertEquals(thrown.getErrorStatus(), USER_NOT_FOUND);
//    }
//
//    @Test
//    void GivenGroupIdWrongValue_Then_list_ContractRepositoryThrowBusinessException() {
//        //mock groupRepository.findAll()
//        List<RoomGroups> listGroups = new ArrayList<RoomGroups>();
//        RoomGroups roomGroups = new RoomGroups();
//        roomGroups.setId(1L);
//        roomGroups.setAddress(1L);
//        roomGroups.setGroupName("group-name");
//        listGroups.add(roomGroups);
//        when(groupRepository.findAll()).thenReturn(listGroups);
//
//        //mock function accountRepository.findById
//        when(contractRepository.findByGroupIdAndContractType(anyLong(), anyInt())).thenThrow(new BusinessException(USER_NOT_FOUND, "Không tìm thấy contract"));
//
//        //result
//        String messageError = "Không tìm thấy contract";
//
//        BusinessException thrown = assertThrows(
//                BusinessException.class,
//                () -> groupService.list()
//        );
//        assertEquals(thrown.getMessage(), messageError);
//        assertEquals(thrown.getErrorStatus(), USER_NOT_FOUND);
//    }
//}