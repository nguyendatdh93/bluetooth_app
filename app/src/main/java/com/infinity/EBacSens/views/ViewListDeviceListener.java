package com.infinity.EBacSens.views;

import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.model_objects.SensorSetting;

import java.util.List;

public interface ViewListDeviceListener {
    void onSuccessStoreSensor(SensorInfor sensorInfor);
    void onFailStoreSensor(String error);
}
