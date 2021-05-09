package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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

    @SerializedName("no")
    @Expose
    private String no;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("measba")
    @Expose
    private MeasureMeasbas measureMeasbas;

    @SerializedName("measdet")
    @Expose
    private ArrayList<MeasureMeasdets> measureMeasdets;

    @SerializedName("measpara")
    @Expose
    private MeasureMeasparas measureMeasparas;

    @SerializedName("measres")
    @Expose
    private ArrayList<MeasureMeasress> measureMeasresses;

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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
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

    public ArrayList<MeasureMeasdets> getMeasureMeasdets() {
        return measureMeasdets;
    }

    public void setMeasureMeasdets(ArrayList<MeasureMeasdets> measureMeasdets) {
        this.measureMeasdets = measureMeasdets;
    }

    public MeasureMeasparas getMeasureMeasparas() {
        return measureMeasparas;
    }

    public void setMeasureMeasparas(MeasureMeasparas measureMeasparas) {
        this.measureMeasparas = measureMeasparas;
    }

    public ArrayList<MeasureMeasress> getMeasureMeasresses() {
        return measureMeasresses;
    }

    public void setMeasureMeasresses(ArrayList<MeasureMeasress> measureMeasresses) {
        this.measureMeasresses = measureMeasresses;
    }

    public SensorMeasure(int id, int sensorId, String datetime, String no, String createdAt, String updatedAt, MeasureMeasbas measureMeasbas, ArrayList<MeasureMeasdets> measureMeasdets, MeasureMeasparas measureMeasparas, ArrayList<MeasureMeasress> measureMeasresses) {
        this.id = id;
        this.sensorId = sensorId;
        this.datetime = datetime;
        this.no = no;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.measureMeasbas = measureMeasbas;
        this.measureMeasdets = measureMeasdets;
        this.measureMeasparas = measureMeasparas;
        this.measureMeasresses = measureMeasresses;
    }
}
