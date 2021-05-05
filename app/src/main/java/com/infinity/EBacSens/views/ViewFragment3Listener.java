package com.infinity.EBacSens.views;

import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.model_objects.SensorSetting;

import java.util.ArrayList;
import java.util.List;

public interface ViewFragment3Listener {
    void onSuccessUpdateSettingSensor();
    void onFailUpdateSettingSensor(String error);
    void onGetSettingSensor(ArrayList<SensorSetting> sensorSetting);
}
