package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SensorMeasureDetail {

    @SerializedName("data")
    @Expose
    private SensorMeasure sensorMeasure;

    public SensorMeasure getSensorMeasure() {
        return sensorMeasure;
    }

    public void setSensorMeasure(SensorMeasure sensorMeasure) {
        this.sensorMeasure = sensorMeasure;
    }

    public SensorMeasureDetail(SensorMeasure sensorMeasure) {
        this.sensorMeasure = sensorMeasure;
    }
}
