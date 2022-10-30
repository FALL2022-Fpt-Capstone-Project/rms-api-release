package vn.com.fpt.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.fpt.model.GeneralServiceDTO;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static vn.com.fpt.model.GeneralServiceDTO.SQL_RESULT_SET_MAPPING;

@Service
@RequiredArgsConstructor
public class ServicesServiceImpl implements ServicesService {
    private final EntityManager entityManager;

    @Override
    public List<GeneralServiceDTO> listGeneralService(Long contractId) {

        StringBuilder selectBuild = new StringBuilder("SELECT ");
        selectBuild.append("mgs.general_service_id,");
        selectBuild.append("mgs.service_price,");
        selectBuild.append("mgs.note,");
        selectBuild.append("mgs.service_type_id,");
        selectBuild.append("mst.service_type_name,");
        selectBuild.append("mgs.service_type_id,");
        selectBuild.append("mbs.service_name,");
        selectBuild.append("mbs.service_show_name ");


        StringBuilder fromBuild = new StringBuilder("FROM ");
        fromBuild.append("manager_general_services mgs ");
        fromBuild.append("INNER JOIN manager_basic_services mbs on mgs.service_id = mbs.service_id ");
        fromBuild.append("INNER JOIN manager_service_types mst on mgs.service_type_id = mst.service_types_id ");

        StringBuilder whereBuild = new StringBuilder("WHERE 1=1 ");
        whereBuild.append("AND mgs.contract_id = :contractId");

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .append(whereBuild)
                .toString();

        Query query = entityManager.createNativeQuery(queryBuild, SQL_RESULT_SET_MAPPING);
        query.setParameter("contractId", contractId);
        return query.getResultList();
    }
}
