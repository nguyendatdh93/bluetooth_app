package com.infinity.EBacSens.model_objects;

public class Sensor {
    private String name;
    private boolean toggle;

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

    public Sensor(String name, boolean toggle) {
        this.name = name;
        this.toggle = toggle;
    }
}
