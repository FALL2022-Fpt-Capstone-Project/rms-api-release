package vn.com.fpt.service.rooms;

import org.apache.commons.collections.ListUtils;
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
import vn.com.fpt.repositories.RackRenterRepository;
import vn.com.fpt.repositories.RoomsRepository;
import vn.com.fpt.requests.RoomsPreviewRequest;
import vn.com.fpt.requests.AddRoomsRequest;
import vn.com.fpt.requests.UpdateRoomRequest;
import vn.com.fpt.responses.GroupContractedResponse;
import vn.com.fpt.responses.RoomsPreviewResponse;
import vn.com.fpt.responses.RoomsResponse;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.contract.ContractService;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.services.ServicesService;
import vn.com.fpt.specification.BaseSpecification;
import vn.com.fpt.specification.SearchCriteria;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static vn.com.fpt.common.constants.ErrorStatusConstants.*;
import static vn.com.fpt.common.constants.ManagerConstants.NOT_RENTED_YET;
import static vn.com.fpt.common.constants.SearchOperation.*;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomsRepository roomsRepository;

    private final AssetService assetService;

    private final ContractService contractService;

    private final GroupService groupService;

    private final RackRenterRepository rackRenters;

    private final ServicesService servicesService;

    public RoomServiceImpl(RoomsRepository roomsRepository,
                           AssetService assetService,
                           @Lazy ContractService contractService,
                           @Lazy GroupService service,
                           @Lazy ServicesService servicesService,
                           RackRenterRepository rackRenters) {
        this.roomsRepository = roomsRepository;
        this.assetService = assetService;
        this.contractService = contractService;
        this.groupService = service;
        this.rackRenters = rackRenters;
        this.servicesService = servicesService;
    }

    @Override
    public List<RoomsResponse> listRoom(Long groupId,
                                        Long groupContractId,
                                        Integer floor,
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
        return roomsRepository.findAll(
                        specification,
                        Sort.by("roomFloor").ascending().and(Sort.by("createdAt").descending())
                )
                .stream().map(RoomsResponse::of).toList();
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
        var group = groupService.getGroup(groupId);
        List<GroupContractedResponse.RoomLeaseContracted> result = new ArrayList<>(Collections.emptyList());
        for (Contracts contracts : listGroupContract) {
            var roomLeaseContracted = roomsRepository.findAllByGroupContractIdAndGroupId(contracts.getId(), groupId);
            var rackRenter = rackRenters.findById(contracts.getRackRenters()).get();
            result.add(
                    GroupContractedResponse.RoomLeaseContracted.of(
                            contracts.getId(),
                            contracts.getGroupId(),
                            group.getGroupName(),
                            rackRenter.getRackRenterFullName(),
                            rackRenter.getGender(),
                            rackRenter.getPhoneNumber(),
                            rackRenter.getIdentityNumber(),
                            contracts.getContractPrice(),
                            contracts.getContractDeposit(),
                            roomLeaseContracted,
                            servicesService.listGeneralServiceByGroupId(groupId),
                            assetService.listHandOverAsset(contracts.getId()),
                            roomLeaseContracted.size(),
                            roomsRepository.findAllFloorByGroupContractIdAndGroupId(contracts.getId(), groupId).size()
                    )
            );
        }
        return result;
    }

    @Override
    public List<GroupContractedResponse.RoomNonLeaseContracted> listRoomLeaseNonContracted(Long groupId) {
        List<GroupContractedResponse.RoomNonLeaseContracted> result = new ArrayList<>(Collections.emptyList());
        var group = groupService.getGroup(groupId);
        var listRoomNonLeaseContracted = roomsRepository.findAllByGroupContractIdNullAndGroupId(groupId);
        result.add(
                GroupContractedResponse.RoomNonLeaseContracted.of(
                        groupId,
                        group.getGroupName(),
                        listRoomNonLeaseContracted,
                        servicesService.listGeneralServiceByGroupId(groupId),
                        listRoomNonLeaseContracted.size(),
                        roomsRepository.findAllFloorByGroupNonContractAndGroupId(groupId).size()
                )
        );
        return result;
    }

    @Override
    public List<Rooms> add(List<Rooms> rooms) {
        rooms.forEach(e -> {
            if (checkDuplicateRoomName(
                    roomsRepository.findAllByGroupIdAndIsDisableFalse(e.getGroupId()),
                    e.getRoomName()))
                throw new BusinessException(DUPLICATE_NAME, "Tên phòng bị trùng: " + e.getRoomName());
        });
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
        if (Objects.nonNull(roomChecker(id).getContractId()))
            throw new BusinessException(ROOM_NOT_AVAILABLE, "Phòng " + roomChecker(id).getRoomName() + " đã có người thuê. Không thể xóa!!");
        return roomsRepository.save(Rooms.delete(room(id), operator));
    }

    @Override
    public List<Rooms> removeRoom(List<Long> id, Long operator) {
        var checkEmptyRoom = roomsRepository.findAllByIdInAndContractIdNotNull(id);
        if (!checkEmptyRoom.isEmpty()) {
            String var1 = String.join(", ", checkEmptyRoom.stream().map(Rooms::getRoomName).toList());
            throw new BusinessException(ROOM_NOT_AVAILABLE, "Phòng " + var1 + " đã có người thuê. Không thể xóa!!");
        }
        List<Rooms> toDelete = new ArrayList<>(Collections.emptyList());
        id.forEach(e -> toDelete.add(Rooms.delete(room(e), operator)));
        return roomsRepository.saveAll(toDelete);
    }

    @Override
    public Rooms updateRoom(Long id, AddRoomsRequest roomsRequest) {
        // TODO
        return null;
    }

    @Override
    public Rooms updateRoom(Rooms roomsRequest) {
        if (checkDuplicateRoomName(
                roomsRepository.findByGroupIdAndIdNot(room(roomsRequest.getId()).getGroupId(), roomsRequest.getId()),
                roomsRequest.getRoomName()))
            throw new BusinessException(DUPLICATE_NAME, "Tên phòng bị trùng: " + roomsRequest.getRoomName());
        return roomsRepository.save(roomsRequest);
    }

    @Override
    public List<Rooms> updateRoom(List<Rooms> rooms) {
        rooms.forEach(e -> {
            if (checkDuplicateRoomName(
                    roomsRepository.findAllByGroupIdAndIsDisableFalse(room(e.getId()).getGroupId()),
                    e.getRoomName()))
                throw new BusinessException(DUPLICATE_NAME, "Tên phòng bị trùng: " + e.getRoomName());
        });
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

    @Override
    public List<Rooms> update(List<UpdateRoomRequest> requests, Long operator) {
        List<Rooms> listRoomToUpdate = new ArrayList<>(Collections.emptyList());
        var listExitedRoom = roomsRepository.findAllByGroupIdAndIdNotIn
                (
                        room(requests.get(0).getRoomId()).getGroupId(),
                        requests.stream().map(UpdateRoomRequest::getRoomId).toList()
                );
        requests.forEach(e -> {
                    if (checkDuplicateRoomName(
                            listExitedRoom,
                            e.getRoomName()))
                        throw new BusinessException(DUPLICATE_NAME, "Tên phòng bị trùng: " + e.getRoomName());
                    listRoomToUpdate.add(
                            Rooms.modify(
                                    room(e.getRoomId()),
                                    e.getRoomName(),
                                    e.getRoomFloor(),
                                    e.getRoomLimitPeople(),
                                    e.getRoomPrice(),
                                    e.getRoomArea(),
                                    operator));
                }
        );
        return roomsRepository.saveAll(listRoomToUpdate);
    }

    @Override
    public List<RoomsPreviewResponse> preview(RoomsPreviewRequest request) {
        Map<Integer, RoomsResponse[]> temp1 = new HashMap<>();

        for (Integer i : request.getListFloor()) {
            RoomsResponse[] temp2 = null;
            if (Objects.isNull(listRoom(request.getGroupId(), null, i, null, null))) {
                temp1.put(i, null);
            } else {
                for (RoomsResponse rs : listRoom(request.getGroupId(), null, i, null, null)) {
                    if (Objects.isNull(temp2)) temp2 = new RoomsResponse[99];

                    String roomNumber = rs.getRoomName().split("(?<=\\D)(?=\\d)")[1]; //A201 -> 201
                    if (checkNoobRoomName(roomNumber)) {
                        var temp3 = roomNumber.split("");
                        int index = Integer.parseInt(temp3[roomNumber.length() - 2] + temp3[roomNumber.length() - 1]);
                        if (!Objects.isNull(temp2)) {
                            if (temp2[index] != null) {
                                int var1 = 1;
                                while (true) {
                                    if (Objects.isNull(temp2[var1])) {
                                        temp2[var1] = rs;
                                        break;
                                    }
                                }
                            } else {
                                temp2[index] = rs;
                            }
                        }
                    }
                }
            }
            temp1.put(i, temp2);
        }
        List<RoomsPreviewResponse> gen = new ArrayList<>(Collections.emptyList());

        List<RoomsPreviewResponse> oldRoom1 = new ArrayList<>(Collections.emptyList());
        var oldRoom2 = roomsRepository.findAllByRoomFloorInAndGroupIdAndIsDisableIs(request.getListFloor(), request.getGroupId(), false);
        oldRoom1.addAll(oldRoom2.stream().map(RoomsPreviewResponse::old).toList());

        for (Integer i : request.getListFloor()) {

            var room = temp1.get(i);
            if (ObjectUtils.isEmpty(temp1)) {
                for (int y = 1; y <= request.getTotalRoomPerFloor(); y++) {
                    gen.add(new RoomsPreviewResponse());
                }
            } else {
                boolean flag = false;
                if (room[1] == null) {
                    int var2 = 1;
                    for (int x = var2; x < request.getTotalRoomPerFloor() - 1; x++) {
                        if (room[x] == null) {
                            var2++;
                        }
                    }
                    if (request.getTotalRoomPerFloor() - var2 == 0 || (double) var2 / request.getTotalRoomPerFloor() >= 0.5) {
                        // check duplicate
                        for (int y5 = 1; y5 <= request.getTotalRoomPerFloor(); y5++) {
                            String roomName = request.getRoomNameConvention() + i + String.format("%02d", y5);
                            gen.add(new RoomsPreviewResponse(
                                    null,
                                    roomName,
                                    i,
                                    request.getRoomLimitedPeople(),
                                    0,
                                    0,
                                    request.getGroupId(),
                                    null,
                                    null,
                                    request.getRoomPrice(),
                                    request.getRoomArea(),
                                    false,
                                    false,
                                    checkDuplicateRoomName(oldRoom2, roomName)));
                        }
                        flag = true;
                    }
                }
                if (room[1] != null || !flag) {
                    for (int y0 = 2; y0 < room.length; y0++) {
                        if (y0 < 99 - request.getTotalRoomPerFloor()) {
                            if (room[y0] == null) {
                                flag = false;
                                int var2 = 0;
                                for (int x = y0; x < request.getTotalRoomPerFloor() + y0 - 1; x++) {
                                    if (room[x] == null) {
                                        var2++;
                                    }
                                }
                                if (request.getTotalRoomPerFloor() - var2 == 0 || (double) var2 / request.getTotalRoomPerFloor() >= 0.5) {
                                    // check duplicate
                                    for (int y1 = y0; y1 < request.getTotalRoomPerFloor() + y0; y1++) {
                                        String roomName = request.getRoomNameConvention() + i + String.format("%02d", y1);
                                        gen.add(new RoomsPreviewResponse(
                                                null,
                                                roomName,
                                                i,
                                                request.getRoomLimitedPeople(),
                                                0,
                                                0,
                                                request.getGroupId(),
                                                null,
                                                null,
                                                request.getRoomPrice(),
                                                request.getRoomArea(),
                                                false,
                                                false,
                                                checkDuplicateRoomName(oldRoom2, roomName)));
                                    }
                                    flag = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!flag) {
                        for (int y4 = 1; y4 < request.getTotalRoomPerFloor(); y4++) {
                            String roomName = request.getRoomNameConvention() + i + String.format("%02d", y4);
                            gen.add(new RoomsPreviewResponse(
                                    null,
                                    roomName,
                                    i,
                                    request.getRoomLimitedPeople(),
                                    0,
                                    0,
                                    request.getGroupId(),
                                    null,
                                    null,
                                    request.getRoomPrice(),
                                    request.getRoomArea(),
                                    false,
                                    false,
                                    checkDuplicateRoomName(oldRoom2, roomName)));
                        }
                    }
                }
            }
        }
        return ListUtils.union(oldRoom2, gen);
    }

    public boolean checkNoobRoomName(String roomName) {
        Pattern pattern1 = Pattern.compile("^[0-9]{3}$", Pattern.CASE_INSENSITIVE);
        Pattern pattern2 = Pattern.compile("^[0-9]{4}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(roomName);
        Matcher matcher2 = pattern2.matcher(roomName);

        if (matcher1.matches()) {
            return true;
        } else if (matcher2.matches()) {
            return true;
        }
        return false;
    }

    public boolean checkDuplicateRoomName(List<Rooms> rooms, String roomName) {
        for (Rooms room : rooms) {
            if (!Objects.isNull(room)) {
                if (roomName.equalsIgnoreCase(room.getRoomName())) return true;
            }
        }
        return false;
    }

}
