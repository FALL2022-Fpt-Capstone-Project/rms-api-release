package vn.com.fpt.common.constants;

import java.util.Date;
import java.util.Objects;

public class ManagerConstants {

    // filter contract condition
    public static final String ALMOST_EXPIRED_CONTRACT = "almostExpired";
    public static final String LATEST_CONTRACT = "latest";
    public static final String EXPIRED_CONTRACT = "expired";

    //filter staff account condition
    public static final int DEACTIVATE_ACCOUNT = 0;
    public static final int ACTIVATE_ACCOUNT = 1;
    public static final int NONE_FILTER_DEACTIVATE = -1;

    //assets type
    public static final Long TYPE_BATH_ROOM = 1L;
    public static final Long TYPE_LIVING_ROOM = 2L;
    public static final Long TYPE_KITCHEN = 3L;
    public static final Long TYPE_BED_ROOM = 4L;
    public static final Long TYPE_ANOTHER = 5L;
    public static final Long TYPE_OFFICE = 6L;
    //basic service
    public static final Long SERVICE_ELECTRIC = 1L;
    public static final Long SERVICE_WATER = 2L;
    public static final Long SERVICE_VEHICLES = 3L;
    public static final Long SERVICE_INTERNET = 4L;
    public static final Long SERVICE_CLEANING = 5L;
    public static final Long SERVICE_OTHER = 6L;

    //service types
    public static final Long SERVICE_TYPE_METER = 1L;
    public static final Long SERVICE_TYPE_MONTH = 2L;
    public static final Long SERVICE_TYPE_PERSON = 3L;

    //service default prices
    public static final Double ELECTRIC_DEFAULT_PRICE = 3500.0;
    public static final Double WATER_DEFAULT_PRICE = 30000.0;
    public static final Double INTERNET_DEFAULT_PRICE = 100000.0;
    public static final Double VEHICLES_DEFAULT_PRICE = 50000.0;

    public static final Integer SUBLEASE_CONTRACT = 1;
    public static final Integer LEASE_CONTRACT = 0;

    public static final Boolean MEMBER = false;

    public static final Boolean REPRESENT = true;

    public static Boolean ADDITIONAL_ASSETS(Long id) {
        if (Objects.isNull(id)) return false;
        return id < 0;
    }

    public static Boolean VALIDATE_CONTRACT_TERM(Date startDate, Date endDate) {
        return endDate.compareTo(startDate) < 0;
    }
}
