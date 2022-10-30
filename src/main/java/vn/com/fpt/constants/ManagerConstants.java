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
    Long TYPE_BATH_ROOM = 1L;
    Long TYPE_LIVING_ROOM = 2L;
    Long TYPE_KITCHEN = 3L;
    Long TYPE_BED_ROOM = 4L;
    Long TYPE_ANOTHER = 5L;
    Long TYPE_OFFICE = 6L;

    //basic service
    Long SERVICE_ELECTRIC = 1L;
    Long SERVICE_WATER = 2L;
    Long SERVICE_VEHICLES = 3L;
    Long SERVICE_INTERNET = 4L;
    Long SERVICE_CLEANING = 5L;
    Long SERVICE_OTHER = 6L;

    //service types
    Long SERVICE_TYPE_METER = 1L;
    Long SERVICE_TYPE_MONTH = 2L;
    Long SERVICE_TYPE_PERSON = 3L;

    //service default prices
    Double ELECTRIC_DEFAULT_PRICE = 3500.0;
    Double WATER_DEFAULT_PRICE = 30000.0;
    Double INTERNET_DEFAULT_PRICE = 100000.0;
    Double VEHICLES_DEFAULT_PRICE = 50000.0;

}
