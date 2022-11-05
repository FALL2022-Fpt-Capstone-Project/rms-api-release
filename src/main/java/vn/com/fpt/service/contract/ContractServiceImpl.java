package vn.com.fpt.service.contract;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Address;
import vn.com.fpt.entity.BasicAssets;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.entity.Renters;
import vn.com.fpt.model.RoomContractDTO;
import vn.com.fpt.repositories.*;
import vn.com.fpt.requests.RenterRequest;
import vn.com.fpt.requests.RoomContractRequest;
import vn.com.fpt.service.assets.AssetService;
import vn.com.fpt.service.renter.RenterService;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.service.services.ServicesService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static vn.com.fpt.common.utils.DateUtils.*;
import static vn.com.fpt.constants.ErrorStatusConstants.*;
import static vn.com.fpt.constants.ManagerConstants.*;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;
    private final AssetService assetService;

    private final RoomService roomService;

    private final RenterService renterService;

    private final ServicesService servicesService;


    @Override
    @Transactional
    public RoomContractRequest addContract(RoomContractRequest request, Long operator) {
        Contracts contractsInformation = Contracts.addForRenter(request, operator);

        var roomId = request.getRoomId();
        var groupContractId = groupContract(request.getGroupId()).getId();
        var room = roomService.emptyRoom(roomId);

        //parse string to date
        Date startDate = parse(request.getContractStartDate(), DATE_FORMAT_3);
        Date endDate = parse(request.getContractEndDate(), DATE_FORMAT_3);

        assert endDate != null;
        assert startDate != null;
        // kiểm tra ngày bắt đầu và ngày kết thúc
        if (VALIDATE_CONTRACT_TERM(startDate, endDate))
            throw new BusinessException(INVALID_TIME, "Ngày kết thúc không được trước ngày bắt đầu");


        if (ObjectUtils.isEmpty(request.getRenterOldId())) {
            //TODO: Nếu có properties liên quan đến địa chỉ khách ký hợp đồng thì sẽ set vào sau
            var address = Address.add(
                    "",
                    "",
                    "",
                    "",
                    operator);
            var newRenter =
                    renterService.addRenter(Renters.add(RenterRequest.contractOf(request), address, operator));

            // sau khi thêm khách thuê đại diện -> set thông tin đại diện cho hợp đồng để add
            contractsInformation.setRenters(newRenter.getId());
        } else {
            // nếu khách đã tồn tại -> set renter_id vào hợp đồng
            contractsInformation.setRenters(request.getRenterOldId());
        }

        // lưu thông tin hợp đồng
        var addedContract = contractRepository.save(contractsInformation);
        var contractId = addedContract.getId();

        // cập nhập trạng thái phòng trống -> đã thuê D:
        roomService.updateRoomStatus(roomId, contractId, operator);

        // lưu thành viên vào phòng
        if (!request.getListRenter().isEmpty()) {
            if (request.getListRenter().size() + 1 > room.getRoomLimitPeople())
                throw new BusinessException(RENTER_LIMIT, "Giới hạn thành viên trong phòng " + room.getRoomLimitPeople() + " số lượng thành viên hiện tại:" + request.getListRenter().size() + 1);
            request.getListRenter().forEach(e -> {
                e.setRepresent(false);
                e.setRoomId(roomId);
                var address = Address.add(e, operator);
                var renter = Renters.add(e, address, operator);
                renterService.addRenter(renter);
            });
        }

        /* nếu có những tài sản mới không thuộc tài sản bàn giao với tòa ban đầu thì sẽ:
        add thêm vào những tài sản cơ bản, thiết yếu -> add thêm vào tài sản chung của tòa -> add tài sản bàn giao cho phòng
         */

        // lưu tài sản bàn giao
        if (!request.getListHandOverAssets().isEmpty()) {
            request.getListHandOverAssets().forEach(handOverAsset -> {
                //kiểm tra những trang thiết bị không thuộc tòa (những tài sản không thuộc tòa thì id sẽ < 0)
                if (ADDITIONAL_ASSETS(handOverAsset.getAssetsId())) {
                    //thêm tài sản cơ bản, thiết yếu
                    assetService.add(
                            BasicAssets.add(handOverAsset.getAssetsAdditionalName(),
                                    handOverAsset.getAssetsAdditionalType(), operator));

                    //thêm tài sản chung cho tòa
                    assetService.addGeneralAsset(
                            handOverAsset,
                            operator,
                            groupContractId,
                            startDate);

                    //thêm tài sản bàn giao cho phòng
                    assetService.addHandOverAsset(
                            handOverAsset,
                            operator,
                            contractId,
                            startDate);

                    //cập nhập số lượng tài sản của tòa
                    assetService.updateGeneralAssetQuantity(
                            groupContractId,
                            handOverAsset.getAssetsId(),
                            handOverAsset.getHandOverAssetQuantity());
                }
                // nếu không có tài sản mới thì sẽ chỉ thêm tài sản bàn giao cho phòng
                else {
                    assetService.addHandOverAsset(
                            handOverAsset,
                            operator,
                            contractId,
                            startDate);

                    //cập nhập số lượng tài sản của tòa
                    assetService.updateGeneralAssetQuantity(
                            groupContractId,
                            handOverAsset.getAssetsId(),
                            handOverAsset.getHandOverAssetQuantity());
                }
            });
        }
        //lưu dịch vụ chung
        //TODO: Set chỉ số điện nước cho phòng
        if (!request.getListGeneralService().isEmpty()) {
            request.getListGeneralService().forEach(service -> servicesService.addHandOverGeneralService(service, contractId, startDate, operator));
        }
        return request;
    }


    @Override
    public RoomContractRequest updateContract(Long id, RoomContractRequest request, Long operator) {
        var old = contractRepository.findById(id).get();
        Contracts contractsInformation = Contracts.modifyForRenter(old, request, operator);

        var roomId = old.getRoomId();
        var groupContractId = groupContract(old.getGroupId()).getId();
        var room = roomService.emptyRoom(roomId);

        //parse string to date
        Date startDate = parse(request.getContractStartDate(), DATE_FORMAT_3);
        Date endDate = parse(request.getContractEndDate(), DATE_FORMAT_3);

        assert endDate != null;
        assert startDate != null;
        // kiểm tra ngày bắt đầu và ngày kết thúc
        if (VALIDATE_CONTRACT_TERM(endDate, startDate))
            throw new BusinessException(INVALID_TIME, "Ngày kết thúc không được trước ngày bắt đầu");

        if (ObjectUtils.isEmpty(request.getRenterOldId())) {
            //TODO: Nếu có properties liên quan đến địa chỉ khách ký hợp đồng thì sẽ set vào sau

            var address = Address.add(
                    "",
                    "",
                    "",
                    "",
                    operator);

            var newRenter =
                    renterService.addRenter(Renters.add(RenterRequest.contractOf(request), address, operator));

            // sau khi thêm khách thuê đại diện -> set thông tin đại diện cho hợp đồng để add
            contractsInformation.setRenters(newRenter.getId());
        } else {
            // nếu khách đã tồn tại -> set renter_id vào hợp đồng
            renterService.updateRenter(request.getRenterOldId(), RenterRequest.contractOf(request), operator);
        }

        // cập nhập thông tin hợp đồng
        contractRepository.save(contractsInformation);

        // cập nhập trạng thái phòng trống -> đã thuê D:
        // cập nhập thành viên vào phòng
        if (!request.getListRenter().isEmpty()) {
            if (request.getListRenter().size() + 1 > room.getRoomLimitPeople())
                throw new BusinessException(RENTER_LIMIT, "Giới hạn thành viên trong phòng " + room.getRoomLimitPeople() + " số lượng thành viên hiện tại:" + request.getListRenter().size() + 1);
            request.getListRenter().forEach(e -> {
                var address = Address.add(RenterRequest.contractOf(request), operator);
                var renter = Renters.add(RenterRequest.contractOf(request), address, operator);
                e.setRepresent(false);
                e.setRoomId(roomId);
                renterService.updateRenter(e.getId(), renter, operator);
            });
        }

        /* nếu có những tài sản mới không thuộc tài sản bàn giao với tòa ban đầu thì sẽ:
        add thêm vào những tài sản cơ bản, thiết yếu -> add thêm vào tài sản chung của tòa -> add tài sản bàn giao cho phòng
         */

        // lưu tài sản bàn giao
        if (!request.getListHandOverAssets().isEmpty()) {
            request.getListHandOverAssets().forEach(handOverAsset -> {
                //kiểm tra những trang thiết bị không thuộc tòa (những tài sản không thuộc tòa thì id sẽ < 0)
                if (ADDITIONAL_ASSETS(handOverAsset.getAssetsId())) {
                    //thêm tài sản cơ bản, thiết yếu
                    assetService.add(
                            BasicAssets.add(handOverAsset.getAssetsAdditionalName(),
                                    handOverAsset.getAssetsAdditionalType(), operator));

                    //thêm tài sản chung cho tòa
                    assetService.addGeneralAsset(
                            handOverAsset,
                            operator,
                            groupContractId,
                            startDate);

                    //thêm tài sản bàn giao cho phòng
                    assetService.addHandOverAsset(
                            handOverAsset,
                            operator,
                            old.getId(),
                            startDate);

                    //cập nhập số lượng tài sản của tòa
                    assetService.updateGeneralAssetQuantity(
                            groupContractId,
                            handOverAsset.getAssetsId(),
                            handOverAsset.getHandOverAssetQuantity());
                }
                // nếu không có tài sản mới thì sẽ chỉ thêm tài sản bàn giao cho phòng
                else {
                    var oldHandOverAsset = assetService.handOverAsset(handOverAsset.getHandOverAssetId());
                    assetService.updateHandOverAsset(
                            assetService.handOverAsset(handOverAsset.getHandOverAssetId()),
                            handOverAsset,
                            operator,
                            old.getId(),
                            old.getContractStartDate());

                    //cập nhập số lượng tài sản của tòa
                    assetService.updateGeneralAssetQuantity(
                            groupContractId,
                            handOverAsset.getAssetsId(),
                            handOverAsset.getHandOverAssetQuantity() - oldHandOverAsset.getQuantity());
                }
            });
        }
        //lưu dịch vụ chung
        //TODO: Set chỉ số điện nước cho phòng
        if (!request.getListGeneralService().isEmpty()) {
            request.getListGeneralService().forEach(service -> servicesService.updateHandOverGeneralService(
                    service.getHandOverGeneralServiceId(),
                    service,
                    old.getId(),
                    startDate,
                    operator));
        }
        return request;
    }

    @Override
    public Contracts groupContract(Long groupId) {
        return contractRepository.findByGroupIdAndContractType(groupId, LEASE_CONTRACT);
    }

    @Override
    public Contracts contract(Long id) {
        return contractRepository.findById(id).orElseThrow(() -> new BusinessException(CONTRACT_NOT_FOUND, "Hợp đồng contract_id" + id));
    }

    @Override
    public RoomContractDTO roomContract(Long id) {
        var contract = contract(id);
        return RoomContractDTO.of(contract,
                renterService.listRenter(contract.getRoomId()),
                assetService.listHandOverAsset(id),
                servicesService.listHandOverGeneralService(id));
    }

    @Override
    public List<RoomContractDTO> listRoomContract(Long groupId) {
        List<RoomContractDTO> roomContract = new ArrayList<>();
        var listContract = contractRepository.findAllByGroupIdAndContractType(groupId, SUBLEASE_CONTRACT);
        if (listContract.isEmpty()) return null;
        listContract.forEach(e -> {
            roomContract.add(RoomContractDTO.of(e,
                    renterService.listRenter(e.getRoomId()),
                    assetService.listHandOverAsset(e.getId()),
                    servicesService.listHandOverGeneralService(e.getId())));
        });
        return roomContract;
    }
}
