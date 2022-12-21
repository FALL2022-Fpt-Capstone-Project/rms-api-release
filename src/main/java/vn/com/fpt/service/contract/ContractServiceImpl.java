package vn.com.fpt.service.contract;

import lombok.SneakyThrows;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.entity.*;
import vn.com.fpt.model.GeneralServiceDTO;
import vn.com.fpt.model.GroupContractDTO;
import vn.com.fpt.model.HandOverGeneralServiceDTO;
import vn.com.fpt.model.RoomContractDTO;
import vn.com.fpt.repositories.*;
import vn.com.fpt.requests.*;
import vn.com.fpt.responses.GroupContractedResponse;
import vn.com.fpt.responses.RentersResponse;
import vn.com.fpt.service.TableLogComponent;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.renter.RenterService;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.services.ServicesService;
import vn.com.fpt.specification.BaseSpecification;
import vn.com.fpt.specification.SearchCriteria;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static vn.com.fpt.common.utils.DateUtils.*;
import static vn.com.fpt.common.constants.ErrorStatusConstants.*;
import static vn.com.fpt.common.constants.ManagerConstants.*;
import static vn.com.fpt.common.constants.SearchOperation.*;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;

    private final AssetService assetService;
    private final RoomService roomService;
    private final RenterService renterService;
    private final ServicesService servicesService;
    private final AddressRepository addressRepository;
    private final GroupService groupService;
    private final RenterRepository renterRepository;

    private final RackRenterRepository rackRenterRepository;

    private final MoneySourceRepository moneySourceRepository;
    private final TableLogComponent tableLogComponent;

    private final RoomBillRepository roomBillRepository;

    private final RoomsRepository roomsRepository;

    public ContractServiceImpl(ContractRepository contractRepository,
                               AssetService assetService,
                               @Lazy RoomService roomService,
                               @Lazy RenterService renterService,
                               ServicesService servicesService,
                               AddressRepository addressRepository,
                               @Lazy GroupService groupService,
                               @Lazy RenterRepository renterRepository,
                               RackRenterRepository rackRenters,
                               MoneySourceRepository moneySourceRepository,
                               TableLogComponent tableLogComponent,
                               RoomBillRepository roomBillRepository,
                               RoomsRepository roomsRepository) {
        this.contractRepository = contractRepository;
        this.assetService = assetService;
        this.roomService = roomService;
        this.renterService = renterService;
        this.servicesService = servicesService;
        this.addressRepository = addressRepository;
        this.groupService = groupService;
        this.renterRepository = renterRepository;
        this.rackRenterRepository = rackRenters;
        this.moneySourceRepository = moneySourceRepository;
        this.tableLogComponent = tableLogComponent;
        this.roomBillRepository = roomBillRepository;
        this.roomsRepository = roomsRepository;
    }

    @Override
    @Transactional
    @SneakyThrows
    public RoomContractRequest addContract(RoomContractRequest request, Long operator) {
        Contracts contractsInformation = Contracts.addForSubLease(request, operator);

        var roomId = request.getRoomId();
        var groupContractId = roomService.room(roomId).getGroupContractId();

        if (ObjectUtils.isEmpty(groupContractId))
            throw new BusinessException("Phòng này chưa có hợp đồng nhóm phòng. Vui lòng kiểm tra lại!!");
        var room = roomService.emptyRoom(roomId);
        room.setRoomPrice(request.getContractPrice());
        roomService.updateRoom(room);


        //parse string to date
        Date startDate = parse(request.getContractStartDate());
        Date endDate = parse(request.getContractEndDate());

        // kiểm tra ngày bắt đầu và ngày kết thúc
        if (Boolean.TRUE.equals(VALIDATE_CONTRACT_TERM(startDate, endDate)))
            throw new BusinessException(INVALID_TIME, "Ngày kết thúc không được trước ngày bắt đầu!!");
        var checkRenter = renterService.findRenter(request.getRenterIdentityCard());
        if (Objects.isNull(checkRenter)) {
            var address = Address.add(
                    "",
                    "",
                    "",
                    request.getAddressMoreDetail(),
                    operator);
            var newRenter =
                    renterService.addRenter(Renters.add(RenterRequest.contractOf(request), address, operator));

            // sau khi thêm khách thuê đại diện -> set thông tin đại diện cho hợp đồng để add
            contractsInformation.setRenters(newRenter.getId());
        } else {
            // nếu khách đã tồn tại -> set renter_id vào hợp đồng
            contractsInformation.setRenters(checkRenter.getId());
            renterService.updateRenter(checkRenter.getId(), RenterRequest.contractOf(request), operator);
        }

        // lưu thông tin hợp đồng
        var addedContract = contractRepository.save(contractsInformation);
        var contractId = addedContract.getId();

        // cập nhập trạng thái phòng trống -> đã thuê D:
        roomService.updateRoomStatus(roomId, contractId, operator);

        // lưu thành viên vào phòng
        if (ObjectUtils.isNotEmpty(request.getListRenter())) {
            if (request.getListRenter().size() > room.getRoomLimitPeople())
                throw new BusinessException(RENTER_LIMIT, "Giới hạn thành viên trong phòng " + room.getRoomLimitPeople() + " số lượng thành viên hiện tại: " + (request.getListRenter().size()));
            request.getListRenter().forEach(e -> {
                e.setRepresent(false);
                e.setRoomId(roomId);
                var address = Address.add(e, operator);
                var renter = Renters.add(e, address, operator);
                renterService.addRenter(renter);
            });
        }

        //lưu dịch vụ chung
        if (!request.getListGeneralService().isEmpty()) {
            AtomicInteger currentElectric = new AtomicInteger(0);
            AtomicInteger currentWater = new AtomicInteger(0);

            request.getListGeneralService().forEach(service -> {
                        var serviceId = servicesService.generalService(service.getGeneralServiceId()).getServiceId().longValue();
                        var serviceTypeId = servicesService.generalService(service.getGeneralServiceId()).getServiceTypeId().longValue();
                        if (serviceId == SERVICE_WATER && serviceTypeId == SERVICE_TYPE_METER) {
                            currentWater.set(service.getHandOverGeneralServiceIndex());
                        }
                        if (serviceId == SERVICE_ELECTRIC) {
                            currentElectric.set(service.getHandOverGeneralServiceIndex());
                        }
                    }
            );
            //cập nhập chỉ số điện nước cho phòng
            roomService.setServiceIndex(request.getRoomId(),
                    currentElectric.get(),
                    currentWater.get(),
                    operator);
        }


        var var1 = moneySourceRepository.save(MoneySource.of(
                        "Tiền cọc hợp đồng phòng " + room.getRoomName(),
                        request.getContractDeposit(),
                        IN_MONEY,
                        now(),
                        null,
                        ANOTHER_BILL
                )
        );
        tableLogComponent.saveMoneySourceHistory(List.of(var1));

        var var2 = roomBillRepository.save(RoomBill.add(
                        addedContract.getId(),
                        room.getGroupContractId(),
                        room.getGroupId(),
                        room.getId(),
                        request.getContractPrice() * request.getContractPaymentCycle(),
                        request.getContractPaymentCycle(),
                        now(),
                        "Tiền phòng lập hợp đồng phòng " + room.getRoomName() + "ở " + groupService.getGroup(request.getGroupId()).getGroupName()
                )
        );
        tableLogComponent.saveRoomBillHistory(var2);

        var var3 = moneySourceRepository.save(MoneySource.of(
                var2.getNote(),
                var2.getRoomTotalMoney(),
                IN_MONEY,
                var2.getBillCreatedTime(),
                var2.getId(),
                ROOM_BILL
        ));
        tableLogComponent.saveMoneySourceHistory(List.of(var3));

        return request;
    }

    @Override
    @Transactional
    public GroupContractRequest addContract(GroupContractRequest request, Long operator) {
        var existedRackRenter = renterService.findRackRenter(request.getRackRenterIdentity());
        Long rackRenterId;
        if (Objects.isNull(existedRackRenter)) {
            rackRenterId = renterService.addRackRenter(
                    request.getRackRenterName(),
                    request.getRackRenterGender(),
                    request.getRackRenterPhone(),
                    request.getRackRenterEmail(),
                    request.getRackRenterIdentity(),
                    Address.add(null, null, null, request.getRackRenterAddress(), operator),
                    request.getRackRenterNote(),
                    operator
            ).getId();
        } else {
            rackRenterId = existedRackRenter.getId();
        }
        var contract = Contracts.addForLease(request, operator);
        contract.setRackRenters(rackRenterId);


        var addedContract = contractRepository.save(contract);
        var listRoom = roomService.listRoom(request.getListRoom());
        listRoom.forEach(e -> {
            if (e.getGroupContractId() != null) throw new BusinessException(GROUP_NOT_AVAILABLE, e.getRoomName());
        });

        listRoom.forEach(e -> e.setGroupContractId(addedContract.getId()));
        roomService.updateRoom(listRoom);
        return request;
    }

    @Override
    @Transactional
    public GroupContractRequest updateContract(Long groupContractId, GroupContractRequest request, Long operator) {
        var oldContract = contract(groupContractId);
        var oldRackRenter = renterService.rackRenter(oldContract.getRackRenters());
        rackRenterRepository.save(RackRenters.modify(
                oldRackRenter,
                request.getRackRenterName(),
                request.getRackRenterGender(),
                request.getRackRenterPhone(),
                request.getRackRenterEmail(),
                addressRepository.save(
                        new Address().modify(
                                oldRackRenter.getAddress(),
                                "",
                                "", "",
                                request.getRackRenterAddress(),
                                operator)
                ),
                request.getRackRenterNote(),
                operator
        ));
        var newContract = Contracts.modifyForLease(oldContract, request, operator);
        contractRepository.save(newContract);

        return request;
    }


    @Override
    public RoomContractRequest updateContract(Long id, RoomContractRequest request, Long operator) {
        var oldContract = contractRepository.findById(id).get();
        Contracts contractsInformation = Contracts.modifyForSublease(oldContract, request, operator);


        var roomId = oldContract.getRoomId(); //oldRoom
        var groupContractId = roomService.room(roomId).getGroupContractId();
        if (ObjectUtils.isEmpty(groupContractId))
            throw new BusinessException("Phòng này chưa có hợp đồng nhóm phòng. Vui lòng kiểm tra lại!!");

        if (!Objects.equals(request.getRoomId(), roomId)) { // nếu khác thì sẽ check r add
            roomId = roomService.emptyRoom(request.getRoomId()).getId();
        }
        contractsInformation.setRoomId(roomId);

        //update giá phòng
        var oldRoom = roomService.room(roomId);
        var newRoom = oldRoom;

        newRoom.setRoomPrice(request.getContractPrice());
        roomService.updateRoom(Rooms.modify(oldRoom, newRoom, operator));

        // kiểm tra ngày bắt đầu và ngày kết thúc
        Date startDate = parse(request.getContractStartDate());
        Date endDate = parse(request.getContractEndDate());

        if (Boolean.TRUE.equals(VALIDATE_CONTRACT_TERM(startDate, endDate)))
            throw new BusinessException(INVALID_TIME, "Ngày kết thúc không được trước ngày bắt đầu");
        var modifyRenter = RenterRequest.contractOf(request);

        // cập nhập thông tin thằng đại diện
        modifyRenter.setRoomId(roomId);
        modifyRenter.setAddressMoreDetail(request.getAddressMoreDetail());
        renterService.updateRenter(oldContract.getRenters(), modifyRenter, operator);


        // cập nhập thông tin hợp đồng
        contractRepository.save(contractsInformation);

        /* nếu có những tài sản mới không thuộc tài sản bàn giao với tòa ban đầu thì sẽ:
        add thêm vào những tài sản cơ bản, thiết yếu -> add thêm vào tài sản chung của tòa -> add tài sản bàn giao cho phòng
        */
        //cap nhap dịch vụ chung
        if (ObjectUtils.isNotEmpty(request.getListGeneralService())) {

            AtomicInteger currentElectric = new AtomicInteger(0);
            AtomicInteger currentWater = new AtomicInteger(0);

            request.getListGeneralService().forEach(service -> {
                if (Objects.equals(service.getServiceId(), SERVICE_WATER)) {
                    currentWater.set(service.getHandOverGeneralServiceIndex());
                }
                if (Objects.equals(service.getServiceId(), SERVICE_ELECTRIC)) {
                    currentElectric.set(service.getHandOverGeneralServiceIndex());
                }
            });
            //cập nhập chỉ số điện nước cho phòng
            roomService.setServiceIndex(request.getRoomId(),
                    currentElectric.get(),
                    currentWater.get(),
                    operator);
        }
        return request;
    }

    @Override
    public List<Contracts> listGroupContract(Long groupId) {
        return contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse(groupId, LEASE_CONTRACT);
    }

    @Override
    public Contracts contract(Long id) {
        return contractRepository.findById(id).orElseThrow(() -> new BusinessException(CONTRACT_NOT_FOUND, "Hợp đồng contract_id" + id));
    }

    @Override
    public RoomContractDTO roomContract(Long id) {
        var contract = contract(id);
        List<RentersResponse> listRenter = new ArrayList<>(renterService.listMember(contract.getRoomId()));
        listRenter.add(renterService.renter(contract.getRenters()));

        var room = roomService.room(contract.getRoomId());
        List<HandOverGeneralServiceDTO> listService = new ArrayList<>();
        List<GeneralServiceDTO> list = servicesService.listGeneralServiceByGroupId(contract.getGroupId());
        var water = list.stream().filter(x -> x.getServiceId() == (BigInteger.valueOf(SERVICE_WATER))).findAny().get(new GeneralServiceDTO());
        var electric = list.stream().filter(z -> z.getServiceId() == (BigInteger.valueOf(SERVICE_ELECTRIC))).findAny().orElse(new GeneralServiceDTO());
        HandOverGeneralServiceDTO water1 = new HandOverGeneralServiceDTO();
        water1.setServiceId(water.getServiceId());
        water1.setServiceName(water.getServiceName());
        water1.setServicePrice(water.getServicePrice());
        water1.setServiceShowName(water.getServiceShowName());
        water1.setServiceTypeId(water.getServiceTypeId());
        water1.setServiceTypeName(water.getServiceName());
        water1.setHandOverGeneralServiceIndex(room.getRoomCurrentWaterIndex());


        HandOverGeneralServiceDTO electric1 = new HandOverGeneralServiceDTO();
        electric1.setServiceId(electric.getServiceId());
        electric1.setServiceName(electric.getServiceName());
        electric1.setServicePrice(electric.getServicePrice());
        electric1.setServiceShowName(electric.getServiceShowName());
        electric1.setServiceTypeId(electric.getServiceTypeId());
        electric1.setServiceTypeName(electric.getServiceName());
        electric1.setHandOverGeneralServiceIndex(room.getRoomCurrentElectricIndex());
        listService.add(water1);
        listService.add(electric1);
        var roomContract = RoomContractDTO.of(contract,
                listRenter,
                assetService.listRoomAsset(contract.getRoomId(), null),
                listService);
        roomContract.setRoom(roomService.room(roomContract.getRoomId()));
        roomContract.setRoomName(roomService.room(roomContract.getRoomId()).getRoomName());
        roomContract.setGroupName(((GroupContractedResponse) groupService.group(roomContract.getGroupId())).getGroupName());
        roomContract.setListRoom(
                roomService.listRoom(
                        contract.getGroupId(),
                        roomService.room(roomContract.getRoomId()).getGroupContractId(),
                        null,
                        null,
                        null)
        );
        return roomContract;
    }

    @Override
    public List<RoomContractDTO> listRoomContract(Long groupId,
                                                  String phoneNumber,
                                                  String identity,
                                                  String renterName,
                                                  Boolean isDisable,
                                                  String startDate,
                                                  String endDate,
                                                  Integer status,
                                                  Long duration,
                                                  List<Long> roomId) {
        List<RoomContractDTO> roomContracts = new ArrayList<>();

        BaseSpecification<Renters> renterSpec = new BaseSpecification<>();

        if (StringUtils.isNoneBlank(phoneNumber)) {
            renterSpec.add(new SearchCriteria("phoneNumber", phoneNumber, EQUAL));
        }
        if (StringUtils.isNoneBlank(identity)) {
            renterSpec.add(new SearchCriteria("identityNumber", identity, EQUAL));
        }
        if (StringUtils.isNoneBlank(renterName)) {
            renterSpec.add(new SearchCriteria("renterFullName", renterName, MATCH));
        }

        List<Long> searchRenter = renterRepository.findAll(renterSpec).stream().map(Renters::getId).toList();

        if (searchRenter.isEmpty()) return Collections.emptyList();

        BaseSpecification<Contracts> contractSpec = new BaseSpecification<>();
        contractSpec.add(new SearchCriteria("contractType", SUBLEASE_CONTRACT, EQUAL));

        if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(groupId)) {
            contractSpec.add(new SearchCriteria("groupId", groupId, EQUAL));
        }

        if (StringUtils.isNoneBlank(startDate, endDate)) {
            contractSpec.add(new SearchCriteria("contractStartDate", DateUtils.parse(startDate), GREATER_THAN_EQUAL));

            contractSpec.add(new SearchCriteria("contractEndDate", DateUtils.parse(endDate), LESS_THAN_EQUAL));
        }

        if (ObjectUtils.isNotEmpty(roomId)) {
            contractSpec.add(new SearchCriteria("roomId", roomId, IN));
        }

        List<Contracts> listDisbaleContract = new ArrayList<>(Collections.emptyList());
        BaseSpecification<Contracts> contractSpec2 = new BaseSpecification<>();
        if (ObjectUtils.isNotEmpty(status)) {

            switch (status) {
                case LATEST_CONTRACT -> {
                    contractSpec.add(SearchCriteria.of("contractStartDate", monthsCalculate(now(), -duration), GREATER_THAN_EQUAL));
                    contractSpec.add(SearchCriteria.of("contractStartDate", now(), LESS_THAN_EQUAL));
                }
                case ALMOST_EXPIRED_CONTRACT -> {
                    contractSpec.add(SearchCriteria.of("contractEndDate", now(), GREATER_THAN_EQUAL));
                    contractSpec.add(SearchCriteria.of("contractEndDate", monthsCalculate(now(), duration), LESS_THAN_EQUAL));
                }
                case EXPIRED_CONTRACT -> {
                    contractSpec.add(SearchCriteria.of("contractEndDate", now(), LESS_THAN_EQUAL));
                    contractSpec2.add(SearchCriteria.of("contractIsDisable", true, EQUAL));
                    listDisbaleContract.addAll(contractRepository.findAll(contractSpec2, Sort.by("contractStartDate").descending()));
                }
            }
        }

        if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(isDisable)) {
            contractSpec.add(new SearchCriteria("contractIsDisable", isDisable, EQUAL));
            if ((ObjectUtils.isEmpty(status) ? EXPIRED_CONTRACT : status) != EXPIRED_CONTRACT)
                contractSpec.add(new SearchCriteria("contractIsDisable", isDisable, EQUAL));
        }
        if (!searchRenter.isEmpty()) {
            contractSpec.add(new SearchCriteria("renters", searchRenter, IN));
        }
        var listContract = contractRepository.findAll(contractSpec, Sort.by("contractStartDate").descending());
        if (!listDisbaleContract.isEmpty()) listContract.addAll(listDisbaleContract);

        if (listContract.isEmpty()) return Collections.emptyList();
        listContract.forEach(e -> {
            var room = roomService.room(e.getRoomId());
            List<HandOverGeneralServiceDTO> listService = new ArrayList<>();
            List<GeneralServiceDTO> list = servicesService.listGeneralServiceByGroupId(e.getGroupId());
            var water = list.stream().filter(x -> x.getServiceId() == (BigInteger.valueOf(SERVICE_WATER))).findAny().get();
            var electrict = list.stream().filter(z -> z.getServiceId() == (BigInteger.valueOf(SERVICE_ELECTRIC))).findAny().get();

            HandOverGeneralServiceDTO water1 = new HandOverGeneralServiceDTO();
            water1.setServiceId(water.getServiceId());
            water1.setServiceName(water.getServiceName());
            water1.setServicePrice(water.getServicePrice());
            water1.setServiceShowName(water.getServiceShowName());
            water1.setServiceTypeId(water.getServiceTypeId());
            water1.setServiceTypeName(water.getServiceName());
            water1.setHandOverGeneralServiceIndex(room.getRoomCurrentWaterIndex());


            HandOverGeneralServiceDTO electric1 = new HandOverGeneralServiceDTO();
            electric1.setServiceId(electrict.getServiceId());
            electric1.setServiceName(electrict.getServiceName());
            electric1.setServicePrice(electrict.getServicePrice());
            electric1.setServiceShowName(electrict.getServiceShowName());
            electric1.setServiceTypeId(electrict.getServiceTypeId());
            electric1.setServiceTypeName(electrict.getServiceName());
            electric1.setHandOverGeneralServiceIndex(room.getRoomCurrentElectricIndex());
            listService.add(water1);
            listService.add(electric1);

            List<RentersResponse> listRenter = new ArrayList<>(renterService.listMember(e.getRoomId()));
            listRenter.add(renterService.renter(e.getRenters()));

            var group = (GroupContractedResponse) groupService.group(e.getGroupId());
            var roomContract = RoomContractDTO.of(e,
                    listRenter,
                    assetService.listRoomAsset(e.getRoomId(), null),
                    listService);
            roomContract.setRoom(room);
            roomContract.setRoomName(room.getRoomName());
            roomContract.setGroupName(group.getGroupName());
            roomContracts.add(roomContract);

        });
        return roomContracts;
    }

    @Override
    public List<GroupContractDTO> listGroupContract(String phoneNumber,
                                                    String identity,
                                                    String name,
                                                    Long groupId,
                                                    Long contractId,
                                                    String startDate,
                                                    String endDate,
                                                    Integer status,
                                                    Integer duration,
                                                    Boolean isDisable) {
//        if (ObjectUtils.isNotEmpty(groupId)) {
//            var groupContracts = contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse(groupId, LEASE_CONTRACT);
//            if (Objects.isNull(groupContracts)) return Collections.emptyList();
//            List<GroupContractDTO> listGroupContract = new ArrayList<>();
//            groupContracts.forEach
//                    (e ->
//                            listGroupContract.add(GroupContractDTO.of(
//                                            e,
//                                            (GroupContractedResponse) groupService.group(e.getGroupId()),
//                                            servicesService.listGeneralServiceByGroupId(e.getGroupId()),
//                                            roomService.listRoom(e.getGroupId(), e.getId(), null, null, null),
//                                            renterService.rackRenter(e.getRackRenters())
//                                    )
//                            )
//                    );
//            return listGroupContract;
//        }

        if (ObjectUtils.isNotEmpty(contractId)) {
            var groupContracts = List.of(contractRepository.findById(contractId).get());
            List<GroupContractDTO> listGroupContract = new ArrayList<>();
            groupContracts.forEach
                    (e ->
                            listGroupContract.add(GroupContractDTO.of(
                                            e,
                                            (GroupContractedResponse) groupService.group(e.getGroupId()),
                                            servicesService.listGeneralServiceByGroupId(e.getGroupId()),
                                            roomService.listRoom(e.getGroupId(), e.getId(), null, null, null),
                                            renterService.rackRenter(e.getRackRenters())
                                    )
                            )
                    );
            return listGroupContract;
        }

        //filter theo thông tin rack-renter
        BaseSpecification<RackRenters> rackRenterSpec = new BaseSpecification<>();
        if (StringUtils.isNoneBlank(name)) {
            rackRenterSpec.add(SearchCriteria.of("rackRenterFullName", name, MATCH));
        }
        if (StringUtils.isNoneBlank(phoneNumber)) {
            rackRenterSpec.add(SearchCriteria.of("phoneNumber", phoneNumber, EQUAL));
        }
        if (StringUtils.isNoneBlank(identity)) {
            rackRenterSpec.add(SearchCriteria.of("identityNumber", identity, EQUAL));
        }
        var rackRenterIdToFilter = renterService.listRackRenter(rackRenterSpec).stream().map(RackRenters::getId).toList();

        //filter theo thông tin hợp đồng
        BaseSpecification<Contracts> contractsSpec = new BaseSpecification<>();
        contractsSpec.add(SearchCriteria.of("contractType", LEASE_CONTRACT, EQUAL));

        if (StringUtils.isNoneBlank(startDate, endDate)) {
            contractsSpec.add(SearchCriteria.of("contractStartDate", parse(startDate, DATE_FORMAT_3), GREATER_THAN_EQUAL));
            contractsSpec.add(SearchCriteria.of("contractStartDate", parse(endDate, DATE_FORMAT_3), LESS_THAN_EQUAL));
        }
        if (Objects.nonNull(groupId)) {
            contractsSpec.add(SearchCriteria.of("groupId", groupId, EQUAL));
        }

        List<Contracts> listDisbaleContract = new ArrayList<>(Collections.emptyList());
        BaseSpecification<Contracts> contractSpec2 = new BaseSpecification<>();
        if (ObjectUtils.isNotEmpty(status)) {
            switch (status) {
                case LATEST_CONTRACT -> {
                    contractsSpec.add(SearchCriteria.of("contractStartDate", monthsCalculate(now(), -duration.longValue()), GREATER_THAN_EQUAL));
                    contractsSpec.add(SearchCriteria.of("contractStartDate", now(), LESS_THAN_EQUAL));
                }
                case ALMOST_EXPIRED_CONTRACT -> {
                    contractsSpec.add(SearchCriteria.of("contractEndDate", now(), GREATER_THAN_EQUAL));
                    contractsSpec.add(SearchCriteria.of("contractEndDate", monthsCalculate(now(), duration.longValue()), LESS_THAN_EQUAL));
                }
                case EXPIRED_CONTRACT -> {
                    contractsSpec.add(SearchCriteria.of("contractEndDate", now(), LESS_THAN_EQUAL));
                    contractSpec2.add(SearchCriteria.of("contractIsDisable", true, EQUAL));
                    listDisbaleContract.addAll(contractRepository.findAll(contractSpec2, Sort.by("contractStartDate").descending()));
                }
            }
        }

        if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(isDisable)) {
            contractsSpec.add(new SearchCriteria("contractIsDisable", isDisable, EQUAL));
            if ((ObjectUtils.isEmpty(status) ? EXPIRED_CONTRACT : status) != EXPIRED_CONTRACT)
                contractsSpec.add(new SearchCriteria("contractIsDisable", isDisable, EQUAL));
        }

        contractsSpec.add(SearchCriteria.of("rackRenters", rackRenterIdToFilter, IN));

        List<GroupContractDTO> listGroupContract = new ArrayList<>();
        List<Contracts> groupContracts = new ArrayList<>();

        groupContracts.addAll(contractRepository.findAll(contractsSpec, Sort.by("contractStartDate").ascending()));
        if (!listDisbaleContract.isEmpty()) groupContracts.addAll(listDisbaleContract);

        if (groupContracts.isEmpty()) return Collections.emptyList();

        groupContracts.forEach
                (e ->
                        listGroupContract.add(GroupContractDTO.of(
                                        e,
                                        (GroupContractedResponse) groupService.group(e.getGroupId()),
                                        servicesService.listGeneralServiceByGroupId(e.getId()),
                                        roomService.listRoom(e.getGroupId(), e.getId(), null, null, null),
                                        renterService.rackRenter(e.getRackRenters())
                                )
                        )
                );
        return listGroupContract;
    }

    @Override
    public GroupContractDTO groupContract(Long contractId) {
        return listGroupContract(
                null,
                null,
                null,
                null,
                contractId,
                null,
                null,
                null,
                null,
                null).get(0);
    }

    @Override
    @SneakyThrows
    @Transactional
    public EndRoomContractRequest endRoomContract(EndRoomContractRequest request, Long operator) {
        var endContract = contract(request.getContractId());
        var room = roomService.room(endContract.getRoomId());
        room.setContractId(null);
        roomService.updateRoom(room);
        endContract.setContractIsDisable(true);
        //last modified
        endContract.setModifiedAt(now());
        endContract.setModifiedBy(operator);
        contractRepository.save(endContract);
        List<Renters> renters = renterRepository.findAllByRoomId(endContract.getRoomId());
        renters.forEach(e -> {
            e.setRoomId(null);
            e.setRepresent(false);
        });
        renterRepository.saveAll(renters);
        return request;
    }

    @Override
    public EndGroupContractRequest endGroupContract(EndGroupContractRequest request, Long operator) {
        var endContract = contract(request.getGroupContractId());
        endContract.setContractIsDisable(true);
        contractRepository.save(endContract);
        var room = roomsRepository.findAllByGroupContractId(request.getGroupContractId());
        room.forEach(e -> {
            e.setGroupContractId(null);
            e.setContractId(null);
        });
        roomsRepository.saveAll(room);
        var renters = renterRepository.findAllByRoomIdIn(room.stream().map(Rooms::getId).toList());
        renters.forEach(e -> {
            e.setRoomId(null);
            e.setRepresent(null);
        });
        return request;
    }
}
