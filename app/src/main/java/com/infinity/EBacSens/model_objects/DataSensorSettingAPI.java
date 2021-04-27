package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataSensorSettingAPI {
    @SerializedName("data")
    @Expose
    List<SensorSetting> sensors;

    public List<SensorSetting> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorSetting> sensors) {
        this.sensors = sensors;
    }

    public DataSensorSettingAPI(List<SensorSetting> sensors) {
        this.sensors = sensors;
    }
}
