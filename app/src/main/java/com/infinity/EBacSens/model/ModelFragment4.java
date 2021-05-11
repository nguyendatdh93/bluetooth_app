package com.infinity.EBacSens.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.BacSetting;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


        JSONArray jsonArrayBacMeasure = new JSONArray();


        for (int i = 0 ; i < sensorSetting.getBacSettings().size() ; i++){
            JSONObject object = new JSONObject();
            try {
                object.put("bacname", sensorSetting.getBacSettings().get(i).getBacName());
                object.put("e1", sensorSetting.getBacSettings().get(i).getE1());
                object.put("e2", sensorSetting.getBacSettings().get(i).getE2());
                object.put("e3", sensorSetting.getBacSettings().get(i).getE3());
                object.put("e4", sensorSetting.getBacSettings().get(i).getE4());
                object.put("id", sensorSetting.getBacSettings().get(i).getId());
                object.put("sensor_setting_id", sensorSetting.getBacSettings().get(i).getSensor_setting_id());
                object.put("created_at", sensorSetting.getBacSettings().get(i).getCreatedAt());
                jsonArrayBacMeasure.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Call<SensorMeasure> callback;

        if (measureMeasresses.size() == 1){
            callback = dataClient.storeMeasure(token,
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
                    jsonArrayBacMeasure.toString(),
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
        }else if (measureMeasresses.size() == 2){
            callback = dataClient.storeMeasure(token,
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
                    jsonArrayBacMeasure.toString(),
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
                    measureMeasresses.get(1).getName(),
                    measureMeasresses.get(1).getPkpot(),
                    measureMeasresses.get(1).getDltc(),
                    measureMeasresses.get(1).getBgc(),
                    measureMeasresses.get(1).getErr(),
                    measureMeasresses.get(1).getBlpsx(),
                    measureMeasresses.get(1).getBlpsy(),
                    measureMeasresses.get(1).getBlpex(),
                    measureMeasresses.get(1).getBlpey(),

                    measureMeasdets.get(0).getNo(),
                    measureMeasdets.get(0).getDeltae(),
                    measureMeasdets.get(0).getDeltai(),
                    measureMeasdets.get(0).getEb(),
                    measureMeasdets.get(0).getIb(),
                    measureMeasdets.get(0).getEf(),
                    measureMeasdets.get(0).get_if(),
                    measureMeasdets.get(1).getNo(),
                    measureMeasdets.get(1).getDeltae(),
                    measureMeasdets.get(1).getDeltai(),
                    measureMeasdets.get(1).getEb(),
                    measureMeasdets.get(1).getIb(),
                    measureMeasdets.get(1).getEf(),
                    measureMeasdets.get(1).get_if()
            );
        }else if (measureMeasresses.size() == 3){
            callback = dataClient.storeMeasure(token,
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
                    jsonArrayBacMeasure.toString(),
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
                    measureMeasresses.get(1).getName(),
                    measureMeasresses.get(1).getPkpot(),
                    measureMeasresses.get(1).getDltc(),
                    measureMeasresses.get(1).getBgc(),
                    measureMeasresses.get(1).getErr(),
                    measureMeasresses.get(1).getBlpsx(),
                    measureMeasresses.get(1).getBlpsy(),
                    measureMeasresses.get(1).getBlpex(),
                    measureMeasresses.get(1).getBlpey(),
                    measureMeasresses.get(2).getName(),
                    measureMeasresses.get(2).getPkpot(),
                    measureMeasresses.get(2).getDltc(),
                    measureMeasresses.get(2).getBgc(),
                    measureMeasresses.get(2).getErr(),
                    measureMeasresses.get(2).getBlpsx(),
                    measureMeasresses.get(2).getBlpsy(),
                    measureMeasresses.get(2).getBlpex(),
                    measureMeasresses.get(2).getBlpey(),

                    measureMeasdets.get(0).getNo(),
                    measureMeasdets.get(0).getDeltae(),
                    measureMeasdets.get(0).getDeltai(),
                    measureMeasdets.get(0).getEb(),
                    measureMeasdets.get(0).getIb(),
                    measureMeasdets.get(0).getEf(),
                    measureMeasdets.get(0).get_if(),
                    measureMeasdets.get(1).getNo(),
                    measureMeasdets.get(1).getDeltae(),
                    measureMeasdets.get(1).getDeltai(),
                    measureMeasdets.get(1).getEb(),
                    measureMeasdets.get(1).getIb(),
                    measureMeasdets.get(1).getEf(),
                    measureMeasdets.get(1).get_if(),
                    measureMeasdets.get(2).getNo(),
                    measureMeasdets.get(2).getDeltae(),
                    measureMeasdets.get(2).getDeltai(),
                    measureMeasdets.get(2).getEb(),
                    measureMeasdets.get(2).getIb(),
                    measureMeasdets.get(2).getEf(),
                    measureMeasdets.get(2).get_if()
            );
        }else if (measureMeasresses.size() == 4){
            callback = dataClient.storeMeasure(token,
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
                    jsonArrayBacMeasure.toString(),
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
                    measureMeasresses.get(1).getName(),
                    measureMeasresses.get(1).getPkpot(),
                    measureMeasresses.get(1).getDltc(),
                    measureMeasresses.get(1).getBgc(),
                    measureMeasresses.get(1).getErr(),
                    measureMeasresses.get(1).getBlpsx(),
                    measureMeasresses.get(1).getBlpsy(),
                    measureMeasresses.get(1).getBlpex(),
                    measureMeasresses.get(1).getBlpey(),
                    measureMeasresses.get(2).getName(),
                    measureMeasresses.get(2).getPkpot(),
                    measureMeasresses.get(2).getDltc(),
                    measureMeasresses.get(2).getBgc(),
                    measureMeasresses.get(2).getErr(),
                    measureMeasresses.get(2).getBlpsx(),
                    measureMeasresses.get(2).getBlpsy(),
                    measureMeasresses.get(2).getBlpex(),
                    measureMeasresses.get(2).getBlpey(),
                    measureMeasresses.get(3).getName(),
                    measureMeasresses.get(3).getPkpot(),
                    measureMeasresses.get(3).getDltc(),
                    measureMeasresses.get(3).getBgc(),
                    measureMeasresses.get(3).getErr(),
                    measureMeasresses.get(3).getBlpsx(),
                    measureMeasresses.get(3).getBlpsy(),
                    measureMeasresses.get(3).getBlpex(),
                    measureMeasresses.get(3).getBlpey(),

                    measureMeasdets.get(0).getNo(),
                    measureMeasdets.get(0).getDeltae(),
                    measureMeasdets.get(0).getDeltai(),
                    measureMeasdets.get(0).getEb(),
                    measureMeasdets.get(0).getIb(),
                    measureMeasdets.get(0).getEf(),
                    measureMeasdets.get(0).get_if(),
                    measureMeasdets.get(1).getNo(),
                    measureMeasdets.get(1).getDeltae(),
                    measureMeasdets.get(1).getDeltai(),
                    measureMeasdets.get(1).getEb(),
                    measureMeasdets.get(1).getIb(),
                    measureMeasdets.get(1).getEf(),
                    measureMeasdets.get(1).get_if(),
                    measureMeasdets.get(2).getNo(),
                    measureMeasdets.get(2).getDeltae(),
                    measureMeasdets.get(2).getDeltai(),
                    measureMeasdets.get(2).getEb(),
                    measureMeasdets.get(2).getIb(),
                    measureMeasdets.get(2).getEf(),
                    measureMeasdets.get(2).get_if(),
                    measureMeasdets.get(3).getNo(),
                    measureMeasdets.get(3).getDeltae(),
                    measureMeasdets.get(3).getDeltai(),
                    measureMeasdets.get(3).getEb(),
                    measureMeasdets.get(3).getIb(),
                    measureMeasdets.get(3).getEf(),
                    measureMeasdets.get(3).get_if()
            );
        }else if (measureMeasresses.size() == 5){
            callback = dataClient.storeMeasure(token,
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
                    jsonArrayBacMeasure.toString(),
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
                    measureMeasresses.get(1).getName(),
                    measureMeasresses.get(1).getPkpot(),
                    measureMeasresses.get(1).getDltc(),
                    measureMeasresses.get(1).getBgc(),
                    measureMeasresses.get(1).getErr(),
                    measureMeasresses.get(1).getBlpsx(),
                    measureMeasresses.get(1).getBlpsy(),
                    measureMeasresses.get(1).getBlpex(),
                    measureMeasresses.get(1).getBlpey(),
                    measureMeasresses.get(2).getName(),
                    measureMeasresses.get(2).getPkpot(),
                    measureMeasresses.get(2).getDltc(),
                    measureMeasresses.get(2).getBgc(),
                    measureMeasresses.get(2).getErr(),
                    measureMeasresses.get(2).getBlpsx(),
                    measureMeasresses.get(2).getBlpsy(),
                    measureMeasresses.get(2).getBlpex(),
                    measureMeasresses.get(2).getBlpey(),
                    measureMeasresses.get(3).getName(),
                    measureMeasresses.get(3).getPkpot(),
                    measureMeasresses.get(3).getDltc(),
                    measureMeasresses.get(3).getBgc(),
                    measureMeasresses.get(3).getErr(),
                    measureMeasresses.get(3).getBlpsx(),
                    measureMeasresses.get(3).getBlpsy(),
                    measureMeasresses.get(3).getBlpex(),
                    measureMeasresses.get(3).getBlpey(),
                    measureMeasresses.get(4).getName(),
                    measureMeasresses.get(4).getPkpot(),
                    measureMeasresses.get(4).getDltc(),
                    measureMeasresses.get(4).getBgc(),
                    measureMeasresses.get(4).getErr(),
                    measureMeasresses.get(4).getBlpsx(),
                    measureMeasresses.get(4).getBlpsy(),
                    measureMeasresses.get(4).getBlpex(),
                    measureMeasresses.get(4).getBlpey(),

                    measureMeasdets.get(0).getNo(),
                    measureMeasdets.get(0).getDeltae(),
                    measureMeasdets.get(0).getDeltai(),
                    measureMeasdets.get(0).getEb(),
                    measureMeasdets.get(0).getIb(),
                    measureMeasdets.get(0).getEf(),
                    measureMeasdets.get(0).get_if(),
                    measureMeasdets.get(1).getNo(),
                    measureMeasdets.get(1).getDeltae(),
                    measureMeasdets.get(1).getDeltai(),
                    measureMeasdets.get(1).getEb(),
                    measureMeasdets.get(1).getIb(),
                    measureMeasdets.get(1).getEf(),
                    measureMeasdets.get(1).get_if(),
                    measureMeasdets.get(2).getNo(),
                    measureMeasdets.get(2).getDeltae(),
                    measureMeasdets.get(2).getDeltai(),
                    measureMeasdets.get(2).getEb(),
                    measureMeasdets.get(2).getIb(),
                    measureMeasdets.get(2).getEf(),
                    measureMeasdets.get(2).get_if(),
                    measureMeasdets.get(3).getNo(),
                    measureMeasdets.get(3).getDeltae(),
                    measureMeasdets.get(3).getDeltai(),
                    measureMeasdets.get(3).getEb(),
                    measureMeasdets.get(3).getIb(),
                    measureMeasdets.get(3).getEf(),
                    measureMeasdets.get(3).get_if(),
                    measureMeasdets.get(4).getNo(),
                    measureMeasdets.get(4).getDeltae(),
                    measureMeasdets.get(4).getDeltai(),
                    measureMeasdets.get(4).getEb(),
                    measureMeasdets.get(4).getIb(),
                    measureMeasdets.get(4).getEf(),
                    measureMeasdets.get(4).get_if()
            );
        }else {
            callback = dataClient.storeMeasure(token,
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
                    jsonArrayBacMeasure.toString()
            );
        }


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
