package vn.com.fpt.repositories.config_repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.config.Month;

@Repository
public interface MonthConfigRepository extends JpaRepository<Month, Long> {
}
