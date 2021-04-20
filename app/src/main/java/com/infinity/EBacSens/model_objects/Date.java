package com.infinity.EBacSens.model_objects;

public class Date {
    private String date;
    private boolean selected;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Date(String date, boolean selected) {
        this.date = date;
        this.selected = selected;
    }
}
