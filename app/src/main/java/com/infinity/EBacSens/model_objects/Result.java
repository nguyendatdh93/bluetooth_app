package com.infinity.EBacSens.model_objects;

public class Result {
    private String name;
    private String result;
    private String heightTop;
    private String bguA;
    private String error;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getHeightTop() {
        return heightTop;
    }

    public void setHeightTop(String heightTop) {
        this.heightTop = heightTop;
    }

    public String getBguA() {
        return bguA;
    }

    public void setBguA(String bguA) {
        this.bguA = bguA;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Result(String name, String result, String heightTop, String bguA, String error) {
        this.name = name;
        this.result = result;
        this.heightTop = heightTop;
        this.bguA = bguA;
        this.error = error;
    }
}
