package com.infinity.EBacSens.views;

import com.infinity.EBacSens.model_objects.SensorMeasure;
import com.infinity.EBacSens.model_objects.SensorMeasureDetail;
import com.infinity.EBacSens.model_objects.SensorMeasurePage;
import com.infinity.EBacSens.model_objects.SensorSetting;

import java.util.List;

public interface ViewFragment4Listener {
    void onGetDataMeasurePage(List<SensorMeasurePage.MeasurePage> measurePages);
    void onGetDataMeasureDetail(SensorMeasureDetail sensorMeasureDetail);
    void onSuccessStoreMeasure(SensorMeasure sensorMeasure);
    void onFailStoreMeasure(String error);
}
