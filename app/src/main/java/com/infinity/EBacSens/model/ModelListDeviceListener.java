package com.infinity.EBacSens.model;

import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.model_objects.SensorSetting;

import java.util.List;

public interface ModelListDeviceListener {
    void onSuccessStoreSensor(SensorInfor sensorInfor);
    void onFailStoreSensor(String error);
}
