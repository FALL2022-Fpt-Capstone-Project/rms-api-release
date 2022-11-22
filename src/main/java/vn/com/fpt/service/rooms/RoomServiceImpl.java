package vn.com.fpt.service.rooms;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.repositories.RoomsRepository;
import vn.com.fpt.requests.RoomsRequest;
import vn.com.fpt.responses.GroupContractedResponse;
import vn.com.fpt.responses.RoomsResponse;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.contract.ContractService;
import vn.com.fpt.specification.BaseSpecification;
import vn.com.fpt.specification.SearchCriteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static vn.com.fpt.common.constants.ErrorStatusConstants.ROOM_NOT_AVAILABLE;
import static vn.com.fpt.common.constants.ErrorStatusConstants.ROOM_NOT_FOUND;
import static vn.com.fpt.common.constants.ManagerConstants.NOT_RENTED_YET;
import static vn.com.fpt.common.constants.SearchOperation.*;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomsRepository roomsRepository;

    private final AssetService assetService;

    private final ContractService contractService;

    public RoomServiceImpl(RoomsRepository roomsRepository,
                           AssetService assetService,
                           @Lazy ContractService contractService) {
        this.roomsRepository = roomsRepository;
        this.assetService = assetService;
        this.contractService = contractService;
    }

    @Override
    public List<RoomsResponse> listRoom(Long groupId,
                                        Long groupContractId,
                                        Long floor,
                                        Integer status,
                                        String name) {
        BaseSpecification<Rooms> specification = new BaseSpecification<>();
        specification.add(new SearchCriteria("isDisable", false, EQUAL));
        if (ObjectUtils.isNotEmpty(groupId)) {
            specification.add(new SearchCriteria("groupId", groupId, EQUAL));
        }
        if (ObjectUtils.isNotEmpty(floor)) {
            specification.add(new SearchCriteria("roomFloor", floor, EQUAL));
        }
        if (ObjectUtils.isNotEmpty(groupContractId)) {
            specification.add(new SearchCriteria("groupContractId", groupContractId, EQUAL));
        }
        if (ObjectUtils.isNotEmpty(status)) {
            if (status == 1) {
                specification.add(new SearchCriteria("contractId", null, EQUAL));
            } else {
                specification.add(new SearchCriteria("contractId", null, NOT_EQUAL));
            }
        }
        if (StringUtils.isNoneBlank(name)) {
            specification.add(new SearchCriteria("roomName", name, MATCH));
        }
        return roomsRepository.findAll(specification, Sort.by("roomFloor").ascending()).stream().map(RoomsResponse::of).toList();
    }

    @Override
    public Rooms room(Long id) {
        return roomChecker(id);
    }

    @Override
    public List<Rooms> listRoom(List<Long> roomId) {
        return roomsRepository.findAllById(roomId);
    }

    @Override
    public List<GroupContractedResponse.RoomLeaseContracted> listRoomLeaseContracted(Long groupId) {
        var listGroupContract = contractService.listGroupContract(groupId);

        List<GroupContractedResponse.RoomLeaseContracted> result = new ArrayList<>(Collections.emptyList());
        for (Contracts contracts : listGroupContract) {
            var roomLeaseContracted = roomsRepository.findAllByGroupContractIdAndGroupId(contracts.getId(), groupId);
            result.add(
                    GroupContractedResponse.RoomLeaseContracted.of(
                            contracts.getId(),
                            contracts.getGroupId(),
                            roomLeaseContracted,
                            assetService.listHandOverAsset(contracts.getId()),
                            roomLeaseContracted.size() + 1,
                            roomsRepository.findAllFloorByGroupContractIdAndGroupId(contracts.getId(), groupId).size() + 1
                    )
            );
        }
        return result;
    }

    @Override
    public List<GroupContractedResponse.RoomNonLeaseContracted> listRoomLeaseNonContracted(Long groupId) {
        List<GroupContractedResponse.RoomNonLeaseContracted> result = new ArrayList<>(Collections.emptyList());
        var listRoomNonLeaseContracted = roomsRepository.findAllByGroupContractIdNullAndGroupId(groupId);
        result.add(
                GroupContractedResponse.RoomNonLeaseContracted.of(
                        groupId,
                        listRoomNonLeaseContracted,
                        listRoomNonLeaseContracted.size() + 1,
                        roomsRepository.findAllFloorByGroupNonContractAndGroupId(groupId).size() + 1
                )
        );
        return result;
    }

    @Override
    public List<Rooms> add(List<Rooms> rooms) {
        return roomsRepository.saveAll(rooms);
    }

    @Override
    @Transactional
    public List<Rooms> generateRoom(Integer totalRoom,
                                    Integer totalFloor,
                                    Integer generalLimitedPeople,
                                    Double generalPrice,
                                    Double generalArea,
                                    String nameConvention,
                                    Long groupId,
                                    Long operator) {
        return add(previewGenerateRoom(
                totalRoom,
                totalFloor,
                generalLimitedPeople,
                generalPrice,
                generalArea,
                nameConvention,
                groupId,
                operator)
        );
    }

    @Override
    public List<Rooms> previewGenerateRoom(Integer totalRoom,
                                           Integer totalFloor,
                                           Integer generalLimitedPeople,
                                           Double generalPrice,
                                           Double generalArea,
                                           String nameConvention,
                                           Long groupId,
                                           Long operator) {

        List<Rooms> generateRoom = new ArrayList<>();
        // tự động gen phòng theo tầng
        for (int floor = 1; floor <= totalFloor; floor++) {
            for (int room = 1; room <= totalRoom; room++) {
                String roomName = nameConvention + floor + String.format("%02d", room);
                generateRoom.add(Rooms.add(roomName,
                        floor,
                        generalLimitedPeople,
                        groupId,
                        NOT_RENTED_YET,
                        generalPrice,
                        generalArea,
                        operator
                ));
            }
        }
        return generateRoom;
    }

    @Override
    public Rooms add(Rooms rooms) {
        return roomsRepository.save(rooms);
    }

    @Override
    public Rooms removeRoom(Long id, Long operator) {
        return roomsRepository.save(Rooms.delete(room(id), operator));
    }

    @Override
    public Rooms updateRoom(Long id, RoomsRequest roomsRequest) {
        // TODO
        return null;
    }

    @Override
    public Rooms updateRoom(Rooms roomsRequest) {
        return roomsRepository.save(roomsRequest);
    }

    @Override
    public List<Rooms> updateRoom(List<Rooms> rooms) {
        return roomsRepository.saveAll(rooms);
    }

    @Override
    public Rooms setServiceIndex(Long id, Integer electric, Integer water, Long operator) {
        var roomToSet = room(id);
        roomToSet.setRoomCurrentElectricIndex(electric);
        roomToSet.setRoomCurrentWaterIndex(water);

        return roomsRepository.save(Rooms.modify(room(id), roomToSet, operator));
    }
    @Override
    public Rooms roomChecker(Long id) {
        return roomsRepository.findById(id).orElseThrow(() ->
                new BusinessException(ROOM_NOT_FOUND, "Không tìm thấy phòng room_id: " + id));
    }

    @Override
    public Rooms getRoom(Long id) {
        return roomsRepository.findById(id).get();
    }

    @Override
    public Rooms emptyRoom(Long id) {
        var room = roomChecker(id);
        if (Objects.nonNull(room.getContractId()))
            throw new BusinessException(ROOM_NOT_AVAILABLE, "Phòng này đã có người thuê room_id :" + id);
        return room;
    }

    @Override
    public Rooms updateRoomStatus(Long id, Long contractId, Long operator) {
        var room = roomChecker(id);

        room.setContractId(contractId);

        room.setModifiedAt(DateUtils.now());
        room.setModifiedBy(operator);
        return room;
    }

}
