package vn.com.fpt.service.contract;

import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.entity.*;
import vn.com.fpt.model.GroupContractDTO;
import vn.com.fpt.model.RoomContractDTO;
import vn.com.fpt.repositories.*;
import vn.com.fpt.requests.GroupContractRequest;
import vn.com.fpt.requests.RenterRequest;
import vn.com.fpt.requests.RoomContractRequest;
import vn.com.fpt.responses.RentersResponse;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.renter.RenterService;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.services.ServicesService;
import vn.com.fpt.specification.BaseSpecification;
import vn.com.fpt.specification.SearchCriteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static vn.com.fpt.common.utils.DateUtils.*;
import static vn.com.fpt.common.constants.ErrorStatusConstants.*;
import static vn.com.fpt.common.constants.ManagerConstants.*;
import static vn.com.fpt.common.constants.SearchOperation.*;

@Service
@Lazy
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;

    private final AssetService assetService;
    private final RoomService roomService;
    private final RenterService renterService;
    private final ServicesService servicesService;
    private final AddressRepository addressRepository;
    private final GroupService groupService;

    private final RenterRepository renterRepository;

    @Override
    @Transactional
    public RoomContractRequest addContract(RoomContractRequest request, Long operator) {
        Contracts contractsInformation = Contracts.addForLease(request, operator);

        var roomId = request.getRoomId();
        var groupContractId = groupContract(request.getGroupId()).getId();
        var room = roomService.emptyRoom(roomId);

        //parse string to date
        Date startDate = parse(request.getContractStartDate(), DATE_FORMAT_3);
        Date endDate = parse(request.getContractEndDate(), DATE_FORMAT_3);

        assert endDate != null;
        assert startDate != null;
        // kiểm tra ngày bắt đầu và ngày kết thúc
        if (Boolean.TRUE.equals(VALIDATE_CONTRACT_TERM(startDate, endDate)))
            throw new BusinessException(INVALID_TIME, "Ngày kết thúc không được trước ngày bắt đầu");
        var checkRenter = renterService.findRenter(request.getRenterIdentityCard());
        if (Objects.isNull(checkRenter)) {
            //TODO: Nếu có properties liên quan đến địa chỉ khách ký hợp đồng thì sẽ set vào sau
            var address = Address.add(
                    "",
                    "",
                    "",
                    "",
                    operator);
            var newRenter =
                    renterService.addRenter(Renters.add(RenterRequest.contractOf(request), address, operator));

            // sau khi thêm khách thuê đại diện -> set thông tin đại diện cho hợp đồng để add
            contractsInformation.setRenters(newRenter.getId());
        } else {
            // nếu khách đã tồn tại -> set renter_id vào hợp đồng
            contractsInformation.setRenters(checkRenter.getId());
        }

        // lưu thông tin hợp đồng
        var addedContract = contractRepository.save(contractsInformation);
        var contractId = addedContract.getId();

        // cập nhập trạng thái phòng trống -> đã thuê D:
        roomService.updateRoomStatus(roomId, contractId, operator);

        // lưu thành viên vào phòng
        if (!request.getListRenter().isEmpty()) {
            if (request.getListRenter().size() + 1 > room.getRoomLimitPeople())
                throw new BusinessException(RENTER_LIMIT, "Giới hạn thành viên trong phòng " + room.getRoomLimitPeople() + " số lượng thành viên hiện tại:" + (request.getListRenter().size() + 1));
            request.getListRenter().forEach(e -> {
                e.setRepresent(false);
                e.setRoomId(roomId);
                var address = Address.add(e, operator);
                var renter = Renters.add(e, address, operator);
                renterService.addRenter(renter);
            });
        }

        /* nếu có những tài sản mới không thuộc tài sản bàn giao với tòa ban đầu thì sẽ:
        add thêm vào những tài sản cơ bản, thiết yếu -> add thêm vào tài sản chung của tòa -> add tài sản bàn giao cho phòng
         */

        // lưu tài sản bàn giao
        if (!request.getListHandOverAssets().isEmpty()) {
            request.getListHandOverAssets().forEach(handOverAsset -> {
                //kiểm tra những trang thiết bị không thuộc tòa (những tài sản không thuộc tòa thì id sẽ < 0)
                if (Boolean.TRUE.equals(ADDITIONAL_ASSETS(handOverAsset.getAssetId()))) {
                    //thêm tài sản cơ bản, thiết yếu
                    assetService.add(
                            BasicAssets.add(handOverAsset.getAssetsAdditionalName(),
                                    handOverAsset.getAssetsAdditionalType(), operator));

                    //thêm tài sản chung cho tòa
                    assetService.addGeneralAsset(
                            handOverAsset,
                            handOverAsset.getAssetId(),
                            groupContractId,
                            startDate);

                    //thêm tài sản bàn giao cho phòng
                    var handOverAssets = assetService.addHandOverAsset(
                            handOverAsset,
                            handOverAsset.getAssetId(),
                            contractId,
                            startDate);

                    //cập nhập số lượng tài sản của tòa
                    assetService.updateGeneralAssetQuantity(
                            groupContractId,
                            handOverAssets.getAssetId(),
                            handOverAsset.getHandOverAssetQuantity());
                }
                // nếu không có tài sản mới thì sẽ chỉ thêm tài sản bàn giao cho phòng
                else {
                    assetService.addHandOverAsset(
                            handOverAsset,
                            operator,
                            contractId,
                            startDate);

                    //cập nhập số lượng tài sản của tòa
                    assetService.updateGeneralAssetQuantity(
                            groupContractId,
                            handOverAsset.getAssetId(),
                            handOverAsset.getHandOverAssetQuantity());
                }
            });
        }
        //lưu dịch vụ chung
        //TODO: Set chỉ số điện nước cho phòng
        if (!request.getListGeneralService().isEmpty()) {
            request.getListGeneralService().forEach(service -> servicesService.addHandOverGeneralService(service, contractId, startDate, operator));
        }
        return request;
    }

    @Override
    @Transactional
    public GroupContractRequest addContract(GroupContractRequest request, Long operator) {

        var address = Address.add(request.getAddressCity(),
                request.getAddressDistrict(),
                request.getAddressWards(),
                request.getAddressMoreDetails(),
                operator);

        // tạo mới 1 nhóm phòng
        var group = RoomGroups.add(request.getGroupName(),
                addressRepository.save(address).getId(),
                operator);

        // tạo mới 1 hợp đồng cho nhóm phòng đấy
        var contract = contractRepository.save(Contracts.addForGroup(request,
                group.getId(),
                operator));


        if (!request.getListFloorAndRoom().isEmpty()) {
            List<Rooms> generateRoom = new ArrayList<>();

            // tự động gen phòng theo tầng
            request.getListFloorAndRoom().forEach(e -> {
                for (int i = 1; i <= e.getRoom(); i++) {
                    String roomName = e.getFloor() + String.format("%02d", i);
                    var room = Rooms.add(roomName,
                            e.getFloor(),
                            request.getGeneralLimitPeople(),
                            group.getId(),
                            request.getGeneralPrice(),
                            request.getGeneralArea(),
                            operator);
                    generateRoom.add(room);
                }
            });
            roomService.add(generateRoom);
        }

        //thêm tài sản bàn giao
        if (!request.getListHandOverAsset().isEmpty()) {
            request.getListHandOverAsset().forEach(e -> assetService.addHandOverAsset(e,
                    operator,
                    contract.getId(),
                    DateUtils.parse(e.getHandOverDateDelivery(), DATE_FORMAT_3)));

        }

        servicesService.addGeneralService(request.getListGeneralService(), operator);

        return request;
    }


    @Override
    public RoomContractRequest updateContract(Long id, RoomContractRequest request, Long operator) {
        var old = contractRepository.findById(id).get();
        Contracts contractsInformation = Contracts.modifyForLease(old, request, operator);


        var groupContractId = groupContract(old.getGroupId()).getId();
        var roomId = old.getRoomId(); //oldRoom

        if (!Objects.equals(request.getRoomId(), roomId)) { // nếu khác thì sẽ check r add
            roomId = roomService.emptyRoom(request.getRoomId()).getId();
        }
        contractsInformation.setRoomId(roomId);

        // kiểm tra ngày bắt đầu và ngày kết thúc
        Date startDate = parse(request.getContractStartDate(), DATE_FORMAT_3);
        Date endDate = parse(request.getContractEndDate(), DATE_FORMAT_3);
        assert endDate != null;
        assert startDate != null;

        if (Boolean.TRUE.equals(VALIDATE_CONTRACT_TERM(startDate, endDate)))
            throw new BusinessException(INVALID_TIME, "Ngày kết thúc không được trước ngày bắt đầu");
        var modifyRenter = RenterRequest.contractOf(request);

        // cập nhập thông tin thằng đại diện
        modifyRenter.setRoomId(roomId);
        renterService.updateRenter(old.getRenters(), modifyRenter, operator);


        // cập nhập thông tin hợp đồng
        var updatedContract = contractRepository.save(contractsInformation);

        /* nếu có những tài sản mới không thuộc tài sản bàn giao với tòa ban đầu thì sẽ:
        add thêm vào những tài sản cơ bản, thiết yếu -> add thêm vào tài sản chung của tòa -> add tài sản bàn giao cho phòng
        */

        // lưu tài sản bàn giao
        if (!request.getListHandOverAssets().isEmpty()) {
            request.getListHandOverAssets().forEach(handOverAsset -> {
                //kiểm tra những trang thiết bị không thuộc tòa (những tài sản không thuộc tòa thì id sẽ < 0)
                if (Boolean.TRUE.equals(ADDITIONAL_ASSETS(handOverAsset.getAssetId()))) {
                    //thêm tài sản cơ bản, thiết yếu
                    assetService.add(
                            BasicAssets.add(handOverAsset.getAssetsAdditionalName(),
                                    handOverAsset.getAssetsAdditionalType(), operator));

                    //thêm tài sản chung cho tòa
                    assetService.addGeneralAsset(
                            handOverAsset,
                            operator,
                            groupContractId,
                            startDate);

                    //thêm tài sản bàn giao cho phòng
                    assetService.addHandOverAsset(
                            handOverAsset,
                            operator,
                            old.getId(),
                            startDate);

                    //cập nhập số lượng tài sản của tòa
                    assetService.updateGeneralAssetQuantity(
                            groupContractId,
                            handOverAsset.getAssetId(),
                            handOverAsset.getHandOverAssetQuantity());
                }
                // nếu không có tài sản mới thì sẽ chỉ thêm tài sản bàn giao cho phòng
                else {
                    var oldHandOverAsset = assetService.handOverAsset(handOverAsset.getHandOverAssetId());
                    assetService.updateHandOverAsset(
                            assetService.handOverAsset(handOverAsset.getHandOverAssetId()),
                            handOverAsset,
                            operator,
                            old.getId(),
                            old.getContractStartDate());

                    //cập nhập số lượng tài sản của tòa
                    assetService.updateGeneralAssetQuantity(
                            groupContractId,
                            handOverAsset.getAssetId(),
                            handOverAsset.getHandOverAssetQuantity() - oldHandOverAsset.getQuantity());
                }
            });
        }
//        //lưu dịch vụ chung
//        //TODO: Set chỉ số điện nước cho phòng
//        if (!request.getListGeneralService().isEmpty()) {
//            request.getListGeneralService().forEach(service -> servicesService.updateHandOverGeneralService(
//                    service.getHandOverGeneralServiceId(),
//                    service,
//                    old.getId(),
//                    startDate,
//                    operator));
//        }
        return request;
    }

    @Override
    public Contracts groupContract(Long groupId) {
        return contractRepository.findByGroupIdAndContractType(groupId, LEASE_CONTRACT);
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

        var roomContract = RoomContractDTO.of(contract,
                listRenter,
                assetService.listHandOverAsset(id),
                servicesService.listHandOverGeneralService(id));
        roomContract.setRoom(roomService.getRoom(roomContract.getRoomId()));
        roomContract.setRoomName(roomService.getRoom(roomContract.getRoomId()).getRoomName());
        roomContract.setGroupName(groupService.group(roomContract.getGroupId()).getGroupName());
        roomContract.setListRoom(roomService.listRoom(contract.getGroupId(), null, null, null));
        return roomContract;
    }

    @Override
    public List<RoomContractDTO> listRoomContract(Long groupId,
                                                  String phoneNumber,
                                                  String identity,
                                                  String renterName,
                                                  Boolean isDisable,
                                                  String startDate,
                                                  String endDate) {
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

        BaseSpecification<Contracts> contractSpec = new BaseSpecification<>();
        contractSpec.add(new SearchCriteria("contractType", SUBLEASE_CONTRACT, EQUAL));

        if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(groupId)) {
            contractSpec.add(new SearchCriteria("groupId", groupId, EQUAL));
        }

        if (StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)) {
            contractSpec.add(new SearchCriteria("contractStartDate", DateUtils.parse(startDate, DATE_FORMAT_3), GREATER_THAN_EQUAL));

            contractSpec.add(new SearchCriteria("contractEndDate", DateUtils.parse(startDate, DATE_FORMAT_3), LESS_THAN_EQUAL));
        }

        if (org.apache.commons.lang3.ObjectUtils.isNotEmpty(isDisable)) {
            contractSpec.add(new SearchCriteria("contractIsDisable", isDisable, EQUAL));
        }
        if (!searchRenter.isEmpty()) {
            contractSpec.add(new SearchCriteria("renters", searchRenter, IN));
        }

        var listContract = contractRepository.findAll(contractSpec, Sort.by("contractStartDate").descending());

        if (listContract.isEmpty()) return Collections.emptyList();
        listContract.forEach(e -> {
            List<RentersResponse> listRenter = new ArrayList<>(renterService.listMember(e.getRoomId()));
            listRenter.add(renterService.renter(e.getRenters()));

            var group = groupService.group(e.getGroupId());
            var roomContract = RoomContractDTO.of(e,
                    listRenter,
                    assetService.listHandOverAsset(e.getId()),
                    servicesService.listHandOverGeneralService(e.getId()));
            roomContract.setRoom(roomService.getRoom(e.getRoomId()));
            roomContract.setRoomName(roomService.getRoom(e.getRoomId()).getRoomName());
            roomContract.setGroupName(group.getGroupName());
            roomContracts.add(roomContract);
        });
        return roomContracts;
    }

    @Override
    public List<GroupContractDTO> listGroupContract() {
        List<GroupContractDTO> listGroupContract = new ArrayList<>();
        var groupContracts = contractRepository.findAllByContractTypeOrderByContractStartDateDesc(LEASE_CONTRACT);
        if (groupContracts.isEmpty()) return Collections.emptyList();
        groupContracts.forEach(e ->
                listGroupContract.add(GroupContractDTO.of(e,
                        assetService.listHandOverAsset(e.getId()),
                        servicesService.listGeneralService(e.getId()))));
        return listGroupContract;
    }
}
