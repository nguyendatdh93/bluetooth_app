package com.infinity.EBacSens.views;

public interface ViewConnectThread {
    void onGetData(String value);
    void onConnected();
    void onError(String error);
    void onRuned();
}
