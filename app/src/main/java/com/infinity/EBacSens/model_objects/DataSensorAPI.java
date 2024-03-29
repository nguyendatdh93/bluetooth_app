package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataSensorAPI {
    @SerializedName("data")
    @Expose
    List<SensorInfor> sensors;

    public List<SensorInfor> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorInfor> sensors) {
        this.sensors = sensors;
    }

    public DataSensorAPI(List<SensorInfor> sensors) {
        this.sensors = sensors;
    }
}
