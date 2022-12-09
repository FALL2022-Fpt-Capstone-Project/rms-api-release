package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.Renters;

import java.util.List;
import java.util.Optional;

@Repository
public interface RenterRepository extends JpaRepository<Renters, Long>, JpaSpecificationExecutor<Renters> {
    List<Renters> findAllByRoomId(Long roomId);

    List<Renters> findAllByRoomIdIn(List<Long> roomId);

    List<Renters> findAllByRoomIdAndRepresent(Long roomId, Boolean represent);

    Renters findByIdentityNumber(String identityNumber);

    Renters findByRoomIdAndRepresent(Long roomId, Boolean represent);

    Optional<Renters> findByIdentityNumberAndRoomId(String identityNumber, Long roomId);

}
