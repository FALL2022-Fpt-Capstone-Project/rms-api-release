package vn.com.fpt.service.rooms;

import vn.com.fpt.entity.Rooms;
import vn.com.fpt.requests.AdjustRoomPriceRequest;
import vn.com.fpt.requests.RoomsPreviewRequest;
import vn.com.fpt.requests.AddRoomsRequest;
import vn.com.fpt.requests.UpdateRoomRequest;
import vn.com.fpt.responses.AdjustRoomPriceResponse;
import vn.com.fpt.responses.GroupContractedResponse;
import vn.com.fpt.responses.RoomsPreviewResponse;
import vn.com.fpt.responses.RoomsResponse;

import java.util.List;

public interface RoomService {
    List<RoomsResponse> listRoom(Long groupId,
                                 Long groupContractId,
                                 Integer floor,
                                 Integer status,
                                 String name);

    Rooms room (Long id);

    List<Rooms> listRoom(List<Long> roomId);

    List<GroupContractedResponse.RoomLeaseContracted> listRoomLeaseContracted(Long groupId);

    List<GroupContractedResponse.RoomNonLeaseContracted> listRoomLeaseNonContracted(Long groupId);

    List<Rooms> add(List<Rooms> rooms);


    List<Rooms> generateRoom(Integer totalRoom,
                             Integer totalFloor,
                             Integer generalLimitedPeople,
                             Double generalPrice,
                             Double generalArea,
                             String nameConvention,
                             Long groupId,
                             Long operator);

    List<Rooms> previewGenerateRoom(Integer totalRoom,
                                    Integer totalFloor,
                                    Integer generalLimitedPeople,
                                    Double generalPrice,
                                    Double generalArea,
                                    String nameConvention,
                                    Long groupId,
                                    Long operator);

    Rooms add(Rooms rooms);

    Rooms removeRoom(Long id, Long operator);

    List<Rooms> removeRoom(List<Long> id, Long operator);

    Rooms updateRoom(Long id, AddRoomsRequest roomsRequest);

    Rooms updateRoom(Rooms roomsRequest);

    List<Rooms> updateRoom(List<Rooms> rooms);

    Rooms setServiceIndex(Long id, Integer electric, Integer water, Long operator);

    Rooms roomChecker(Long id);



    Rooms emptyRoom(Long id);

    Rooms updateRoomStatus(Long id, Long contractId, Long operator);

    List<Rooms> update(List<UpdateRoomRequest> requests, Long operator);

    RoomsPreviewResponse.SeparationRoomPreview preview(RoomsPreviewRequest request);

    AdjustRoomPriceResponse adjustRoomPrice(AdjustRoomPriceRequest request, Long operator);
}
