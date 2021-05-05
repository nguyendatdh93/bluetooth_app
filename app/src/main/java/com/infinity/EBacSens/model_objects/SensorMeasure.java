package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SensorMeasure {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("sensor_id")
    @Expose
    private int sensorId;

    @SerializedName("datetime")
    @Expose
    private String datetime;

    @SerializedName("measure_id")
    @Expose
    private int measureId;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("measure_measba")
    @Expose
    private MeasureMeasbas measureMeasbas;

    @SerializedName("measure_measdet")
    @Expose
    private MeasureMeasdets measureMeasdets;

    @SerializedName("measure_measpara")
    @Expose
    private MeasureMeasparas measureMeasparas;

    @SerializedName("measure_measres")
    @Expose
    private MeasureMeasress measureMeasress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getMeasureId() {
        return measureId;
    }

    public void setMeasureId(int measureId) {
        this.measureId = measureId;
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

    public MeasureMeasbas getMeasureMeasbas() {
        return measureMeasbas;
    }

    public void setMeasureMeasbas(MeasureMeasbas measureMeasbas) {
        this.measureMeasbas = measureMeasbas;
    }

    public MeasureMeasdets getMeasureMeasdets() {
        return measureMeasdets;
    }

    public void setMeasureMeasdets(MeasureMeasdets measureMeasdets) {
        this.measureMeasdets = measureMeasdets;
    }

    public MeasureMeasparas getMeasureMeasparas() {
        return measureMeasparas;
    }

    public void setMeasureMeasparas(MeasureMeasparas measureMeasparas) {
        this.measureMeasparas = measureMeasparas;
    }

    public MeasureMeasress getMeasureMeasress() {
        return measureMeasress;
    }

    public void setMeasureMeasress(MeasureMeasress measureMeasress) {
        this.measureMeasress = measureMeasress;
    }

    public SensorMeasure(int id, int sensorId, String datetime, int measureId, String createdAt, String updatedAt, MeasureMeasbas measureMeasbas, MeasureMeasdets measureMeasdets, MeasureMeasparas measureMeasparas, MeasureMeasress measureMeasress) {
        this.id = id;
        this.sensorId = sensorId;
        this.datetime = datetime;
        this.measureId = measureId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.measureMeasbas = measureMeasbas;
        this.measureMeasdets = measureMeasdets;
        this.measureMeasparas = measureMeasparas;
        this.measureMeasress = measureMeasress;
    }
}
