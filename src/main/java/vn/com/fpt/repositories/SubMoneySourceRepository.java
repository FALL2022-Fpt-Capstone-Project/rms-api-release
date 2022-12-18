package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.SubMoneySource;

import java.util.List;

@Repository
public interface SubMoneySourceRepository extends JpaRepository<SubMoneySource, Long>, JpaSpecificationExecutor<SubMoneySource> {
    List<SubMoneySource> findAllByMoneySourceId(Long id);
}
