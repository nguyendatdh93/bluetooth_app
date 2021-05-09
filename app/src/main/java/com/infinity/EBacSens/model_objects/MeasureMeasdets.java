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
    private float deltae;

    @SerializedName("deltai")
    @Expose
    private float deltai;

    @SerializedName("eb")
    @Expose
    private float eb;

    @SerializedName("ib")
    @Expose
    private float ib;

    @SerializedName("ef")
    @Expose
    private float ef;

    @SerializedName("if")
    @Expose
    private float _if;

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

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public float getDeltae() {
        return deltae;
    }

    public void setDeltae(float deltae) {
        this.deltae = deltae;
    }

    public float getDeltai() {
        return deltai;
    }

    public void setDeltai(float deltai) {
        this.deltai = deltai;
    }

    public float getEb() {
        return eb;
    }

    public void setEb(float eb) {
        this.eb = eb;
    }

    public float getIb() {
        return ib;
    }

    public void setIb(float ib) {
        this.ib = ib;
    }

    public float getEf() {
        return ef;
    }

    public void setEf(float ef) {
        this.ef = ef;
    }

    public float get_if() {
        return _if;
    }

    public void set_if(float _if) {
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

    public String getCastedRawdmp() {
        return castedRawdmp;
    }

    public void setCastedRawdmp(String castedRawdmp) {
        this.castedRawdmp = castedRawdmp;
    }

    public MeasureMeasdets(int id, String no, float deltae, float deltai, float eb, float ib, float ef, float _if, String createdAt, String updatedAt, String castedRawdmp) {
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
        this.castedRawdmp = castedRawdmp;
    }
}
