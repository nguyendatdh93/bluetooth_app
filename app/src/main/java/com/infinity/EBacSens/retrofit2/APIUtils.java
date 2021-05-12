package com.infinity.EBacSens.retrofit2;

import java.util.UUID;

public class APIUtils {
    public static final String baseUrl = "http://45.122.221.73:9000/";
    public static final String token = "l2S6FmuqpyOfKHjSF6H3r9BOwonAWbS4grG9QARoGnTsEZGbUl";
    //phone
    //public static final String PBAP_UUID = "0000112f-0000-1000-8000-00805f9b34fb";
    // sensor
    public static final String PBAP_UUID = "00001101-0000-1000-8000-00805F9B34FB";

    public static DataClient getData(){
        return RetrofitClient.getClient(baseUrl).create(DataClient.class);
    }
}
