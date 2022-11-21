package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.Contracts;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contracts, Long>, JpaSpecificationExecutor<Contracts> {
    List<Contracts> findAllByGroupIdAndContractType(Long groupId, Integer contractType);

    List<Contracts> findByGroupIdAndContractType(Long groupId, Integer contractType);

    List<Contracts> findAllByContractTypeOrderByContractStartDateDesc(Integer contractType);

    List<Contracts> findAllByRentersIn(List<Long> renterId);


}
