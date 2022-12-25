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


}

