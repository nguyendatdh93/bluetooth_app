package com.infinity.EBacSens.retrofit2;

import com.infinity.EBacSens.model_objects.DataSensorAPI;
import com.infinity.EBacSens.model_objects.DataSensorSettingAPI;
import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.model_objects.SensorMeasureDetail;
import com.infinity.EBacSens.model_objects.SensorMeasurePage;
import com.infinity.EBacSens.model_objects.SensorSetting;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataClient {

    @GET("api/sensor")
    Call<DataSensorAPI> getAllSensorPaired(@Header("token") String token);

    @GET("api/setting")
    Call<DataSensorSettingAPI> getSettingSensor(@Header("token") String token);

    @GET("api/sensor/{idSensor}/measure")
    Call<SensorMeasurePage> getMeasurePage(@Header("token") String token , @Path("idSensor") int idSensor, @Query("page") int page, @Query("ful") int ful);

    @GET("api/sensor/measure/{idMeasure}")
    Call<SensorMeasureDetail> getDetailMeasure(@Header("token") String token , @Path("idMeasure") int idMeasure);

    @DELETE("api/sensor/setting/{idSensor}")
    Call<String> deleteSettingSensor(@Header("token") String token , @Path("idSensor") int idSensor);

    @POST("api/sensor/store")
    @FormUrlEncoded
    Call<SensorInfor> storeSensor(
            @Header("token") String token,
            @Field("name") String name,
            @Field("datetime") String datetime,
            @Field("mac_device") String mac_device
    );

    @POST("api/setting")
    @FormUrlEncoded
    Call<ErrorSensorSetting> saveSettingSensor(
            @Header("token") String token,
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
            @Field("bac[0][pkp]") int bac0pkp
    );

    @POST("api/setting")
    @FormUrlEncoded
    Call<ErrorSensorSetting> saveSettingSensor(
            @Header("token") String token,
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

    @POST("api/setting")
    @FormUrlEncoded
    Call<ErrorSensorSetting> saveSettingSensor(
            @Header("token") String token,
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
            @Field("bac[1][pkp]") int bac1pkp,
            @Field("bac[2][bacname]") String bac2bacname,
            @Field("bac[2][e1]") int bac2e1,
            @Field("bac[2][e2]") int bac2e2,
            @Field("bac[2][e3]") int bac2e3,
            @Field("bac[2][e4]") int bac2e4,
            @Field("bac[2][pkp]") int bac2pkp
    );

    @POST("api/setting")
    @FormUrlEncoded
    Call<ErrorSensorSetting> saveSettingSensor(
            @Header("token") String token,
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
            @Field("bac[1][pkp]") int bac1pkp,
            @Field("bac[2][bacname]") String bac2bacname,
            @Field("bac[2][e1]") int bac2e1,
            @Field("bac[2][e2]") int bac2e2,
            @Field("bac[2][e3]") int bac2e3,
            @Field("bac[2][e4]") int bac2e4,
            @Field("bac[2][pkp]") int bac2pkp,
            @Field("bac[3][bacname]") String bac3bacname,
            @Field("bac[3][e1]") int bac3e1,
            @Field("bac[3][e2]") int bac3e2,
            @Field("bac[3][e3]") int bac3e3,
            @Field("bac[3][e4]") int bac3e4,
            @Field("bac[3][pkp]") int bac3pkp
    );


    @POST("api/sensor/{idSensor}/setting")
    @FormUrlEncoded
    Call<ErrorSensorSetting> saveSettingSensor(
            @Header("token") String token,
            @Field("setname") String setname);

    @POST("api/setting")
    @FormUrlEncoded
    Call<ErrorSensorSetting> saveSettingSensor(
            @Header("token") String token,
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
            @Field("bac[1][pkp]") int bac1pkp,
            @Field("bac[2][bacname]") String bac2bacname,
            @Field("bac[2][e1]") int bac2e1,
            @Field("bac[2][e2]") int bac2e2,
            @Field("bac[2][e3]") int bac2e3,
            @Field("bac[2][e4]") int bac2e4,
            @Field("bac[2][pkp]") int bac2pkp,
            @Field("bac[3][bacname]") String bac3bacname,
            @Field("bac[3][e1]") int bac3e1,
            @Field("bac[3][e2]") int bac3e2,
            @Field("bac[3][e3]") int bac3e3,
            @Field("bac[3][e4]") int bac3e4,
            @Field("bac[3][pkp]") int bac3pkp,
            @Field("bac[4][bacname]") String bac4bacname,
            @Field("bac[4][e1]") int bac4e1,
            @Field("bac[4][e2]") int bac4e2,
            @Field("bac[4][e3]") int bac4e3,
            @Field("bac[4][e4]") int bac4e4,
            @Field("bac[4][pkp]") int bac4pkp
    );
}
