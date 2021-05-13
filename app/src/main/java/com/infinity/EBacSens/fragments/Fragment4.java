package com.infinity.EBacSens.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.snackbar.Snackbar;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.activitys.ListDeviceActivity;
import com.infinity.EBacSens.activitys.MainActivity;
import com.infinity.EBacSens.adapters.AdapteRCVDatetime;
import com.infinity.EBacSens.adapters.AdapteRCVDeviceOnline;
import com.infinity.EBacSens.adapters.AdapteRCVGraph;
import com.infinity.EBacSens.adapters.AdapteRCVResult;
import com.infinity.EBacSens.adapters.AdapterRCVHistoryMeasure;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.BacSetting;
import com.infinity.EBacSens.model_objects.Graph;
import com.infinity.EBacSens.model_objects.MeasureMeasbas;
import com.infinity.EBacSens.model_objects.MeasureMeasdets;
import com.infinity.EBacSens.model_objects.MeasureMeasparas;
import com.infinity.EBacSens.model_objects.MeasureMeasress;
import com.infinity.EBacSens.model_objects.ModelRCVDatetime;
import com.infinity.EBacSens.model_objects.Result;
import com.infinity.EBacSens.model_objects.SensorMeasure;
import com.infinity.EBacSens.model_objects.SensorMeasureDetail;
import com.infinity.EBacSens.model_objects.SensorMeasurePage;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.model_objects.VerticalSpaceItemDecoration;
import com.infinity.EBacSens.presenter.PresenterFragment4;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.task.ConnectThread;
import com.infinity.EBacSens.views.ViewAdapterRCVDatetimeListener;
import com.infinity.EBacSens.views.ViewConnectThread;
import com.infinity.EBacSens.views.ViewFragment4Listener;
import com.infinity.EBacSens.views.ViewRCVHistoryMeasure;
import com.opencsv.CSVWriter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.infinity.EBacSens.activitys.MainActivity.STATE_CONNECTED;
import static com.infinity.EBacSens.activitys.MainActivity.STATE_DISCONNECTED;
import static com.infinity.EBacSens.activitys.MainActivity.STATE_LISTENING;
import static com.infinity.EBacSens.activitys.MainActivity.connectThread;
import static com.infinity.EBacSens.activitys.MainActivity.mBluetoothAdapter;
import static com.infinity.EBacSens.retrofit2.APIUtils.PBAP_UUID;

public class Fragment4 extends Fragment implements ViewFragment4Listener, ViewConnectThread, ViewAdapterRCVDatetimeListener, Handler.Callback {

    private View view;
    private Activity activity;
    private Context context;

    private SeekBar skbProgress;
    private Button btnExportCSV, btnHistoryMeasure;
    private TextView txtProcess;

    private RecyclerView rcvResult;
    private ArrayList<Result> arrResult;
    private AdapteRCVResult adapteRCVResult;

    private RecyclerView rcvGraph;
    private ArrayList<Graph> arrGraph;
    private AdapteRCVGraph adapteRCVGraph;

    private RecyclerView rcvDatetime;
    private ArrayList<ModelRCVDatetime> arrRCVDatetime;
    private AdapteRCVDatetime adapteRCVDatetime;

    private ArrayList<SensorMeasure> arrMeasure;

    private Dialog dialogProcessing;

    // popup
    private LinearLayout containerPopup;
    private ImageView imgTitle;
    private TextView txtTitle, txtContent;

    private List<SensorMeasurePage.MeasurePage> arrMeasurePage;
    private PresenterFragment4 presenterFragment4;

    private int positionCSV;
    private SensorMeasure sensorMeasureExport;

    private Handler handler;
    private int countTryConnect = 0;
    private final int maxTryConnect = 2;

