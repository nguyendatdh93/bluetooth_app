package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeasureMeasparas {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("sensor_measure_id")
    @Expose
    private int sensorMeasureId;

    @SerializedName("rawdmp")
    @Expose
    private String rawdmp;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("casted_settings")
    @Expose
    private CastedSettings castedSettings;

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

    public String getRawdmp() {
        return rawdmp;
    }

    public void setRawdmp(String rawdmp) {
        this.rawdmp = rawdmp;
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

    public CastedSettings getCastedSettings() {
        return castedSettings;
    }

    public void setCastedSettings(CastedSettings castedSettings) {
        this.castedSettings = castedSettings;
    }

    public MeasureMeasparas(int id, int sensorMeasureId, String rawdmp, String createdAt, String updatedAt, CastedSettings castedSettings) {
        this.id = id;
        this.sensorMeasureId = sensorMeasureId;
        this.rawdmp = rawdmp;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.castedSettings = castedSettings;
    }

    public static class CastedSettings{

        @SerializedName("setname")
        @Expose
        private String setName;

        @SerializedName("bacs")
        @Expose
        private int bacs;

        @SerializedName("crng")
        @Expose
        private int crng;

        @SerializedName("eqp1")
        @Expose
        private int eqp1;

        public String getSetName() {
            return setName;
        }

        public void setSetName(String setName) {
            this.setName = setName;
        }

        public int getBacs() {
            return bacs;
        }

        public void setBacs(int bacs) {
            this.bacs = bacs;
        }

        public int getCrng() {
            return crng;
        }

        public void setCrng(int crng) {
            this.crng = crng;
        }

        public int getEqp1() {
            return eqp1;
        }

        public void setEqp1(int eqp1) {
            this.eqp1 = eqp1;
        }

        public CastedSettings(String setName, int bacs, int crng, int eqp1) {
            this.setName = setName;
            this.bacs = bacs;
            this.crng = crng;
            this.eqp1 = eqp1;
        }
    }
}
