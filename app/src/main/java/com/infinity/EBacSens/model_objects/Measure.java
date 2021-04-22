package com.infinity.EBacSens.model_objects;

public class Measure {
    private String name;
    private String datetime;
    private String result;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Measure(String name, String datetime, String result) {
        this.name = name;
        this.datetime = datetime;
        this.result = result;
    }
}
