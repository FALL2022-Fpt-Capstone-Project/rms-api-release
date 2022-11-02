package vn.com.fpt.service.contract;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.constants.ErrorStatusConstants;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.entity.Renters;
import vn.com.fpt.repositories.*;
import vn.com.fpt.requests.RenterRequest;
import vn.com.fpt.requests.RoomContractRequest;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.services.ServicesService;

import java.util.Date;

import static vn.com.fpt.common.utils.DateUtils.DATETIME_FORMAT_CUSTOM;
import static vn.com.fpt.common.utils.DateUtils.parse;
import static vn.com.fpt.constants.ErrorStatusConstants.INVALID_TIME;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    private final ServicesService servicesService;

    private final HandOverAssetsRepository handOverAssetsRepository;

    private final RenterRepository renterRepository;

    private final HandOverGeneralServicesRepository handOverGeneralServicesRepository;

    private final RoomsRepository roomRepository;

    private final RoomService roomService;


    @Override
    @Transactional
    public RoomContractRequest addContract(RoomContractRequest request, Long operator) {
        Contracts contractsInformation = Contracts.addForRenter(request, operator);

        var room = roomService.emptyRoom(request.getRoomId());

        Date startDate = parse(request.getContractStartDate(), DATETIME_FORMAT_CUSTOM);
        Date endDate = parse(request.getContractEndDate(), DATETIME_FORMAT_CUSTOM);

        // kiểm tra ngày bắt đầu và ngày kết thúc
        if (endDate.compareTo(startDate) < 0)
            throw new BusinessException(INVALID_TIME, "Ngày kết thúc không được trước ngày bắt đầu");


        if (ObjectUtils.isEmpty(request.getRenterOldId())) {
            var address = Address.add("",
                    "",
                    "",
                    "", operator);
            var newRenter = Renters.add(RenterRequest.contractOf(request), address, operator);
            // sau khi thêm khách thuê đại diện -> set thông tin đại diện cho hợp đồng để add
            contractsInformation.setRenters(newRenter.getId());
        } else {
            contractsInformation.setRenters(request.getRenterOldId());
        }
        var addedContract = contractRepository.save(contractsInformation);
        Long contractId = addedContract.getId();

        // cập nhập trạng thái phòng trống -> đã thuê D:
        roomService.updateRoomStatus(request.getRoomId(), contractId, operator);

        // thêm tài sản bàn giao
        if (!request.getListHandOverAssets().isEmpty()) {

        }

        return null;
    }
}
