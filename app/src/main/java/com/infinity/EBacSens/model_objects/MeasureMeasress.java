package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeasureMeasress {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("sensor_measure_id")
    @Expose
    private int sensorMeasureId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("pkpot")
    @Expose
    private int pkpot;

    @SerializedName("dltc")
    @Expose
    private int dltc;

    @SerializedName("bgc")
    @Expose
    private int bgc;

    @SerializedName("err")
    @Expose
    private int err;

    @SerializedName("blpsx")
    @Expose
    private String blpsx;

    @SerializedName("blpsy")
    @Expose
    private String blpsy;

    @SerializedName("blpex")
    @Expose
    private String blpex;

    @SerializedName("blpey")
    @Expose
    private String blpey;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPkpot() {
        return pkpot;
    }

    public void setPkpot(int pkpot) {
        this.pkpot = pkpot;
    }

    public int getDltc() {
        return dltc;
    }

    public void setDltc(int dltc) {
        this.dltc = dltc;
    }

    public int getBgc() {
        return bgc;
    }

    public void setBgc(int bgc) {
        this.bgc = bgc;
    }

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getBlpsx() {
        return blpsx;
    }

    public void setBlpsx(String blpsx) {
        this.blpsx = blpsx;
    }

    public String getBlpsy() {
        return blpsy;
    }

    public void setBlpsy(String blpsy) {
        this.blpsy = blpsy;
    }

    public String getBlpex() {
        return blpex;
    }

    public void setBlpex(String blpex) {
        this.blpex = blpex;
    }

    public String getBlpey() {
        return blpey;
    }

    public void setBlpey(String blpey) {
        this.blpey = blpey;
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

    public MeasureMeasress(int id, int sensorMeasureId, String name, int pkpot, int dltc, int bgc, int err, String blpsx, String blpsy, String blpex, String blpey, String createdAt, String updatedAt) {
        this.id = id;
        this.sensorMeasureId = sensorMeasureId;
        this.name = name;
        this.pkpot = pkpot;
        this.dltc = dltc;
        this.bgc = bgc;
        this.err = err;
        this.blpsx = blpsx;
        this.blpsy = blpsy;
        this.blpex = blpex;
        this.blpey = blpey;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
