package com.infinity.EBacSens.data_sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.infinity.EBacSens.model_objects.SensorInfor;

import java.util.ArrayList;

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
//        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + "ContactCampaign" + " (" +
//                "number_phone" + " TEXT, " +
//                "id_campaign" + " integer , FOREIGN KEY (" + "id_campaign" + ") REFERENCES " + "Campaign" + "(" + "id" + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertDevice(String macAddress, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mac_address", macAddress);
        values.put("name", name);
        db.insert("devices", null, values);
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

    public void insertCampaign(int id, String name, String created_at, String time_send, int repeat, String content, int active) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("created_at", created_at);
        values.put("time_send", time_send);
        values.put("repeat", repeat);
        values.put("content", content);
        values.put("active", active);
        db.insert("Campaign", null, values);
    }

    public void insertContactCampaign(int id_campaign, String number_phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_campaign", id_campaign);
        values.put("number_phone", number_phone);

        db.insert("ContactCampaign", null, values);
    }

    public int getNumberCampaign() {
        int counter = 0;
        String query = "SELECT * FROM Campaign";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                counter++;
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return counter;
    }

//    public ArrayList<Campaign> getListCampaign() {
//        ArrayList<Campaign> campaigns = new ArrayList<>();
//
//        String query = "SELECT * FROM Campaign";
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
//        if (cursor.moveToFirst()) {
//            do {
//                campaigns.add(new Campaign(cursor.getInt(0),
//                        cursor.getString(1),
//                        cursor.getString(2),
//                        cursor.getString(3),
//                        cursor.getInt(4),
//                        cursor.getString(5),
//                        cursor.getInt(6)
//                        )
//                );
//            } while (cursor.moveToNext());
//        }
//        sqLiteDatabase.close();
//        return campaigns;
//    }

    public boolean removeCampaign(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Campaign", "id=" + id, null) > 0;
    }

    public ArrayList<String> getListContactCampaign(int idCampaign) {
        ArrayList<String> contactCampaigns = new ArrayList<>();

        String query = "SELECT ContactCampaign.number_phone FROM (Campaign INNER JOIN ContactCampaign ON Campaign.id = ContactCampaign.id_campaign) WHERE Campaign.id = " + idCampaign;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                contactCampaigns.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return contactCampaigns;
    }
}
