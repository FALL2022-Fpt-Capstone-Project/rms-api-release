package vn.com.fpt.service.config_value;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.com.fpt.entity.config.Month;
import vn.com.fpt.repositories.config_repo.MonthConfigRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    public final MonthConfigRepository monthRepo;

    @Override
    public List<Month> listConfigMonth() {
        return monthRepo.findAll(Sort.by("numberMonth").ascending());
    }
}
