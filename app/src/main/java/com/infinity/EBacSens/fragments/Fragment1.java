package com.infinity.EBacSens.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
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
import com.infinity.EBacSens.presenter.PresenterFragment1;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.task.ConnectThread;
import com.infinity.EBacSens.views.ViewConnectThread;
import com.infinity.EBacSens.views.ViewFragment1Listener;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.infinity.EBacSens.activitys.MainActivity.STATE_CONNECTED;
import static com.infinity.EBacSens.activitys.MainActivity.STATE_DISCONNECTED;
import static com.infinity.EBacSens.activitys.MainActivity.STATE_LISTENING;
import static com.infinity.EBacSens.activitys.MainActivity.connectThread;
import static com.infinity.EBacSens.activitys.MainActivity.mBluetoothAdapter;
import static com.infinity.EBacSens.retrofit2.APIUtils.PBAP_UUID;

public class Fragment1 extends Fragment implements ViewConnectThread , Handler.Callback{

    private View view;
    private Activity activity;
    private Context context;

    //private RelativeLayout container;
    private LinearLayout containerInfor , containerStatus;
    private TextView txtInfor1, txtInfor2, txtInfor3, txtStatusConnection;
    private Button btnTestConnect, btnConnect, btnDisconnect;

    private TextView txtDialogProcessingTitle;
    private Dialog dialogProcessing;

    private ArrayList<BluetoothDevice> arrDevicePaired;

    private IntentFilter intentFilter;

    //private ConnectThread connectThread;

    // popup
    private LinearLayout containerPopup;
    ImageView imgTitle;
    TextView txtTitle , txtContent;

    // 0 = test , 1 = connect
    private int statusConnect;
    private int countTryConnect = 0;
    private final int maxTryConnect = 2;
    private Handler handler;

