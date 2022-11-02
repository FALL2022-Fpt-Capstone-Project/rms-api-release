package vn.com.fpt.service.assets;

import vn.com.fpt.entity.AssetTypes;
import vn.com.fpt.entity.BasicAssets;
import vn.com.fpt.model.HandOverAssetsDTO;
import vn.com.fpt.requests.BasicAssetsRequest;

import java.util.List;

public interface AssetService {

    List<HandOverAssetsDTO> listHandOverAsset(Long contractId);

    List<AssetTypes> listAssetType();

    List<BasicAssets> listBasicAsset();

    BasicAssets basicAssets(Long id);

    BasicAssets add(BasicAssetsRequest request, Long operator);

    BasicAssets update(Long id, BasicAssetsRequest request, Long operator);

    String deleteBasicAsset(Long id);
}
