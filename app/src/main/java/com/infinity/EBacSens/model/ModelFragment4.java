package com.infinity.EBacSens.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.DataSensorSettingAPI;
import com.infinity.EBacSens.model_objects.MeasureMeasbas;
import com.infinity.EBacSens.model_objects.MeasureMeasdets;
import com.infinity.EBacSens.model_objects.MeasureMeasress;
import com.infinity.EBacSens.model_objects.SensorMeasure;
import com.infinity.EBacSens.model_objects.SensorMeasureDetail;
import com.infinity.EBacSens.model_objects.SensorMeasurePage;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.retrofit2.DataClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelFragment4 {

    private ModelFragmeant4Listener modelFragmeant4Listener;

    public ModelFragment4(ModelFragmeant4Listener modelFragmeant4Listener) {
        this.modelFragmeant4Listener = modelFragmeant4Listener;
    }

    public void handleGetMeasurePage(String token, int idSensor, int page, int ful) {
        DataClient dataClient = APIUtils.getData();
        final Call<SensorMeasurePage> callback = dataClient.getMeasurePage(token, idSensor, page, ful);
        callback.enqueue(new Callback<SensorMeasurePage>() {
            @Override
            public void onResponse(@NonNull Call<SensorMeasurePage> call, @NonNull Response<SensorMeasurePage> response) {
                modelFragmeant4Listener.onGetDataMeasurePage(response.body() != null ? response.body().getMeasurePages() : null);
            }

            @Override
            public void onFailure(@NonNull Call<SensorMeasurePage> call, @NonNull Throwable t) {
                modelFragmeant4Listener.onGetDataMeasurePage(null);
                Protector.appendLog(t.getMessage());
            }
        });
    }

    public void handleGetDetailMeasure(String token, int idMeasure) {
        DataClient dataClient = APIUtils.getData();
        final Call<SensorMeasureDetail> callback = dataClient.getDetailMeasure(token, idMeasure);
        callback.enqueue(new Callback<SensorMeasureDetail>() {
            @Override
            public void onResponse(@NonNull Call<SensorMeasureDetail> call, @NonNull Response<SensorMeasureDetail> response) {
                modelFragmeant4Listener.onGetDataMeasureDetail(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SensorMeasureDetail> call, @NonNull Throwable t) {
                modelFragmeant4Listener.onGetDataMeasureDetail(null);
                Protector.appendLog(t.getMessage());
            }
        });
    }

    public void handleStoreMeasure(String token, int idMeasure, String datetime, String no, SensorSetting sensorSetting, MeasureMeasbas measureMeasbas, ArrayList<MeasureMeasress> measureMeasresses , ArrayList<MeasureMeasdets> measureMeasdets) {
        DataClient dataClient = APIUtils.getData();

        final Call<SensorMeasure> callback = dataClient.storeMeasure(token,
                idMeasure,
                datetime,
                no,
                sensorSetting.getSetname(),
                sensorSetting.getBacs(),
                sensorSetting.getCrng(),
                sensorSetting.getEqp1(),
                sensorSetting.getEqt1(),
                sensorSetting.getEqp2(),
                sensorSetting.getEqt2(),
                sensorSetting.getEqp3(),
                sensorSetting.getEqt3(),
                sensorSetting.getEqp4(),
                sensorSetting.getEqt4(),
                sensorSetting.getEqp5(),
                sensorSetting.getEqt5(),
                sensorSetting.getStp(),
                sensorSetting.getEnp(),
                sensorSetting.getPp(),
                sensorSetting.getDlte(),
                sensorSetting.getPwd(),
                sensorSetting.getPtm(),
                sensorSetting.getIbst(),
                sensorSetting.getIben(),
                sensorSetting.getIfst(),
                sensorSetting.getIfen(),
                sensorSetting.getBacSettings().get(0).getBacName(),
                sensorSetting.getBacSettings().get(0).getE1(),
                sensorSetting.getBacSettings().get(0).getE2(),
                sensorSetting.getBacSettings().get(0).getE3(),
                sensorSetting.getBacSettings().get(0).getE4(),
                sensorSetting.getBacSettings().get(0).getId(),

                measureMeasbas.getDatetime(),
                measureMeasbas.getPstaterr(),
                measureMeasbas.getNum(),

                measureMeasresses.get(0).getName(),
                measureMeasresses.get(0).getPkpot(),
                measureMeasresses.get(0).getDltc(),
                measureMeasresses.get(0).getBgc(),
                measureMeasresses.get(0).getErr(),
                measureMeasresses.get(0).getBlpsx(),
                measureMeasresses.get(0).getBlpsy(),
                measureMeasresses.get(0).getBlpex(),
                measureMeasresses.get(0).getBlpey(),

                measureMeasdets.get(0).getNo(),
                measureMeasdets.get(0).getDeltae(),
                measureMeasdets.get(0).getDeltai(),
                measureMeasdets.get(0).getEb(),
                measureMeasdets.get(0).getIb(),
                measureMeasdets.get(0).getEf(),
                measureMeasdets.get(0).get_if()
                );
        callback.enqueue(new Callback<SensorMeasure>() {
            @Override
            public void onResponse(@NonNull Call<SensorMeasure> call, @NonNull Response<SensorMeasure> response) {
                if (response.isSuccessful()){
                    modelFragmeant4Listener.onSuccessStoreMeasure(response.body());
                }else {
                    modelFragmeant4Listener.onFailStoreMeasure(String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<SensorMeasure> call, @NonNull Throwable t) {
                modelFragmeant4Listener.onFailStoreMeasure(t.getMessage());
                Protector.appendLog(t.getMessage());
            }
        });
    }
}
