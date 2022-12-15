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

    /**
     * Method under test: {@link ContractServiceImpl#addContract(GroupContractRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddContract() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.length()" because "text" is null
        //       at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1474)
        //       at java.text.DateFormat.parse(DateFormat.java:397)
        //       at vn.com.fpt.common.utils.DateUtils.parse(DateUtils.java:52)
        //       at vn.com.fpt.entity.Contracts.of(Contracts.java:105)
        //       at vn.com.fpt.entity.Contracts.addForLease(Contracts.java:112)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.addContract(ContractServiceImpl.java:234)
        //   See https://diff.blue/R013 to resolve this issue.

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

        Account account4 = new Account();
        account4.setAddress(new Address());
        LocalDateTime atStartOfDayResult38 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account4.setCreatedAt(Date.from(atStartOfDayResult38.atZone(ZoneId.of("UTC")).toInstant()));
        account4.setCreatedBy(1L);
        account4.setDeactivate(true);
        account4.setFullName("Dr Jane Doe");
        account4.setGender(true);
        account4.setId(123L);
        LocalDateTime atStartOfDayResult39 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account4.setModifiedAt(Date.from(atStartOfDayResult39.atZone(ZoneId.of("UTC")).toInstant()));
        account4.setModifiedBy(1L);
        account4.setOwner(true);
        account4.setPassword("iloveyou");
        account4.setPhoneNumber("4105551212");
        account4.setRoles(new HashSet<>());
        account4.setUserName("janedoe");

        RackRenters rackRenters5 = new RackRenters();
        rackRenters5.setAddress(new Address());
        LocalDateTime atStartOfDayResult40 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters5.setCreatedAt(Date.from(atStartOfDayResult40.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters5.setCreatedBy(1L);
        rackRenters5.setEmail("jane.doe@example.org");
        rackRenters5.setGender(true);
        rackRenters5.setId(123L);
        rackRenters5.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult41 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters5.setModifiedAt(Date.from(atStartOfDayResult41.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters5.setModifiedBy(1L);
        rackRenters5.setNote("Note");
        rackRenters5.setPhoneNumber("4105551212");
        rackRenters5.setRackRenterFullName("Dr Jane Doe");

        Renters renters5 = new Renters();
        renters5.setAddress(new Address());
        LocalDateTime atStartOfDayResult42 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters5.setCreatedAt(Date.from(atStartOfDayResult42.atZone(ZoneId.of("UTC")).toInstant()));
        renters5.setCreatedBy(1L);
        renters5.setEmail("jane.doe@example.org");
        renters5.setGender(true);
        renters5.setId(123L);
        renters5.setIdentityNumber("42");
        renters5.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult43 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters5.setModifiedAt(Date.from(atStartOfDayResult43.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult44 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setCreatedAt(Date.from(atStartOfDayResult44.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setCreatedBy(1L);
        address4.setId(123L);
        LocalDateTime atStartOfDayResult45 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address4.setModifiedAt(Date.from(atStartOfDayResult45.atZone(ZoneId.of("UTC")).toInstant()));
        address4.setModifiedBy(1L);
        address4.setRackRenters(rackRenters5);
        address4.setRenters(renters5);

        Account account5 = new Account();
        account5.setAddress(address4);
        LocalDateTime atStartOfDayResult46 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account5.setCreatedAt(Date.from(atStartOfDayResult46.atZone(ZoneId.of("UTC")).toInstant()));
        account5.setCreatedBy(1L);
        account5.setDeactivate(true);
        account5.setFullName("Dr Jane Doe");
        account5.setGender(true);
        account5.setId(123L);
        LocalDateTime atStartOfDayResult47 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account5.setModifiedAt(Date.from(atStartOfDayResult47.atZone(ZoneId.of("UTC")).toInstant()));
        account5.setModifiedBy(1L);
        account5.setOwner(true);
        account5.setPassword("iloveyou");
        account5.setPhoneNumber("4105551212");
        account5.setRoles(new HashSet<>());
        account5.setUserName("janedoe");

        Account account6 = new Account();
        account6.setAddress(new Address());
        LocalDateTime atStartOfDayResult48 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account6.setCreatedAt(Date.from(atStartOfDayResult48.atZone(ZoneId.of("UTC")).toInstant()));
        account6.setCreatedBy(1L);
        account6.setDeactivate(true);
        account6.setFullName("Dr Jane Doe");
        account6.setGender(true);
        account6.setId(123L);
        LocalDateTime atStartOfDayResult49 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account6.setModifiedAt(Date.from(atStartOfDayResult49.atZone(ZoneId.of("UTC")).toInstant()));
        account6.setModifiedBy(1L);
        account6.setOwner(true);
        account6.setPassword("iloveyou");
        account6.setPhoneNumber("4105551212");
        account6.setRoles(new HashSet<>());
        account6.setUserName("janedoe");

        RackRenters rackRenters6 = new RackRenters();
        rackRenters6.setAddress(new Address());
        LocalDateTime atStartOfDayResult50 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters6.setCreatedAt(Date.from(atStartOfDayResult50.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters6.setCreatedBy(1L);
        rackRenters6.setEmail("jane.doe@example.org");
        rackRenters6.setGender(true);
        rackRenters6.setId(123L);
        rackRenters6.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult51 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters6.setModifiedAt(Date.from(atStartOfDayResult51.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters6.setModifiedBy(1L);
        rackRenters6.setNote("Note");
        rackRenters6.setPhoneNumber("4105551212");
        rackRenters6.setRackRenterFullName("Dr Jane Doe");

        Renters renters6 = new Renters();
        renters6.setAddress(new Address());
        LocalDateTime atStartOfDayResult52 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters6.setCreatedAt(Date.from(atStartOfDayResult52.atZone(ZoneId.of("UTC")).toInstant()));
        renters6.setCreatedBy(1L);
        renters6.setEmail("jane.doe@example.org");
        renters6.setGender(true);
        renters6.setId(123L);
        renters6.setIdentityNumber("42");
        renters6.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult53 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters6.setModifiedAt(Date.from(atStartOfDayResult53.atZone(ZoneId.of("UTC")).toInstant()));
        renters6.setModifiedBy(1L);
        renters6.setPhoneNumber("4105551212");
        renters6.setRenterFullName("Dr Jane Doe");
        renters6.setRenterIdentity(new Identity());
        renters6.setRepresent(true);
        renters6.setRoomId(123L);

        Address address5 = new Address();
        address5.setAccount(account6);
        address5.setAddressCity("42 Main St");
        address5.setAddressDistrict("42 Main St");
        address5.setAddressMoreDetails("42 Main St");
        address5.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult54 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address5.setCreatedAt(Date.from(atStartOfDayResult54.atZone(ZoneId.of("UTC")).toInstant()));
        address5.setCreatedBy(1L);
        address5.setId(123L);
        LocalDateTime atStartOfDayResult55 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address5.setModifiedAt(Date.from(atStartOfDayResult55.atZone(ZoneId.of("UTC")).toInstant()));
        address5.setModifiedBy(1L);
        address5.setRackRenters(rackRenters6);
        address5.setRenters(renters6);

        RackRenters rackRenters7 = new RackRenters();
        rackRenters7.setAddress(address5);
        LocalDateTime atStartOfDayResult56 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters7.setCreatedAt(Date.from(atStartOfDayResult56.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters7.setCreatedBy(1L);
        rackRenters7.setEmail("jane.doe@example.org");
        rackRenters7.setGender(true);
        rackRenters7.setId(123L);
        rackRenters7.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult57 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters7.setModifiedAt(Date.from(atStartOfDayResult57.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters7.setModifiedBy(1L);
        rackRenters7.setNote("Note");
        rackRenters7.setPhoneNumber("4105551212");
        rackRenters7.setRackRenterFullName("Dr Jane Doe");

        Account account7 = new Account();
        account7.setAddress(new Address());
        LocalDateTime atStartOfDayResult58 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account7.setCreatedAt(Date.from(atStartOfDayResult58.atZone(ZoneId.of("UTC")).toInstant()));
        account7.setCreatedBy(1L);
        account7.setDeactivate(true);
        account7.setFullName("Dr Jane Doe");
        account7.setGender(true);
        account7.setId(123L);
        LocalDateTime atStartOfDayResult59 = LocalDate.of(1970, 1, 1).atStartOfDay();
        account7.setModifiedAt(Date.from(atStartOfDayResult59.atZone(ZoneId.of("UTC")).toInstant()));
        account7.setModifiedBy(1L);
        account7.setOwner(true);
        account7.setPassword("iloveyou");
        account7.setPhoneNumber("4105551212");
        account7.setRoles(new HashSet<>());
        account7.setUserName("janedoe");

        RackRenters rackRenters8 = new RackRenters();
        rackRenters8.setAddress(new Address());
        LocalDateTime atStartOfDayResult60 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters8.setCreatedAt(Date.from(atStartOfDayResult60.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters8.setCreatedBy(1L);
        rackRenters8.setEmail("jane.doe@example.org");
        rackRenters8.setGender(true);
        rackRenters8.setId(123L);
        rackRenters8.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult61 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters8.setModifiedAt(Date.from(atStartOfDayResult61.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters8.setModifiedBy(1L);
        rackRenters8.setNote("Note");
        rackRenters8.setPhoneNumber("4105551212");
        rackRenters8.setRackRenterFullName("Dr Jane Doe");

        Renters renters7 = new Renters();
        renters7.setAddress(new Address());
        LocalDateTime atStartOfDayResult62 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters7.setCreatedAt(Date.from(atStartOfDayResult62.atZone(ZoneId.of("UTC")).toInstant()));
        renters7.setCreatedBy(1L);
        renters7.setEmail("jane.doe@example.org");
        renters7.setGender(true);
        renters7.setId(123L);
        renters7.setIdentityNumber("42");
        renters7.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult63 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters7.setModifiedAt(Date.from(atStartOfDayResult63.atZone(ZoneId.of("UTC")).toInstant()));
        renters7.setModifiedBy(1L);
        renters7.setPhoneNumber("4105551212");
        renters7.setRenterFullName("Dr Jane Doe");
        renters7.setRenterIdentity(new Identity());
        renters7.setRepresent(true);
        renters7.setRoomId(123L);

        Address address6 = new Address();
        address6.setAccount(account7);
        address6.setAddressCity("42 Main St");
        address6.setAddressDistrict("42 Main St");
        address6.setAddressMoreDetails("42 Main St");
        address6.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult64 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address6.setCreatedAt(Date.from(atStartOfDayResult64.atZone(ZoneId.of("UTC")).toInstant()));
        address6.setCreatedBy(1L);
        address6.setId(123L);
        LocalDateTime atStartOfDayResult65 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address6.setModifiedAt(Date.from(atStartOfDayResult65.atZone(ZoneId.of("UTC")).toInstant()));
        address6.setModifiedBy(1L);
        address6.setRackRenters(rackRenters8);
        address6.setRenters(renters7);

        Renters renters8 = new Renters();
        renters8.setAddress(new Address());
        LocalDateTime atStartOfDayResult66 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters8.setCreatedAt(Date.from(atStartOfDayResult66.atZone(ZoneId.of("UTC")).toInstant()));
        renters8.setCreatedBy(1L);
        renters8.setEmail("jane.doe@example.org");
        renters8.setGender(true);
        renters8.setId(123L);
        renters8.setIdentityNumber("42");
        renters8.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult67 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters8.setModifiedAt(Date.from(atStartOfDayResult67.atZone(ZoneId.of("UTC")).toInstant()));
        renters8.setModifiedBy(1L);
        renters8.setPhoneNumber("4105551212");
        renters8.setRenterFullName("Dr Jane Doe");
        renters8.setRenterIdentity(new Identity());
        renters8.setRepresent(true);
        renters8.setRoomId(123L);

        Identity identity1 = new Identity();
        LocalDateTime atStartOfDayResult68 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setCreatedAt(Date.from(atStartOfDayResult68.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setCreatedBy(1L);
        identity1.setId(123L);
        identity1.setIdentityBackImg("Identity Back Img");
        identity1.setIdentityFrontImg("Identity Front Img");
        LocalDateTime atStartOfDayResult69 = LocalDate.of(1970, 1, 1).atStartOfDay();
        identity1.setModifiedAt(Date.from(atStartOfDayResult69.atZone(ZoneId.of("UTC")).toInstant()));
        identity1.setModifiedBy(1L);
        identity1.setRenters(renters8);

        Renters renters9 = new Renters();
        renters9.setAddress(address6);
        LocalDateTime atStartOfDayResult70 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters9.setCreatedAt(Date.from(atStartOfDayResult70.atZone(ZoneId.of("UTC")).toInstant()));
        renters9.setCreatedBy(1L);
        renters9.setEmail("jane.doe@example.org");
        renters9.setGender(true);
        renters9.setId(123L);
        renters9.setIdentityNumber("42");
        renters9.setLicensePlates("License Plates");
        LocalDateTime atStartOfDayResult71 = LocalDate.of(1970, 1, 1).atStartOfDay();
        renters9.setModifiedAt(Date.from(atStartOfDayResult71.atZone(ZoneId.of("UTC")).toInstant()));
        renters9.setModifiedBy(1L);
        renters9.setPhoneNumber("4105551212");
        renters9.setRenterFullName("Dr Jane Doe");
        renters9.setRenterIdentity(identity1);
        renters9.setRepresent(true);
        renters9.setRoomId(123L);

        Address address7 = new Address();
        address7.setAccount(account5);
        address7.setAddressCity("42 Main St");
        address7.setAddressDistrict("42 Main St");
        address7.setAddressMoreDetails("42 Main St");
        address7.setAddressWards("42 Main St");
        LocalDateTime atStartOfDayResult72 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address7.setCreatedAt(Date.from(atStartOfDayResult72.atZone(ZoneId.of("UTC")).toInstant()));
        address7.setCreatedBy(1L);
        address7.setId(123L);
        LocalDateTime atStartOfDayResult73 = LocalDate.of(1970, 1, 1).atStartOfDay();
        address7.setModifiedAt(Date.from(atStartOfDayResult73.atZone(ZoneId.of("UTC")).toInstant()));
        address7.setModifiedBy(1L);
        address7.setRackRenters(rackRenters7);
        address7.setRenters(renters9);

        RackRenters rackRenters9 = new RackRenters();
        rackRenters9.setAddress(address7);
        LocalDateTime atStartOfDayResult74 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters9.setCreatedAt(Date.from(atStartOfDayResult74.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters9.setCreatedBy(1L);
        rackRenters9.setEmail("jane.doe@example.org");
        rackRenters9.setGender(true);
        rackRenters9.setId(123L);
        rackRenters9.setIdentityNumber("42");
        LocalDateTime atStartOfDayResult75 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rackRenters9.setModifiedAt(Date.from(atStartOfDayResult75.atZone(ZoneId.of("UTC")).toInstant()));
        rackRenters9.setModifiedBy(1L);
        rackRenters9.setNote("Note");
        rackRenters9.setPhoneNumber("4105551212");
        rackRenters9.setRackRenterFullName("Dr Jane Doe");
        when(renterService.addRackRenter((String) any(), (Boolean) any(), (String) any(), (String) any(), (String) any(),
                (Address) any(), (String) any(), (Long) any())).thenReturn(rackRenters4);
        when(renterService.findRackRenter((String) any())).thenReturn(rackRenters9);
        contractServiceImpl.addContract(new GroupContractRequest(), 1L);
    }

    /**
     * Method under test: {@link ContractServiceImpl#addContract(RoomContractRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddContract2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.length()" because "text" is null
        //       at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1474)
        //       at java.text.DateFormat.parse(DateFormat.java:397)
        //       at vn.com.fpt.common.utils.DateUtils.parse(DateUtils.java:52)
        //       at vn.com.fpt.entity.Contracts.of(Contracts.java:91)
        //       at vn.com.fpt.entity.Contracts.addForSubLease(Contracts.java:121)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.addContract(ContractServiceImpl.java:96)
        //   See https://diff.blue/R013 to resolve this issue.

        contractServiceImpl.addContract(new RoomContractRequest(), 1L);
    }

    /**
     * Method under test: {@link ContractServiceImpl#addContract(RoomContractRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddContract3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   vn.com.fpt.common.BusinessException: yyyy-MM-dd
        //       at jdk.proxy4.$Proxy91.findRenter(null)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.addContract(ContractServiceImpl.java:115)
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
        when(roomService.updateRoom((Rooms) any())).thenReturn(rooms2);
        when(roomService.emptyRoom((Long) any())).thenReturn(rooms1);
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(renterService.addRenter((Renters) any())).thenThrow(new BusinessException("yyyy-MM-dd"));
        when(renterService.findRenter((String) any())).thenThrow(new BusinessException("yyyy-MM-dd"));
        ArrayList<HandOverGeneralServiceRequest> listGeneralService = new ArrayList<>();
        contractServiceImpl.addContract(new RoomContractRequest("yyyy-MM-dd", 10.0d, 10.0d, 1, 1, "2020-03-01",
                "2020-03-01", "yyyy-MM-dd", 1, 123L, "yyyy-MM-dd", "4105551212", "jane.doe@example.org", true, "yyyy-MM-dd",
                "yyyy-MM-dd", "42 Main St", 123L, 1, 123L, listGeneralService, new ArrayList<>()), 1L);
    }

    /**
     * Method under test: {@link ContractServiceImpl#updateContract(Long, GroupContractRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateContract() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   vn.com.fpt.common.BusinessException: Msg
        //       at jdk.proxy4.$Proxy91.rackRenter(null)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.updateContract(ContractServiceImpl.java:264)
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
        Optional<Contracts> ofResult = Optional.of(contracts);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);
        when(renterService.rackRenter((Long) any())).thenThrow(new BusinessException("Msg"));
        contractServiceImpl.updateContract(123L, new GroupContractRequest(), 1L);
    }

    /**
     * Method under test: {@link ContractServiceImpl#updateContract(Long, RoomContractRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateContract2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "String.length()" because "text" is null
        //       at java.text.SimpleDateFormat.parse(SimpleDateFormat.java:1474)
        //       at java.text.DateFormat.parse(DateFormat.java:397)
        //       at vn.com.fpt.common.utils.DateUtils.parse(DateUtils.java:52)
        //       at vn.com.fpt.entity.Contracts.of(Contracts.java:91)
        //       at vn.com.fpt.entity.Contracts.modifyForSublease(Contracts.java:130)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.updateContract(ContractServiceImpl.java:292)
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
        Optional<Contracts> ofResult = Optional.of(contracts);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);
        contractServiceImpl.updateContract(123L, new RoomContractRequest(), 1L);
    }

    /**
     * Method under test: {@link ContractServiceImpl#updateContract(Long, RoomContractRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateContract3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:143)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.updateContract(ContractServiceImpl.java:291)
        //   See https://diff.blue/R013 to resolve this issue.

        when(contractRepository.findById((Long) any())).thenReturn(Optional.empty());
        contractServiceImpl.updateContract(123L, new RoomContractRequest(), 1L);
    }

    /**
     * Method under test: {@link ContractServiceImpl#updateContract(Long, RoomContractRequest, Long)}
     */
    @Test
    void testUpdateContract4() {
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
        Optional<Contracts> ofResult = Optional.of(contracts);

        Contracts contracts1 = new Contracts();
        contracts1.setAddressId(123L);
        contracts1.setContractBillCycle(1);
        contracts1.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractEndDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractIsDisable(true);
        contracts1.setContractName("Contract Name");
        contracts1.setContractPaymentCycle(1);
        contracts1.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractStartDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractTerm(1);
        contracts1.setContractType(1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setCreatedBy(1L);
        contracts1.setGroupId(123L);
        contracts1.setId(123L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setModifiedBy(1L);
        contracts1.setNote("Note");
        contracts1.setRackRenters(1L);
        contracts1.setRenters(1L);
        contracts1.setRoomId(123L);
        when(contractRepository.save((Contracts) any())).thenReturn(contracts1);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        when(roomService.updateRoom((Rooms) any())).thenReturn(rooms1);
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(renterService.updateRenter((Long) any(), (RenterRequest) any(), (Long) any()))
                .thenReturn(new RentersResponse());
        ArrayList<HandOverGeneralServiceRequest> listGeneralService = new ArrayList<>();
        RoomContractRequest roomContractRequest = new RoomContractRequest("yyyy-MM-dd", 10.0d, 10.0d, 1, 1, "2020-03-01",
                "2020-03-01", "yyyy-MM-dd", 1, 123L, "yyyy-MM-dd", "4105551212", "jane.doe@example.org", true, "yyyy-MM-dd",
                "yyyy-MM-dd", "42 Main St", 123L, 1, 123L, listGeneralService, new ArrayList<>());

        assertSame(roomContractRequest, contractServiceImpl.updateContract(123L, roomContractRequest, 1L));
        verify(contractRepository).save((Contracts) any());
        verify(contractRepository).findById((Long) any());
        verify(roomService, atLeast(1)).room((Long) any());
        verify(roomService).updateRoom((Rooms) any());
        verify(renterService).updateRenter((Long) any(), (RenterRequest) any(), (Long) any());
    }

    /**
     * Method under test: {@link ContractServiceImpl#updateContract(Long, RoomContractRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateContract5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   vn.com.fpt.common.BusinessException: yyyy-MM-dd
        //       at jdk.proxy4.$Proxy91.updateRenter(null)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.updateContract(ContractServiceImpl.java:323)
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
        Optional<Contracts> ofResult = Optional.of(contracts);

        Contracts contracts1 = new Contracts();
        contracts1.setAddressId(123L);
        contracts1.setContractBillCycle(1);
        contracts1.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractEndDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractIsDisable(true);
        contracts1.setContractName("Contract Name");
        contracts1.setContractPaymentCycle(1);
        contracts1.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractStartDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractTerm(1);
        contracts1.setContractType(1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setCreatedBy(1L);
        contracts1.setGroupId(123L);
        contracts1.setId(123L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setModifiedBy(1L);
        contracts1.setNote("Note");
        contracts1.setRackRenters(1L);
        contracts1.setRenters(1L);
        contracts1.setRoomId(123L);
        when(contractRepository.save((Contracts) any())).thenReturn(contracts1);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        when(roomService.updateRoom((Rooms) any())).thenReturn(rooms1);
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(renterService.updateRenter((Long) any(), (RenterRequest) any(), (Long) any()))
                .thenThrow(new BusinessException("yyyy-MM-dd"));
        ArrayList<HandOverGeneralServiceRequest> listGeneralService = new ArrayList<>();
        contractServiceImpl.updateContract(123L,
                new RoomContractRequest("yyyy-MM-dd", 10.0d, 10.0d, 1, 1, "2020-03-01", "2020-03-01", "yyyy-MM-dd", 1, 123L,
                        "yyyy-MM-dd", "4105551212", "jane.doe@example.org", true, "yyyy-MM-dd", "yyyy-MM-dd", "42 Main St", 123L,
                        1, 123L, listGeneralService, new ArrayList<>()),
                1L);
    }

    /**
     * Method under test: {@link ContractServiceImpl#listGroupContract(Long)}
     */
    @Test
    void testListGroupContract() {
        ArrayList<Contracts> contractsList = new ArrayList<>();
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenReturn(contractsList);
        List<Contracts> actualListGroupContractResult = contractServiceImpl.listGroupContract(123L);
        assertSame(contractsList, actualListGroupContractResult);
        assertTrue(actualListGroupContractResult.isEmpty());
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
    }

    /**
     * Method under test: {@link ContractServiceImpl#listGroupContract(Long)}
     */
    @Test
    void testListGroupContract2() {
        when(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any()))
                .thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> contractServiceImpl.listGroupContract(123L));
        verify(contractRepository).findByGroupIdAndContractTypeAndContractIsDisableIsFalse((Long) any(), (Integer) any());
    }

    @Test
    void testContract() {
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
        Optional<Contracts> ofResult = Optional.of(contracts);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(contracts, contractServiceImpl.contract(123L));
        verify(contractRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ContractServiceImpl#contract(Long)}
     */
    @Test
    void testContract2() {
        when(contractRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> contractServiceImpl.contract(123L));
        verify(contractRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ContractServiceImpl#contract(Long)}
     */
    @Test
    void testContract3() {
        when(contractRepository.findById((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> contractServiceImpl.contract(123L));
        verify(contractRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ContractServiceImpl#roomContract(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRoomContract() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:143)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.roomContract(ContractServiceImpl.java:374)
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
        Optional<Contracts> ofResult = Optional.of(contracts);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);

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
        when(renterService.listMember((Long) any())).thenReturn(new ArrayList<>());
        when(renterService.renter((Long) any())).thenReturn(new RentersResponse());
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        contractServiceImpl.roomContract(123L);
    }

    /**
     * Method under test: {@link ContractServiceImpl#roomContract(Long)}
     */
    @Test
    void testRoomContract2() {
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
        Optional<Contracts> ofResult = Optional.of(contracts);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);

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
        when(renterService.listMember((Long) any())).thenReturn(new ArrayList<>());
        when(renterService.renter((Long) any())).thenReturn(new RentersResponse());
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> contractServiceImpl.roomContract(123L));
        verify(contractRepository).findById((Long) any());
        verify(roomService).room((Long) any());
        verify(renterService).listMember((Long) any());
        verify(renterService).renter((Long) any());
        verify(servicesService).listGeneralServiceByGroupId((Long) any());
    }

    /**
     * Method under test: {@link ContractServiceImpl#roomContract(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRoomContract3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:143)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.roomContract(ContractServiceImpl.java:374)
        //   See https://diff.blue/R013 to resolve this issue.

        Contracts contracts = mock(Contracts.class);
        when(contracts.getGroupId()).thenReturn(123L);
        when(contracts.getRenters()).thenReturn(1L);
        when(contracts.getRoomId()).thenReturn(123L);
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
        Optional<Contracts> ofResult = Optional.of(contracts);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);

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
        when(renterService.listMember((Long) any())).thenReturn(new ArrayList<>());
        when(renterService.renter((Long) any())).thenReturn(new RentersResponse());
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        contractServiceImpl.roomContract(123L);
    }

    /**
     * Method under test: {@link ContractServiceImpl#roomContract(Long)}
     */
    @Test
    void testRoomContract4() {
        when(contractRepository.findById((Long) any())).thenReturn(Optional.empty());
        Contracts contracts = mock(Contracts.class);
        when(contracts.getGroupId()).thenReturn(123L);
        when(contracts.getRenters()).thenReturn(1L);
        when(contracts.getRoomId()).thenReturn(123L);
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
        when(renterService.listMember((Long) any())).thenReturn(new ArrayList<>());
        when(renterService.renter((Long) any())).thenReturn(new RentersResponse());
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        assertThrows(BusinessException.class, () -> contractServiceImpl.roomContract(123L));
        verify(contractRepository).findById((Long) any());
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
    }

    /**
     * Method under test: {@link ContractServiceImpl#listRoomContract(Long, String, String, String, Boolean, String, String, Integer, Long, List)}
     */
    @Test
    void testListRoomContract() {
        when(contractRepository.findAll((Specification<Contracts>) any(), (Sort) any())).thenReturn(new ArrayList<>());
        when(renterRepository.findAll((Specification<Renters>) any())).thenReturn(new ArrayList<>());
        assertTrue(contractServiceImpl
                .listRoomContract(123L, "4105551212", "Identity", "Renter Name", true, "2020-03-01", "2020-03-01", 1, 1L,
                        new ArrayList<>())
                .isEmpty());
        verify(contractRepository).findAll((Specification<Contracts>) any(), (Sort) any());
        verify(renterRepository).findAll((Specification<Renters>) any());
    }

    /**
     * Method under test: {@link ContractServiceImpl#listRoomContract(Long, String, String, String, Boolean, String, String, Integer, Long, List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListRoomContract2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   vn.com.fpt.common.BusinessException: phoneNumber
        //       at jdk.proxy4.$Proxy93.findAll(null)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.listRoomContract(ContractServiceImpl.java:439)
        //   See https://diff.blue/R013 to resolve this issue.

        when(contractRepository.findAll((Specification<Contracts>) any(), (Sort) any())).thenReturn(new ArrayList<>());
        when(renterRepository.findAll((Specification<Renters>) any())).thenThrow(new BusinessException("phoneNumber"));
        contractServiceImpl.listRoomContract(123L, "4105551212", "Identity", "Renter Name", true, "2020-03-01",
                "2020-03-01", 1, 1L, new ArrayList<>());
    }

    /**
     * Method under test: {@link ContractServiceImpl#listRoomContract(Long, String, String, String, Boolean, String, String, Integer, Long, List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListRoomContract3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:143)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.lambda$listRoomContract$9(ContractServiceImpl.java:496)
        //       at java.util.ArrayList.forEach(ArrayList.java:1511)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.listRoomContract(ContractServiceImpl.java:492)
        //   See https://diff.blue/R013 to resolve this issue.

        Contracts contracts = new Contracts();
        contracts.setAddressId(123L);
        contracts.setContractBillCycle(1);
        contracts.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractEndDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractIsDisable(true);
        contracts.setContractName("phoneNumber");
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
        contracts.setNote("phoneNumber");
        contracts.setRackRenters(1L);
        contracts.setRenters(1L);
        contracts.setRoomId(123L);

        ArrayList<Contracts> contractsList = new ArrayList<>();
        contractsList.add(contracts);
        when(contractRepository.findAll((Specification<Contracts>) any(), (Sort) any())).thenReturn(contractsList);

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
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(renterRepository.findAll((Specification<Renters>) any())).thenReturn(new ArrayList<>());
        contractServiceImpl.listRoomContract(123L, "4105551212", "Identity", "Renter Name", true, "2020-03-01",
                "2020-03-01", 1, 1L, new ArrayList<>());
    }

    /**
     * Method under test: {@link ContractServiceImpl#listRoomContract(Long, String, String, String, Boolean, String, String, Integer, Long, List)}
     */
    @Test
    void testListRoomContract4() {
        Contracts contracts = new Contracts();
        contracts.setAddressId(123L);
        contracts.setContractBillCycle(1);
        contracts.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractEndDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractIsDisable(true);
        contracts.setContractName("phoneNumber");
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
        contracts.setNote("phoneNumber");
        contracts.setRackRenters(1L);
        contracts.setRenters(1L);
        contracts.setRoomId(123L);

        ArrayList<Contracts> contractsList = new ArrayList<>();
        contractsList.add(contracts);
        when(contractRepository.findAll((Specification<Contracts>) any(), (Sort) any())).thenReturn(contractsList);

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
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenThrow(new BusinessException("phoneNumber"));
        when(renterRepository.findAll((Specification<Renters>) any())).thenReturn(new ArrayList<>());
        assertThrows(BusinessException.class, () -> contractServiceImpl.listRoomContract(123L, "4105551212", "Identity",
                "Renter Name", true, "2020-03-01", "2020-03-01", 1, 1L, new ArrayList<>()));
        verify(contractRepository).findAll((Specification<Contracts>) any(), (Sort) any());
        verify(roomService).room((Long) any());
        verify(servicesService).listGeneralServiceByGroupId((Long) any());
        verify(renterRepository).findAll((Specification<Renters>) any());
    }

    /**
     * Method under test: {@link ContractServiceImpl#listRoomContract(Long, String, String, String, Boolean, String, String, Integer, Long, List)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListRoomContract5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:143)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.lambda$listRoomContract$9(ContractServiceImpl.java:496)
        //       at java.util.ArrayList.forEach(ArrayList.java:1511)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.listRoomContract(ContractServiceImpl.java:492)
        //   See https://diff.blue/R013 to resolve this issue.

        Contracts contracts = mock(Contracts.class);
        when(contracts.getGroupId()).thenReturn(123L);
        when(contracts.getRoomId()).thenReturn(123L);
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
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts.setContractEndDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        contracts.setContractIsDisable(true);
        contracts.setContractName("phoneNumber");
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
        contracts.setNote("phoneNumber");
        contracts.setRackRenters(1L);
        contracts.setRenters(1L);
        contracts.setRoomId(123L);

        ArrayList<Contracts> contractsList = new ArrayList<>();
        contractsList.add(contracts);
        when(contractRepository.findAll((Specification<Contracts>) any(), (Sort) any())).thenReturn(contractsList);

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
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(renterRepository.findAll((Specification<Renters>) any())).thenReturn(new ArrayList<>());
        contractServiceImpl.listRoomContract(123L, "4105551212", "Identity", "Renter Name", true, "2020-03-01",
                "2020-03-01", 1, 1L, new ArrayList<>());
    }

    /**
     * Method under test: {@link ContractServiceImpl#groupContract(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGroupContract() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   vn.com.fpt.common.BusinessException: Msg
        //       at jdk.proxy4.$Proxy92.group(null)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.lambda$listGroupContract$11(ContractServiceImpl.java:571)
        //       at java.lang.Iterable.forEach(Iterable.java:75)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.listGroupContract(ContractServiceImpl.java:568)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.groupContract(ContractServiceImpl.java:627)
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
        Optional<Contracts> ofResult = Optional.of(contracts);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        when(renterService.rackRenter((Long) any())).thenThrow(new BusinessException("yyyy-MM-dd"));
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(groupService.group((Long) any())).thenThrow(new BusinessException("Msg"));
        contractServiceImpl.groupContract(123L);
    }

    /**
     * Method under test: {@link ContractServiceImpl#groupContract(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGroupContract2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:143)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.listGroupContract(ContractServiceImpl.java:565)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.groupContract(ContractServiceImpl.java:627)
        //   See https://diff.blue/R013 to resolve this issue.

        when(contractRepository.findById((Long) any())).thenReturn(Optional.empty());
        Contracts contracts = mock(Contracts.class);
        when(contracts.getGroupId()).thenReturn(123L);
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
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        when(renterService.rackRenter((Long) any())).thenThrow(new BusinessException("yyyy-MM-dd"));
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(groupService.group((Long) any())).thenReturn("Group");
        contractServiceImpl.groupContract(123L);
    }

    /**
     * Method under test: {@link ContractServiceImpl#groupContract(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGroupContract3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   vn.com.fpt.common.BusinessException: yyyy-MM-dd
        //       at jdk.proxy4.$Proxy91.rackRenter(null)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.lambda$listGroupContract$11(ContractServiceImpl.java:574)
        //       at java.lang.Iterable.forEach(Iterable.java:75)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.listGroupContract(ContractServiceImpl.java:568)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.groupContract(ContractServiceImpl.java:627)
        //   See https://diff.blue/R013 to resolve this issue.

        Contracts contracts = mock(Contracts.class);
        when(contracts.getId()).thenReturn(123L);
        when(contracts.getRackRenters()).thenReturn(1L);
        when(contracts.getGroupId()).thenReturn(123L);
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
        Optional<Contracts> ofResult = Optional.of(contracts);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);
        when(roomService.listRoom((Long) any(), (Long) any(), (Integer) any(), (Integer) any(), (String) any()))
                .thenReturn(new ArrayList<>());
        when(renterService.rackRenter((Long) any())).thenThrow(new BusinessException("yyyy-MM-dd"));
        when(servicesService.listGeneralServiceByGroupId((Long) any())).thenReturn(new ArrayList<>());
        when(groupService.group((Long) any())).thenReturn(new GroupContractedResponse());
        contractServiceImpl.groupContract(123L);
    }

    /**
     * Method under test: {@link ContractServiceImpl#endRoomContract(EndRoomContractRequest, Long)}
     */
    @Test
    void testEndRoomContract() {
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
        Optional<Contracts> ofResult = Optional.of(contracts);

        Contracts contracts1 = new Contracts();
        contracts1.setAddressId(123L);
        contracts1.setContractBillCycle(1);
        contracts1.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractEndDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractIsDisable(true);
        contracts1.setContractName("Contract Name");
        contracts1.setContractPaymentCycle(1);
        contracts1.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractStartDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractTerm(1);
        contracts1.setContractType(1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setCreatedBy(1L);
        contracts1.setGroupId(123L);
        contracts1.setId(123L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setModifiedBy(1L);
        contracts1.setNote("Note");
        contracts1.setRackRenters(1L);
        contracts1.setRenters(1L);
        contracts1.setRoomId(123L);
        when(contractRepository.save((Contracts) any())).thenReturn(contracts1);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        when(roomService.updateRoom((Rooms) any())).thenReturn(rooms1);
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(renterRepository.saveAll((Iterable<Renters>) any())).thenReturn(new ArrayList<>());
        when(renterRepository.findAllByRoomId((Long) any())).thenReturn(new ArrayList<>());
        EndRoomContractRequest endRoomContractRequest = new EndRoomContractRequest();
        assertSame(endRoomContractRequest, contractServiceImpl.endRoomContract(endRoomContractRequest, 1L));
        verify(contractRepository).save((Contracts) any());
        verify(contractRepository).findById((Long) any());
        verify(roomService).room((Long) any());
        verify(roomService).updateRoom((Rooms) any());
        verify(renterRepository).saveAll((Iterable<Renters>) any());
        verify(renterRepository).findAllByRoomId((Long) any());
    }

    /**
     * Method under test: {@link ContractServiceImpl#endRoomContract(EndRoomContractRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testEndRoomContract2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   vn.com.fpt.common.BusinessException: Msg
        //       at jdk.proxy4.$Proxy93.findAllByRoomId(null)
        //       at vn.com.fpt.service.contract.ContractServiceImpl.endRoomContract(ContractServiceImpl.java:648)
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
        Optional<Contracts> ofResult = Optional.of(contracts);

        Contracts contracts1 = new Contracts();
        contracts1.setAddressId(123L);
        contracts1.setContractBillCycle(1);
        contracts1.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractEndDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractIsDisable(true);
        contracts1.setContractName("Contract Name");
        contracts1.setContractPaymentCycle(1);
        contracts1.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractStartDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractTerm(1);
        contracts1.setContractType(1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setCreatedBy(1L);
        contracts1.setGroupId(123L);
        contracts1.setId(123L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setModifiedBy(1L);
        contracts1.setNote("Note");
        contracts1.setRackRenters(1L);
        contracts1.setRenters(1L);
        contracts1.setRoomId(123L);
        when(contractRepository.save((Contracts) any())).thenReturn(contracts1);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        when(roomService.updateRoom((Rooms) any())).thenReturn(rooms1);
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(renterRepository.saveAll((Iterable<Renters>) any())).thenThrow(new BusinessException("Msg"));
        when(renterRepository.findAllByRoomId((Long) any())).thenThrow(new BusinessException("Msg"));
        contractServiceImpl.endRoomContract(new EndRoomContractRequest(), 1L);
    }

    /**
     * Method under test: {@link ContractServiceImpl#endRoomContract(EndRoomContractRequest, Long)}
     */
    @Test
    void testEndRoomContract3() {
        Contracts contracts = mock(Contracts.class);
        when(contracts.getRoomId()).thenReturn(123L);
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
        Optional<Contracts> ofResult = Optional.of(contracts);

        Contracts contracts1 = new Contracts();
        contracts1.setAddressId(123L);
        contracts1.setContractBillCycle(1);
        contracts1.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractEndDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractIsDisable(true);
        contracts1.setContractName("Contract Name");
        contracts1.setContractPaymentCycle(1);
        contracts1.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractStartDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractTerm(1);
        contracts1.setContractType(1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setCreatedBy(1L);
        contracts1.setGroupId(123L);
        contracts1.setId(123L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setModifiedBy(1L);
        contracts1.setNote("Note");
        contracts1.setRackRenters(1L);
        contracts1.setRenters(1L);
        contracts1.setRoomId(123L);
        when(contractRepository.save((Contracts) any())).thenReturn(contracts1);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);

        Rooms rooms = new Rooms();
        rooms.setContractId(123L);
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setCreatedAt(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        rooms.setCreatedBy(1L);
        rooms.setGroupContractId(123L);
        rooms.setGroupId(123L);
        rooms.setId(123L);
        rooms.setIsDisable(true);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms.setModifiedAt(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
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
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setCreatedAt(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setCreatedBy(1L);
        rooms1.setGroupContractId(123L);
        rooms1.setGroupId(123L);
        rooms1.setId(123L);
        rooms1.setIsDisable(true);
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        rooms1.setModifiedAt(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        rooms1.setModifiedBy(1L);
        rooms1.setRoomArea(10.0d);
        rooms1.setRoomCurrentElectricIndex(1);
        rooms1.setRoomCurrentWaterIndex(1);
        rooms1.setRoomFloor(1);
        rooms1.setRoomLimitPeople(1);
        rooms1.setRoomName("Room Name");
        rooms1.setRoomPrice(10.0d);
        when(roomService.updateRoom((Rooms) any())).thenReturn(rooms1);
        when(roomService.room((Long) any())).thenReturn(rooms);
        when(renterRepository.saveAll((Iterable<Renters>) any())).thenReturn(new ArrayList<>());
        when(renterRepository.findAllByRoomId((Long) any())).thenReturn(new ArrayList<>());
        EndRoomContractRequest endRoomContractRequest = new EndRoomContractRequest();
        assertSame(endRoomContractRequest, contractServiceImpl.endRoomContract(endRoomContractRequest, 1L));
        verify(contractRepository).save((Contracts) any());
        verify(contractRepository).findById((Long) any());
        verify(contracts, atLeast(1)).getRoomId();
        verify(contracts).setCreatedAt((Date) any());
        verify(contracts).setCreatedBy((Long) any());
        verify(contracts).setId((Long) any());
        verify(contracts).setModifiedAt((Date) any());
        verify(contracts).setModifiedBy((Long) any());
        verify(contracts).setAddressId((Long) any());
        verify(contracts).setContractBillCycle((Integer) any());
        verify(contracts).setContractDeposit((Double) any());
        verify(contracts).setContractEndDate((Date) any());
        verify(contracts, atLeast(1)).setContractIsDisable((Boolean) any());
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
        verify(roomService).updateRoom((Rooms) any());
        verify(renterRepository).saveAll((Iterable<Renters>) any());
        verify(renterRepository).findAllByRoomId((Long) any());
    }

    /**
     * Method under test: {@link ContractServiceImpl#endGroupContract(EndGroupContractRequest, Long)}
     */
    @Test
    void testEndGroupContract() {
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
        Optional<Contracts> ofResult = Optional.of(contracts);

        Contracts contracts1 = new Contracts();
        contracts1.setAddressId(123L);
        contracts1.setContractBillCycle(1);
        contracts1.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractEndDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractIsDisable(true);
        contracts1.setContractName("Contract Name");
        contracts1.setContractPaymentCycle(1);
        contracts1.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractStartDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractTerm(1);
        contracts1.setContractType(1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setCreatedBy(1L);
        contracts1.setGroupId(123L);
        contracts1.setId(123L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setModifiedBy(1L);
        contracts1.setNote("Note");
        contracts1.setRackRenters(1L);
        contracts1.setRenters(1L);
        contracts1.setRoomId(123L);
        when(contractRepository.save((Contracts) any())).thenReturn(contracts1);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);
        when(renterRepository.findAllByRoomIdIn((List<Long>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByGroupContractId((Long) any())).thenReturn(new ArrayList<>());
        EndGroupContractRequest endGroupContractRequest = new EndGroupContractRequest();
        assertSame(endGroupContractRequest, contractServiceImpl.endGroupContract(endGroupContractRequest, 1L));
        verify(contractRepository).save((Contracts) any());
        verify(contractRepository).findById((Long) any());
        verify(renterRepository).findAllByRoomIdIn((List<Long>) any());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByGroupContractId((Long) any());
    }

    /**
     * Method under test: {@link ContractServiceImpl#endGroupContract(EndGroupContractRequest, Long)}
     */
    @Test
    void testEndGroupContract2() {
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
        Optional<Contracts> ofResult = Optional.of(contracts);

        Contracts contracts1 = new Contracts();
        contracts1.setAddressId(123L);
        contracts1.setContractBillCycle(1);
        contracts1.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractEndDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractIsDisable(true);
        contracts1.setContractName("Contract Name");
        contracts1.setContractPaymentCycle(1);
        contracts1.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractStartDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractTerm(1);
        contracts1.setContractType(1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setCreatedBy(1L);
        contracts1.setGroupId(123L);
        contracts1.setId(123L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setModifiedBy(1L);
        contracts1.setNote("Note");
        contracts1.setRackRenters(1L);
        contracts1.setRenters(1L);
        contracts1.setRoomId(123L);
        when(contractRepository.save((Contracts) any())).thenReturn(contracts1);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);
        when(renterRepository.findAllByRoomIdIn((List<Long>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenThrow(new BusinessException("Msg"));
        when(roomsRepository.findAllByGroupContractId((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class,
                () -> contractServiceImpl.endGroupContract(new EndGroupContractRequest(), 1L));
        verify(contractRepository).save((Contracts) any());
        verify(contractRepository).findById((Long) any());
        verify(roomsRepository).findAllByGroupContractId((Long) any());
    }

    /**
     * Method under test: {@link ContractServiceImpl#endGroupContract(EndGroupContractRequest, Long)}
     */
    @Test
    void testEndGroupContract3() {
        Contracts contracts = mock(Contracts.class);
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
        Optional<Contracts> ofResult = Optional.of(contracts);

        Contracts contracts1 = new Contracts();
        contracts1.setAddressId(123L);
        contracts1.setContractBillCycle(1);
        contracts1.setContractDeposit(10.0d);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractEndDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractIsDisable(true);
        contracts1.setContractName("Contract Name");
        contracts1.setContractPaymentCycle(1);
        contracts1.setContractPrice(10.0d);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setContractStartDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setContractTerm(1);
        contracts1.setContractType(1);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setCreatedAt(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setCreatedBy(1L);
        contracts1.setGroupId(123L);
        contracts1.setId(123L);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        contracts1.setModifiedAt(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        contracts1.setModifiedBy(1L);
        contracts1.setNote("Note");
        contracts1.setRackRenters(1L);
        contracts1.setRenters(1L);
        contracts1.setRoomId(123L);
        when(contractRepository.save((Contracts) any())).thenReturn(contracts1);
        when(contractRepository.findById((Long) any())).thenReturn(ofResult);
        when(renterRepository.findAllByRoomIdIn((List<Long>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.saveAll((Iterable<Rooms>) any())).thenReturn(new ArrayList<>());
        when(roomsRepository.findAllByGroupContractId((Long) any())).thenReturn(new ArrayList<>());
        EndGroupContractRequest endGroupContractRequest = new EndGroupContractRequest();
        assertSame(endGroupContractRequest, contractServiceImpl.endGroupContract(endGroupContractRequest, 1L));
        verify(contractRepository).save((Contracts) any());
        verify(contractRepository).findById((Long) any());
        verify(contracts).setCreatedAt((Date) any());
        verify(contracts).setCreatedBy((Long) any());
        verify(contracts).setId((Long) any());
        verify(contracts).setModifiedAt((Date) any());
        verify(contracts).setModifiedBy((Long) any());
        verify(contracts).setAddressId((Long) any());
        verify(contracts).setContractBillCycle((Integer) any());
        verify(contracts).setContractDeposit((Double) any());
        verify(contracts).setContractEndDate((Date) any());
        verify(contracts, atLeast(1)).setContractIsDisable((Boolean) any());
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
        verify(renterRepository).findAllByRoomIdIn((List<Long>) any());
        verify(roomsRepository).saveAll((Iterable<Rooms>) any());
        verify(roomsRepository).findAllByGroupContractId((Long) any());
    }
}

