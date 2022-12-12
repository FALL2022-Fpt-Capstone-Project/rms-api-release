package vn.com.fpt.service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

import org.hibernate.engine.spi.SessionDelegatorBaseImpl;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import vn.com.fpt.common.BusinessException;
import vn.com.fpt.entity.BasicServices;
import vn.com.fpt.entity.GeneralService;
import vn.com.fpt.entity.HandOverGeneralServices;
import vn.com.fpt.entity.ServiceTypes;
import vn.com.fpt.repositories.BasicServicesRepository;
import vn.com.fpt.repositories.GeneralServiceRepository;
import vn.com.fpt.repositories.HandOverGeneralServicesRepository;
import vn.com.fpt.repositories.ServiceTypesRepository;
import vn.com.fpt.requests.GeneralServiceRequest;
import vn.com.fpt.requests.HandOverGeneralServiceRequest;

@ContextConfiguration(classes = {ServicesServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ServicesServiceImplTest {
    @MockBean
    private BasicServicesRepository basicServicesRepository;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private GeneralServiceRepository generalServiceRepository;

    @MockBean
    private HandOverGeneralServicesRepository handOverGeneralServicesRepository;

    @MockBean
    private ServiceTypesRepository serviceTypesRepository;

    @Autowired
    private ServicesServiceImpl servicesServiceImpl;

    /**
     * Method under test: {@link ServicesServiceImpl#listGeneralService(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListGeneralService() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.persistence.Query.setParameter(String, Object)" because "query" is null
        //       at vn.com.fpt.service.services.ServicesServiceImpl.listGeneralService(ServicesServiceImpl.java:71)
        //   See https://diff.blue/R013 to resolve this issue.

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

        (new ServicesServiceImpl(
                new SessionDelegatorBaseImpl(delegate3,
                        new SessionDelegatorBaseImpl(delegate5,
                                new SessionDelegatorBaseImpl(delegate6, new SessionDelegatorBaseImpl(null, null)))),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class))).listGeneralService(123L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#listGeneralService(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListGeneralService2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.persistence.Query.setParameter(String, Object)" because "query" is null
        //       at vn.com.fpt.service.services.ServicesServiceImpl.listGeneralService(ServicesServiceImpl.java:71)
        //   See https://diff.blue/R013 to resolve this issue.

        SessionDelegatorBaseImpl delegate = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate1 = new SessionDelegatorBaseImpl(delegate,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate2 = new SessionDelegatorBaseImpl(null, null);

        (new ServicesServiceImpl(
                new SessionDelegatorBaseImpl(delegate1,
                        new SessionDelegatorBaseImpl(delegate2, new SessionDelegatorBaseImpl(null, null))),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class))).listGeneralService(1L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#listGeneralService(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListGeneralService3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.persistence.Query.setParameter(String, Object)" because "query" is null
        //       at vn.com.fpt.service.services.ServicesServiceImpl.listGeneralService(ServicesServiceImpl.java:71)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ServicesServiceImpl(null, mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class),
                mock(GeneralServiceRepository.class), mock(HandOverGeneralServicesRepository.class)))
                .listGeneralService(123L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#listGeneralServiceByGroupId(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListGeneralServiceByGroupId() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.persistence.Query.setParameter(String, Object)" because "query" is null
        //       at vn.com.fpt.service.services.ServicesServiceImpl.listGeneralServiceByGroupId(ServicesServiceImpl.java:103)
        //   See https://diff.blue/R013 to resolve this issue.

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

        (new ServicesServiceImpl(
                new SessionDelegatorBaseImpl(delegate3,
                        new SessionDelegatorBaseImpl(delegate5,
                                new SessionDelegatorBaseImpl(delegate6, new SessionDelegatorBaseImpl(null, null)))),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class))).listGeneralServiceByGroupId(123L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#listGeneralServiceByGroupId(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListGeneralServiceByGroupId2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.persistence.Query.setParameter(String, Object)" because "query" is null
        //       at vn.com.fpt.service.services.ServicesServiceImpl.listGeneralServiceByGroupId(ServicesServiceImpl.java:103)
        //   See https://diff.blue/R013 to resolve this issue.

        SessionDelegatorBaseImpl delegate = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate1 = new SessionDelegatorBaseImpl(delegate,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate2 = new SessionDelegatorBaseImpl(null, null);

        (new ServicesServiceImpl(
                new SessionDelegatorBaseImpl(delegate1,
                        new SessionDelegatorBaseImpl(delegate2, new SessionDelegatorBaseImpl(null, null))),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class))).listGeneralServiceByGroupId(1L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#listGeneralServiceByGroupId(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListGeneralServiceByGroupId3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.persistence.Query.setParameter(String, Object)" because "query" is null
        //       at vn.com.fpt.service.services.ServicesServiceImpl.listGeneralServiceByGroupId(ServicesServiceImpl.java:103)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ServicesServiceImpl(null, mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class),
                mock(GeneralServiceRepository.class), mock(HandOverGeneralServicesRepository.class)))
                .listGeneralServiceByGroupId(123L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#listGeneralServiceByGroupIdAndContractId(Long, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListGeneralServiceByGroupIdAndContractId() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.persistence.Query.setParameter(String, Object)" because "query" is null
        //       at vn.com.fpt.service.services.ServicesServiceImpl.listGeneralServiceByGroupIdAndContractId(ServicesServiceImpl.java:136)
        //   See https://diff.blue/R013 to resolve this issue.

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

        (new ServicesServiceImpl(
                new SessionDelegatorBaseImpl(delegate3,
                        new SessionDelegatorBaseImpl(delegate5,
                                new SessionDelegatorBaseImpl(delegate6, new SessionDelegatorBaseImpl(null, null)))),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class))).listGeneralServiceByGroupIdAndContractId(123L, 123L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#listGeneralServiceByGroupIdAndContractId(Long, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListGeneralServiceByGroupIdAndContractId2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.persistence.Query.setParameter(String, Object)" because "query" is null
        //       at vn.com.fpt.service.services.ServicesServiceImpl.listGeneralServiceByGroupIdAndContractId(ServicesServiceImpl.java:136)
        //   See https://diff.blue/R013 to resolve this issue.

        SessionDelegatorBaseImpl delegate = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate1 = new SessionDelegatorBaseImpl(delegate,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate2 = new SessionDelegatorBaseImpl(null, null);

        (new ServicesServiceImpl(
                new SessionDelegatorBaseImpl(delegate1,
                        new SessionDelegatorBaseImpl(delegate2, new SessionDelegatorBaseImpl(null, null))),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class))).listGeneralServiceByGroupIdAndContractId(1L, 1L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#listGeneralServiceByGroupIdAndContractId(Long, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListGeneralServiceByGroupIdAndContractId3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.persistence.Query.setParameter(String, Object)" because "query" is null
        //       at vn.com.fpt.service.services.ServicesServiceImpl.listGeneralServiceByGroupIdAndContractId(ServicesServiceImpl.java:136)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ServicesServiceImpl(null, mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class),
                mock(GeneralServiceRepository.class), mock(HandOverGeneralServicesRepository.class)))
                .listGeneralServiceByGroupIdAndContractId(123L, 123L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#generalService(Long)}
     */
    @Test
    void testGeneralService() {
        GeneralService generalService = new GeneralService();
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);
        Optional<GeneralService> ofResult = Optional.of(generalService);
        when(generalServiceRepository.findById((Long) any())).thenReturn(ofResult);
        when(entityManager.createNativeQuery((String) any(), (String) any())).thenThrow(new BusinessException("SELECT "));
        assertThrows(BusinessException.class, () -> servicesServiceImpl.generalService(123L));
        verify(generalServiceRepository).findById((Long) any());
        verify(entityManager).createNativeQuery((String) any(), (String) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#listHandOverGeneralService(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListHandOverGeneralService() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.persistence.Query.setParameter(String, Object)" because "query" is null
        //       at vn.com.fpt.service.services.ServicesServiceImpl.listHandOverGeneralService(ServicesServiceImpl.java:207)
        //   See https://diff.blue/R013 to resolve this issue.

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

        (new ServicesServiceImpl(
                new SessionDelegatorBaseImpl(delegate3,
                        new SessionDelegatorBaseImpl(delegate5,
                                new SessionDelegatorBaseImpl(delegate6, new SessionDelegatorBaseImpl(null, null)))),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class))).listHandOverGeneralService(123L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#listHandOverGeneralService(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListHandOverGeneralService2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.persistence.Query.setParameter(String, Object)" because "query" is null
        //       at vn.com.fpt.service.services.ServicesServiceImpl.listHandOverGeneralService(ServicesServiceImpl.java:207)
        //   See https://diff.blue/R013 to resolve this issue.

        SessionDelegatorBaseImpl delegate = new SessionDelegatorBaseImpl(null, null);

        SessionDelegatorBaseImpl delegate1 = new SessionDelegatorBaseImpl(delegate,
                new SessionDelegatorBaseImpl(null, null));

        SessionDelegatorBaseImpl delegate2 = new SessionDelegatorBaseImpl(null, null);

        (new ServicesServiceImpl(
                new SessionDelegatorBaseImpl(delegate1,
                        new SessionDelegatorBaseImpl(delegate2, new SessionDelegatorBaseImpl(null, null))),
                mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class), mock(GeneralServiceRepository.class),
                mock(HandOverGeneralServicesRepository.class))).listHandOverGeneralService(1L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#listHandOverGeneralService(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testListHandOverGeneralService3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "javax.persistence.Query.setParameter(String, Object)" because "query" is null
        //       at vn.com.fpt.service.services.ServicesServiceImpl.listHandOverGeneralService(ServicesServiceImpl.java:207)
        //   See https://diff.blue/R013 to resolve this issue.

        (new ServicesServiceImpl(null, mock(BasicServicesRepository.class), mock(ServiceTypesRepository.class),
                mock(GeneralServiceRepository.class), mock(HandOverGeneralServicesRepository.class)))
                .listHandOverGeneralService(123L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#deleteGeneralHandOverService(List)}
     */
    @Test
    void testDeleteGeneralHandOverService() {
        doNothing().when(handOverGeneralServicesRepository).deleteAll((Iterable<HandOverGeneralServices>) any());
        ArrayList<HandOverGeneralServices> handOverGeneralServicesList = new ArrayList<>();
        List<HandOverGeneralServices> actualDeleteGeneralHandOverServiceResult = servicesServiceImpl
                .deleteGeneralHandOverService(handOverGeneralServicesList);
        assertSame(handOverGeneralServicesList, actualDeleteGeneralHandOverServiceResult);
        assertTrue(actualDeleteGeneralHandOverServiceResult.isEmpty());
        verify(handOverGeneralServicesRepository).deleteAll((Iterable<HandOverGeneralServices>) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#deleteGeneralHandOverService(List)}
     */
    @Test
    void testDeleteGeneralHandOverService2() {
        doThrow(new BusinessException("Msg")).when(handOverGeneralServicesRepository)
                .deleteAll((Iterable<HandOverGeneralServices>) any());
        assertThrows(BusinessException.class, () -> servicesServiceImpl.deleteGeneralHandOverService(new ArrayList<>()));
        verify(handOverGeneralServicesRepository).deleteAll((Iterable<HandOverGeneralServices>) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#handOverGeneralServices(Long)}
     */
    @Test
    void testHandOverGeneralServices() {
        ArrayList<HandOverGeneralServices> handOverGeneralServicesList = new ArrayList<>();
        when(handOverGeneralServicesRepository.findAllByContractId((Long) any())).thenReturn(handOverGeneralServicesList);
        List<HandOverGeneralServices> actualHandOverGeneralServicesResult = servicesServiceImpl
                .handOverGeneralServices(123L);
        assertSame(handOverGeneralServicesList, actualHandOverGeneralServicesResult);
        assertTrue(actualHandOverGeneralServicesResult.isEmpty());
        verify(handOverGeneralServicesRepository).findAllByContractId((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#handOverGeneralServices(Long)}
     */
    @Test
    void testHandOverGeneralServices2() {
        when(handOverGeneralServicesRepository.findAllByContractId((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> servicesServiceImpl.handOverGeneralServices(123L));
        verify(handOverGeneralServicesRepository).findAllByContractId((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#basicServices()}
     */
    @Test
    void testBasicServices() {
        ArrayList<BasicServices> basicServicesList = new ArrayList<>();
        when(basicServicesRepository.findAll()).thenReturn(basicServicesList);
        List<BasicServices> actualBasicServicesResult = servicesServiceImpl.basicServices();
        assertSame(basicServicesList, actualBasicServicesResult);
        assertTrue(actualBasicServicesResult.isEmpty());
        verify(basicServicesRepository).findAll();
    }

    /**
     * Method under test: {@link ServicesServiceImpl#basicServices()}
     */
    @Test
    void testBasicServices2() {
        when(basicServicesRepository.findAll()).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> servicesServiceImpl.basicServices());
        verify(basicServicesRepository).findAll();
    }

    /**
     * Method under test: {@link ServicesServiceImpl#basicService(Long)}
     */
    @Test
    void testBasicService() {
        BasicServices basicServices = new BasicServices();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        basicServices.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        basicServices.setCreatedBy(1L);
        basicServices.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        basicServices.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        basicServices.setModifiedBy(1L);
        basicServices.setServiceName("Service Name");
        basicServices.setServiceShowName("Service Show Name");
        Optional<BasicServices> ofResult = Optional.of(basicServices);
        when(basicServicesRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(basicServices, servicesServiceImpl.basicService(123L));
        verify(basicServicesRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#basicService(Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testBasicService2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.util.NoSuchElementException: No value present
        //       at java.util.Optional.get(Optional.java:143)
        //       at vn.com.fpt.service.services.ServicesServiceImpl.basicService(ServicesServiceImpl.java:230)
        //   See https://diff.blue/R013 to resolve this issue.

        when(basicServicesRepository.findById((Long) any())).thenReturn(Optional.empty());
        servicesServiceImpl.basicService(123L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#basicService(Long)}
     */
    @Test
    void testBasicService3() {
        when(basicServicesRepository.findById((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> servicesServiceImpl.basicService(123L));
        verify(basicServicesRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#serviceTypes()}
     */
    @Test
    void testServiceTypes() {
        ArrayList<ServiceTypes> serviceTypesList = new ArrayList<>();
        when(serviceTypesRepository.findAll()).thenReturn(serviceTypesList);
        List<ServiceTypes> actualServiceTypesResult = servicesServiceImpl.serviceTypes();
        assertSame(serviceTypesList, actualServiceTypesResult);
        assertTrue(actualServiceTypesResult.isEmpty());
        verify(serviceTypesRepository).findAll();
    }

    /**
     * Method under test: {@link ServicesServiceImpl#serviceTypes()}
     */
    @Test
    void testServiceTypes2() {
        when(serviceTypesRepository.findAll()).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> servicesServiceImpl.serviceTypes());
        verify(serviceTypesRepository).findAll();
    }

    /**
     * Method under test: {@link ServicesServiceImpl#updateGeneralService(Long, GeneralServiceRequest, Long)}
     */
    @Test
    void testUpdateGeneralService() {
        GeneralService generalService = new GeneralService();
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);
        Optional<GeneralService> ofResult = Optional.of(generalService);

        GeneralService generalService1 = new GeneralService();
        generalService1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        generalService1.setCreatedBy(1L);
        generalService1.setGroupId(123L);
        generalService1.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        generalService1.setModifiedBy(1L);
        generalService1.setNote("Note");
        generalService1.setServiceId(123L);
        generalService1.setServicePrice(10.0d);
        generalService1.setServiceTypeId(123L);
        when(generalServiceRepository.save((GeneralService) any())).thenReturn(generalService1);
        when(generalServiceRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(generalService1, servicesServiceImpl.updateGeneralService(123L, new GeneralServiceRequest(), 1L));
        verify(generalServiceRepository).save((GeneralService) any());
        verify(generalServiceRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#updateGeneralService(Long, GeneralServiceRequest, Long)}
     */
    @Test
    void testUpdateGeneralService2() {
        GeneralService generalService = new GeneralService();
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);
        Optional<GeneralService> ofResult = Optional.of(generalService);
        when(generalServiceRepository.save((GeneralService) any())).thenThrow(new BusinessException("Msg"));
        when(generalServiceRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(BusinessException.class,
                () -> servicesServiceImpl.updateGeneralService(123L, new GeneralServiceRequest(), 1L));
        verify(generalServiceRepository).save((GeneralService) any());
        verify(generalServiceRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#updateGeneralService(Long, GeneralServiceRequest, Long)}
     */
    @Test
    void testUpdateGeneralService3() {
        GeneralService generalService = new GeneralService();
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);
        when(generalServiceRepository.save((GeneralService) any())).thenReturn(generalService);
        when(generalServiceRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class,
                () -> servicesServiceImpl.updateGeneralService(123L, new GeneralServiceRequest(), 1L));
        verify(generalServiceRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#updateGeneralService(Long, GeneralServiceRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateGeneralService4() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.requests.GeneralServiceRequest.getServiceId()" because "request" is null
        //       at vn.com.fpt.entity.GeneralService.of(GeneralService.java:60)
        //       at vn.com.fpt.entity.GeneralService.modify(GeneralService.java:76)
        //       at vn.com.fpt.service.services.ServicesServiceImpl.updateGeneralService(ServicesServiceImpl.java:241)
        //   See https://diff.blue/R013 to resolve this issue.

        GeneralService generalService = new GeneralService();
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);
        Optional<GeneralService> ofResult = Optional.of(generalService);

        GeneralService generalService1 = new GeneralService();
        generalService1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        generalService1.setCreatedBy(1L);
        generalService1.setGroupId(123L);
        generalService1.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        generalService1.setModifiedBy(1L);
        generalService1.setNote("Note");
        generalService1.setServiceId(123L);
        generalService1.setServicePrice(10.0d);
        generalService1.setServiceTypeId(123L);
        when(generalServiceRepository.save((GeneralService) any())).thenReturn(generalService1);
        when(generalServiceRepository.findById((Long) any())).thenReturn(ofResult);
        servicesServiceImpl.updateGeneralService(123L, null, 1L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#addGeneralService(List, Long)}
     */
    @Test
    void testAddGeneralService() {
        ArrayList<GeneralService> generalServiceList = new ArrayList<>();
        when(generalServiceRepository.saveAll((Iterable<GeneralService>) any())).thenReturn(generalServiceList);
        List<GeneralService> actualAddGeneralServiceResult = servicesServiceImpl.addGeneralService(new ArrayList<>(), 1L);
        assertSame(generalServiceList, actualAddGeneralServiceResult);
        assertTrue(actualAddGeneralServiceResult.isEmpty());
        verify(generalServiceRepository).saveAll((Iterable<GeneralService>) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#addGeneralService(List, Long)}
     */
    @Test
    void testAddGeneralService2() {
        ArrayList<GeneralService> generalServiceList = new ArrayList<>();
        when(generalServiceRepository.saveAll((Iterable<GeneralService>) any())).thenReturn(generalServiceList);

        ArrayList<GeneralServiceRequest> generalServiceRequestList = new ArrayList<>();
        generalServiceRequestList.add(new GeneralServiceRequest());
        List<GeneralService> actualAddGeneralServiceResult = servicesServiceImpl
                .addGeneralService(generalServiceRequestList, 1L);
        assertSame(generalServiceList, actualAddGeneralServiceResult);
        assertTrue(actualAddGeneralServiceResult.isEmpty());
        verify(generalServiceRepository).saveAll((Iterable<GeneralService>) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#addGeneralService(List, Long)}
     */
    @Test
    void testAddGeneralService3() {
        ArrayList<GeneralService> generalServiceList = new ArrayList<>();
        when(generalServiceRepository.saveAll((Iterable<GeneralService>) any())).thenReturn(generalServiceList);

        ArrayList<GeneralServiceRequest> generalServiceRequestList = new ArrayList<>();
        generalServiceRequestList.add(new GeneralServiceRequest());
        generalServiceRequestList.add(new GeneralServiceRequest());
        List<GeneralService> actualAddGeneralServiceResult = servicesServiceImpl
                .addGeneralService(generalServiceRequestList, 1L);
        assertSame(generalServiceList, actualAddGeneralServiceResult);
        assertTrue(actualAddGeneralServiceResult.isEmpty());
        verify(generalServiceRepository).saveAll((Iterable<GeneralService>) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#addGeneralService(List, Long)}
     */
    @Test
    void testAddGeneralService4() {
        when(generalServiceRepository.saveAll((Iterable<GeneralService>) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> servicesServiceImpl.addGeneralService(new ArrayList<>(), 1L));
        verify(generalServiceRepository).saveAll((Iterable<GeneralService>) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#addGeneralService(List, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddGeneralService5() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.requests.GeneralServiceRequest.getServiceId()" because "request" is null
        //       at vn.com.fpt.entity.GeneralService.of(GeneralService.java:60)
        //       at java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:575)
        //       at java.util.stream.AbstractPipeline.evaluateToArrayNode(AbstractPipeline.java:260)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:616)
        //       at java.util.stream.ReferencePipeline.toArray(ReferencePipeline.java:622)
        //       at java.util.stream.ReferencePipeline.toList(ReferencePipeline.java:627)
        //       at vn.com.fpt.service.services.ServicesServiceImpl.addGeneralService(ServicesServiceImpl.java:251)
        //   See https://diff.blue/R013 to resolve this issue.

        when(generalServiceRepository.saveAll((Iterable<GeneralService>) any())).thenReturn(new ArrayList<>());

        ArrayList<GeneralServiceRequest> generalServiceRequestList = new ArrayList<>();
        generalServiceRequestList.add(null);
        servicesServiceImpl.addGeneralService(generalServiceRequestList, 1L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#addGeneralService(GeneralServiceRequest, Long)}
     */
    @Test
    void testAddGeneralService6() {
        GeneralService generalService = new GeneralService();
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);
        when(generalServiceRepository.save((GeneralService) any())).thenReturn(generalService);
        assertSame(generalService, servicesServiceImpl.addGeneralService(new GeneralServiceRequest(), 1L));
        verify(generalServiceRepository).save((GeneralService) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#addGeneralService(GeneralServiceRequest, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddGeneralService7() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.requests.GeneralServiceRequest.getServiceId()" because "request" is null
        //       at vn.com.fpt.entity.GeneralService.of(GeneralService.java:60)
        //       at vn.com.fpt.entity.GeneralService.add(GeneralService.java:69)
        //       at vn.com.fpt.service.services.ServicesServiceImpl.addGeneralService(ServicesServiceImpl.java:246)
        //   See https://diff.blue/R013 to resolve this issue.

        GeneralService generalService = new GeneralService();
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);
        when(generalServiceRepository.save((GeneralService) any())).thenReturn(generalService);
        servicesServiceImpl.addGeneralService((GeneralServiceRequest) null, 1L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#addGeneralService(GeneralServiceRequest, Long)}
     */
    @Test
    void testAddGeneralService8() {
        when(generalServiceRepository.save((GeneralService) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class,
                () -> servicesServiceImpl.addGeneralService(new GeneralServiceRequest(), 1L));
        verify(generalServiceRepository).save((GeneralService) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#addHandOverGeneralService(HandOverGeneralServiceRequest, Long, Date, Long)}
     */
    @Test
    void testAddHandOverGeneralService() {
        HandOverGeneralServices handOverGeneralServices = new HandOverGeneralServices();
        handOverGeneralServices.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        handOverGeneralServices.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        handOverGeneralServices.setCreatedBy(1L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        handOverGeneralServices.setDateDelivery(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        handOverGeneralServices.setGeneralServiceId(123L);
        handOverGeneralServices.setHandOverGeneralServiceIndex(1);
        handOverGeneralServices.setId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        handOverGeneralServices.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        handOverGeneralServices.setModifiedBy(1L);
        when(handOverGeneralServicesRepository.save((HandOverGeneralServices) any())).thenReturn(handOverGeneralServices);
        HandOverGeneralServiceRequest request = new HandOverGeneralServiceRequest();
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertSame(handOverGeneralServices, servicesServiceImpl.addHandOverGeneralService(request, 123L,
                Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()), 1L));
        verify(handOverGeneralServicesRepository).save((HandOverGeneralServices) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#addHandOverGeneralService(HandOverGeneralServiceRequest, Long, Date, Long)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAddHandOverGeneralService2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "vn.com.fpt.requests.HandOverGeneralServiceRequest.getHandOverGeneralServiceIndex()" because "request" is null
        //       at vn.com.fpt.service.services.ServicesServiceImpl.addHandOverGeneralService(ServicesServiceImpl.java:261)
        //   See https://diff.blue/R013 to resolve this issue.

        HandOverGeneralServices handOverGeneralServices = new HandOverGeneralServices();
        handOverGeneralServices.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        handOverGeneralServices.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        handOverGeneralServices.setCreatedBy(1L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        handOverGeneralServices.setDateDelivery(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        handOverGeneralServices.setGeneralServiceId(123L);
        handOverGeneralServices.setHandOverGeneralServiceIndex(1);
        handOverGeneralServices.setId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        handOverGeneralServices.setModifiedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        handOverGeneralServices.setModifiedBy(1L);
        when(handOverGeneralServicesRepository.save((HandOverGeneralServices) any())).thenReturn(handOverGeneralServices);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        servicesServiceImpl.addHandOverGeneralService(null, 123L,
                Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()), 1L);
    }

    /**
     * Method under test: {@link ServicesServiceImpl#addHandOverGeneralService(HandOverGeneralServiceRequest, Long, Date, Long)}
     */
    @Test
    void testAddHandOverGeneralService3() {
        when(handOverGeneralServicesRepository.save((HandOverGeneralServices) any()))
                .thenThrow(new BusinessException("Msg"));
        HandOverGeneralServiceRequest request = new HandOverGeneralServiceRequest();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        assertThrows(BusinessException.class, () -> servicesServiceImpl.addHandOverGeneralService(request, 123L,
                Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()), 1L));
        verify(handOverGeneralServicesRepository).save((HandOverGeneralServices) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#quickAddGeneralService(Long, Long)}
     */
    @Test
    void testQuickAddGeneralService() {
        ArrayList<GeneralService> generalServiceList = new ArrayList<>();
        when(generalServiceRepository.saveAll((Iterable<GeneralService>) any())).thenReturn(generalServiceList);
        when(generalServiceRepository.findAllByGroupId((Long) any())).thenReturn(new ArrayList<>());
        List<GeneralService> actualQuickAddGeneralServiceResult = servicesServiceImpl.quickAddGeneralService(123L, 1L);
        assertSame(generalServiceList, actualQuickAddGeneralServiceResult);
        assertTrue(actualQuickAddGeneralServiceResult.isEmpty());
        verify(generalServiceRepository).saveAll((Iterable<GeneralService>) any());
        verify(generalServiceRepository).findAllByGroupId((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#quickAddGeneralService(Long, Long)}
     */
    @Test
    void testQuickAddGeneralService2() {
        GeneralService generalService = new GeneralService();
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);

        ArrayList<GeneralService> generalServiceList = new ArrayList<>();
        generalServiceList.add(generalService);
        ArrayList<GeneralService> generalServiceList1 = new ArrayList<>();
        when(generalServiceRepository.saveAll((Iterable<GeneralService>) any())).thenReturn(generalServiceList1);
        when(generalServiceRepository.findAllByGroupId((Long) any())).thenReturn(generalServiceList);
        List<GeneralService> actualQuickAddGeneralServiceResult = servicesServiceImpl.quickAddGeneralService(123L, 1L);
        assertSame(generalServiceList1, actualQuickAddGeneralServiceResult);
        assertTrue(actualQuickAddGeneralServiceResult.isEmpty());
        verify(generalServiceRepository).saveAll((Iterable<GeneralService>) any());
        verify(generalServiceRepository).findAllByGroupId((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#quickAddGeneralService(Long, Long)}
     */
    @Test
    void testQuickAddGeneralService3() {
        GeneralService generalService = new GeneralService();
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);

        GeneralService generalService1 = new GeneralService();
        generalService1.setContractId(123L);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService1.setCreatedAt(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        generalService1.setCreatedBy(1L);
        generalService1.setGroupId(123L);
        generalService1.setId(123L);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService1.setModifiedAt(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        generalService1.setModifiedBy(1L);
        generalService1.setNote("Note");
        generalService1.setServiceId(123L);
        generalService1.setServicePrice(10.0d);
        generalService1.setServiceTypeId(123L);

        ArrayList<GeneralService> generalServiceList = new ArrayList<>();
        generalServiceList.add(generalService1);
        generalServiceList.add(generalService);
        ArrayList<GeneralService> generalServiceList1 = new ArrayList<>();
        when(generalServiceRepository.saveAll((Iterable<GeneralService>) any())).thenReturn(generalServiceList1);
        when(generalServiceRepository.findAllByGroupId((Long) any())).thenReturn(generalServiceList);
        List<GeneralService> actualQuickAddGeneralServiceResult = servicesServiceImpl.quickAddGeneralService(123L, 1L);
        assertSame(generalServiceList1, actualQuickAddGeneralServiceResult);
        assertTrue(actualQuickAddGeneralServiceResult.isEmpty());
        verify(generalServiceRepository).saveAll((Iterable<GeneralService>) any());
        verify(generalServiceRepository).findAllByGroupId((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#quickAddGeneralService(Long, Long)}
     */
    @Test
    void testQuickAddGeneralService4() {
        when(generalServiceRepository.saveAll((Iterable<GeneralService>) any())).thenThrow(new BusinessException("Msg"));
        when(generalServiceRepository.findAllByGroupId((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> servicesServiceImpl.quickAddGeneralService(123L, 1L));
        verify(generalServiceRepository).findAllByGroupId((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#quickAddGeneralService(Long, Long)}
     */
    @Test
    void testQuickAddGeneralService5() {
        GeneralService generalService = mock(GeneralService.class);
        when(generalService.getServiceId()).thenReturn(123L);
        doNothing().when(generalService).setCreatedAt((Date) any());
        doNothing().when(generalService).setCreatedBy((Long) any());
        doNothing().when(generalService).setId((Long) any());
        doNothing().when(generalService).setModifiedAt((Date) any());
        doNothing().when(generalService).setModifiedBy((Long) any());
        doNothing().when(generalService).setContractId((Long) any());
        doNothing().when(generalService).setGroupId((Long) any());
        doNothing().when(generalService).setNote((String) any());
        doNothing().when(generalService).setServiceId((Long) any());
        doNothing().when(generalService).setServicePrice((Double) any());
        doNothing().when(generalService).setServiceTypeId((Long) any());
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);

        ArrayList<GeneralService> generalServiceList = new ArrayList<>();
        generalServiceList.add(generalService);
        ArrayList<GeneralService> generalServiceList1 = new ArrayList<>();
        when(generalServiceRepository.saveAll((Iterable<GeneralService>) any())).thenReturn(generalServiceList1);
        when(generalServiceRepository.findAllByGroupId((Long) any())).thenReturn(generalServiceList);
        List<GeneralService> actualQuickAddGeneralServiceResult = servicesServiceImpl.quickAddGeneralService(123L, 1L);
        assertSame(generalServiceList1, actualQuickAddGeneralServiceResult);
        assertTrue(actualQuickAddGeneralServiceResult.isEmpty());
        verify(generalServiceRepository).saveAll((Iterable<GeneralService>) any());
        verify(generalServiceRepository).findAllByGroupId((Long) any());
        verify(generalService).getServiceId();
        verify(generalService).setCreatedAt((Date) any());
        verify(generalService).setCreatedBy((Long) any());
        verify(generalService).setId((Long) any());
        verify(generalService).setModifiedAt((Date) any());
        verify(generalService).setModifiedBy((Long) any());
        verify(generalService).setContractId((Long) any());
        verify(generalService).setGroupId((Long) any());
        verify(generalService).setNote((String) any());
        verify(generalService).setServiceId((Long) any());
        verify(generalService).setServicePrice((Double) any());
        verify(generalService).setServiceTypeId((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#quickAddGeneralService(Long, Long)}
     */
    @Test
    void testQuickAddGeneralService6() {
        GeneralService generalService = mock(GeneralService.class);
        when(generalService.getServiceId()).thenReturn(1L);
        doNothing().when(generalService).setCreatedAt((Date) any());
        doNothing().when(generalService).setCreatedBy((Long) any());
        doNothing().when(generalService).setId((Long) any());
        doNothing().when(generalService).setModifiedAt((Date) any());
        doNothing().when(generalService).setModifiedBy((Long) any());
        doNothing().when(generalService).setContractId((Long) any());
        doNothing().when(generalService).setGroupId((Long) any());
        doNothing().when(generalService).setNote((String) any());
        doNothing().when(generalService).setServiceId((Long) any());
        doNothing().when(generalService).setServicePrice((Double) any());
        doNothing().when(generalService).setServiceTypeId((Long) any());
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);

        ArrayList<GeneralService> generalServiceList = new ArrayList<>();
        generalServiceList.add(generalService);
        ArrayList<GeneralService> generalServiceList1 = new ArrayList<>();
        when(generalServiceRepository.saveAll((Iterable<GeneralService>) any())).thenReturn(generalServiceList1);
        when(generalServiceRepository.findAllByGroupId((Long) any())).thenReturn(generalServiceList);
        List<GeneralService> actualQuickAddGeneralServiceResult = servicesServiceImpl.quickAddGeneralService(123L, 1L);
        assertSame(generalServiceList1, actualQuickAddGeneralServiceResult);
        assertTrue(actualQuickAddGeneralServiceResult.isEmpty());
        verify(generalServiceRepository).saveAll((Iterable<GeneralService>) any());
        verify(generalServiceRepository).findAllByGroupId((Long) any());
        verify(generalService).getServiceId();
        verify(generalService).setCreatedAt((Date) any());
        verify(generalService).setCreatedBy((Long) any());
        verify(generalService).setId((Long) any());
        verify(generalService).setModifiedAt((Date) any());
        verify(generalService).setModifiedBy((Long) any());
        verify(generalService).setContractId((Long) any());
        verify(generalService).setGroupId((Long) any());
        verify(generalService).setNote((String) any());
        verify(generalService).setServiceId((Long) any());
        verify(generalService).setServicePrice((Double) any());
        verify(generalService).setServiceTypeId((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#removeGeneralService(Long)}
     */
    @Test
    void testRemoveGeneralService() {
        GeneralService generalService = new GeneralService();
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);
        Optional<GeneralService> ofResult = Optional.of(generalService);
        doNothing().when(generalServiceRepository).delete((GeneralService) any());
        when(generalServiceRepository.findById((Long) any())).thenReturn(ofResult);
        assertEquals("Xóa dịch vụ chung thành công. general_service_id : 123",
                servicesServiceImpl.removeGeneralService(123L));
        verify(generalServiceRepository).findById((Long) any());
        verify(generalServiceRepository).delete((GeneralService) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#removeGeneralService(Long)}
     */
    @Test
    void testRemoveGeneralService2() {
        GeneralService generalService = new GeneralService();
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);
        Optional<GeneralService> ofResult = Optional.of(generalService);
        doThrow(new BusinessException("Msg")).when(generalServiceRepository).delete((GeneralService) any());
        when(generalServiceRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(BusinessException.class, () -> servicesServiceImpl.removeGeneralService(123L));
        verify(generalServiceRepository).findById((Long) any());
        verify(generalServiceRepository).delete((GeneralService) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#removeGeneralService(Long)}
     */
    @Test
    void testRemoveGeneralService3() {
        doNothing().when(generalServiceRepository).delete((GeneralService) any());
        when(generalServiceRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> servicesServiceImpl.removeGeneralService(123L));
        verify(generalServiceRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#removeGroupGeneralService(Long)}
     */
    @Test
    void testRemoveGroupGeneralService() {
        when(generalServiceRepository.findAllByGroupId((Long) any())).thenReturn(new ArrayList<>());
        doNothing().when(generalServiceRepository).deleteAll((Iterable<GeneralService>) any());
        assertEquals("Xóa dịch vụ chung thành công của tòa. group_id : 123",
                servicesServiceImpl.removeGroupGeneralService(123L));
        verify(generalServiceRepository).findAllByGroupId((Long) any());
        verify(generalServiceRepository).deleteAll((Iterable<GeneralService>) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#removeGroupGeneralService(Long)}
     */
    @Test
    void testRemoveGroupGeneralService2() {
        when(generalServiceRepository.findAllByGroupId((Long) any())).thenThrow(new BusinessException("Msg"));
        doThrow(new BusinessException("Msg")).when(generalServiceRepository).deleteAll((Iterable<GeneralService>) any());
        assertThrows(BusinessException.class, () -> servicesServiceImpl.removeGroupGeneralService(123L));
        verify(generalServiceRepository).findAllByGroupId((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#findGeneralServiceById(Long)}
     */
    @Test
    void testFindGeneralServiceById() {
        GeneralService generalService = new GeneralService();
        generalService.setContractId(123L);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setCreatedAt(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setCreatedBy(1L);
        generalService.setGroupId(123L);
        generalService.setId(123L);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        generalService.setModifiedAt(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        generalService.setModifiedBy(1L);
        generalService.setNote("Note");
        generalService.setServiceId(123L);
        generalService.setServicePrice(10.0d);
        generalService.setServiceTypeId(123L);
        Optional<GeneralService> ofResult = Optional.of(generalService);
        when(generalServiceRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(generalService, servicesServiceImpl.findGeneralServiceById(123L));
        verify(generalServiceRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#findGeneralServiceById(Long)}
     */
    @Test
    void testFindGeneralServiceById2() {
        when(generalServiceRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(BusinessException.class, () -> servicesServiceImpl.findGeneralServiceById(123L));
        verify(generalServiceRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link ServicesServiceImpl#findGeneralServiceById(Long)}
     */
    @Test
    void testFindGeneralServiceById3() {
        when(generalServiceRepository.findById((Long) any())).thenThrow(new BusinessException("Msg"));
        assertThrows(BusinessException.class, () -> servicesServiceImpl.findGeneralServiceById(123L));
        verify(generalServiceRepository).findById((Long) any());
    }
}

