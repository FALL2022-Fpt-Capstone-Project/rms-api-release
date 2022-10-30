package vn.com.fpt.service.services;

import vn.com.fpt.model.GeneralServiceDTO;

import java.util.List;

public interface ServicesService {
    List<GeneralServiceDTO> listGeneralService(Long contractId);


}
