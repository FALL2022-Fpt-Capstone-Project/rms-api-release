package vn.com.fpt.service.statistical;

import lombok.AllArgsConstructor;;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.com.fpt.common.utils.Operator;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.entity.MoneySource;
import vn.com.fpt.entity.RecurringBill;
import vn.com.fpt.repositories.ContractRepository;
import vn.com.fpt.repositories.MoneySourceRepository;
import vn.com.fpt.repositories.RecurringBillRepository;
import vn.com.fpt.responses.*;
import vn.com.fpt.service.bill.BillService;
import vn.com.fpt.service.contract.ContractService;
import vn.com.fpt.service.group.GroupService;
import vn.com.fpt.service.rooms.RoomService;
import vn.com.fpt.specification.BaseSpecification;
import vn.com.fpt.specification.SearchCriteria;

import java.time.ZoneId;
import java.util.*;

import static vn.com.fpt.common.constants.ManagerConstants.MONTH;
import static vn.com.fpt.common.constants.ManagerConstants.SUBLEASE_CONTRACT;
import static vn.com.fpt.common.constants.SearchOperation.*;
import static vn.com.fpt.common.utils.DateUtils.*;

@Service
@AllArgsConstructor
public class StatisticalServiceImpl implements StatisticalService {
    private final ContractService contractService;

    private final ContractRepository contractRepo;

    private final BillService billService;

    private final RoomService roomService;

    private final GroupService groupService;

    private final RecurringBillRepository billRepository;

