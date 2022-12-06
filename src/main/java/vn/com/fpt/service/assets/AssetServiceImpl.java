package vn.com.fpt.service.assets;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import vn.com.fpt.entity.AssetTypes;
import vn.com.fpt.entity.BasicAssets;
import vn.com.fpt.entity.RoomAssets;
import vn.com.fpt.model.BasicAssetDTO;
import vn.com.fpt.model.RoomAssetDTO;
import vn.com.fpt.repositories.AssetTypesRepository;
import vn.com.fpt.repositories.BasicAssetRepository;
import vn.com.fpt.repositories.RoomAssetRepository;
import vn.com.fpt.requests.BasicAssetsRequest;import vn.com.fpt.requests.RoomAssetsRequest;
import vn.com.fpt.service.contract.ContractService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

import static vn.com.fpt.common.constants.ManagerConstants.DEFAULT_ASSET_QUANTITY;


@Service
public class AssetServiceImpl implements AssetService {
    private final EntityManager entityManager;

    private final AssetTypesRepository assetTypesRepository;

    private final BasicAssetRepository basicAssetRepository;

    private final ContractService contractService;

    private final RoomAssetRepository roomAssetRepository;

    public AssetServiceImpl(EntityManager entityManager,
                            AssetTypesRepository assetTypesRepository,
                            BasicAssetRepository basicAssetRepository,
                            @Lazy ContractService contractService,
                            RoomAssetRepository roomAssetRepository) {
        this.entityManager = entityManager;
        this.assetTypesRepository = assetTypesRepository;
        this.basicAssetRepository = basicAssetRepository;
        this.contractService = contractService;
        this.roomAssetRepository = roomAssetRepository;
    }



    @Override
    public List<AssetTypes> listAssetType() {
        return assetTypesRepository.findAll();
    }

    @Override
    public List<BasicAssetDTO> listBasicAsset() {
        StringBuilder selectBuild = new StringBuilder("SELECT ");
        selectBuild.append("mba.asset_id, ");
        selectBuild.append("mba.asset_name, ");
        selectBuild.append("mba.asset_type_id, ");
        selectBuild.append("mat.asset_type_name, ");
        selectBuild.append("mat.asset_type_show_name ");

        StringBuilder fromBuild = new StringBuilder("FROM ");
        fromBuild.append("manager_basic_assets mba ");
        fromBuild.append("INNER JOIN manager_asset_types mat ON mat.asset_types_id = mba.asset_type_id");

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .toString();

        Query query = entityManager.createNativeQuery(queryBuild, BasicAssetDTO.SQL_RESULT_SET_MAPPING);

        return query.getResultList();
    }

    @Override
    public List<RoomAssetDTO> listRoomAsset(Long roomId, Long assetType) {
        StringBuilder selectBuild = new StringBuilder("SELECT ");
        selectBuild.append("mra.room_asset_id,");
        selectBuild.append("mr.room_id,");
        selectBuild.append("mr.group_id,");
        selectBuild.append("mra.asset_name,");
        selectBuild.append("mra.asset_quantity,");
        selectBuild.append("mra.asset_type_id,");
        selectBuild.append("mat.asset_type_name,");
        selectBuild.append("mat.asset_type_show_name ");

        StringBuilder fromBuild = new StringBuilder("FROM ");
        fromBuild.append("manager_room_assets mra ");
        fromBuild.append("INNER JOIN manager_rooms mr on mra.room_id = mr.room_id ");
        fromBuild.append("INNER JOIN manager_asset_types mat on mra.asset_type_id = mat.asset_types_id ");

        Map<String, Object> params = new HashMap<>();

        StringBuilder whereBuild = new StringBuilder("WHERE 1=1 ");
        if (ObjectUtils.isNotEmpty(roomId)) {
            params.put("roomId", roomId);
            whereBuild.append("AND mr.room_id = :roomId ");
        }

        if (ObjectUtils.isNotEmpty(assetType)) {
            params.put("assetTypeId", assetType);
            whereBuild.append("AND mra.asset_type_id = :assetTypeId ");
        }

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .append(whereBuild)
                .toString();

        Query query = entityManager.createNativeQuery(queryBuild, RoomAssetDTO.SQL_RESULT_SETS_MAPPING);
        if (!params.isEmpty()) params.forEach(query::setParameter);
        return query.getResultList();
    }

    @Override
    public BasicAssets basicAssets(Long id) {
        return basicAssetRepository.findById(id).get();
    }

    @Override
    public BasicAssets add(BasicAssetsRequest request, Long operator) {
        return basicAssetRepository.save(BasicAssets.add(request, operator));
    }

    @Override
    public BasicAssets add(BasicAssets request) {
        return basicAssetRepository.save(BasicAssets.add(request));
    }

    @Override
    public List<RoomAssets> add(List<RoomAssets> listRoomAssets) {
        return roomAssetRepository.saveAll(listRoomAssets);
    }

