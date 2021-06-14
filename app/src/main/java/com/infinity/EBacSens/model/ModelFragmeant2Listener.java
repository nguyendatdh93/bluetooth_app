package com.infinity.EBacSens.model;

import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.model_objects.TimeZone;

import java.util.ArrayList;

public interface ModelFragmeant2Listener {
    void onGetTime(TimeZone timeZone);
}
