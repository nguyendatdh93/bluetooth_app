package com.infinity.EBacSens.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.activitys.MainActivity;
import com.infinity.EBacSens.adapters.AdapteRCVBacSetting;
import com.infinity.EBacSens.adapters.AdapteRCVDevicePaired;
import com.infinity.EBacSens.adapters.AdapterRCVHistoryMeasure;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.BacSetting;
import com.infinity.EBacSens.model_objects.DataSensorSettingAPI;
import com.infinity.EBacSens.model_objects.ErrorSensorSetting;
import com.infinity.EBacSens.model_objects.FollowSensor;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.presenter.PresenterAdapterRCVDevicePaired;
import com.infinity.EBacSens.presenter.PresenterFragment3;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.task.ConnectThread;
import com.infinity.EBacSens.views.ViewConnectThread;
import com.infinity.EBacSens.views.ViewFragment3Listener;
import com.infinity.EBacSens.views.ViewRCVHistoryMeasure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.infinity.EBacSens.activitys.MainActivity.mBluetoothAdapter;
import static com.infinity.EBacSens.retrofit2.APIUtils.PBAP_UUID;

public class Fragment3 extends Fragment implements ViewFragment3Listener, ViewRCVHistoryMeasure , ViewConnectThread {

    private View view;
    private Activity activity;
    private Context context;
    private RelativeLayout container;

    private Button btnReceiveSettingMeasure, btnSaveSettingMeasure , btnRead , btnWrite;
    private EditText edtNameMEasure, edtCrng, edtEqp1, edtEqt1, edtEqp2, edtEqt2, edtEqp3, edtEqt3, edtEqp4, edtEqt4, edtEqp5, edtEqt5, edtStp, edtEnp, edtPp, edtDlte, edtPwd, edtPtm, edtIbst, edtIben, edtIfst, edtIfen;

    private ArrayList<SensorSetting> arrSensorSetting;

    private RecyclerView rcvBacSetting;
    private ArrayList<BacSetting> arrBacSetting;
    private AdapteRCVBacSetting adapteRCVBacSetting;

    private AdapterRCVHistoryMeasure adapterRCVHistoryMeasure;

    private Dialog dialogProcessing, dialogHistoryMeasure, dialogYesNo;

    private PresenterFragment3 presenterFragment3;
    private Spinner spnNumber;

