package com.infinity.EBacSens.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.adapters.AdapteRCVDeviceOnline;
import com.infinity.EBacSens.adapters.AdapteRCVDevicePaired;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.MeasureMeasdets;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.presenter.PresenterListDevice;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.views.ViewListDeviceListener;
import com.infinity.EBacSens.views.ViewRCVDeviceOnline;
import com.infinity.EBacSens.views.ViewRCVDevicePaired;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ListDeviceActivity extends AppCompatActivity implements ViewRCVDevicePaired, ViewRCVDeviceOnline, ViewListDeviceListener {

    private RecyclerView rcvDevicePaired;
    private ArrayList<SensorInfor> arrDevicePaired;
    private AdapteRCVDevicePaired adapteRCVDevicePaired;

    private ArrayList<BluetoothDevice> arrDeviceOnline;
    private AdapteRCVDeviceOnline adapteRCVDeviceOnline;

    private BluetoothAdapter mBluetoothAdapter;

    private RelativeLayout container;
    private TextView txtStatusBluetooth;
    private TextView txtDialogProcessingTitle;
    private Dialog dialogProcessing, dialogListDeviceOnline;
    private IntentFilter intentFilter;
    private IntentFilter intentFilter2;
    private IntentFilter intentFilter3;

    private PresenterListDevice presenterListDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_device);
        addPerMissions();
        addController();
    }

    private void addPerMissions() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                getOnlineDevice();
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }

    private void getOnlineDevice() {
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.startDiscovery();
        }
    }

    private void addController() {
        presenterListDevice = new PresenterListDevice(this);
        intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        intentFilter2 = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter3 = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);

        container = findViewById(R.id.container);
        rcvDevicePaired = findViewById(R.id.rcv_device_paired);
        txtStatusBluetooth = findViewById(R.id.txt_status_bluetooth);
        rcvDevicePaired.setHasFixedSize(true);
        rcvDevicePaired.setLayoutManager(new LinearLayoutManager(this));
        arrDevicePaired = new ArrayList<>();
        adapteRCVDevicePaired = new AdapteRCVDevicePaired(this, arrDevicePaired, this);
        rcvDevicePaired.setAdapter(adapteRCVDevicePaired);
        adapteRCVDevicePaired.onLoadMore();
        rcvDevicePaired.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    adapteRCVDevicePaired.onLoadMore();
                }
            }
        });

        arrDeviceOnline = new ArrayList<>();
        arrDeviceOnline.add(null);
        if (adapteRCVDeviceOnline != null) {
            adapteRCVDeviceOnline.notifyDataSetChanged();
        }

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        initDialogProcessing();
    }

    private void initDialogProcessing() {
        dialogProcessing = new Dialog(this);
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
    public void onClickRCVDevicePaired(int position) {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("device", arrDevicePaired.get(position));
        startActivity(i);
    }

    @Override
    public void onUnpairRCVDevicePaired(int position) {
//        unpairDevice(arrDevicePaired.get(position));
//        arrDevicePaired.remove(position);
//        arrFollowItem.remove(position);
//        adapteRCVDevicePaired.notifyItemRemoved(position);
//        adapteRCVDevicePaired.notifyItemRangeChanged(position, arrDevicePaired.size());
    }

    @Override
    public void onRefreshItem(int position) {
        if (arrDevicePaired.get(position).getMacDevice() != null && arrDeviceOnline != null) {
            for (int i = 0; i < arrDeviceOnline.size(); i++) {
                if (arrDeviceOnline.get(i) != null && arrDeviceOnline.get(i).getAddress() != null && arrDevicePaired.get(position).getMacDevice().equals(arrDeviceOnline.get(i).getAddress())) {
                    arrDevicePaired.get(position).setStatusConnect(-1);
                    break;
                }
            }
        }
    }

    private void checkEnableBluetooth() {
        if (mBluetoothAdapter == null) {
            txtStatusBluetooth.setVisibility(View.VISIBLE);
            txtStatusBluetooth.setText(getResources().getString(R.string.device_does_not_support_bluetooth));
            txtStatusBluetooth.setTextColor(getResources().getColor(R.color.red));
        } else if (!mBluetoothAdapter.isEnabled()) {
            txtStatusBluetooth.setVisibility(View.VISIBLE);
            txtStatusBluetooth.setText(getResources().getString(R.string.device_not_enable_bluetooth));
            txtStatusBluetooth.setTextColor(getResources().getColor(R.color.red));
        } else {
            txtStatusBluetooth.setVisibility(View.GONE);
            rcvDevicePaired.setVisibility(View.VISIBLE);
            getOnlineDevice();
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

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                arrDeviceOnline.add(arrDeviceOnline.size() - 1, device);
                if (adapteRCVDeviceOnline != null) {
                    adapteRCVDeviceOnline.notifyItemInserted(arrDeviceOnline.size() - 2);
                }

                for (int i = 0; i < arrDevicePaired.size(); i++) {
                    if (arrDevicePaired.get(i).getStatusConnect() != 1 && device.getAddress().equals(arrDevicePaired.get(i).getMacDevice())) {
                        arrDevicePaired.get(i).setStatusConnect(-1);
                        adapteRCVDevicePaired.notifyItemChanged(i);
                        break;
                    }
                }

                // not show device online when paired
                for (int i = 0; i < arrDevicePaired.size(); i++) {
                    if (device.getAddress().equals(arrDevicePaired.get(i).getMacDevice())) {
                        arrDeviceOnline.remove(arrDeviceOnline.size() - 2);
                        if (adapteRCVDeviceOnline != null) {
                            adapteRCVDeviceOnline.notifyItemRemoved(arrDeviceOnline.size() - 2);
                        }
                        break;
                    }
                }
            }

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    showSuccessMessage(context.getResources().getString(R.string.paired));
                    txtDialogProcessingTitle.setText(context.getResources().getString(R.string.storing_to_cloud));
                    presenterListDevice.receivedStoreSensor(APIUtils.token, device.getName(), Protector.getCurrentTime(), device.getAddress());
                } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
                    showSuccessMessage(context.getResources().getString(R.string.unpaired));
                } else if (state == BluetoothDevice.BOND_NONE) {
                    cancelDialogProcessing();
                }
            }

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        txtStatusBluetooth.setVisibility(View.VISIBLE);
                        txtStatusBluetooth.setText(context.getResources().getString(R.string.bluetooth_off));
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        txtStatusBluetooth.setVisibility(View.VISIBLE);
                        txtStatusBluetooth.setText(context.getResources().getString(R.string.turning_bluetooth_off));
                        break;
                    case BluetoothAdapter.STATE_ON:
                        txtStatusBluetooth.setVisibility(View.GONE);
                        rcvDevicePaired.setVisibility(View.VISIBLE);
                        arrDeviceOnline.clear();
                        arrDeviceOnline.add(null);
                        if (adapteRCVDeviceOnline != null) {
                            adapteRCVDeviceOnline.notifyDataSetChanged();
                        }
                        getOnlineDevice();
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        txtStatusBluetooth.setText(context.getResources().getString(R.string.turning_bluetooth_on));
                        rcvDevicePaired.setVisibility(View.VISIBLE);
                        break;
                }
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(broadcastReceiver, intentFilter);
        registerReceiver(broadcastReceiver, intentFilter2);
        registerReceiver(broadcastReceiver, intentFilter3);
        checkEnableBluetooth();
    }

    @Override
    protected void onStop() {
        super.onStop();
        arrDeviceOnline.clear();
        arrDeviceOnline.add(null);
        if (adapteRCVDeviceOnline != null) {
            adapteRCVDeviceOnline.notifyDataSetChanged();
        }
        unregisterReceiver(broadcastReceiver);
    }

    public void onShowDeviceOnline(View view) {
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, getResources().getString(R.string.device_does_not_support_bluetooth), Toast.LENGTH_SHORT).show();
            return;
        } else if (!mBluetoothAdapter.isEnabled()) {
            Dialog dialogYesNo = new Dialog(this);
            dialogYesNo.setContentView(R.layout.dialog_yes_no);
            TextView txtTitle = dialogYesNo.findViewById(R.id.dialog_yes_no_txt_title);
            txtTitle.setText(getResources().getString(R.string.turn_on_bluetooth));
            Button btnYes = dialogYesNo.findViewById(R.id.dialog_yes_no_btn_yes);
            Button btnNo = dialogYesNo.findViewById(R.id.dialog_yes_no_btn_no);

            btnYes.setOnClickListener(v -> {
                mBluetoothAdapter.enable();
                dialogYesNo.cancel();
            });
            btnNo.setOnClickListener(v -> dialogYesNo.cancel());
            dialogYesNo.show();
            Window window = dialogYesNo.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            return;
        }

        dialogListDeviceOnline = new Dialog(this);
        dialogListDeviceOnline.setContentView(R.layout.dialog_list_device_online);

        dialogListDeviceOnline.findViewById(R.id.dialog_list_device_online_btn_close).setOnClickListener(v -> dialogListDeviceOnline.cancel());
        RecyclerView rcvDeviceOnline = dialogListDeviceOnline.findViewById(R.id.dialog_list_device_online_rcv);
        rcvDeviceOnline.setLayoutManager(new LinearLayoutManager(dialogListDeviceOnline.getContext()));
        adapteRCVDeviceOnline = new AdapteRCVDeviceOnline(dialogListDeviceOnline.getContext(), arrDeviceOnline, this);
        rcvDeviceOnline.setAdapter(adapteRCVDeviceOnline);
        dialogListDeviceOnline.show();
        Window window = dialogListDeviceOnline.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onClickRCVDeviceOnline(int position) {
        showDialogProcessing();
        txtDialogProcessingTitle.setText(getResources().getString(R.string.pairing));
        BluetoothDevice device = arrDeviceOnline.get(position);

        if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
            unpairDevice(device);
        } else {
            pairDevice(device);
        }
    }

    private void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            cancelDialogProcessing();
        }

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

    @Override
    protected void onResume() {
        super.onResume();
        if (adapteRCVDevicePaired != null && arrDevicePaired != null && MainActivity.device != null && MainActivity.device.getMacDevice() != null) {
            for (int i = 0; i < arrDevicePaired.size(); i++) {
                if (arrDevicePaired.get(i).getStatusConnect() == 1) {
                    arrDevicePaired.get(i).setStatusConnect(-1);
                }
            }

            for (int i = 0; i < arrDevicePaired.size(); i++) {
                if (arrDevicePaired.get(i).getMacDevice() != null && arrDevicePaired.get(i).getMacDevice().equals(MainActivity.device.getMacDevice()) && MainActivity.device.getStatusConnect() == 1) {
                    arrDevicePaired.get(i).setStatusConnect(1);
                    break;
                }
            }

            adapteRCVDevicePaired.notifyDataSetChanged();
        }
    }

    @Override
    public void onSuccessStoreSensor(SensorInfor sensorInfor) {
        sensorInfor.setStatusConnect(-1);
        arrDevicePaired.add(0,sensorInfor);
        adapteRCVDevicePaired.notifyItemInserted(0);
        cancelDialogProcessing();
        if (dialogListDeviceOnline != null) {
            dialogListDeviceOnline.cancel();
        }
    }

    @Override
    public void onFailStoreSensor(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        cancelDialogProcessing();
    }
}