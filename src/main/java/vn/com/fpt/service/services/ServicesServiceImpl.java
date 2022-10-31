package vn.com.fpt.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.common.utils.DateUtils;
import vn.com.fpt.constants.ErrorStatusConstants;
import vn.com.fpt.constants.ManagerConstants;
import vn.com.fpt.entity.BasicServices;
import vn.com.fpt.entity.GeneralService;
import vn.com.fpt.entity.ServiceTypes;
import vn.com.fpt.model.GeneralServiceDTO;
import vn.com.fpt.repositories.BasicServicesRepository;
import vn.com.fpt.repositories.GeneralServiceRepository;
import vn.com.fpt.repositories.ServiceTypesRepository;
import vn.com.fpt.requests.AddGeneralServiceRequest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static vn.com.fpt.constants.ErrorStatusConstants.GENERAL_SERVICE_EXISTED;
import static vn.com.fpt.constants.ErrorStatusConstants.GENERAL_SERVICE_NOT_FOUND;
import static vn.com.fpt.model.GeneralServiceDTO.SQL_RESULT_SET_MAPPING;

@Service
@RequiredArgsConstructor
public class ServicesServiceImpl implements ServicesService {
    private final EntityManager entityManager;

    private final BasicServicesRepository basicServicesRepository;

    private final ServiceTypesRepository serviceTypesRepository;

    private final GeneralServiceRepository generalServiceRepository;

    @Override
    public List<GeneralServiceDTO> listGeneralService(Long contractId) {

        StringBuilder selectBuild = new StringBuilder("SELECT ");
        selectBuild.append("mgs.general_service_id,");
        selectBuild.append("mgs.service_price,");
        selectBuild.append("mgs.note,");
        selectBuild.append("mgs.service_type_id,");
        selectBuild.append("mst.service_type_name,");
        selectBuild.append("mgs.service_id,");
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

    @Override
    public GeneralServiceDTO generalService(Long id) {

        var checker = findGeneralServiceById(id);

        StringBuilder selectBuild = new StringBuilder("SELECT ");
        selectBuild.append("mgs.general_service_id,");
        selectBuild.append("mgs.service_price,");
        selectBuild.append("mgs.note,");
        selectBuild.append("mgs.service_type_id,");
        selectBuild.append("mst.service_type_name,");
        selectBuild.append("mgs.service_id,");
        selectBuild.append("mbs.service_name,");
        selectBuild.append("mbs.service_show_name ");


        StringBuilder fromBuild = new StringBuilder("FROM ");
        fromBuild.append("manager_general_services mgs ");
        fromBuild.append("INNER JOIN manager_basic_services mbs on mgs.service_id = mbs.service_id ");
        fromBuild.append("INNER JOIN manager_service_types mst on mgs.service_type_id = mst.service_types_id ");

        StringBuilder whereBuild = new StringBuilder("WHERE 1=1 ");
        whereBuild.append("AND mgs.general_service_id = :generalServiceId");

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .append(whereBuild)
                .toString();

        Query query = entityManager.createNativeQuery(queryBuild, SQL_RESULT_SET_MAPPING);
        query.setParameter("generalServiceId", id);

        return (GeneralServiceDTO) query.getSingleResult();
    }

    @Override
    public List<BasicServices> basicServices() {
        return basicServicesRepository.findAll();
    }

    @Override
    public List<ServiceTypes> serviceTypes() {
        return serviceTypesRepository.findAll();
    }

    @Override
    public GeneralService updateGeneralService(Long generalServiceId, AddGeneralServiceRequest request, Long operator) {
        var generalService = findGeneralServiceById(generalServiceId);
        return generalServiceRepository.save(GeneralService.modify(request, operator));
    }

    @Override
    public GeneralService addGeneralService(AddGeneralServiceRequest request, Long operator) {
        return generalServiceRepository.save(GeneralService.add(request, operator));
    }

    @Override
    public List<GeneralService> quickAddGeneralService(Long contractId, Long operator) {

        GeneralService defaultElectric =
                new GeneralService(ManagerConstants.SERVICE_ELECTRIC, contractId, ManagerConstants.SERVICE_TYPE_METER, ManagerConstants.ELECTRIC_DEFAULT_PRICE);
        GeneralService defaultWater =
                new GeneralService(ManagerConstants.SERVICE_WATER, contractId, ManagerConstants.SERVICE_TYPE_METER, ManagerConstants.WATER_DEFAULT_PRICE);
        GeneralService defaultInternet =
                new GeneralService(ManagerConstants.SERVICE_INTERNET, contractId, ManagerConstants.SERVICE_TYPE_MONTH, ManagerConstants.INTERNET_DEFAULT_PRICE);
        GeneralService defaultVehicles =
                new GeneralService(ManagerConstants.SERVICE_VEHICLES, contractId, ManagerConstants.SERVICE_TYPE_PERSON, ManagerConstants.VEHICLES_DEFAULT_PRICE);

        List<GeneralService> defaultGeneralService = new ArrayList<>();
        defaultGeneralService.add(defaultElectric);
        defaultGeneralService.add(defaultWater);
        defaultGeneralService.add(defaultInternet);
        defaultGeneralService.add(defaultVehicles);

        var currentGenralService = generalServiceRepository.findAllByContractId(contractId);
        Map<Long, GeneralService> map = new HashMap<>();
        currentGenralService.forEach(e -> map.put(e.getServiceId(), e));

        List<GeneralService> quickAddList = new ArrayList<>();
        // check duplicate
        defaultGeneralService.forEach(e1 -> {
            if (map.get(e1.getServiceId()) == null) {
                e1.setCreatedAt(DateUtils.now());
                e1.setCreatedBy(operator);
                quickAddList.add(e1);
            }
        });
        if (quickAddList.isEmpty())
            throw new BusinessException(GENERAL_SERVICE_EXISTED, "Thêm nhanh không thành công do các dịch vụ chung đã có sẵn trong tòa!!");

        return generalServiceRepository.saveAll(quickAddList);
    }

    @Override
    public String removeGeneralService(Long id) {
        var generalService = findGeneralServiceById(id);
        generalServiceRepository.delete(generalService);
        return "Xóa dịch vụ chung thành công. general_service_id : " + id;
    }


    GeneralService findGeneralServiceById(Long id) {
        return generalServiceRepository.findById(id)
                .orElseThrow(() -> new BusinessException(GENERAL_SERVICE_NOT_FOUND, "Không tìm thấy tài sản general_asset_id: " + id));
    }
}
