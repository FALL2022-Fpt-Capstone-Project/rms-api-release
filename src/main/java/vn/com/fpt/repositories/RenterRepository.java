package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.Renters;

import java.util.List;

@Repository
public interface RenterRepository extends JpaRepository<Renters, Long>, JpaSpecificationExecutor<Renters> {
    List<Renters> findAllByRoomId(Long roomId);
}
