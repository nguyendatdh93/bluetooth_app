package com.infinity.EBacSens.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.infinity.EBacSens.model_objects.DataSensorAPI;
import com.infinity.EBacSens.model_objects.Sensor;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.retrofit2.DataClient;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelAdapterRCVDevicePaired {

    private ModelAdapterRCVDevicePairedListener modelAdapterRCVDevicePairedListener;

    public ModelAdapterRCVDevicePaired(ModelAdapterRCVDevicePairedListener modelAdapterRCVDevicePairedListener) {
        this.modelAdapterRCVDevicePairedListener = modelAdapterRCVDevicePairedListener;
    }

    public void handleGetData(int limit , int offset){
        DataClient dataClient = APIUtils.getData();
        final Call<DataSensorAPI> callback = dataClient.getAllSensorPaired(APIUtils.token);
        callback.enqueue(new Callback<DataSensorAPI>() {
            @Override
            public void onResponse(@NonNull Call<DataSensorAPI> call, @NonNull Response<DataSensorAPI> response) {
                if (response.body() != null && response.body().getSensors() != null){
                    ArrayList<Sensor> sensors = (ArrayList<Sensor>) response.body().getSensors();
                    modelAdapterRCVDevicePairedListener.onGetData(sensors);
                }else {
                    modelAdapterRCVDevicePairedListener.onGetData(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataSensorAPI> call, @NonNull Throwable t) {
                modelAdapterRCVDevicePairedListener.onGetData(null);
            }
        });
    }

    public void handleDeleteSettingSensor(String token , long idSensor , int position){
        DataClient dataClient = APIUtils.getData();
        final Call<String> callback = dataClient.deleteSettingSensor(token , idSensor);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.code() == 200) {
                    modelAdapterRCVDevicePairedListener.onSuccessDeleteSettingSensor(position);
                } else {
                    modelAdapterRCVDevicePairedListener.onFailDeleteSettingSensor(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                modelAdapterRCVDevicePairedListener.onFailDeleteSettingSensor(t.getMessage());
            }
        });
    }
}
