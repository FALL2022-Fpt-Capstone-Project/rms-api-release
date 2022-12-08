package vn.com.fpt.service.group;

import vn.com.fpt.entity.RoomGroups;
import vn.com.fpt.requests.AddGroupRequest;
import vn.com.fpt.requests.UpdateGroupRequest;
import vn.com.fpt.responses.GroupAllResponse;
import vn.com.fpt.responses.GroupContractedResponse;
import vn.com.fpt.responses.GroupNonContractedResponse;

import java.util.List;

public interface GroupService {
    Object group(Long groupId);

    RoomGroups getGroup(Long groupId);

    List<GroupContractedResponse> listContracted(String city);

    List<GroupNonContractedResponse> listNonContracted(String city);

    GroupAllResponse listGroup(String city);

    String delete(Long id, Long operator);

    AddGroupRequest add(AddGroupRequest request, Long operator);

    String update(Long groupId, UpdateGroupRequest request, Long operator);

}
