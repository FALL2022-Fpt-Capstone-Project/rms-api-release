package vn.com.fpt.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.entity.Contracts;

import java.io.Serializable;
import java.util.List;

import static vn.com.fpt.common.utils.DateUtils.monthsBetween;

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

    private String note;

    private Integer contractTerm;

    private Boolean contractIsDisable;

    private Long renters;

    private Long roomId;

    private Long groupId;

    private Integer contractType;

    private List<HandOverAssetsDTO> listHandOverAsset;

    private List<GeneralServiceDTO> listGeneralService;

    public static GroupContractDTO of(Contracts contract,
                                      List<HandOverAssetsDTO> handOverAssets,
                                      List<GeneralServiceDTO> generalServices) {
        var response = GroupContractDTO.builder()
                .contractId(contract.getId())
                .contractName(contract.getContractName())
                .contractPrice(contract.getContractPrice())
                .contractDeposit(contract.getContractDeposit())
                .contractBillCycle(contract.getContractBillCycle())
                .contractPaymentCycle(contract.getContractPaymentCycle())
                .contractStartDate(DateUtils.format(contract.getContractStartDate(), DateUtils.DATE_FORMAT_3))
                .contractEndDate(DateUtils.format(contract.getContractEndDate(), DateUtils.DATE_FORMAT_3))
                .contractTerm(monthsBetween(contract.getContractEndDate(), contract.getContractStartDate()))
                .note(contract.getNote())
                .contractTerm(contract.getContractTerm())
                .contractIsDisable(contract.getContractIsDisable())
                .renters(contract.getRenters())
                .roomId(contract.getRoomId())
                .groupId(contract.getGroupId())
                .contractType(contract.getContractType())
                .build();
        response.setListHandOverAsset(handOverAssets);
        response.setListGeneralService(generalServices);
        return response;
    }
}
