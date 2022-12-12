package vn.com.fpt.service.statistical;

import lombok.AllArgsConstructor;;
import org.springframework.stereotype.Service;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.entity.RecurringBill;
import vn.com.fpt.repositories.ContractRepository;
import vn.com.fpt.responses.StatisticalBillResponse;
import vn.com.fpt.responses.StatisticalBillStatusResponse;
import vn.com.fpt.responses.StatisticalRoomContractResponse;
import vn.com.fpt.responses.StatisticalTotalNeedToPaid;
import vn.com.fpt.service.bill.BillService;
import vn.com.fpt.service.contract.ContractService;
import vn.com.fpt.specification.BaseSpecification;
import vn.com.fpt.specification.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static vn.com.fpt.common.constants.ManagerConstants.SUBLEASE_CONTRACT;
import static vn.com.fpt.common.constants.SearchOperation.*;
import static vn.com.fpt.common.utils.DateUtils.*;

@Service
@AllArgsConstructor
public class StatisticalServiceImpl implements StatisticalService {
    private final ContractService contractService;

    private final ContractRepository contractRepo;

    private final BillService billService;



    @Override
    public StatisticalRoomContractResponse statisticalRoomContract(Long groupId, Long duration) {
        List<SearchCriteria> rootCondition = new ArrayList<>();
        var now = now();

        if (!Objects.isNull(groupId)) {
            rootCondition.add(SearchCriteria.of("groupId", groupId, EQUAL));
        }
        rootCondition.add(SearchCriteria.of("contractType", SUBLEASE_CONTRACT, EQUAL));

        // tổng số hợp đồng sắp hết hạn
        BaseSpecification<Contracts> almostExpired = new BaseSpecification<>();
        almostExpired.as(rootCondition);
        almostExpired.add(SearchCriteria.of("contractEndDate", now, GREATER_THAN_EQUAL));
        almostExpired.add(SearchCriteria.of("contractEndDate", monthsCalculate(now, duration), LESS_THAN_EQUAL));
        Integer total1 = contractRepo.findAll(almostExpired).size();

        // tổng số hợp đồng mới tạo
        BaseSpecification<Contracts> latest = new BaseSpecification<>();
        latest.as(rootCondition);
        latest.add(SearchCriteria.of("contractStartDate", monthsCalculate(now, -duration), GREATER_THAN_EQUAL));
        latest.add(SearchCriteria.of("contractStartDate", now, LESS_THAN_EQUAL));
        Integer total2 = contractRepo.findAll(latest).size();

        // tổng số hợp đồng đã hết hạn
        BaseSpecification<Contracts> expired = new BaseSpecification<>();
        expired.as(rootCondition);
        expired.add(SearchCriteria.of("contractEndDate", now, LESS_THAN_EQUAL));

        BaseSpecification<Contracts> isDisable = new BaseSpecification<>();
        isDisable.as(rootCondition);
        isDisable.add(SearchCriteria.of("contractIsDisable", true, EQUAL));
        Integer total3 = contractRepo.findAll(isDisable).size() + contractRepo.findAll(expired).size();

        Integer total4 = contractRepo.findAll(new BaseSpecification<>(rootCondition)).size();

        return StatisticalRoomContractResponse.of(duration, total1, total2, total3, total4);
    }

    @Override
    public StatisticalBillResponse billStatus(Long groupId, Integer paymentCircle, String time) {
        int month;
        int year;
        try {
            var date = parse(time, "MM-yyyy");
            var localDate = toLocalDate(date);
            month = localDate.getMonthValue();
            year = localDate.getYear();
        } catch (Exception e) {
            var date = parse(time, "dd-MM-yyyy");
            var localDate = toLocalDate(date);
            month = localDate.getMonthValue();
            year = localDate.getYear();
        }
        int finalMonth = month;
        int finalYear = year;
        var recurringBill = billService.listRecurringBillByGroupId(groupId).stream().filter(e ->
                        toLocalDate(
                                e.getBillCreatedTime()).getMonthValue() == finalMonth
                                && toLocalDate(e.getBillCreatedTime()).getYear() == finalYear
                )
                .toList();
        if (recurringBill.isEmpty()) return new StatisticalBillResponse(groupId, 0.0, 0.0, paymentCircle, time);
        List<Double> listPaid = recurringBill.stream().filter(e -> e.getIsPaid()).map(RecurringBill::getTotalMoney).toList();
        List<Double> listNeedToPaid = recurringBill.stream().map(RecurringBill::getTotalMoney).toList();
        return new StatisticalBillResponse(groupId,
                listNeedToPaid.stream().mapToDouble(e -> e).sum(),
                listPaid.stream().mapToDouble(e -> e).sum(),
                paymentCircle,
                "" + month + "/" + year);
    }

    @Override
    public Integer totalRoomNotBilled(Long groupId, Integer paymentCircle) {

        return billService.listBillRoomStatus(groupId, paymentCircle).stream().filter(e->e.getIsBilled()==false).toList().size();
    }

    @Override
    public StatisticalBillStatusResponse totalMoneyBillStatus(String time, Long groupId, Integer paymentCircle) {
        int month=0;
        int year=0;
        try {
            var date = parse(time, "MM-yyyy");
            var localDate = toLocalDate(date);
            month = localDate.getMonthValue();
            year = localDate.getYear();
        } catch (Exception e) {
            var date = parse(time, "dd-MM-yyyy");
            var localDate = toLocalDate(date);
            month = localDate.getMonthValue();
            year = localDate.getYear();
        }
        int finalMonth = month;
        int finalYear = year;
        var recurringBill =
                billService.listRecurringBillByGroupId(groupId).stream().filter(e ->
                                toLocalDate(
                                        e.getBillCreatedTime()).getMonthValue() == finalMonth
                                        && toLocalDate(e.getBillCreatedTime()).getYear() == finalYear
                        )
                        .toList();
        return new StatisticalBillStatusResponse(groupId,
                recurringBill.size(),
                recurringBill.stream().filter(e -> e.getIsPaid()).toList().size(),
                time);
    }
}
