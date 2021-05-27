package com.infinity.EBacSens.model;

import androidx.annotation.NonNull;

import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.DataSensorSettingAPI;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.retrofit2.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelListDevice {

    private ModelListDeviceListener modelListDeviceListener;

    public ModelListDevice(ModelListDeviceListener modelListDeviceListener) {
        this.modelListDeviceListener = modelListDeviceListener;
    }

    public void handleStoreSensor(String token , String name , String datetime , String macDevice){
        DataClient dataClient = APIUtils.getData();
        final Call<SensorInfor> callback = dataClient.storeSensor(token , name , datetime , macDevice);
        callback.enqueue(new Callback<SensorInfor>() {
            @Override
            public void onResponse(@NonNull Call<SensorInfor> call, @NonNull Response<SensorInfor> response) {
                if (response.body() != null){
                    modelListDeviceListener.onSuccessStoreSensor(response.body());
                }else {
                    modelListDeviceListener.onFailStoreSensor("error" + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SensorInfor> call, @NonNull Throwable t) {
                modelListDeviceListener.onFailStoreSensor(t.getMessage());
                Protector.appendLog(true ,t.getMessage());
            }
        });
    }
}
