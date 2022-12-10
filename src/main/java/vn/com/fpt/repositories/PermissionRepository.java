package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.Permission;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAllByAccountIdAndPermissionIdIn(Long accountId, List<Long> permissionId);

    List<Permission> findAllByAccountId(Long accountId);

}
