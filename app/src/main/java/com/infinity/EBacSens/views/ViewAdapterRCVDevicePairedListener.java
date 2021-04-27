package com.infinity.EBacSens.views;

import com.infinity.EBacSens.model_objects.SensorInfor;

import java.util.ArrayList;

public interface ViewAdapterRCVDevicePairedListener {
    void onGetData(ArrayList<SensorInfor> arrayList);
    void onLoaded();
    void onLoadMore();
    void onSuccessDeleteSettingSensor(int position);
    void onFailDeleteSettingSensor(String error);
}
