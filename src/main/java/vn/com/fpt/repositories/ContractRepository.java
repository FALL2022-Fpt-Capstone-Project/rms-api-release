package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.Contracts;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contracts, Long>, JpaSpecificationExecutor<Contracts> {
    List<Contracts> findAllByGroupIdAndContractType(Long groupId, Integer contractType);

    List<Contracts> findAllByGroupIdAndContractTypeAndContractPaymentCycle(Long groupId, Integer contractType, Integer payment);

    List<Contracts> findByGroupIdAndContractTypeAndContractIsDisableIsFalse(Long groupId, Integer contractType);

    List<Contracts> findByGroupIdAndContractTypeAndContractIsDisableIsFalseAndAddressIdIn(Long groupId, Integer contracType, List<Long> addressId);

    List<Contracts> findAllByContractTypeOrderByContractStartDateDesc(Integer contractType);

    List<Contracts> findAllByRentersIn(List<Long> renterId);

    List<Contracts> findAllByContractType(Integer contractType);

    List<Contracts>findAllByIdIn(List<Long> id);

    @Query("SELECT DISTINCT c.rackRenters FROM Contracts c WHERE c.groupId = :groupId AND c.contractType = 0")
    Long getRackRenterByGroupId(@Param("groupId") Long groupId);


}
