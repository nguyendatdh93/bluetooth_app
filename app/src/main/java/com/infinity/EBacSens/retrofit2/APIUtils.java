package com.infinity.EBacSens.retrofit2;

public class APIUtils {
    public static final String baseUrl = "http://45.122.221.73:9000/";
    public static final String token = "l2S6FmuqpyOfKHjSF6H3r9BOwonAWbS4grG9QARoGnTsEZGbUl";

    public static DataClient getData(){
        return RetrofitClient.getClient(baseUrl).create(DataClient.class);
    }
}
