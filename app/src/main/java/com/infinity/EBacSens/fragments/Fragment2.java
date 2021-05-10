package com.infinity.EBacSens.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

import static com.infinity.EBacSens.activitys.MainActivity.STATE_CONNECTED;
import static com.infinity.EBacSens.activitys.MainActivity.STATE_DISCONNECTED;
import static com.infinity.EBacSens.activitys.MainActivity.STATE_LISTENING;
import static com.infinity.EBacSens.activitys.MainActivity.mBluetoothAdapter;
import static com.infinity.EBacSens.retrofit2.APIUtils.PBAP_UUID;

public class Fragment2 extends Fragment implements ViewConnectThread  , Handler.Callback{

    private View view;
    private Activity activity;
    private Context context;
    private RelativeLayout container;

    private EditText edtNameMeasure , edtDatetime , edtPowerOffMin , edtPeakMode;
    private Button btnRead , btnWrite;

    private Dialog dialogProcessing;
    private TextView txtDialogProcessingTitle , txtDatetime;

    private ConnectThread connectThread;

    // 1 = write , 0 = read
    private int statusButton = 0;
    private Handler handler;
    private int countTryConnect = 0;
    private final int maxTryConnect = 3;

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
        }else {
            showErrorMessage("Device not have mac address");
        }
    }

    private void addEvents() {
        btnWrite.setOnClickListener(v -> {
            if (edtNameMeasure.getText().toString().length() == 0){
                edtNameMeasure.setError("error");
                edtNameMeasure.requestFocus();
                return;
            }
            if (edtPeakMode.getText().toString().length() == 0 || Protector.tryParseInt(edtPeakMode.getText().toString()) > 2 || Protector.tryParseInt(edtPeakMode.getText().toString()) < 0){
                edtPeakMode.setError("error");
                edtPeakMode.requestFocus();
                return;
            }
            if (Protector.tryParseInt(edtPowerOffMin.getText().toString()) > 60){
                edtPowerOffMin.setError("error");
                edtPowerOffMin.requestFocus();
                return;
            }
            statusButton = 1;
            if (mBluetoothAdapter != null) {
                connectSensor();
            }else {
                showErrorMessage("Device not support Bluetooth");
            }
        });
        btnRead.setOnClickListener(v -> {
            if (edtPeakMode.getText().toString().length() == 0 || Protector.tryParseInt(edtPeakMode.getText().toString()) > 2 || Protector.tryParseInt(edtPeakMode.getText().toString()) < 0){
                edtPeakMode.setError("error");
                edtPeakMode.requestFocus();
                return;
            }
            if (Protector.tryParseInt(edtPowerOffMin.getText().toString()) > 60){
                edtPowerOffMin.setError("error");
                edtPowerOffMin.requestFocus();
                return;
            }
            statusButton = 0;
            if (mBluetoothAdapter != null) {
                connectSensor();
            }else {
                showErrorMessage("Device not support Bluetooth");
            }
        });

        txtDatetime.setOnClickListener(v -> edtDatetime.setText(Protector.getCurrentTime()));
    }

    private void addController() {
        handler = new Handler(this);

        container = view.findViewById(R.id.container_fragment_2);
        txtDatetime = view.findViewById(R.id.fragment_2_txt_time);
        edtDatetime = view.findViewById(R.id.fragment_2_edt_datetime);
        edtNameMeasure = view.findViewById(R.id.fragment_2_edt_name_measure);
        edtPowerOffMin = view.findViewById(R.id.fragment_2_edt_power_off_min);
        edtPeakMode = view.findViewById(R.id.fragment_2_edt_peakmode);
        btnWrite = view.findViewById(R.id.fragment_2_btn_write);
        btnRead = view.findViewById(R.id.fragment_2_btn_read);
        edtDatetime.setText(Protector.getCurrentTime());
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
        Protector.appendLogSensor(value);
    }

    @Override
    public void onConnected() {
        if (connectThread != null) {
            connectThread.run();
        }
        Message message = Message.obtain();
        message.what = STATE_CONNECTED;
        handler.sendMessage(message);
    }

    @Override
    public void onError(String error) {
        Message message = Message.obtain();
        message.what = STATE_DISCONNECTED;
        handler.sendMessage(message);
    }

    @Override
    public void onRuned() {
        Message message = Message.obtain();
        message.what = STATE_LISTENING;
        handler.sendMessage(message);

        if (connectThread != null) {
            connectThread.write("*" + (statusButton == 1 ? "W" : "R" ) + ",IDNAME,"+ edtNameMeasure.getText().toString() + "[CR]");
            connectThread.write("*" + (statusButton == 1 ? "W" : "R" ) + ",DATETIME,"+ edtDatetime.getText().toString() + "[CR]");
            connectThread.write("*" + (statusButton == 1 ? "W" : "R" ) + ",PRMID,"+ edtDatetime.getText().toString() + "[CR]");
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case 2:
                MainActivity.device.setStatusConnect(1);
                cancelDialogProcessing();
                break;
            case 1:
                MainActivity.device.setStatusConnect(1);
                cancelDialogProcessing();
                break;
            case 0:
                MainActivity.device.setStatusConnect(0);
                cancelDialogProcessing();
                if (++countTryConnect >= maxTryConnect){
                    countTryConnect = 1;
                    showErrorMessage("Can't connect");
                }else {
                    connectSensor();
                }
                break;
        }
        return false;
    }
}

