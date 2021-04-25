package com.infinity.EBacSens.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Protector {
    public static String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" , Locale.US);
        Date date = new Date();
        return dateFormat.format(date);
    }
}
