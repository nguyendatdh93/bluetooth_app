package com.infinity.EBacSens.retrofit2;

import com.infinity.EBacSens.model_objects.DataSensorAPI;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface DataClient {

    @GET("api/sensor/1/settings")
    Call<DataSensorAPI> getAllSensorPaired(@Header("token") String token);

    @DELETE("api/sensor/setting/{idSensor}")
    Call<String> deleteSettingSensor(@Header("token") String token , @Path("idSensor") long idSensor);
}
