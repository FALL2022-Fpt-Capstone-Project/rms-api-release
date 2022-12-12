package vn.com.fpt.service.bill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.com.fpt.entity.BasicServices;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.entity.MoneySource;
import vn.com.fpt.entity.RecurringBill;
import vn.com.fpt.entity.RoomGroups;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.model.GeneralServiceDTO;
import vn.com.fpt.model.HandOverGeneralServiceDTO;
import vn.com.fpt.model.RoomAssetDTO;
import vn.com.fpt.model.RoomContractDTO;
import vn.com.fpt.repositories.ContractRepository;
import vn.com.fpt.repositories.MoneySourceRepository;
import vn.com.fpt.repositories.RecurringBillRepository;
import vn.com.fpt.repositories.RoomBillRepository;
import vn.com.fpt.repositories.ServiceBillRepository;
import vn.com.fpt.requests.AddBillRequest;
import vn.com.fpt.requests.PreviewAddBillRequest;
import vn.com.fpt.responses.PayBillInformationResponse;
import vn.com.fpt.responses.RentersResponse;
import vn.com.fpt.responses.RoomsResponse;
import vn.com.fpt.service.TableLogComponent;
import vn.com.fpt.service.UpdateLog;
import vn.com.fpt.service.contract.ContractService;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.renter.RenterService;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.services.ServicesService;

