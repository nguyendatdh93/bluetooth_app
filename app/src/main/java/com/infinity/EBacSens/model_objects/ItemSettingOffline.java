package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemSettingOffline {

    @SerializedName("dltc_from")
    @Expose
    private String dltc_from;

    @SerializedName("dltc_to")
    @Expose
    private String dltc_to;

    @SerializedName("quantity_from")
    @Expose
    private String quantity_from;

    @SerializedName("quantity_to")
    @Expose
    private String quantity_to;

    @SerializedName("level")
    @Expose
    private int level;

    public String getDltc_from() {
        return dltc_from;
    }

    public void setDltc_from(String dltc_from) {
        this.dltc_from = dltc_from;
    }

    public String getDltc_to() {
        return dltc_to;
    }

    public void setDltc_to(String dltc_to) {
        this.dltc_to = dltc_to;
    }

    public String getQuantity_from() {
        return quantity_from;
    }

    public void setQuantity_from(String quantity_from) {
        this.quantity_from = quantity_from;
    }

    public String getQuantity_to() {
        return quantity_to;
    }

    public void setQuantity_to(String quantity_to) {
        this.quantity_to = quantity_to;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ItemSettingOffline(String dltc_from, String dltc_to, String quantity_from, String quantity_to, int level) {
        this.dltc_from = dltc_from;
        this.dltc_to = dltc_to;
        this.quantity_from = quantity_from;
        this.quantity_to = quantity_to;
        this.level = level;
    }
}
