package vn.com.fpt.service.assets;

import vn.com.fpt.model.HandOverAssetsDTO;

import java.util.List;

public interface AssetService {

    List<HandOverAssetsDTO> listHandOverAsset(Long contractId);
}
