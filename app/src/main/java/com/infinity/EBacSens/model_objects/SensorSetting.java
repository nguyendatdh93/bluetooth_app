package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SensorSetting implements Serializable {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("setname")
    @Expose
    private String setname;

    @SerializedName("bacs")
    @Expose
    private int bacs;

    @SerializedName("crng")
    @Expose
    private int crng;

    @SerializedName("eqp1")
    @Expose
    private int eqp1;

    @SerializedName("eqt1")
    @Expose
    private int eqt1;

    @SerializedName("eqp2")
    @Expose
    private int eqp2;

    @SerializedName("eqt2")
    @Expose
    private int eqt2;

    @SerializedName("eqp3")
    @Expose
    private int eqp3;

    @SerializedName("eqt3")
    @Expose
    private int eqt3;

    @SerializedName("eqp4")
    @Expose
    private int eqp4;

    @SerializedName("eqt4")
    @Expose
    private int eqt4;

    @SerializedName("eqp5")
    @Expose
    private int eqp5;

    @SerializedName("eqt5")
    @Expose
    private int eqt5;

    @SerializedName("stp")
    @Expose
    private int stp;

    @SerializedName("enp")
    @Expose
    private int enp;

    @SerializedName("pp")
    @Expose
    private int pp;

    @SerializedName("dlte")
    @Expose
    private int dlte;

    @SerializedName("pwd")
    @Expose
    private int pwd;

    @SerializedName("ptm")
    @Expose
    private int ptm;

    @SerializedName("ibst")
    @Expose
    private int ibst;

    @SerializedName("iben")
    @Expose
    private int iben;

    @SerializedName("ifst")
    @Expose
    private int ifst;

    @SerializedName("ifen")
    @Expose
    private int ifen;

    @SerializedName("sensor_id")
    @Expose
    private int sensorId;

    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("bac_settings")
    @Expose
    private List<BacSetting> bacSetting;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSetname() {
        return setname;
    }

    public void setSetname(String setname) {
        this.setname = setname;
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

    public int getEqt1() {
        return eqt1;
    }

    public void setEqt1(int eqt1) {
        this.eqt1 = eqt1;
    }

    public int getEqp2() {
        return eqp2;
    }

    public void setEqp2(int eqp2) {
        this.eqp2 = eqp2;
    }

    public int getEqt2() {
        return eqt2;
    }

    public void setEqt2(int eqt2) {
        this.eqt2 = eqt2;
    }

    public int getEqp3() {
        return eqp3;
    }

    public void setEqp3(int eqp3) {
        this.eqp3 = eqp3;
    }

    public int getEqt3() {
        return eqt3;
    }

    public void setEqt3(int eqt3) {
        this.eqt3 = eqt3;
    }

    public int getEqp4() {
        return eqp4;
    }

    public void setEqp4(int eqp4) {
        this.eqp4 = eqp4;
    }

    public int getEqt4() {
        return eqt4;
    }

    public void setEqt4(int eqt4) {
        this.eqt4 = eqt4;
    }

    public int getEqp5() {
        return eqp5;
    }

    public void setEqp5(int eqp5) {
        this.eqp5 = eqp5;
    }

    public int getEqt5() {
        return eqt5;
    }

    public void setEqt5(int eqt5) {
        this.eqt5 = eqt5;
    }

    public int getStp() {
        return stp;
    }

    public void setStp(int stp) {
        this.stp = stp;
    }

    public int getEnp() {
        return enp;
    }

    public void setEnp(int enp) {
        this.enp = enp;
    }

    public int getPp() {
        return pp;
    }

    public void setPp(int pp) {
        this.pp = pp;
    }

    public int getDlte() {
        return dlte;
    }

    public void setDlte(int dlte) {
        this.dlte = dlte;
    }

    public int getPwd() {
        return pwd;
    }

    public void setPwd(int pwd) {
        this.pwd = pwd;
    }

    public int getPtm() {
        return ptm;
    }

    public void setPtm(int ptm) {
        this.ptm = ptm;
    }

    public int getIbst() {
        return ibst;
    }

    public void setIbst(int ibst) {
        this.ibst = ibst;
    }

    public int getIben() {
        return iben;
    }

    public void setIben(int iben) {
        this.iben = iben;
    }

    public int getIfst() {
        return ifst;
    }

    public void setIfst(int ifst) {
        this.ifst = ifst;
    }

    public int getIfen() {
        return ifen;
    }

    public void setIfen(int ifen) {
        this.ifen = ifen;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
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

    public List<BacSetting> getBacSetting() {
        return bacSetting;
    }

    public void setBacSetting(List<BacSetting> bacSetting) {
        this.bacSetting = bacSetting;
    }

    public SensorSetting() {
    }

    public SensorSetting(int id, String setname, int bacs, int crng, int eqp1, int eqt1, int eqp2, int eqt2, int eqp3, int eqt3, int eqp4, int eqt4, int eqp5, int eqt5, int stp, int enp, int pp, int dlte, int pwd, int ptm, int ibst, int iben, int ifst, int ifen, int sensorId, String deletedAt, String updatedAt, String createdAt, List<BacSetting> bacSetting) {
        this.id = id;
        this.setname = setname;
        this.bacs = bacs;
        this.crng = crng;
        this.eqp1 = eqp1;
        this.eqt1 = eqt1;
        this.eqp2 = eqp2;
        this.eqt2 = eqt2;
        this.eqp3 = eqp3;
        this.eqt3 = eqt3;
        this.eqp4 = eqp4;
        this.eqt4 = eqt4;
        this.eqp5 = eqp5;
        this.eqt5 = eqt5;
        this.stp = stp;
        this.enp = enp;
        this.pp = pp;
        this.dlte = dlte;
        this.pwd = pwd;
        this.ptm = ptm;
        this.ibst = ibst;
        this.iben = iben;
        this.ifst = ifst;
        this.ifen = ifen;
        this.sensorId = sensorId;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.bacSetting = bacSetting;
    }
}
