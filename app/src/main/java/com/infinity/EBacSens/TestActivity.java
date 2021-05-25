package com.infinity.EBacSens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.EBacSens.activitys.MainActivity;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.task.ConnectThread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.infinity.EBacSens.activitys.MainActivity.mBluetoothAdapter;

public class TestActivity extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter;
    SerialPortProfileConnectThread connectTread = null;

    private BluetoothReceiveTask mReceiveTask;

    private Handler handler;
    private TextView txtResult;
    private Spinner spn;
    private EditText edtInput2;
    private boolean connected = false;
    SensorInfor device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        txtResult = findViewById(R.id.txt_result);
        edtInput2 = findViewById(R.id.edt_input_2);
        spn = findViewById(R.id.spn);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtInput2.setText(spn.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                byte[] readBuff = (byte[]) msg.obj;
                String tempMsg = new String(readBuff, 0, msg.arg1);

                if (tempMsg.equals("Connected")){
                    connected = true;
                }else if (tempMsg.equals("Disconnected")){
                    connected = false;
                }
                txtResult.setText(txtResult.getText().toString() + tempMsg + "\n");
                Protector.appendLogSensor(tempMsg);
                return false;
            }
        });

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        device = (SensorInfor) getIntent().getSerializableExtra("device");

    }

    public void connect(View view) {
        if (connected) {
            Message readMsg = handler.obtainMessage(
                    1, "Connected".getBytes(StandardCharsets.UTF_8).length, -1,
                    "Connected".getBytes(StandardCharsets.UTF_8));
            readMsg.sendToTarget();
        } else {
            if (mBluetoothAdapter == null || device.getMacDevice() == null) {
                // エラー: Bluetooth なし.
                Toast.makeText(this, "please , choose other device have mac_address", Toast.LENGTH_SHORT).show();
            } else {
                mBluetoothAdapter.cancelDiscovery();
                Message readMsg = handler.obtainMessage(
                        1, "Connecting".getBytes(StandardCharsets.UTF_8).length, -1,
                        "Connecting".getBytes(StandardCharsets.UTF_8));
                readMsg.sendToTarget();
                connectTread = new SerialPortProfileConnectThread(mBluetoothAdapter.getRemoteDevice(device.getMacDevice()));
                connectTread.start();


                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                mReceiveTask = new BluetoothReceiveTask(connectTread.getSocket(), handler);
                mReceiveTask.start();

//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }

    }

    public void disconnect(View view) {
        if (connected && mReceiveTask != null) {
            mReceiveTask.finish();
        }
    }

    public void sendData(View view) {
        if (connected || edtInput2.getText().toString().length() != 0){
            mReceiveTask.write(edtInput2.getText().toString());
            Protector.appendLog(edtInput2.getText().toString());
        }
    }

    public void testConnect(View view) {
        Toast.makeText(this, connected ? "Connected" : "Disconnected", Toast.LENGTH_SHORT).show();
    }

    public void sendData1(View view) {
        if (connected || edtInput2.getText().toString().length() != 0){
            mReceiveTask.write1(edtInput2.getText().toString());
            Protector.appendLog(edtInput2.getText().toString());
        }
    }

    public void sendData2(View view) {
        if (connected || edtInput2.getText().toString().length() != 0){
            mReceiveTask.write2(edtInput2.getText().toString());
            Protector.appendLog(edtInput2.getText().toString());
        }
    }

    public static class BluetoothConnectThread extends Thread {
        protected String TAG = "BluetoothConnectThread";

        protected final BluetoothSocket mmSocket;

        public BluetoothSocket getSocket() {
            return mmSocket;
        }

        public BluetoothConnectThread(BluetoothDevice device, UUID uuid) {
            BluetoothSocket tmp = null;
            try {
                tmp = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) {
                e.printStackTrace();
                // NOP.
            }
            mmSocket = tmp;
        }


        public void run() {
            if (mmSocket == null) {
                return;
            }

            try {
                mmSocket.connect();
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    mmSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return;
            }
            Log.i(TAG, "Bluetooth connecting.");
        }
    }

    public static class SerialPortProfileConnectThread extends BluetoothConnectThread {

        // "00001101-0000-1000-8000-00805f9b34fb" = SPP (シリアルポートプロファイル) の UUID.
        public static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

        public SerialPortProfileConnectThread(BluetoothDevice device) {
            super(device, SPP_UUID);
            TAG = "SerialPortProfileConnectThread";
        }
    }

    static class BluetoothReceiveTask extends Thread {
        public static final String TAG = "BluetoothReceiveTask";

        Handler handler;
        protected InputStream mInputStream;
        protected OutputStream mOutputStream;

        protected BluetoothSocket mSocket;

        protected volatile boolean mIsCancel;

        public BluetoothReceiveTask(BluetoothSocket socket, Handler handler) {
            this.handler = handler;
            mIsCancel = false;
            mSocket = null;
            mInputStream = null;
            mOutputStream = null;
            if (socket == null) {
                Log.e(TAG, "parameter socket is null.");
                return;
            }

            try {
                mInputStream = socket.getInputStream();
                mOutputStream = socket.getOutputStream();
                mSocket = socket;
                Message readMsg = handler.obtainMessage(
                        1, "Connected".getBytes(StandardCharsets.UTF_8).length, -1,
                        "Connected".getBytes(StandardCharsets.UTF_8));
                readMsg.sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void write(String value) {
            try {
                value += '\n';
                mOutputStream.write(value.getBytes());
                Message readMsg = handler.obtainMessage(
                        1, ("Sent rules : " + value).getBytes(StandardCharsets.UTF_8).length, -1,
                        ("Sent rules : " + value).getBytes(StandardCharsets.UTF_8));
                readMsg.sendToTarget();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void write1(String value) {
            try {
                value += '\r';
                mOutputStream.write(value.getBytes());
                Message readMsg = handler.obtainMessage(
                        1, ("Sent rules : " + value).getBytes(StandardCharsets.UTF_8).length, -1,
                        ("Sent rules : " + value).getBytes(StandardCharsets.UTF_8));
                readMsg.sendToTarget();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void write2(String value) {
            try {
                value += "\r\n";
                mOutputStream.write(value.getBytes());
                Message readMsg = handler.obtainMessage(
                        1, ("Sent rules : " + value).getBytes(StandardCharsets.UTF_8).length, -1,
                        ("Sent rules : " + value).getBytes(StandardCharsets.UTF_8));
                readMsg.sendToTarget();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run() {
            byte[] buffer = new byte[64];
            int readSize;

            Log.i(TAG, "start read task.");
            Message readMsg = handler.obtainMessage(
                    1, "Listenting from sensor".getBytes(StandardCharsets.UTF_8).length, -1,
                    "Listenting from sensor".getBytes(StandardCharsets.UTF_8));

            readMsg.sendToTarget();
            while (mInputStream != null) {
                if (mIsCancel) {
                    break;
                }

                try {
                    readSize = mInputStream.read(buffer);
                    Protector.appendLogSensor(readSize + "");
//                    if (readSize == 64) {
//                        // 処理.
//                    } else {
//                        Log.e(TAG, "NG " + readSize + "byte");
//                    }
                    String str = new String(buffer, StandardCharsets.UTF_8);

                    Message readMsg1 = handler.obtainMessage(
                            1, ("Received : " + str).getBytes(StandardCharsets.UTF_8).length, -1,
                            ("Received : " + str).getBytes(StandardCharsets.UTF_8));
                    readMsg1.sendToTarget();
                    Thread.sleep(0);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                } catch (InterruptedException e) {
                    Log.e(TAG, "InterruptedException!!");
                    // NOP.
                    break;
                }
            }
            Log.i(TAG, "exit read task.");
        }

        public void cancel() {
            mIsCancel = true;
        }

        public void finish() {
            if (mSocket == null) {
                return;
            }

            try {
                mSocket.close();
                Message readMsg1 = handler.obtainMessage(
                        1, ("Disconnected").getBytes(StandardCharsets.UTF_8).length, -1,
                        ("Disconnected").getBytes(StandardCharsets.UTF_8));
                readMsg1.sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}