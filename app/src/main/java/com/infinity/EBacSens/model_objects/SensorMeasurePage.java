package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SensorMeasurePage {

    @SerializedName("data")
    @Expose
    private ArrayList<MeasurePage> measurePages;

    public ArrayList<MeasurePage> getMeasurePages() {
        return measurePages;
    }

    public void setMeasurePages(ArrayList<MeasurePage> measurePages) {
        this.measurePages = measurePages;
    }

    public SensorMeasurePage(ArrayList<MeasurePage> measurePages) {
        this.measurePages = measurePages;
    }

    public static class MeasurePage{
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

        public MeasurePage(int id, int sensorId, String datetime, String no, String createdAt, String updatedAt) {
            this.id = id;
            this.sensorId = sensorId;
            this.datetime = datetime;
            this.no = no;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
    }
}
