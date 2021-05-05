package com.infinity.EBacSens.model;

import androidx.annotation.NonNull;

import com.infinity.EBacSens.model_objects.DataSensorSettingAPI;
import com.infinity.EBacSens.model_objects.SensorMeasureDetail;
import com.infinity.EBacSens.model_objects.SensorMeasurePage;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.retrofit2.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelFragment4 {

    private ModelFragmeant4Listener modelFragmeant4Listener;

    public ModelFragment4(ModelFragmeant4Listener modelFragmeant4Listener) {
        this.modelFragmeant4Listener = modelFragmeant4Listener;
    }

    public void handleGetMeasurePage(String token , int idSensor , int page , int ful){
        DataClient dataClient = APIUtils.getData();
        final Call<SensorMeasurePage> callback = dataClient.getMeasurePage(token , idSensor , page ,  ful);
        callback.enqueue(new Callback<SensorMeasurePage>() {
            @Override
            public void onResponse(@NonNull Call<SensorMeasurePage> call, @NonNull Response<SensorMeasurePage> response) {
                modelFragmeant4Listener.onGetDataMeasurePage(response.body() != null ? response.body().getMeasurePages() : null);
            }

            @Override
            public void onFailure(@NonNull Call<SensorMeasurePage> call, @NonNull Throwable t) {
                modelFragmeant4Listener.onGetDataMeasurePage(null);
            }
        });
    }

    public void handleGetDetailMeasure(String token , int idMeasure){
        DataClient dataClient = APIUtils.getData();
        final Call<SensorMeasureDetail> callback = dataClient.getDetailMeasure(token , idMeasure);
        callback.enqueue(new Callback<SensorMeasureDetail>() {
            @Override
            public void onResponse(@NonNull Call<SensorMeasureDetail> call, @NonNull Response<SensorMeasureDetail> response) {
                modelFragmeant4Listener.onGetDataMeasureDetail(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<SensorMeasureDetail> call, @NonNull Throwable t) {
                modelFragmeant4Listener.onGetDataMeasureDetail(null);
            }
        });
    }
}
