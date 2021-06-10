package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MeasureMeasdets {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("no")
    @Expose
    private String no;

    @SerializedName("deltae")
    @Expose
    private double deltae;

    @SerializedName("deltai")
    @Expose
    private double deltai;

    @SerializedName("eb")
    @Expose
    private double eb;

    @SerializedName("ib")
    @Expose
    private double ib;

    @SerializedName("ef")
    @Expose
    private double ef;

    @SerializedName("if")
    @Expose
    private double _if;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public MeasureMeasdets() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public double getDeltae() {
        return deltae;
    }

    public void setDeltae(double deltae) {
        this.deltae = deltae;
    }

    public double getDeltai() {
        return deltai;
    }

    public void setDeltai(double deltai) {
        this.deltai = deltai;
    }

    public double getEb() {
        return eb;
    }

    public void setEb(double eb) {
        this.eb = eb;
    }

    public double getIb() {
        return ib;
    }

    public void setIb(double ib) {
        this.ib = ib;
    }

    public double getEf() {
        return ef;
    }

    public void setEf(double ef) {
        this.ef = ef;
    }

    public double get_if() {
        return _if;
    }

    public void set_if(double _if) {
        this._if = _if;
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

    public MeasureMeasdets(int id, String no, double deltae, double deltai, double eb, double ib, double ef, double _if, String createdAt, String updatedAt) {
        this.id = id;
        this.no = no;
        this.deltae = deltae;
        this.deltai = deltai;
        this.eb = eb;
        this.ib = ib;
        this.ef = ef;
        this._if = _if;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
