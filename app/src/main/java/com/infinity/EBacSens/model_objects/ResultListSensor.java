package com.infinity.EBacSens.model_objects;

public class ResultListSensor {
    private String datetime;
    private String no;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public ResultListSensor(String datetime, String no) {
        this.datetime = datetime;
        this.no = no;
    }
}
