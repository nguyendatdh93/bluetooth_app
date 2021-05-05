package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeasureMeasdets {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("sensor_measure_id")
    @Expose
    private int sensorMeasureId;

    @SerializedName("settings")
    @Expose
    private String settings;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("casted_rawdmp")
    @Expose
    private List<Integer> castedRawdmp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSensorMeasureId() {
        return sensorMeasureId;
    }

    public void setSensorMeasureId(int sensorMeasureId) {
        this.sensorMeasureId = sensorMeasureId;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Integer> getCastedRawdmp() {
        return castedRawdmp;
    }

    public void setCastedRawdmp(List<Integer> castedRawdmp) {
        this.castedRawdmp = castedRawdmp;
    }

    public MeasureMeasdets(int id, int sensorMeasureId, String settings, String createdAt, String updatedAt, List<Integer> castedRawdmp) {
        this.id = id;
        this.sensorMeasureId = sensorMeasureId;
        this.settings = settings;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.castedRawdmp = castedRawdmp;
    }
}
