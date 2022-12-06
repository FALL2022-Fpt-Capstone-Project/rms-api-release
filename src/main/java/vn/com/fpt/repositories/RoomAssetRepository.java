package vn.com.fpt.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.RoomAssets;

import java.util.List;

@Repository
public interface RoomAssetRepository extends JpaRepository<RoomAssets, Long>, JpaSpecificationExecutor<RoomAssets> {
    List<RoomAssets> findAllByRoomId(Long roomId);

    List<RoomAssets> findAllByIdIn(List<Long> id);

    RoomAssets findByAssetNameLikeIgnoreCaseAndAssetTypeId(String name, Long assetTypeId);
}
