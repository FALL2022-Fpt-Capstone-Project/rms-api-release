package vn.com.fpt.service.contract;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.repositories.*;
import vn.com.fpt.requests.ContractRequest;
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
    @Transactional
    public ContractRequest addContract(ContractRequest request, Long operator) {
        Contracts contractsInformation = Contracts.addForRenter(request, operator);
        if (request.getRenterOldId() != null) {
            
        }
        var addedContract = contractRepository.save(contractsInformation);

        return null;
    }
}
