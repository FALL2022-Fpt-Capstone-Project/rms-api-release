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


}