package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataSensorSettingAPI {
    @SerializedName("data")
    @Expose
    ArrayList<SensorSetting> sensors;

    public ArrayList<SensorSetting> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<SensorSetting> sensors) {
        this.sensors = sensors;
    }

    public DataSensorSettingAPI(ArrayList<SensorSetting> sensors) {
        this.sensors = sensors;
    }
}
