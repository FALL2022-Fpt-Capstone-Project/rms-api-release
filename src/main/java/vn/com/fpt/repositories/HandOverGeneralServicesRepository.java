package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.HandOverGeneralServices;

import java.util.List;

@Repository
public interface HandOverGeneralServicesRepository extends JpaRepository<HandOverGeneralServices, Long> {
    List<HandOverGeneralServices> findAllByContractId(Long contractId);
}
