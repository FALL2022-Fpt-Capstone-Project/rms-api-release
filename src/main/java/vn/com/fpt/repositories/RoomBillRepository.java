package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.RoomBill;

import java.util.List;

@Repository
public interface RoomBillRepository extends JpaRepository<RoomBill, Long>, JpaSpecificationExecutor<RoomBill> {
}
