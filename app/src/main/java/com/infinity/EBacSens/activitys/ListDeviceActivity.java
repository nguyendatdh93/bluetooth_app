package com.infinity.EBacSens.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.adapters.AdapteRCVDeviceOnline;
import com.infinity.EBacSens.adapters.AdapteRCVDevicePaired;
import com.infinity.EBacSens.model_objects.Sensor;
import com.infinity.EBacSens.model_objects.VerticalSpaceItemDecoration;
import com.infinity.EBacSens.views.ViewRCVMenuDrawListener;

import java.util.ArrayList;
import java.util.Set;

public class ListDeviceActivity extends AppCompatActivity implements ViewRCVMenuDrawListener {

    private RecyclerView rcvDevicePaired;
    private ArrayList<Sensor> arrDevicePaired;
    private AdapteRCVDevicePaired adapteRCVDevicePaired;

    ArrayList<Sensor> arrDeviceOnline;
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

    private void getOnlineDevice(){
        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.startDiscovery();
        }
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
    public void onClickRCVMenuDraw(int position) {
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
        registerReceiver(broadcastReceiver, intentFilter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                arrDeviceOnline.add(new Sensor(device.getName(), device.getAddress(), false, false));
                if (adapteRCVDeviceOnline != null){
                    adapteRCVDeviceOnline.notifyItemInserted(arrDeviceOnline.size() - 1);
                }

                for (int i = 0 ; i < arrDevicePaired.size() ; i++){
                    if (device.getAddress().equals(arrDevicePaired.get(i).getAddress())){
                        arrDevicePaired.get(i).setToggle(true);
                        adapteRCVDevicePaired.notifyItemChanged(i);
                        break;
                    }
                }
            }
        }
    };

    public void onShowDeviceOnline(View view) {
        Dialog dialogListDeviceOnline = new Dialog(this);
        dialogListDeviceOnline.setContentView(R.layout.dialog_list_device_online);

        RecyclerView rcvDeviceOnline = dialogListDeviceOnline.findViewById(R.id.dialog_list_device_online_rcv);
        rcvDeviceOnline.setLayoutManager(new LinearLayoutManager(dialogListDeviceOnline.getContext()));
        adapteRCVDeviceOnline = new AdapteRCVDeviceOnline(dialogListDeviceOnline.getContext(), arrDeviceOnline);
        rcvDeviceOnline.setAdapter(adapteRCVDeviceOnline);
        dialogListDeviceOnline.show();
    }
}