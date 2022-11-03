package vn.com.fpt.service.assets;

import lombok.*;
import org.springframework.stereotype.Service;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.constants.ErrorStatusConstants;
import vn.com.fpt.entity.AssetTypes;
import vn.com.fpt.entity.BasicAssets;
import vn.com.fpt.entity.HandOverAssets;
import vn.com.fpt.model.HandOverAssetsDTO;
import vn.com.fpt.repositories.AssetTypesRepository;
import vn.com.fpt.repositories.BasicAssetRepository;
import vn.com.fpt.repositories.HandOverAssetsRepository;
import vn.com.fpt.requests.BasicAssetsRequest;
import vn.com.fpt.requests.HandOverAssetsRequest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static vn.com.fpt.constants.ErrorStatusConstants.ASSET_OUT_OF_STOCK;
import static vn.com.fpt.model.HandOverAssetsDTO.SQL_RESULT_SET_MAPPING;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {
    private final EntityManager entityManager;

    private final AssetTypesRepository assetTypesRepository;

    private final BasicAssetRepository basicAssetRepository;

    private final HandOverAssetsRepository handOverAssetsRepository;

    @Override
    public List<HandOverAssetsDTO> listHandOverAsset(Long contractId) {
        Map<String, Object> params = new HashMap<>();

        StringBuilder selectBuild = new StringBuilder("SELECT ");
        selectBuild.append("hos.hand_over_asset_id,");
        selectBuild.append("hos.hand_over_asset_quantity,");
        selectBuild.append("hos.hand_over_asset_status,");
        selectBuild.append("hos.hand_over_asset_date_delivery,");
        selectBuild.append("mba.asset_id,");
        selectBuild.append("mba.asset_name,");
        selectBuild.append("mba.asset_type_id,");
        selectBuild.append("mat.asset_type_name,");
        selectBuild.append("mat.asset_type_show_name ");

        StringBuilder fromBuild = new StringBuilder("FROM ");
        fromBuild.append("manager_hand_over_assets hos ");
        fromBuild.append("INNER JOIN manager_basic_assets mba on hos.asset_id = mba.asset_id ");
        fromBuild.append("INNER JOIN manager_asset_types mat on mba.asset_type_id = mat.asset_types_id ");

        StringBuilder whereBuild = new StringBuilder("WHERE ");
        whereBuild.append("hos.contract_id = :contractId");
        params.put("contractId", contractId);

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .append(whereBuild)
                .toString();

        Query query = entityManager.createNativeQuery(queryBuild, SQL_RESULT_SET_MAPPING);
        params.forEach(query::setParameter);
        return query.getResultList();
    }

    @Override
    public List<AssetTypes> listAssetType() {
        return assetTypesRepository.findAll();
    }

    @Override
    public List<BasicAssets> listBasicAsset() {
        return basicAssetRepository.findAll();
    }

    @Override
    public BasicAssets basicAssets(Long id) {
        return basicAssetRepository.findById(id).get();
    }

    @Override
    public BasicAssets add(BasicAssetsRequest request, Long operator) {
        return basicAssetRepository.save(BasicAssets.add(request));
    }

    @Override
    public BasicAssets add(BasicAssets request) {
        return basicAssetRepository.save(BasicAssets.add(request));
    }

    @Override
    public HandOverAssets addHandOverAsset(HandOverAssetsRequest request,
                                           Long operator,
                                           Long contractId,
                                           Date dateDelivery
    ) {
        return handOverAssetsRepository.save(
                HandOverAssets.add(request, contractId, dateDelivery, operator));
    }

    @Override
    public HandOverAssets updateHandOverAsset(HandOverAssets old,
                                              HandOverAssetsRequest request,
                                              Long operator,
                                              Long contractId,
                                              Date dateDelivery) {
        return handOverAssetsRepository.save(
                HandOverAssets.modify(old, request, contractId, dateDelivery, operator));
    }

    @Override
    public HandOverAssets addGeneralAsset(HandOverAssetsRequest request, Long operator, Long contractId, Date dateDelivery) {
        return handOverAssetsRepository.save(
                HandOverAssets.add(request, contractId, dateDelivery, operator));
    }

    @Override
    public HandOverAssets updateGeneralAssetQuantity(Long contractId, Long assetId, Integer quantity) {
        var toUpdate = handOverAssetsRepository.findByContractIdAndAndAssetId(contractId, assetId);
        Integer updateQuantity = toUpdate.getQuantity() - quantity;
        if (updateQuantity < 0)
            throw new BusinessException(ASSET_OUT_OF_STOCK, "Số lượng trang thiết bị của asset_id: " + assetId + "là: " + toUpdate.getQuantity() + ", số lượng muốn thêm là: " + quantity);
        toUpdate.setQuantity(updateQuantity);
        return handOverAssetsRepository.save(toUpdate);
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
    public HandOverAssets handOverAsset(Long id) {
        return handOverAssetsRepository.findById(id).get();
    }
}
