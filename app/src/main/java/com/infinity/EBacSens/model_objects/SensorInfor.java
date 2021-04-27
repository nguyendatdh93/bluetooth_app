package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SensorInfor implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("mac_device")
    @Expose
    private String macDevice;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("datetime")
    @Expose
    private String datetime;

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

    public String getMacDevice() {
        return macDevice;
    }

    public void setMacDevice(String macDevice) {
        this.macDevice = macDevice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
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

    public SensorInfor(int id, String macDevice, String name, String datetime, String createdAt, String updatedAt) {
        this.id = id;
        this.macDevice = macDevice;
        this.name = name;
        this.datetime = datetime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
