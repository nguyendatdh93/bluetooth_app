package com.infinity.EBacSens.data_sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.infinity.EBacSens.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DBHaiPhongComputer";

    private String[] arrCity , arrDistrict , arrSubDistrict;

    private Context context;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + "City" + " ("+
                "idCity" +" integer primary key, " +
                "name" +" TEXT )");


        ContentValues values = new ContentValues();
        arrCity = context.getResources().getStringArray(R.array.arrCity);
        for (String s : arrCity) {
            JSONObject messageJson;
            try {
                messageJson = new JSONObject(s);
                int idCity = messageJson.getInt("idCity");
                String name = messageJson.getString("name");
                values.put("idCity", idCity);
                values.put("name", name);
                sqLiteDatabase.insert("City", null, values);
                values.clear();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (String s : arrDistrict) {
            JSONObject messageJson;
            try {
                messageJson = new JSONObject(s);
                int idDistric = messageJson.getInt("idDistrict");
                String name = messageJson.getString("name");
                int idCity = messageJson.getInt("idCity");
                values.put("idDistrict", idDistric);
                values.put("name", name);
                values.put("idCity", idCity);
                sqLiteDatabase.insert("District", null, values);
                values.clear();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (String s : arrSubDistrict) {
            JSONObject messageJson;
            try {
                messageJson = new JSONObject(s);
                int idSubDistric = messageJson.getInt("idSubDistrict");
                String name = messageJson.getString("name");
                int idDistrict = messageJson.getInt("idDistrict");
                values.put("idSubDistrict", idSubDistric);
                values.put("name", name);
                values.put("idDistrict", idDistrict);
                sqLiteDatabase.insert("SubDistrict", null, values);
                values.clear();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

//    public ArrayList<City> getListCity(){
//        ArrayList<City> cityList = new ArrayList<>();
//
//        String query = "SELECT * FROM City";
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery(query , null);
//        if (cursor.moveToFirst()){
//            do {
//                City city = new City();
//                city.setId(cursor.getInt(0));
//                city.setName(cursor.getString(1));
//                cityList.add(city);
//            }while (cursor.moveToNext());
//        }
//        sqLiteDatabase.close();
//        return cityList;
//    }
//
//    public ArrayList<City> getFindCity(String cityName){
//        ArrayList<City> cityList = new ArrayList<>();
//
//        String query = "SELECT * FROM City WHERE name like '%"+cityName+"%'";
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery(query , null);
//        if (cursor.moveToFirst()){
//            do {
//                City city = new City();
//                city.setId(cursor.getInt(0));
//                city.setName(cursor.getString(1));
//                cityList.add(city);
//            }while (cursor.moveToNext());
//        }
//        sqLiteDatabase.close();
//        return cityList;
//    }
}
