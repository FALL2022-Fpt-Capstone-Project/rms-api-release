package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.authentication.Account;

@Repository
public interface StaffRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {

}
