package vn.com.fpt.service.config_value;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.com.fpt.entity.config.Month;
import vn.com.fpt.model.DistrictDTO;
import vn.com.fpt.repositories.config_repo.MonthConfigRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static vn.com.fpt.common.constants.ManagerConstants.LEASE_CONTRACT;
import static vn.com.fpt.model.DistrictDTO.SQL_RESULT_SET_MAPPING;

@Service
@AllArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    public final MonthConfigRepository monthRepo;

    private final EntityManager entityManager;

    @Override
    public List<Month> listConfigMonth() {
        return monthRepo.findAll(Sort.by("numberMonth").ascending());
    }

    @Override
    public List<DistrictDTO> listAddedDistrict() {
        StringBuilder selectBuild = new StringBuilder("SELECT ");
        selectBuild.append("SELECT DISTINCT a.address_city ");


        StringBuilder fromBuild = new StringBuilder("FROM ");
        fromBuild.append("FROM manager_address a ");
        fromBuild.append("JOIN manager_contracts c ON c.address_id = a.address_id ");

        StringBuilder whereBuild = new StringBuilder("WHERE 1=1 ");
        whereBuild.append("WHERE c.contract_type = :contractType");

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .append(whereBuild)
                .toString();

        Query query = entityManager.createNativeQuery(queryBuild, SQL_RESULT_SET_MAPPING);
        query.setParameter("contractType", LEASE_CONTRACT);

        return new ArrayList<>(query.getResultList());
    }
}
