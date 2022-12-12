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

    /**
     * Method under test: {@link ConfigServiceImpl#listConfigMonth()}
     */
    @Test
    void testListConfigMonth() {
        ArrayList<Month> monthList = new ArrayList<>();
        when(monthConfigRepository.findAll((Sort) any())).thenReturn(monthList);
        List<Month> actualListConfigMonthResult = configServiceImpl.listConfigMonth();
        assertSame(monthList, actualListConfigMonthResult);
        assertTrue(actualListConfigMonthResult.isEmpty());
        verify(monthConfigRepository).findAll((Sort) any());
    }

    /**
     * Method under test: {@link ConfigServiceImpl#listConfigRoom()}
     */
    @Test
    void testListConfigRoom() {
        ArrayList<TotalRoom> totalRoomList = new ArrayList<>();
        when(roomConfigRepository.findAll((Sort) any())).thenReturn(totalRoomList);
        List<TotalRoom> actualListConfigRoomResult = configServiceImpl.listConfigRoom();
        assertSame(totalRoomList, actualListConfigRoomResult);
        assertTrue(actualListConfigRoomResult.isEmpty());
        verify(roomConfigRepository).findAll((Sort) any());
    }

    /**
     * Method under test: {@link ConfigServiceImpl#listConfigFloor()}
     */
    @Test
    void testListConfigFloor() {
        ArrayList<TotalFloor> totalFloorList = new ArrayList<>();
        when(floorConfigRepository.findAll((Sort) any())).thenReturn(totalFloorList);
        List<TotalFloor> actualListConfigFloorResult = configServiceImpl.listConfigFloor();
        assertSame(totalFloorList, actualListConfigFloorResult);
        assertTrue(actualListConfigFloorResult.isEmpty());
        verify(floorConfigRepository).findAll((Sort) any());
    }

    /**
     * Method under test: {@link ConfigServiceImpl#listAddedCity()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListAddedCity() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "Object.getClass()" because "obj" is null
        //       at jdk.proxy3.$Proxy84.createNativeQuery(null)
        //       at vn.com.fpt.service.configValue.ConfigServiceImpl.listAddedCity(ConfigServiceImpl.java:67)
        //   See https://diff.blue/R013 to resolve this issue.

        MonthConfigRepository monthRepo = mock(MonthConfigRepository.class);
        RoomConfigRepository roomRepo = mock(RoomConfigRepository.class);
        FloorConfigRepository floorRepo = mock(FloorConfigRepository.class);
        SessionDelegatorBaseImpl delegate = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate1 = new SessionDelegatorBaseImpl(delegate,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate2 = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate3 = new SessionDelegatorBaseImpl(delegate1,
                new SessionDelegatorBaseImpl(delegate2, new SessionDelegatorBaseImpl(null, null)));

        SessionDelegatorBaseImpl delegate4 = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate5 = new SessionDelegatorBaseImpl(delegate4,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate6 = new SessionDelegatorBaseImpl(null, null);

        (new ConfigServiceImpl(monthRepo, roomRepo, floorRepo,
                new SessionDelegatorBaseImpl(delegate3, new SessionDelegatorBaseImpl(delegate5,
                        new SessionDelegatorBaseImpl(delegate6, new SessionDelegatorBaseImpl(null, null)))))).listAddedCity();
    }

    /**
     * Method under test: {@link ConfigServiceImpl#listAddedCity()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListAddedCity2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "Object.getClass()" because "obj" is null
        //       at jdk.proxy3.$Proxy84.createNativeQuery(null)
        //       at vn.com.fpt.service.configValue.ConfigServiceImpl.listAddedCity(ConfigServiceImpl.java:67)
        //   See https://diff.blue/R013 to resolve this issue.

        MonthConfigRepository monthRepo = mock(MonthConfigRepository.class);
        RoomConfigRepository roomRepo = mock(RoomConfigRepository.class);
        FloorConfigRepository floorRepo = mock(FloorConfigRepository.class);
        SessionDelegatorBaseImpl delegate = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate1 = new SessionDelegatorBaseImpl(delegate,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate2 = new SessionDelegatorBaseImpl(null, null);

        (new ConfigServiceImpl(monthRepo, roomRepo, floorRepo, new SessionDelegatorBaseImpl(delegate1,
                new SessionDelegatorBaseImpl(delegate2, new SessionDelegatorBaseImpl(null, null))))).listAddedCity();
    }

    /**
     * Method under test: {@link ConfigServiceImpl#listAddedCity()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListAddedCity3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "Object.getClass()" because "obj" is null
        //       at jdk.proxy3.$Proxy84.createNativeQuery(null)
        //       at vn.com.fpt.service.configValue.ConfigServiceImpl.listAddedCity(ConfigServiceImpl.java:67)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ConfigServiceImpl(mock(MonthConfigRepository.class), mock(RoomConfigRepository.class),
                mock(FloorConfigRepository.class), null)).listAddedCity();
    }
}

