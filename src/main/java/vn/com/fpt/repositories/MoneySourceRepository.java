package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.MoneySource;

import java.util.List;

@Repository
public interface MoneySourceRepository extends JpaRepository<MoneySource, Long>, JpaSpecificationExecutor<MoneySource> {

    List<MoneySource> findAllByKeyInAndMoneyType(List<Long> id, String moneyType);

    List<MoneySource> findAllByMoneyType(String type);

}
