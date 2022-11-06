package vn.com.fpt.service.contract;

import vn.com.fpt.entity.Contracts;
import vn.com.fpt.model.GroupContractDTO;
import vn.com.fpt.model.RoomContractDTO;
import vn.com.fpt.requests.GroupContractRequest;
import vn.com.fpt.requests.RoomContractRequest;

import java.util.List;

public interface ContractService {
    RoomContractRequest addContract(RoomContractRequest request, Long operator);

    GroupContractRequest addContract(GroupContractRequest request, Long operator);

    RoomContractRequest updateContract(Long id, RoomContractRequest request, Long operator);

    Contracts groupContract(Long groupId);

    Contracts contract(Long id);

    RoomContractDTO roomContract(Long id);

    List<RoomContractDTO> listRoomContract(Long groupId,
                                           String phoneNumber,
                                           String identity,
                                           String renterName,
                                           String startDate,
                                           String endDate);

    List<GroupContractDTO> listGroupContract();
}
