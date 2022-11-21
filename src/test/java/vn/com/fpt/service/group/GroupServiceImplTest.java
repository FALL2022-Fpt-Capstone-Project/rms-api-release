//package vn.com.fpt.service.group;
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