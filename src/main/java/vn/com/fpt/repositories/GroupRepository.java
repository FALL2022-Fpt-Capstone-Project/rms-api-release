package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.RoomGroups;

@Repository
public interface GroupRepository extends JpaRepository<RoomGroups, Long> {
}