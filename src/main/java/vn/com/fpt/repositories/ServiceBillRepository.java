package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.ServiceBill;

import java.util.List;

@Repository
public interface ServiceBillRepository extends JpaRepository<ServiceBill, Long>, JpaSpecificationExecutor<ServiceBill> {

    List<ServiceBill> findAllByRoomIdAndServiceIdAndServiceTypeId(Long roomId, Long serviceId, Long serviceTypeId);

    List<ServiceBill> findAllByRoomIdAndServiceId(Long roomId, Long serviceId);

    List<ServiceBill> findAllByServiceId(Long serviceId);

}

