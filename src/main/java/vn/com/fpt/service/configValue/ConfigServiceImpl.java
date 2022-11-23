package vn.com.fpt.service.configValue;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import vn.com.fpt.entity.config.Month;
import vn.com.fpt.entity.config.TotalFloor;
import vn.com.fpt.entity.config.TotalRoom;
import vn.com.fpt.model.DistrictDTO;
import vn.com.fpt.repositories.FloorConfigRepository;
import vn.com.fpt.repositories.RoomConfigRepository;
import vn.com.fpt.repositories.config_repo.MonthConfigRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static vn.com.fpt.model.DistrictDTO.SQL_RESULT_SET_MAPPING;

@Service
@Configurable
@AllArgsConstructor
public class ConfigServiceImpl implements ConfigService {

    public final MonthConfigRepository monthRepo;

    private final RoomConfigRepository roomRepo;

    private final FloorConfigRepository floorRepo;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Month> listConfigMonth() {
        return monthRepo.findAll(Sort.by("numberMonth").ascending());
    }

    @Override
    public List<TotalRoom> listConfigRoom() {
        return roomRepo.findAll(Sort.by("numberRoom").ascending());
    }

    @Override
    public List<TotalFloor> listConfigFloor() {
        return floorRepo.findAll(Sort.by("numberFloor").ascending());
    }

    @Override
    public List<DistrictDTO> listAddedCity() {
        StringBuilder selectBuild = new StringBuilder("SELECT ");
        selectBuild.append("DISTINCT c.address_city ");

        StringBuilder fromBuild = new StringBuilder("FROM ");
        fromBuild.append("manager_room_groups a ");
        fromBuild.append("JOIN manager_address c ON c.address_id = a.address_id ");

        String queryBuild = new StringBuilder()
                .append(selectBuild)
                .append(fromBuild)
                .toString();

        Query query = entityManager.createNativeQuery(queryBuild, SQL_RESULT_SET_MAPPING);
        List<DistrictDTO> result = new ArrayList<>(query.getResultList());
        AtomicLong i = new AtomicLong(1);
        result.forEach(e->{
            e.setId(i.getAndIncrement());
        });
        return result;
    }
}
