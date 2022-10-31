package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.Rooms;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Long> {
}
