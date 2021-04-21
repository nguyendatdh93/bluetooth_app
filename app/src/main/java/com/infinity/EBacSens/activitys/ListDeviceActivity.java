package com.infinity.EBacSens.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.adapters.AdapteRCVDeviceOnline;
import com.infinity.EBacSens.adapters.AdapteRCVDevicePaired;
import com.infinity.EBacSens.model_objects.Sensor;
import com.infinity.EBacSens.views.ViewRCVDeviceOnline;
import com.infinity.EBacSens.views.ViewRCVDevicePaired;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ListDeviceActivity extends AppCompatActivity implements ViewRCVDevicePaired, ViewRCVDeviceOnline {

    private RecyclerView rcvDevicePaired;
    private ArrayList<Sensor> arrDevicePaired;
    private AdapteRCVDevicePaired adapteRCVDevicePaired;

    ArrayList<BluetoothDevice> arrDeviceOnline;
    AdapteRCVDeviceOnline adapteRCVDeviceOnline;

    ArrayList<BluetoothDevice> devices;
    BluetoothAdapter mBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_device);
        addController();
        addEvent();
    }

    private void addEvent() {
        getPairedDevices();
        getOnlineDevice();
    }

    private void getPairedDevices() {
        if (devices == null) {
            devices = new ArrayList<>();
        } else {
            devices.clear();
        }
        if (mBluetoothAdapter != null) {
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) {
                for (BluetoothDevice curDevice : pairedDevices) {
                    devices.add(curDevice);
                    arrDevicePaired.add(new Sensor(curDevice.getName(), curDevice.getAddress(), false, false));
                    adapteRCVDevicePaired.notifyItemInserted(arrDevicePaired.size() - 1);
                }
            }
        }

    }

    private void getOnlineDevice() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                PermissionListener permissionListener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (mBluetoothAdapter != null) {
                            mBluetoothAdapter.startDiscovery();
                        }
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }
                };
                TedPermission.with(ListDeviceActivity.this).setPermissionListener(permissionListener).setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION).check();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {

            }
        };
        TedPermission.with(this).setPermissionListener(permissionListener).setPermissions(Manifest.permission.ACCESS_FINE_LOCATION).check();
    }

    private void addController() {
        rcvDevicePaired = findViewById(R.id.rcv_device_paired);
        rcvDevicePaired.setHasFixedSize(true);
        rcvDevicePaired.setLayoutManager(new LinearLayoutManager(this));
        arrDevicePaired = new ArrayList<>();
        adapteRCVDevicePaired = new AdapteRCVDevicePaired(this, arrDevicePaired, this);
        rcvDevicePaired.setAdapter(adapteRCVDevicePaired);

        arrDeviceOnline = new ArrayList<>();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    public void onClickRCVDevicePaired(int position) {
        for (int i = 0; i < arrDevicePaired.size(); i++) {
            if (arrDevicePaired.get(i).isSelected()) {
                arrDevicePaired.get(i).setSelected(false);
                adapteRCVDevicePaired.notifyItemChanged(i);
            }
        }
        arrDevicePaired.get(position).setSelected(true);
        adapteRCVDevicePaired.notifyItemChanged(position);

//        txtSensorName.setText(arrMenuDraw.get(position).getName());
//        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        IntentFilter intentFilter2 = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(broadcastReceiver, intentFilter);
        registerReceiver(broadcastReceiver, intentFilter2);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                arrDeviceOnline.add(device);
                if (adapteRCVDeviceOnline != null) {
                    adapteRCVDeviceOnline.notifyItemInserted(arrDeviceOnline.size() - 1);
                }

                for (int i = 0; i < arrDevicePaired.size(); i++) {
                    if (device.getAddress().equals(arrDevicePaired.get(i).getAddress())) {
                        arrDevicePaired.get(i).setToggle(true);
                        adapteRCVDevicePaired.notifyItemChanged(i);
                        break;
                    }
                }

                for (int i = 0; i < arrDevicePaired.size(); i++) {
                    if (device.getAddress().equals(arrDevicePaired.get(i).getAddress())) {
                        arrDeviceOnline.remove(arrDeviceOnline.size()-1);
                        adapteRCVDeviceOnline.notifyItemRemoved(arrDeviceOnline.size()-1);
                        break;
                    }
                }
            }

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);

                if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    arrDevicePaired.add(new Sensor(device.getName(), device.getAddress(), false, false));
                    adapteRCVDevicePaired.notifyItemInserted(arrDevicePaired.size() - 1);
                    Toast.makeText(context, "paired", Toast.LENGTH_SHORT).show();
                } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
                    Toast.makeText(context, "Unpaired", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    public void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    public void onShowDeviceOnline(View view) {
        Dialog dialogListDeviceOnline = new Dialog(this);
        dialogListDeviceOnline.setContentView(R.layout.dialog_list_device_online);

        RecyclerView rcvDeviceOnline = dialogListDeviceOnline.findViewById(R.id.dialog_list_device_online_rcv);
        rcvDeviceOnline.setLayoutManager(new LinearLayoutManager(dialogListDeviceOnline.getContext()));
        adapteRCVDeviceOnline = new AdapteRCVDeviceOnline(dialogListDeviceOnline.getContext(), arrDeviceOnline, this);
        rcvDeviceOnline.setAdapter(adapteRCVDeviceOnline);
        dialogListDeviceOnline.show();
    }

    @Override
    public void onClickRCVDeviceOnline(int position) {
        BluetoothDevice device = arrDeviceOnline.get(position);

        if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
            unpairDevice(device);
        } else {
            Toast.makeText(this, "Pairing", Toast.LENGTH_SHORT).show();
            pairDevice(device);
        }
    }

    private void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unpairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}