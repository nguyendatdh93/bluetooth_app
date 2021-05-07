package com.infinity.EBacSens.retrofit2;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {

    public static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl){

        OkHttpClient builder = new OkHttpClient.Builder()
                .readTimeout(60 , TimeUnit.SECONDS)
                .writeTimeout(60 , TimeUnit.SECONDS)
                .connectTimeout(60 , TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();



        //Gson gson = new GsonBuilder().setLenient().create();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(builder)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;
    }


}
