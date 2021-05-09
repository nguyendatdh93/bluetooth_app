package com.infinity.EBacSens.retrofit2;

import com.infinity.EBacSens.model_objects.BacSetting;
import com.infinity.EBacSens.model_objects.DataSensorAPI;
import com.infinity.EBacSens.model_objects.DataSensorSettingAPI;
import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.model_objects.SensorMeasure;
import com.infinity.EBacSens.model_objects.SensorMeasureDetail;
import com.infinity.EBacSens.model_objects.SensorMeasurePage;
import com.infinity.EBacSens.model_objects.SensorSetting;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
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
    @POST("api/sensor/measure")
    @FormUrlEncoded
    Call<SensorMeasure> storeMeasure(
            @Header("token") String token,
            @Field("sensor_id") int sensor_id,
            @Field("datetime") String datetime,
            @Field("no") String no,

            @Field("measpara[setname]") String measpara_setname_,
            @Field("measpara[bacs]") int measpara_bacs_,
            @Field("measpara[crng]") int measpara_crng_,
            @Field("measpara[eqp1]") int measpara_eqp1_,
            @Field("measpara[eqt1]") int measpara_eqt1_,
            @Field("measpara[eqp2]") int measpara_eqp2_,
            @Field("measpara[eqt2]") int measpara_eqt2_,
            @Field("measpara[eqp3]") int measpara_eqp3_,
            @Field("measpara[eqt3]") int measpara_eqt3_,
            @Field("measpara[eqp4]") int measpara_eqp4_,
            @Field("measpara[eqt4]") int measpara_eqt4_,
            @Field("measpara[eqp5]") int measpara_eqp5_,
            @Field("measpara[eqt5]") int measpara_eqt5_,
            @Field("measpara[stp]") int measpara_stp_,
            @Field("measpara[enp]") int measpara_enp_,
            @Field("measpara[pp]") int measpara_pp_,
            @Field("measpara[dlte]") int measpara_dlte_,
            @Field("measpara[pwd]") int measpara_pwd_,
            @Field("measpara[ptm]") int measpara_ptm_,
            @Field("measpara[ibst]") int measpara_ibst_,
            @Field("measpara[iben]") int measpara_iben_,
            @Field("measpara[ifst]") int measpara_ifst_,
            @Field("measpara[ifen]") int measpara_ifen_,
            //@Field("measpara[bac][]") List<BacSetting> bac0bacname,
            @Field("measpara[bac][]") String bac0bacname,
//            @Field("measpara[bac][0]e2") int bac0e2,
//            @Field("measpara[bac][0]e3") int bac0e3,
//            @Field("measpara[bac][0]e4") int bac0e4,
//            @Field("measpara[bac][0]id") int bac0id,
//            @Field("measpara[bac][0]sensor_setting_id") int measparabac0sensor_setting_id,
//            @Field("measpara[bac][0]created_at") String measparabac0created_at,

            @Field("measba[datetime]") String measba_datetime_,
            @Field("measba[pstaterr]") int measba_pastaerr_,
            @Field("measba[num]") int measba_num_,


            @Field("measres[0][name]") String measres0name,
            @Field("measres[0][pkpot]") int measres0pkpot,
            @Field("measres[0][dltc]") int measres0dltc,
            @Field("measres[0][bgc]") int measres0bgc,
            @Field("measres[0][err]") int measres0err,
            @Field("measres[0][blpsx]") String measres0blpsx,
            @Field("measres[0][blpsy]") String measres0blpsy,
            @Field("measres[0][blpex]") String measres0blpex,
            @Field("measres[0][blpey]") String measres0blpey,


            @Field("measdet[0][no]") String measdet0no,
            @Field("measdet[0][deltae]") float measdet0deltae,
            @Field("measdet[0][deltai]") float measdet0deltai,
            @Field("measdet[0][eb]") float measdet0eb,
            @Field("measdet[0][ib]") float measdet0ib,
            @Field("measdet[0][ef]") float measdet0ef,
            @Field("measdet[0][if]") float measdet0if
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
