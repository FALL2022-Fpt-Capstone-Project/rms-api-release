package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.config.TotalFloor;

@Repository
public interface FloorConfigRepository extends JpaRepository<TotalFloor, Long> {
}
