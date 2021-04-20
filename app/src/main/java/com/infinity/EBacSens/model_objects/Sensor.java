package com.infinity.EBacSens.model_objects;

public class Sensor {
    private String name;
    private boolean toggle;
    private boolean selected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Sensor(String name, boolean toggle, boolean selected) {
        this.name = name;
        this.toggle = toggle;
        this.selected = selected;
    }
}
