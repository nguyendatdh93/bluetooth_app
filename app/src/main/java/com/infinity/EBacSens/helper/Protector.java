package com.infinity.EBacSens.helper;

import android.content.Context;

import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.retrofit2.RetrofitClient;

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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" , Locale.US);
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

    public static ErrorSensorSetting parseErrorSensorSetting (Response<?> response){
        Converter<ResponseBody, ErrorSensorSetting> converter = RetrofitClient.retrofit.responseBodyConverter(ErrorSensorSetting.class , new Annotation[0]);
        ErrorSensorSetting errorResponse;
        try{
            errorResponse = converter.convert(response.errorBody());
        }catch (IOException e){
            return new ErrorSensorSetting();
        }
        return errorResponse;
    }

}
