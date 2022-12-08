package vn.com.fpt.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.entity.RackRenters;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.responses.GroupAllResponse;
import vn.com.fpt.responses.GroupContractedResponse;
import vn.com.fpt.responses.RoomsResponse;

import java.io.Serializable;
import java.util.List;

import static vn.com.fpt.common.utils.DateUtils.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GroupContractDTO implements Serializable {
    private Long contractId;
    private String contractName;
    private Double contractPrice;
    private Double contractDeposit;
    private Integer contractBillCycle;
    private Integer contractPaymentCycle;
    private String contractStartDate;
    private String contractEndDate;
    private Integer contractTerm;
    private Boolean contractIsDisable;
    private String note;

    private Long    rackRenter;
    private String  rackRenterFullName;
    private String rackRenterEmail;
    private Boolean gender;
    private String  phoneNumber;
    private String  identityNumber;

    private Long    groupId;
    private String  groupName;
    private Integer totalRoom ;
    private Integer totalFloor;

    private Address address;

    private Integer contractType;

    private List<GeneralServiceDTO> listGeneralService;
    private List<RoomsResponse> listLeaseContractedRoom;

    public static GroupContractDTO of(Contracts contract,
                                      GroupContractedResponse groupContracted,
                                      List<GeneralServiceDTO> generalServices,
                                      List<RoomsResponse> listLeaseContractedRoom,
                                      RackRenters rackRenters) {
        var response = GroupContractDTO.builder()
                .contractId(contract.getId())
                .contractName(contract.getContractName())
                .contractPrice(contract.getContractPrice())
                .contractDeposit(contract.getContractDeposit())
                .contractBillCycle(contract.getContractBillCycle())
                .contractPaymentCycle(contract.getContractPaymentCycle())
                .contractStartDate(format(contract.getContractStartDate(),DATE_FORMAT_3))
                .contractEndDate(format(contract.getContractEndDate(), DATE_FORMAT_3))
                .contractTerm(monthsBetween(contract.getContractEndDate(), contract.getContractStartDate()))
                .note(contract.getNote())
                .contractIsDisable(contract.getContractIsDisable())
                .rackRenter(contract.getRackRenters())
                .groupId(contract.getGroupId())
                .groupName(groupContracted.getGroupName())
                .totalRoom(groupContracted.getTotalRoom())
                .totalFloor(groupContracted.getTotalFloor())
                .listLeaseContractedRoom(listLeaseContractedRoom)
                .contractType(contract.getContractType())
                .rackRenter(rackRenters.getId())
                .rackRenterFullName(rackRenters.getRackRenterFullName())
                .gender(rackRenters.getGender())
                .phoneNumber(rackRenters.getPhoneNumber())
                .identityNumber(rackRenters.getIdentityNumber())
                .rackRenterEmail(rackRenters.getEmail())
                .address(groupContracted.getAddress())
                .build();
        response.setListGeneralService(generalServices);
        return response;
    }
}
