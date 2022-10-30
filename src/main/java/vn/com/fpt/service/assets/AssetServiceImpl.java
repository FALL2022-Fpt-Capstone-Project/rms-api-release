package vn.com.fpt.service.assets;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.fpt.model.HandOverAssetsDTO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static vn.com.fpt.model.HandOverAssetsDTO.SQL_RESULT_SET_MAPPING;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {
    private final EntityManager entityManager;

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
}
