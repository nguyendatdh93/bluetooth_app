package com.infinity.EBacSens.model_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.infinity.EBacSens.helper.Protector;

import java.util.ArrayList;

public class SettingOffline {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("object")
    @Expose
    private ArrayList<ItemSettingOffline> object;

//    public int calculateLevel(double dltc){
//        int index = object.size()-1;
//        for(int i = 0 ; i < object.size()-2 ; i++){
//            if(dltc >= object.get(i).getDltc_from() && dltc <= object.get(i).getDltc_to()){
//                index = i;
//                break;
//            }
//        }
//
//        return object.get(index).getLevel();
//    }

//    public int getQuantity(double dltc){
//        int index = object.size()-1;
//        for(int i = 0 ; i < object.size()-2 ; i++){
//            if(dltc >= object.get(i).getDltc_from() && dltc <= object.get(i).getDltc_to()){
//                index = i;
//                break;
//            }
//        }
//
//        return object.get(index).getQuantity_from();
//    }

    public String getMeasresLevel(double dltc) {

        int index = -1;

        for (int i = 0; i < object.size(); i++) {
            if (dltc >= Protector.tryParseFloat(object.get(i).getDltc_from()) ) {
                if(object.get(i).getDltc_to().length() != 0 ){
                    if(dltc <= Protector.tryParseFloat(object.get(i).getDltc_to())){
                        index = i;
                        break;
                    }
                }else {
                    index = i;
                    break;
                }
            }
        }

        if (index == -1) {
            return "---";
        }

        return object.get(index).getLevel() + "";
    }

    public String getMeasresNumberOrganism(double dltc) {
//        if (object.size() == 0) {
//            return "---";
//        }
//
//        int index = object.size() - 1;
//        for (int i = 0; i < object.size() - 2; i++) {
//            if (dltc >= Protector.tryParseFloat(object.get(i).getDltc_from()) && dltc <= Protector.tryParseFloat(object.get(i).getDltc_to())){
//                index = i;
//                break;
//            }
//        }
//
//        if (object.size() == 1 || index == object.size()-1) {
//            return "≥" + object.get(index).getQuantity_to();
//        }
//        return object.get(index).getQuantity_from() + "〜" + object.get(index).getQuantity_to();


        int index = -1;

        for (int i = 0; i < object.size(); i++) {
            if (dltc >= Protector.tryParseFloat(object.get(i).getDltc_from()) ) {
                if(object.get(i).getDltc_to().length() != 0 ){
                    if(dltc <= Protector.tryParseFloat(object.get(i).getDltc_to())){
                        index = i;
                        break;
                    }
                }else {
                    index = i;
                    break;
                }
            }
        }

        if (index == -1) {
            return "---";
        }

        if(object.get(index).getQuantity_to().length() == 0){
            return "≥" + object.get(index).getQuantity_from();
        }else {
            return object.get(index).getQuantity_from() + "〜" + object.get(index).getQuantity_to();
        }

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
