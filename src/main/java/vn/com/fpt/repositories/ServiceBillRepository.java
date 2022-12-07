package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.ServiceBill;

import java.util.List;

@Repository
public interface ServiceBillRepository extends JpaRepository<ServiceBill, Long>, JpaSpecificationExecutor<ServiceBill> {
    @Query(value = "SELECT FROM manager_room_bill WHERE " +
            "EXTRACT(MONTH from created_at) = :month " +
            "AND " +
            "EXTRACT(YEAR from created_at) = :year " +
            "AND " +
            "room_id = :roomId AND service_id = :serviceId AND service_type_id = :serviceTypeId",
            nativeQuery = true)
    ServiceBill findAllByRoomIdAndByServiceIdAndCreatedBy(Long roomId, Long serviceId, Long serviceTypeId, int month, int year);
}

