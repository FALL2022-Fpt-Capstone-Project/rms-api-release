package vn.com.fpt.service.group;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.*;
import vn.com.fpt.repositories.*;
import vn.com.fpt.requests.AddGroupRequest;
import vn.com.fpt.requests.UpdateGroupRequest;
import vn.com.fpt.responses.GroupAllResponse;
import vn.com.fpt.responses.GroupContractedResponse;
import vn.com.fpt.responses.GroupNonContractedResponse;
import vn.com.fpt.responses.RoomsResponse;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.services.ServicesService;
import vn.com.fpt.specification.BaseSpecification;
import vn.com.fpt.specification.SearchCriteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static vn.com.fpt.common.constants.ErrorStatusConstants.*;
import static vn.com.fpt.common.constants.ManagerConstants.*;
import static vn.com.fpt.common.constants.SearchOperation.IN;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final RoomsRepository roomsRepository;

    private final RoomService roomService;

    private final ServicesService servicesService;

    private final GroupRepository groupRepository;

    private final ContractRepository contractRepository;

    private final AssetService assetService;

    private final RackRenterRepository rackRenterRepo;

    private final AddressRepository addressRepository;

    @Override
    public Object group(Long groupId) {
        for (GroupContractedResponse groupContractedResponse : listContracted(null)) {
            if (Objects.equals(groupContractedResponse.getGroupId(), groupId))
                return groupContractedResponse;

        }
        for (GroupNonContractedResponse groupNonContractedResponse : listNonContracted(null)) {
            if (Objects.equals(groupNonContractedResponse.getGroupId(), groupId))
                return groupNonContractedResponse;
        }
        throw new BusinessException("Không tìm thấy nhóm phòng group_id: " + groupId);
    }

    @Override
    public RoomGroups getGroup(Long groupId) {
        return groupRepository.findById(groupId).get();
    }

    @Override
    public List<GroupContractedResponse> listContracted(String city) {
        List<Long> addressId = null;
        if (StringUtils.isNotEmpty(city)) {
            addressId = addressRepository.findAllByAddressCityEqualsIgnoreCase(city).stream().map(Address::getId).toList();
        }
        BaseSpecification<RoomGroups> contractSpec = new BaseSpecification<>();

        if (ObjectUtils.isNotEmpty(addressId)) {
            contractSpec.add(SearchCriteria.of("address", addressId, IN));
        }

        var listContractedGroup = groupRepository.findAll(contractSpec, Sort.by("id").descending());
        List<GroupContractedResponse> result = new ArrayList<>(Collections.emptyList());
        for (RoomGroups roomGroups : listContractedGroup) {
            if (!roomGroups.getIsDisable() && !contractRepository.findAllByGroupIdAndContractType(roomGroups.getId(), LEASE_CONTRACT).isEmpty()) {
                GroupContractedResponse group = new GroupContractedResponse();
                var roomLeaseContracted = roomService.listRoomLeaseContracted(roomGroups.getId());
                group.setGroupId(roomGroups.getId());
                group.setGroupName(roomGroups.getGroupName());
                group.setDescription(roomGroups.getGroupDescription());
                group.setTotalRoom(roomsRepository.findAllRoomsByGroupId(roomGroups.getId()).size());
                group.setTotalFloor(roomsRepository.findAllFloorByGroupId(roomGroups.getId()).size());
                group.setAddress(addressRepository.findById(roomGroups.getAddress()).get());
                group.setListRooms(roomService.listRoom(roomGroups.getId(), null, null, null, null));
                group.setListGeneralService(servicesService.listGeneralServiceByGroupId(roomGroups.getId()));
                group.setListRoomLeaseContracted(roomLeaseContracted);
                group.setListRoomNonLeaseContracted(roomService.listRoomLeaseNonContracted(roomGroups.getId()));
                group.setTotalFloorLeaseContracted(roomsRepository.findAllFloorByGroupContractIdNotNullAndGroupId(group.getGroupId()).size());
                group.setTotalRoomLeaseContracted(roomLeaseContracted.size());
                group.setGroupContracted(true);
                result.add(group);
            }
        }
        return result;
    }

    @Override
    public List<GroupNonContractedResponse> listNonContracted(String city) {
        List<Long> addressId = null;
        if (StringUtils.isNotEmpty(city)) {
            addressId = addressRepository.findAllByAddressCityEqualsIgnoreCase(city).stream().map(Address::getId).toList();
        }

        BaseSpecification<RoomGroups> contractSpec = new BaseSpecification<>();
        if (ObjectUtils.isNotEmpty(addressId)) {
            contractSpec.add(SearchCriteria.of("address", addressId, IN));
        }
        var listNonContractedGroup = groupRepository.findAll(contractSpec, Sort.by("id").descending());
        List<GroupNonContractedResponse> result = new ArrayList<>(Collections.emptyList());

        for (RoomGroups roomGroups : listNonContractedGroup) {
            if (contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse(roomGroups.getId(), LEASE_CONTRACT).isEmpty() && !roomGroups.getIsDisable()) {
                GroupNonContractedResponse group = new GroupNonContractedResponse();
                group.setGroupId(roomGroups.getId());
                group.setGroupName(roomGroups.getGroupName());
                group.setDescription(roomGroups.getGroupDescription());
                group.setTotalRoom(roomsRepository.findAllByGroupIdAndIsDisableIsFalse(roomGroups.getId()).size());
                group.setTotalFloor(roomsRepository.findAllFloorByGroupId(roomGroups.getId()).size());
                group.setAddress(addressRepository.findById(roomGroups.getAddress()).get());
                group.setListRooms(roomService.listRoom(roomGroups.getId(), null, null, null, null));
                group.setListGeneralService(servicesService.listGeneralServiceByGroupId(roomGroups.getId()));
                group.setGroupContracted(false);
                result.add(group);
            }
        }
        return result;
    }

    @Override
    public GroupAllResponse listGroup(String city) {
        return GroupAllResponse.of(listContracted(city), listNonContracted(city));
    }

    @Override
    @Transactional
    public String delete(Long id, Long operator) {
        if (ObjectUtils.isNotEmpty(contractRepository.findByGroupIdAndContractTypeAndContractIsDisableIsFalse(id, LEASE_CONTRACT)))
            throw new BusinessException("Xóa thất bại. Do nhóm phòng đang tồn tại hợp đồng. Tính năng đang phát triển!!");
        var group = groupRepository.findById(id).get();
        // Xóa tòa
        groupRepository.save(RoomGroups.delete(group, operator));

        //Xóa tất các các phòng thuộc tòa
        roomService.listRoom(id, null, null, null, null)
                .forEach(e -> roomService.removeRoom(e.getRoomId(), operator));
        //Xóa service chung thuộc tòa
        servicesService.removeGroupGeneralService(id);

        return "Xóa thành công";
    }

    @Override
    @Transactional
    public AddGroupRequest add(AddGroupRequest request, Long operator) {
        if (request.getTotalRoomPerFloor() * request.getTotalFloor() >= 40)
            throw new BusinessException(INVALID_TOTAL, "Tổng số phòng: " + (request.getTotalRoomPerFloor() * request.getTotalFloor()));
        var addressId = addressRepository.save(Address.add(
                request.getAddressCity(),
                request.getAddressDistrict(),
                request.getAddressWard(),
                request.getAddressMoreDetail(),
                operator)
        ).getId();

        //Thêm mới 1 nhóm
        var existedGroup = groupRepository.findAllByIsDisableIsFalse();
        if (checkDuplicateGroupName(existedGroup, request.getGroupName()))
            throw new BusinessException(DUPLICATE_NAME, "Tên tòa bị trùng: " + request.getGroupName());
        var group = groupRepository.save(RoomGroups.add(
                request.getGroupName(),
                request.getDescription(),
                addressId,
                operator)
        );

        //Tự động generate phòng
        var listRoomId = roomService.generateRoom(
                request.getTotalRoomPerFloor(),
                request.getTotalFloor(),
                request.getRoomLimitedPeople(),
                request.getRoomPrice(),
                request.getRoomArea(),
                request.getRoomNameConvention(),
                group.getId(),
                operator).stream().map(Rooms::getId).toList();
        List<RoomAssets> listRoomAsset = new ArrayList<>(Collections.emptyList());

        if (ObjectUtils.isNotEmpty(request.getListAdditionalAsset())) {
            for (Long lri : listRoomId) {
                request.getListAdditionalAsset().forEach(e -> {
                    if (!ObjectUtils.isEmpty(e)) {
                        listRoomAsset.add(RoomAssets.add(
                                e.getAssetName(),
                                ObjectUtils.isEmpty(e.getAssetQuantity()) ? DEFAULT_ASSET_QUANTITY : e.getAssetQuantity(),
                                e.getAssetTypeId(),
                                lri,
                                operator));
                    }
                });
            }
        }
        if (ObjectUtils.isNotEmpty(request.getListAsset())) {
            for (Long lri : listRoomId) {
                request.getListAsset().forEach(e -> {
                    if (!ObjectUtils.isEmpty(e))
                        listRoomAsset.add(RoomAssets.add(
                                e.getAssetName(),
                                ObjectUtils.isEmpty(e.getAssetQuantity()) ? DEFAULT_ASSET_QUANTITY : e.getAssetQuantity(),
                                e.getAssetTypeId(),
                                lri,
                                operator));

                });
            }
        }
        if (!listRoomAsset.isEmpty()) assetService.add(listRoomAsset);
        //add general service
        request.getListGeneralService().forEach(e -> {
            if (e.getServiceId() != SERVICE_ELECTRIC || e.getServiceId() != SERVICE_WATER) {
                if (e.getGeneralServiceType().equals(SERVICE_TYPE_METER)) throw new BusinessException(INVALID_TYPE, "Cách tính tiền dịch vụ không hợp lệ!!");
            }
            e.setGroupId(group.getId());
        });
        servicesService.addGeneralService(request.getListGeneralService(), operator);

        return request;
    }

    @Override
    @Transactional
    public String update(Long groupId,
                         UpdateGroupRequest request,
                         Long operator) {
        var oldGroup = groupRepository.findById(groupId).get();

        var oldAddress = addressRepository.findById(oldGroup.getAddress()).get();
        var newAddress = new Address().modify(
                oldAddress,
                request.getAddressCity(),
                request.getAddressDistrict(),
                request.getAddressWard(),
                request.getAddressMoreDetail(),
                operator);
        addressRepository.save(newAddress);
        if (checkDuplicateGroupName(groupRepository.findAllByIdNotAndIsDisableIsFalse(groupId), request.getGroupName()))
            throw new BusinessException(DUPLICATE_NAME, "Tên tòa bị trùng: " + request.getGroupName());
        var newGroup = RoomGroups.modify(
                oldGroup,
                request.getGroupName(),
                request.getDescription(),
                operator);
        groupRepository.save(newGroup);

        var room = roomService.listRoom(groupId, null, null, null, null);
        List<Rooms> newRoom = new ArrayList<>(Collections.emptyList());
        for (RoomsResponse old : room) {
            newRoom.add(Rooms.modify(
                    roomsRepository.findById(old.getRoomId()).get(),
                    old.getRoomName(),
                    old.getRoomFloor(),
                    old.getRoomLimitPeople(),
                    old.getRoomPrice(),
                    old.getRoomArea(),
                    operator
            ));
        }
        roomsRepository.saveAll(newRoom);

        return "Cập nhập nhóm phòng thành công!!";
    }

    private boolean checkDuplicateGroupName(List<RoomGroups> roomGroups, String groupName) {
        for (RoomGroups roomGroup : roomGroups) {
            if (roomGroup.getGroupName().equalsIgnoreCase(groupName)) return true;
        }
        return false;
    }


}
