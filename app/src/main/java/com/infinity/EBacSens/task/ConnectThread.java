package com.infinity.EBacSens.task;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.util.Log;

import com.infinity.EBacSens.activitys.MainActivity;
import com.infinity.EBacSens.views.ViewConnectThread;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ConnectThread extends Thread {

    private InputStream mmInStream;
    private OutputStream mmOutStream;
    private byte[] mmBuffer; // mmBuffer store for the stream
     // mmBuffer store for the stream
    ViewConnectThread callback;

    private Handler handler; // handler that gets info from Bluetooth service

    // Defines several constants used when transmitting messages between the
    // service and the UI.
    private interface MessageConstants {
        public static final int MESSAGE_READ = 4;
        public static final int MESSAGE_WRITE = 5;
        public static final int MESSAGE_TOAST = 6;

        // ... (Add other message types here as needed.)
    }

    BluetoothSocket mmSocket = null;

    public ConnectThread(BluetoothSocket socket , Handler handler , ViewConnectThread callback) {
        this.mmSocket = socket;
        this.handler = handler;
        this.callback = callback;

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams; using temp objects because
        // member streams are final.
        try {
            tmpIn = socket.getInputStream();
        } catch (IOException e) {
            Log.e("Connection", "Error occurred when creating input stream", e);
        }
        try {
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.e("Connection", "Error occurred when creating output stream", e);
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void connect(){
        try {
            mmSocket.connect();
            callback.onConnected();
        } catch (Exception e) {
            if (mmSocket != null) {
                try {
                    mmSocket.close();
                    callback.onError(e.getMessage());
                } catch (IOException e1) {
                    callback.onError(e1.getMessage());
                }
                mmSocket = null;
            }else {
                callback.onError(e.getMessage());
            }
        }
    }

    public void run() {
        callback.onRuned();
        mmBuffer = new byte[1024];

        int numBytes = 0; // bytes returned from read()
        String result = "";
        // Keep listening to the InputStream until an exception occurs.
        while (true) {
            try {
                if (mmInStream.available() > 0) {
                    byte[] mmBufferTemp = new byte[mmInStream.available()];

                    numBytes += mmInStream.read(mmBufferTemp);

                    String str = new String(mmBufferTemp, StandardCharsets.UTF_8);
                    result += str;

                    if (result.contains("[CR]") || result.contains("\n") || result.contains("\r") || result.contains("\r\n") || result.contains("*MEASUREFINISH")){
                        result = result.replace("[CR]" , "").replace("\n" , "").replace("\n" , "").replace("\r\n" , "");
                        Message readMsg = handler.obtainMessage(
                                MessageConstants.MESSAGE_READ, result.getBytes(StandardCharsets.UTF_8).length, -1,
                                result.getBytes());
                        readMsg.sendToTarget();
                        numBytes = 0;
                        result ="";
                    }
                    //Thread.sleep(100);
                }
            } catch (IOException e) {
                Log.d("Connection", "Input stream was disconnected", e);
                break;
            }

        }
    }

    public void write(String value) {
        try {
            value += "\n";
            mmOutStream.write(value.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            if (mmSocket != null){
                mmSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}