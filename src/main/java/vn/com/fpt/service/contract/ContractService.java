package vn.com.fpt.service.contract;

import vn.com.fpt.entity.Contracts;
import vn.com.fpt.model.GroupContractDTO;
import vn.com.fpt.model.RoomContractDTO;
import vn.com.fpt.requests.EndGroupContractRequest;
import vn.com.fpt.requests.EndRoomContractRequest;
import vn.com.fpt.requests.GroupContractRequest;
import vn.com.fpt.requests.RoomContractRequest;

import java.util.List;

public interface ContractService {
    RoomContractRequest addContract(RoomContractRequest request, Long operator);

    GroupContractRequest addContract(GroupContractRequest request, Long operator);

    GroupContractRequest updateContract(Long groupContractId, GroupContractRequest request, Long operator);

    RoomContractRequest updateContract(Long id, RoomContractRequest request, Long operator);

    List<Contracts> listGroupContract(Long groupId);

    Contracts contract(Long id);

    RoomContractDTO roomContract(Long id);

    List<RoomContractDTO> listRoomContract(Long groupId,
                                           String phoneNumber,
                                           String identity,
                                           String renterName,
                                           Boolean isDisable,
                                           String startDate,
                                           String endDate,
                                           Integer status,
                                           Long duration,
                                           List<Long> roomId);

    List<GroupContractDTO> listGroupContract(String phoneNumber,
                                             String identity,
                                             String name,
                                             Long groupId,
                                             Long contractId,
                                             String startDate,
                                             String endDate,
                                             Boolean isDisable);

    GroupContractDTO groupContract(Long contractId);

    EndRoomContractRequest endRoomContract(EndRoomContractRequest request, Long operator);

    EndGroupContractRequest endGroupContract(EndGroupContractRequest request, Long operator);
}
