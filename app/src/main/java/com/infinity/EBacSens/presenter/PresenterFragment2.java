package com.infinity.EBacSens.presenter;

import com.infinity.EBacSens.model.ModelFragmeant2Listener;
import com.infinity.EBacSens.model.ModelFragmeant3Listener;
import com.infinity.EBacSens.model.ModelFragment2;
import com.infinity.EBacSens.model.ModelFragment3;
import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.model_objects.TimeZone;
import com.infinity.EBacSens.views.ViewFragment2Listener;
import com.infinity.EBacSens.views.ViewFragment3Listener;

import java.util.ArrayList;

public class PresenterFragment2 implements ModelFragmeant2Listener {

    private ModelFragment2 modelFragment2;
    private ViewFragment2Listener callback;

    public PresenterFragment2(ViewFragment2Listener callback) {
        modelFragment2 = new ModelFragment2(this);
        this.callback = callback;
    }

    public void receivedGetTimezone(String token){
        modelFragment2.handleGetTimezone(token);
    }

    @Override
    public void onGetTime(TimeZone timeZone) {
        callback.onGetTime(timeZone);
    }
}
