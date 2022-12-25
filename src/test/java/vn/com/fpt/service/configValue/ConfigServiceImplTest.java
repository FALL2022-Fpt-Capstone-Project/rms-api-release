package vn.com.fpt.service.configValue;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.engine.spi.SessionDelegatorBaseImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.com.fpt.entity.config.Month;
import vn.com.fpt.entity.config.TotalFloor;
import vn.com.fpt.entity.config.TotalRoom;
import vn.com.fpt.repositories.FloorConfigRepository;
import vn.com.fpt.repositories.RoomConfigRepository;
import vn.com.fpt.repositories.config_repo.MonthConfigRepository;

@ContextConfiguration(classes = {ConfigServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ConfigServiceImplTest {
    @Autowired
    private ConfigServiceImpl configServiceImpl;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private FloorConfigRepository floorConfigRepository;

    @MockBean
    private MonthConfigRepository monthConfigRepository;

    @MockBean
    private RoomConfigRepository roomConfigRepository;

}

