package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.Rooms;

import java.util.List;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Long>, JpaSpecificationExecutor<Rooms> {
    List<Rooms> findAllByGroupIdAndIsDisableIsFalse(Long groupId);

    List<Rooms> findAllByGroupIdAndIdNotInAndIsDisableIsFalse(Long groupId, List<Long> roomId);

    List<Rooms> findByGroupIdAndIdNotAndIsDisableIsFalse(Long groupId, Long roomId);

    List<Rooms> findAllByGroupContractIdAndGroupId(Long groupContractId, Long groupId);

    List<Rooms> findAllByGroupContractIdNullAndGroupIdAndIsDisableIsFalse(Long groupId);

    List<Rooms> findAllByRoomFloorInAndGroupIdAndIsDisableIs(List<Integer> floor, Long groupId, Boolean isDisable);

    @Query("select DISTINCT (r.roomFloor) from Rooms r WHERE r.groupContractId = :groupContractId AND r.groupId = :groupId")
    List<Integer> findAllFloorByGroupContractIdAndGroupId(@Param("groupContractId") Long groupContractId,
                                                          @Param("groupId") Long groupId);

    @Query("select DISTINCT (r.roomFloor) from Rooms r WHERE r.groupContractId IS NOT null AND r.groupId = :groupId")
    List<Integer> findAllFloorByGroupContractIdNotNullAndGroupId(@Param("groupId") Long groupId);

    @Query("select DISTINCT (r.roomFloor) from Rooms r WHERE r.groupContractId is NULL AND r.groupId = :groupId AND r.isDisable = false")
    List<Integer> findAllFloorByGroupNonContractAndGroupId(@Param("groupId") Long groupId);

    @Query("select distinct (r.roomName) from Rooms r where r.groupContractId = :groupContractId")
    List<String> findAllRoomsByGroupContractId(@Param("groupContractId") Long groupContractId);

    @Query("select distinct (r.roomName) from Rooms r where r.groupId = :groupId and r.isDisable = false")
    List<String> findAllRoomsByGroupId(@Param("groupId") Long groupId);

    @Query("select DISTINCT (r.roomFloor) from Rooms r WHERE r.groupId = :groupId")
    List<Integer> findAllFloorByGroupId(@Param("groupId") Long groupId);

    List<Rooms> findAllByIdInAndContractIdNotNull(List<Long> id);

    List<Rooms> findAllByGroupContractId(Long groupContractId);

    List<Rooms> findAllByGroupContractIdAndIsDisableIsFalse(Long groupContractId);

    Rooms findByIdAndContractIdNotNull(Long id);
}
