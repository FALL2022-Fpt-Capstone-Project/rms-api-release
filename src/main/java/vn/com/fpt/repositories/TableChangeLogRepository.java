package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.TableChangeLog;

@Repository
public interface TableChangeLogRepository extends JpaRepository<TableChangeLog, Long>, JpaSpecificationExecutor<Long> {
}
