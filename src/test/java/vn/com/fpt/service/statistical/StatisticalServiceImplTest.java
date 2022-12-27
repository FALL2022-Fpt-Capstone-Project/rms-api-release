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
        output.setExpiredContract(2);
        output.setAlmostExpiredContract(1);

        //result
        StatisticalRoomContractResponse result = statisticalService.statisticalRoomContract(groupId, duration);

        assertEquals(result.getDuration(), output.getDuration());
        assertEquals(result.getExpiredContract(), output.getExpiredContract());
        assertEquals(result.getDuration(), output.getDuration());

    }

    /**
     * Method under test: {@link StatisticalServiceImpl#statisticalRoomContract(Long, Long)}
     */
    @Test
    void testStatisticalRoomContract() {
        when(contractRepository.findAll((Specification<Contracts>) org.mockito.Mockito.any()))
                .thenReturn(new ArrayList<>());
        StatisticalRoomContractResponse actualStatisticalRoomContractResult = statisticalServiceImpl
                .statisticalRoomContract(123L, 1L);
        assertEquals(0, actualStatisticalRoomContractResult.getAlmostExpiredContract().intValue());
        assertEquals(0, actualStatisticalRoomContractResult.getTotalContract().intValue());
        assertEquals(0, actualStatisticalRoomContractResult.getLatestContract().intValue());
        assertEquals(0, actualStatisticalRoomContractResult.getExpiredContract().intValue());
        assertEquals(1L, actualStatisticalRoomContractResult.getDuration().longValue());
        verify(contractRepository, atLeast(1)).findAll((Specification<Contracts>) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#statisticalRoomContract(Long, Long)}
     */
    @Test
    void testStatisticalRoomContract2() {
        when(contractRepository.findAll((Specification<Contracts>) org.mockito.Mockito.any()))
                .thenReturn(new ArrayList<>());
        StatisticalRoomContractResponse actualStatisticalRoomContractResult = statisticalServiceImpl
                .statisticalRoomContract(123L, 0L);
        assertEquals(0, actualStatisticalRoomContractResult.getAlmostExpiredContract().intValue());
        assertEquals(0, actualStatisticalRoomContractResult.getTotalContract().intValue());
        assertEquals(0, actualStatisticalRoomContractResult.getLatestContract().intValue());
        assertEquals(0, actualStatisticalRoomContractResult.getExpiredContract().intValue());
        assertEquals(0L, actualStatisticalRoomContractResult.getDuration().longValue());
        verify(contractRepository, atLeast(1)).findAll((Specification<Contracts>) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#statisticalRoomContract(Long, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testStatisticalRoomContract3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.time.DateTimeException: Invalid value for Year (valid values -999999999 - 999999999): -768614336404562628
        //       at java.time.temporal.ValueRange.checkValidIntValue(ValueRange.java:338)
        //       at java.time.temporal.ChronoField.checkValidIntValue(ChronoField.java:737)
        //       at java.time.LocalDate.plusMonths(LocalDate.java:1330)
        //       at vn.com.fpt.common.utils.DateUtils.monthsCalculate(DateUtils.java:89)
        //       at vn.com.fpt.service.statistical.StatisticalServiceImpl.statisticalRoomContract(StatisticalServiceImpl.java:51)
        //   See https://diff.blue/R013 to resolve this issue.

        when(contractRepository.findAll((Specification<Contracts>) org.mockito.Mockito.any()))
                .thenReturn(new ArrayList<>());
        statisticalServiceImpl.statisticalRoomContract(123L, Long.MAX_VALUE);
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#statisticalRoomContract(Long, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testStatisticalRoomContract4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.time.DateTimeException: Invalid value for Year (valid values -999999999 - 999999999): -768614336404562628
        //       at java.time.temporal.ValueRange.checkValidIntValue(ValueRange.java:338)
        //       at java.time.temporal.ChronoField.checkValidIntValue(ChronoField.java:737)
        //       at java.time.LocalDate.plusMonths(LocalDate.java:1330)
        //       at java.time.LocalDate.minusMonths(LocalDate.java:1494)
        //       at vn.com.fpt.common.utils.DateUtils.monthsCalculate(DateUtils.java:87)
        //       at vn.com.fpt.service.statistical.StatisticalServiceImpl.statisticalRoomContract(StatisticalServiceImpl.java:51)
        //   See https://diff.blue/R013 to resolve this issue.

        when(contractRepository.findAll((Specification<Contracts>) org.mockito.Mockito.any()))
                .thenReturn(new ArrayList<>());
        statisticalServiceImpl.statisticalRoomContract(123L, Long.MIN_VALUE);
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#billStatus(Long, Integer, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testBillStatus() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Date.toInstant()" because "dateToConvert" is null
        //       at vn.com.fpt.common.utils.DateUtils.toLocalDate(DateUtils.java:70)
        //       at vn.com.fpt.service.statistical.StatisticalServiceImpl.billStatus(StatisticalServiceImpl.java:87)
        //   See https://diff.blue/R013 to resolve this issue.

        statisticalServiceImpl.billStatus(123L, 1, "Time");
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#billStatus(Long, Integer, String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testBillStatus2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Date.toInstant()" because "dateToConvert" is null
        //       at vn.com.fpt.common.utils.DateUtils.toLocalDate(DateUtils.java:70)
        //       at vn.com.fpt.service.statistical.StatisticalServiceImpl.billStatus(StatisticalServiceImpl.java:87)
        //   See https://diff.blue/R013 to resolve this issue.

        statisticalServiceImpl.billStatus(1L, 1, "Time");
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#totalRoomNotBilled(Long, Integer)}
     */
    @Test
    void testTotalRoomNotBilled() {
        when(billService.listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any()))
                .thenReturn(new ArrayList<>());
        assertEquals(0, statisticalServiceImpl.totalRoomNotBilled(123L, 1).intValue());
        verify(billService).listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#totalRoomNotBilled(Long, Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testTotalRoomNotBilled2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.lang.Boolean.booleanValue()" because the return value of "vn.com.fpt.responses.BillRoomStatusResponse.getIsBilled()" is null
        //       at vn.com.fpt.service.statistical.StatisticalServiceImpl.lambda$totalRoomNotBilled$4(StatisticalServiceImpl.java:112)
        //       at java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:575)
        //       at java.util.stream.AbstractPipeline.evaluateToArrayNode(AbstractPipeline.java:260)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:616)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:622)
        //       at java.util.stream.ReferencePipeline.toList(ReferencePipeline.java:627)
        //       at vn.com.fpt.service.statistical.StatisticalServiceImpl.totalRoomNotBilled(StatisticalServiceImpl.java:112)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<BillRoomStatusResponse> billRoomStatusResponseList = new ArrayList<>();
        billRoomStatusResponseList.add(new BillRoomStatusResponse());
        when(billService.listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any()))
                .thenReturn(billRoomStatusResponseList);
        statisticalServiceImpl.totalRoomNotBilled(123L, 1);
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#totalRoomNotBilled(Long, Integer)}
     */
    @Test
    void testTotalRoomNotBilled3() {
        when(billService.listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any()))
                .thenReturn(new ArrayList<>());
        assertEquals(0, statisticalServiceImpl.totalRoomNotBilled(5L, 1).intValue());
        verify(billService).listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#totalRoomNotBilled(Long, Integer)}
     */
    @Test
    void testTotalRoomNotBilled4() {
        when(billService.listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any()))
                .thenReturn(new ArrayList<>());
        assertEquals(0, statisticalServiceImpl.totalRoomNotBilled(1L, 1).intValue());
        verify(billService).listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#totalRoomNotBilled(Long, Integer)}
     */
    @Test
    void testTotalRoomNotBilled5() {
        when(billService.listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any()))
                .thenReturn(new ArrayList<>());
        assertEquals(0, statisticalServiceImpl.totalRoomNotBilled(0L, 1).intValue());
        verify(billService).listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#totalRoomNotBilled(Long, Integer)}
     */
    @Test
    void testTotalRoomNotBilled6() {
        BillRoomStatusResponse billRoomStatusResponse = new BillRoomStatusResponse();
        billRoomStatusResponse.setIsBilled(true);

        ArrayList<BillRoomStatusResponse> billRoomStatusResponseList = new ArrayList<>();
        billRoomStatusResponseList.add(billRoomStatusResponse);
        when(billService.listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any()))
                .thenReturn(billRoomStatusResponseList);
        assertEquals(0, statisticalServiceImpl.totalRoomNotBilled(123L, 1).intValue());
        verify(billService).listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#totalRoomNotBilled(Long, Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testTotalRoomNotBilled7() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.responses.BillRoomStatusResponse.getIsBilled()" because "e" is null
        //       at vn.com.fpt.service.statistical.StatisticalServiceImpl.lambda$totalRoomNotBilled$4(StatisticalServiceImpl.java:112)
        //       at java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:178)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:575)
        //       at java.util.stream.AbstractPipeline.evaluateToArrayNode(AbstractPipeline.java:260)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:616)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:622)
        //       at java.util.stream.ReferencePipeline.toList(ReferencePipeline.java:627)
        //       at vn.com.fpt.service.statistical.StatisticalServiceImpl.totalRoomNotBilled(StatisticalServiceImpl.java:112)
        //   See https://diff.blue/R013 to resolve this issue.

        ArrayList<BillRoomStatusResponse> billRoomStatusResponseList = new ArrayList<>();
        billRoomStatusResponseList.add(null);
        when(billService.listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any()))
                .thenReturn(billRoomStatusResponseList);
        statisticalServiceImpl.totalRoomNotBilled(123L, 1);
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#totalRoomNotBilled(Long, Integer)}
     */
    @Test
    void testTotalRoomNotBilled8() {
        BillRoomStatusResponse billRoomStatusResponse = mock(BillRoomStatusResponse.class);
        when(billRoomStatusResponse.getIsBilled()).thenReturn(false);

        ArrayList<BillRoomStatusResponse> billRoomStatusResponseList = new ArrayList<>();
        billRoomStatusResponseList.add(billRoomStatusResponse);
        when(billService.listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any()))
                .thenReturn(billRoomStatusResponseList);
        assertEquals(1, statisticalServiceImpl.totalRoomNotBilled(123L, 1).intValue());
        verify(billService).listBillRoomStatus((Long) org.mockito.Mockito.any(), (Integer) org.mockito.Mockito.any());
        verify(billRoomStatusResponse).getIsBilled();
    }

    /**
     * Method under test: {@link StatisticalServiceImpl#totalRoomNotBilled(Long, Integer)}
     */

    /**
     * Method under test: {@link StatisticalServiceImpl#totalMoneyBillStatus(String, Long, Integer)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testTotalMoneyBillStatus() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Date.toInstant()" because "dateToConvert" is null
        //       at vn.com.fpt.common.utils.DateUtils.toLocalDate(DateUtils.java:70)
        //       at vn.com.fpt.service.statistical.StatisticalServiceImpl.totalMoneyBillStatus(StatisticalServiceImpl.java:126)
        //   See https://diff.blue/R013 to resolve this issue.

        statisticalServiceImpl.totalMoneyBillStatus("Time", 123L, 1);
    }
}