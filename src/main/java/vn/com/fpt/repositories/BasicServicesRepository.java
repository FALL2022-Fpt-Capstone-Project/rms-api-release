package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.BasicServices;

@Repository
public interface BasicServicesRepository extends JpaRepository<BasicServices, Long> {
}
