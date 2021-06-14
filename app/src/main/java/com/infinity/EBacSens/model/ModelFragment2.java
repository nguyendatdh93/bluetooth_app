package com.infinity.EBacSens.model;

import androidx.annotation.NonNull;

import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.DataSensorSettingAPI;
import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.model_objects.TimeZone;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.retrofit2.DataClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelFragment2 {

    private ModelFragmeant2Listener modelFragmeant2Listener;

    public ModelFragment2(ModelFragmeant2Listener modelFragmeant2Listener) {
        this.modelFragmeant2Listener = modelFragmeant2Listener;
    }

    public void handleGetTimezone(String user_content_key , String lib){
        DataClient dataClient = APIUtils.getDataTimezone();
        final Call<TimeZone> callback = dataClient.getTimeZone(user_content_key , lib);
        callback.enqueue(new Callback<TimeZone>() {
            @Override
            public void onResponse(@NonNull Call<TimeZone> call, @NonNull Response<TimeZone> response) {
                modelFragmeant2Listener.onGetTime(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TimeZone> call, @NonNull Throwable t) {
                modelFragmeant2Listener.onGetTime(null);
            }
        });
    }
}
