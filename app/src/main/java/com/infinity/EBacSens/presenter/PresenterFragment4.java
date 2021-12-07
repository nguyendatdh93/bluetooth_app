package com.infinity.EBacSens.presenter;

import android.content.Context;

import com.infinity.EBacSens.model.ModelFragmeant4Listener;
import com.infinity.EBacSens.model.ModelFragment4;
import com.infinity.EBacSens.model_objects.MeasureMeasbas;
import com.infinity.EBacSens.model_objects.MeasureMeasdets;
import com.infinity.EBacSens.model_objects.MeasureMeasparas;
import com.infinity.EBacSens.model_objects.MeasureMeasress;
import com.infinity.EBacSens.model_objects.SensorMeasure;
import com.infinity.EBacSens.model_objects.SensorMeasureDetail;
import com.infinity.EBacSens.model_objects.SensorMeasurePage;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.views.ViewFragment4Listener;

import java.util.ArrayList;
import java.util.List;

public class PresenterFragment4 implements ModelFragmeant4Listener {

    private ModelFragment4 modelFragment4;
    private ViewFragment4Listener callback;

    public PresenterFragment4(ViewFragment4Listener callback , Context context) {
        modelFragment4 = new ModelFragment4(this , context);
        this.callback = callback;
    }

    public void receivedGetMeasurePage(String token , int idSensor , int page , int ful){
        modelFragment4.handleGetMeasurePage(token , idSensor , page , ful);
    }

    public void receivedGetDetailMeasure(String token , int idMeasure){
        modelFragment4.handleGetDetailMeasure(token , idMeasure);
    }

    public void receivedStoreMeasure(String token, int idMeasure, String datetime, String no, MeasureMeasparas measureMeasparas, MeasureMeasbas measureMeasbas, ArrayList<MeasureMeasress> measureMeasresses, ArrayList<MeasureMeasdets> measureMeasdets){
        modelFragment4.handleStoreMeasure(token , idMeasure , datetime , no , measureMeasparas , measureMeasbas , measureMeasresses , measureMeasdets);
    }

    @Override
    public void onGetDataMeasurePage(List<SensorMeasurePage.MeasurePage> measurePages) {
        callback.onGetDataMeasurePage(measurePages);
    }

    @Override
    public void onGetDataMeasureDetail(SensorMeasureDetail sensorMeasureDetail) {
        callback.onGetDataMeasureDetail(sensorMeasureDetail);
    }

    @Override
    public void onSuccessStoreMeasure(SensorMeasure sensorMeasure) {
        callback.onSuccessStoreMeasure(sensorMeasure);
    }

    @Override
    public void onFailStoreMeasure(String error) {
        callback.onFailStoreMeasure(error);
    }
}
