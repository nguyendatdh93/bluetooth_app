package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataSensorAPI {
    @SerializedName("data")
    @Expose
    List<Sensor> sensors;

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public DataSensorAPI(List<Sensor> sensors) {
        this.sensors = sensors;
    }
}
