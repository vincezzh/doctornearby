package com.akhaltech.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vince on 2015-09-15.
 */
public class GlobalConstant {

    public static final String TYPE_DEV = "DEV";
    public static final String TYPE_PROD = "PROD";
    public static final int NOTIFICATION_PERIOD_MINUTES = 10;

    public static final Map<String, String> PROVINCE_NAME_MAP = new HashMap<String, String>() {
        {
            put("Alberta", "AB");
            put("British Columbia", "BC");
            put("Manitoba", "MB");
            put("New Brunswick", "NB");
            put("Newfoundland and Labrador", "NL");
            put("Nova Scotia", "NS");
            put("Northwest Territories", "NT");
            put("Nunavut", "NU");
            put("Ontario", "ON");
            put("Prince Edward Island", "PE");
            put("Quebec", "QC");
            put("Saskatchewan", "SK");
            put("Yukon", "YT");
        }
    };

    public static final String APP_SERVER_URL = "http://localhost:9091";
    public static final String DB_SERVER_URL = "localhost";
    public static final int DB_PORT = 27017;
    public static final String DB_NAME = "doctornearby";

    public static final String DEFAULT_ID_KEY = "_id";
    public static final String COLLECTION_DOCTOR = "doctor";
    public static final String COLLECTION_USER_BOOKMARK = "user_bookmark";
    public static final String COLLECTION_USER_MEDICINE = "user_medicine";
    public static final int DEFAULT_PAGE_SIZE = 25;
}
