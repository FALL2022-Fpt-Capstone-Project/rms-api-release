package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.BasicAssets;

@Repository
public interface BasicAssetRepository extends JpaRepository<BasicAssets, Long> {
}
