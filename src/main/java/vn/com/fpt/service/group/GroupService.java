package vn.com.fpt.service.group;

import vn.com.fpt.responses.GroupResponse;

import java.util.List;

public interface GroupService {
    GroupResponse group(Long groupId);

    List<GroupResponse> list();
}