@ContextConfiguration(classes = {BillServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BillServiceImplTest {
    @Autowired
    private BillServiceImpl billServiceImpl;

    @MockBean
    private ContractRepository contractRepository;

    @MockBean
    private ContractService contractService;

    @MockBean
    private GroupService groupService;

    @MockBean
    private MoneySourceRepository moneySourceRepository;

    @MockBean
    private RecurringBillRepository recurringBillRepository;

    @MockBean
    private RenterService renterService;

    @MockBean
    private RoomBillRepository roomBillRepository;

    @MockBean
    private RoomService roomService;

    @MockBean
    private ServiceBillRepository serviceBillRepository;

    @MockBean
    private ServicesService servicesService;

    @MockBean
    private TableLogComponent tableLogComponent;

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    void testListBillRoomStatus() {
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        assertTrue(billServiceImpl.listBillRoomStatus(123L, 1).isEmpty());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    void testListBillRoomStatus2() {
        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(new RoomsResponse());
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        assertTrue(billServiceImpl.listBillRoomStatus(123L, 1).isEmpty());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    void testListBillRoomStatus3() {
        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(new RoomsResponse());
        roomsResponseList.add(new RoomsResponse());
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        assertTrue(billServiceImpl.listBillRoomStatus(123L, 1).isEmpty());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListBillRoomStatus4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.responses.RoomsResponse.getContractId()" because "e" is null
        //       at vn.com.fpt.service.bill.BillServiceImpl.lambda$listBillRoomStatus$0(BillServiceImpl.java:62)
        //       at java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:575)
        //       at java.util.stream.AbstractPipeline.evaluateToArrayNode(AbstractPipeline.java:260)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:616)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:622)
        //       at java.util.stream.ReferencePipeline.toList(ReferencePipeline.java:627)
        //       at vn.com.fpt.service.bill.BillServiceImpl.listBillRoomStatus(BillServiceImpl.java:62)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(null);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        billServiceImpl.listBillRoomStatus(123L, 1);
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    void testListBillRoomStatus5() {
        when(contractService.listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any()))
                .thenReturn(new ArrayList<>());

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(
                new RoomsResponse(123L, "Room Name", 1, 1, 1, 1, 123L, 123L, 123L, 10.0d, 10.0d, true, new ArrayList<>()));
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        assertTrue(billServiceImpl.listBillRoomStatus(123L, 1).isEmpty());
        verify(contractService).listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListBillRoomStatus6() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Integer.equals(Object)" because the return value of "vn.com.fpt.model.RoomContractDTO.getContractPaymentCycle()" is null
        //       at vn.com.fpt.service.bill.BillServiceImpl.lambda$listBillRoomStatus$1(BillServiceImpl.java:81)
        //       at java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:575)
        //       at java.util.stream.AbstractPipeline.evaluateToArrayNode(AbstractPipeline.java:260)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:616)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:622)
        //       at java.util.stream.ReferencePipeline.toList(ReferencePipeline.java:627)
        //       at vn.com.fpt.service.bill.BillServiceImpl.listBillRoomStatus(BillServiceImpl.java:81)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<RoomContractDTO> roomContractDTOList = new ArrayList<>();
        roomContractDTOList.add(new RoomContractDTO());
        when(contractService.listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any()))
                .thenReturn(roomContractDTOList);

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(
                new RoomsResponse(123L, "Room Name", 1, 1, 1, 1, 123L, 123L, 123L, 10.0d, 10.0d, true, new ArrayList<>()));
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        billServiceImpl.listBillRoomStatus(123L, 1);
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    void testListBillRoomStatus7() {
        when(contractService.listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any()))
                .thenReturn(new ArrayList<>());

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(
                new RoomsResponse(123L, "Room Name", 1, 1, 1, 1, 123L, 123L, 123L, 10.0d, 10.0d, true, new ArrayList<>()));
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        assertTrue(billServiceImpl.listBillRoomStatus(123L, 0).isEmpty());
        verify(contractService).listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListBillRoomStatus8() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Integer.equals(Object)" because "paymentCircle" is null
        //       at vn.com.fpt.service.bill.BillServiceImpl.listBillRoomStatus(BillServiceImpl.java:78)
        //   See https://diff.blue/R013 to resolve this issue.

        when(contractService.listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any()))
                .thenReturn(new ArrayList<>());

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(
                new RoomsResponse(123L, "Room Name", 1, 1, 1, 1, 123L, 123L, 123L, 10.0d, 10.0d, true, new ArrayList<>()));
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        billServiceImpl.listBillRoomStatus(123L, null);
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    void testListBillRoomStatus9() {
        ArrayList<RoomContractDTO> roomContractDTOList = new ArrayList<>();
        Rooms room = new Rooms();
        ArrayList<RentersResponse> listRenter = new ArrayList<>();
        ArrayList<RoomAssetDTO> listRoomAsset = new ArrayList<>();
        ArrayList<HandOverGeneralServiceDTO> listHandOverGeneralService = new ArrayList<>();
        roomContractDTOList.add(new RoomContractDTO(123L, "Contract Name", "Group Name", room, 10.0d, 10.0d, 1, 1,
                "2020-03-01", "2020-03-01", "Note", 1, true, 1L, 123L, "Room Name", 123L, 1, listRenter, listRoomAsset,
                listHandOverGeneralService, new ArrayList<>()));
        when(contractService.listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any()))
                .thenReturn(roomContractDTOList);

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(
                new RoomsResponse(123L, "Room Name", 1, 1, 1, 1, 123L, 123L, 123L, 10.0d, 10.0d, true, new ArrayList<>()));

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
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        when(recurringBillRepository.findAllByRoomId((Long) any())).thenReturn(new ArrayList<>());
        assertEquals(1, billServiceImpl.listBillRoomStatus(123L, 1).size());
        verify(contractService).listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
        verify(roomService).room((Long) any());
        verify(servicesService).listGeneralServiceByGroupId((Long) any());
        verify(renterService).listRenter((Long) any());
        verify(recurringBillRepository).findAllByRoomId((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    void testListBillRoomStatus10() {
        ArrayList<RoomContractDTO> roomContractDTOList = new ArrayList<>();
        Rooms room = new Rooms();
        ArrayList<RentersResponse> listRenter = new ArrayList<>();
        ArrayList<RoomAssetDTO> listRoomAsset = new ArrayList<>();
        ArrayList<HandOverGeneralServiceDTO> listHandOverGeneralService = new ArrayList<>();
        roomContractDTOList.add(new RoomContractDTO(123L, "Contract Name", "Group Name", room, 10.0d, 10.0d, 5, 1,
                "2020-03-01", "2020-03-01", "Note", 1, true, 1L, 123L, "Room Name", 123L, 1, listRenter, listRoomAsset,
                listHandOverGeneralService, new ArrayList<>()));
        when(contractService.listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any()))
                .thenReturn(roomContractDTOList);

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(
                new RoomsResponse(123L, "Room Name", 1, 1, 1, 1, 123L, 123L, 123L, 10.0d, 10.0d, true, new ArrayList<>()));

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
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        when(recurringBillRepository.findAllByRoomId((Long) any())).thenReturn(new ArrayList<>());
        assertEquals(1, billServiceImpl.listBillRoomStatus(123L, 1).size());
        verify(contractService).listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
        verify(roomService).room((Long) any());
        verify(servicesService).listGeneralServiceByGroupId((Long) any());
        verify(renterService).listRenter((Long) any());
        verify(recurringBillRepository).findAllByRoomId((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    void testListBillRoomStatus11() {
        ArrayList<RoomContractDTO> roomContractDTOList = new ArrayList<>();
        Rooms room = new Rooms();
        ArrayList<RentersResponse> listRenter = new ArrayList<>();
        ArrayList<RoomAssetDTO> listRoomAsset = new ArrayList<>();
        ArrayList<HandOverGeneralServiceDTO> listHandOverGeneralService = new ArrayList<>();
        roomContractDTOList.add(new RoomContractDTO(123L, "Contract Name", "Group Name", room, 10.0d, 10.0d, 0, 1,
                "2020-03-01", "2020-03-01", "Note", 1, true, 1L, 123L, "Room Name", 123L, 1, listRenter, listRoomAsset,
                listHandOverGeneralService, new ArrayList<>()));
        when(contractService.listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any()))
                .thenReturn(roomContractDTOList);

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(
                new RoomsResponse(123L, "Room Name", 1, 1, 1, 1, 123L, 123L, 123L, 10.0d, 10.0d, true, new ArrayList<>()));

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
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        when(recurringBillRepository.findAllByRoomId((Long) any())).thenReturn(new ArrayList<>());
        assertThrows(ArithmeticException.class, () -> billServiceImpl.listBillRoomStatus(123L, 1));
        verify(contractService).listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
        verify(roomService).room((Long) any());
        verify(servicesService).listGeneralServiceByGroupId((Long) any());
        verify(renterService).listRenter((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListBillRoomStatus12() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Integer.intValue()" because the return value of "vn.com.fpt.model.RoomContractDTO.getContractBillCycle()" is null
        //       at vn.com.fpt.service.bill.BillServiceImpl.listBillRoomStatus(BillServiceImpl.java:107)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<RoomContractDTO> roomContractDTOList = new ArrayList<>();
        Rooms room = new Rooms();
        ArrayList<RentersResponse> listRenter = new ArrayList<>();
        ArrayList<RoomAssetDTO> listRoomAsset = new ArrayList<>();
        ArrayList<HandOverGeneralServiceDTO> listHandOverGeneralService = new ArrayList<>();
        roomContractDTOList.add(new RoomContractDTO(123L, "Contract Name", "Group Name", room, 10.0d, 10.0d, null, 1,
                "2020-03-01", "2020-03-01", "Note", 1, true, 1L, 123L, "Room Name", 123L, 1, listRenter, listRoomAsset,
                listHandOverGeneralService, new ArrayList<>()));
        when(contractService.listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any()))
                .thenReturn(roomContractDTOList);

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(
                new RoomsResponse(123L, "Room Name", 1, 1, 1, 1, 123L, 123L, 123L, 10.0d, 10.0d, true, new ArrayList<>()));

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
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        when(recurringBillRepository.findAllByRoomId((Long) any())).thenReturn(new ArrayList<>());
        billServiceImpl.listBillRoomStatus(123L, 1);
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    void testListBillRoomStatus13() {
        ArrayList<RoomContractDTO> roomContractDTOList = new ArrayList<>();
        Rooms room = new Rooms();
        ArrayList<RentersResponse> listRenter = new ArrayList<>();
        ArrayList<RoomAssetDTO> listRoomAsset = new ArrayList<>();
        ArrayList<HandOverGeneralServiceDTO> listHandOverGeneralService = new ArrayList<>();
        roomContractDTOList.add(new RoomContractDTO(123L, "Contract Name", "Group Name", room, 10.0d, 10.0d, 1, 5,
                "2020-03-01", "2020-03-01", "Note", 1, true, 1L, 123L, "Room Name", 123L, 1, listRenter, listRoomAsset,
                listHandOverGeneralService, new ArrayList<>()));
        when(contractService.listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any()))
                .thenReturn(roomContractDTOList);

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(
                new RoomsResponse(123L, "Room Name", 1, 1, 1, 1, 123L, 123L, 123L, 10.0d, 10.0d, true, new ArrayList<>()));

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
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        when(recurringBillRepository.findAllByRoomId((Long) any())).thenReturn(new ArrayList<>());
        assertTrue(billServiceImpl.listBillRoomStatus(123L, 1).isEmpty());
        verify(contractService).listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListBillRoomStatus14() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Date.toInstant()" because "dateToConvert" is null
        //       at vn.com.fpt.common.utils.DateUtils.toLocalDate(DateUtils.java:70)
        //       at vn.com.fpt.common.utils.DateUtils.monthsBetween(DateUtils.java:76)
        //       at vn.com.fpt.service.bill.BillServiceImpl.listBillRoomStatus(BillServiceImpl.java:107)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<RoomContractDTO> roomContractDTOList = new ArrayList<>();
        Rooms room = new Rooms();
        ArrayList<RentersResponse> listRenter = new ArrayList<>();
        ArrayList<RoomAssetDTO> listRoomAsset = new ArrayList<>();
        ArrayList<HandOverGeneralServiceDTO> listHandOverGeneralService = new ArrayList<>();
        roomContractDTOList.add(new RoomContractDTO(123L, "Contract Name", "Group Name", room, 10.0d, 10.0d, 1, 1,
                "2020/03/01", "2020-03-01", "Note", 1, true, 1L, 123L, "Room Name", 123L, 1, listRenter, listRoomAsset,
                listHandOverGeneralService, new ArrayList<>()));
        when(contractService.listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any()))
                .thenReturn(roomContractDTOList);

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(
                new RoomsResponse(123L, "Room Name", 1, 1, 1, 1, 123L, 123L, 123L, 10.0d, 10.0d, true, new ArrayList<>()));

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
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        when(recurringBillRepository.findAllByRoomId((Long) any())).thenReturn(new ArrayList<>());
        billServiceImpl.listBillRoomStatus(123L, 1);
    }

    /**
     * Method under test: {@link BillServiceImpl#listBillRoomStatus(Long, Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListBillRoomStatus15() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.length()" because "text" is null
        //       at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1474)
        //       at java.text.DateFormat.parse(DateFormat.java:397)
        //       at vn.com.fpt.common.utils.DateUtils.parse(DateUtils.java:52)
        //       at vn.com.fpt.common.utils.DateUtils.parse(DateUtils.java:60)
        //       at vn.com.fpt.service.bill.BillServiceImpl.listBillRoomStatus(BillServiceImpl.java:107)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<RoomContractDTO> roomContractDTOList = new ArrayList<>();
        Rooms room = new Rooms();
        ArrayList<RentersResponse> listRenter = new ArrayList<>();
        ArrayList<RoomAssetDTO> listRoomAsset = new ArrayList<>();
        ArrayList<HandOverGeneralServiceDTO> listHandOverGeneralService = new ArrayList<>();
        roomContractDTOList.add(new RoomContractDTO(123L, "Contract Name", "Group Name", room, 10.0d, 10.0d, 1, 1, null,
                "2020-03-01", "Note", 1, true, 1L, 123L, "Room Name", 123L, 1, listRenter, listRoomAsset,
                listHandOverGeneralService, new ArrayList<>()));
        when(contractService.listRoomContract((Long) any(), (String) any(), (String) any(), (String) any(),
                (Boolean) any(), (String) any(), (String) any(), (Integer) any(), (Long) any(), (List<Long>) any()))
                .thenReturn(roomContractDTOList);

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(
                new RoomsResponse(123L, "Room Name", 1, 1, 1, 1, 123L, 123L, 123L, 10.0d, 10.0d, true, new ArrayList<>()));

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
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        when(recurringBillRepository.findAllByRoomId((Long) any())).thenReturn(new ArrayList<>());
        billServiceImpl.listBillRoomStatus(123L, 1);
    }

    /**
     * Method under test: {@link BillServiceImpl#addBill(List)}
     */
    @Test
    void testAddBill() {
        ArrayList<AddBillRequest> addBillRequestList = new ArrayList<>();
        List<AddBillRequest> actualAddBillResult = billServiceImpl.addBill(addBillRequestList);
        assertSame(addBillRequestList, actualAddBillResult);
        assertTrue(actualAddBillResult.isEmpty());
    }

    /**
     * Method under test: {@link BillServiceImpl#addBill(List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddBill2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Double.doubleValue()" because the return value of "vn.com.fpt.requests.AddBillRequest.getTotalRoomMoney()" is null
        //       at vn.com.fpt.service.bill.BillServiceImpl.addBill(BillServiceImpl.java:145)
        //   See https://diff.blue/R013 to resolve this issue.

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
        when(contractService.contract((Long) any())).thenReturn(contracts);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        when(roomService.room((Long) any())).thenReturn(rooms);

        ArrayList<AddBillRequest> addBillRequestList = new ArrayList<>();
        addBillRequestList.add(new AddBillRequest());
        billServiceImpl.addBill(addBillRequestList);
    }

    /**
     * Method under test: {@link BillServiceImpl#addBill(List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddBill3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.length()" because "text" is null
        //       at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1474)
        //       at java.text.DateFormat.parse(DateFormat.java:397)
        //       at vn.com.fpt.common.utils.DateUtils.parse(DateUtils.java:52)
        //       at vn.com.fpt.common.utils.DateUtils.parse(DateUtils.java:60)
        //       at vn.com.fpt.service.bill.BillServiceImpl.addBill(BillServiceImpl.java:153)
        //   See https://diff.blue/R013 to resolve this issue.

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
        when(contractService.contract((Long) any())).thenReturn(contracts);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        when(roomService.room((Long) any())).thenReturn(rooms);

        AddBillRequest addBillRequest = new AddBillRequest();
        addBillRequest.setTotalRoomMoney(10.0d);

        ArrayList<AddBillRequest> addBillRequestList = new ArrayList<>();
        addBillRequestList.add(addBillRequest);
        billServiceImpl.addBill(addBillRequestList);
    }

    /**
     * Method under test: {@link BillServiceImpl#addBill(List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddBill4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Collection.isEmpty()" because "that" is null
        //       at vn.com.fpt.service.bill.BillServiceImpl.addBill(BillServiceImpl.java:162)
        //   See https://diff.blue/R013 to resolve this issue.

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
        when(contractService.contract((Long) any())).thenReturn(contracts);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        when(roomService.room((Long) any())).thenReturn(rooms);

        AddBillRequest addBillRequest = new AddBillRequest();
        addBillRequest.setTotalRoomMoney(0.0d);

        ArrayList<AddBillRequest> addBillRequestList = new ArrayList<>();
        addBillRequestList.add(addBillRequest);
        billServiceImpl.addBill(addBillRequestList);
    }

    /**
     * Method under test: {@link BillServiceImpl#addBill(List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddBill5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.length()" because "text" is null
        //       at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1474)
        //       at java.text.DateFormat.parse(DateFormat.java:397)
        //       at vn.com.fpt.common.utils.DateUtils.parse(DateUtils.java:52)
        //       at vn.com.fpt.common.utils.DateUtils.parse(DateUtils.java:60)
        //       at vn.com.fpt.service.bill.BillServiceImpl.addBill(BillServiceImpl.java:216)
        //   See https://diff.blue/R013 to resolve this issue.

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
        when(contractService.contract((Long) any())).thenReturn(contracts);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        when(roomService.room((Long) any())).thenReturn(rooms);

        AddBillRequest addBillRequest = new AddBillRequest();
        addBillRequest.setServiceBill(new ArrayList<>());
        addBillRequest.setTotalRoomMoney(0.0d);

        ArrayList<AddBillRequest> addBillRequestList = new ArrayList<>();
        addBillRequestList.add(addBillRequest);
        billServiceImpl.addBill(addBillRequestList);
    }

    /**
     * Method under test: {@link BillServiceImpl#addBill(List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddBill6() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.length()" because "text" is null
        //       at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1474)
        //       at java.text.DateFormat.parse(DateFormat.java:397)
        //       at vn.com.fpt.common.utils.DateUtils.parse(DateUtils.java:52)
        //       at vn.com.fpt.common.utils.DateUtils.parse(DateUtils.java:60)
        //       at vn.com.fpt.service.bill.BillServiceImpl.addBill(BillServiceImpl.java:186)
        //   See https://diff.blue/R013 to resolve this issue.

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
        when(contractService.contract((Long) any())).thenReturn(contracts);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        when(roomService.setServiceIndex((Long) any(), (Integer) any(), (Integer) any(), (Long) any()))
                .thenReturn(rooms1);
        when(roomService.room((Long) any())).thenReturn(rooms);

        BasicServices basicServices = new BasicServices();
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        basicServices.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        basicServices.setCreatedBy(1L);
        basicServices.setId(123L);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        basicServices.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        basicServices.setModifiedBy(1L);
        basicServices.setServiceName("Service Name");
        basicServices.setServiceShowName("Service Show Name");
        when(servicesService.basicService((Long) any())).thenReturn(basicServices);

        ArrayList<AddBillRequest.ServiceBill> serviceBillList = new ArrayList<>();
        serviceBillList.add(new AddBillRequest.ServiceBill());

        AddBillRequest addBillRequest = new AddBillRequest();
        addBillRequest.setServiceBill(serviceBillList);
        addBillRequest.setTotalRoomMoney(0.0d);

        ArrayList<AddBillRequest> addBillRequestList = new ArrayList<>();
        addBillRequestList.add(addBillRequest);
        billServiceImpl.addBill(addBillRequestList);
    }

    /**
     * Method under test: {@link BillServiceImpl#listRoomWithBillStatus(Long)}
     */
    @Test
    void testListRoomWithBillStatus() {
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
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
        assertTrue(billServiceImpl.listRoomWithBillStatus(123L).isEmpty());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
        verify(groupService).getGroup((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listRoomWithBillStatus(Long)}
     */
    @Test
    void testListRoomWithBillStatus2() {
        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(new RoomsResponse());
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);

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
        assertTrue(billServiceImpl.listRoomWithBillStatus(123L).isEmpty());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
        verify(groupService).getGroup((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listRoomWithBillStatus(Long)}
     */
    @Test
    void testListRoomWithBillStatus3() {
        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(new RoomsResponse());
        roomsResponseList.add(new RoomsResponse());
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);

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
        assertTrue(billServiceImpl.listRoomWithBillStatus(123L).isEmpty());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
        verify(groupService).getGroup((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listRoomWithBillStatus(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListRoomWithBillStatus4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.responses.RoomsResponse.getContractId()" because "e" is null
        //       at vn.com.fpt.service.bill.BillServiceImpl.lambda$listRoomWithBillStatus$6(BillServiceImpl.java:242)
        //       at java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:575)
        //       at java.util.stream.AbstractPipeline.evaluateToArrayNode(AbstractPipeline.java:260)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:616)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:622)
        //       at java.util.stream.ReferencePipeline.toList(ReferencePipeline.java:627)
        //       at vn.com.fpt.service.bill.BillServiceImpl.listRoomWithBillStatus(BillServiceImpl.java:242)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(null);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);

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
        billServiceImpl.listRoomWithBillStatus(123L);
    }

    /**
     * Method under test: {@link BillServiceImpl#listRoomWithBillStatus(Long)}
     */
    @Test
    void testListRoomWithBillStatus5() {
        when(contractService.roomContract((Long) any())).thenReturn(new RoomContractDTO());

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(new RoomsResponse(123L, "Đã đến kỳ lập hóa đơn (Kỳ 15)", 10, 10, 1, 1, 123L, 123L, 123L,
                10.0d, 10.0d, true, new ArrayList<>()));
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);

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
        assertTrue(billServiceImpl.listRoomWithBillStatus(123L).isEmpty());
        verify(contractService).roomContract((Long) any());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
        verify(groupService).getGroup((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listRoomWithBillStatus(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListRoomWithBillStatus6() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.model.RoomContractDTO.getContractIsDisable()" because "contract" is null
        //       at vn.com.fpt.service.bill.BillServiceImpl.lambda$listRoomWithBillStatus$7(BillServiceImpl.java:247)
        //       at java.lang.Iterable.forEach(Iterable.java:75)
        //       at vn.com.fpt.service.bill.BillServiceImpl.listRoomWithBillStatus(BillServiceImpl.java:245)
        //   See https://diff.blue/R013 to resolve this issue.

        when(contractService.roomContract((Long) any())).thenReturn(null);

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(new RoomsResponse(123L, "Đã đến kỳ lập hóa đơn (Kỳ 15)", 10, 10, 1, 1, 123L, 123L, 123L,
                10.0d, 10.0d, true, new ArrayList<>()));
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);

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
        billServiceImpl.listRoomWithBillStatus(123L);
    }

    /**
     * Method under test: {@link BillServiceImpl#listRoomWithBillStatus(Long)}
     */
    @Test
    void testListRoomWithBillStatus7() {
        Rooms room = new Rooms();
        ArrayList<RentersResponse> listRenter = new ArrayList<>();
        ArrayList<RoomAssetDTO> listRoomAsset = new ArrayList<>();
        ArrayList<HandOverGeneralServiceDTO> listHandOverGeneralService = new ArrayList<>();
        when(contractService.roomContract((Long) any())).thenReturn(new RoomContractDTO(123L,
                "Đã đến kỳ lập hóa đơn (Kỳ 15)", "Đã đến kỳ lập hóa đơn (Kỳ 15)", room, 10.0d, 10.0d, 10, 10, "2020-03-01",
                "2020-03-01", "Đã đến kỳ lập hóa đơn (Kỳ 15)", 10, true, 10L, 123L, "Đã đến kỳ lập hóa đơn (Kỳ 15)", 123L, 10,
                listRenter, listRoomAsset, listHandOverGeneralService, new ArrayList<>()));

        ArrayList<RoomsResponse> roomsResponseList = new ArrayList<>();
        roomsResponseList.add(new RoomsResponse(123L, "Đã đến kỳ lập hóa đơn (Kỳ 15)", 10, 10, 1, 1, 123L, 123L, 123L,
                10.0d, 10.0d, true, new ArrayList<>()));
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(roomsResponseList);

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
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        when(recurringBillRepository.findAllByRoomIdAndIsPaid((Long) any(), (Boolean) any()))
                .thenReturn(new ArrayList<>());
        assertEquals(1, billServiceImpl.listRoomWithBillStatus(123L).size());
        verify(contractService).roomContract((Long) any());
        verify(roomService).listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any());
        verify(groupService).getGroup((Long) any());
        verify(renterService).listRenter((Long) any());
        verify(recurringBillRepository).findAllByRoomIdAndIsPaid((Long) any(), (Boolean) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#addBillPreview(List)}
     */
    @Test
    void testAddBillPreview() {
        assertTrue(billServiceImpl.addBillPreview(new ArrayList<>()).isEmpty());
    }

    /**
     * Method under test: {@link BillServiceImpl#addBillPreview(List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddBillPreview2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Double.doubleValue()" because the return value of "vn.com.fpt.requests.PreviewAddBillRequest.getTotalMoneyRoomPrice()" is null
        //       at vn.com.fpt.service.bill.BillServiceImpl.lambda$addBillPreview$8(BillServiceImpl.java:294)
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:575)
        //       at java.util.stream.AbstractPipeline.evaluateToArrayNode(AbstractPipeline.java:260)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:616)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:622)
        //       at java.util.stream.ReferencePipeline.toList(ReferencePipeline.java:627)
        //       at vn.com.fpt.service.bill.BillServiceImpl.addBillPreview(BillServiceImpl.java:307)
        //   See https://diff.blue/R013 to resolve this issue.

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
        when(contractService.contract((Long) any())).thenReturn(contracts);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());

        ArrayList<PreviewAddBillRequest> previewAddBillRequestList = new ArrayList<>();
        previewAddBillRequestList.add(new PreviewAddBillRequest());
        billServiceImpl.addBillPreview(previewAddBillRequestList);
    }

    /**
     * Method under test: {@link BillServiceImpl#addBillPreview(List)}
     */
    @Test
    void testAddBillPreview3() {
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
        when(contractService.contract((Long) any())).thenReturn(contracts);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());

        PreviewAddBillRequest previewAddBillRequest = new PreviewAddBillRequest();
        previewAddBillRequest.setTotalMoneyRoomPrice(10.0d);

        ArrayList<PreviewAddBillRequest> previewAddBillRequestList = new ArrayList<>();
        previewAddBillRequestList.add(previewAddBillRequest);
        assertEquals(1, billServiceImpl.addBillPreview(previewAddBillRequestList).size());
        verify(contractService).contract((Long) any());
        verify(roomService).room((Long) any());
        verify(renterService).listRenter((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#addBillPreview(List)}
     */
    @Test
    void testAddBillPreview4() {
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
        when(contractService.contract((Long) any())).thenReturn(contracts);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        PreviewAddBillRequest previewAddBillRequest = mock(PreviewAddBillRequest.class);
        when(previewAddBillRequest.getTotalMoneyRoomPrice()).thenReturn(10.0d);
        when(previewAddBillRequest.getTotalMoneyServicePrice()).thenReturn(10.0d);
        when(previewAddBillRequest.getRoomCurrentElectricIndex()).thenReturn(1);
        when(previewAddBillRequest.getRoomId()).thenReturn(123L);
        when(previewAddBillRequest.getServiceBill()).thenReturn(new ArrayList<>());

        ArrayList<PreviewAddBillRequest> previewAddBillRequestList = new ArrayList<>();
        previewAddBillRequestList.add(previewAddBillRequest);
        assertEquals(1, billServiceImpl.addBillPreview(previewAddBillRequestList).size());
        verify(contractService).contract((Long) any());
        verify(roomService).room((Long) any());
        verify(renterService).listRenter((Long) any());
        verify(previewAddBillRequest).getTotalMoneyRoomPrice();
        verify(previewAddBillRequest, atLeast(1)).getTotalMoneyServicePrice();
        verify(previewAddBillRequest, atLeast(1)).getRoomCurrentElectricIndex();
        verify(previewAddBillRequest, atLeast(1)).getRoomId();
        verify(previewAddBillRequest).getServiceBill();
    }

    /**
     * Method under test: {@link BillServiceImpl#addBillPreview(List)}
     */
    @Test
    void testAddBillPreview5() {
        Contracts contracts = mock(Contracts.class);
        when(contracts.getContractBillCycle()).thenReturn(0);
        when(contracts.getContractPaymentCycle()).thenReturn(1);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(contracts.getContractStartDate())
                .thenReturn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        doNothing().when(contracts).setCreatedAt((Date) any());
        doNothing().when(contracts).setCreatedBy((Long) any());
        doNothing().when(contracts).setId((Long) any());
        doNothing().when(contracts).setModifiedAt((Date) any());
        doNothing().when(contracts).setModifiedBy((Long) any());
        doNothing().when(contracts).setAddressId((Long) any());
        doNothing().when(contracts).setContractBillCycle((Integer) any());
        doNothing().when(contracts).setContractDeposit((Double) any());
        doNothing().when(contracts).setContractEndDate((Date) any());
        doNothing().when(contracts).setContractIsDisable((Boolean) any());
        doNothing().when(contracts).setContractName((String) any());
        doNothing().when(contracts).setContractPaymentCycle((Integer) any());
        doNothing().when(contracts).setContractPrice((Double) any());
        doNothing().when(contracts).setContractStartDate((Date) any());
        doNothing().when(contracts).setContractTerm((Integer) any());
        doNothing().when(contracts).setContractType((Integer) any());
        doNothing().when(contracts).setGroupId((Long) any());
        doNothing().when(contracts).setNote((String) any());
        doNothing().when(contracts).setRackRenters((Long) any());
        doNothing().when(contracts).setRenters((Long) any());
        doNothing().when(contracts).setRoomId((Long) any());
        contracts.setAddressId(123L);
        contracts.setContractBillCycle(1);
        contracts.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractEndDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractIsDisable(true);
        contracts.setContractName("Contract Name");
        contracts.setContractPaymentCycle(1);
        contracts.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractStartDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractTerm(1);
        contracts.setContractType(1);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setCreatedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setCreatedBy(1L);
        contracts.setGroupId(123L);
        contracts.setId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setModifiedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setModifiedBy(1L);
        contracts.setNote("Note");
        contracts.setRackRenters(1L);
        contracts.setRenters(1L);
        contracts.setRoomId(123L);
        when(contractService.contract((Long) any())).thenReturn(contracts);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        PreviewAddBillRequest previewAddBillRequest = mock(PreviewAddBillRequest.class);
        when(previewAddBillRequest.getTotalMoneyRoomPrice()).thenReturn(10.0d);
        when(previewAddBillRequest.getTotalMoneyServicePrice()).thenReturn(10.0d);
        when(previewAddBillRequest.getRoomCurrentElectricIndex()).thenReturn(1);
        when(previewAddBillRequest.getRoomId()).thenReturn(123L);
        when(previewAddBillRequest.getServiceBill()).thenReturn(new ArrayList<>());

        ArrayList<PreviewAddBillRequest> previewAddBillRequestList = new ArrayList<>();
        previewAddBillRequestList.add(previewAddBillRequest);
        assertThrows(ArithmeticException.class, () -> billServiceImpl.addBillPreview(previewAddBillRequestList));
        verify(contractService).contract((Long) any());
        verify(contracts).getContractBillCycle();
        verify(contracts).getContractPaymentCycle();
        verify(contracts).getContractStartDate();
        verify(contracts).setCreatedAt((Date) any());
        verify(contracts).setCreatedBy((Long) any());
        verify(contracts).setId((Long) any());
        verify(contracts).setModifiedAt((Date) any());
        verify(contracts).setModifiedBy((Long) any());
        verify(contracts).setAddressId((Long) any());
        verify(contracts).setContractBillCycle((Integer) any());
        verify(contracts).setContractDeposit((Double) any());
        verify(contracts).setContractEndDate((Date) any());
        verify(contracts).setContractIsDisable((Boolean) any());
        verify(contracts).setContractName((String) any());
        verify(contracts).setContractPaymentCycle((Integer) any());
        verify(contracts).setContractPrice((Double) any());
        verify(contracts).setContractStartDate((Date) any());
        verify(contracts).setContractTerm((Integer) any());
        verify(contracts).setContractType((Integer) any());
        verify(contracts).setGroupId((Long) any());
        verify(contracts).setNote((String) any());
        verify(contracts).setRackRenters((Long) any());
        verify(contracts).setRenters((Long) any());
        verify(contracts).setRoomId((Long) any());
        verify(roomService).room((Long) any());
        verify(renterService).listRenter((Long) any());
        verify(previewAddBillRequest).getTotalMoneyRoomPrice();
        verify(previewAddBillRequest, atLeast(1)).getTotalMoneyServicePrice();
        verify(previewAddBillRequest, atLeast(1)).getRoomCurrentElectricIndex();
        verify(previewAddBillRequest, atLeast(1)).getRoomId();
        verify(previewAddBillRequest).getServiceBill();
    }

    /**
     * Method under test: {@link BillServiceImpl#roomBillHistory(Long)}
     */
    @Test
    void testRoomBillHistory() {
        ArrayList<RecurringBill> recurringBillList = new ArrayList<>();
        when(recurringBillRepository.findAllByRoomId((Long) any())).thenReturn(recurringBillList);
        List<RecurringBill> actualRoomBillHistoryResult = billServiceImpl.roomBillHistory(123L);
        assertSame(recurringBillList, actualRoomBillHistoryResult);
        assertTrue(actualRoomBillHistoryResult.isEmpty());
        verify(recurringBillRepository).findAllByRoomId((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#payRoomBill(List)}
     */
    @Test
    void testPayRoomBill() throws JsonProcessingException {
        when(recurringBillRepository.saveAll((Iterable<RecurringBill>) any())).thenReturn(new ArrayList<>());
        when(recurringBillRepository.findAllByIdIn((List<Long>) any())).thenReturn(new ArrayList<>());
        when(moneySourceRepository.saveAll((Iterable<MoneySource>) any())).thenReturn(new ArrayList<>());
        doNothing().when(tableLogComponent).saveMoneySourceHistory((List<MoneySource>) any());
        doNothing().when(tableLogComponent).updateEvent((List<UpdateLog>) any());
        billServiceImpl.payRoomBill(new ArrayList<>());
        verify(recurringBillRepository).saveAll((Iterable<RecurringBill>) any());
        verify(recurringBillRepository).findAllByIdIn((List<Long>) any());
        verify(moneySourceRepository).saveAll((Iterable<MoneySource>) any());
        verify(tableLogComponent).saveMoneySourceHistory((List<MoneySource>) any());
        verify(tableLogComponent).updateEvent((List<UpdateLog>) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#payRoomBill(List)}
     */
    @Test
    void testPayRoomBill2() throws JsonProcessingException {
        RecurringBill recurringBill = new RecurringBill();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setBillCreatedTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setBillType("Bill Type");
        recurringBill.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setCreatedBy(1L);
        recurringBill.setDescription("The characteristics of someone or something");
        recurringBill.setGroupContractId(123L);
        recurringBill.setGroupId(123L);
        recurringBill.setId(123L);
        recurringBill.setIsDebt(true);
        recurringBill.setIsInBillCircle(true);
        recurringBill.setIsPaid(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setModifiedBy(1L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setPaymentTerm(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setRoomBillId(123L);
        recurringBill.setRoomId(123L);
        recurringBill.setRoomName("Room Name");
        recurringBill.setServiceBillId(123L);
        recurringBill.setTotalMoney(10.0d);

        ArrayList<RecurringBill> recurringBillList = new ArrayList<>();
        recurringBillList.add(recurringBill);
        when(recurringBillRepository.saveAll((Iterable<RecurringBill>) any())).thenReturn(new ArrayList<>());
        when(recurringBillRepository.findAllByIdIn((List<Long>) any())).thenReturn(recurringBillList);
        when(moneySourceRepository.saveAll((Iterable<MoneySource>) any())).thenReturn(new ArrayList<>());
        doNothing().when(tableLogComponent).saveMoneySourceHistory((List<MoneySource>) any());
        doNothing().when(tableLogComponent).updateEvent((List<UpdateLog>) any());
        billServiceImpl.payRoomBill(new ArrayList<>());
        verify(recurringBillRepository).saveAll((Iterable<RecurringBill>) any());
        verify(recurringBillRepository).findAllByIdIn((List<Long>) any());
        verify(moneySourceRepository).saveAll((Iterable<MoneySource>) any());
        verify(tableLogComponent).saveMoneySourceHistory((List<MoneySource>) any());
        verify(tableLogComponent).updateEvent((List<UpdateLog>) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#payRoomBill(List)}
     */
    @Test
    void testPayRoomBill3() throws JsonProcessingException {
        RecurringBill recurringBill = new RecurringBill();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setBillCreatedTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setBillType("Bill Type");
        recurringBill.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setCreatedBy(1L);
        recurringBill.setDescription("The characteristics of someone or something");
        recurringBill.setGroupContractId(123L);
        recurringBill.setGroupId(123L);
        recurringBill.setId(123L);
        recurringBill.setIsDebt(true);
        recurringBill.setIsInBillCircle(true);
        recurringBill.setIsPaid(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setModifiedBy(1L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setPaymentTerm(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setRoomBillId(123L);
        recurringBill.setRoomId(123L);
        recurringBill.setRoomName("Room Name");
        recurringBill.setServiceBillId(123L);
        recurringBill.setTotalMoney(10.0d);

        RecurringBill recurringBill1 = new RecurringBill();
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill1.setBillCreatedTime(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill1.setBillType(RecurringBill.TABLE_NAME);
        recurringBill1.setContractId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill1.setCreatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill1.setCreatedBy(1L);
        recurringBill1.setDescription("The characteristics of someone or something");
        recurringBill1.setGroupContractId(123L);
        recurringBill1.setGroupId(123L);
        recurringBill1.setId(123L);
        recurringBill1.setIsDebt(true);
        recurringBill1.setIsInBillCircle(true);
        recurringBill1.setIsPaid(true);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill1.setModifiedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill1.setModifiedBy(1L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill1.setPaymentTerm(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill1.setRoomBillId(123L);
        recurringBill1.setRoomId(123L);
        recurringBill1.setRoomName(RecurringBill.TABLE_NAME);
        recurringBill1.setServiceBillId(123L);
        recurringBill1.setTotalMoney(10.0d);

        ArrayList<RecurringBill> recurringBillList = new ArrayList<>();
        recurringBillList.add(recurringBill1);
        recurringBillList.add(recurringBill);
        when(recurringBillRepository.saveAll((Iterable<RecurringBill>) any())).thenReturn(new ArrayList<>());
        when(recurringBillRepository.findAllByIdIn((List<Long>) any())).thenReturn(recurringBillList);
        when(moneySourceRepository.saveAll((Iterable<MoneySource>) any())).thenReturn(new ArrayList<>());
        doNothing().when(tableLogComponent).saveMoneySourceHistory((List<MoneySource>) any());
        doNothing().when(tableLogComponent).updateEvent((List<UpdateLog>) any());
        billServiceImpl.payRoomBill(new ArrayList<>());
        verify(recurringBillRepository).saveAll((Iterable<RecurringBill>) any());
        verify(recurringBillRepository).findAllByIdIn((List<Long>) any());
        verify(moneySourceRepository).saveAll((Iterable<MoneySource>) any());
        verify(tableLogComponent).saveMoneySourceHistory((List<MoneySource>) any());
        verify(tableLogComponent).updateEvent((List<UpdateLog>) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#payRoomBill(List)}
     */
    @Test
    void testPayRoomBill4() throws JsonProcessingException {
        RecurringBill recurringBill = mock(RecurringBill.class);
        when(recurringBill.getIsDebt()).thenReturn(true);
        when(recurringBill.getIsPaid()).thenReturn(true);
        when(recurringBill.getTotalMoney()).thenReturn(10.0d);
        when(recurringBill.getId()).thenReturn(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        when(recurringBill.getBillCreatedTime())
                .thenReturn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        doNothing().when(recurringBill).setCreatedAt((Date) any());
        doNothing().when(recurringBill).setCreatedBy((Long) any());
        doNothing().when(recurringBill).setId((Long) any());
        doNothing().when(recurringBill).setModifiedAt((Date) any());
        doNothing().when(recurringBill).setModifiedBy((Long) any());
        doNothing().when(recurringBill).setBillCreatedTime((Date) any());
        doNothing().when(recurringBill).setBillType((String) any());
        doNothing().when(recurringBill).setContractId((Long) any());
        doNothing().when(recurringBill).setDescription((String) any());
        doNothing().when(recurringBill).setGroupContractId((Long) any());
        doNothing().when(recurringBill).setGroupId((Long) any());
        doNothing().when(recurringBill).setIsDebt((Boolean) any());
        doNothing().when(recurringBill).setIsInBillCircle((Boolean) any());
        doNothing().when(recurringBill).setIsPaid((Boolean) any());
        doNothing().when(recurringBill).setPaymentTerm((Date) any());
        doNothing().when(recurringBill).setRoomBillId((Long) any());
        doNothing().when(recurringBill).setRoomId((Long) any());
        doNothing().when(recurringBill).setRoomName((String) any());
        doNothing().when(recurringBill).setServiceBillId((Long) any());
        doNothing().when(recurringBill).setTotalMoney((Double) any());
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setBillCreatedTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setBillType("Bill Type");
        recurringBill.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setCreatedBy(1L);
        recurringBill.setDescription("The characteristics of someone or something");
        recurringBill.setGroupContractId(123L);
        recurringBill.setGroupId(123L);
        recurringBill.setId(123L);
        recurringBill.setIsDebt(true);
        recurringBill.setIsInBillCircle(true);
        recurringBill.setIsPaid(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setModifiedBy(1L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setPaymentTerm(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setRoomBillId(123L);
        recurringBill.setRoomId(123L);
        recurringBill.setRoomName("Room Name");
        recurringBill.setServiceBillId(123L);
        recurringBill.setTotalMoney(10.0d);

        ArrayList<RecurringBill> recurringBillList = new ArrayList<>();
        recurringBillList.add(recurringBill);
        when(recurringBillRepository.saveAll((Iterable<RecurringBill>) any())).thenReturn(new ArrayList<>());
        when(recurringBillRepository.findAllByIdIn((List<Long>) any())).thenReturn(recurringBillList);
        when(moneySourceRepository.saveAll((Iterable<MoneySource>) any())).thenReturn(new ArrayList<>());
        doNothing().when(tableLogComponent).saveMoneySourceHistory((List<MoneySource>) any());
        doNothing().when(tableLogComponent).updateEvent((List<UpdateLog>) any());
        billServiceImpl.payRoomBill(new ArrayList<>());
        verify(recurringBillRepository).saveAll((Iterable<RecurringBill>) any());
        verify(recurringBillRepository).findAllByIdIn((List<Long>) any());
        verify(recurringBill).getIsDebt();
        verify(recurringBill).getIsPaid();
        verify(recurringBill).getTotalMoney();
        verify(recurringBill, atLeast(1)).getId();
        verify(recurringBill, atLeast(1)).getBillCreatedTime();
        verify(recurringBill).setCreatedAt((Date) any());
        verify(recurringBill).setCreatedBy((Long) any());
        verify(recurringBill).setId((Long) any());
        verify(recurringBill).setModifiedAt((Date) any());
        verify(recurringBill).setModifiedBy((Long) any());
        verify(recurringBill).setBillCreatedTime((Date) any());
        verify(recurringBill).setBillType((String) any());
        verify(recurringBill).setContractId((Long) any());
        verify(recurringBill).setDescription((String) any());
        verify(recurringBill).setGroupContractId((Long) any());
        verify(recurringBill).setGroupId((Long) any());
        verify(recurringBill, atLeast(1)).setIsDebt((Boolean) any());
        verify(recurringBill).setIsInBillCircle((Boolean) any());
        verify(recurringBill, atLeast(1)).setIsPaid((Boolean) any());
        verify(recurringBill).setPaymentTerm((Date) any());
        verify(recurringBill).setRoomBillId((Long) any());
        verify(recurringBill).setRoomId((Long) any());
        verify(recurringBill).setRoomName((String) any());
        verify(recurringBill).setServiceBillId((Long) any());
        verify(recurringBill).setTotalMoney((Double) any());
        verify(moneySourceRepository).saveAll((Iterable<MoneySource>) any());
        verify(tableLogComponent).saveMoneySourceHistory((List<MoneySource>) any());
        verify(tableLogComponent).updateEvent((List<UpdateLog>) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#deleteRoomBill(List)}
     */
    @Test
    void testDeleteRoomBill() {
        when(recurringBillRepository.findAllByIdIn((List<Long>) any())).thenReturn(new ArrayList<>());
        billServiceImpl.deleteRoomBill(new ArrayList<>());
        verify(recurringBillRepository).findAllByIdIn((List<Long>) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#deleteRoomBill(List)}
     */
    @Test
    void testDeleteRoomBill2() {
        RecurringBill recurringBill = new RecurringBill();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setBillCreatedTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setBillType("Bill Type");
        recurringBill.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setCreatedBy(1L);
        recurringBill.setDescription("The characteristics of someone or something");
        recurringBill.setGroupContractId(123L);
        recurringBill.setGroupId(123L);
        recurringBill.setId(123L);
        recurringBill.setIsDebt(true);
        recurringBill.setIsInBillCircle(true);
        recurringBill.setIsPaid(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setModifiedBy(1L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setPaymentTerm(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setRoomBillId(123L);
        recurringBill.setRoomId(123L);
        recurringBill.setRoomName("Room Name");
        recurringBill.setServiceBillId(123L);
        recurringBill.setTotalMoney(10.0d);

        ArrayList<RecurringBill> recurringBillList = new ArrayList<>();
        recurringBillList.add(recurringBill);
        doNothing().when(recurringBillRepository).delete((RecurringBill) any());
        when(recurringBillRepository.findAllByIdIn((List<Long>) any())).thenReturn(recurringBillList);
        when(serviceBillRepository.findAllById((Iterable<Long>) any())).thenReturn(new ArrayList<>());
        when(roomBillRepository.findAllById((Iterable<Long>) any())).thenReturn(new ArrayList<>());
        billServiceImpl.deleteRoomBill(new ArrayList<>());
        verify(recurringBillRepository).findAllByIdIn((List<Long>) any());
        verify(recurringBillRepository).delete((RecurringBill) any());
        verify(serviceBillRepository).findAllById((Iterable<Long>) any());
        verify(roomBillRepository).findAllById((Iterable<Long>) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#deleteRoomBill(List)}
     */
    @Test
    void testDeleteRoomBill3() {
        RecurringBill recurringBill = new RecurringBill();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setBillCreatedTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setBillType("Bill Type");
        recurringBill.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setCreatedBy(1L);
        recurringBill.setDescription("The characteristics of someone or something");
        recurringBill.setGroupContractId(123L);
        recurringBill.setGroupId(123L);
        recurringBill.setId(123L);
        recurringBill.setIsDebt(true);
        recurringBill.setIsInBillCircle(true);
        recurringBill.setIsPaid(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setModifiedBy(1L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setPaymentTerm(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setRoomBillId(123L);
        recurringBill.setRoomId(123L);
        recurringBill.setRoomName("Room Name");
        recurringBill.setServiceBillId(123L);
        recurringBill.setTotalMoney(10.0d);

        RecurringBill recurringBill1 = new RecurringBill();
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill1.setBillCreatedTime(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill1.setBillType("Bill Type");
        recurringBill1.setContractId(123L);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill1.setCreatedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill1.setCreatedBy(1L);
        recurringBill1.setDescription("The characteristics of someone or something");
        recurringBill1.setGroupContractId(123L);
        recurringBill1.setGroupId(123L);
        recurringBill1.setId(123L);
        recurringBill1.setIsDebt(true);
        recurringBill1.setIsInBillCircle(true);
        recurringBill1.setIsPaid(true);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill1.setModifiedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill1.setModifiedBy(1L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill1.setPaymentTerm(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill1.setRoomBillId(123L);
        recurringBill1.setRoomId(123L);
        recurringBill1.setRoomName("Room Name");
        recurringBill1.setServiceBillId(123L);
        recurringBill1.setTotalMoney(10.0d);

        ArrayList<RecurringBill> recurringBillList = new ArrayList<>();
        recurringBillList.add(recurringBill1);
        recurringBillList.add(recurringBill);
        doNothing().when(recurringBillRepository).delete((RecurringBill) any());
        when(recurringBillRepository.findAllByIdIn((List<Long>) any())).thenReturn(recurringBillList);
        when(serviceBillRepository.findAllById((Iterable<Long>) any())).thenReturn(new ArrayList<>());
        when(roomBillRepository.findAllById((Iterable<Long>) any())).thenReturn(new ArrayList<>());
        billServiceImpl.deleteRoomBill(new ArrayList<>());
        verify(recurringBillRepository).findAllByIdIn((List<Long>) any());
        verify(recurringBillRepository, atLeast(1)).delete((RecurringBill) any());
        verify(serviceBillRepository, atLeast(1)).findAllById((Iterable<Long>) any());
        verify(roomBillRepository, atLeast(1)).findAllById((Iterable<Long>) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#deleteRoomBill(List)}
     */
    @Test
    void testDeleteRoomBill4() {
        RecurringBill recurringBill = mock(RecurringBill.class);
        when(recurringBill.getRoomBillId()).thenReturn(123L);
        when(recurringBill.getServiceBillId()).thenReturn(123L);
        doNothing().when(recurringBill).setCreatedAt((Date) any());
        doNothing().when(recurringBill).setCreatedBy((Long) any());
        doNothing().when(recurringBill).setId((Long) any());
        doNothing().when(recurringBill).setModifiedAt((Date) any());
        doNothing().when(recurringBill).setModifiedBy((Long) any());
        doNothing().when(recurringBill).setBillCreatedTime((Date) any());
        doNothing().when(recurringBill).setBillType((String) any());
        doNothing().when(recurringBill).setContractId((Long) any());
        doNothing().when(recurringBill).setDescription((String) any());
        doNothing().when(recurringBill).setGroupContractId((Long) any());
        doNothing().when(recurringBill).setGroupId((Long) any());
        doNothing().when(recurringBill).setIsDebt((Boolean) any());
        doNothing().when(recurringBill).setIsInBillCircle((Boolean) any());
        doNothing().when(recurringBill).setIsPaid((Boolean) any());
        doNothing().when(recurringBill).setPaymentTerm((Date) any());
        doNothing().when(recurringBill).setRoomBillId((Long) any());
        doNothing().when(recurringBill).setRoomId((Long) any());
        doNothing().when(recurringBill).setRoomName((String) any());
        doNothing().when(recurringBill).setServiceBillId((Long) any());
        doNothing().when(recurringBill).setTotalMoney((Double) any());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setBillCreatedTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setBillType("Bill Type");
        recurringBill.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setCreatedBy(1L);
        recurringBill.setDescription("The characteristics of someone or something");
        recurringBill.setGroupContractId(123L);
        recurringBill.setGroupId(123L);
        recurringBill.setId(123L);
        recurringBill.setIsDebt(true);
        recurringBill.setIsInBillCircle(true);
        recurringBill.setIsPaid(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setModifiedBy(1L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setPaymentTerm(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setRoomBillId(123L);
        recurringBill.setRoomId(123L);
        recurringBill.setRoomName("Room Name");
        recurringBill.setServiceBillId(123L);
        recurringBill.setTotalMoney(10.0d);

        ArrayList<RecurringBill> recurringBillList = new ArrayList<>();
        recurringBillList.add(recurringBill);
        doNothing().when(recurringBillRepository).delete((RecurringBill) any());
        when(recurringBillRepository.findAllByIdIn((List<Long>) any())).thenReturn(recurringBillList);
        when(serviceBillRepository.findAllById((Iterable<Long>) any())).thenReturn(new ArrayList<>());
        when(roomBillRepository.findAllById((Iterable<Long>) any())).thenReturn(new ArrayList<>());
        billServiceImpl.deleteRoomBill(new ArrayList<>());
        verify(recurringBillRepository).findAllByIdIn((List<Long>) any());
        verify(recurringBillRepository).delete((RecurringBill) any());
        verify(recurringBill).getRoomBillId();
        verify(recurringBill, atLeast(1)).getServiceBillId();
        verify(recurringBill).setCreatedAt((Date) any());
        verify(recurringBill).setCreatedBy((Long) any());
        verify(recurringBill).setId((Long) any());
        verify(recurringBill).setModifiedAt((Date) any());
        verify(recurringBill).setModifiedBy((Long) any());
        verify(recurringBill).setBillCreatedTime((Date) any());
        verify(recurringBill).setBillType((String) any());
        verify(recurringBill).setContractId((Long) any());
        verify(recurringBill).setDescription((String) any());
        verify(recurringBill).setGroupContractId((Long) any());
        verify(recurringBill).setGroupId((Long) any());
        verify(recurringBill).setIsDebt((Boolean) any());
        verify(recurringBill).setIsInBillCircle((Boolean) any());
        verify(recurringBill).setIsPaid((Boolean) any());
        verify(recurringBill).setPaymentTerm((Date) any());
        verify(recurringBill).setRoomBillId((Long) any());
        verify(recurringBill).setRoomId((Long) any());
        verify(recurringBill).setRoomName((String) any());
        verify(recurringBill).setServiceBillId((Long) any());
        verify(recurringBill).setTotalMoney((Double) any());
        verify(serviceBillRepository).findAllById((Iterable<Long>) any());
        verify(roomBillRepository).findAllById((Iterable<Long>) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#payBillInformation(Long)}
     */
    @Test
    void testPayBillInformation() {
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
        when(contractService.contract((Long) any())).thenReturn(contracts);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setModifiedBy(1L);
        rooms.setRoomArea(10.0d);
        rooms.setRoomCurrentElectricIndex(1);
        rooms.setRoomCurrentWaterIndex(1);
        rooms.setRoomFloor(1);
        rooms.setRoomLimitPeople(1);
        rooms.setRoomName("Room Name");
        rooms.setRoomPrice(10.0d);
        when(roomService.room((Long) any())).thenReturn(rooms);
        ArrayList<GeneralServiceDTO> generalServiceDTOList = new ArrayList<>();
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(generalServiceDTOList);
        when(renterService.listRenter((Long) any())).thenReturn(new ArrayList<>());
        PayBillInformationResponse actualPayBillInformationResult = billServiceImpl.payBillInformation(123L);
        assertEquals(123L, actualPayBillInformationResult.getContractId().longValue());
        assertEquals(0, actualPayBillInformationResult.getTotalRenter().intValue());
        assertEquals(10.0d, actualPayBillInformationResult.getRoomPrice().doubleValue());
        assertEquals("Room Name", actualPayBillInformationResult.getRoomName());
        assertEquals(1, actualPayBillInformationResult.getRoomLimitPeople().intValue());
        assertEquals(123L, actualPayBillInformationResult.getRoomId().longValue());
        assertEquals(1, actualPayBillInformationResult.getRoomFloor().intValue());
        assertEquals(generalServiceDTOList, actualPayBillInformationResult.getListGeneralService());
        assertEquals(123L, actualPayBillInformationResult.getGroupId().longValue());
        assertEquals(123L, actualPayBillInformationResult.getGroupContractId().longValue());
        assertEquals(1, actualPayBillInformationResult.getContractPaymentCycle().intValue());
        verify(contractService).contract((Long) any());
        verify(roomService).room((Long) any());
        verify(servicesService).listGeneralServiceByGroupId((Long) any());
        verify(renterService).listRenter((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#groupBillCheck(Long)}
     */
    @Test
    void testGroupBillCheck() {
        when(recurringBillRepository.findAllByGroupContractIdAndIsPaidIsFalseOrIsDebtIsTrue((Long) any()))
                .thenReturn(new ArrayList<>());
        assertFalse(billServiceImpl.groupBillCheck(123L));
        verify(recurringBillRepository).findAllByGroupContractIdAndIsPaidIsFalseOrIsDebtIsTrue((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#groupBillCheck(Long)}
     */
    @Test
    void testGroupBillCheck2() {
        RecurringBill recurringBill = new RecurringBill();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setBillCreatedTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setBillType("Bill Type");
        recurringBill.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setCreatedBy(1L);
        recurringBill.setDescription("The characteristics of someone or something");
        recurringBill.setGroupContractId(123L);
        recurringBill.setGroupId(123L);
        recurringBill.setId(123L);
        recurringBill.setIsDebt(true);
        recurringBill.setIsInBillCircle(true);
        recurringBill.setIsPaid(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setModifiedBy(1L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setPaymentTerm(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setRoomBillId(123L);
        recurringBill.setRoomId(123L);
        recurringBill.setRoomName("Room Name");
        recurringBill.setServiceBillId(123L);
        recurringBill.setTotalMoney(10.0d);

        ArrayList<RecurringBill> recurringBillList = new ArrayList<>();
        recurringBillList.add(recurringBill);
        when(recurringBillRepository.findAllByGroupContractIdAndIsPaidIsFalseOrIsDebtIsTrue((Long) any()))
                .thenReturn(recurringBillList);
        assertTrue(billServiceImpl.groupBillCheck(123L));
        verify(recurringBillRepository).findAllByGroupContractIdAndIsPaidIsFalseOrIsDebtIsTrue((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#roomBillCheck(Long)}
     */
    @Test
    void testRoomBillCheck() {
        when(recurringBillRepository.findAllByRoomIdAndIsPaidIsFalseOrIsDebtIsTrue((Long) any()))
                .thenReturn(new ArrayList<>());
        assertFalse(billServiceImpl.roomBillCheck(123L));
        verify(recurringBillRepository).findAllByRoomIdAndIsPaidIsFalseOrIsDebtIsTrue((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#roomBillCheck(Long)}
     */
    @Test
    void testRoomBillCheck2() {
        RecurringBill recurringBill = new RecurringBill();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setBillCreatedTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setBillType("Bill Type");
        recurringBill.setContractId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setCreatedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setCreatedBy(1L);
        recurringBill.setDescription("The characteristics of someone or something");
        recurringBill.setGroupContractId(123L);
        recurringBill.setGroupId(123L);
        recurringBill.setId(123L);
        recurringBill.setIsDebt(true);
        recurringBill.setIsInBillCircle(true);
        recurringBill.setIsPaid(true);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setModifiedBy(1L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        recurringBill.setPaymentTerm(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        recurringBill.setRoomBillId(123L);
        recurringBill.setRoomId(123L);
        recurringBill.setRoomName("Room Name");
        recurringBill.setServiceBillId(123L);
        recurringBill.setTotalMoney(10.0d);

        ArrayList<RecurringBill> recurringBillList = new ArrayList<>();
        recurringBillList.add(recurringBill);
        when(recurringBillRepository.findAllByRoomIdAndIsPaidIsFalseOrIsDebtIsTrue((Long) any()))
                .thenReturn(recurringBillList);
        assertTrue(billServiceImpl.roomBillCheck(123L));
        verify(recurringBillRepository).findAllByRoomIdAndIsPaidIsFalseOrIsDebtIsTrue((Long) any());
    }

    /**
     * Method under test: {@link BillServiceImpl#listRecurringBillByGroupId(Long)}
     */
    @Test
    void testListRecurringBillByGroupId() {
        ArrayList<RecurringBill> recurringBillList = new ArrayList<>();
        when(recurringBillRepository.findAllByGroupContractId((Long) any())).thenReturn(recurringBillList);
        List<RecurringBill> actualListRecurringBillByGroupIdResult = billServiceImpl.listRecurringBillByGroupId(123L);
        assertSame(recurringBillList, actualListRecurringBillByGroupIdResult);
        assertTrue(actualListRecurringBillByGroupIdResult.isEmpty());
        verify(recurringBillRepository).findAllByGroupContractId((Long) any());
    }
}

