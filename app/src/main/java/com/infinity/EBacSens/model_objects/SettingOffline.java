package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SettingOffline {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("object")
    @Expose
    private ArrayList<ItemSettingOffline> object;

    public int calculateLevel(double dltc){
        int index = object.size()-1;
        for(int i = 0 ; i < object.size()-2 ; i++){
            if(dltc >= object.get(i).getDltc_from() && dltc <= object.get(i).getDltc_to()){
                index = i;
                break;
            }
        }

        return object.get(index).getLevel();
    }

    public int getQuantity(double dltc){
        int index = object.size()-1;
        for(int i = 0 ; i < object.size()-2 ; i++){
            if(dltc >= object.get(i).getDltc_from() && dltc <= object.get(i).getDltc_to()){
                index = i;
                break;
            }
        }

        return object.get(index).getQuantity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<ItemSettingOffline> getObject() {
        return object;
    }

    public void setObject(ArrayList<ItemSettingOffline> object) {
        this.object = object;
    }

    public SettingOffline(int id, ArrayList<ItemSettingOffline> object) {
        this.id = id;
        this.object = object;
    }

}
