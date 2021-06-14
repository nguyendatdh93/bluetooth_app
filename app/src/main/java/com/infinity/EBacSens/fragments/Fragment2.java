package com.infinity.EBacSens.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.activitys.MainActivity;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.TimeZone;
import com.infinity.EBacSens.presenter.PresenterFragment2;
import com.infinity.EBacSens.task.ConnectThread;
import com.infinity.EBacSens.views.ViewConnectThread;
import com.infinity.EBacSens.views.ViewFragment2Listener;

import java.io.IOException;
import java.util.ArrayList;

import static com.infinity.EBacSens.activitys.MainActivity.STATE_CONNECTED;
import static com.infinity.EBacSens.activitys.MainActivity.STATE_DISCONNECTED;
import static com.infinity.EBacSens.activitys.MainActivity.STATE_LISTENING;
import static com.infinity.EBacSens.activitys.MainActivity.connectThread;
import static com.infinity.EBacSens.activitys.MainActivity.mBluetoothAdapter;
import static com.infinity.EBacSens.retrofit2.APIUtils.PBAP_UUID;

public class Fragment2 extends Fragment implements ViewConnectThread, Handler.Callback , ViewFragment2Listener {

    private View view;
    private Context context;

    private EditText edtNameMeasure, edtDatetime, edtPowerOffMin, edtPeakMode;
    private Button btnRead, btnWrite;

    private Dialog dialogProcessing;
    private TextView txtDatetime;

    // popup
    private LinearLayout containerPopup;
    private ImageView imgTitle;
    private TextView txtTitle, txtContent;

    // 1 = write , 0 = read
    private int statusButton = 0;
    private Handler handler;
    private int countTryConnect = 0;
    private final int maxTryConnect = 2;

    private ArrayList<String> arrRules;
    private ArrayList<String> arrResults;

