package com.infinity.EBacSens.presenter;

import com.infinity.EBacSens.model.ModelAdapterRCVDevicePaired;
import com.infinity.EBacSens.model.ModelAdapterRCVDevicePairedListener;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.views.ViewAdapterRCVDevicePairedListener;

import java.util.ArrayList;

public class PresenterAdapterRCVDevicePaired implements ModelAdapterRCVDevicePairedListener {

    private ModelAdapterRCVDevicePaired modelAdapterRCVDevicePaired;
    private ViewAdapterRCVDevicePairedListener callback;

    public PresenterAdapterRCVDevicePaired(ViewAdapterRCVDevicePairedListener callback) {
        modelAdapterRCVDevicePaired = new ModelAdapterRCVDevicePaired(this);
        this.callback = callback;
    }

    public void receivedGetData(int limit , int offset){
        modelAdapterRCVDevicePaired.handleGetData(limit , offset);
    }

    public void receivedDeleteSettingSensor(String token , long idSensor , int position){
        modelAdapterRCVDevicePaired.handleDeleteSettingSensor(token , idSensor , position);
    }

    @Override
    public void onGetData(ArrayList<SensorInfor> arrayList){
        callback.onLoaded();
        callback.onGetData(arrayList);
    }

    @Override
    public void onSuccessDeleteSettingSensor(int position) {
        callback.onSuccessDeleteSettingSensor(position);
    }


    @Override
    public void onFailDeleteSettingSensor(String error) {
        callback.onFailDeleteSettingSensor(error);
    }
}
