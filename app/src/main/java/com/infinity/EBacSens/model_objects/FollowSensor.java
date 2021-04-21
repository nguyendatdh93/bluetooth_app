package com.infinity.EBacSens.model_objects;

public class FollowSensor {
    private boolean toggle;
    private boolean selected;

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

    public FollowSensor(boolean toggle, boolean selected) {
        this.toggle = toggle;
        this.selected = selected;
    }
}
