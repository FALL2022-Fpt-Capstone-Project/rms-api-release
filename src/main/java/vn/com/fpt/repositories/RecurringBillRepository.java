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
            "EXTRACT(MONTH from bill_created_time) = :month " +
            "AND " +
            "EXTRACT(YEAR from bill_created_time) = :year " +
            "AND " +
            "contract_id = :contractId",
            nativeQuery = true)
    RecurringBill findByContractIdAndCreatedAt(Long contractId, int month, int year);

    @Query(value = "SELECT FROM manager_recurring_bill WHERE " +
            "EXTRACT(MONTH from bill_created_time) = :month " +
            "AND " +
            "EXTRACT(YEAR from bill_created_time) = :year " +
            "AND " +
            "room_id = :roomId",
            nativeQuery = true)
    RecurringBill findByRoomIdAndCreatedAt(Long roomId, int month, int year);

    List<RecurringBill> findAllByRoomIdAndIsPaid(Long roomId, Boolean isPaid);

    List<RecurringBill> findAllByRoomId(Long roomId);

    List<RecurringBill> findAllByIdIn(List<Long> id);

    List<RecurringBill> findAllByGroupContractIdAndIsPaidIsFalseOrIsDebtIsTrue(Long groupContractId);

    List<RecurringBill> findAllByRoomIdAndIsPaidIsFalseOrIsDebtIsTrue(Long roomId);

    @Query(value = "SELECT FROM manager_recurring_bill WHERE " +
            "EXTRACT(MONTH from created_at) = :month " +
            "AND " +
            "EXTRACT(YEAR from created_at) = :year " +
            "AND " +
            "group_id = :groupId",
            nativeQuery = true)
    List<RecurringBill> findAllByGroupIdAndTime(Long groupId, int month, int year);

}
