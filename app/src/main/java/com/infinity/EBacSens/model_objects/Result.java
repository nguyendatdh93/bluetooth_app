package com.infinity.EBacSens.model_objects;

public class Result {
    private String name;
    private float a,b,c,d;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getB() {
        return b;
    }

    public void setB(float b) {
        this.b = b;
    }

    public float getC() {
        return c;
    }

    public void setC(float c) {
        this.c = c;
    }

    public float getD() {
        return d;
    }

    public void setD(float d) {
        this.d = d;
    }

    public Result(String name, float a, float b, float c, float d) {
        this.name = name;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
}
