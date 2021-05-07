package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ErrorSensorSetting {

    @SerializedName("errors")
    @Expose
    private ErrorSaveSetting errors;

    public ErrorSensorSetting() {
    }

    public ErrorSensorSetting(ErrorSaveSetting errors) {
        this.errors = errors;
    }

    public ErrorSaveSetting getErrors() {
        return errors;
    }

    public void setErrors(ErrorSaveSetting errors) {
        this.errors = errors;
    }
}
