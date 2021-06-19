package com.infinity.EBacSens.model;

import androidx.annotation.NonNull;

import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.MeasureMeasbas;
import com.infinity.EBacSens.model_objects.MeasureMeasdets;
import com.infinity.EBacSens.model_objects.MeasureMeasparas;
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
                //Protector.appendLog(true ,t.getMessage());
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
                //Protector.appendLog(true ,t.getMessage());
            }
        });
    }

    public void handleStoreMeasure(String token, int idMeasure, String datetime, String no, MeasureMeasparas measureMeasparas, MeasureMeasbas measureMeasbas, ArrayList<MeasureMeasress> measureMeasresses , ArrayList<MeasureMeasdets> measureMeasdets) {
        DataClient dataClient = APIUtils.getData();

        JSONArray jsonArrayBacMeasure = new JSONArray();
        JSONArray jsonArrayMeasureDet = new JSONArray();

        for (int i = 0 ; i < measureMeasparas.getArrBac().size() ; i++){
            JSONObject object = new JSONObject();
            try {
                object.put("bacname", measureMeasparas.getArrBac().get(i).getBacName());
                object.put("e1", measureMeasparas.getArrBac().get(i).getE1());
                object.put("e2", measureMeasparas.getArrBac().get(i).getE2());
                object.put("e3", measureMeasparas.getArrBac().get(i).getE3());
                object.put("e4", measureMeasparas.getArrBac().get(i).getE4());
                object.put("id", measureMeasparas.getArrBac().get(i).getId());
                object.put("sensor_setting_id", measureMeasparas.getArrBac().get(i).getSensor_setting_id());
                object.put("created_at", measureMeasparas.getArrBac().get(i).getCreatedAt());
                jsonArrayBacMeasure.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0 ; i < measureMeasdets.size() ; i++){
            JSONObject object = new JSONObject();
            try {
                object.put("id", measureMeasdets.get(i).getId());
                object.put("no", measureMeasdets.get(i).getNo());
                object.put("deltae", measureMeasdets.get(i).getDeltae());
                object.put("deltai", measureMeasdets.get(i).getDeltai());
                object.put("eb", measureMeasdets.get(i).getEb());
                object.put("ib", measureMeasdets.get(i).getIb());
                object.put("ef", measureMeasdets.get(i).getEf());
                object.put("if", measureMeasdets.get(i).get_if());
                object.put("created_at", measureMeasdets.get(i).getCreatedAt());
                object.put("updated_at", measureMeasdets.get(i).getUpdatedAt());
                jsonArrayMeasureDet.put(object);
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
                    measureMeasparas.getSetname(),
                    measureMeasparas.getBacs(),
                    measureMeasparas.getCrng(),
                    measureMeasparas.getEqp1(),
                    measureMeasparas.getEqt1(),
                    measureMeasparas.getEqp2(),
                    measureMeasparas.getEqt2(),
                    measureMeasparas.getEqp3(),
                    measureMeasparas.getEqt3(),
                    measureMeasparas.getEqp4(),
                    measureMeasparas.getEqt4(),
                    measureMeasparas.getEqp5(),
                    measureMeasparas.getEqt5(),
                    measureMeasparas.getStp(),
                    measureMeasparas.getEnp(),
                    measureMeasparas.getPp(),
                    measureMeasparas.getDlte(),
                    measureMeasparas.getPwd(),
                    measureMeasparas.getPtm(),
                    measureMeasparas.getIbst(),
                    measureMeasparas.getIben(),
                    measureMeasparas.getIfst(),
                    measureMeasparas.getIfen(),
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

                    jsonArrayMeasureDet.toString()
            );
        }else if (measureMeasresses.size() == 2){
            callback = dataClient.storeMeasure(token,
                    idMeasure,
                    datetime,
                    no,
                    measureMeasparas.getSetname(),
                    measureMeasparas.getBacs(),
                    measureMeasparas.getCrng(),
                    measureMeasparas.getEqp1(),
                    measureMeasparas.getEqt1(),
                    measureMeasparas.getEqp2(),
                    measureMeasparas.getEqt2(),
                    measureMeasparas.getEqp3(),
                    measureMeasparas.getEqt3(),
                    measureMeasparas.getEqp4(),
                    measureMeasparas.getEqt4(),
                    measureMeasparas.getEqp5(),
                    measureMeasparas.getEqt5(),
                    measureMeasparas.getStp(),
                    measureMeasparas.getEnp(),
                    measureMeasparas.getPp(),
                    measureMeasparas.getDlte(),
                    measureMeasparas.getPwd(),
                    measureMeasparas.getPtm(),
                    measureMeasparas.getIbst(),
                    measureMeasparas.getIben(),
                    measureMeasparas.getIfst(),
                    measureMeasparas.getIfen(),
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

                    jsonArrayMeasureDet.toString()
            );
        }else if (measureMeasresses.size() == 3){
            callback = dataClient.storeMeasure(token,
                    idMeasure,
                    datetime,
                    no,
                    measureMeasparas.getSetname(),
                    measureMeasparas.getBacs(),
                    measureMeasparas.getCrng(),
                    measureMeasparas.getEqp1(),
                    measureMeasparas.getEqt1(),
                    measureMeasparas.getEqp2(),
                    measureMeasparas.getEqt2(),
                    measureMeasparas.getEqp3(),
                    measureMeasparas.getEqt3(),
                    measureMeasparas.getEqp4(),
                    measureMeasparas.getEqt4(),
                    measureMeasparas.getEqp5(),
                    measureMeasparas.getEqt5(),
                    measureMeasparas.getStp(),
                    measureMeasparas.getEnp(),
                    measureMeasparas.getPp(),
                    measureMeasparas.getDlte(),
                    measureMeasparas.getPwd(),
                    measureMeasparas.getPtm(),
                    measureMeasparas.getIbst(),
                    measureMeasparas.getIben(),
                    measureMeasparas.getIfst(),
                    measureMeasparas.getIfen(),
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

                    jsonArrayMeasureDet.toString()
            );
        }else if (measureMeasresses.size() == 4){
            callback = dataClient.storeMeasure(token,
                    idMeasure,
                    datetime,
                    no,
                    measureMeasparas.getSetname(),
                    measureMeasparas.getBacs(),
                    measureMeasparas.getCrng(),
                    measureMeasparas.getEqp1(),
                    measureMeasparas.getEqt1(),
                    measureMeasparas.getEqp2(),
                    measureMeasparas.getEqt2(),
                    measureMeasparas.getEqp3(),
                    measureMeasparas.getEqt3(),
                    measureMeasparas.getEqp4(),
                    measureMeasparas.getEqt4(),
                    measureMeasparas.getEqp5(),
                    measureMeasparas.getEqt5(),
                    measureMeasparas.getStp(),
                    measureMeasparas.getEnp(),
                    measureMeasparas.getPp(),
                    measureMeasparas.getDlte(),
                    measureMeasparas.getPwd(),
                    measureMeasparas.getPtm(),
                    measureMeasparas.getIbst(),
                    measureMeasparas.getIben(),
                    measureMeasparas.getIfst(),
                    measureMeasparas.getIfen(),
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

                    jsonArrayMeasureDet.toString()
            );
        }else if (measureMeasresses.size() == 5){
            callback = dataClient.storeMeasure(token,
                    idMeasure,
                    datetime,
                    no,
                    measureMeasparas.getSetname(),
                    measureMeasparas.getBacs(),
                    measureMeasparas.getCrng(),
                    measureMeasparas.getEqp1(),
                    measureMeasparas.getEqt1(),
                    measureMeasparas.getEqp2(),
                    measureMeasparas.getEqt2(),
                    measureMeasparas.getEqp3(),
                    measureMeasparas.getEqt3(),
                    measureMeasparas.getEqp4(),
                    measureMeasparas.getEqt4(),
                    measureMeasparas.getEqp5(),
                    measureMeasparas.getEqt5(),
                    measureMeasparas.getStp(),
                    measureMeasparas.getEnp(),
                    measureMeasparas.getPp(),
                    measureMeasparas.getDlte(),
                    measureMeasparas.getPwd(),
                    measureMeasparas.getPtm(),
                    measureMeasparas.getIbst(),
                    measureMeasparas.getIben(),
                    measureMeasparas.getIfst(),
                    measureMeasparas.getIfen(),
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

                    jsonArrayMeasureDet.toString()
            );
        }else {
            callback = dataClient.storeMeasure(token,
                    idMeasure,
                    datetime,
                    no,
                    measureMeasparas.getSetname(),
                    measureMeasparas.getBacs(),
                    measureMeasparas.getCrng(),
                    measureMeasparas.getEqp1(),
                    measureMeasparas.getEqt1(),
                    measureMeasparas.getEqp2(),
                    measureMeasparas.getEqt2(),
                    measureMeasparas.getEqp3(),
                    measureMeasparas.getEqt3(),
                    measureMeasparas.getEqp4(),
                    measureMeasparas.getEqt4(),
                    measureMeasparas.getEqp5(),
                    measureMeasparas.getEqt5(),
                    measureMeasparas.getStp(),
                    measureMeasparas.getEnp(),
                    measureMeasparas.getPp(),
                    measureMeasparas.getDlte(),
                    measureMeasparas.getPwd(),
                    measureMeasparas.getPtm(),
                    measureMeasparas.getIbst(),
                    measureMeasparas.getIben(),
                    measureMeasparas.getIfst(),
                    measureMeasparas.getIfen(),
                    jsonArrayBacMeasure.toString(),
                    jsonArrayMeasureDet.toString()
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
                //Protector.appendLog(true ,t.getMessage());
            }
        });
    }
}
