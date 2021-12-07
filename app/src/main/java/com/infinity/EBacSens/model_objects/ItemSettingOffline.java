package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemSettingOffline {

    @SerializedName("dltc_from")
    @Expose
    private double dltc_from;

    @SerializedName("dltc_to")
    @Expose
    private double dltc_to;

    @SerializedName("quantity")
    @Expose
    private int quantity;

    @SerializedName("level")
    @Expose
    private int level;

    public ItemSettingOffline(double dltc_from, double dltc_to, int quantity, int level) {
        this.dltc_from = dltc_from;
        this.dltc_to = dltc_to;
        this.quantity = quantity;
        this.level = level;
    }

    public double getDltc_from() {
        return dltc_from;
    }

    public void setDltc_from(double dltc_from) {
        this.dltc_from = dltc_from;
    }

    public double getDltc_to() {
        return dltc_to;
    }

    public void setDltc_to(double dltc_to) {
        this.dltc_to = dltc_to;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