    private ArrayList<String> arrRules;
    private ArrayList<String> arrResults;
    private int resultStart = 0;
    private ArrayList<BacSetting> bacSettings;
    private ArrayList<MeasureMeasress> measureMeasresses;
    private ArrayList<MeasureMeasdets> measureMeasdets;
    private SensorSetting sensorSetting;
    private MeasureMeasbas measureMeasbas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_4, container, false);
        addController();
        addEvents();
        return view;
    }

    private void addEvents() {
        btnExportCSV.setOnClickListener(v -> {
            exportFileCSV(sensorMeasureExport);
        });

        btnHistoryMeasure.setOnClickListener(v -> {
            if (mBluetoothAdapter != null) {
                connectSensor();
            } else {
                showPopup("Failed", "Device not have mac address.", false);
            }
        });

        showDialogProcessing();
        presenterFragment4.receivedGetMeasurePage(APIUtils.token, MainActivity.device.getId(), 1, 0);
    }

    private void addController() {
        handler = new Handler(this);
        presenterFragment4 = new PresenterFragment4(this);
        btnExportCSV = view.findViewById(R.id.fragment_4_btn_csv);
        btnHistoryMeasure = view.findViewById(R.id.fragment_4_btn_history_measure);
        skbProgress = view.findViewById(R.id.fragment_4_skb_progress);
        skbProgress.setEnabled(false);
        skbProgress.setPadding(0, 0, 0, 0);
        txtProcess = view.findViewById(R.id.fragment_4_txt_progress);

        rcvResult = view.findViewById(R.id.fragment_4_rcv_result);
        rcvResult.setHasFixedSize(true);
        rcvResult.setNestedScrollingEnabled(false);
        rcvResult.setLayoutManager(new LinearLayoutManager(context));
        arrResult = new ArrayList<>();

        adapteRCVResult = new AdapteRCVResult(context, arrResult);
        rcvResult.setAdapter(adapteRCVResult);

        rcvGraph = view.findViewById(R.id.fragment_4_rcv_graph);
        rcvGraph.setHasFixedSize(true);
        rcvGraph.setNestedScrollingEnabled(false);
        rcvGraph.setLayoutManager(new LinearLayoutManager(context));
        arrGraph = new ArrayList<>();

        adapteRCVGraph = new AdapteRCVGraph(context, arrGraph);
        rcvGraph.setAdapter(adapteRCVGraph);

        rcvDatetime = view.findViewById(R.id.fragment_4_rcv_datetime);
        rcvDatetime.setHasFixedSize(true);
        rcvDatetime.setLayoutManager(new LinearLayoutManager(context));
        arrRCVDatetime = new ArrayList<>();

        adapteRCVDatetime = new AdapteRCVDatetime(context, arrRCVDatetime, this);
        rcvDatetime.setAdapter(adapteRCVDatetime);

        arrMeasure = new ArrayList<>();
        arrMeasurePage = new ArrayList<>();

        arrRules = new ArrayList<>();
        arrResults = new ArrayList<>();
        bacSettings = new ArrayList<>();

        initDialogProcessing();
        initPopup();
    }

    private void initPopup() {
        containerPopup = view.findViewById(R.id.container_popup);
        ImageButton imgClose = view.findViewById(R.id.fragment_popup_img_close);
        imgTitle = view.findViewById(R.id.fragment_popup_img_title);

        imgClose.setOnClickListener(v -> {
            hidePopup();
        });

        txtTitle = view.findViewById(R.id.fragment_popup_txt_title);
        txtContent = view.findViewById(R.id.fragment_popup_txt_content);
    }

    private void showPopup(String title, String content, boolean success) {
        txtTitle.setText(title);
        txtContent.setText(content);

        if (success) {
            imgTitle.setBackground(context.getResources().getDrawable(R.drawable.circle_green));
            imgTitle.setImageResource(R.drawable.ic_baseline_check_24);
        } else {
            imgTitle.setBackground(context.getResources().getDrawable(R.drawable.circle_red));
            imgTitle.setImageResource(R.drawable.ic_baseline_close_24);
        }

        containerPopup.setVisibility(View.VISIBLE);

        Animation animSlide = AnimationUtils.loadAnimation(context,
                R.anim.left_to_right);
        containerPopup.startAnimation(animSlide);

        final Runnable r = new Runnable() {
            public void run() {
                hidePopup();
            }
        };
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

    private void connectSensor() {
        if (MainActivity.device.getMacDevice() != null && mBluetoothAdapter != null) {
            try {
                if (connectThread != null) {
                    connectThread.cancel();
                }
                connectThread = new ConnectThread(mBluetoothAdapter.getRemoteDevice(MainActivity.device.getMacDevice()).createInsecureRfcommSocketToServiceRecord(ParcelUuid.fromString(PBAP_UUID).getUuid()), handler, this);
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
            showPopup("Failed", "Device not have mac address.", false);
        }
    }

    private void initDialogProcessing() {
        dialogProcessing = new Dialog(context);
        dialogProcessing.setContentView(R.layout.dialog_processing);
        dialogProcessing.setCancelable(false);
    }

    private void showDialogProcessing() {
        if (dialogProcessing == null) {
            dialogProcessing = new Dialog(context);
            dialogProcessing.setContentView(R.layout.dialog_processing);
            dialogProcessing.setCancelable(false);
        }
        dialogProcessing.show();
    }

    private void cancelDialogProcessing() {
        if (dialogProcessing != null) {
            dialogProcessing.cancel();
        }
    }

    private void exportFileCSV(SensorMeasure sensorMeasure) {
        if (sensorMeasure != null) {
            PermissionListener permissionListener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    skbProgress.setProgress(0);
                    txtProcess.setText("Exporting...");
                    txtProcess.setTextColor(context.getResources().getColor(R.color.black));

                    Observable.create(emitter -> {
                        File folder = new File(Environment.getExternalStorageDirectory() +
                                File.separator + "/EBacSens");
                        boolean success;
                        if (!folder.exists()) {
                            success = folder.mkdirs();
                        }

                        FileOutputStream os = new FileOutputStream(Environment.getExternalStorageDirectory() +
                                File.separator + "/EBacSens/ExportResult_" + Protector.getCurrentTime().replace(":", "-") + ".csv");
                        os.write(0xef);
                        os.write(0xbb);
                        os.write(0xbf);

                        CSVWriter writer;
                        try {
                            writer = new CSVWriter(new OutputStreamWriter(os));
                            List<String[]> data = new ArrayList<>();
                            data.add(new String[]{"測定日時", sensorMeasure.getDatetime()});
                            data.add(new String[]{"データ番号", sensorMeasure.getNo()});
                            data.add(new String[]{"設定名", String.valueOf(sensorMeasure.getMeasureMeasparas().getSetname())});
                            data.add(new String[]{"測定数", String.valueOf(sensorMeasure.getMeasureMeasparas().getBacs())});
                            data.add(new String[]{"レンジ", String.valueOf(sensorMeasure.getMeasureMeasparas().getCrng())});
                            data.add(new String[]{"平衡電位1", String.valueOf(sensorMeasure.getMeasureMeasparas().getEqp1())});
                            data.add(new String[]{"平衡時間1", String.valueOf(sensorMeasure.getMeasureMeasparas().getEqt1())});
                            data.add(new String[]{"平衡電位2", String.valueOf(sensorMeasure.getMeasureMeasparas().getEqp2())});
                            data.add(new String[]{"平衡時間2", String.valueOf(sensorMeasure.getMeasureMeasparas().getEqt2())});
                            data.add(new String[]{"平衡電位3", String.valueOf(sensorMeasure.getMeasureMeasparas().getEqp3())});
                            data.add(new String[]{"平衡時間3", String.valueOf(sensorMeasure.getMeasureMeasparas().getEqt3())});
                            data.add(new String[]{"平衡電位4", String.valueOf(sensorMeasure.getMeasureMeasparas().getEqp4())});
                            data.add(new String[]{"平衡時間4", String.valueOf(sensorMeasure.getMeasureMeasparas().getEqt4())});
                            data.add(new String[]{"平衡電位5", String.valueOf(sensorMeasure.getMeasureMeasparas().getEqp5())});
                            data.add(new String[]{"平衡時間5", String.valueOf(sensorMeasure.getMeasureMeasparas().getEqt5())});
                            data.add(new String[]{"開始電位", String.valueOf(sensorMeasure.getMeasureMeasparas().getStp())});
                            data.add(new String[]{"終了電位", String.valueOf(sensorMeasure.getMeasureMeasparas().getEnp())});
                            data.add(new String[]{"パルス振幅", String.valueOf(sensorMeasure.getMeasureMeasparas().getPp())});
                            data.add(new String[]{"ΔE", String.valueOf(sensorMeasure.getMeasureMeasparas().getDlte())});
                            data.add(new String[]{"パルス幅", String.valueOf(sensorMeasure.getMeasureMeasparas().getPwd())});
                            data.add(new String[]{"パルス期間", String.valueOf(sensorMeasure.getMeasureMeasparas().getPtm())});
                            data.add(new String[]{"ベース電流サンプル時間下限", String.valueOf(sensorMeasure.getMeasureMeasparas().getIbst())});
                            data.add(new String[]{"ベース電流サンプル時間上限", String.valueOf(sensorMeasure.getMeasureMeasparas().getIben())});
                            data.add(new String[]{"ファラデー電流サンプル時間下限", String.valueOf(sensorMeasure.getMeasureMeasparas().getIfst())});
                            data.add(new String[]{"ファラデー電流サンプル時間上限", String.valueOf(sensorMeasure.getMeasureMeasparas().getIfen())});
                            for (int i = 0; i < sensorMeasure.getMeasureMeasparas().getArrBac().size(); i++) {
                                data.add(new String[]{"微生物" + (i + 1), sensorMeasure.getMeasureMeasparas().getArrBac().get(i).getBacName()});
                                data.add(new String[]{"E1ベースライン検索開始電位", String.valueOf(sensorMeasure.getMeasureMeasparas().getArrBac().get(i).getE1())});
                                data.add(new String[]{"E2ベースライン検索開始電位", String.valueOf(sensorMeasure.getMeasureMeasparas().getArrBac().get(i).getE2())});
                                data.add(new String[]{"E3ベースライン検索終了電位", String.valueOf(sensorMeasure.getMeasureMeasparas().getArrBac().get(i).getE3())});
                                data.add(new String[]{"E4ベースライン検索終了電位", String.valueOf(sensorMeasure.getMeasureMeasparas().getArrBac().get(i).getE4())});
                                data.add(new String[]{"ピーク位置", String.valueOf(sensorMeasure.getMeasureMeasparas().getArrBac().get(i).getPkp())});
                            }
                            data.add(new String[]{});

                            if (sensorMeasure.getMeasureMeasresses().size() == 1) {
                                data.add(new String[]{"測定対象物名", sensorMeasure.getMeasureMeasresses().get(0).getName()});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getPkpot())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getDltc())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBgc())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpsx())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpsy())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpex())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpey())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getErr())});
                            } else if (sensorMeasure.getMeasureMeasresses().size() == 2) {
                                data.add(new String[]{"測定対象物名", sensorMeasure.getMeasureMeasresses().get(0).getName(), sensorMeasure.getMeasureMeasresses().get(1).getName()});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getPkpot()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getPkpot())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getDltc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getDltc())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBgc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBgc())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpsx()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpsx())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpsy()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpsy())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpex()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpex())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpey()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpey())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getErr()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getErr())});
                            } else if (sensorMeasure.getMeasureMeasresses().size() == 3) {
                                data.add(new String[]{"測定対象物名", sensorMeasure.getMeasureMeasresses().get(0).getName(), sensorMeasure.getMeasureMeasresses().get(1).getName(), sensorMeasure.getMeasureMeasresses().get(2).getName()});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getPkpot()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getPkpot()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getPkpot())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getDltc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getDltc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getDltc())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBgc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBgc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBgc())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpsx()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpsx()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBlpsx())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpsy()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpsy()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBlpsy())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpex()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpex()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBlpex())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpey()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpey()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBlpey())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getErr()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getErr()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getErr())});
                            } else if (sensorMeasure.getMeasureMeasresses().size() == 4) {
                                data.add(new String[]{"測定対象物名", sensorMeasure.getMeasureMeasresses().get(0).getName(), sensorMeasure.getMeasureMeasresses().get(1).getName(), sensorMeasure.getMeasureMeasresses().get(2).getName(), sensorMeasure.getMeasureMeasresses().get(3).getName()});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getPkpot()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getPkpot()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getPkpot()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getPkpot())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getDltc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getDltc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getDltc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getDltc())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBgc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBgc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBgc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getBgc())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpsx()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpsx()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBlpsx()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getBlpsx())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpsy()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpsy()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBlpsy()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getBlpsy())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpex()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpex()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBlpex()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getBlpex())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpey()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpey()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBlpey()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getBlpey())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getErr()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getErr()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getErr()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getErr())});
                            } else if (sensorMeasure.getMeasureMeasresses().size() == 5) {
                                data.add(new String[]{"測定対象物名", sensorMeasure.getMeasureMeasresses().get(0).getName(), sensorMeasure.getMeasureMeasresses().get(1).getName(), sensorMeasure.getMeasureMeasresses().get(2).getName(), sensorMeasure.getMeasureMeasresses().get(3).getName(), sensorMeasure.getMeasureMeasresses().get(4).getName()});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getPkpot()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getPkpot()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getPkpot()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getPkpot()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(4).getPkpot())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getDltc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getDltc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getDltc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getDltc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(4).getDltc())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBgc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBgc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBgc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getBgc()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(4).getBgc())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpsx()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpsx()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBlpsx()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getBlpsx()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(4).getBlpsx())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpsy()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpsy()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBlpsy()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getBlpsy()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(4).getBlpsy())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpex()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpex()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBlpex()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getBlpex()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(4).getBlpex())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getBlpey()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getBlpey()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getBlpey()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getBlpey()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(4).getBlpey())});
                                data.add(new String[]{"ピーク電位", String.valueOf(sensorMeasure.getMeasureMeasresses().get(0).getErr()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(1).getErr()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(2).getErr()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(3).getErr()), String.valueOf(sensorMeasure.getMeasureMeasresses().get(4).getErr())});
                            }

                            data.add(new String[]{});
                            data.add(new String[]{"No", "E(mV)", "DeltaI(uA)", "Eb", "Ib", "Ef", "If"});

                            for (int i = 0; i < sensorMeasure.getMeasureMeasdets().size(); i++) {
                                data.add(new String[]{String.valueOf(sensorMeasure.getMeasureMeasdets().get(i).getNo()),
                                        String.valueOf(sensorMeasure.getMeasureMeasdets().get(i).getDeltae()),
                                        String.valueOf(sensorMeasure.getMeasureMeasdets().get(i).getDeltai()),
                                        String.valueOf(sensorMeasure.getMeasureMeasdets().get(i).getEb()),
                                        String.valueOf(sensorMeasure.getMeasureMeasdets().get(i).getIb()),
                                        String.valueOf(sensorMeasure.getMeasureMeasdets().get(i).getEf()),
                                        String.valueOf(sensorMeasure.getMeasureMeasdets().get(i).get_if())});
                            }

                            for (int i = 0; i < data.size(); i++) {
                                writer.writeNext(data.get(i));
                                int percent = i * 100 / data.size();
                                int[] ii = {percent};
                                emitter.onNext(ii);
                                SystemClock.sleep(1);
                            }

                            writer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        emitter.onComplete();
                    }).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(o -> {
                        // update progress
                        int[] i = (int[]) o;
                        skbProgress.setProgress(i[0]);
                        txtProcess.setText(i[0] + "%");
                    }, t -> {
                        // on error
                        txtProcess.setText(t.getMessage());
                    }, () -> {
                        // progress tamom shod
                        skbProgress.setProgress(100);
                        txtProcess.setText("Done!");
                        txtProcess.setTextColor(context.getResources().getColor(R.color.green));
                        showPopup("Success", "Exported CSV.", true);
                    });
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {
                    showPopup("Failed", "Access denied", false);
                }
            };
            TedPermission.with(context).setPermissionListener(permissionListener).setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).check();
        } else {
            showPopup("Success", "Null response.", false);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    public void onGetDataMeasurePage(List<SensorMeasurePage.MeasurePage> measurePages) {
        if (measurePages != null) {
            arrMeasurePage.clear();
            arrRCVDatetime.clear();
            arrMeasurePage.addAll(measurePages);
            for (int i = 0; i < measurePages.size(); i++) {
                arrRCVDatetime.add(new ModelRCVDatetime(measurePages.get(i).getDatetime(), false));
            }
            adapteRCVDatetime.notifyDataSetChanged();
            if (arrRCVDatetime.size() > 0) {
                arrRCVDatetime.get(0).setSelected(true);
                adapteRCVDatetime.notifyItemChanged(0);
                skbProgress.setProgress(0);
                positionCSV = 0;
                showDialogProcessing();
                presenterFragment4.receivedGetDetailMeasure(APIUtils.token, arrMeasurePage.get(0).getId());
            }
        }
        cancelDialogProcessing();
    }

    @Override
    public void onGetDataMeasureDetail(SensorMeasureDetail sensorMeasureDetail) {
        arrGraph.clear();
        arrResult.clear();
        if (sensorMeasureDetail != null && sensorMeasureDetail.getSensorMeasure().getMeasureMeasresses() != null) {
            sensorMeasureExport = sensorMeasureDetail.getSensorMeasure();
            for (int i = 0; i < sensorMeasureExport.getMeasureMeasparas().getArrBac().size() && sensorMeasureExport.getMeasureMeasresses().size() > i; i++) {
                arrGraph.add(new Graph(
                        sensorMeasureExport.getMeasureMeasparas().getArrBac().get(i).getBacName(),
                        (sensorMeasureExport.getMeasureMeasresses().get(i).getDltc() / (sensorMeasureExport.getMeasureMeasresses().get(0).getPkpot() == 0 ? 1 : sensorMeasureExport.getMeasureMeasresses().get(0).getPkpot())),
                        level((sensorMeasureExport.getMeasureMeasresses().get(i).getDltc() / (sensorMeasureExport.getMeasureMeasresses().get(0).getPkpot() == 0 ? 1 : sensorMeasureExport.getMeasureMeasresses().get(0).getPkpot()))),
                        "ピーク高さ／ピーク電位"
                ));
            }

            for (int i = 0; i < sensorMeasureExport.getMeasureMeasresses().size(); i++) {
                arrResult.add(new Result(sensorMeasureExport.getMeasureMeasresses().get(i).getName(),
                        String.valueOf(sensorMeasureExport.getMeasureMeasresses().get(i).getPkpot()),
                        String.valueOf(sensorMeasureExport.getMeasureMeasresses().get(i).getDltc()),
                        String.valueOf(sensorMeasureExport.getMeasureMeasresses().get(i).getBgc()),
                        String.valueOf(sensorMeasureExport.getMeasureMeasresses().get(i).getErr())
                ));
            }

        }
        adapteRCVGraph.notifyDataSetChanged();
        adapteRCVResult.notifyDataSetChanged();
        cancelDialogProcessing();
    }

    @Override
    public void onSuccessStoreMeasure(SensorMeasure sensorMeasure) {
        showPopup("Success", "Stored.", true);

        arrRCVDatetime.clear();
        arrMeasure.clear();
        presenterFragment4.receivedGetMeasurePage(APIUtils.token, MainActivity.device.getId(), 1, 0);
        skbProgress.setProgress(100);
        txtProcess.setText("Success Stored!");
        txtProcess.setTextColor(context.getResources().getColor(R.color.black));
    }

    @Override
    public void onFailStoreMeasure(String error) {
        cancelDialogProcessing();
        showPopup("Failed", error, false);
        Protector.appendLog(error);
    }

    private int level(int val) {
        if (val <= 1000) {
            return 1;
        } else if (val <= 2000) {
            return 2;
        } else if (val <= 3000) {
            return 3;
        } else if (val <= 4000) {
            return 4;
        } else {
            return 5;
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

    @Override
    public void onClickRCVDatetime(int position) {
        skbProgress.setProgress(0);
        txtProcess.setText("");
        positionCSV = position;
        showDialogProcessing();
        presenterFragment4.receivedGetDetailMeasure(APIUtils.token, arrMeasurePage.get(position).getId());
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case 4:
                byte[] readBuff = (byte[]) msg.obj;
                String tempMsg = new String(readBuff, 0, msg.arg1);

                // log file
                Protector.appendLogSensor(tempMsg);

                // result sensor
                arrResults.add(tempMsg);
                if (resultStart == 0) {
                    resultStart++;
                    for (int i = 0; i < Protector.tryParseInt(arrResults.get(0)); i++) {
                        arrRules.add("*R,BACNAME" + (i + 1) + "");
                        arrRules.add("*R,E1_" + (i + 1) + "");
                        arrRules.add("*R,E2_" + (i + 1) + "");
                        arrRules.add("*R,E3_" + (i + 1) + "");
                        arrRules.add("*R,E4_" + (i + 1) + "");
                        arrRules.add("*R,PKP" + (i + 1) + "");
                    }
                    arrResults.clear();
                    connectThread.write(arrRules.get(0));
                    Protector.appendLog(arrRules.get(0));
                    arrRules.remove(0);
                }

                if (resultStart == 1) {
                    if (arrRules.size() != 0) {
                        connectThread.write(arrRules.get(0));
                        Protector.appendLog(arrRules.get(0));
                        arrRules.remove(0);
                    } else {
                        resultStart++;

                        for (int i = 0; i < arrResults.size(); i += 6) {
                            bacSettings.add(new BacSetting(MainActivity.device.getId(),
                                    MainActivity.device.getId(), arrResults.get(i),
                                    Protector.tryParseInt(arrResults.get(i + 1)),
                                    Protector.tryParseInt(arrResults.get(i + 2)),
                                    Protector.tryParseInt(arrResults.get(i + 3)),
                                    Protector.tryParseInt(arrResults.get(i + 4)),
                                    Protector.tryParseInt(arrResults.get(i + 5)), Protector.getCurrentTime(), Protector.getCurrentTime()));
                        }

                        arrResults.clear();

                        arrRules.add("*R,SETNAME");
                        arrRules.add("*R,CRNG");
                        arrRules.add("*R,EQP1");
                        arrRules.add("*R,EQT1");
                        arrRules.add("*R,EQP2");
                        arrRules.add("*R,EQT2");
                        arrRules.add("*R,EQP3");
                        arrRules.add("*R,EQT3");
                        arrRules.add("*R,EQP4");
                        arrRules.add("*R,EQT4");
                        arrRules.add("*R,EQP5");
                        arrRules.add("*R,EQT5");
                        arrRules.add("*R,STP");
                        arrRules.add("*R,ENP");
                        arrRules.add("*R,PP");
                        arrRules.add("*R,DLTE");
                        arrRules.add("*R,PWD");
                        arrRules.add("*R,PTM");
                        arrRules.add("*R,IBST");
                        arrRules.add("*R,IBEN");
                        arrRules.add("*R,IFST");
                        arrRules.add("*R,IFEN");

                        connectThread.write(arrRules.get(0));
                        Protector.appendLog(arrRules.get(0));
                        arrRules.remove(0);
                    }
                }
                if (resultStart == 3) {
                    if (arrRules.size() != 0) {
                        connectThread.write(arrRules.get(0));
                        arrRules.remove(0);
                    } else {
                        resultStart++;
                        sensorSetting = new SensorSetting(MainActivity.device.getId(),
                                arrResults.get(0),
                                bacSettings.size(),
                                Protector.tryParseInt(arrResults.get(1)),
                                Protector.tryParseInt(arrResults.get(2)),
                                Protector.tryParseInt(arrResults.get(3)),
                                Protector.tryParseInt(arrResults.get(4)),
                                Protector.tryParseInt(arrResults.get(5)),
                                Protector.tryParseInt(arrResults.get(6)),
                                Protector.tryParseInt(arrResults.get(7)),
                                Protector.tryParseInt(arrResults.get(8)),
                                Protector.tryParseInt(arrResults.get(9)),
                                Protector.tryParseInt(arrResults.get(10)),
                                Protector.tryParseInt(arrResults.get(11)),
                                Protector.tryParseInt(arrResults.get(12)),
                                Protector.tryParseInt(arrResults.get(13)),
                                Protector.tryParseInt(arrResults.get(14)),
                                Protector.tryParseInt(arrResults.get(15)),
                                Protector.tryParseInt(arrResults.get(16)),
                                Protector.tryParseInt(arrResults.get(17)),
                                Protector.tryParseInt(arrResults.get(18)),
                                Protector.tryParseInt(arrResults.get(19)),
                                Protector.tryParseInt(arrResults.get(20)),
                                Protector.tryParseInt(arrResults.get(21)), MainActivity.device.getId(), Protector.getCurrentTime(), Protector.getCurrentTime(), Protector.getCurrentTime(), bacSettings);
                        arrResults.clear();
                        arrRules.add("*R_DATETIME");
                        arrRules.add("*R_NUM");
                        arrRules.add("*R_PSTATERR");

                        connectThread.write(arrRules.get(0));
                        Protector.appendLog(arrRules.get(0));
                        arrRules.remove(0);
                    }
                }
                if (resultStart == 4) {
                    if (arrRules.size() != 0) {
                        connectThread.write(arrRules.get(0));
                        arrRules.remove(0);
                    } else {
                        resultStart++;
                        measureMeasbas = new MeasureMeasbas(MainActivity.device.getId(),
                                arrResults.get(0), Protector.tryParseInt(arrResults.get(1)), Protector.tryParseInt(arrResults.get(2)), "", "");
                        arrResults.clear();

                        arrRules.add("*MEASRES");
                        connectThread.write(arrRules.get(0));
                        Protector.appendLog(arrRules.get(0));
                        arrRules.remove(0);
                    }
                }
                if (resultStart == 5) {
                    if (arrRules.size() != 0) {
                        connectThread.write(arrRules.get(0));
                        arrRules.remove(0);
                    } else {
                        resultStart++;
                        for (int i = 0; i < Protector.tryParseInt(arrResults.get(0)); i++) {
                            arrRules.add("*R_NAME_" + (i + 1) + "");
                            arrRules.add("*R_PKPOT_" + (i + 1) + "");
                            arrRules.add("*R_DLTC_" + (i + 1) + "");
                            arrRules.add("*R_BGC_" + (i + 1) + "");
                            arrRules.add("*R_ERR_" + (i + 1) + "");
                            arrRules.add("*R_BLPSX_" + (i + 1) + "");
                            arrRules.add("*R_BLPSY_" + (i + 1) + "");
                            arrRules.add("*R_BLPEX_" + (i + 1) + "");
                            arrRules.add("*R_BLPEY_" + (i + 1) + "");
                        }
                        arrResults.clear();
                        connectThread.write(arrRules.get(0));
                        Protector.appendLog(arrRules.get(0));
                        arrRules.remove(0);
                    }
                }
                if (resultStart == 6) {
                    if (arrRules.size() != 0) {
                        connectThread.write(arrRules.get(0));
                        arrRules.remove(0);
                    } else {
                        resultStart++;
                        for (int i = 0; i < arrResults.size(); i += 8) {
                            measureMeasresses = new ArrayList<>();
                            measureMeasresses.add(new MeasureMeasress(MainActivity.device.getId(),
                                    arrResults.get(i),
                                    Protector.tryParseInt(arrResults.get(i + 1)),
                                    Protector.tryParseInt(arrResults.get(i + 2)),
                                    Protector.tryParseInt(arrResults.get(i + 3)),
                                    Protector.tryParseInt(arrResults.get(i + 4)),
                                    arrResults.get(i + 5),
                                    arrResults.get(i + 6),
                                    arrResults.get(i + 7),
                                    arrResults.get(i + 8), Protector.getCurrentTime(), Protector.getCurrentTime()));
                        }

                        arrRules.add("＊RAWDMP8,10");
                        arrResults.clear();
                        connectThread.write(arrRules.get(0));
                        Protector.appendLog(arrRules.get(0));
                        arrRules.remove(0);
                    }
                }
                if (resultStart == 7) {
                    if (arrRules.size() != 0) {
                        connectThread.write(arrRules.get(0));
                        Protector.appendLog(arrRules.get(0));
                        arrRules.remove(0);
                    } else {
                        resultStart++;
                        int no = 1;
                        for (int i = 0; i < arrResults.get(0).length(); i += 24) {
                            measureMeasdets = new ArrayList<>();
                            measureMeasdets.add(new MeasureMeasdets(MainActivity.device.getId(),
                                    (no++) + "",
                                    Integer.parseInt(arrResults.get(0).substring(i, (i + 4)), 16),
                                    Integer.parseInt(arrResults.get(0).substring((i + 4), (i + 8)), 16),
                                    Integer.parseInt(arrResults.get(0).substring((i + 8), (i + 12)), 16),
                                    Integer.parseInt(arrResults.get(0).substring((i + 12), (i + 16)), 16),
                                    Integer.parseInt(arrResults.get(0).substring((i + 16), (i + 20)), 16),
                                    Integer.parseInt(arrResults.get(0).substring((i + 20), (i + 24)), 16),
                                    Protector.getCurrentTime(), Protector.getCurrentTime(), Protector.getCurrentTime()));
                        }

                        arrResults.clear();
                        presenterFragment4.receivedStoreMeasure(APIUtils.token, MainActivity.device.getId(), Protector.getCurrentTime(), "06", sensorSetting, measureMeasbas, measureMeasresses, measureMeasdets);
                    }
                }
                break;
            case 2:
                countTryConnect = 1;
                MainActivity.device.setStatusConnect(1);

                arrRules.clear();
                arrResults.clear();
                bacSettings.clear();

                if (connectThread != null) {
                    resultStart = 0;
                    arrRules.add("*R,BACS");
                    connectThread.write(arrRules.get(0));
                    Protector.appendLog(arrRules.get(0));
                    arrResults.remove(0);
                }
                break;
            case 1:
                MainActivity.device.setStatusConnect(1);
                cancelDialogProcessing();
                break;
            case 0:
                MainActivity.device.setStatusConnect(0);
                cancelDialogProcessing();
                if (++countTryConnect >= maxTryConnect) {
                    countTryConnect = 1;
                    showPopup("Failed", "Something went terribly wrong.\n" + "Try again.", false);
                } else {
                    connectSensor();
                }
                break;
        }
        return false;
    }
}

