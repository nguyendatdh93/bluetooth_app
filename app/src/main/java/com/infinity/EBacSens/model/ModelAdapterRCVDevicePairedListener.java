package com.infinity.EBacSens.model;

import com.infinity.EBacSens.model_objects.SensorInfor;

import java.util.ArrayList;

public interface ModelAdapterRCVDevicePairedListener {
    void onGetData(ArrayList<SensorInfor> arrayList);
    void onSuccessDeleteSettingSensor(int position);
    void onFailDeleteSettingSensor(String error);
}
