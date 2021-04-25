package com.infinity.EBacSens.model_objects;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sensor implements Serializable {

    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("sensorID")
    @Expose
    private long sensorID;

    @SerializedName("setname")
    @Expose
    private String setname;

    @SerializedName("bacs")
    @Expose
    private long bacs;

    @SerializedName("crng")
    @Expose
    private long crng;

    @SerializedName("eqp1")
    @Expose
    private long eqp1;

    @SerializedName("eqt1")
    @Expose
    private long eqt1;

    @SerializedName("eqp2")
    @Expose
    private long eqp2;

    @SerializedName("eqt2")
    @Expose
    private long eqt2;

    @SerializedName("eqp3")
    @Expose
    private long eqp3;

    @SerializedName("eqt3")
    @Expose
    private long eqt3;

    @SerializedName("eqp4")
    @Expose
    private long eqp4;

    @SerializedName("eqt4")
    @Expose
    private long eqt4;

    @SerializedName("eqp5")
    @Expose
    private long eqp5;

    @SerializedName("eqt5")
    @Expose
    private long eqt5;

    @SerializedName("stp")
    @Expose
    private long stp;

    @SerializedName("enp")
    @Expose
    private long enp;

    @SerializedName("pp")
    @Expose
    private long pp;

    @SerializedName("dlte")
    @Expose
    private long dlte;

    @SerializedName("pwd")
    @Expose
    private long pwd;

    @SerializedName("ptm")
    @Expose
    private long ptm;

    @SerializedName("ibst")
    @Expose
    private long ibst;

    @SerializedName("iben")
    @Expose
    private long iben;

    @SerializedName("ifst")
    @Expose
    private long ifst;

    @SerializedName("ifen")
    @Expose
    private long ifen;

    @SerializedName("deletedAt")
    @Expose
    private String deletedAt;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSensorID() {
        return sensorID;
    }

    public void setSensorID(long sensorID) {
        this.sensorID = sensorID;
    }

    public String getSetname() {
        return setname;
    }

    public void setSetname(String setname) {
        this.setname = setname;
    }

    public long getBacs() {
        return bacs;
    }

    public void setBacs(long bacs) {
        this.bacs = bacs;
    }

    public long getCrng() {
        return crng;
    }

    public void setCrng(long crng) {
        this.crng = crng;
    }

    public long getEqp1() {
        return eqp1;
    }

    public void setEqp1(long eqp1) {
        this.eqp1 = eqp1;
    }

    public long getEqt1() {
        return eqt1;
    }

    public void setEqt1(long eqt1) {
        this.eqt1 = eqt1;
    }

    public long getEqp2() {
        return eqp2;
    }

    public void setEqp2(long eqp2) {
        this.eqp2 = eqp2;
    }

    public long getEqt2() {
        return eqt2;
    }

    public void setEqt2(long eqt2) {
        this.eqt2 = eqt2;
    }

    public long getEqp3() {
        return eqp3;
    }

    public void setEqp3(long eqp3) {
        this.eqp3 = eqp3;
    }

    public long getEqt3() {
        return eqt3;
    }

    public void setEqt3(long eqt3) {
        this.eqt3 = eqt3;
    }

    public long getEqp4() {
        return eqp4;
    }

    public void setEqp4(long eqp4) {
        this.eqp4 = eqp4;
    }

    public long getEqt4() {
        return eqt4;
    }

    public void setEqt4(long eqt4) {
        this.eqt4 = eqt4;
    }

    public long getEqp5() {
        return eqp5;
    }

    public void setEqp5(long eqp5) {
        this.eqp5 = eqp5;
    }

    public long getEqt5() {
        return eqt5;
    }

    public void setEqt5(long eqt5) {
        this.eqt5 = eqt5;
    }

    public long getStp() {
        return stp;
    }

    public void setStp(long stp) {
        this.stp = stp;
    }

    public long getEnp() {
        return enp;
    }

    public void setEnp(long enp) {
        this.enp = enp;
    }

    public long getPp() {
        return pp;
    }

    public void setPp(long pp) {
        this.pp = pp;
    }

    public long getDlte() {
        return dlte;
    }

    public void setDlte(long dlte) {
        this.dlte = dlte;
    }

    public long getPwd() {
        return pwd;
    }

    public void setPwd(long pwd) {
        this.pwd = pwd;
    }

    public long getPtm() {
        return ptm;
    }

    public void setPtm(long ptm) {
        this.ptm = ptm;
    }

    public long getIbst() {
        return ibst;
    }

    public void setIbst(long ibst) {
        this.ibst = ibst;
    }

    public long getIben() {
        return iben;
    }

    public void setIben(long iben) {
        this.iben = iben;
    }

    public long getIfst() {
        return ifst;
    }

    public void setIfst(long ifst) {
        this.ifst = ifst;
    }

    public long getIfen() {
        return ifen;
    }

    public void setIfen(long ifen) {
        this.ifen = ifen;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
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

    public Sensor(long id, long sensorID, String setname, long bacs, long crng, long eqp1, long eqt1, long eqp2, long eqt2, long eqp3, long eqt3, long eqp4, long eqt4, long eqp5, long eqt5, long stp, long enp, long pp, long dlte, long pwd, long ptm, long ibst, long iben, long ifst, long ifen, String deletedAt, String createdAt, String updatedAt) {
        this.id = id;
        this.sensorID = sensorID;
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
        this.deletedAt = deletedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
