package com.infinity.EBacSens.model;

import com.infinity.EBacSens.model_objects.Sensor;

import java.util.ArrayList;

public interface ModelAdapterRCVDevicePairedListener {
    void onGetData(ArrayList<Sensor> arrayList);
    void onSuccessDeleteSettingSensor(int position);
    void onFailDeleteSettingSensor(String error);
}
