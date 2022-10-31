package vn.com.fpt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.com.fpt.entity.ServiceTypes;

@Repository
public interface ServiceTypesRepository extends JpaRepository<ServiceTypes, Long> {
}
