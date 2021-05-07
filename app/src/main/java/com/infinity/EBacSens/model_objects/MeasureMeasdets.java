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

    @SerializedName("no")
    @Expose
    private String no;

    @SerializedName("deltae")
    @Expose
    private String deltae;

    @SerializedName("deltal")
    @Expose
    private String deltal;

    @SerializedName("eb")
    @Expose
    private String eb;

    @SerializedName("lb")
    @Expose
    private String lb;

    @SerializedName("ef")
    @Expose
    private String ef;

    @SerializedName("lf")
    @Expose
    private String lf;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("casted_rawdmp")
    @Expose
    private String castedRawdmp;

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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDeltae() {
        return deltae;
    }

    public void setDeltae(String deltae) {
        this.deltae = deltae;
    }

    public String getDeltal() {
        return deltal;
    }

    public void setDeltal(String deltal) {
        this.deltal = deltal;
    }

    public String getEb() {
        return eb;
    }

    public void setEb(String eb) {
        this.eb = eb;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getEf() {
        return ef;
    }

    public void setEf(String ef) {
        this.ef = ef;
    }

    public String getLf() {
        return lf;
    }

    public void setLf(String lf) {
        this.lf = lf;
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

    public String getCastedRawdmp() {
        return castedRawdmp;
    }

    public void setCastedRawdmp(String castedRawdmp) {
        this.castedRawdmp = castedRawdmp;
    }

    public MeasureMeasdets(int id, int sensorMeasureId, String no, String deltae, String deltal, String eb, String lb, String ef, String lf, String createdAt, String updatedAt, String castedRawdmp) {
        this.id = id;
        this.sensorMeasureId = sensorMeasureId;
        this.no = no;
        this.deltae = deltae;
        this.deltal = deltal;
        this.eb = eb;
        this.lb = lb;
        this.ef = ef;
        this.lf = lf;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.castedRawdmp = castedRawdmp;
    }
}
