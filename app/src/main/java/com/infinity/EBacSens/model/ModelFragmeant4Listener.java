package com.infinity.EBacSens.model;

import com.infinity.EBacSens.model_objects.SensorMeasureDetail;
import com.infinity.EBacSens.model_objects.SensorMeasurePage;
import com.infinity.EBacSens.model_objects.SensorSetting;

import java.util.List;

public interface ModelFragmeant4Listener {
    void onGetDataMeasurePage(List<SensorMeasurePage.MeasurePage> measurePages);
    void onGetDataMeasureDetail(SensorMeasureDetail sensorMeasureDetail);
}
