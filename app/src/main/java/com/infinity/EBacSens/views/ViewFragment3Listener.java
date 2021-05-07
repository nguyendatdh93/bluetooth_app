package com.infinity.EBacSens.views;

import com.infinity.EBacSens.model_objects.DataSensorSettingAPI;
import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.model_objects.SensorSetting;

import java.util.ArrayList;
import java.util.List;

public interface ViewFragment3Listener {
    void onSuccessUpdateSettingSensor();
    void onFailUpdateSettingSensor(ErrorSensorSetting errorSensorSetting);
    void onGetSettingSensor(ArrayList<SensorSetting> sensorSetting);
    void onSuccessDeleteSettingSensor(int position);
    void onFailDeleteSettingSensor(String error);
}
