package com.infinity.EBacSens.model;

import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.model_objects.SensorSetting;

import java.util.ArrayList;
import java.util.List;

public interface ModelFragmeant3Listener {
    void onSuccessUpdateSettingSensor();
    void onFailUpdateSettingSensor(String error);
    void onGetSettingSensor(List<SensorSetting> sensorSetting);
}