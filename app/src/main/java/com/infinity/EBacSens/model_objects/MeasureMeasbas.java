package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeasureMeasbas {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("sensor_measure_id")
    @Expose
    private int sensorMeasureId;

    @SerializedName("datetime")
    @Expose
    private String datetime;

    @SerializedName("num")
    @Expose
    private int num;

    @SerializedName("pastaerr")
    @Expose
    private int pastaerr;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPastaerr() {
        return pastaerr;
    }

    public void setPastaerr(int pastaerr) {
        this.pastaerr = pastaerr;
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

    public MeasureMeasbas(int id, int sensorMeasureId, String datetime, int num, int pastaerr, String createdAt, String updatedAt) {
        this.id = id;
        this.sensorMeasureId = sensorMeasureId;
        this.datetime = datetime;
        this.num = num;
        this.pastaerr = pastaerr;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
