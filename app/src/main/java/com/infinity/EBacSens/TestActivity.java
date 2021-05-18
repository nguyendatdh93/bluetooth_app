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
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        txtResult = findViewById(R.id.txt_result);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        SensorInfor device = (SensorInfor) getIntent().getSerializableExtra("device");

        if (mBluetoothAdapter == null || device.getMacDevice() == null) {
            // エラー: Bluetooth なし.
            Toast.makeText(this, "please , choose other device have mac_address", Toast.LENGTH_SHORT).show();
        }else {
            mBluetoothAdapter.cancelDiscovery();

            connectTread = new SerialPortProfileConnectThread(mBluetoothAdapter.getRemoteDevice(device.getMacDevice()));
            connectTread.start();


            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message msg) {
                    byte[] readBuff = (byte[]) msg.obj;
                    String tempMsg = new String(readBuff, 0, msg.arg1);

                    txtResult.setText(txtResult.getText().toString()+ tempMsg +"\n");

                    return false;
                }
            });
            mReceiveTask = new BluetoothReceiveTask(connectTread.getSocket() , handler);
            mReceiveTask.start();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void sendData(View view) {
        mReceiveTask.write("*R,IDNAME");
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

        public BluetoothReceiveTask(BluetoothSocket socket , Handler handler) {
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
                        1, "Đã kết nối".getBytes(StandardCharsets.UTF_8).length, -1,
                        "Đã kết nối".getBytes(StandardCharsets.UTF_8));
                readMsg.sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void write(String value) {
            try {
                mOutputStream.write(value.getBytes());
                Message readMsg = handler.obtainMessage(
                        1, ("Đã gửi lệnh : " + value).getBytes(StandardCharsets.UTF_8).length, -1,
                        ("Đã gửi lệnh : " + value).getBytes(StandardCharsets.UTF_8));
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
                    1, "Đang đợi kết quả từ sensor".getBytes(StandardCharsets.UTF_8).length, -1,
                    "Đang đợi kết quả từ sensor".getBytes(StandardCharsets.UTF_8));

            readMsg.sendToTarget();
            while (mInputStream != null) {
                if (mIsCancel) {
                    break;
                }

                try {
                    readSize = mInputStream.read(buffer);
                    Protector.appendLogSensor(readSize+"");
//                    if (readSize == 64) {
//                        // 処理.
//                    } else {
//                        Log.e(TAG, "NG " + readSize + "byte");
//                    }
                    String str = new String(buffer, StandardCharsets.UTF_8);

                    Message readMsg1 = handler.obtainMessage(
                            1, ("Đã nhận : " + str).getBytes(StandardCharsets.UTF_8).length, -1,
                            ("Đã nhận : " + str).getBytes(StandardCharsets.UTF_8));
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}