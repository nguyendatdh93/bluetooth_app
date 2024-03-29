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
import com.infinity.EBacSens.model_objects.TimeZone;

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

    @GET("api/time-cloud")
    Call<TimeZone> getTimeZone(@Header("token") String token);

    @DELETE("api/setting/{idSensor}")
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
            @Field("measpara[bac]") String bac0bacname,

            @Field("measdet") String measdet
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
            @Field("measpara[bac]") String bac0bacname,

            @Field("measba[datetime]") String measba_datetime_,
            @Field("measba[pstaterr]") int measba_pastaerr_,
            @Field("measba[num]") int measba_num_,

            @Field("measres[0][name]") String measres0name,
            @Field("measres[0][pkpot]") String measres0pkpot,
            @Field("measres[0][dltc]") String measres0dltc,
            @Field("measres[0][bgc]") String measres0bgc,
            @Field("measres[0][err]") String measres0err,
            @Field("measres[0][blpsx]") String measres0blpsx,
            @Field("measres[0][blpsy]") String measres0blpsy,
            @Field("measres[0][blpex]") String measres0blpex,
            @Field("measres[0][blpey]") String measres0blpey,

            @Field("measdet") String measdet
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
            @Field("measpara[bac]") String bac0bacname,

            @Field("measba[datetime]") String measba_datetime_,
            @Field("measba[pstaterr]") int measba_pastaerr_,
            @Field("measba[num]") int measba_num_,

            @Field("measres[0][name]") String measres0name,
            @Field("measres[0][pkpot]") String measres0pkpot,
            @Field("measres[0][dltc]") String measres0dltc,
            @Field("measres[0][bgc]") String measres0bgc,
            @Field("measres[0][err]") String measres0err,
            @Field("measres[0][blpsx]") String measres0blpsx,
            @Field("measres[0][blpsy]") String measres0blpsy,
            @Field("measres[0][blpex]") String measres0blpex,
            @Field("measres[0][blpey]") String measres0blpey,
            @Field("measres[1][name]") String measres1name,
            @Field("measres[1][pkpot]") String measres1pkpot,
            @Field("measres[1][dltc]") String measres1dltc,
            @Field("measres[1][bgc]") String measres1bgc,
            @Field("measres[1][err]") String measres1err,
            @Field("measres[1][blpsx]") String measres1blpsx,
            @Field("measres[1][blpsy]") String measres1blpsy,
            @Field("measres[1][blpex]") String measres1blpex,
            @Field("measres[1][blpey]") String measres1blpey,

            @Field("measdet") String measdet
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
            @Field("measpara[bac]") String bac0bacname,

            @Field("measba[datetime]") String measba_datetime_,
            @Field("measba[pstaterr]") int measba_pastaerr_,
            @Field("measba[num]") int measba_num_,

            @Field("measres[0][name]") String measres0name,
            @Field("measres[0][pkpot]") String measres0pkpot,
            @Field("measres[0][dltc]") String measres0dltc,
            @Field("measres[0][bgc]") String measres0bgc,
            @Field("measres[0][err]") String measres0err,
            @Field("measres[0][blpsx]") String measres0blpsx,
            @Field("measres[0][blpsy]") String measres0blpsy,
            @Field("measres[0][blpex]") String measres0blpex,
            @Field("measres[0][blpey]") String measres0blpey,
            @Field("measres[1][name]") String measres1name,
            @Field("measres[1][pkpot]") String measres1pkpot,
            @Field("measres[1][dltc]") String measres1dltc,
            @Field("measres[1][bgc]") String measres1bgc,
            @Field("measres[1][err]") String measres1err,
            @Field("measres[1][blpsx]") String measres1blpsx,
            @Field("measres[1][blpsy]") String measres1blpsy,
            @Field("measres[1][blpex]") String measres1blpex,
            @Field("measres[1][blpey]") String measres1blpey,
            @Field("measres[2][name]") String measres2name,
            @Field("measres[2][pkpot]") String measres2pkpot,
            @Field("measres[2][dltc]") String measres2dltc,
            @Field("measres[2][bgc]") String measres2bgc,
            @Field("measres[2][err]") String measres2err,
            @Field("measres[2][blpsx]") String measres2blpsx,
            @Field("measres[2][blpsy]") String measres2blpsy,
            @Field("measres[2][blpex]") String measres2blpex,
            @Field("measres[2][blpey]") String measres2blpey,

            @Field("measdet") String measdet
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
            @Field("measpara[bac]") String bac0bacname,

            @Field("measba[datetime]") String measba_datetime_,
            @Field("measba[pstaterr]") int measba_pastaerr_,
            @Field("measba[num]") int measba_num_,

            @Field("measres[0][name]") String measres0name,
            @Field("measres[0][pkpot]") String measres0pkpot,
            @Field("measres[0][dltc]") String measres0dltc,
            @Field("measres[0][bgc]") String measres0bgc,
            @Field("measres[0][err]") String measres0err,
            @Field("measres[0][blpsx]") String measres0blpsx,
            @Field("measres[0][blpsy]") String measres0blpsy,
            @Field("measres[0][blpex]") String measres0blpex,
            @Field("measres[0][blpey]") String measres0blpey,
            @Field("measres[1][name]") String measres1name,
            @Field("measres[1][pkpot]") String measres1pkpot,
            @Field("measres[1][dltc]") String measres1dltc,
            @Field("measres[1][bgc]") String measres1bgc,
            @Field("measres[1][err]") String measres1err,
            @Field("measres[1][blpsx]") String measres1blpsx,
            @Field("measres[1][blpsy]") String measres1blpsy,
            @Field("measres[1][blpex]") String measres1blpex,
            @Field("measres[1][blpey]") String measres1blpey,
            @Field("measres[2][name]") String measres2name,
            @Field("measres[2][pkpot]") String measres2pkpot,
            @Field("measres[2][dltc]") String measres2dltc,
            @Field("measres[2][bgc]") String measres2bgc,
            @Field("measres[2][err]") String measres2err,
            @Field("measres[2][blpsx]") String measres2blpsx,
            @Field("measres[2][blpsy]") String measres2blpsy,
            @Field("measres[2][blpex]") String measres2blpex,
            @Field("measres[2][blpey]") String measres2blpey,
            @Field("measres[3][name]") String measres3name,
            @Field("measres[3][pkpot]") String measres3pkpot,
            @Field("measres[3][dltc]") String measres3dltc,
            @Field("measres[3][bgc]") String measres3bgc,
            @Field("measres[3][err]") String measres3err,
            @Field("measres[3][blpsx]") String measres3blpsx,
            @Field("measres[3][blpsy]") String measres3blpsy,
            @Field("measres[3][blpex]") String measres3blpex,
            @Field("measres[3][blpey]") String measres3blpey,

            @Field("measdet") String measdet
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
            @Field("measpara[bac]") String bac0bacname,

            @Field("measba[datetime]") String measba_datetime_,
            @Field("measba[pstaterr]") int measba_pastaerr_,
            @Field("measba[num]") int measba_num_,

            @Field("measres[0][name]") String measres0name,
            @Field("measres[0][pkpot]") String measres0pkpot,
            @Field("measres[0][dltc]") String measres0dltc,
            @Field("measres[0][bgc]") String measres0bgc,
            @Field("measres[0][err]") String measres0err,
            @Field("measres[0][blpsx]") String measres0blpsx,
            @Field("measres[0][blpsy]") String measres0blpsy,
            @Field("measres[0][blpex]") String measres0blpex,
            @Field("measres[0][blpey]") String measres0blpey,
            @Field("measres[1][name]") String measres1name,
            @Field("measres[1][pkpot]") String measres1pkpot,
            @Field("measres[1][dltc]") String measres1dltc,
            @Field("measres[1][bgc]") String measres1bgc,
            @Field("measres[1][err]") String measres1err,
            @Field("measres[1][blpsx]") String measres1blpsx,
            @Field("measres[1][blpsy]") String measres1blpsy,
            @Field("measres[1][blpex]") String measres1blpex,
            @Field("measres[1][blpey]") String measres1blpey,
            @Field("measres[2][name]") String measres2name,
            @Field("measres[2][pkpot]") String measres2pkpot,
            @Field("measres[2][dltc]") String measres2dltc,
            @Field("measres[2][bgc]") String measres2bgc,
            @Field("measres[2][err]") String measres2err,
            @Field("measres[2][blpsx]") String measres2blpsx,
            @Field("measres[2][blpsy]") String measres2blpsy,
            @Field("measres[2][blpex]") String measres2blpex,
            @Field("measres[2][blpey]") String measres2blpey,
            @Field("measres[3][name]") String measres3name,
            @Field("measres[3][pkpot]") String measres3pkpot,
            @Field("measres[3][dltc]") String measres3dltc,
            @Field("measres[3][bgc]") String measres3bgc,
            @Field("measres[3][err]") String measres3err,
            @Field("measres[3][blpsx]") String measres3blpsx,
            @Field("measres[3][blpsy]") String measres3blpsy,
            @Field("measres[3][blpex]") String measres3blpex,
            @Field("measres[3][blpey]") String measres3blpey,
            @Field("measres[4][name]") String measres4name,
            @Field("measres[4][pkpot]") String measres4pkpot,
            @Field("measres[4][dltc]") String measres4dltc,
            @Field("measres[4][bgc]") String measres4bgc,
            @Field("measres[4][err]") String measres4err,
            @Field("measres[4][blpsx]") String measres4blpsx,
            @Field("measres[4][blpsy]") String measres4blpsy,
            @Field("measres[4][blpex]") String measres4blpex,
            @Field("measres[4][blpey]") String measres4blpey,

            @Field("measdet") String measdet
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
