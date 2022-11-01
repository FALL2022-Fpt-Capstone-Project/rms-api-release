package vn.com.fpt.constants;

public class ManagerConstants {

    // filter contract condition
    String ALMOST_EXPIRED_CONTRACT = "almostExpired";
    String LATEST_CONTRACT = "latest";
    String EXPIRED_CONTRACT = "expired";

    //filter staff account condition
    int DEACTIVATE_ACCOUNT = 0;
    int ACTIVATE_ACCOUNT = 1;
    int NONE_FILTER_DEACTIVATE = -1;


    // permission
    int PERMISSION_MATERIAL = 1;
    int PERMISSION_MONEY = 2;
    int PERMISSION_RECEIPT = 3;
    int PERMISSION_CONTRACT = 4;

    int[] PERMISSION_ALL = new int[]{PERMISSION_MATERIAL, PERMISSION_MONEY, PERMISSION_RECEIPT, PERMISSION_CONTRACT};

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

}
