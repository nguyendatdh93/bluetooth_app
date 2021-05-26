package com.infinity.EBacSens.helper;

import android.os.Environment;

import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.retrofit2.RetrofitClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class Protector {
    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return tryParseHex(value);
        }
    }

    public static int tryParseHex(String value) {
        try {
            return Integer.parseInt(value , 16);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static ErrorSensorSetting parseErrorSensorSetting(Response<?> response) {
        Converter<ResponseBody, ErrorSensorSetting> converter = RetrofitClient.retrofit.responseBodyConverter(ErrorSensorSetting.class, new Annotation[0]);
        ErrorSensorSetting errorResponse = null;
        if (response.errorBody() != null){
            try {
                errorResponse = converter.convert(response.errorBody());
            } catch (IOException e) {
                return new ErrorSensorSetting();
            }
        }
        return errorResponse;
    }

    public static void appendLog(String text) {
        if (text != null){
            text = getCurrentTime() + " " + text;
            File folder = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "/eBacSens");
            boolean success;
            if (!folder.exists()) {
                success = folder.mkdirs();
            }

            File logFile = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "/eBacSens/logTest.txt");

            if (!logFile.exists()) {
                try {
                    boolean sucess = logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                //BufferedWriter for performance, true to set append to file flag
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(text);
                buf.newLine();
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void appendLogSensor(String text) {
        if (text != null){
            text = getCurrentTime() + " " + text;
            File folder = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "/eBacSens");
            boolean success;
            if (!folder.exists()) {
                success = folder.mkdirs();
            }

            File logFile = new File(Environment.getExternalStorageDirectory() +
                    File.separator + "/eBacSens/LogSensor.txt");

            if (!logFile.exists()) {
                try {
                    boolean sucess = logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                //BufferedWriter for performance, true to set append to file flag
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(text);
                buf.newLine();
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String convertTimeZone(String time){
        String t1 = time.substring(0,10);
        String t2 = time.substring(11,19);
        return t1+" "+t2;
    }

    public static String formatTimeSensor(String value){
        if (value != null && value.length() > 13){
            return value.substring(0,4) + "-" + value.substring(4,6) + "-" + value.substring(6,8) + " " + value.substring(8,10) + ":" + value.substring(10,12) + ":" + value.substring(12,14);
        }
        return value;
    }


}
