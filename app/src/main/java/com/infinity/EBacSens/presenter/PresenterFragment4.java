package com.infinity.EBacSens.presenter;

import com.infinity.EBacSens.model.ModelFragmeant3Listener;
import com.infinity.EBacSens.model.ModelFragmeant4Listener;
import com.infinity.EBacSens.model.ModelFragment3;
import com.infinity.EBacSens.model.ModelFragment4;
import com.infinity.EBacSens.model_objects.SensorMeasureDetail;
import com.infinity.EBacSens.model_objects.SensorMeasurePage;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.views.ViewFragment3Listener;
import com.infinity.EBacSens.views.ViewFragment4Listener;

import java.util.List;

public class PresenterFragment4 implements ModelFragmeant4Listener {

    private ModelFragment4 modelFragment4;
    private ViewFragment4Listener callback;

    public PresenterFragment4(ViewFragment4Listener callback) {
        modelFragment4 = new ModelFragment4(this);
        this.callback = callback;
    }

    public void receivedGetMeasurePage(String token , int idSensor , int page , int ful){
        modelFragment4.handleGetMeasurePage(token , idSensor , page , ful);
    }

    public void receivedGetDetailMeasure(String token , int idMeasure){
        modelFragment4.handleGetDetailMeasure(token , idMeasure);
    }

    @Override
    public void onGetDataMeasurePage(List<SensorMeasurePage.MeasurePage> measurePages) {
        callback.onGetDataMeasurePage(measurePages);
    }

    @Override
    public void onGetDataMeasureDetail(SensorMeasureDetail sensorMeasureDetail) {
        callback.onGetDataMeasureDetail(sensorMeasureDetail);
    }
}
