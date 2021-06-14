package com.infinity.EBacSens.retrofit2;

public class APIUtils {
    public static final String baseUrl = "http://18.179.106.209/";
    public static final String baseUrlTimezone = "https://script.googleusercontent.com/";
    public static final String token = "l2S6FmuqpyOfKHjSF6H3r9BOwonAWbS4grG9QARoGnTsEZGbUl";
    //phone
    //public static final String PBAP_UUID = "0000112f-0000-1000-8000-00805f9b34fb";
    // sensor
    public static final String PBAP_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    public static DataClient getData(){
        return RetrofitClient.getClient(baseUrl).create(DataClient.class);
    }

    public static DataClient getDataTimezone(){
        return RetrofitClient.getClient(baseUrlTimezone).create(DataClient.class);
    }
}
