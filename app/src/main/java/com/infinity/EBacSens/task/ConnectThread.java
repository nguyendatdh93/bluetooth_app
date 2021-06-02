package com.infinity.EBacSens.task;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.infinity.EBacSens.views.ViewConnectThread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ConnectThread extends Thread {

    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private final ViewConnectThread callback;

    private final Handler handler; // handler that gets info from Bluetooth service

    private boolean isMeasBas = false;
    private boolean isList = false;

    private interface MessageConstants {
        int MESSAGE_READ = 4;
        int MESSAGE_WRITE = 5;
        int MESSAGE_TOAST = 6;
        int MESSAGE_ERR = 10;

        // ... (Add other message types here as needed.)
    }

    BluetoothSocket mmSocket;

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
        // mmBuffer store for the stream
        //byte[] mmBuffer = new byte[1024];

        int numBytes = 0; // bytes returned from read()
        StringBuilder result = new StringBuilder();
        // Keep listening to the InputStream until an exception occurs.
        while (true) {
            try {
                if (mmInStream.available() > 0) {
                    byte[] mmBufferTemp = new byte[mmInStream.available()];

                    numBytes += mmInStream.read(mmBufferTemp);

                    String str = new String(mmBufferTemp, StandardCharsets.UTF_8);
                    result.append(str);

//                    if (result.toString().contains("[CR]") || result.toString().contains("\n") || result.toString().contains("\r") || result.toString().contains("\r\n") && (result.toString().contains("*ERR"))){
//                        Message readMsg = handler.obtainMessage(
//                                MessageConstants.MESSAGE_ERR, result.toString().getBytes(StandardCharsets.UTF_8).length, -1,
//                                result.toString().getBytes());
//                        readMsg.sendToTarget();
//                        numBytes = 0;
//
//                        result = new StringBuilder();
//                    }else
                    if (isList){
                        if (result.toString().contains("[CR]") || result.toString().contains("\n") || result.toString().contains("\r") || result.toString().contains("\r\n") || result.toString().contains("*MEASUREFINISH")){
                            Message readMsg = handler.obtainMessage(
                                    MessageConstants.MESSAGE_READ, result.toString().getBytes(StandardCharsets.UTF_8).length, -1,
                                    result.toString().getBytes());
                            readMsg.sendToTarget();
                            numBytes = 0;

                            result = new StringBuilder();
                        }
                    }else
                    if (isMeasBas){
                        if (result.toString().contains("[CR]") || result.toString().contains("\n") || result.toString().contains("\r") || result.toString().contains("\r\n") || result.toString().contains("*MEASUREFINISH")  ){
                            Message readMsg = handler.obtainMessage(
                                    MessageConstants.MESSAGE_READ, result.toString().getBytes(StandardCharsets.UTF_8).length, -1,
                                    result.toString().getBytes());
                            readMsg.sendToTarget();
                            numBytes = 0;

                            result = new StringBuilder();
                        }
                    }else {
//                        if (result.toString().contains("*LIST")){
//                            if (result.toString().contains("*LISTEND")){
//                                Message readMsg = handler.obtainMessage(
//                                        MessageConstants.MESSAGE_READ, result.toString().getBytes(StandardCharsets.UTF_8).length, -1,
//                                        result.toString().getBytes());
//                                readMsg.sendToTarget();
//                                numBytes = 0;
//                                result = new StringBuilder();
//                            }
//                        }else
                            if (result.toString().contains("[CR]") || result.toString().contains("\n") || result.toString().contains("\r") || result.toString().contains("\r\n") || result.toString().contains("*MEASUREFINISH")){
                            result = new StringBuilder(result.toString().replace("[CR]", "").replace("\n", "").replace("\r", "").replace("\r\n", ""));
                            String[] values = result.toString().split(",");
                            String data ="";
                            if (values.length > 1){
                                data = values[1];
                            }

                            Message readMsg = handler.obtainMessage(
                                    MessageConstants.MESSAGE_READ, data.getBytes(StandardCharsets.UTF_8).length, -1,
                                    data.getBytes());
                            readMsg.sendToTarget();

                            numBytes = 0;
                            result = new StringBuilder();
                        }
                    }
                }
            } catch (IOException e) {
                Log.d("Connection", "Input stream was disconnected", e);
                break;
            }

        }
    }

    public void writeLine() {
        try {
            mmOutStream.write("\r".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String value) {
        this.isMeasBas = false;
        this.isList = false;
        try {
            value += "\r";
            mmOutStream.write(value.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeMeasBas(String value) {
        this.isMeasBas = true;
        try {
            value += "\r";
            mmOutStream.write(value.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeList(String value) {
        this.isList = true;
        this.isMeasBas = false;
        try {
            value += "\r";
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