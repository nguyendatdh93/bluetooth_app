package com.infinity.EBacSens.model_objects;

public class ModelRCVDatetime {
    private String datetime;
    private boolean selected;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public ModelRCVDatetime(String datetime, boolean selected) {
        this.datetime = datetime;
        this.selected = selected;
    }
}
