package com.infinity.EBacSens.model_objects;

import android.bluetooth.BluetoothDevice;

public class Sensor{
    private String name;
    private String address;
    private boolean toggle;
    private boolean selected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Sensor(String name, String address, boolean toggle, boolean selected) {
        this.name = name;
        this.address = address;
        this.toggle = toggle;
        this.selected = selected;
    }
}
