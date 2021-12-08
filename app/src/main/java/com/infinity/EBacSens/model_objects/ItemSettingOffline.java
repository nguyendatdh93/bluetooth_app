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

    @SerializedName("quantity_from")
    @Expose
    private int quantity_from;

    @SerializedName("quantity_to")
    @Expose
    private int quantity_to;

    @SerializedName("level")
    @Expose
    private int level;

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

    public int getQuantity_from() {
        return quantity_from;
    }

    public void setQuantity_from(int quantity_from) {
        this.quantity_from = quantity_from;
    }

    public int getQuantity_to() {
        return quantity_to;
    }

    public void setQuantity_to(int quantity_to) {
        this.quantity_to = quantity_to;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ItemSettingOffline(double dltc_from, double dltc_to, int quantity_from, int quantity_to, int level) {
        this.dltc_from = dltc_from;
        this.dltc_to = dltc_to;
        this.quantity_from = quantity_from;
        this.quantity_to = quantity_to;
        this.level = level;
    }
}
