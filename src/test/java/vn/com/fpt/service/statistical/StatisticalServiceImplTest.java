package vn.com.fpt.service.statistical;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.repositories.ContractRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;

import vn.com.fpt.requests.RegisterRequest;
import vn.com.fpt.responses.AccountResponse;
import vn.com.fpt.responses.BillRoomStatusResponse;
import vn.com.fpt.responses.StatisticalRoomContractResponse;
import vn.com.fpt.service.bill.BillService;
import vn.com.fpt.service.contract.ContractService;
import vn.com.fpt.specification.BaseSpecification;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static vn.com.fpt.common.constants.ErrorStatusConstants.CONTRACT_NOT_FOUND;
import static vn.com.fpt.common.constants.ErrorStatusConstants.EXISTED_ACCOUNT;

@ContextConfiguration(classes = {StatisticalServiceImpl.class})
@ExtendWith(MockitoExtension.class)
class StatisticalServiceImplTest {

    @MockBean
    private BillService billService;

    @MockBean
    private ContractRepository contractRepository;

    @MockBean
    private ContractService contractService;

    @Autowired
    private StatisticalServiceImpl statisticalServiceImpl;

    @InjectMocks
    StatisticalServiceImpl statisticalService;

    @Mock
    private ContractRepository contractRepo;


}