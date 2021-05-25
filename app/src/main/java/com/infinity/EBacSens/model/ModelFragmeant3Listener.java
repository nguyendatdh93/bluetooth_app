package com.infinity.EBacSens.model;

import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.model_objects.SensorSetting;

import java.util.ArrayList;

public interface ModelFragmeant3Listener {
    void onSuccessUpdateSettingSensor();
    void onFailUpdateSettingSensor(ErrorSensorSetting errorSensorSetting);
    void onGetSettingSensor(ArrayList<SensorSetting> sensorSetting);
    void onSuccessDeleteSettingSensor(int position);
    void onFailDeleteSettingSensor(String error);
}
