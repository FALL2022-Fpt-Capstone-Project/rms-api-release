package vn.com.fpt.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.entity.RackRenters;
import vn.com.fpt.responses.GroupResponse;

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
    private String rackRenterFullName;
    private Boolean gender;
    private String  phoneNumber;
    private String  identityNumber;

    private Long    groupId;
    private Integer totalRoom ;
    private Integer totalFloor;

    private Integer contractType;

    private List<HandOverAssetsDTO> listHandOverAsset;
    private List<GeneralServiceDTO> listGeneralService;

    public static GroupContractDTO of(Contracts contract,
                                      GroupResponse groupResponse,
                                      List<HandOverAssetsDTO> handOverAssets,
                                      List<GeneralServiceDTO> generalServices,
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
                .totalRoom(groupResponse.getTotalRoom())
                .totalFloor(groupResponse.getTotalFloor())
                .contractType(contract.getContractType())
                .rackRenter(rackRenters.getId())
                .rackRenterFullName(rackRenters.getRackRenterFullName())
                .gender(rackRenters.getGender())
                .phoneNumber(rackRenters.getPhoneNumber())
                .identityNumber(rackRenters.getIdentityNumber())
                .build();
        response.setListHandOverAsset(handOverAssets);
        response.setListGeneralService(generalServices);
        return response;
    }
}
