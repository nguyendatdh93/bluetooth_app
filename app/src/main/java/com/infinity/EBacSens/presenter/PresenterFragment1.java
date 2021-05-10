package com.infinity.EBacSens.presenter;

import com.infinity.EBacSens.model.ModelFragmeant3Listener;
import com.infinity.EBacSens.model.ModelFragment3;
import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.views.ViewFragment1Listener;
import com.infinity.EBacSens.views.ViewFragment3Listener;

import java.util.ArrayList;

public class PresenterFragment1 {

    //private ModelFragment3 modelFragment3;
    private ViewFragment1Listener callback;

    public PresenterFragment1(ViewFragment1Listener callback) {
        //modelFragment3 = new ModelFragment3(this);
        this.callback = callback;
    }

    public void receivedUpdateUI(){
        callback.onUpdateUI();
    }

}
