package com.infinity.EBacSens.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.DataSensorSettingAPI;
import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.retrofit2.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelFragment3 {

    private ModelFragmeant3Listener modelFragmeant3Listener;

    public ModelFragment3(ModelFragmeant3Listener modelFragmeant3Listener) {
        this.modelFragmeant3Listener = modelFragmeant3Listener;
    }

    public void handleSaveSettingMeasure(String token , int idSensor , SensorSetting sensorSetting){
        DataClient dataClient = APIUtils.getData();
        Call<ErrorSensorSetting> callback;
        if (sensorSetting.getBacSettings().size() == 1){
            callback = dataClient.saveSettingSensor(token ,  sensorSetting.getSetname(), sensorSetting.getBacs(), sensorSetting.getCrng(), sensorSetting.getEqp1(), sensorSetting.getEqt1() , sensorSetting.getEqp2(), sensorSetting.getEqt2() , sensorSetting.getEqp3(), sensorSetting.getEqt3() , sensorSetting.getEqp4(), sensorSetting.getEqt4() , sensorSetting.getEqp5(), sensorSetting.getEqt5() , sensorSetting.getStp(), sensorSetting.getEnp(), sensorSetting.getPp(), sensorSetting.getDlte(), sensorSetting.getPwd(), sensorSetting.getPtm(), sensorSetting.getIbst(), sensorSetting.getIben(), sensorSetting.getIfst(), sensorSetting.getIfen(), sensorSetting.getBacSettings().get(0).getBacName(), sensorSetting.getBacSettings().get(0).getE1(), sensorSetting.getBacSettings().get(0).getE2(), sensorSetting.getBacSettings().get(0).getE3(), sensorSetting.getBacSettings().get(0).getE4(), sensorSetting.getBacSettings().get(0).getPkp());
        }else if (sensorSetting.getBacSettings().size() == 2){
            callback = dataClient.saveSettingSensor(token ,  sensorSetting.getSetname(), sensorSetting.getBacs(), sensorSetting.getCrng(), sensorSetting.getEqp1(), sensorSetting.getEqt1() , sensorSetting.getEqp2(), sensorSetting.getEqt2() , sensorSetting.getEqp3(), sensorSetting.getEqt3() , sensorSetting.getEqp4(), sensorSetting.getEqt4() , sensorSetting.getEqp5(), sensorSetting.getEqt5() , sensorSetting.getStp(), sensorSetting.getEnp(), sensorSetting.getPp(), sensorSetting.getDlte(), sensorSetting.getPwd(), sensorSetting.getPtm(), sensorSetting.getIbst(), sensorSetting.getIben(), sensorSetting.getIfst(), sensorSetting.getIfen(), sensorSetting.getBacSettings().get(0).getBacName(), sensorSetting.getBacSettings().get(0).getE1(), sensorSetting.getBacSettings().get(0).getE2(), sensorSetting.getBacSettings().get(0).getE3(), sensorSetting.getBacSettings().get(0).getE4(), sensorSetting.getBacSettings().get(0).getPkp(), sensorSetting.getBacSettings().get(1).getBacName(), sensorSetting.getBacSettings().get(1).getE1(), sensorSetting.getBacSettings().get(1).getE2(), sensorSetting.getBacSettings().get(1).getE3(), sensorSetting.getBacSettings().get(1).getE4(), sensorSetting.getBacSettings().get(1).getPkp());
        }else if (sensorSetting.getBacSettings().size() == 3){
            callback = dataClient.saveSettingSensor(token ,  sensorSetting.getSetname(), sensorSetting.getBacs(), sensorSetting.getCrng(), sensorSetting.getEqp1(), sensorSetting.getEqt1() , sensorSetting.getEqp2(), sensorSetting.getEqt2() , sensorSetting.getEqp3(), sensorSetting.getEqt3() , sensorSetting.getEqp4(), sensorSetting.getEqt4() , sensorSetting.getEqp5(), sensorSetting.getEqt5() , sensorSetting.getStp(), sensorSetting.getEnp(), sensorSetting.getPp(), sensorSetting.getDlte(), sensorSetting.getPwd(), sensorSetting.getPtm(), sensorSetting.getIbst(), sensorSetting.getIben(), sensorSetting.getIfst(), sensorSetting.getIfen(), sensorSetting.getBacSettings().get(0).getBacName(), sensorSetting.getBacSettings().get(0).getE1(), sensorSetting.getBacSettings().get(0).getE2(), sensorSetting.getBacSettings().get(0).getE3(), sensorSetting.getBacSettings().get(0).getE4(), sensorSetting.getBacSettings().get(0).getPkp(), sensorSetting.getBacSettings().get(1).getBacName(), sensorSetting.getBacSettings().get(1).getE1(), sensorSetting.getBacSettings().get(1).getE2(), sensorSetting.getBacSettings().get(1).getE3(), sensorSetting.getBacSettings().get(1).getE4(), sensorSetting.getBacSettings().get(1).getPkp(), sensorSetting.getBacSettings().get(2).getBacName(), sensorSetting.getBacSettings().get(2).getE1(), sensorSetting.getBacSettings().get(2).getE2(), sensorSetting.getBacSettings().get(2).getE3(), sensorSetting.getBacSettings().get(2).getE4(), sensorSetting.getBacSettings().get(2).getPkp());
        }else if (sensorSetting.getBacSettings().size() == 4){
            callback = dataClient.saveSettingSensor(token ,  sensorSetting.getSetname(), sensorSetting.getBacs(), sensorSetting.getCrng(), sensorSetting.getEqp1(), sensorSetting.getEqt1() , sensorSetting.getEqp2(), sensorSetting.getEqt2() , sensorSetting.getEqp3(), sensorSetting.getEqt3() , sensorSetting.getEqp4(), sensorSetting.getEqt4() , sensorSetting.getEqp5(), sensorSetting.getEqt5() , sensorSetting.getStp(), sensorSetting.getEnp(), sensorSetting.getPp(), sensorSetting.getDlte(), sensorSetting.getPwd(), sensorSetting.getPtm(), sensorSetting.getIbst(), sensorSetting.getIben(), sensorSetting.getIfst(), sensorSetting.getIfen(), sensorSetting.getBacSettings().get(0).getBacName(), sensorSetting.getBacSettings().get(0).getE1(), sensorSetting.getBacSettings().get(0).getE2(), sensorSetting.getBacSettings().get(0).getE3(), sensorSetting.getBacSettings().get(0).getE4(), sensorSetting.getBacSettings().get(0).getPkp(), sensorSetting.getBacSettings().get(1).getBacName(), sensorSetting.getBacSettings().get(1).getE1(), sensorSetting.getBacSettings().get(1).getE2(), sensorSetting.getBacSettings().get(1).getE3(), sensorSetting.getBacSettings().get(1).getE4(), sensorSetting.getBacSettings().get(1).getPkp(), sensorSetting.getBacSettings().get(2).getBacName(), sensorSetting.getBacSettings().get(2).getE1(), sensorSetting.getBacSettings().get(2).getE2(), sensorSetting.getBacSettings().get(2).getE3(), sensorSetting.getBacSettings().get(2).getE4(), sensorSetting.getBacSettings().get(2).getPkp(), sensorSetting.getBacSettings().get(3).getBacName(), sensorSetting.getBacSettings().get(3).getE1(), sensorSetting.getBacSettings().get(3).getE2(), sensorSetting.getBacSettings().get(3).getE3(), sensorSetting.getBacSettings().get(3).getE4(), sensorSetting.getBacSettings().get(3).getPkp());
        }else{
            callback = dataClient.saveSettingSensor(token , sensorSetting.getSetname(), sensorSetting.getBacs(), sensorSetting.getCrng(), sensorSetting.getEqp1(), sensorSetting.getEqt1() , sensorSetting.getEqp2(), sensorSetting.getEqt2() , sensorSetting.getEqp3(), sensorSetting.getEqt3() , sensorSetting.getEqp4(), sensorSetting.getEqt4() , sensorSetting.getEqp5(), sensorSetting.getEqt5() , sensorSetting.getStp(), sensorSetting.getEnp(), sensorSetting.getPp(), sensorSetting.getDlte(), sensorSetting.getPwd(), sensorSetting.getPtm(), sensorSetting.getIbst(), sensorSetting.getIben(), sensorSetting.getIfst(), sensorSetting.getIfen(), sensorSetting.getBacSettings().get(0).getBacName(), sensorSetting.getBacSettings().get(0).getE1(), sensorSetting.getBacSettings().get(0).getE2(), sensorSetting.getBacSettings().get(0).getE3(), sensorSetting.getBacSettings().get(0).getE4(), sensorSetting.getBacSettings().get(0).getPkp(), sensorSetting.getBacSettings().get(1).getBacName(), sensorSetting.getBacSettings().get(1).getE1(), sensorSetting.getBacSettings().get(1).getE2(), sensorSetting.getBacSettings().get(1).getE3(), sensorSetting.getBacSettings().get(1).getE4(), sensorSetting.getBacSettings().get(1).getPkp(), sensorSetting.getBacSettings().get(2).getBacName(), sensorSetting.getBacSettings().get(2).getE1(), sensorSetting.getBacSettings().get(2).getE2(), sensorSetting.getBacSettings().get(2).getE3(), sensorSetting.getBacSettings().get(2).getE4(), sensorSetting.getBacSettings().get(2).getPkp(), sensorSetting.getBacSettings().get(3).getBacName(), sensorSetting.getBacSettings().get(3).getE1(), sensorSetting.getBacSettings().get(3).getE2(), sensorSetting.getBacSettings().get(3).getE3(), sensorSetting.getBacSettings().get(3).getE4(), sensorSetting.getBacSettings().get(3).getPkp(), sensorSetting.getBacSettings().get(4).getBacName(), sensorSetting.getBacSettings().get(4).getE1(), sensorSetting.getBacSettings().get(4).getE2(), sensorSetting.getBacSettings().get(4).getE3(), sensorSetting.getBacSettings().get(4).getE4(), sensorSetting.getBacSettings().get(4).getPkp());
        }

        callback.enqueue(new Callback<ErrorSensorSetting>() {
            @Override
            public void onResponse(@NonNull Call<ErrorSensorSetting> call, @NonNull Response<ErrorSensorSetting> response) {
                if (response.isSuccessful()){
                    modelFragmeant3Listener.onSuccessUpdateSettingSensor();
                }else {
                    ErrorSensorSetting errorSensorSetting = Protector.parseErrorSensorSetting(response);
                    modelFragmeant3Listener.onFailUpdateSettingSensor(errorSensorSetting);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ErrorSensorSetting> call, @NonNull Throwable t) {
                modelFragmeant3Listener.onFailUpdateSettingSensor(null);
            }
        });
    }

    public void handleReceiveSettingMeasure(String token){
        DataClient dataClient = APIUtils.getData();
        final Call<DataSensorSettingAPI> callback = dataClient.getSettingSensor(token);
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

    public void handleDeleteSettingMeasure(String token , int idSensor , int position){
        DataClient dataClient = APIUtils.getData();
        final Call<String> callback = dataClient.deleteSettingSensor(token , idSensor);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.code() == 200) {
                    modelFragmeant3Listener.onSuccessDeleteSettingSensor(position);
                } else {
                    modelFragmeant3Listener.onFailDeleteSettingSensor(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                modelFragmeant3Listener.onFailDeleteSettingSensor(t.getMessage());
            }
        });
    }
}
