package vn.com.fpt.service.statistical;

import lombok.AllArgsConstructor;;
import org.springframework.stereotype.Service;
import vn.com.fpt.entity.Contracts;
import vn.com.fpt.repositories.ContractRepository;
import vn.com.fpt.responses.StatisticalRoomContractResponse;
import vn.com.fpt.service.contract.ContractService;
import vn.com.fpt.specification.BaseSpecification;
import vn.com.fpt.specification.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static vn.com.fpt.common.constants.ManagerConstants.SUBLEASE_CONTRACT;
import static vn.com.fpt.common.constants.SearchOperation.*;
import static vn.com.fpt.common.utils.DateUtils.*;

@Service
@AllArgsConstructor
public class StatisticalServiceImpl implements StatisticalService {
    private final ContractService contractService;

    private final ContractRepository contractRepo;


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

        return StatisticalRoomContractResponse.of(duration, total1, total2, total3);
    }
}
