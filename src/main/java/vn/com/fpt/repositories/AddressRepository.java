package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
