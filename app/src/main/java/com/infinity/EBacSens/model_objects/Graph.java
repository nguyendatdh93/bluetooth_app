package com.infinity.EBacSens.model_objects;

public class Graph {

    private String name;
    private String result;
    private String level;
    private String description;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Graph(String name, String result, String level, String description) {
        this.name = name;
        this.result = result;
        this.level = level;
        this.description = description;
    }
}
