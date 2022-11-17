package vn.com.fpt.service.statistical;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.repositories.ContractRepository;

import static org.mockito.ArgumentMatchers.*;

import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.responses.AccountResponse;
import vn.com.fpt.responses.StatisticalRoomContractResponse;
import vn.com.fpt.specification.BaseSpecification;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static vn.com.fpt.common.constants.ErrorStatusConstants.CONTRACT_NOT_FOUND;
import static vn.com.fpt.common.constants.ErrorStatusConstants.EXISTED_ACCOUNT;

@ExtendWith(MockitoExtension.class)
class StatisticalServiceImplTest {

    @InjectMocks
    StatisticalServiceImpl statisticalService;

    @Mock
    private ContractRepository contractRepo;

    @Test
    void GivenExactValue_Then_statisticalRoomContract_ResultStatisticalRoomContractResponseExact() {
        //input
        Long groupId = 1L;
        Long duration = 1L;

        //mock  contractRepo.findAll
        List<Contracts> listContract = new ArrayList<>();
        Contracts contract = new Contracts();
        listContract.add(contract);
        when(contractRepo.findAll(any(BaseSpecification.class))).thenReturn(listContract);

        //output
        StatisticalRoomContractResponse output = new StatisticalRoomContractResponse();
        output.setDuration(1L);
        output.setExpiredContract(1);
        output.setAlmostExpiredContract(1);

        //result
        StatisticalRoomContractResponse result = statisticalService.statisticalRoomContract(groupId, duration);

        assertEquals(result.getDuration(), output.getDuration());
        assertEquals(result.getExpiredContract(), output.getExpiredContract());
        assertEquals(result.getDuration(), output.getDuration());

    }

    @Test
    void GivenWrongValue_Then_statisticalRoomContract_ThrowsException() {
        //input
        Long groupId = null;
        Long duration = null;

        //mock  contractRepo.findAll
        List<Contracts> listContract = new ArrayList<>();
        Contracts contract = new Contracts();
        listContract.add(contract);
        when(contractRepo.findAll(any(BaseSpecification.class))).thenThrow(new BusinessException(CONTRACT_NOT_FOUND, "Contracts empty"));

        //output
        StatisticalRoomContractResponse output = new StatisticalRoomContractResponse();
        output.setDuration(1L);
        output.setExpiredContract(1);
        output.setAlmostExpiredContract(1);

        //result
        String messageError = "Contracts empty";

        BusinessException thrown = assertThrows(
                BusinessException.class,
                () -> statisticalService.statisticalRoomContract(groupId, duration)
        );
        assertEquals(thrown.getMessage(), messageError);
        assertEquals(thrown.getErrorStatus(), CONTRACT_NOT_FOUND);

    }
}