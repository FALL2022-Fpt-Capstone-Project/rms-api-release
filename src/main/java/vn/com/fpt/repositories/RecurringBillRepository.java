package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.RecurringBill;

import java.util.List;

@Repository
public interface RecurringBillRepository extends JpaRepository<RecurringBill, Long>, JpaSpecificationExecutor<RecurringBill> {

    @Query(value = "SELECT FROM manager_recurring_bill WHERE " +
            "EXTRACT(MONTH from created_at) = :month " +
            "AND " +
            "EXTRACT(YEAR from created_at) = :year " +
            "AND " +
            "contract_id = :contractId",

            nativeQuery = true)
    RecurringBill findByContractIdAndCreatedAt(Long contractId, int month, int year);
}
