package vn.com.fpt.service.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.fpt.repositories.*;
import vn.com.fpt.requests.AddContractRequest;
import vn.com.fpt.service.services.ServicesService;

@Service
@RequiredArgsConstructor
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    private final ServicesService servicesService;

    private final HandOverAssetsRepository handOverAssetsRepository;

    private final RenterRepository renterRepository;

    private final HandOverGeneralServicesRepository handOverGeneralServicesRepository;

    private final RoomsRepository roomRepository;



    @Override
    public AddContractRequest addContract(AddContractRequest request, Long operator) {
        return null;
    }
}
