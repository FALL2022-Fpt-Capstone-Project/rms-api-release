package vn.com.fpt.service.assets;

import vn.com.fpt.entity.AssetTypes;
import vn.com.fpt.entity.BasicAssets;
import vn.com.fpt.entity.HandOverAssets;
import vn.com.fpt.model.BasicAssetDTO;
import vn.com.fpt.model.HandOverAssetsDTO;
import vn.com.fpt.requests.BasicAssetsRequest;
import vn.com.fpt.requests.HandOverAssetsRequest;

import java.util.Date;
import java.util.List;

public interface AssetService {

    List<HandOverAssetsDTO> listHandOverAsset(Long contractId);

    List<AssetTypes> listAssetType();

    List<BasicAssetDTO> listBasicAsset();

    BasicAssets basicAssets(Long id);

    BasicAssets add(BasicAssetsRequest request, Long operator);

    BasicAssets add(BasicAssets request);

    HandOverAssets addHandOverAsset(HandOverAssetsRequest request,
                                    Long operator,
                                    Long contractId,
                                    Date dateDelivery);

    HandOverAssets addAdditionalAsset(HandOverAssetsRequest request,
                                      Long contractId,
                                      Long operator);

    HandOverAssets updateHandOverAsset(HandOverAssets old,
                                       HandOverAssetsRequest request,
                                       Long operator,
                                       Long contractId,
                                       Date dateDelivery);

    HandOverAssets addGeneralAsset(HandOverAssetsRequest request,
                                   Long operator,
                                   Long contractId,
                                   Date dateDelivery);

    HandOverAssets updateGeneralAssetQuantity(Long contractId, Long assetId, Integer quantity);

    BasicAssets update(Long id,
                       BasicAssetsRequest request,
                       Long operator);

    String deleteBasicAsset(Long id);

    HandOverAssets handOverAsset(Long id);
}
