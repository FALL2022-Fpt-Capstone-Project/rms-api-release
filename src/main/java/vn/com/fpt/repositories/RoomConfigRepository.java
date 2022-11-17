package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.config.TotalRoom;

@Repository
public interface RoomConfigRepository extends JpaRepository<TotalRoom, Long> {
}
