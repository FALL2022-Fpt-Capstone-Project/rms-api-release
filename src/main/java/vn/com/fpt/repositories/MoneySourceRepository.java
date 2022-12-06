package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.MoneySource;

@Repository
public interface MoneySourceRepository extends JpaRepository<MoneySource, Long>, JpaSpecificationExecutor<MoneySource> {

}
