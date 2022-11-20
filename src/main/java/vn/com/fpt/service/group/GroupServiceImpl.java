package vn.com.fpt.service.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import vn.com.fpt.repositories.AddressRepository;
import vn.com.fpt.repositories.ContractRepository;
import vn.com.fpt.repositories.GroupRepository;
import vn.com.fpt.repositories.RoomsRepository;
import vn.com.fpt.responses.GroupResponse;
import vn.com.fpt.responses.RoomsResponse;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.services.ServicesService;

import java.util.ArrayList;
import java.util.List;

import static vn.com.fpt.common.constants.ManagerConstants.LEASE_CONTRACT;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final RoomsRepository roomsRepository;

    private final ServicesService servicesService;

    private final GroupRepository groupRepository;

    private final ContractRepository contractRepository;

    private final AssetService assetService;

    private final AddressRepository addressRepository;

    @Override
    public GroupResponse group(Long groupId) {
        var contract = contractRepository.findByGroupIdAndContractType(groupId, LEASE_CONTRACT);
        var group = groupRepository.findById(contract.getGroupId()).get();
        var rooms = roomsRepository.findAllByGroupId(groupId)
                .stream()
                .map(RoomsResponse::of)
                .toList();
        var address = addressRepository.findById(group.getAddress()).get();
        var generalService = servicesService.listGeneralService(contract.getId());
        var handOverAsset = assetService.listHandOverAsset(contract.getId());

        var totalFloor = roomsRepository.findAllFloorByGroupId(groupId).size() + 1;
        var totalRoom = roomsRepository.findAllRoomsByGroupId(groupId).size() + 1;

        return GroupResponse.of(group,
                address,
                rooms,
                generalService,
                handOverAsset,
                totalFloor,
                totalRoom);
    }

    @Override
    public List<GroupResponse> list() {
        List<GroupResponse> groupResponse = new ArrayList<>();
        groupRepository.findAll()
                .forEach(group -> {
                    var contract = contractRepository.findByGroupIdAndContractType(group.getId(), LEASE_CONTRACT);
                    groupResponse.add(GroupResponse.of(
                            group,
                            addressRepository.findById(group.getAddress()).get(),
                            roomsRepository.findAllByGroupId(group.getId())
                                    .stream()
                                    .map(RoomsResponse::of)
                                    .toList(),
                            servicesService.listGeneralService(contract.getId()),
                            assetService.listHandOverAsset(contract.getId()),
                            roomsRepository.findAllFloorByGroupId(group.getId()).size() + 1,
                            roomsRepository.findAllRoomsByGroupId(group.getId()).size() + 1
                    ));
                });
        return groupResponse;
    }
}
