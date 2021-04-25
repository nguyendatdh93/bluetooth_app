package com.infinity.EBacSens.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.helper.Protector;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Fragment2 extends Fragment {

    private View view;
    private Activity activity;
    private Context context;

    private EditText edtDatetime;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_2, container, false);
        addController();
        addEvents();
        return view;
    }

    private void addEvents() {
        edtDatetime.setText(Protector.getCurrentTime());
        edtDatetime.setOnClickListener(v -> showDateTimeDialog(edtDatetime));
    }

    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener= (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

            TimePickerDialog.OnTimeSetListener timeSetListener= (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" , Locale.getDefault());
                date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
            };

            new TimePickerDialog(context,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
        };

        new DatePickerDialog(context,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void addController() {

        edtDatetime = view.findViewById(R.id.fragment_2_edt_datetime);

//        TimeZone tz = TimeZone.getTimeZone("GMT+05:30");
//        Calendar c = Calendar.getInstance(tz);
//        String time = String.format("%02d" , c.get(Calendar.HOUR_OF_DAY))+":"+
//                String.format("%02d" , c.get(Calendar.MINUTE))+":"+
//                   String.format("%02d" , c.get(Calendar.SECOND))+":"+
//               String.format("%03d" , c.get(Calendar.MILLISECOND));
//
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (Activity) context;
    }

}