    private PresenterFragment2 presenterFragment2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_2, container, false);
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
                connectThread = new ConnectThread(context,mBluetoothAdapter.getRemoteDevice(MainActivity.device.getMacDevice()).createInsecureRfcommSocketToServiceRecord(ParcelUuid.fromString(PBAP_UUID).getUuid()), handler, this);
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
        } else {
            showPopup(context.getResources().getString(R.string.failure), "Device not support Bluetooth.", false);
        }
    }

    private void addEvents() {
        btnWrite.setOnClickListener(v -> {
            if (edtNameMeasure.getText().toString().length() == 0) {
                edtNameMeasure.setError("error");
                edtNameMeasure.requestFocus();
                return;
            }
            if (edtPeakMode.getText().toString().length() == 0 || Protector.tryParseInt(edtPeakMode.getText().toString()) > 2 || Protector.tryParseInt(edtPeakMode.getText().toString()) < 0) {
                edtPeakMode.setError("error");
                edtPeakMode.requestFocus();
                return;
            }
            if (Protector.tryParseInt(edtPowerOffMin.getText().toString()) > 60) {
                edtPowerOffMin.setError("error");
                edtPowerOffMin.requestFocus();
                return;
            }
            statusButton = 1;
            if (mBluetoothAdapter != null) {
                connectSensor();
            } else {
                showPopup(context.getResources().getString(R.string.failure), "Device not support Bluetooth.", false);
            }
        });
        btnRead.setOnClickListener(v -> {
            if (edtPeakMode.getText().toString().length() == 0 || Protector.tryParseInt(edtPeakMode.getText().toString()) > 2 || Protector.tryParseInt(edtPeakMode.getText().toString()) < 0) {
                edtPeakMode.setError("error");
                edtPeakMode.requestFocus();
                return;
            }
            if (Protector.tryParseInt(edtPowerOffMin.getText().toString()) > 60) {
                edtPowerOffMin.setError("error");
                edtPowerOffMin.requestFocus();
                return;
            }
            statusButton = 0;
            if (mBluetoothAdapter != null) {
                connectSensor();
            } else {
                showPopup(context.getResources().getString(R.string.failure), "Device not support Bluetooth.", false);
            }
        });

        txtDatetime.setOnClickListener(v -> {
            showDialogProcessing();
            presenterFragment2.receivedGetTimezone("aFPo3lR0Zz8AnK7ngttxZNv7cfIILSdNZtuq43q-rfulJKg5sZ-vh1c4Ymb-PXg5MmuR-mfvR-44BXXtVwfQ7F5cCeerX9AJm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_nRPgeZU6HP9chrVt_7vAh9SpFDN6xYpdGG5RFzrzEq_aK3LiQpDz1Yq4OHReOTuTPy7kGWxj4isIazi4H4vefKSQGe7rCpOzPdHFFlBbjlEoF03_9gXuSm0uHw19_wEoIQ426QlX6Gw" , "44BXXtVwfQ7F5cCeerX9AJm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_nRPgeZU6HP9chrVt_7vAh9SpFDN6xYpdGG5RFzrzEq_aK3LiQpDz1Yq4OHReOTuTPy7kGWxj4isIazi4H4vefKSQGe7rCpOzPdHFFlBbjlEoF03_9gXuSm0uHw19_wEoIQ426QlX6Gw");
        });
    }

    private void addController() {
        presenterFragment2 = new PresenterFragment2(this);

        handler = new Handler(this);

        txtDatetime = view.findViewById(R.id.fragment_2_txt_time);
        edtDatetime = view.findViewById(R.id.fragment_2_edt_datetime);
        edtNameMeasure = view.findViewById(R.id.fragment_2_edt_name_measure);
        edtPowerOffMin = view.findViewById(R.id.fragment_2_edt_power_off_min);
        edtPeakMode = view.findViewById(R.id.fragment_2_edt_peakmode);
        btnWrite = view.findViewById(R.id.fragment_2_btn_write);
        btnRead = view.findViewById(R.id.fragment_2_btn_read);
        edtDatetime.setText(Protector.getCurrentTimeSensor());

        arrRules = new ArrayList<>();
        arrResults = new ArrayList<>();
        initDialogProcessing();
        initPopup();
    }

    private void initDialogProcessing() {
        dialogProcessing = new Dialog(context);
        dialogProcessing.setContentView(R.layout.dialog_processing);
        dialogProcessing.setCancelable(false);
    }

    private void showDialogProcessing() {
        if (dialogProcessing != null) {
            dialogProcessing.show();
        }
    }

    private void cancelDialogProcessing() {
        if (dialogProcessing != null) {
            dialogProcessing.dismiss();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onGetData(String value) {
        // get data from sensor
    }

    @Override
    public void onConnected() {
        if (connectThread != null) {
            connectThread.start();
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
    }

    private void initPopup() {
        containerPopup = view.findViewById(R.id.container_popup);
        ImageButton imgClose = view.findViewById(R.id.fragment_popup_img_close);
        imgTitle = view.findViewById(R.id.fragment_popup_img_title);

        imgClose.setOnClickListener(v -> hidePopup());

        txtTitle = view.findViewById(R.id.fragment_popup_txt_title);
        txtContent = view.findViewById(R.id.fragment_popup_txt_content);
    }

    private void showPopup(String title, String content, boolean success) {
        txtTitle.setText(title);
        txtContent.setText(content);

        if (success) {
            imgTitle.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_green));
            imgTitle.setImageResource(R.drawable.ic_baseline_check_24);
        } else {
            imgTitle.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_red));
            imgTitle.setImageResource(R.drawable.ic_baseline_close_24);
        }

        containerPopup.setVisibility(View.VISIBLE);

        Animation animSlide = AnimationUtils.loadAnimation(context,
                R.anim.left_to_right);
        containerPopup.startAnimation(animSlide);

        final Runnable r = this::hidePopup;
        handler.postDelayed(r, 3000);
    }

    private void hidePopup() {
        if (containerPopup.getVisibility() == View.VISIBLE) {
            Animation animSlide = AnimationUtils.loadAnimation(context,
                    R.anim.right_to_left);
            containerPopup.startAnimation(animSlide);
            containerPopup.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case 10:
                byte[] readBuffError = (byte[]) msg.obj;
                String tempMsgError = new String(readBuffError, 0, msg.arg1);
                tempMsgError = tempMsgError.trim();
                // log file
                Protector.appendLog(context,true, tempMsgError);

                cancelDialogProcessing();
                showPopup(context.getResources().getString(R.string.failure), context.getResources().getString(R.string.processing_failed), false);
            case 4:
                byte[] readBuff = (byte[]) msg.obj;
                String tempMsg = new String(readBuff, 0, msg.arg1);
                tempMsg = tempMsg.trim();
                // log file
                Protector.appendLog(context,true , tempMsg);
                // result sensor
                arrResults.add(tempMsg);
                if (arrRules.size() == 0) {
                    edtNameMeasure.setText(arrResults.get(0));
                    edtDatetime.setText(""+Protector.formatTimeSensor(arrResults.get(1)));
                    edtPeakMode.setText(""+Protector.tryParseInt(arrResults.get(2)));
                    edtPowerOffMin.setText(""+Protector.tryParseInt(arrResults.get(3)));
                    cancelDialogProcessing();
                    showPopup(context.getResources().getString(R.string.done), context.getResources().getString(R.string.the_process_is_complete), true);
                } else {
                    connectThread.write(arrRules.get(0));
                    arrRules.remove(0);
                }

                break;
            case 2:
                countTryConnect = 1;
                MainActivity.device.setStatusConnect(1);

                arrRules.clear();
                arrResults.clear();
                arrRules.add("*" + (statusButton == 1 ? "W,IDNAME," + edtNameMeasure.getText().toString() : "R,IDNAME") + "");
                arrRules.add("*" + (statusButton == 1 ? "W,DATETIME," + edtDatetime.getText().toString().replace("-", " ").replace(" " , "").replace(":","").replace("/","") : "R,DATETIME") + "");
                arrRules.add("*" + (statusButton == 1 ? "W,PEAKMODE," + edtPeakMode.getText().toString() : "R,PEAKMODE") + "");
                arrRules.add("*" + (statusButton == 1 ? "W,POWOFFMIN," + edtPowerOffMin.getText().toString() : "R,POWOFFMIN") + "");
                if (statusButton == 1) {
                    arrRules.add("*W,SAVE");
                }

                if (connectThread != null) {
                    connectThread.write(arrRules.get(0));
                    arrRules.remove(0);
                }

                break;
            case 0:
                MainActivity.device.setStatusConnect(0);
                if (++countTryConnect > maxTryConnect) {
                    countTryConnect = 1;
                    cancelDialogProcessing();
                    showPopup(context.getResources().getString(R.string.failure), context.getResources().getString(R.string.processing_failed), false);
                } else {
                    connectSensor();
                }
                break;
        }
        return false;
    }

    @Override
    public void onGetTime(TimeZone timeZone) {
        if (timeZone!= null){
            String year = (timeZone.getYear()+"").substring(0,2);
            String month;
            if (timeZone.getMonth() < 10){
                month = "0" + timeZone.getMonth();
            }else {
                month = String.valueOf(timeZone.getMonth());
            }
            String day;
            if (timeZone.getDay() < 10){
                day = "0" + timeZone.getDay();
            }else {
                day = String.valueOf(timeZone.getDay());
            }
            String hours;
            if (timeZone.getHours() < 10){
                hours = "0" + timeZone.getHours();
            }else {
                hours = String.valueOf(timeZone.getHours());
            }
            String minutes;
            if (timeZone.getMinutes() < 10){
                minutes = "0" + timeZone.getMinutes();
            }else {
                minutes = String.valueOf(timeZone.getMinutes());
            }
            String seconds;
            if (timeZone.getSeconds() < 10){
                seconds = "0" + timeZone.getSeconds();
            }else {
                seconds = String.valueOf(timeZone.getSeconds());
            }
            edtDatetime.setText(year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds);
        }else {
            edtDatetime.setText(Protector.getCurrentTimeSensor());
        }
        cancelDialogProcessing();
    }
}