    private int statusButton;
    private ConnectThread connectThread;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_3, container, false);
        addController();
        addEvents();
        return view;
    }

    private void addEvents() {
        btnReceiveSettingMeasure.setOnClickListener(v -> {
            showDialogProcessing();
            presenterFragment3.receivedReceiveSettingMeasure(APIUtils.token);
        });

        btnSaveSettingMeasure.setOnClickListener(v -> {
            SensorSetting sensorSetting = new SensorSetting();
            sensorSetting.setSetname(edtNameMEasure.getText().toString());
            sensorSetting.setBacs(Protector.tryParseInt(spnNumber.getSelectedItem().toString()));
            sensorSetting.setCrng(Protector.tryParseInt(edtCrng.getText().toString()));
            sensorSetting.setEqp1(Protector.tryParseInt(edtEqp1.getText().toString()));
            sensorSetting.setEqp2(Protector.tryParseInt(edtEqp2.getText().toString()));
            sensorSetting.setEqp3(Protector.tryParseInt(edtEqp3.getText().toString()));
            sensorSetting.setEqp4(Protector.tryParseInt(edtEqp4.getText().toString()));
            sensorSetting.setEqp5(Protector.tryParseInt(edtEqp5.getText().toString()));
            sensorSetting.setEqt1(Protector.tryParseInt(edtEqt1.getText().toString()));
            sensorSetting.setEqt2(Protector.tryParseInt(edtEqt2.getText().toString()));
            sensorSetting.setEqt3(Protector.tryParseInt(edtEqt3.getText().toString()));
            sensorSetting.setEqt4(Protector.tryParseInt(edtEqt4.getText().toString()));
            sensorSetting.setEqt5(Protector.tryParseInt(edtEqt5.getText().toString()));
            sensorSetting.setStp(Protector.tryParseInt(edtStp.getText().toString()));
            sensorSetting.setEnp(Protector.tryParseInt(edtEnp.getText().toString()));
            sensorSetting.setPp(Protector.tryParseInt(edtPp.getText().toString()));
            sensorSetting.setDlte(Protector.tryParseInt(edtDlte.getText().toString()));
            sensorSetting.setPwd(Protector.tryParseInt(edtPwd.getText().toString()));
            sensorSetting.setPtm(Protector.tryParseInt(edtPtm.getText().toString()));
            sensorSetting.setIbst(Protector.tryParseInt(edtIbst.getText().toString()));
            sensorSetting.setIben(Protector.tryParseInt(edtIben.getText().toString()));
            sensorSetting.setIfst(Protector.tryParseInt(edtIfst.getText().toString()));
            sensorSetting.setIfen(Protector.tryParseInt(edtIfen.getText().toString()));
            sensorSetting.setBacSettings(arrBacSetting);
            showDialogProcessing();
            presenterFragment3.receivedSaveSettingMeasure(APIUtils.token, MainActivity.device.getId(), sensorSetting);
        });

        spnNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                arrBacSetting.clear();
                for (int i = 0; i < Protector.tryParseInt(spnNumber.getSelectedItem().toString()); i++) {
                    arrBacSetting.add(new BacSetting(-1 , -1 , "", 1, 1, 1, 1, 1 , "" , ""));
                }
                adapteRCVBacSetting.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        btnWrite.setOnClickListener(v -> {
            statusButton = 1;
            if (mBluetoothAdapter != null) {
                connectSensor();
            }else {
                showErrorMessage("Device not support Bluetooth");
            }
        });

        btnRead.setOnClickListener(v -> {
            statusButton = 0;
            if (mBluetoothAdapter != null) {
                connectSensor();
            }else {
                showErrorMessage("Device not support Bluetooth");
            }
        });
    }

    private void addController() {
        presenterFragment3 = new PresenterFragment3(this);
        container = view.findViewById(R.id.container_fragment_3);
        spnNumber = view.findViewById(R.id.fragment_3_spn_number);
        btnReceiveSettingMeasure = view.findViewById(R.id.fragment_3_btn_receive_setting_measure);
        btnSaveSettingMeasure = view.findViewById(R.id.fragment_3_btn_save_setting_measure);
        btnRead = view.findViewById(R.id.fragment_3_btn_read);
        btnWrite = view.findViewById(R.id.fragment_3_btn_write);
        edtNameMEasure = view.findViewById(R.id.fragment_3_edt_name_measure);
        rcvBacSetting = view.findViewById(R.id.fragment_3_rcv_bac_setting);
        rcvBacSetting.setHasFixedSize(true);
        rcvBacSetting.setNestedScrollingEnabled(false);
        rcvBacSetting.setLayoutManager(new LinearLayoutManager(context));
        edtCrng = view.findViewById(R.id.fragment_3_edt_crng);
        edtEqp1 = view.findViewById(R.id.fragment_3_edt_eqp1);
        edtEqt1 = view.findViewById(R.id.fragment_3_edt_eqt1);
        edtEqp2 = view.findViewById(R.id.fragment_3_edt_eqp2);
        edtEqt2 = view.findViewById(R.id.fragment_3_edt_eqt2);
        edtEqp3 = view.findViewById(R.id.fragment_3_edt_eqp3);
        edtEqt3 = view.findViewById(R.id.fragment_3_edt_eqt3);
        edtEqp4 = view.findViewById(R.id.fragment_3_edt_eqp4);
        edtEqt4 = view.findViewById(R.id.fragment_3_edt_eqt4);
        edtEqp5 = view.findViewById(R.id.fragment_3_edt_eqp5);
        edtEqt5 = view.findViewById(R.id.fragment_3_edt_eqt5);
        edtStp = view.findViewById(R.id.fragment_3_edt_stp);
        edtEnp = view.findViewById(R.id.fragment_3_edt_enp);
        edtPp = view.findViewById(R.id.fragment_3_edt_pp);
        edtDlte = view.findViewById(R.id.fragment_3_edt_dlte);
        edtPwd = view.findViewById(R.id.fragment_3_edt_pwd);
        edtPtm = view.findViewById(R.id.fragment_3_edt_ptm);
        edtIbst = view.findViewById(R.id.fragment_3_edt_ibst);
        edtIben = view.findViewById(R.id.fragment_3_edt_iben);
        edtIfst = view.findViewById(R.id.fragment_3_edt_ifst);
        edtIfen = view.findViewById(R.id.fragment_3_edt_ifen);

        arrBacSetting = new ArrayList<>();
        arrBacSetting.add(new BacSetting(-1 , -1 ,null, 1, 1, 1, 1, 1 , null , null));
        adapteRCVBacSetting = new AdapteRCVBacSetting(context, arrBacSetting);
        rcvBacSetting.setAdapter(adapteRCVBacSetting);

        arrSensorSetting = new ArrayList<>();

        initDialogProcessing();
    }

    private void connectSensor() {
        if (MainActivity.device.getMacDevice() != null && mBluetoothAdapter != null) {
            try {
                if (connectThread != null){
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
        }else {
            showErrorMessage("Device not have mac address");
        }
    }

    private void initDialogProcessing() {
        dialogProcessing = new Dialog(context);
        dialogProcessing.setContentView(R.layout.dialog_processing);
        dialogProcessing.setCancelable(false);
    }

    private void showDialogProcessing() {
        dialogProcessing.show();
    }

    private void cancelDialogProcessing() {
        if (dialogProcessing != null) {
            dialogProcessing.cancel();
        }
    }

    private void setContentSensorSetting(int position) {
        if (arrSensorSetting != null && arrSensorSetting.size() > position) {
            edtNameMEasure.setText(arrSensorSetting.get(position).getSetname());
            edtCrng.setText(String.valueOf(arrSensorSetting.get(position).getCrng()));
            edtEqp1.setText(String.valueOf(arrSensorSetting.get(position).getEqp1()));
            edtEqt1.setText(String.valueOf(arrSensorSetting.get(position).getEqt1()));
            edtEqp2.setText(String.valueOf(arrSensorSetting.get(position).getEqp2()));
            edtEqt2.setText(String.valueOf(arrSensorSetting.get(position).getEqt2()));
            edtEqp3.setText(String.valueOf(arrSensorSetting.get(position).getEqp3()));
            edtEqt3.setText(String.valueOf(arrSensorSetting.get(position).getEqt3()));
            edtEqp4.setText(String.valueOf(arrSensorSetting.get(position).getEqp4()));
            edtEqt4.setText(String.valueOf(arrSensorSetting.get(position).getEqt4()));
            edtEqp5.setText(String.valueOf(arrSensorSetting.get(position).getEqp5()));
            edtEqt5.setText(String.valueOf(arrSensorSetting.get(position).getEqt5()));
            edtStp.setText(String.valueOf(arrSensorSetting.get(position).getEqt5()));
            edtEnp.setText(String.valueOf(arrSensorSetting.get(position).getEqt5()));
            edtPp.setText(String.valueOf(arrSensorSetting.get(position).getPp()));
            edtDlte.setText(String.valueOf(arrSensorSetting.get(position).getDlte()));
            edtPwd.setText(String.valueOf(arrSensorSetting.get(position).getPwd()));
            edtPtm.setText(String.valueOf(arrSensorSetting.get(position).getPtm()));
            edtIbst.setText(String.valueOf(arrSensorSetting.get(position).getIbst()));
            edtIben.setText(String.valueOf(arrSensorSetting.get(position).getIben()));
            edtIfst.setText(String.valueOf(arrSensorSetting.get(position).getIfst()));
            edtIfen.setText(String.valueOf(arrSensorSetting.get(position).getIfen()));

            if (arrSensorSetting.get(position).getBacSettings() != null) {
                arrBacSetting.clear();
                arrBacSetting.addAll(arrSensorSetting.get(position).getBacSettings());
                adapteRCVBacSetting.notifyDataSetChanged();
            }
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    public void onSuccessUpdateSettingSensor() {
        cancelDialogProcessing();
        showSuccessMessage("Success");
    }

    @Override
    public void onFailUpdateSettingSensor(ErrorSensorSetting errorSensorSetting) {
        cancelDialogProcessing();
        if (errorSensorSetting != null && errorSensorSetting.getErrors() != null) {
            if (errorSensorSetting.getErrors().getSetname() != null && errorSensorSetting.getErrors().getSetname().size() > 0) {
                edtNameMEasure.setError(errorSensorSetting.getErrors().getSetname().get(0));
                edtNameMEasure.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getCrng() != null && errorSensorSetting.getErrors().getCrng().size() > 0) {
                edtCrng.setError(errorSensorSetting.getErrors().getCrng().get(0));
                edtCrng.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getEqp1() != null && errorSensorSetting.getErrors().getEqp1().size() > 0) {
                edtEqp1.setError(errorSensorSetting.getErrors().getEqp1().get(0));
                edtEqp1.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getEqt1() != null && errorSensorSetting.getErrors().getEqt1().size() > 0) {
                edtEqt1.setError(errorSensorSetting.getErrors().getEqt1().get(0));
                edtEqt1.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getEqp2() != null && errorSensorSetting.getErrors().getEqp2().size() > 0) {
                edtEqp2.setError(errorSensorSetting.getErrors().getEqp2().get(0));
                edtEqp2.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getEqt2() != null && errorSensorSetting.getErrors().getEqt2().size() > 0) {
                edtEqt2.setError(errorSensorSetting.getErrors().getEqt2().get(0));
                edtEqt2.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getEqp3() != null && errorSensorSetting.getErrors().getEqp3().size() > 0) {
                edtEqp3.setError(errorSensorSetting.getErrors().getEqp3().get(0));
                edtEqp3.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getEqt3() != null && errorSensorSetting.getErrors().getEqt3().size() > 0) {
                edtEqt3.setError(errorSensorSetting.getErrors().getEqt3().get(0));
                edtEqt3.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getEqp4() != null && errorSensorSetting.getErrors().getEqp4().size() > 0) {
                edtEqp4.setError(errorSensorSetting.getErrors().getEqp4().get(0));
                edtEqp4.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getEqt4() != null && errorSensorSetting.getErrors().getEqt4().size() > 0) {
                edtEqt4.setError(errorSensorSetting.getErrors().getEqt4().get(0));
                edtEqt4.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getEqp5() != null && errorSensorSetting.getErrors().getEqp5().size() > 0) {
                edtEqp5.setError(errorSensorSetting.getErrors().getEqp5().get(0));
                edtEqp5.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getEqt5() != null && errorSensorSetting.getErrors().getEqt5().size() > 0) {
                edtEqt5.setError(errorSensorSetting.getErrors().getEqt5().get(0));
                edtEqt5.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getStp() != null && errorSensorSetting.getErrors().getStp().size() > 0) {
                edtStp.setError(errorSensorSetting.getErrors().getStp().get(0));
                edtStp.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getEnp() != null && errorSensorSetting.getErrors().getEnp().size() > 0) {
                edtEnp.setError(errorSensorSetting.getErrors().getEnp().get(0));
                edtEnp.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getPp() != null && errorSensorSetting.getErrors().getPp().size() > 0) {
                edtPp.setError(errorSensorSetting.getErrors().getPp().get(0));
                edtPp.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getDlte() != null && errorSensorSetting.getErrors().getDlte().size() > 0) {
                edtDlte.setError(errorSensorSetting.getErrors().getDlte().get(0));
                edtDlte.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getPwd() != null && errorSensorSetting.getErrors().getPwd().size() > 0) {
                edtPwd.setError(errorSensorSetting.getErrors().getPwd().get(0));
                edtPwd.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getPtm() != null && errorSensorSetting.getErrors().getPtm().size() > 0) {
                edtPtm.setError(errorSensorSetting.getErrors().getPtm().get(0));
                edtPtm.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getIbst() != null && errorSensorSetting.getErrors().getIbst().size() > 0) {
                edtIbst.setError(errorSensorSetting.getErrors().getIbst().get(0));
                edtIbst.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getIben() != null && errorSensorSetting.getErrors().getIben().size() > 0) {
                edtIben.setError(errorSensorSetting.getErrors().getIben().get(0));
                edtIben.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getIfst() != null && errorSensorSetting.getErrors().getIfst().size() > 0) {
                edtIfst.setError(errorSensorSetting.getErrors().getIfst().get(0));
                edtIfst.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getIfen() != null && errorSensorSetting.getErrors().getIfen().size() > 0) {
                edtIfen.setError(errorSensorSetting.getErrors().getIfen().get(0));
                edtIfen.requestFocus();
                return;
            }

            if (errorSensorSetting.getErrors().getBac0bacname() != null && errorSensorSetting.getErrors().getBac0bacname().size() > 0) {
                adapteRCVBacSetting.alertName(0 , errorSensorSetting.getErrors().getBac0bacname().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac1bacname() != null && errorSensorSetting.getErrors().getBac1bacname().size() > 0) {
                adapteRCVBacSetting.alertName(1 , errorSensorSetting.getErrors().getBac1bacname().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac2bacname() != null && errorSensorSetting.getErrors().getBac2bacname().size() > 0) {
                adapteRCVBacSetting.alertName(2 , errorSensorSetting.getErrors().getBac2bacname().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac3bacname() != null && errorSensorSetting.getErrors().getBac3bacname().size() > 0) {
                adapteRCVBacSetting.alertName(3 , errorSensorSetting.getErrors().getBac3bacname().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac4bacname() != null && errorSensorSetting.getErrors().getBac4bacname().size() > 0) {
                adapteRCVBacSetting.alertName(4 , errorSensorSetting.getErrors().getBac4bacname().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac0e1() != null && errorSensorSetting.getErrors().getBac0e1().size() > 0) {
                adapteRCVBacSetting.alertE1(0 , errorSensorSetting.getErrors().getBac0e1().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac1e1() != null && errorSensorSetting.getErrors().getBac1e1().size() > 0) {
                adapteRCVBacSetting.alertE1(1 , errorSensorSetting.getErrors().getBac1e1().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac2e1() != null && errorSensorSetting.getErrors().getBac2e1().size() > 0) {
                adapteRCVBacSetting.alertE1(2 , errorSensorSetting.getErrors().getBac2e1().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac3e1() != null && errorSensorSetting.getErrors().getBac3e1().size() > 0) {
                adapteRCVBacSetting.alertE1(3 , errorSensorSetting.getErrors().getBac3e1().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac4e1() != null && errorSensorSetting.getErrors().getBac4e1().size() > 0) {
                adapteRCVBacSetting.alertE1(4 , errorSensorSetting.getErrors().getBac4e1().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac0e2() != null && errorSensorSetting.getErrors().getBac0e2().size() > 0) {
                adapteRCVBacSetting.alertE2(0 , errorSensorSetting.getErrors().getBac0e2().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac1e2() != null && errorSensorSetting.getErrors().getBac1e2().size() > 0) {
                adapteRCVBacSetting.alertE2(1 , errorSensorSetting.getErrors().getBac1e2().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac2e2() != null && errorSensorSetting.getErrors().getBac2e2().size() > 0) {
                adapteRCVBacSetting.alertE2(2 , errorSensorSetting.getErrors().getBac2e2().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac3e2() != null && errorSensorSetting.getErrors().getBac3e2().size() > 0) {
                adapteRCVBacSetting.alertE2(3 , errorSensorSetting.getErrors().getBac3e2().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac4e2() != null && errorSensorSetting.getErrors().getBac4e2().size() > 0) {
                adapteRCVBacSetting.alertE2(4 , errorSensorSetting.getErrors().getBac4e2().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac0e3() != null && errorSensorSetting.getErrors().getBac0e3().size() > 0) {
                adapteRCVBacSetting.alertE3(0 , errorSensorSetting.getErrors().getBac0e3().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac1e3() != null && errorSensorSetting.getErrors().getBac1e3().size() > 0) {
                adapteRCVBacSetting.alertE3(1 , errorSensorSetting.getErrors().getBac1e3().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac2e3() != null && errorSensorSetting.getErrors().getBac2e3().size() > 0) {
                adapteRCVBacSetting.alertE3(2 , errorSensorSetting.getErrors().getBac2e3().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac3e3() != null && errorSensorSetting.getErrors().getBac3e3().size() > 0) {
                adapteRCVBacSetting.alertE3(3 , errorSensorSetting.getErrors().getBac3e3().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac4e3() != null && errorSensorSetting.getErrors().getBac4e3().size() > 0) {
                adapteRCVBacSetting.alertE3(4 , errorSensorSetting.getErrors().getBac4e3().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac0e4() != null && errorSensorSetting.getErrors().getBac0e4().size() > 0) {
                adapteRCVBacSetting.alertE4(0 , errorSensorSetting.getErrors().getBac0e4().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac1e4() != null && errorSensorSetting.getErrors().getBac1e4().size() > 0) {
                adapteRCVBacSetting.alertE4(1 , errorSensorSetting.getErrors().getBac1e4().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac2e4() != null && errorSensorSetting.getErrors().getBac2e4().size() > 0) {
                adapteRCVBacSetting.alertE4(2 , errorSensorSetting.getErrors().getBac2e4().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac3e4() != null && errorSensorSetting.getErrors().getBac3e4().size() > 0) {
                adapteRCVBacSetting.alertE4(3 , errorSensorSetting.getErrors().getBac3e4().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac4e4() != null && errorSensorSetting.getErrors().getBac4e4().size() > 0) {
                adapteRCVBacSetting.alertE4(4 , errorSensorSetting.getErrors().getBac4e4().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac0pkp() != null && errorSensorSetting.getErrors().getBac0pkp().size() > 0) {
                adapteRCVBacSetting.alertSelectBox(0 , errorSensorSetting.getErrors().getBac0pkp().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac1pkp() != null && errorSensorSetting.getErrors().getBac1pkp().size() > 0) {
                adapteRCVBacSetting.alertSelectBox(1 , errorSensorSetting.getErrors().getBac1pkp().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac2pkp() != null && errorSensorSetting.getErrors().getBac2pkp().size() > 0) {
                adapteRCVBacSetting.alertSelectBox(2 , errorSensorSetting.getErrors().getBac2pkp().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac3pkp() != null && errorSensorSetting.getErrors().getBac3pkp().size() > 0) {
                adapteRCVBacSetting.alertSelectBox(3 , errorSensorSetting.getErrors().getBac3pkp().get(0));
                return;
            }

            if (errorSensorSetting.getErrors().getBac4pkp() != null && errorSensorSetting.getErrors().getBac4pkp().size() > 0) {
                adapteRCVBacSetting.alertSelectBox(4 , errorSensorSetting.getErrors().getBac4pkp().get(0));
            }
        }
    }

    @Override
    public void onGetSettingSensor(ArrayList<SensorSetting> sensorSetting) {
        if (sensorSetting != null) {
            arrSensorSetting.clear();
            arrSensorSetting.addAll(sensorSetting);
        }
        showDialogHistoryMeasure();
        cancelDialogProcessing();
    }

    @Override
    public void onSuccessDeleteSettingSensor(int position) {
        arrSensorSetting.remove(position);
        adapterRCVHistoryMeasure.notifyItemRemoved(position);
        adapterRCVHistoryMeasure.notifyItemRangeChanged(position, arrSensorSetting.size());
        dialogYesNo.cancel();
        cancelDialogProcessing();
    }

    @Override
    public void onFailDeleteSettingSensor(String error) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
        cancelDialogProcessing();
    }

    private void showDialogHistoryMeasure() {
        dialogHistoryMeasure = new Dialog(context);
        dialogHistoryMeasure.setContentView(R.layout.dialog_history_measure);

        RecyclerView rcvHistoryMeasure = dialogHistoryMeasure.findViewById(R.id.dialog_history_measure_rcv);
        rcvHistoryMeasure.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rcvHistoryMeasure.setLayoutManager(linearLayoutManager);

        rcvHistoryMeasure.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));

        adapterRCVHistoryMeasure = new AdapterRCVHistoryMeasure(dialogHistoryMeasure.getContext(), arrSensorSetting, this);
        rcvHistoryMeasure.setAdapter(adapterRCVHistoryMeasure);

        dialogHistoryMeasure.findViewById(R.id.dialog_history_measure_btn_close).setOnClickListener(v -> dialogHistoryMeasure.cancel());

        dialogHistoryMeasure.show();
        Window window = dialogHistoryMeasure.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    private void showErrorMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(container, message, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.RED);
        TextView textView = view.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public void onDeleteRCVHistoryMeasure(int position) {
        dialogYesNo = new Dialog(context);
        dialogYesNo.setContentView(R.layout.dialog_yes_no);

        dialogYesNo.findViewById(R.id.dialog_yes_no_btn_no).setOnClickListener(v -> dialogYesNo.cancel());
        dialogYesNo.findViewById(R.id.dialog_yes_no_btn_yes).setOnClickListener(v -> {
            showDialogProcessing();
            presenterFragment3.receivedDeleteSettingMeasure(APIUtils.token, MainActivity.device.getId(), position);
        });

        dialogYesNo.show();
        Window window = dialogYesNo.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onUseRCVHistoryMeasure(int position) {
        setContentSensorSetting(position);
        if (dialogHistoryMeasure != null) {
            dialogHistoryMeasure.cancel();
        }
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
        MainActivity.device.setStatusConnect(1);
    }

    @Override
    public void onError(String error) {
        cancelDialogProcessing();
        showErrorMessage(error);
    }

    @Override
    public void onRuned() {
        cancelDialogProcessing();
        if (connectThread != null) {
            connectThread.write("*" + (statusButton == 1 ? "R" : 0 ) + ",IDNAME"+ edtNameMEasure.getText().toString());

            // test result
            edtNameMEasure.setText("Name ex response");
        }
    }
}

