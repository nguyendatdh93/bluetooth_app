package com.infinity.EBacSens.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
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
import android.widget.Button;
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

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.infinity.EBacSens.activitys.MainActivity.STATE_CONNECTED;
import static com.infinity.EBacSens.activitys.MainActivity.STATE_CONNECTED_TEST;
import static com.infinity.EBacSens.activitys.MainActivity.STATE_DISCONNECTED;
import static com.infinity.EBacSens.activitys.MainActivity.STATE_LISTENING;
import static com.infinity.EBacSens.activitys.MainActivity.mBluetoothAdapter;
import static com.infinity.EBacSens.retrofit2.APIUtils.PBAP_UUID;

public class Fragment1 extends Fragment implements ViewConnectThread , Handler.Callback{

    private View view;
    private Activity activity;
    private Context context;

    private RelativeLayout container;
    private LinearLayout containerInfor , containerStatus;
    private TextView txtInfor1, txtInfor2, txtInfor3, txtStatusConnection;
    private Button btnTestConnect, btnConnect, btnDisconnect;

    private TextView txtDialogProcessingTitle;
    private Dialog dialogProcessing;

    private ArrayList<BluetoothDevice> arrDevicePaired;

    private IntentFilter intentFilter;

    private ConnectThread connectThread;

    // 0 = test , 1 = connect
    private int statusConnect;

    private Handler handler;

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
                        showErrorMessage("Device not support Bluetooth");
                    }
                }
            } else {
                showErrorMessage("Device not have mac address");
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
                        showErrorMessage("Device not support Bluetooth");
                    }
                }
            } else {
                showErrorMessage("Device not have mac address");
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

    private void showSuccessMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(container, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.GREEN);
        TextView textView = (TextView) view.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.BLACK);
        snackbar.show();
    }

    private void showErrorMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(container, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.RED);
        TextView textView = (TextView) view.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private void addController() {
        intentFilter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);

        container = view.findViewById(R.id.container_fragment_1);
        containerStatus = view.findViewById(R.id.fragment_1_container_status);
        containerInfor = view.findViewById(R.id.fragment_1_container_infor);
        container = view.findViewById(R.id.container_fragment_1);
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


        initDialogProcessing();
        getPairedDevices();
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

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    showSuccessMessage("Paired");
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

        // write to sensor
        connectThread.write("*R,IDNAME[CR]");
        connectThread.write("*R,VER[CR]");
        connectThread.write("*R,SER[CR]");
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        if (statusConnect == 1) {

            // test

            MainActivity.device.setStatusConnect(1);
            cancelDialogProcessing();
            containerInfor.setVisibility(View.VISIBLE);
            containerStatus.setVisibility(View.GONE);
            txtInfor1.setText("MODEL:EbacSens");
            txtInfor2.setText("Ver.a.014");
            txtInfor3.setText("Serial:0003");

            return false;
        }else if(statusConnect == 0) {
            MainActivity.device.setStatusConnect(1);
            cancelDialogProcessing();
            containerInfor.setVisibility(View.GONE);
            containerStatus.setVisibility(View.VISIBLE);
            txtStatusConnection.setText(context.getResources().getString(R.string.connection_test_success));
            txtStatusConnection.setTextColor(context.getResources().getColor(R.color.green));
            return false;
        }

        switch (msg.what){
            //
            case 3:
                MainActivity.device.setStatusConnect(1);
                cancelDialogProcessing();
                containerInfor.setVisibility(View.GONE);
                containerStatus.setVisibility(View.VISIBLE);
                txtStatusConnection.setText(context.getResources().getString(R.string.connection_test_success));
                txtStatusConnection.setTextColor(context.getResources().getColor(R.color.green));
                break;
            case 1:
                MainActivity.device.setStatusConnect(1);
                cancelDialogProcessing();
                containerInfor.setVisibility(View.VISIBLE);
                containerStatus.setVisibility(View.GONE);
                txtInfor1.setText("MODEL:EbacSens");
                txtInfor2.setText("Ver.a.014");
                txtInfor3.setText("Serial:0003");
                break;
            case 0:
                MainActivity.device.setStatusConnect(0);
                cancelDialogProcessing();
                containerInfor.setVisibility(View.GONE);
                containerStatus.setVisibility(View.VISIBLE);
                txtStatusConnection.setText("Disconnected");
                txtStatusConnection.setTextColor(context.getResources().getColor(R.color.red));
        }
        return false;
    }
}