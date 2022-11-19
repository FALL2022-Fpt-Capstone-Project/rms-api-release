package vn.com.fpt.service.services;

import vn.com.fpt.entity.BasicServices;
import vn.com.fpt.entity.GeneralService;
import vn.com.fpt.entity.HandOverGeneralServices;
import vn.com.fpt.entity.ServiceTypes;
import vn.com.fpt.model.GeneralServiceDTO;
import vn.com.fpt.model.HandOverGeneralServiceDTO;
import vn.com.fpt.requests.GeneralServiceRequest;
import vn.com.fpt.requests.HandOverGeneralServiceRequest;

import java.util.Date;
import java.util.List;

public interface ServicesService {
    List<GeneralServiceDTO> listGeneralService(Long contractId);

    GeneralServiceDTO generalService(Long id);

    List<BasicServices> basicServices();

    List<ServiceTypes> serviceTypes();
    GeneralService addGeneralService(GeneralServiceRequest request, Long operator);

    List<GeneralService> addGeneralService(List<GeneralServiceRequest> request, Long operator);
    HandOverGeneralServices addHandOverGeneralService(HandOverGeneralServiceRequest request,
                                                      Long contractId,
                                                      Date dateDelivery,
                                                      Long operator);

    HandOverGeneralServices updateHandOverGeneralService(Long id,
                                                         HandOverGeneralServiceRequest request,
                                                         Long contractId,
                                                         Date dateDelivery,
                                                         Long operator);

    GeneralService updateGeneralService(Long generalServiceId,
                                        GeneralServiceRequest request,
                                        Long operator);

    List<HandOverGeneralServiceDTO> listHandOverGeneralService(Long contractId);

    List<GeneralService> quickAddGeneralService(Long contractId, Long operator);

    String removeGeneralService(Long id);


}
