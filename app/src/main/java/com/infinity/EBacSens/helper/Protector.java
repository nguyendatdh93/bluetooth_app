package com.infinity.EBacSens.helper;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.util.Log;

import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.retrofit2.RetrofitClient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

import static android.os.storage.StorageManager.ACTION_MANAGE_STORAGE;

public class Protector {

    public static boolean STATUS_NETWORK = true;

    public static String getCurrentTimeSensor() {
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }

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

    public static float tryParseFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return tryParseHex(value);
        }
    }

    public static int tryParseHex(String value) {
        try {
            return Integer.valueOf(value, 16).shortValue();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static ErrorSensorSetting parseErrorSensorSetting(Response<?> response) {
        Converter<ResponseBody, ErrorSensorSetting> converter = RetrofitClient.retrofit.responseBodyConverter(ErrorSensorSetting.class, new Annotation[0]);
        ErrorSensorSetting errorResponse = null;
        if (response.errorBody() != null) {
            try {
                errorResponse = converter.convert(response.errorBody());
            } catch (IOException e) {
                return new ErrorSensorSetting();
            }
        }
        return errorResponse;
    }

//    public static void appendLog(boolean isReceive, String text) {
//        if (text != null) {
//            if (isReceive) {
//                text = getCurrentTime() + " Received: " + text;
//            } else {
//                text = getCurrentTime() + " Sent: " + text;
//            }
//
//            File folder = new File(Environment.getExternalStorageDirectory() +
//                    File.separator + "/eBacSens");
//            boolean success;
//            if (!folder.exists()) {
//                success = folder.mkdirs();
//            }
//
//            File logFile = new File(Environment.getExternalStorageDirectory() +
//                    File.separator + "/eBacSens/log.txt");
//
//            if (!logFile.exists()) {
//                try {
//                    boolean sucess = logFile.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            try {
//                //BufferedWriter for performance, true to set append to file flag
//                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
//                buf.append(text);
//                buf.newLine();
//                buf.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void appendLog(Context context, boolean isReceive, String text) {
        if (text != null) {
            if (isReceive) {
                text = getCurrentTime() + " Received: " + text;
            } else {
                text = getCurrentTime() + " Sent: " + text;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                ContentResolver resolver = context.getContentResolver();
                ContentValues values = new ContentValues();

                values.put(MediaStore.MediaColumns.DISPLAY_NAME, "log.txt");
                values.put(MediaStore.MediaColumns.MIME_TYPE, "text/txt");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + "/eBacSens");
                try {
                    Uri uri = resolver.insert(MediaStore.Files.getContentUri("external"), values);

                    File file = new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DOCUMENTS + "/" + "eBacSens/log.txt");
                    if (!file.exists()) {
                        try {
                            resolver.openOutputStream(uri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        //BufferedWriter for performance, true to set append to file flag
                        BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
                        buf.append(text);
                        buf.newLine();
                        buf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
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
    }

    public static String formatTimeSensor(String value) {
        if (value != null && value.length() >= 12) {
            return value.substring(0, 2) + "-" + value.substring(2, 4) + "-" + value.substring(4, 6) + " " + value.substring(6, 8) + ":" + value.substring(8, 10) + ":" + value.substring(10, 12);
        }
        return value;
    }

    public static String convertTimeZone(String time) {
        String t1 = time.substring(0, 10);
        String t2 = time.substring(11, 19);
        return t1 + " " + t2;
    }

    public static int HexToDecDataMeasdet(String value) {
        return Integer.valueOf(value, 16).shortValue();
    }

    public static String intToHex(int value) {
        String SS = String.format("%08X", value);
        return SS.substring(SS.length() - 4);
    }
}