    private ArrayList<String> arrRules;
    private ArrayList<String> arrResults;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1, container, false);
        addController();
        addEvents();

        return view;
    }

    private void connectSensor() {
        if (MainActivity.device.getMacDevice() != null && mBluetoothAdapter != null) {
            try {
                if (connectThread != null) {
                    connectThread.cancel();
                }
                connectThread = new ConnectThread(mBluetoothAdapter.getRemoteDevice(MainActivity.device.getMacDevice()).createInsecureRfcommSocketToServiceRecord(ParcelUuid.fromString(PBAP_UUID).getUuid()), handler ,this);
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
        handler = new Handler(this);

        btnTestConnect.setOnClickListener(v -> {
            if (MainActivity.device.getMacDevice() != null) {
                boolean connection = false;
                for (int i = 0; i < arrDevicePaired.size(); i++) {
                    if (MainActivity.device.getMacDevice().equals(arrDevicePaired.get(i).getAddress())) {
                        connection = true;
                    }
                }
                if (connection && mBluetoothAdapter != null) {
                    showDialogProcessing();
                    statusConnect = 0;
                    connectSensor();
                } else {
                    if (mBluetoothAdapter != null && MainActivity.device.getMacDevice() != null) {
                        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(MainActivity.device.getMacDevice());
                        txtDialogProcessingTitle.setText("Paring...");
                        showDialogProcessing();
                        pairDevice(device);
                    } else {
                        showPopup("Failed" , "Device not have mac address." , false);
                    }
                }
            } else {
                showPopup("Failed" , "Device not have mac address." , false);
            }
        });

        btnConnect.setOnClickListener(v -> {
            if (MainActivity.device.getMacDevice() != null) {
                boolean connection = false;
                for (int i = 0; i < arrDevicePaired.size(); i++) {
                    if (MainActivity.device.getMacDevice().equals(arrDevicePaired.get(i).getAddress())) {
                        connection = true;
                    }
                }
                if (connection && mBluetoothAdapter != null) {
                    statusConnect = 1;
                    connectSensor();
                    showDialogProcessing();
                } else {
                    if (mBluetoothAdapter != null && MainActivity.device.getMacDevice() != null) {
                        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(MainActivity.device.getMacDevice());
                        txtDialogProcessingTitle.setText("Paring...");
                        showDialogProcessing();
                        pairDevice(device);
                    } else {
                        showPopup("Failed" , "Device not have mac address." , false);
                    }
                }
            } else {
                showPopup("Failed" , "Device not have mac address." , false);
            }
        });

        btnDisconnect.setOnClickListener(v -> {
            statusConnect = -1;
            if (connectThread != null) {
                connectThread.cancel();
            }
            showDialogProcessing();
            Message message = Message.obtain();
            message.what = STATE_DISCONNECTED;
            handler.sendMessage(message);
        });
    }

    private void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            cancelDialogProcessing();
        }
    }

    private void getPairedDevices() {
        arrDevicePaired.clear();
        if (mBluetoothAdapter != null) {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            arrDevicePaired.addAll(pairedDevices);
        }
    }

    private void addController() {
        intentFilter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        containerPopup = view.findViewById(R.id.container_popup);
        containerStatus = view.findViewById(R.id.fragment_1_container_status);
        containerInfor = view.findViewById(R.id.fragment_1_container_infor);
        txtInfor1 = view.findViewById(R.id.fragment_1_txt_infor_1);
        txtInfor2 = view.findViewById(R.id.fragment_1_txt_infor_2);
        txtInfor3 = view.findViewById(R.id.fragment_1_txt_infor_3);
        txtStatusConnection = view.findViewById(R.id.fragment_1_txt_status_connection);

        if (MainActivity.device.getStatusConnect() == -1) {
            txtStatusConnection.setTextColor(context.getResources().getColor(R.color.yellow));
            txtStatusConnection.setText("Ready to connect");
        } else if (MainActivity.device.getStatusConnect() == 1) {
            txtStatusConnection.setTextColor(context.getResources().getColor(R.color.green));
            txtStatusConnection.setText("Connected");
        } else {
            txtStatusConnection.setTextColor(context.getResources().getColor(R.color.red));
            txtStatusConnection.setText("Not connection");
        }

        btnTestConnect = view.findViewById(R.id.fragment_1_btn_test_connect);
        btnConnect = view.findViewById(R.id.fragment_1_btn_connect);
        btnDisconnect = view.findViewById(R.id.fragment_1_btn_disconnect);

        arrDevicePaired = new ArrayList<>();
        arrRules = new ArrayList<>();
        arrResults = new ArrayList<>();

        initDialogProcessing();
        getPairedDevices();
        initPopup();
    }

    private void initPopup(){
        ImageButton imgClose = view.findViewById(R.id.fragment_popup_img_close);
        imgTitle = view.findViewById(R.id.fragment_popup_img_title);

        imgClose.setOnClickListener(v -> {
            hidePopup();
        });

        txtTitle = view.findViewById(R.id.fragment_popup_txt_title);
        txtContent = view.findViewById(R.id.fragment_popup_txt_content);
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

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    showPopup("Success" , "Paired." , true);
                    cancelDialogProcessing();
                    if (mBluetoothAdapter != null) {
                        connectSensor();
                    }
                } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
                    cancelDialogProcessing();
                } else if (state == BluetoothDevice.BOND_NONE) {
                    cancelDialogProcessing();
                }
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        activity.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        context.unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    public void onGetData(String value) {

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
        Protector.appendLog(error);
        Log.e("AAAA" , error);
    }

    @Override
    public void onRuned() {
        Message message = Message.obtain();
        message.what = STATE_LISTENING;
        handler.sendMessage(message);
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {

        switch (msg.what){
            case 4:
                byte[] readBuff = (byte[]) msg.obj;
                String tempMsg = new String(readBuff, 0, msg.arg1);
                // log file
                Protector.appendLogSensor(tempMsg);
                // result sensor
                arrResults.add(tempMsg);

                if (statusConnect == 1){
                    if (arrRules.size() == 0 && arrResults.size() >= 3){
                        txtInfor1.setText(arrResults.get(0));
                        txtInfor2.setText(arrResults.get(1));
                        txtInfor3.setText(arrResults.get(2));
                        containerInfor.setVisibility(View.VISIBLE);
                        containerStatus.setVisibility(View.GONE);
                        cancelDialogProcessing();
                    }else {
                        connectThread.write(arrRules.get(0));
                        Protector.appendLog(arrRules.get(0));
                        arrRules.remove(0);
                    }
                }else {
                    cancelDialogProcessing();
                }

                break;
            case 2:
                countTryConnect = 1;
                MainActivity.device.setStatusConnect(1);
                arrRules.clear();
                arrResults.clear();
                if (statusConnect == 1){
                    arrRules.add("*R,IDNAME[CR]");
                    arrRules.add("*R,VER[CR]");
                    arrRules.add("*R,SER[CR]");
                    connectThread.write(arrRules.get(0));
                    Protector.appendLog(arrRules.get(0));
                    arrRules.remove(0);
                }else {
                    containerInfor.setVisibility(View.GONE);
                    containerStatus.setVisibility(View.VISIBLE);
                    txtStatusConnection.setText(context.getResources().getString(R.string.connection_test_success));
                    txtStatusConnection.setTextColor(context.getResources().getColor(R.color.green));
                    cancelDialogProcessing();
                }
                break;
            case 0:
                if (statusConnect == -1){
                    MainActivity.device.setStatusConnect(0);
                    cancelDialogProcessing();
                    containerInfor.setVisibility(View.GONE);
                    containerStatus.setVisibility(View.VISIBLE);
                    txtStatusConnection.setText("Disconnected");
                    txtStatusConnection.setTextColor(context.getResources().getColor(R.color.red));
                }else {
                    MainActivity.device.setStatusConnect(0);
                    cancelDialogProcessing();
                    if (++countTryConnect >= maxTryConnect){
                        countTryConnect = 1;
                        showPopup("Failed" , "Something went terribly wrong.\n" +"Try again." , false);
                    }else {
                        connectSensor();
                    }
                }
        }
        return false;
    }
}