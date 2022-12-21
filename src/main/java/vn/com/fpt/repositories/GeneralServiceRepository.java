package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.GeneralService;

import java.util.List;

@Repository
public interface GeneralServiceRepository extends JpaRepository<GeneralService, Long> {

    List<GeneralService> findAllByContractId(Long contractId);

    List<GeneralService> findAllByGroupId(Long groupId);

    GeneralService findByGroupIdAndServiceId(Long groupId, Long serviceId);

}
