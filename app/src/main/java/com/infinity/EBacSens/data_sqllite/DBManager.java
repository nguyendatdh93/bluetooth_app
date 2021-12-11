package com.infinity.EBacSens.data_sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.BacSetting;
import com.infinity.EBacSens.model_objects.ItemSettingOffline;
import com.infinity.EBacSens.model_objects.MeasureMeasbas;
import com.infinity.EBacSens.model_objects.MeasureMeasdets;
import com.infinity.EBacSens.model_objects.MeasureMeasparas;
import com.infinity.EBacSens.model_objects.MeasureMeasress;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.model_objects.SensorMeasure;
import com.infinity.EBacSens.model_objects.SensorMeasureDetail;
import com.infinity.EBacSens.model_objects.SensorMeasurePage;
import com.infinity.EBacSens.model_objects.SettingOffline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DBeBacSens";

    private String[] arrCity, arrDistrict, arrSubDistrict;

    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + "devices" + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mac_address" + " TEXT, " +
                "name" + " TEXT " +
                ")");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + "setting_offlines" + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "object" + " TEXT " +
                ")");


        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + "measures" + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "mac_address" + " TEXT, " /* mac address */ +
                "datetime" + " TEXT, " +
                "no_" + " TEXT, " +
                "setname" + " TEXT, " +
                "bacs" + " INTEGER, " +
                "crng" + " INTEGER, " +
                "eqp1" + " INTEGER, " +
                "eqt1" + " INTEGER, " +
                "eqp2" + " INTEGER, " +
                "eqt2" + " INTEGER, " +
                "eqp3" + " INTEGER, " +
                "eqt3" + " INTEGER, " +
                "eqp4" + " INTEGER, " +
                "eqt4" + " INTEGER, " +
                "eqp5" + " INTEGER, " +
                "eqt5" + " INTEGER, " +
                "stp" + " INTEGER, " +
                "enp" + " INTEGER, " +
                "pp" + " INTEGER, " +
                "dlte" + " INTEGER, " +
                "pwd" + " INTEGER, " +
                "ptm" + " INTEGER, " +
                "ibst" + " INTEGER, " +
                "iben" + " INTEGER, " +
                "ifst" + " INTEGER, " +
                "ifen" + " INTEGER, " +
                "bac" + " TEXT, " +
                "datetime_p" + " TEXT, " +
                "pstaterr" + " INTEGER, " +
                "num" + " INTEGER, " +
                "measres" + " TEXT, " +
                "measdet" + " measdet " +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertSettingOffline() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        JSONArray jsonArrayMeasureRes = new JSONArray();

        JSONObject object = new JSONObject();
        try {
            object.put("dltc_from", 0);
            object.put("dltc_to", 0.1);
            object.put("quantity_from", 0);
            object.put("quantity_to", 10);
            object.put("level", 0);
            jsonArrayMeasureRes.put(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        object = new JSONObject();
        try {
            object.put("dltc_from", 0.1);
            object.put("dltc_to", 0.2);
            object.put("quantity_from", 10);
            object.put("quantity_to", 10);
            object.put("level", 1);
            jsonArrayMeasureRes.put(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        object = new JSONObject();
        try {
            object.put("dltc_from", 0.2);
            object.put("dltc_to", 0.3);
            object.put("quantity_from", 10);
            object.put("quantity_to", 100);
            object.put("level", 2);
            jsonArrayMeasureRes.put(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        object = new JSONObject();
        try {
            object.put("dltc_from", 0.3);
            object.put("dltc_to", 0.4);
            object.put("quantity_from", 100);
            object.put("quantity_to", 1000);
            object.put("level", 3);
            jsonArrayMeasureRes.put(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        object = new JSONObject();
        try {
            object.put("dltc_from", 0.4);
            object.put("dltc_to", 0.5);
            object.put("quantity_from", 1000);
            object.put("quantity_to", 10000);
            object.put("level", 4);
            jsonArrayMeasureRes.put(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        object = new JSONObject();
        try {
            object.put("dltc_from", 0.5);
            object.put("dltc_to", "");
            object.put("quantity_from", 10000);
            object.put("quantity_to", "");
            object.put("level", 5);
            jsonArrayMeasureRes.put(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        values.put("object",jsonArrayMeasureRes.toString());
        db.insert("setting_offlines", null, values);

        for(int i = 1 ; i < 5 ; i++){
            jsonArrayMeasureRes = new JSONArray();

            object = new JSONObject();
            try {
                object.put("dltc_from", 0);
                object.put("dltc_to", 1);
                object.put("quantity_from", 0);
                object.put("quantity_to", 10);
                object.put("level", 0);
                jsonArrayMeasureRes.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            object = new JSONObject();
            try {
                object.put("dltc_from", 1);
                object.put("dltc_to", 2);
                object.put("quantity_from", 10);
                object.put("quantity_to", 10);
                object.put("level", 1);
                jsonArrayMeasureRes.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            object = new JSONObject();
            try {
                object.put("dltc_from", 2);
                object.put("dltc_to", 3);
                object.put("quantity_from", 10);
                object.put("quantity_to", 100);
                object.put("level", 2);
                jsonArrayMeasureRes.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            object = new JSONObject();
            try {
                object.put("dltc_from", 3);
                object.put("dltc_to", 4);
                object.put("quantity_from", 100);
                object.put("quantity_to", 1000);
                object.put("level", 3);
                jsonArrayMeasureRes.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            object = new JSONObject();
            try {
                object.put("dltc_from", 4);
                object.put("dltc_to", 5);
                object.put("quantity_from", 1000);
                object.put("quantity_to", 10000);
                object.put("level", 4);
                jsonArrayMeasureRes.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            object = new JSONObject();
            try {
                object.put("dltc_from", 5);
                object.put("dltc_to", "");
                object.put("quantity_from", 10000);
                object.put("quantity_to", "");
                object.put("level", 5);
                jsonArrayMeasureRes.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            values.put("object",jsonArrayMeasureRes.toString());
            db.insert("setting_offlines", null, values);
        }
    }

    public void updateSettingOffline(ArrayList<SettingOffline> settingOfflines){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("setting_offlines" , "id!=?", new String[]{"-1"});
        for(int i = 0 ; i < settingOfflines.size() ; i++){
            ContentValues values = new ContentValues();
            JSONArray jsonArrayMeasureRes = new JSONArray();
            for(int j = 0 ; j < settingOfflines.get(i).getObject().size();j++){
                JSONObject object = new JSONObject();
                try {
                    object.put("dltc_from", settingOfflines.get(i).getObject().get(j).getDltc_from());
                    object.put("dltc_to", settingOfflines.get(i).getObject().get(j).getDltc_to());
                    object.put("quantity_from", settingOfflines.get(i).getObject().get(j).getQuantity_from());
                    object.put("quantity_to", settingOfflines.get(i).getObject().get(j).getQuantity_to());
                    object.put("level", settingOfflines.get(i).getObject().get(j).getLevel());
                    jsonArrayMeasureRes.put(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            values.put("object",jsonArrayMeasureRes.toString());
            db.insert("setting_offlines", null, values);
        }

    }

    public boolean isCreatedSettingOffline() {
        int counter = 0;
        String query = "SELECT * FROM setting_offlines";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                counter++;
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return counter > 0;
    }

    public void insertDevice(String macAddress, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mac_address", macAddress);
        values.put("name", name);
        db.insert("devices", null, values);
    }

    public void insertMeasures(String macAddress, String datetime, String no, MeasureMeasparas measureMeasparas, MeasureMeasbas measureMeasbas, ArrayList<MeasureMeasress> measureMeasresses, ArrayList<MeasureMeasdets> measureMeasdets){
        JSONArray jsonArrayBacMeasure = new JSONArray();
        JSONArray jsonArrayMeasureDet = new JSONArray();
        JSONArray jsonArrayMeasureRes = new JSONArray();

        for (int i = 0 ; i < measureMeasparas.getArrBac().size() ; i++){
            JSONObject object = new JSONObject();
            try {
                object.put("bacname", measureMeasparas.getArrBac().get(i).getBacName());
                object.put("e1", measureMeasparas.getArrBac().get(i).getE1());
                object.put("e2", measureMeasparas.getArrBac().get(i).getE2());
                object.put("e3", measureMeasparas.getArrBac().get(i).getE3());
                object.put("e4", measureMeasparas.getArrBac().get(i).getE4());
                object.put("id", measureMeasparas.getArrBac().get(i).getId());
                object.put("sensor_setting_id", measureMeasparas.getArrBac().get(i).getSensor_setting_id());
                object.put("created_at", measureMeasparas.getArrBac().get(i).getCreatedAt());
                jsonArrayBacMeasure.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0 ; i < measureMeasresses.size() ; i++){
            JSONObject object = new JSONObject();
            try {
                object.put("name", measureMeasresses.get(i).getName());
                object.put("pkpot", measureMeasresses.get(i).getPkpot());
                object.put("dltc", measureMeasresses.get(i).getDltc());
                object.put("bgc", measureMeasresses.get(i).getBgc());
                object.put("err", measureMeasresses.get(i).getErr());
                object.put("blpsx", measureMeasresses.get(i).getBlpsx());
                object.put("blpsy", measureMeasresses.get(i).getBlpsy());
                object.put("blpex", measureMeasresses.get(i).getBlpex());
                object.put("blpey", measureMeasresses.get(i).getBlpey());

                ArrayList<SettingOffline> settingOfflines = getSettingsOffline();
                object.put("number_organism", settingOfflines.get(i).getMeasresNumberOrganism(Protector.tryParseFloat(measureMeasresses.get(i).getDltc())));
                object.put("level", settingOfflines.get(i).getMeasresLevel(Protector.tryParseFloat(measureMeasresses.get(i).getDltc())));
                object.put("explain", "ピーク高さ／ピーク電位");

                jsonArrayMeasureRes.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0 ; i < measureMeasdets.size() ; i++){
            JSONObject object = new JSONObject();
            try {
                object.put("id", measureMeasdets.get(i).getId());
                object.put("no", measureMeasdets.get(i).getNo());
                object.put("deltae", measureMeasdets.get(i).getDeltae());
                object.put("deltai", measureMeasdets.get(i).getDeltai());
                object.put("eb", measureMeasdets.get(i).getEb());
                object.put("ib", measureMeasdets.get(i).getIb());
                object.put("ef", measureMeasdets.get(i).getEf());
                object.put("if", measureMeasdets.get(i).get_if());
                object.put("created_at", measureMeasdets.get(i).getCreatedAt());
                object.put("updated_at", measureMeasdets.get(i).getUpdatedAt());
                jsonArrayMeasureDet.put(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mac_address", macAddress);
        values.put("datetime", datetime);
        values.put("no_", no);
        values.put("setname", measureMeasparas.getSetname());
        values.put("bacs", measureMeasparas.getBacs());
        values.put("crng", measureMeasparas.getCrng());
        values.put("eqp1", measureMeasparas.getEqp1());
        values.put("eqt1", measureMeasparas.getEqt1());
        values.put("eqp2", measureMeasparas.getEqp2());
        values.put("eqt2", measureMeasparas.getEqt2());
        values.put("eqp3", measureMeasparas.getEqp3());
        values.put("eqt3", measureMeasparas.getEqt3());
        values.put("eqp4", measureMeasparas.getEqp4());
        values.put("eqt4", measureMeasparas.getEqt4());
        values.put("eqp5", measureMeasparas.getEqp5());
        values.put("eqt5", measureMeasparas.getEqt5());
        values.put("stp", measureMeasparas.getStp());
        values.put("enp", measureMeasparas.getEnp());
        values.put("pp", measureMeasparas.getPp());
        values.put("dlte", measureMeasparas.getDlte());
        values.put("pwd", measureMeasparas.getPwd());
        values.put("ptm", measureMeasparas.getPtm());
        values.put("ibst", measureMeasparas.getIbst());
        values.put("iben", measureMeasparas.getIben());
        values.put("ifst", measureMeasparas.getIfst());
        values.put("ifen", measureMeasparas.getIfen());
        values.put("bac", jsonArrayBacMeasure.toString());
        values.put("datetime_p", measureMeasbas.getDatetime());
        values.put("pstaterr", measureMeasbas.getPstaterr());
        values.put("num", measureMeasbas.getNum());
        values.put("measres", jsonArrayMeasureRes.toString());
        values.put("measdet", jsonArrayMeasureDet.toString());
        db.insert("measures", null, values);
    }

    public SensorMeasureDetail getMeasureDetail(int id) {
        SensorMeasureDetail sensorMeasureDetail = new SensorMeasureDetail();

        String query = "SELECT * FROM measures WHERE id = " + id + " ORDER BY id DESC";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MeasureMeasbas measureMeasbas = new MeasureMeasbas(
                        cursor.getInt(0),
                        cursor.getString(28),
                        cursor.getInt(30),
                        cursor.getInt(29),
                        "",
                        ""
                );


                Type typeMyType = new TypeToken<ArrayList<MeasureMeasress>>(){}.getType();
                ArrayList<MeasureMeasress> measureMeasresses = new Gson().fromJson(cursor.getString(31), typeMyType);

                typeMyType = new TypeToken<ArrayList<MeasureMeasdets>>(){}.getType();
                ArrayList<MeasureMeasdets> measureMeasdets = new Gson().fromJson(cursor.getString(32), typeMyType);

                typeMyType = new TypeToken<ArrayList<BacSetting>>(){}.getType();
                ArrayList<BacSetting> bacSettings = new Gson().fromJson(cursor.getString(27), typeMyType);

                //ArrayList<BacSetting> bacSettings = new Gson().fromJson(cursor.getString(27), ArrayList.class);
                MeasureMeasparas measureMeasparas = new MeasureMeasparas(
                        cursor.getInt(0),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getInt(6), //cr
                        cursor.getInt(7),
                        cursor.getInt(8),
                        cursor.getInt(9),
                        cursor.getInt(10),
                        cursor.getInt(11),
                        cursor.getInt(12),
                        cursor.getInt(13),
                        cursor.getInt(14),
                        cursor.getInt(15),
                        cursor.getInt(16),
                        cursor.getInt(17),
                        cursor.getInt(18),
                        cursor.getInt(19),
                        cursor.getInt(20),
                        cursor.getInt(21),
                        cursor.getInt(22),
                        cursor.getInt(23),
                        cursor.getInt(24),
                        cursor.getInt(25),
                        cursor.getInt(26),
                        "",
                        "",
                        bacSettings
                );

                sensorMeasureDetail.setSensorMeasure(new SensorMeasure(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        "",
                        "",
                        measureMeasbas,
                        measureMeasdets,
                        measureMeasparas,
                        measureMeasresses
                ));
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();

        return sensorMeasureDetail;
    }

    public ArrayList<SettingOffline> getSettingsOffline() {
        if(!isCreatedSettingOffline()){
            insertSettingOffline();
        }
        ArrayList<SettingOffline> settingOfflines = new ArrayList<>();

        String query = "SELECT * FROM setting_offlines";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Type typeMyType = new TypeToken<ArrayList<ItemSettingOffline>>(){}.getType();
                ArrayList<ItemSettingOffline> item = new Gson().fromJson(cursor.getString(1), typeMyType);

                SettingOffline settingOffline = new SettingOffline(cursor.getInt(0), item);
                settingOfflines.add(settingOffline);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return settingOfflines;
    }

    public ArrayList<SensorMeasurePage.MeasurePage> getMeasures() {
        ArrayList<SensorMeasurePage.MeasurePage> measures = new ArrayList<>();

        String query = "SELECT * FROM measures";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                measures.add(new SensorMeasurePage.MeasurePage(cursor.getInt(0),
                                0,
                                cursor.getString(2),
                                cursor.getString(3),
                                null,
                                null
                        )
                );
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return measures;
    }

    public ArrayList<SensorInfor> getDevice() {
        ArrayList<SensorInfor> campaigns = new ArrayList<>();

        String query = "SELECT * FROM devices";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                campaigns.add(new SensorInfor(cursor.getInt(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                0,
                                0,
                                null,
                                null,
                                null
                        )
                );
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return campaigns;
    }
}
