package com.isp.smarttrackapp;

public final class Config {
    public static final String BASE_API_URL = "http://10.0.2.2:5001/api/";

    //Shared preferences Keys
    public static final String KEY_USER_TOKEN = "token";
    public static final String KEY_FCM_TOKEN = "fcm_token";
    public static final String KEY_ACTUAL_TRAJECT_ID = "actual_traject_id";
    public static final String KEY_ACTUAL_INCIDENT_ID = "actual_incident_id";
    public static final String KEY_USER_USERNAME = "username";
    public static final String KEY_USER_REALNAME = "realname";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_LAST_LONGITUDE = "last_longitude";
    public static final String KEY_LAST_LATITUDE = "last_latitude";

    public static final String KEY_AUTH_PASSWORD = "authpassword";
    public static final String KEY_AUTH_FINGERPRINT = "authfingerprint";
    public static final String KEY_AUTH_USERNAME = "authusername";

    public static final int UPDATE_LOCATION_TIME = 3000;
    //Common messages
    public static final String UNEXPECTED_ERROR_MSG = "Ha ocurrido un error inesperado.";
}