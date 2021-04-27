package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BacSetting {

    @SerializedName("bacname")
    @Expose
    private String bacName;

    @SerializedName("e1")
    @Expose
    private int e1;

    @SerializedName("e2")
    @Expose
    private int e2;

    @SerializedName("e3")
    @Expose
    private int e3;

    @SerializedName("e4")
    @Expose
    private int e4;

    @SerializedName("pkp")
    @Expose
    private int pkp;

    @SerializedName("sensor_setting_id")
    @Expose
    private int sensorSettingId;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("id")
    @Expose
    private int id;

    public String getBacName() {
        return bacName;
    }

    public void setBacName(String bacName) {
        this.bacName = bacName;
    }

    public int getE1() {
        return e1;
    }

    public void setE1(int e1) {
        this.e1 = e1;
    }

    public int getE2() {
        return e2;
    }

    public void setE2(int e2) {
        this.e2 = e2;
    }

    public int getE3() {
        return e3;
    }

    public void setE3(int e3) {
        this.e3 = e3;
    }

    public int getE4() {
        return e4;
    }

    public void setE4(int e4) {
        this.e4 = e4;
    }

    public int getPkp() {
        return pkp;
    }

    public void setPkp(int pkp) {
        this.pkp = pkp;
    }

    public int getSensorSettingId() {
        return sensorSettingId;
    }

    public void setSensorSettingId(int sensorSettingId) {
        this.sensorSettingId = sensorSettingId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BacSetting() {
    }

    public BacSetting(String bacName, int e1, int e2, int e3, int e4, int pkp) {
        this.bacName = bacName;
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
        this.e4 = e4;
        this.pkp = pkp;
    }
}
