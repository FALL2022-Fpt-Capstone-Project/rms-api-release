package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.HandOverAssets;

@Repository
public interface HandOverAssetsRepository extends JpaRepository<HandOverAssets, Long> {
    HandOverAssets findByContractIdAndAndAssetId(Long contractId, Long assetId);
}
