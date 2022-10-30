package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.Renters;

@Repository
public interface RenterRepository extends JpaRepository<Renters, Long>{
}
