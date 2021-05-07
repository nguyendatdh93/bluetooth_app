package com.infinity.EBacSens.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.ParcelUuid;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.activitys.MainActivity;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.task.ConnectThread;
import com.infinity.EBacSens.views.ViewConnectThread;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import static com.infinity.EBacSens.activitys.MainActivity.mBluetoothAdapter;
import static com.infinity.EBacSens.retrofit2.APIUtils.PBAP_UUID;

public class Fragment2 extends Fragment implements ViewConnectThread {

    private View view;
    private Activity activity;
    private Context context;
    private RelativeLayout container;

    private EditText edtNameMeasure , edtDatetime , edtPowerOffMin , edtPeakMode;
    private Button btnRead , btnWrite;

    private Dialog dialogProcessing;
    private TextView txtDialogProcessingTitle;

    private ConnectThread connectThread;

    // 1 = write , 0 = read
    int statusButton = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_2, container, false);
        addController();
        addEvents();
        return view;
    }

    private void connectSensor() {
        if (MainActivity.device.getMacDevice() != null && mBluetoothAdapter != null) {
            try {
                if (connectThread != null){
                    connectThread.cancel();
                }
                connectThread = new ConnectThread(mBluetoothAdapter.getRemoteDevice(MainActivity.device.getMacDevice()).createInsecureRfcommSocketToServiceRecord(ParcelUuid.fromString(PBAP_UUID).getUuid()), this);
                showDialogProcessing();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        if (connectThread != null) {
                            connectThread.connect();
                        }
                    }
                };
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addEvents() {
        edtDatetime.setText(Protector.getCurrentTime());
        edtDatetime.setOnClickListener(v -> showDateTimeDialog(edtDatetime));

        btnWrite.setOnClickListener(v -> {
            statusButton = 1;
            if (mBluetoothAdapter != null) {
                connectSensor();
            }else {
                showErrorMessage("Device not support Bluetooth");
            }
        });
        btnRead.setOnClickListener(v -> {
            statusButton = 0;
            if (mBluetoothAdapter != null) {
                connectSensor();
            }else {
                showErrorMessage("Device not support Bluetooth");
            }
        });
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
        container = view.findViewById(R.id.container_fragment_2);
        edtDatetime = view.findViewById(R.id.fragment_2_edt_datetime);
        edtNameMeasure = view.findViewById(R.id.fragment_2_edt_name_measure);
        edtPowerOffMin = view.findViewById(R.id.fragment_2_edt_power_off_min);
        edtPeakMode = view.findViewById(R.id.fragment_2_edt_peakmode);
        btnWrite = view.findViewById(R.id.fragment_2_btn_write);
        btnRead = view.findViewById(R.id.fragment_2_btn_read);

        if (MainActivity.device != null){
            edtPowerOffMin.setText(String.valueOf(MainActivity.device.getPowoffmin()));
            edtPeakMode.setText(String.valueOf(MainActivity.device.getPowoffmin()));
        }

        initDialogProcessing();
    }

    private void initDialogProcessing() {
        dialogProcessing = new Dialog(context);
        dialogProcessing.setContentView(R.layout.dialog_processing);
        dialogProcessing.setCancelable(false);
        txtDialogProcessingTitle = dialogProcessing.findViewById(R.id.dialog_processing_txt_title);
    }

    private void showDialogProcessing() {
        dialogProcessing.show();
    }

    private void cancelDialogProcessing() {
        if (dialogProcessing != null) {
            dialogProcessing.cancel();
        }
    }

    private void showErrorMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(container, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.RED);
        TextView textView = view.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    public void onGetData(String value) {
        // get data from sensor
        Log.e("Connection", value != null ? value : "null");
    }

    @Override
    public void onConnected() {
        cancelDialogProcessing();
        if (connectThread != null) {
            connectThread.run();
        }
        MainActivity.device.setStatusConnect(1);
    }

    @Override
    public void onError(String error) {
        cancelDialogProcessing();
        showErrorMessage(error);
    }

    @Override
    public void onRuned() {
        cancelDialogProcessing();

        if (connectThread != null) {

            connectThread.write("*" + (statusButton == 1 ? "R" : 0 ) + ","+ edtNameMeasure.getText().toString());

            // test result
            edtNameMeasure.setText("Name ex response");
            edtPeakMode.setText(String.valueOf(new Random().nextInt(90) + 10));
            edtPowerOffMin.setText(String.valueOf(new Random().nextInt(60)));
        }
    }
}

