package com.infinity.EBacSens.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import java.util.ArrayList;

import static com.infinity.EBacSens.activitys.MainActivity.STATE_CONNECTED;
import static com.infinity.EBacSens.activitys.MainActivity.STATE_DISCONNECTED;
import static com.infinity.EBacSens.activitys.MainActivity.STATE_LISTENING;
import static com.infinity.EBacSens.activitys.MainActivity.mBluetoothAdapter;
import static com.infinity.EBacSens.retrofit2.APIUtils.PBAP_UUID;

public class Fragment2 extends Fragment implements ViewConnectThread  , Handler.Callback{

    private View view;
    private Activity activity;
    private Context context;

    private EditText edtNameMeasure , edtDatetime , edtPowerOffMin , edtPeakMode;
    private Button btnRead , btnWrite;

    private Dialog dialogProcessing;
    private TextView txtDialogProcessingTitle , txtDatetime;

    private ConnectThread connectThread;

    // popup
    private LinearLayout containerPopup;
    private ImageView imgTitle;
    private TextView txtTitle , txtContent;

    // 1 = write , 0 = read
    private int statusButton = 0;
    private Handler handler;
    private int countTryConnect = 0;
    private final int maxTryConnect = 2;

    private ArrayList<String> arrRules;

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
                connectThread = new ConnectThread(mBluetoothAdapter.getRemoteDevice(MainActivity.device.getMacDevice()).createInsecureRfcommSocketToServiceRecord(ParcelUuid.fromString(PBAP_UUID).getUuid()), handler , this);
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
            showPopup("Failed" , "Device not support Bluetooth." , false);
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
                showPopup("Failed" , "Device not support Bluetooth." , false);
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
                showPopup("Failed" , "Device not support Bluetooth." , false);
            }
        });

        txtDatetime.setOnClickListener(v -> edtDatetime.setText(Protector.getCurrentTime()));
    }

    private void addController() {
        handler = new Handler(this);

        txtDatetime = view.findViewById(R.id.fragment_2_txt_time);
        edtDatetime = view.findViewById(R.id.fragment_2_edt_datetime);
        edtNameMeasure = view.findViewById(R.id.fragment_2_edt_name_measure);
        edtPowerOffMin = view.findViewById(R.id.fragment_2_edt_power_off_min);
        edtPeakMode = view.findViewById(R.id.fragment_2_edt_peakmode);
        btnWrite = view.findViewById(R.id.fragment_2_btn_write);
        btnRead = view.findViewById(R.id.fragment_2_btn_read);
        edtDatetime.setText(Protector.getCurrentTime());

        arrRules = new ArrayList<>();
        initDialogProcessing();
        initPopup();
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    public void onGetData(String value) {
        // get data from sensor
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
    }

    private void initPopup(){
        containerPopup = view.findViewById(R.id.container_popup);
        ImageButton imgClose = view.findViewById(R.id.fragment_popup_img_close);
        imgTitle = view.findViewById(R.id.fragment_popup_img_title);

        imgClose.setOnClickListener(v -> {
            hidePopup();
        });

        txtTitle = view.findViewById(R.id.fragment_popup_txt_title);
        txtContent = view.findViewById(R.id.fragment_popup_txt_content);
    }

    private void showPopup(String title , String content , boolean success){
        txtTitle.setText(title);
        txtContent.setText(content);

        if (success){
            imgTitle.setBackground(context.getResources().getDrawable(R.drawable.circle_green));
            imgTitle.setImageResource(R.drawable.ic_baseline_check_24);
        }else {
            imgTitle.setBackground(context.getResources().getDrawable(R.drawable.circle_red));
            imgTitle.setImageResource(R.drawable.ic_baseline_close_24);
        }

        containerPopup.setVisibility(View.VISIBLE);

        Animation animSlide = AnimationUtils.loadAnimation(context,
                R.anim.left_to_right);
        containerPopup.startAnimation(animSlide);

        final Runnable r = new Runnable() {
            public void run() {
                hidePopup();
            }
        };
        handler.postDelayed(r, 3000);
    }

    private void hidePopup(){
        if (containerPopup.getVisibility() == View.VISIBLE){
            Animation animSlide = AnimationUtils.loadAnimation(context,
                    R.anim.right_to_left);
            containerPopup.startAnimation(animSlide);
            containerPopup.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what){
            case 6:
                cancelDialogProcessing();
                byte[] readBuff = (byte[]) msg.obj;
                String tempMsg = new String(readBuff, 0, msg.arg1);

                // result sensor

                if (arrRules.size() == 0){
                    if (statusButton == 1){
                        connectThread.write("SAVE");
                    }else {
                        cancelDialogProcessing();
                        showPopup("Success" , "You have successfully changed providing time." , true);
                    }
                }else {
                    arrRules.remove(0);
                    connectThread.write(arrRules.get(0));
                }

                Protector.appendLogSensor(tempMsg);

                break;
            case 2:
                // demo will enable below line
                cancelDialogProcessing();

                MainActivity.device.setStatusConnect(1);

                arrRules.clear();
                arrRules.add("*" + (statusButton == 1 ? "W" : "R" ) + ",IDNAME,"+ edtNameMeasure.getText().toString() + "[CR]");
                arrRules.add("*" + (statusButton == 1 ? "W" : "R" ) + ",DATETIME,"+ edtDatetime.getText().toString() + "[CR]");
                arrRules.add("*" + (statusButton == 1 ? "W" : "R" ) + ",PRMID,"+ edtDatetime.getText().toString() + "[CR]");

                if (connectThread != null) {
                    connectThread.write(arrRules.get(0));
                }

                break;
            case 0:
                MainActivity.device.setStatusConnect(0);
                cancelDialogProcessing();
                if (++countTryConnect >= maxTryConnect){
                    countTryConnect = 1;
                    showPopup("Failed" , "Something went terribly wrong.\n" +"Try again." , false);
                }else {
                    connectSensor();
                }
                break;
        }
        return false;
    }
}

