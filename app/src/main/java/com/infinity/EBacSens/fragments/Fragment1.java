package com.infinity.EBacSens.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.activitys.MainActivity;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.retrofit2.APIUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Fragment1 extends Fragment {

    private View view;
    private Activity activity;
    private Context context;

    private BluetoothAdapter mBluetoothAdapter;

    private RelativeLayout container;
    private AutoCompleteTextView edtNameDevice;
    private TextView txtInfor1 , txtStatusConnection;
    private Button btnTestConnect , btnConnect , btnDisconnect;

    private TextView txtDialogProcessingTitle;
    private Dialog dialogProcessing;

    private ArrayList<BluetoothDevice> arrDevicePaired;

    private IntentFilter intentFilter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_1, container, false);
        addController();
        addEvents();
        return view;
    }

    private void addEvents() {
        btnTestConnect.setOnClickListener(v -> {
            if (MainActivity.toggle){
                showSuccessMessage(context.getResources().getString(R.string.connection_test_success));
            }else {
                showErrorMessage("Not connection");
            }
        });

        btnConnect.setOnClickListener(v -> {
            boolean connection = false;
            for (int i = 0 ; i < arrDevicePaired.size() ; i++){
                if (MainActivity.device.getMacDevice().equals(arrDevicePaired.get(i).getAddress())){
                    connection = true;
                }
            }
            if (connection){
                showSuccessMessage(context.getResources().getString(R.string.connection_test_success));
            }else {
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(MainActivity.device.getMacDevice());
                txtDialogProcessingTitle.setText("Paring...");
                showDialogProcessing();
                pairDevice(device);
            }
        });

        btnDisconnect.setOnClickListener(v -> {
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(MainActivity.device.getMacDevice());
            unpairDevice(device);
            showErrorMessage("Unpaired");
        });
    }

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);

        } catch (Exception e) {
            e.printStackTrace();
        }
        cancelDialogProcessing();
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
        edtNameDevice = view.findViewById(R.id.fragment_1_acp_name_device);
        txtInfor1 = view.findViewById(R.id.fragment_1_txt_infor_1);
        txtStatusConnection = view.findViewById(R.id.fragment_1_txt_status_connection);
        if (MainActivity.device != null){
            edtNameDevice.setText(MainActivity.device.getName());
            txtInfor1.setText(MainActivity.device.getName());
        }

        if (MainActivity.toggle){
            txtStatusConnection.setTextColor(context.getResources().getColor(R.color.green));
            txtStatusConnection.setText(context.getResources().getString(R.string.connection_test_success));
        }else {
            txtStatusConnection.setTextColor(context.getResources().getColor(R.color.red));
            txtStatusConnection.setText("Not connection");
        }

        btnTestConnect = view.findViewById(R.id.fragment_1_btn_test_connect);
        btnConnect = view.findViewById(R.id.fragment_1_btn_connect);
        btnDisconnect = view.findViewById(R.id.fragment_1_btn_disconnect);

        arrDevicePaired = new ArrayList<>();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        initDialogProcessing();
        getPairedDevices();
    }

    private void initDialogProcessing(){
        dialogProcessing = new Dialog(context);
        dialogProcessing.setContentView(R.layout.dialog_processing);
        dialogProcessing.setCancelable(false);
        txtDialogProcessingTitle = dialogProcessing.findViewById(R.id.dialog_processing_txt_title);
    }

    private void showDialogProcessing(){
        dialogProcessing.show();
    }

    private void cancelDialogProcessing(){
        if (dialogProcessing != null){
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
                    showSuccessMessage("Paired");
                    cancelDialogProcessing();
                }else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
                    cancelDialogProcessing();
                }else if (state == BluetoothDevice.BOND_NONE){
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
}

