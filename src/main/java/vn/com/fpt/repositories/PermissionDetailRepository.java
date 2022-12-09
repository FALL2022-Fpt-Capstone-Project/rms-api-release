package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.PermissionDetail;

@Repository
public interface PermissionDetailRepository extends JpaRepository<PermissionDetail, Long> {
}
