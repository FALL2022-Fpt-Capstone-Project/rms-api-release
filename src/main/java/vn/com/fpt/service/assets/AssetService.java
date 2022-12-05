package vn.com.fpt.service.assets;

import vn.com.fpt.entity.AssetTypes;
import vn.com.fpt.entity.BasicAssets;
import vn.com.fpt.entity.HandOverAssets;
import vn.com.fpt.entity.RoomAssets;
import vn.com.fpt.model.BasicAssetDTO;
import vn.com.fpt.model.HandOverAssetsDTO;
import vn.com.fpt.requests.BasicAssetsRequest;
import vn.com.fpt.requests.HandOverAssetsRequest;
import vn.com.fpt.requests.RoomAssetsRequest;

import java.util.Date;
import java.util.List;

public interface AssetService {

    List<HandOverAssetsDTO> listHandOverAsset(Long contractId);

    List<AssetTypes> listAssetType();

    List<BasicAssetDTO> listBasicAsset();

    BasicAssets basicAssets(Long id);

    BasicAssets add(BasicAssetsRequest request, Long operator);

    BasicAssets add(BasicAssets request);

    List<RoomAssets> add(List<RoomAssets> listRoomAssets);

    List<RoomAssets> roomAdd(List<RoomAssetsRequest> request, Long operator);

    HandOverAssets addHandOverAsset(HandOverAssetsRequest request,
                                    Long operator,
                                    Long contractId,
                                    Date dateDelivery);

    HandOverAssets addAdditionalAsset(HandOverAssetsRequest request,
                                      Long contractId,
                                      Integer leaseType,
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

    void deleteRoomAsset(Long roomId);

    List<RoomAssets> deleteRoomAsset(List<Long> roomAssets);

    List<RoomAssets> listRoomAsset(Long roomId);

    List<RoomAssets> updateRoomAsset(List<RoomAssetsRequest> roomAssetsRequests, Long operator);

    List<RoomAssets> addRoomAsset(List<RoomAssetsRequest> roomAssetsRequests, Long operator);

    HandOverAssets handOverAsset(Long id);


}
