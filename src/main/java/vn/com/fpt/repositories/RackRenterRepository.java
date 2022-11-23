package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.RackRenters;

@Repository
public interface RackRenterRepository extends JpaRepository<RackRenters, Long>, JpaSpecificationExecutor<RackRenters> {
    RackRenters findByIdentityNumber(String identity);
}
