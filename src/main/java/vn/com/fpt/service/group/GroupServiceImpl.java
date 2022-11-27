package vn.com.fpt.service.group;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static vn.com.fpt.common.constants.ManagerConstants.LEASE_CONTRACT;

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
        for (GroupContractedResponse groupContractedResponse : listContracted()) {
            if (Objects.equals(groupContractedResponse.getGroupId(), groupId))
                return groupContractedResponse;

        }
        for (GroupNonContractedResponse groupNonContractedResponse : listNonContracted()) {
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
    public List<GroupContractedResponse> listContracted() {
        var listContractedGroup = groupRepository.findAll(Sort.by("id").descending());
        List<GroupContractedResponse> result = new ArrayList<>(Collections.emptyList());

        for (RoomGroups roomGroups : listContractedGroup) {
            var contract = contractRepository.findByGroupIdAndContractType(roomGroups.getId(), LEASE_CONTRACT);
            for (Contracts contracts : contract) {
                if (!contract.isEmpty() && !roomGroups.getIsDisable()) {
                    GroupContractedResponse group = new GroupContractedResponse();
                    var roomLeaseContracted = roomService.listRoomLeaseContracted(roomGroups.getId());
                    var rackRenter = rackRenterRepo.findById(contracts.getRackRenters()).get();
                    group.setGroupId(roomGroups.getId());
                    group.setRackRenterName(rackRenter.getRackRenterFullName());
                    group.setRackRenterPhone(rackRenter.getPhoneNumber());
                    group.setRackRenterGender(rackRenter.getGender());
                    group.setRackRenterIdentity(rackRenter.getIdentityNumber());
                    group.setRackRenterEmail(rackRenter.getEmail());
                    group.setRackRenterNote(rackRenter.getNote());
                    group.setGroupName(roomGroups.getGroupName());
                    group.setDescription(roomGroups.getGroupDescription());
                    group.setTotalRoom(roomsRepository.findAllRoomsByGroupId(roomGroups.getId()).size());
                    group.setTotalFloor(roomsRepository.findAllFloorByGroupId(roomGroups.getId()).size());
                    group.setAddress(addressRepository.findById(roomGroups.getAddress()).get());
                    group.setListRooms(roomService.listRoom(roomGroups.getId(), null, null, null, null));
                    group.setListGeneralService(servicesService.listGeneralServiceByGroupId(roomGroups.getId()));
                    group.setListRoomLeaseContracted(roomLeaseContracted);
                    group.setListRoomNonLeaseContracted(roomService.listRoomLeaseNonContracted(roomGroups.getId()));
                    group.setTotalFloorLeaseContracted(roomsRepository.findAllFloorByGroupContractIdAndGroupId(group.getGroupId(), contracts.getId()).size());
                    group.setTotalRoomLeaseContracted(roomLeaseContracted.size());
                    group.setGroupContracted(true);
                    result.add(group);
                }
            }
        }
        return result;
    }

    @Override
    public List<GroupNonContractedResponse> listNonContracted() {
        var listNonContractedGroup = groupRepository.findAll(Sort.by("id").descending());
        List<GroupNonContractedResponse> result = new ArrayList<>(Collections.emptyList());

        for (RoomGroups roomGroups : listNonContractedGroup) {
            if (contractRepository.findByGroupIdAndContractType(roomGroups.getId(), LEASE_CONTRACT).isEmpty() && !roomGroups.getIsDisable()) {
                GroupNonContractedResponse group = new GroupNonContractedResponse();
                group.setGroupId(roomGroups.getId());
                group.setGroupName(roomGroups.getGroupName());
                group.setDescription(roomGroups.getGroupDescription());
                group.setTotalRoom(roomsRepository.findAllRoomsByGroupId(roomGroups.getId()).size());
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
    public GroupAllResponse listGroup() {
        return GroupAllResponse.of(listContracted(), listNonContracted());
    }

    @Override
    @Transactional
    public String delete(Long id, Long operator) {
        if (ObjectUtils.isNotEmpty(contractRepository.findByGroupIdAndContractType(id, LEASE_CONTRACT)))
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
        var addressId = addressRepository.save(Address.add(
                request.getAddressCity(),
                request.getAddressDistrict(),
                request.getAddressWard(),
                request.getAddressMoreDetail(),
                operator)
        ).getId();

        //Thêm mới 1 nhóm
        var group = groupRepository.save(RoomGroups.add(
                request.getGroupName(),
                request.getDescription(),
                addressId,
                operator)
        );

        //Tự động generate phòng
        roomService.generateRoom(
                request.getTotalRoomPerFloor(),
                request.getTotalFloor(),
                request.getRoomLimitedPeople(),
                request.getRoomPrice(),
                request.getRoomArea(),
                request.getRoomNameConvention(),
                group.getId(),
                operator);

        //add general service
        request.getListGeneralService().forEach(e -> e.setGroupId(group.getId()));
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


}
