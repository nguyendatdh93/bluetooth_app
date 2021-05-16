package com.infinity.EBacSens.helper;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.infinity.EBacSens.activitys.ListDeviceActivity;
import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.retrofit2.RetrofitClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
            return 0;
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
        ErrorSensorSetting errorResponse;
        try {
            errorResponse = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ErrorSensorSetting();
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
                    File.separator + "/eBacSens/log.txt");

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
}
