package com.infinity.EBacSens.model_objects;

public class ResultListSensor {
    private String datetime;
    private int no;

    public ResultListSensor(String datetime, int no) {
        this.datetime = datetime;
        this.no = no;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }
}
