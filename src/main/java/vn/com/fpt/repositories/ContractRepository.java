package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.Contracts;

@Repository
public interface ContractRepository extends JpaRepository<Contracts, Long> {
}
