package com.example.musabir.apds.Defaults;

/**
 * Created by Musabir on 4/10/2018.
 */

public class URLHeaders {

    public static final String URL_HEADER = "http://139.59.151.116:9090/apds";
    public static final String URL_CHECK_SENSOR_BY_ID = URL_HEADER + "/sensor/checkSensor";
    public static final String URL_REGISTER = URL_HEADER + "/user/register";
    public static final String URL_REGISTER_SEND_MSISDN = URL_HEADER + "/user/register/sendMsisdn";
    public static final String URL_LOGIN_SEND_MSISDN = URL_HEADER + "/user/login/sendMsisdn";
    public static final String URL_REGISTER_VERIFY_SMS = URL_HEADER + "/user/register/verify/sms";
    public static final String URL_LOGIN_VERIFY_SMS = URL_HEADER + "/user/login/verify/sms";
    public static final String URL_ME_GENERAL = URL_HEADER + "/user/me";
    public static final String URL_ME_CURRENT_SENSOR = URL_HEADER + "/user/me/currentSensor";
    public static final String URL_CHANGE_STATUS = URL_HEADER + "/user/me/changeStatus";
    public static final String URL_GET_LAST_DATA= URL_HEADER + "/user/getLastData";
    public static final String URL_GET_LOGS= URL_HEADER + "/user/getLogs";
    public static final String URL_FILTER= URL_HEADER + "/user/getFilter";
    public static final String URL_UPDATE_NOTIFY= URL_HEADER + "/user/update/notify";


}
