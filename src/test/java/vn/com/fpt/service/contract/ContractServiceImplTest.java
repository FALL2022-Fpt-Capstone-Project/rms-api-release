package vn.com.fpt.service.contract;

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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.entity.Identity;
import vn.com.fpt.entity.RackRenters;
import vn.com.fpt.entity.Renters;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.entity.authentication.Account;
import vn.com.fpt.repositories.AddressRepository;
import vn.com.fpt.repositories.ContractRepository;
import vn.com.fpt.repositories.MoneySourceRepository;
import vn.com.fpt.repositories.RackRenterRepository;
import vn.com.fpt.repositories.RenterRepository;
import vn.com.fpt.repositories.RoomBillRepository;
import vn.com.fpt.repositories.RoomsRepository;
import vn.com.fpt.requests.EndGroupContractRequest;
import vn.com.fpt.requests.EndRoomContractRequest;
import vn.com.fpt.requests.GroupContractRequest;
import vn.com.fpt.requests.HandOverGeneralServiceRequest;
import vn.com.fpt.requests.RenterRequest;
import vn.com.fpt.requests.RoomContractRequest;
import vn.com.fpt.responses.GroupContractedResponse;
import vn.com.fpt.responses.RentersResponse;
import vn.com.fpt.service.TableLogComponent;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.renter.RenterService;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.services.ServicesService;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {ContractServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ContractServiceImplTest {
    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private AssetService assetService;

    @MockBean
    private ContractRepository contractRepository;

    @Autowired
    private ContractServiceImpl contractServiceImpl;

    @MockBean
    private GroupService groupService;

    @MockBean
    private MoneySourceRepository moneySourceRepository;

    @MockBean
    private RackRenterRepository rackRenterRepository;

    @MockBean
    private RenterRepository renterRepository;

    @MockBean
    private RenterService renterService;

    @MockBean
    private RoomBillRepository roomBillRepository;

    @MockBean
    private RoomService roomService;

    @MockBean
    private RoomsRepository roomsRepository;

    @MockBean
    private ServicesService servicesService;

    @MockBean
    private TableLogComponent tableLogComponent;


}

