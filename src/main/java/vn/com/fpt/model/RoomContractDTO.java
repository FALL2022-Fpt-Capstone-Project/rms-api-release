package vn.com.fpt.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.responses.RentersResponse;
import vn.com.fpt.responses.RoomsResponse;

import java.io.Serializable;
import java.util.List;

import static vn.com.fpt.common.utils.DateUtils.monthsBetween;


@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RoomContractDTO implements Serializable {

    private Long contractId;

    private String contractName;

    private String groupName;

    private Rooms room;

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

    private String roomName;

    private Long groupId;

    private Integer contractType;

    private List<RentersResponse> listRenter;

    private List<RoomAssetDTO> listRoomAsset;

    private List<HandOverGeneralServiceDTO> listHandOverGeneralService;

    private List<RoomsResponse> listRoom;

    public static RoomContractDTO of(Contracts contract,
                                     List<RentersResponse> listRenter,
                                     List<RoomAssetDTO> listRoomAsset,
                                     List<HandOverGeneralServiceDTO> listHandOverGeneralService) {
        var response = RoomContractDTO.builder()
                .contractId(contract.getId())
                .contractName(contract.getContractName())
                .contractPrice(contract.getContractPrice())
                .contractDeposit(contract.getContractDeposit())
                .contractBillCycle(contract.getContractBillCycle())
                .contractPaymentCycle(contract.getContractPaymentCycle())
                .contractStartDate(DateUtils.format(contract.getContractStartDate(), DateUtils.DATETIME_FORMAT_CUSTOM))
                .contractEndDate(DateUtils.format(contract.getContractEndDate(), DateUtils.DATETIME_FORMAT_CUSTOM))
                .note(contract.getNote())
                .contractTerm(monthsBetween(contract.getContractEndDate(), contract.getContractStartDate()))
                .contractIsDisable(contract.getContractIsDisable())
                .renters(contract.getRenters())
                .roomId(contract.getRoomId())
                .groupId(contract.getGroupId())
                .contractType(contract.getContractType())
                .build();
        response.setListRenter(listRenter);
        response.setListRoomAsset(listRoomAsset);
        response.setListHandOverGeneralService(listHandOverGeneralService);
        return response;
    }
}
