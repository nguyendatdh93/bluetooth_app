package com.infinity.EBacSens.presenter;

import com.infinity.EBacSens.model.ModelAdapterRCVDevicePaired;
import com.infinity.EBacSens.model.ModelAdapterRCVDevicePairedListener;
import com.infinity.EBacSens.model.ModelFragmeant3Listener;
import com.infinity.EBacSens.model.ModelFragment3;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.views.ViewAdapterRCVDevicePairedListener;
import com.infinity.EBacSens.views.ViewFragment3Listener;

import java.util.ArrayList;
import java.util.List;

public class PresenterFragment3 implements ModelFragmeant3Listener {

    private ModelFragment3 modelFragment3;
    private ViewFragment3Listener callback;

    public PresenterFragment3(ViewFragment3Listener callback) {
        modelFragment3 = new ModelFragment3(this);
        this.callback = callback;
    }

    public void receivedUpdateSettingMeasure(String token , int idSensor , SensorSetting sensorSetting){
        modelFragment3.handleUpdateSettingMeasure(token , idSensor , sensorSetting);
    }

    public void receivedReceiveSettingMeasure(String token , int idSensor){
        modelFragment3.handleReceiveSettingMeasure(token , idSensor );
    }

    @Override
    public void onSuccessUpdateSettingSensor() {
        callback.onSuccessUpdateSettingSensor();
    }

    @Override
    public void onFailUpdateSettingSensor(String error) {
        callback.onFailUpdateSettingSensor(error);
    }

    @Override
    public void onGetSettingSensor(List<SensorSetting> sensorSetting) {
        callback.onGetSettingSensor(sensorSetting);
    }

}
