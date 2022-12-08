package vn.com.fpt.common.constants;

import java.util.Date;
import java.util.Objects;

public abstract class ManagerConstants {

    // filter contract condition
    public static final int ALMOST_EXPIRED_CONTRACT = 1;
    public static final int LATEST_CONTRACT = 2;
    public static final int EXPIRED_CONTRACT = 3;

    //assets type
    public static final Long TYPE_BATH_ROOM = 1L;
    public static final Long TYPE_LIVING_ROOM = 2L;
    public static final Long TYPE_KITCHEN = 3L;
    public static final Long TYPE_BED_ROOM = 4L;
    public static final Long TYPE_ANOTHER = 5L;
    public static final Long TYPE_OFFICE = 6L;

    public static final Boolean INCREASE_ROOM_PRICE = true;
    public static final Boolean DECREASE_ROOM_PRICE = false;

    public static final String IN_MONEY = "IN";
    public static final String OUT_MONEY = "OUT";

    public static final String SERVICE_BILL = "SERVICE";
    public static final String ROOM_BILL = "ROOM";
    public static final String RECURRING_BILL = "RECURRING";
    public static final String ANOTHER_BILL = "ANOTHER";


    public static final Integer DEFAULT_ASSET_QUANTITY = 1;
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

    public static final Long NOT_RENTED_YET = null;

    public static final Boolean MEMBER = false;

    public static final Boolean REPRESENT = true;

    public static Boolean ADDITIONAL_ASSETS(Long id) {
        if (Objects.isNull(id)) return false;
        return id < 0;
    }

    public static Boolean VALIDATE_CONTRACT_TERM(Date startDate, Date endDate) {
        assert startDate != null;
        assert endDate != null;
        return endDate.compareTo(startDate) < 0;
    }
}