    @Override
    public List<RoomAssets> roomAdd(List<RoomAssetsRequest> request, Long operator) {
        Set<Long> roomIds = request.stream().map(RoomAssetsRequest::getRoomId).collect(Collectors.toSet());
        var listAsset = new ArrayList<RoomAssets>(Collections.emptyList());
        for (Long roomId : roomIds) {
            var listExitedRoomAsset = roomAssetRepository.findAllByRoomId(roomId);
            var room = roomAssetRepository.findAllByRoomId(roomId);
            if (listExitedRoomAsset.isEmpty())
                return roomAssetRepository.saveAll(request.stream().map(e -> RoomAssets.add(
                        e.getAssetName(),
                        e.getAssetQuantity(),
                        e.getAssetTypeId(),
                        roomId,
                        operator)).toList());
            for (RoomAssetsRequest rar : request) {
                listAsset.addAll(listExitedRoomAsset.stream().filter(e ->
                        e.getAssetName().trim().replaceAll(" +", "\\s").equalsIgnoreCase(rar.getAssetName().trim().replaceAll(" +", "\\s"))
                ).map(e -> RoomAssets.modify(
                        e,
                        e.getAssetName(),
                        e.getAssetQuantity() + 1,
                        rar.getAssetTypeId(),
                        roomId,
                        operator
                )).toList());

                listAsset.addAll(listExitedRoomAsset.stream().filter(e ->
                        !e.getAssetName().trim().replaceAll(" +", "\\s").equalsIgnoreCase(rar.getAssetName().trim().replaceAll(" +", "\\s"))
                ).map(e -> RoomAssets.add(
                        rar.getAssetName(),
                        ObjectUtils.isEmpty(rar.getAssetQuantity()) ? DEFAULT_ASSET_QUANTITY : rar.getAssetQuantity(),
                        rar.getAssetTypeId(),
                        roomId,
                        operator
                )).toList());
            }
        }
        return roomAssetRepository.saveAll(listAsset);
    }

    @Override
    public BasicAssets update(Long id, BasicAssetsRequest request, Long operator) {
        var oldAsset = basicAssetRepository.findById(id).get();
        return basicAssetRepository.save(BasicAssets.modify(oldAsset, request, operator));
    }

    @Override
    public String deleteBasicAsset(Long id) {
        return null;
    }

    @Override
    public void deleteRoomAsset(Long roomId) {
        var listRoomAsset = roomAssetRepository.findAllByRoomId(roomId);
        roomAssetRepository.deleteAllById(listRoomAsset.stream().map(RoomAssets::getId).toList());
    }

    @Override
    public List<RoomAssets> deleteRoomAsset(List<Long> roomAssets) {
        roomAssetRepository.deleteAllById(roomAssets);
        return roomAssetRepository.findAllByIdIn(roomAssets);
    }

    @Override
    public List<RoomAssets> listRoomAsset(Long roomId) {
        return roomAssetRepository.findAllByRoomId(roomId);
    }

    @Override
    public List<RoomAssets> updateRoomAsset(List<RoomAssetsRequest> roomAssetsRequests, Long operator) {
        List<RoomAssets> listRoomAssetToUpdate = new ArrayList<>(Collections.emptyList());
        roomAssetsRequests.forEach(e -> listRoomAssetToUpdate.add(
                RoomAssets.modify(
                        roomAssetRepository.findById(e.getRoomAssetId()).get(),
                        e.getAssetName(),
                        e.getAssetQuantity(),
                        e.getAssetTypeId(),
                        e.getRoomId(),
                        operator
                ))
        );
        return roomAssetRepository.saveAll(listRoomAssetToUpdate);
    }

    @Override
    public List<RoomAssets> addRoomAsset(List<RoomAssetsRequest> roomAssetsRequests, Long operator) {
        List<RoomAssets> roomAssetToAdd = new ArrayList<>(Collections.emptyList());
        for (RoomAssetsRequest assetsRequest : roomAssetsRequests) {
            for (RoomAssets roomAssets : listRoomAsset(assetsRequest.getRoomId())) {
                roomAssetToAdd.addAll(
                        roomAssetsRequests
                                .stream()
                                .filter(e -> e.getAssetName().equalsIgnoreCase(roomAssets.getAssetName()))
                                .map(e -> RoomAssets.modify(
                                        roomAssets,
                                        roomAssets.getAssetName(),
                                        roomAssets.getAssetQuantity() + 1,
                                        roomAssets.getAssetTypeId(),
                                        roomAssets.getRoomId(),
                                        operator
                                )).toList());

                roomAssetToAdd.addAll(
                        roomAssetsRequests
                                .stream()
                                .filter(e -> !e.getAssetName().equalsIgnoreCase(roomAssets.getAssetName()))
                                .map(e -> RoomAssets.add(
                                        assetsRequest.getAssetName(),
                                        ObjectUtils.isEmpty(assetsRequest.getAssetQuantity()) ? DEFAULT_ASSET_QUANTITY : assetsRequest.getAssetQuantity(),
                                        assetsRequest.getAssetTypeId(),
                                        assetsRequest.getRoomId(),
                                        operator
                                )).toList());

            }
        }
        return roomAssetRepository.saveAll(roomAssetToAdd);
    }

}
