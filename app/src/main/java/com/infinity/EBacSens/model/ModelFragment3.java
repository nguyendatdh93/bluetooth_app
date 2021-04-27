package com.infinity.EBacSens.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.infinity.EBacSens.model_objects.DataSensorSettingAPI;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.retrofit2.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelFragment3 {

    private ModelFragmeant3Listener modelFragmeant3Listener;

    public ModelFragment3(ModelFragmeant3Listener modelFragmeant3Listener) {
        this.modelFragmeant3Listener = modelFragmeant3Listener;
    }

    public void handleUpdateSettingMeasure(String token , int idSensor , SensorSetting sensorSetting){
        DataClient dataClient = APIUtils.getData();

        final Call<DataSensorSettingAPI> callback = dataClient.updateSettingSensor(token , idSensor , sensorSetting.getSetname(), sensorSetting.getBacs(), sensorSetting.getCrng(), sensorSetting.getEqp1(), sensorSetting.getEqt1() , sensorSetting.getEqp2(), sensorSetting.getEqt2() , sensorSetting.getEqp3(), sensorSetting.getEqt3() , sensorSetting.getEqp4(), sensorSetting.getEqt4() , sensorSetting.getEqp5(), sensorSetting.getEqt5() , sensorSetting.getStp(), sensorSetting.getEnp(), sensorSetting.getPp(), sensorSetting.getDlte(), sensorSetting.getPwd(), sensorSetting.getPtm(), sensorSetting.getIbst(), sensorSetting.getIben(), sensorSetting.getIfst(), sensorSetting.getIfen(), sensorSetting.getBacSetting().get(0).getBacName(), sensorSetting.getBacSetting().get(0).getE1(), sensorSetting.getBacSetting().get(0).getE2(), sensorSetting.getBacSetting().get(0).getE3(), sensorSetting.getBacSetting().get(0).getE4(), sensorSetting.getBacSetting().get(0).getPkp(), sensorSetting.getBacSetting().get(1).getBacName(), sensorSetting.getBacSetting().get(1).getE1(), sensorSetting.getBacSetting().get(1).getE2(), sensorSetting.getBacSetting().get(1).getE3(), sensorSetting.getBacSetting().get(1).getE4(), sensorSetting.getBacSetting().get(1).getPkp());
        //final Call<DataSensorSettingAPI> callback = dataClient.updateSettingSensor(token , idSensor , sensorSetting.getSetname(), sensorSetting.getBacs(), sensorSetting.getCrng(), sensorSetting.getEqp1(),sensorSetting.getEqt1() , sensorSetting.getEqp2(), sensorSetting.getEqt2() , sensorSetting.getEqp3(), sensorSetting.getEqt3() , sensorSetting.getEqp4(), sensorSetting.getEqt4() , sensorSetting.getEqp5(), sensorSetting.getEqt5(),sensorSetting.getStp(), sensorSetting.getEnp(), sensorSetting.getPp(),sensorSetting.getDlte(), sensorSetting.getPwd(), sensorSetting.getPtm(), 1,1,1,1, "bac0",1,1,1,1,1 , "bac1",1,1,1,1,1);
        callback.enqueue(new Callback<DataSensorSettingAPI>() {
            @Override
            public void onResponse(@NonNull Call<DataSensorSettingAPI> call, @NonNull Response<DataSensorSettingAPI> response) {
                if (response.code() == 201 || response.code() == 200){
                    modelFragmeant3Listener.onSuccessUpdateSettingSensor();
                }else {
                    modelFragmeant3Listener.onFailUpdateSettingSensor("error " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataSensorSettingAPI> call, @NonNull Throwable t) {
                modelFragmeant3Listener.onFailUpdateSettingSensor(t.getMessage());
            }
        });
    }

    public void handleReceiveSettingMeasure(String token , int idSensor){
        DataClient dataClient = APIUtils.getData();
        final Call<DataSensorSettingAPI> callback = dataClient.getSettingSensor(token , idSensor);
        callback.enqueue(new Callback<DataSensorSettingAPI>() {
            @Override
            public void onResponse(@NonNull Call<DataSensorSettingAPI> call, @NonNull Response<DataSensorSettingAPI> response) {
                if (response.body() != null && response.body().getSensors() != null){
                    modelFragmeant3Listener.onGetSettingSensor(response.body().getSensors());
                }else {
                    modelFragmeant3Listener.onGetSettingSensor(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataSensorSettingAPI> call, @NonNull Throwable t) {
                modelFragmeant3Listener.onGetSettingSensor(null);
            }
        });
    }
}
