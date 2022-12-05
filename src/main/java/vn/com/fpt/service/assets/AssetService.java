package vn.com.fpt.service.assets;

import vn.com.fpt.entity.AssetTypes;
import vn.com.fpt.entity.BasicAssets;
import vn.com.fpt.entity.RoomAssets;
import vn.com.fpt.model.BasicAssetDTO;
import vn.com.fpt.model.RoomAssetDTO;
import vn.com.fpt.requests.BasicAssetsRequest;
import vn.com.fpt.requests.RoomAssetsRequest;

import java.util.List;

public interface AssetService {

    List<AssetTypes> listAssetType();

    List<BasicAssetDTO> listBasicAsset();

    List<RoomAssetDTO> listRoomAsset(Long roomId, Long assetType);

    BasicAssets basicAssets(Long id);

    BasicAssets add(BasicAssetsRequest request, Long operator);

    BasicAssets add(BasicAssets request);

    List<RoomAssets> add(List<RoomAssets> listRoomAssets);

    List<RoomAssets> roomAdd(List<RoomAssetsRequest> request, Long operator);

    BasicAssets update(Long id,
                       BasicAssetsRequest request,
                       Long operator);

    String deleteBasicAsset(Long id);

    void deleteRoomAsset(Long roomId);

    List<RoomAssets> deleteRoomAsset(List<Long> roomAssets);

    List<RoomAssets> listRoomAsset(Long roomId);

    List<RoomAssets> updateRoomAsset(List<RoomAssetsRequest> roomAssetsRequests, Long operator);

    List<RoomAssets> addRoomAsset(List<RoomAssetsRequest> roomAssetsRequests, Long operator);


}
