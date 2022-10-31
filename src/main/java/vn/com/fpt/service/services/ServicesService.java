package vn.com.fpt.service.services;

import vn.com.fpt.entity.BasicServices;
import vn.com.fpt.entity.GeneralService;
import vn.com.fpt.entity.ServiceTypes;
import vn.com.fpt.model.GeneralServiceDTO;
import vn.com.fpt.requests.AddGeneralServiceRequest;

import java.util.List;

public interface ServicesService {
    List<GeneralServiceDTO> listGeneralService(Long contractId);

    GeneralServiceDTO generalService(Long id);

    List<BasicServices> basicServices();

    List<ServiceTypes> serviceTypes();

    GeneralService updateGeneralService(Long generalServiceId, AddGeneralServiceRequest request, Long operator);

    GeneralService addGeneralService(AddGeneralServiceRequest request, Long operator);

    List<GeneralService> quickAddGeneralService(Long contractId, Long operator);

    String removeGeneralService(Long id);


}
