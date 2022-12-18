package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.RoomGroups;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<RoomGroups, Long>, JpaSpecificationExecutor<RoomGroups> {
    List<RoomGroups> findAllByIdNotAndIsDisableIsFalse(Long groupId);

    List<RoomGroups> findAllByIsDisableIsFalse();
}
