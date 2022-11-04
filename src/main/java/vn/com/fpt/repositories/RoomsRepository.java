package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.Rooms;
import vn.com.fpt.entity.authentication.Account;

import java.util.List;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Long>, JpaSpecificationExecutor<Rooms> {
    List<Rooms> findAllByGroupId(Long groupId);

    @Query("select DISTINCT (r.roomFloor) from Rooms r WHERE r.groupId = :groupId")
    List<Integer> findAllFloorByGroupId(@Param("groupId") Long groupId);

    @Query("select distinct (r.roomName) from Rooms r where r.groupId = :groupId")
    List<String> findAllRoomsByGroupId(@Param("groupId") Long groupId);
}
