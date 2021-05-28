package com.infinity.EBacSens.model_objects;

public class Graph {

    private String name;
    private float result;
    private int level;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Graph(String name, float result, int level, String description) {
        this.name = name;
        this.result = result;
        this.level = level;
        this.description = description;
    }
}
