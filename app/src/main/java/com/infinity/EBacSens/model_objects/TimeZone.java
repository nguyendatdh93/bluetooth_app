package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeZone {

    @SerializedName("dayofweek")
    @Expose
    private int dayofweek;

    @SerializedName("dayofweekName")
    @Expose
    private String dayofweekName;

    @SerializedName("day")
    @Expose
    private int day;

    @SerializedName("month")
    @Expose
    private int month;

    @SerializedName("monthName")
    @Expose
    private String monthName;

    @SerializedName("year")
    @Expose
    private int year;

    @SerializedName("hours")
    @Expose
    private int hours;

    @SerializedName("minutes")
    @Expose
    private int minutes;

    @SerializedName("seconds")
    @Expose
    private int seconds;

    @SerializedName("millis")
    @Expose
    private int millis;

    @SerializedName("fulldate")
    @Expose
    private String fulldate;

    @SerializedName("timezone")
    @Expose
    private String timezone;

    @SerializedName("status")
    @Expose
    private String status;

    public int getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(int dayofweek) {
        this.dayofweek = dayofweek;
    }

    public String getDayofweekName() {
        return dayofweekName;
    }

    public void setDayofweekName(String dayofweekName) {
        this.dayofweekName = dayofweekName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getMillis() {
        return millis;
    }

    public void setMillis(int millis) {
        this.millis = millis;
    }

    public String getFulldate() {
        return fulldate;
    }

    public void setFulldate(String fulldate) {
        this.fulldate = fulldate;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TimeZone(int dayofweek, String dayofweekName, int day, int month, String monthName, int year, int hours, int minutes, int seconds, int millis, String fulldate, String timezone, String status) {
        this.dayofweek = dayofweek;
        this.dayofweekName = dayofweekName;
        this.day = day;
        this.month = month;
        this.monthName = monthName;
        this.year = year;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.millis = millis;
        this.fulldate = fulldate;
        this.timezone = timezone;
        this.status = status;
    }
}
