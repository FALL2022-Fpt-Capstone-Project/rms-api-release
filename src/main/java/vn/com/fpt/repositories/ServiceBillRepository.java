package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.ServiceBill;

@Repository
public interface ServiceBillRepository extends JpaRepository<ServiceBill, Long>, JpaSpecificationExecutor<ServiceBill> {
}

