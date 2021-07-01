package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeasureMeasress {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("pkpot")
    @Expose
    private String pkpot;

    @SerializedName("dltc")
    @Expose
    private String dltc;

    @SerializedName("level")
    @Expose
    private String level;

    @SerializedName("number_organism")
    @Expose
    private String number_organism;

    @SerializedName("explain")
    @Expose
    private String explain;

    @SerializedName("bgc")
    @Expose
    private String bgc;

    @SerializedName("err")
    @Expose
    private String err;

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

    public MeasureMeasress() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPkpot() {
        return pkpot;
    }

    public void setPkpot(String pkpot) {
        this.pkpot = pkpot;
    }

    public String getDltc() {
        return dltc;
    }

    public void setDltc(String dltc) {
        this.dltc = dltc;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNumber_organism() {
        return number_organism;
    }

    public void setNumber_organism(String number_organism) {
        this.number_organism = number_organism;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getBgc() {
        return bgc;
    }

    public void setBgc(String bgc) {
        this.bgc = bgc;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
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

    public MeasureMeasress(int id, String name, String pkpot, String dltc, String bgc, String blpsx, String blpsy, String blpex, String blpey, String err, String createdAt, String updatedAt) {
        this.id = id;
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
