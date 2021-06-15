package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeZone {

    @SerializedName("year")
    @Expose
    private int year;

    @SerializedName("month")
    @Expose
    private int month;

    @SerializedName("day")
    @Expose
    private int day;

    @SerializedName("hour")
    @Expose
    private int hour;

    @SerializedName("minute")
    @Expose
    private int minute;

    @SerializedName("second")
    @Expose
    private int second;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public TimeZone(int year, int month, int day, int hour, int minute, int second) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
}
