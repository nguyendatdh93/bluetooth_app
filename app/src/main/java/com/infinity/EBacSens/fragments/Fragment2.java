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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.activitys.MainActivity;
import com.infinity.EBacSens.helper.Protector;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Fragment2 extends Fragment {

    private View view;
    private Activity activity;
    private Context context;

    private EditText edtDatetime , edtPowerOffMin , edtPeakMode;
    private Button btnRead , btnWrite;
    private AutoCompleteTextView acpNameMeasure;
    private String[] arrNameMeasure;
    private ArrayAdapter<String> adapterNameMeasure;

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

        acpNameMeasure.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setStatusButton();
            }
        });

        btnWrite.setOnClickListener(v -> {

        });
    }

    private void setStatusButton(){
        if (acpNameMeasure.getText().toString().contains("R")){
            btnRead.setEnabled(true);
            btnRead.setAlpha(1f);
        }else {
            btnRead.setEnabled(false);
            btnRead.setAlpha(0.5f);
        }

        if (acpNameMeasure.getText().toString().contains("W")){
            btnWrite.setEnabled(true);
            btnWrite.setAlpha(1f);
        }else {
            btnWrite.setEnabled(false);
            btnWrite.setAlpha(0.5f);
        }
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
        acpNameMeasure = view.findViewById(R.id.fragment_2_acp_name_measure);
        edtPowerOffMin = view.findViewById(R.id.fragment_2_edt_power_off_min);
        edtPeakMode = view.findViewById(R.id.fragment_2_edt_peakmode);
        btnWrite = view.findViewById(R.id.fragment_2_btn_write);
        btnRead = view.findViewById(R.id.fragment_2_btn_read);

        if (MainActivity.device != null){
            edtPowerOffMin.setText(String.valueOf(MainActivity.device.getPowoffmin()));
            edtPeakMode.setText(String.valueOf(MainActivity.device.getPowoffmin()));
        }
        arrNameMeasure = context.getResources().getStringArray(R.array.arrNameMeasure);
        if (arrNameMeasure!= null && arrNameMeasure.length > 1){
            acpNameMeasure.setText(arrNameMeasure[0]);
        }
        adapterNameMeasure = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrNameMeasure);
        acpNameMeasure.setAdapter(adapterNameMeasure);
        setStatusButton();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (Activity) context;
    }

}

