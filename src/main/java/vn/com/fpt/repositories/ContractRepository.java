package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.Contracts;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contracts, Long> {
    Contracts findAllByGroupIdAndContractType(Long groupId, Integer contractType);

    List<Contracts> findAllByContractType(Integer contractType);
}
