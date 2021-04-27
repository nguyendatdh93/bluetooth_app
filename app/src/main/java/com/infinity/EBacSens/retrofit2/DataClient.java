package com.infinity.EBacSens.retrofit2;

import com.infinity.EBacSens.model_objects.DataSensorAPI;
import com.infinity.EBacSens.model_objects.DataSensorSettingAPI;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.model_objects.SensorSetting;

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

    @GET("api/sensor")
    Call<DataSensorAPI> getAllSensorPaired(@Header("token") String token);

    @POST("api/sensor/{idSensor}/setting")
    @FormUrlEncoded
    Call<DataSensorSettingAPI> updateSettingSensor(
            @Header("token") String token,
            @Path("idSensor") int idSensor,
            @Field("setname") String setname,
            @Field("bacs") int bacs,
            @Field("crng") int crng,
            @Field("eqp1") int eqp1,
            @Field("eqt1") int eqt1,
            @Field("eqp2") int eqp2,
            @Field("eqt2") int eqt2,
            @Field("eqp3") int eqp3,
            @Field("eqt3") int eqt3,
            @Field("eqp4") int eqp4,
            @Field("eqt4") int eqt4,
            @Field("eqp5") int eqp5,
            @Field("eqt5") int eqt5,
            @Field("stp") int stp,
            @Field("enp") int enp,
            @Field("pp") int pp,
            @Field("dlte") int dlte,
            @Field("pwd") int pwd,
            @Field("ptm") int ptm,
            @Field("ibst") int ibst,
            @Field("iben") int iben,
            @Field("ifst") int ifst,
            @Field("ifen") int ifen,
            @Field("bac[0][bacname]") String bac0bacname,
            @Field("bac[0][e1]") int bac0e1,
            @Field("bac[0][e2]") int bac0e2,
            @Field("bac[0][e3]") int bac0e3,
            @Field("bac[0][e4]") int bac0e4,
            @Field("bac[0][pkp]") int bac0pkp,
            @Field("bac[1][bacname]") String bac1bacname,
            @Field("bac[1][e1]") int bac1e1,
            @Field("bac[1][e2]") int bac1e2,
            @Field("bac[1][e3]") int bac1e3,
            @Field("bac[1][e4]") int bac1e4,
            @Field("bac[1][pkp]") int bac1pkp
            );

    @GET("api/sensor/{idSensor}/settings")
    Call<DataSensorSettingAPI> getSettingSensor(@Header("token") String token , @Path("idSensor") int idSensor);

    @DELETE("api/sensor/setting/{idSensor}")
    Call<String> deleteSettingSensor(@Header("token") String token , @Path("idSensor") long idSensor);

    @POST("api/sensor/store")
    @FormUrlEncoded
    Call<SensorInfor> storeSensor(
            @Header("token") String token,
            @Field("name") String name,
            @Field("datetime") String datetime,
            @Field("mac_device") String mac_device
    );
}
