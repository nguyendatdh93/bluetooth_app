package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeasureMeasbas {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("datetime")
    @Expose
    private String datetime;

    @SerializedName("measba[num]")
    @Expose
    private int num;

    @SerializedName("pstaterr")
    @Expose
    private int pstaterr;

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

    public int getPstaterr() {
        return pstaterr;
    }

    public void setPstaterr(int pstaterr) {
        this.pstaterr = pstaterr;
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

    public MeasureMeasbas() {
    }

    public MeasureMeasbas(int id, String datetime, int num, int pstaterr, String createdAt, String updatedAt) {
        this.id = id;
        this.datetime = datetime;
        this.num = num;
        this.pstaterr = pstaterr;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