    private final MoneySourceRepository moneySourceRepo;


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
        var recurringBill = billService.listRecurringBillByGroupId(groupId).stream().filter(e -> toLocalDate(e.getBillCreatedTime()).getMonthValue() == finalMonth && toLocalDate(e.getBillCreatedTime()).getYear() == finalYear).toList();
        if (recurringBill.isEmpty()) return new StatisticalBillResponse(groupId, 0.0, 0.0, paymentCircle, time);
        List<Double> listPaid = recurringBill.stream().filter(e -> e.getIsPaid()).map(RecurringBill::getTotalMoney).toList();
        List<Double> listNeedToPaid = recurringBill.stream().map(RecurringBill::getTotalMoney).toList();
        return new StatisticalBillResponse(groupId, listNeedToPaid.stream().mapToDouble(e -> e).sum(), listPaid.stream().mapToDouble(e -> e).sum(), paymentCircle, "" + month + "/" + year);
    }

    @Override
    public Integer totalRoomNotBilled(Long groupId, Integer paymentCircle) {

        return billService.listBillRoomStatus(groupId, paymentCircle).stream().filter(e -> e.getIsBilled() == false).toList().size();
    }

    @Override
    public StatisticalBillStatusResponse totalMoneyBillStatus(String time, Long groupId, Integer paymentCircle) {
        int month = 0;
        int year = 0;
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
        var recurringBill = billService.listRecurringBillByGroupId(groupId).stream().filter(e -> toLocalDate(e.getBillCreatedTime()).getMonthValue() == finalMonth && toLocalDate(e.getBillCreatedTime()).getYear() == finalYear).toList();
        return new StatisticalBillStatusResponse(groupId, recurringBill.size(), recurringBill.stream().filter(e -> e.getIsPaid()).toList().size(), time);
    }

    @Override
    public List<ListBilledRoomResponse> listBilledRoom(Long groupId, String createdTime) {
        List<RecurringBill> recurringBills = new ArrayList<>(Collections.emptyList());
        if (createdTime != null) {
            int month = toLocalDate(Objects.requireNonNull(parse(createdTime, "MM-yyyy"))).getMonthValue();
            int year = toLocalDate(Objects.requireNonNull(parse(createdTime, "MM-yyyy"))).getYear();
            if (groupId == null) {
                recurringBills.addAll(billRepository.findAllByIsPaid(false).stream().filter(e -> toLocalDate(e.getBillCreatedTime()).getMonthValue() == month && toLocalDate(e.getBillCreatedTime()).getYear() == year).toList());
            } else {
                recurringBills.addAll(billService.listRecurringBillByGroupId(groupId).stream().filter(e -> !e.getIsPaid() && toLocalDate(e.getBillCreatedTime()).getMonthValue() == month && toLocalDate(e.getBillCreatedTime()).getYear() == year).toList());
            }
        } else {
            if (groupId == null) {
                recurringBills.addAll(billRepository.findAllByIsPaid(false));
            } else {
                recurringBills.addAll(billService.listRecurringBillByGroupId(groupId).stream().filter(e -> !e.getIsPaid()).toList());
            }
        }

        return recurringBills.stream().map(e -> {
            var room = roomService.room(e.getRoomId());
            var group = groupService.getGroup(e.getGroupId());
            return new ListBilledRoomResponse(room.getId(), room.getRoomName(), groupId, group.getGroupName(), format(e.getBillCreatedTime(), DATE_FORMAT_3), e.getPaymentTerm1(), e.getTotalMoney());
        }).toList();
    }

    @Override
    public StatisticalRoomStatusResponse statisticalRoomStatus(Long groupId) {
        var listRoom = roomService.listRoom(groupId, null, null, null, null);
        StatisticalRoomStatusResponse response = new StatisticalRoomStatusResponse();
        var totalLeaseRentedRoom = listRoom.stream().filter(e -> e.getGroupContractId() != null && !e.getIsDisable()).toList().size();
        var totalEmptyRoom = listRoom.stream().filter(e -> e.getContractId() == null && !e.getIsDisable() && e.getGroupContractId() != null).toList().size();
        var totalRentedRoom = listRoom.stream().filter(e -> e.getContractId() != null && !e.getIsDisable() && e.getGroupContractId() != null).toList().size();
        if (groupId == null) {
            response.setGroupId(null);
            response.setGroupName("all");
        } else {
            response.setGroupId(groupId);
            response.setGroupName(groupService.getGroup(groupId).getGroupName());
        }
        response.setTotalRoom(totalLeaseRentedRoom);
        response.setTotalEmptyRoom(totalEmptyRoom);
        response.setTotalRentedRoom(totalRentedRoom);
        return response;
    }

    @Override
    public List<StatisticalChartRevenueResponse> chartRevenue(Integer year) {
        var allBill = billRepository.findAllByIsPaid(true);
        var moneySourceOut = moneySourceRepo.findAllByMoneyType("OUT");
        List<StatisticalChartRevenueResponse> response = new ArrayList<>(Collections.emptyList());
        for (int i : MONTH) {
            var moneyIn = allBill.stream().filter(e -> toLocalDate(e.getBillCreatedTime()).getMonthValue() == i && toLocalDate(e.getBillCreatedTime()).getYear() == year).mapToDouble(RecurringBill::getTotalMoney).sum();
            var moneyOut = moneySourceOut.stream().filter(e -> toLocalDate(e.getMoneySourceTime()).getMonthValue() == i && toLocalDate(e.getMoneySourceTime()).getYear() == year).mapToDouble(MoneySource::getTotalMoney).sum();
            response.add(new StatisticalChartRevenueResponse(year, i, moneyIn - moneyOut));
        }

        return response;
    }

    @Override
    public StatisticalChartContractResponse chartContract(Integer year) {
        var listContract = contractRepo.findAllByContractType(SUBLEASE_CONTRACT);

        var listCreatedInYear = listContract.stream().filter(e -> toLocalDate(e.getContractStartDate()).getYear() == year && !e.getContractIsDisable()).toList();

        var listEndedInYear = listContract.stream().filter(e -> toLocalDate(e.getModifiedAt() == null ? now() : e.getModifiedAt()).getYear() == year && e.getContractIsDisable()).toList();
        listEndedInYear.forEach(
                e -> {
                    if (e.getModifiedAt() == null) e.setModifiedAt(now());
                }
        );

        List<StatisticalChartContractResponse.StatisticalContract> byMonth = new ArrayList<>(Collections.emptyList());
        for (int i : MONTH) {
            var statisticalContract = new StatisticalChartContractResponse.StatisticalContract();
            statisticalContract.setMonth(i);
            statisticalContract.setYear(year);
            statisticalContract.setTotalCreated(listCreatedInYear.stream().filter(e -> toLocalDate(e.getContractStartDate()).getMonthValue() == i).toList().size());
            statisticalContract.setTotalEnded(listEndedInYear.stream().filter(e -> toLocalDate(e.getModifiedAt()).getMonthValue() == i).toList().size());

            byMonth.add(statisticalContract);
        }

        StatisticalChartContractResponse response = new StatisticalChartContractResponse();
        response.setTotalAllEnded(listEndedInYear.size());
        response.setTotalAllCreated(listCreatedInYear.size());
        response.setListByMonth(byMonth);

        return response;
    }

    @Scheduled(cron = "0 * * * * ?")
    private void extendContract() {
        var listContract = contractRepo.findAllByContractType(SUBLEASE_CONTRACT).stream().filter(e -> !e.getContractIsDisable() && e.getContractEndDate().compareTo(now()) < 0).toList();
        if (!listContract.isEmpty()) {
            listContract.forEach(e -> {
                var extend = Date.from(toLocalDate(e.getContractEndDate()).plusYears(1L).atStartOfDay(ZoneId.systemDefault()).toInstant());
                e.setContractEndDate(extend);
                e.setContractTerm(monthsBetween(e.getContractStartDate(), e.getContractEndDate()));
                e.setModifiedBy(Operator.operator());
                e.setModifiedAt(now());
            });
            contractRepo.saveAll(listContract);
        }
    }
}

