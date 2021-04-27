package com.infinity.EBacSens.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.activitys.MainActivity;
import com.infinity.EBacSens.adapters.AdapteRCVBacSetting;
import com.infinity.EBacSens.adapters.AdapteRCVDevicePaired;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.BacSetting;
import com.infinity.EBacSens.model_objects.FollowSensor;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.presenter.PresenterAdapterRCVDevicePaired;
import com.infinity.EBacSens.presenter.PresenterFragment3;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.views.ViewFragment3Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Fragment3 extends Fragment implements ViewFragment3Listener {

    private View view;
    private Activity activity;
    private Context context;

    private Button btnReceiveSettingMeasure , btnSaveSettingMeasure;
    private AutoCompleteTextView acpTxtName;
    private EditText edtCrng, edtBacs , edtEqp1 , edtEqt1, edtEqp2 , edtEqt2, edtEqp3 , edtEqt3, edtEqp4 , edtEqt4, edtEqp5 , edtEqt5 , edtStp , edtEnp, edtPp , edtDlte, edtPwd , edtPtm, edtIbst , edtIben, edtIfst , edtIfen;

    private List<String> arrAcpName;
    private List<SensorSetting> arrSensorSetting;
    private ArrayAdapter<String> adapterAcpName;

    private RecyclerView rcvBacSetting;
    private ArrayList<BacSetting> arrBacSetting;
    private AdapteRCVBacSetting adapteRCVBacSetting;

    private Dialog dialogProcessing;

    private PresenterFragment3 presenterFragment3;

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
            presenterFragment3.receivedReceiveSettingMeasure(APIUtils.token, MainActivity.device.getId());
        });

        btnSaveSettingMeasure.setOnClickListener(v -> {
            SensorSetting sensorSetting = new SensorSetting();
            sensorSetting.setSetname(acpTxtName.getText().toString());
            sensorSetting.setBacs(Protector.tryParseInt(edtBacs.getText().toString()));
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

            List<BacSetting> arrBacSetting = new ArrayList<>();
            sensorSetting.setBacSetting(arrBacSetting);
            sensorSetting.getBacSetting().add(new BacSetting("bac0" , 1 , 1 , 1 , 1 , 1 ));
            sensorSetting.getBacSetting().add(new BacSetting("bac1" , 1 , 1 , 1 , 1 , 1 ));

            showDialogProcessing();
            presenterFragment3.receivedUpdateSettingMeasure(APIUtils.token, MainActivity.device.getId() , sensorSetting);
        });

        acpTxtName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setContentSensorSetting(position);
            }
        });
    }

    private void addController() {
        presenterFragment3 = new PresenterFragment3(this);
        btnReceiveSettingMeasure = view.findViewById(R.id.fragment_3_btn_receive_setting_measure);
        btnSaveSettingMeasure = view.findViewById(R.id.fragment_3_btn_save_setting_measure);
        acpTxtName = view.findViewById(R.id.fragment_3_acp_name_device);
        rcvBacSetting = view.findViewById(R.id.fragment_3_rcv_bac_setting);
        rcvBacSetting.setHasFixedSize(true);
        rcvBacSetting.setNestedScrollingEnabled(false);
        rcvBacSetting.setLayoutManager(new LinearLayoutManager(context));
        edtCrng = view.findViewById(R.id.fragment_3_edt_crng);
        edtBacs = view.findViewById(R.id.fragment_3_edt_bacs);
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

        arrAcpName = new ArrayList<>();
        adapterAcpName = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrAcpName);
        acpTxtName.setAdapter(adapterAcpName);

        arrBacSetting = new ArrayList<>();
        adapteRCVBacSetting = new AdapteRCVBacSetting(context , arrBacSetting);
        rcvBacSetting.setAdapter(adapteRCVBacSetting);
        initDialogProcessing();
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

    private void setContentSensorSetting(int position){
        if (arrSensorSetting != null && arrSensorSetting.size() > position){
            acpTxtName.setText(arrSensorSetting.get(position).getSetname());
            edtCrng.setText(String.valueOf(arrSensorSetting.get(position).getCrng()));
            edtBacs.setText(String.valueOf(arrSensorSetting.get(position).getBacs()));
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

            if (arrSensorSetting.get(position).getBacSetting() != null){
                arrBacSetting.clear();
                arrBacSetting.addAll(arrSensorSetting.get(position).getBacSetting());
                adapteRCVBacSetting.notifyDataSetChanged();
            }
        }
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
        Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailUpdateSettingSensor(String error) {
        cancelDialogProcessing();
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetSettingSensor(List<SensorSetting> sensorSetting) {
        arrSensorSetting = sensorSetting;
        if (sensorSetting != null && sensorSetting.size() > 0) {
            // add to acpName
            adapterAcpName.clear();
            for (int i = 0 ; i < sensorSetting.size();i++){
                adapterAcpName.add(sensorSetting.get(i).getSetname());
            }
            adapterAcpName.notifyDataSetChanged();
            setContentSensorSetting(arrSensorSetting.size()-1);
        }

        cancelDialogProcessing();
    }
}

