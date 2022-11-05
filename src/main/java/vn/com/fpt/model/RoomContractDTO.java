package vn.com.fpt.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.responses.RentersResponse;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoomContractDTO implements Serializable {

    private String contractName;

    private String groupName;

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

    private List<RentersResponse> listRenter;

    private List<HandOverAssetsDTO> listHandOverAsset;

    private List<HandOverGeneralServiceDTO> listHandOverGeneralService;

    public static RoomContractDTO of(Contracts contract,
                                     List<RentersResponse> listRenter,
                                     List<HandOverAssetsDTO> listHandOverAsset,
                                     List<HandOverGeneralServiceDTO> listHandOverGeneralService) {
        var response = RoomContractDTO.builder()
                .contractName(contract.getContractName())
                .contractPrice(contract.getContractPrice())
                .contractDeposit(contract.getContractDeposit())
                .contractBillCycle(contract.getContractBillCycle())
                .contractPaymentCycle(contract.getContractPaymentCycle())
                .contractStartDate(DateUtils.format(contract.getContractStartDate(), DateUtils.DATETIME_FORMAT_CUSTOM))
                .contractEndDate(DateUtils.format(contract.getContractEndDate(), DateUtils.DATETIME_FORMAT_CUSTOM))
                .note(contract.getNote())
                .contractTerm(contract.getContractTerm())
                .contractIsDisable(contract.getContractIsDisable())
                .renters(contract.getRenters())
                .roomId(contract.getRoomId())
                .groupId(contract.getGroupId())
                .contractType(contract.getContractType())
                .build();
        response.setListRenter(listRenter);
        response.setListHandOverAsset(listHandOverAsset);
        response.setListHandOverGeneralService(listHandOverGeneralService);
        return response;
    }
}
