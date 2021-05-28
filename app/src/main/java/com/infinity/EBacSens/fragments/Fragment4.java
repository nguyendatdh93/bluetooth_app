package com.infinity.EBacSens.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.activitys.ListDeviceActivity;
import com.infinity.EBacSens.activitys.MainActivity;
import com.infinity.EBacSens.adapters.AdapteRCVDatetime;
import com.infinity.EBacSens.adapters.AdapteRCVGraph;
import com.infinity.EBacSens.adapters.AdapteRCVResult;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.BacSetting;
import com.infinity.EBacSens.model_objects.Graph;
import com.infinity.EBacSens.model_objects.MeasureMeasbas;
import com.infinity.EBacSens.model_objects.MeasureMeasdets;
import com.infinity.EBacSens.model_objects.MeasureMeasparas;
import com.infinity.EBacSens.model_objects.MeasureMeasress;
import com.infinity.EBacSens.model_objects.ModelRCVDatetime;
import com.infinity.EBacSens.model_objects.Result;
import com.infinity.EBacSens.model_objects.ResultListSensor;
import com.infinity.EBacSens.model_objects.SensorMeasure;
import com.infinity.EBacSens.model_objects.SensorMeasureDetail;
import com.infinity.EBacSens.model_objects.SensorMeasurePage;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.presenter.PresenterFragment4;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.task.ConnectThread;
import com.infinity.EBacSens.views.ViewAdapterRCVDatetimeListener;
import com.infinity.EBacSens.views.ViewConnectThread;
import com.infinity.EBacSens.views.ViewFragment4Listener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<MeasureMeasress> measureMeasresses = new ArrayList<>();
    private ArrayList<MeasureMeasdets> measureMeasdets;
    private SensorSetting sensorSetting;
    private MeasureMeasbas measureMeasbas = new MeasureMeasbas();
    private MeasureMeasparas measureMeasparas = new MeasureMeasparas();
    private MeasureMeasress measureMeasress = new MeasureMeasress();
    private BacSetting bacSetting = new BacSetting();
    private ArrayList<BacSetting> bacSettings = new ArrayList<>();
    private String noDet;

    private final ArrayList<ResultListSensor> resultListSensors = new ArrayList<>();
    private final ArrayList<ResultListSensor> resultListSensorsTemp = new ArrayList<>();
    private final ArrayList<String> rulersBas = new ArrayList<>();
    private final ArrayList<String> rulersPara = new ArrayList<>();
    private final ArrayList<String> rulersRes = new ArrayList<>();
    private final ArrayList<String> rulersDet = new ArrayList<>();

    private ArrayList<MeasureMeasbas> resultBas = new ArrayList<>();
    private ArrayList<MeasureMeasparas> resultParas = new ArrayList<>();
    private ArrayList<ArrayList<MeasureMeasress>> resultRess = new ArrayList<>();
    private ArrayList<ArrayList<MeasureMeasdets>> resultDets = new ArrayList<>();
    private int posReadDet = 0;

    private int timesMeasBas = 0 , timesMeasPara = 0 , timesMeasRes = -1 , timesDet = 0;
    private int numMeasres = 0 , numBacs = 0;

    private int counterStore = 0;

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
                showPopup(context.getResources().getString(R.string.failure), "Device not have mac address.", false);
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
        //rcvDatetime.setHasFixedSize(true);
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
            showPopup(context.getResources().getString(R.string.failure), "Device not have mac address.", false);
        }
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

    private void exportFileCSV(SensorMeasure sensorMeasure) {
        if (sensorMeasure != null) {
            Dexter.withContext(context)
                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            skbProgress.setProgress(0);
                            txtProcess.setText(context.getResources().getString(R.string.exporting));
                            txtProcess.setTextColor(context.getResources().getColor(R.color.black));

                            Observable.create(emitter -> {
                                File folder = new File(Environment.getExternalStorageDirectory() +
                                        File.separator + "/eBacSens");
                                boolean success;
                                if (!folder.exists()) {
                                    success = folder.mkdirs();
                                }

                                FileOutputStream os = new FileOutputStream(Environment.getExternalStorageDirectory() +
                                        File.separator + "/eBacSens/ExportResult_" + Protector.getCurrentTime().replace(":", "-") + ".csv");
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

                                    switch (sensorMeasure.getMeasureMeasparas().getCrng()) {
                                        case 0:
                                            data.add(new String[]{"レンジ", "3mA"});
                                            break;
                                        case 1:
                                            data.add(new String[]{"レンジ", "300uA"});
                                            break;
                                        case 2:
                                            data.add(new String[]{"レンジ", "30uA"});
                                            break;
                                        default:
                                            data.add(new String[]{"レンジ", "unknown"});
                                    }

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
                                        data.add(new String[]{sensorMeasure.getMeasureMeasdets().get(i).getNo(), String.valueOf(sensorMeasure.getMeasureMeasdets().get(i).getDeltae()), String.valueOf(sensorMeasure.getMeasureMeasdets().get(i).getDeltai()), String.valueOf(sensorMeasure.getMeasureMeasdets().get(i).getEb()), String.valueOf(sensorMeasure.getMeasureMeasdets().get(i).getIb()), String.valueOf(sensorMeasure.getMeasureMeasdets().get(i).getEf()), String.valueOf(sensorMeasure.getMeasureMeasdets().get(i).get_if())});
                                    }

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
                                txtProcess.setText(context.getResources().getString(R.string.done));
                                txtProcess.setTextColor(context.getResources().getColor(R.color.green));
                                showPopup(context.getResources().getString(R.string.done), context.getResources().getString(R.string.the_process_is_complete), true);
                            });
                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            showPopup(context.getResources().getString(R.string.failure), "Access denied", false);
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                    }).check();
        } else {
            showPopup(context.getResources().getString(R.string.failure), "Null response.", false);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
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
            for (int i = 0; sensorMeasureExport.getMeasureMeasparas() != null && sensorMeasureExport.getMeasureMeasparas().getArrBac() != null && i < sensorMeasureExport.getMeasureMeasparas().getArrBac().size() && sensorMeasureExport.getMeasureMeasresses() != null && sensorMeasureExport.getMeasureMeasresses().size() > i; i++) {
                arrGraph.add(new Graph(
                        sensorMeasureExport.getMeasureMeasparas().getArrBac().get(i).getBacName(),
                        (sensorMeasureExport.getMeasureMeasresses().get(i).getDltc() / (sensorMeasureExport.getMeasureMeasresses().get(0).getPkpot() == 0 ? 1 : sensorMeasureExport.getMeasureMeasresses().get(0).getPkpot())),
                        level((sensorMeasureExport.getMeasureMeasresses().get(i).getDltc() / (sensorMeasureExport.getMeasureMeasresses().get(0).getPkpot() == 0 ? 1 : sensorMeasureExport.getMeasureMeasresses().get(0).getPkpot()))),
                        "ピーク高さ／ピーク電位"
                ));
            }

            for (int i = 0; sensorMeasureExport.getMeasureMeasresses() != null && i < sensorMeasureExport.getMeasureMeasresses().size(); i++) {
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
        if (resultListSensors.size() == ++counterStore) {
            showPopup(context.getResources().getString(R.string.done), context.getResources().getString(R.string.success_stored), true);
            arrRCVDatetime.clear();
            arrMeasure.clear();
            presenterFragment4.receivedGetMeasurePage(APIUtils.token, MainActivity.device.getId(), 1, 0);
            skbProgress.setProgress(100);
            txtProcess.setText(context.getResources().getString(R.string.success_stored));
            txtProcess.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    @Override
    public void onFailStoreMeasure(String error) {
        cancelDialogProcessing();
        showPopup(context.getResources().getString(R.string.failure), error, false);
        Protector.appendLog(true, error);
    }

    private int level(float val) {
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
                tempMsg = tempMsg.trim();
                // log file
                Protector.appendLog(true, tempMsg);

                // result sensor
                arrResults.add(tempMsg);
                if (resultStart == 0) {
                    resultStart++;
                    for (int i = 0; i < Protector.tryParseInt(tempMsg.split(",")[1]); i++) {
                        arrRules.add(i + "");
                    }
                    arrRules.remove(0);
                    connectThread.writeLine();

                } else if (resultStart == 1) {
                    resultListSensorsTemp.add(new ResultListSensor(tempMsg.trim().split(",")[1] , Protector.tryParseInt(tempMsg.trim().split(",")[2])));
                    if (arrRules.size() != 0){
                        connectThread.writeLine();
                        arrRules.remove(0);
                    }else {
                        resultStart++;

                        for (int i = 0; i < resultListSensorsTemp.size(); i++) {
                            boolean isHave = false;
                            for (int j = 0; j < arrMeasurePage.size(); j++) {
                                if (resultListSensorsTemp.get(i).getDatetime().replace("/", "-").equals(arrMeasurePage.get(j).getDatetime().replace("/", "-"))) {
                                    isHave = true;
                                    break;
                                }
                            }
                            if (!isHave) {
                                // read sensor
                                resultListSensors.add(new ResultListSensor(resultListSensorsTemp.get(i).getDatetime().replace("/", "-"), resultListSensorsTemp.get(i).getNo()));
                            }

                        }
                        if (resultListSensors.size() > 0) {
                            for (int i = 0; i < resultListSensors.size(); i++) {
                                rulersBas.add("*R,MEASBAS," + resultListSensors.get(i).getNo());
                                rulersPara.add("*R,MEASPARA," + resultListSensors.get(i).getNo());
                                rulersRes.add("*R,MEASRES," + resultListSensors.get(i).getNo());
                                rulersDet.add("*R,MEASDET," + resultListSensors.get(i).getNo());
                            }
                            arrResults.clear();

                            connectThread.writeMeasBas(rulersBas.get(0));
                            Protector.appendLog(false, rulersBas.get(0));
                            rulersBas.remove(0);
                        } else {
                            cancelDialogProcessing();
                            showPopup(context.getResources().getString(R.string.done), context.getResources().getString(R.string.success_stored), true);
                        }
                    }
                }
                else if (resultStart == 2) {
                    if (rulersBas.size() != 0) {
                        timesMeasBas++;
                        if (timesMeasBas %3 == 1){
                            measureMeasbas.setDatetime(tempMsg.split(",")[1].replace("/","-"));
                            connectThread.writeLine();
                        }else if (timesMeasBas %3 == 2){
                            measureMeasbas.setNum(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasBas %3 == 0){
                            measureMeasbas.setPstaterr(Protector.tryParseInt(tempMsg.split(",")[1]));
                            resultBas.add(measureMeasbas);
                            measureMeasbas = new MeasureMeasbas();
                            connectThread.writeMeasBas(rulersBas.get(0));
                            rulersBas.remove(0);
                        }
                    } else {
                        timesMeasBas++;
                        if (timesMeasBas %3 == 1){
                            measureMeasbas.setDatetime(tempMsg.split(",")[1].replace("/","-"));
                            connectThread.writeLine();
                        }else if (timesMeasBas %3 == 2){
                            measureMeasbas.setNum(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasBas %3 == 0){
                            measureMeasbas.setPstaterr(Protector.tryParseInt(tempMsg.split(",")[1]));
                            resultBas.add(measureMeasbas);
                            measureMeasbas = new MeasureMeasbas();
                            resultStart++;
                            connectThread.writeMeasBas(rulersPara.get(0));
                            Protector.appendLog(false, rulersPara.get(0));
                            rulersPara.remove(0);
                        }
                    }
                } else if (resultStart == 3) {
                    if (rulersPara.size() != 0) {
                        timesMeasPara++;
                        if (timesMeasPara %23 == 1 && timesMeasPara <= 23){
                            measureMeasparas.setSetname(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 2 && timesMeasPara <= 23){
                            measureMeasparas.setBacs(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 3 && timesMeasPara <= 23){
                            measureMeasparas.setCrng(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 4 && timesMeasPara <= 23){
                            measureMeasparas.setEqp1(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 5 && timesMeasPara <= 23){
                            measureMeasparas.setEqt1(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 6 && timesMeasPara <= 23){
                            measureMeasparas.setEqp2(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 7 && timesMeasPara <= 23){
                            measureMeasparas.setEqt2(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 8 && timesMeasPara <= 23){
                            measureMeasparas.setEqp3(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 9 && timesMeasPara <= 23){
                            measureMeasparas.setEqt3(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 10 && timesMeasPara <= 23){
                            measureMeasparas.setEqp4(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 11 && timesMeasPara <= 23){
                            measureMeasparas.setEqt4(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 12 && timesMeasPara <= 23){
                            measureMeasparas.setEqp5(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 13 && timesMeasPara <= 23){
                            measureMeasparas.setEqt5(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 14 && timesMeasPara <= 23){
                            measureMeasparas.setStp(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 15 && timesMeasPara <= 23){
                            measureMeasparas.setEnp(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 16 && timesMeasPara <= 23){
                            measureMeasparas.setPp(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 17 && timesMeasPara <= 23){
                            measureMeasparas.setDlte(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 18 && timesMeasPara <= 23){
                            measureMeasparas.setPwd(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 19 && timesMeasPara <= 23){
                            measureMeasparas.setPtm(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 20 && timesMeasPara <= 23){
                            measureMeasparas.setIbst(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 21 && timesMeasPara <= 23){
                            measureMeasparas.setIben(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 22 && timesMeasPara <= 23){
                            measureMeasparas.setIfst(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara % 23 == 0 && timesMeasPara <= 23){
                            measureMeasparas.setIfen(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (numBacs/6 < measureMeasparas.getBacs()){
                            switch (numBacs % 6){
                                case 0:
                                    bacSetting.setBacName(tempMsg.split(",")[1]);
                                    break;
                                case 1:
                                    bacSetting.setE1(Protector.tryParseInt(tempMsg.split(",")[1]));
                                    break;
                                case 2:
                                    bacSetting.setE2(Protector.tryParseInt(tempMsg.split(",")[1]));
                                    break;
                                case 3:
                                    bacSetting.setE3(Protector.tryParseInt(tempMsg.split(",")[1]));
                                    break;
                                case 4:
                                    bacSetting.setE4(Protector.tryParseInt(tempMsg.split(",")[1]));
                                    break;
                                case 5:
                                    bacSetting.setPkp(Protector.tryParseInt(tempMsg.split(",")[1]));
                                    bacSettings.add(new BacSetting(
                                            bacSetting.getId(),
                                            MainActivity.device.getId(),
                                            bacSetting.getBacName(),
                                            bacSetting.getE1(),
                                            bacSetting.getE2(),
                                            bacSetting.getE3(),
                                            bacSetting.getE4(),
                                            bacSetting.getPkp(),
                                            bacSetting.getUpdatedAt(),
                                            bacSetting.getCreatedAt()
                                    ));
                                    bacSetting = new BacSetting();
                                    break;
                            }
                            numBacs++;

                            if (numBacs/6 >= measureMeasparas.getBacs()){
                                measureMeasparas.setArrBac(bacSettings);
                                ArrayList<BacSetting> bacSettingsTemp = new ArrayList<>();
                                bacSettingsTemp.addAll(bacSettings);

                                resultParas.add(new MeasureMeasparas(
                                        MainActivity.device.getId(),
                                        measureMeasparas.getSetname(),
                                        measureMeasparas.getBacs(),
                                        measureMeasparas.getCrng(),
                                        measureMeasparas.getEqp1(),
                                        measureMeasparas.getEqt1(),
                                        measureMeasparas.getEqp2(),
                                        measureMeasparas.getEqt2(),
                                        measureMeasparas.getEqp3(),
                                        measureMeasparas.getEqt3(),
                                        measureMeasparas.getEqp4(),
                                        measureMeasparas.getEqt4(),
                                        measureMeasparas.getEqp5(),
                                        measureMeasparas.getEqt5(),
                                        measureMeasparas.getStp(),
                                        measureMeasparas.getEnp(),
                                        measureMeasparas.getPp(),
                                        measureMeasparas.getDlte(),
                                        measureMeasparas.getPwd(),
                                        measureMeasparas.getPtm(),
                                        measureMeasparas.getIbst(),
                                        measureMeasparas.getIben(),
                                        measureMeasparas.getIfst(),
                                        measureMeasparas.getIfen(),
                                        measureMeasparas.getUpdatedAt(),
                                        measureMeasparas.getCreatedAt(),
                                        bacSettingsTemp
                                ));

                                measureMeasparas = new MeasureMeasparas();
                                bacSettings.clear();
                                timesMeasPara = 0;
                                numBacs = 0;
                                connectThread.writeMeasBas(rulersPara.get(0));
                                Protector.appendLog(false, rulersPara.get(0));
                                rulersPara.remove(0);
                            }else {
                                connectThread.writeLine();
                            }
                        }
                    } else {
                        timesMeasPara++;
                        if (timesMeasPara %23 == 1 && timesMeasPara <= 23){
                            measureMeasparas.setSetname(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 2 && timesMeasPara <= 23){
                            measureMeasparas.setBacs(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 3 && timesMeasPara <= 23){
                            measureMeasparas.setCrng(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 4 && timesMeasPara <= 23){
                            measureMeasparas.setEqp1(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 5 && timesMeasPara <= 23){
                            measureMeasparas.setEqt1(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 6 && timesMeasPara <= 23){
                            measureMeasparas.setEqp2(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 7 && timesMeasPara <= 23){
                            measureMeasparas.setEqt2(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 8 && timesMeasPara <= 23){
                            measureMeasparas.setEqp3(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 9 && timesMeasPara <= 23){
                            measureMeasparas.setEqt3(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 10 && timesMeasPara <= 23){
                            measureMeasparas.setEqp4(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 11 && timesMeasPara <= 23){
                            measureMeasparas.setEqt4(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 12 && timesMeasPara <= 23){
                            measureMeasparas.setEqp5(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 13 && timesMeasPara <= 23){
                            measureMeasparas.setEqt5(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 14 && timesMeasPara <= 23){
                            measureMeasparas.setStp(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 15 && timesMeasPara <= 23){
                            measureMeasparas.setEnp(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 16 && timesMeasPara <= 23){
                            measureMeasparas.setPp(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 17 && timesMeasPara <= 23){
                            measureMeasparas.setDlte(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 18 && timesMeasPara <= 23){
                            measureMeasparas.setPwd(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 19 && timesMeasPara <= 23){
                            measureMeasparas.setPtm(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 20 && timesMeasPara <= 23){
                            measureMeasparas.setIbst(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 21 && timesMeasPara <= 23){
                            measureMeasparas.setIben(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara %23 == 22 && timesMeasPara <= 23){
                            measureMeasparas.setIfst(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasPara % 23 == 0 && timesMeasPara <= 23){
                            measureMeasparas.setIfen(Protector.tryParseInt(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (numBacs/6 < measureMeasparas.getBacs()){
                            switch (numBacs % 6){
                                case 0:
                                    bacSetting.setBacName(tempMsg.split(",")[1]);
                                    break;
                                case 1:
                                    bacSetting.setE1(Protector.tryParseInt(tempMsg.split(",")[1]));
                                    break;
                                case 2:
                                    bacSetting.setE2(Protector.tryParseInt(tempMsg.split(",")[1]));
                                    break;
                                case 3:
                                    bacSetting.setE3(Protector.tryParseInt(tempMsg.split(",")[1]));
                                    break;
                                case 4:
                                    bacSetting.setE4(Protector.tryParseInt(tempMsg.split(",")[1]));
                                    break;
                                case 5:
                                    bacSetting.setPkp(Protector.tryParseInt(tempMsg.split(",")[1]));
                                    bacSettings.add(new BacSetting(
                                            bacSetting.getId(),
                                            MainActivity.device.getId(),
                                            bacSetting.getBacName(),
                                            bacSetting.getE1(),
                                            bacSetting.getE2(),
                                            bacSetting.getE3(),
                                            bacSetting.getE4(),
                                            bacSetting.getPkp(),
                                            bacSetting.getUpdatedAt(),
                                            bacSetting.getCreatedAt()
                                    ));
                                    bacSetting = new BacSetting();
                                    break;
                            }
                            numBacs++;

                            if (numBacs/6 >= measureMeasparas.getBacs()){
                                measureMeasparas.setArrBac(bacSettings);
                                ArrayList<BacSetting> bacSettingsTemp = new ArrayList<>();
                                bacSettingsTemp.addAll(bacSettings);

                                resultParas.add(new MeasureMeasparas(
                                        MainActivity.device.getId(),
                                        measureMeasparas.getSetname(),
                                        measureMeasparas.getBacs(),
                                        measureMeasparas.getCrng(),
                                        measureMeasparas.getEqp1(),
                                        measureMeasparas.getEqt1(),
                                        measureMeasparas.getEqp2(),
                                        measureMeasparas.getEqt2(),
                                        measureMeasparas.getEqp3(),
                                        measureMeasparas.getEqt3(),
                                        measureMeasparas.getEqp4(),
                                        measureMeasparas.getEqt4(),
                                        measureMeasparas.getEqp5(),
                                        measureMeasparas.getEqt5(),
                                        measureMeasparas.getStp(),
                                        measureMeasparas.getEnp(),
                                        measureMeasparas.getPp(),
                                        measureMeasparas.getDlte(),
                                        measureMeasparas.getPwd(),
                                        measureMeasparas.getPtm(),
                                        measureMeasparas.getIbst(),
                                        measureMeasparas.getIben(),
                                        measureMeasparas.getIfst(),
                                        measureMeasparas.getIfen(),
                                        measureMeasparas.getUpdatedAt(),
                                        measureMeasparas.getCreatedAt(),
                                        bacSettingsTemp
                                ));

                                measureMeasparas = new MeasureMeasparas();
                                bacSettings.clear();
                                resultStart++;

                                connectThread.writeMeasBas(rulersRes.get(0));
                                Protector.appendLog(false, rulersRes.get(0));
                                rulersRes.remove(0);
                            }else {
                                connectThread.writeLine();
                            }
                        }
                    }
                } else if (resultStart == 4) {
                    if (rulersRes.size() != 0) {
                        timesMeasRes++;
                        if (timesMeasRes == 0){
                            numMeasres = Protector.tryParseInt(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 1){
                            measureMeasress.setName(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 2){
                            measureMeasress.setPkpot(Protector.tryParseFloat(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 3){
                            measureMeasress.setDltc(Protector.tryParseFloat(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 4){
                            measureMeasress.setBgc(Protector.tryParseFloat(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 5){
                            measureMeasress.setBlpsx(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 6){
                            measureMeasress.setBlpsy(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 7){
                            measureMeasress.setBlpex(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 8){
                            measureMeasress.setBlpey(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 0){
                            measureMeasress.setErr(Protector.tryParseInt(tempMsg.split(",")[1]));

                            measureMeasress.setErr(Protector.tryParseInt(tempMsg.split(",")[1]));
                            measureMeasresses.add(new MeasureMeasress(
                                    MainActivity.device.getId(),
                                    measureMeasress.getName(),
                                    measureMeasress.getPkpot(),
                                    measureMeasress.getDltc(),
                                    measureMeasress.getBgc(),
                                    measureMeasress.getErr(),
                                    measureMeasress.getBlpsx(),
                                    measureMeasress.getBlpsy(),
                                    measureMeasress.getBlpex(),
                                    measureMeasress.getBlpey(),
                                    measureMeasress.getUpdatedAt(),
                                    measureMeasress.getCreatedAt()
                            ));
                            measureMeasress = new MeasureMeasress();

                            if (timesMeasRes/9 >= numMeasres){
                                ArrayList<MeasureMeasress> measureMeasressesTemp = new ArrayList<>();
                                measureMeasressesTemp.addAll(measureMeasresses);
                                resultRess.add(measureMeasressesTemp);
                                measureMeasresses.clear();
                                timesMeasRes = -1;
                                connectThread.writeMeasBas(rulersRes.get(0));
                                rulersRes.remove(0);
                            }else {
                                connectThread.writeLine();
                            }
                        }
                    } else {
                        timesMeasRes++;
                        if (timesMeasRes == 0){
                            numMeasres = Protector.tryParseInt(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 1){
                            measureMeasress.setName(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 2){
                            measureMeasress.setPkpot(Protector.tryParseFloat(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 3){
                            measureMeasress.setDltc(Protector.tryParseFloat(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 4){
                            measureMeasress.setBgc(Protector.tryParseFloat(tempMsg.split(",")[1]));
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 5){
                            measureMeasress.setBlpsx(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 6){
                            measureMeasress.setBlpsy(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 7){
                            measureMeasress.setBlpex(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 8){
                            measureMeasress.setBlpey(tempMsg.split(",")[1]);
                            connectThread.writeLine();
                        }else if (timesMeasRes %9 == 0){
                            measureMeasress.setErr(Protector.tryParseInt(tempMsg.split(",")[1]));
                            measureMeasresses.add(new MeasureMeasress(
                                    MainActivity.device.getId(),
                                    measureMeasress.getName(),
                                    measureMeasress.getPkpot(),
                                    measureMeasress.getDltc(),
                                    measureMeasress.getBgc(),
                                    measureMeasress.getErr(),
                                    measureMeasress.getBlpsx(),
                                    measureMeasress.getBlpsy(),
                                    measureMeasress.getBlpex(),
                                    measureMeasress.getBlpey(),
                                    measureMeasress.getUpdatedAt(),
                                    measureMeasress.getCreatedAt()
                            ));
                            measureMeasress = new MeasureMeasress();
                            if (timesMeasRes/9 >= numMeasres){
                                ArrayList<MeasureMeasress> measureMeasressesTemp = new ArrayList<>();
                                measureMeasressesTemp.addAll(measureMeasresses);
                                resultRess.add(measureMeasressesTemp);
                                measureMeasresses.clear();
                                resultStart++;
                                connectThread.writeMeasBas(rulersDet.get(0));
                                Protector.appendLog(false, rulersDet.get(0));
                                rulersDet.remove(0);
                            }else {
                                connectThread.writeLine();
                            }
                        }
                    }
                } else if (resultStart == 5) {
                    if (rulersDet.size() != 0) {
                        timesDet++;
                        if (timesDet %3 == 1){
                            noDet = tempMsg.split(",")[1];
                            connectThread.writeLine();
                        }else if (timesDet %3 == 2){
                            timesDet = 0;
                            String arrHex = tempMsg;

                            ArrayList<MeasureMeasdets> measureMeasdets = new ArrayList<>();

                            float pixel = 0;
                            switch (resultParas.get(posReadDet).getCrng()) {
                                case 0:
                                    pixel = 0.1f;
                                    break;
                                case 1:
                                    pixel = 0.01f;
                                    break;
                                case 2:
                                    pixel = 0.001f;
                                    break;
                            }
                            float deltaE = resultParas.get(posReadDet).getStp() + resultParas.get(posReadDet).getDlte();

                            for (int i = 0; i <= arrHex.length() - 8; i += 8) {
                                float ib = pixel * Protector.HexToDecDataMeasdet(arrHex.substring(i, i + 4));
                                float _if = pixel * Protector.HexToDecDataMeasdet(arrHex.substring(i + 4, i + 8));
                                float deltaI = _if - ib;

                                float eb = resultParas.get(posReadDet).getStp() + deltaE;
                                float ef = resultParas.get(posReadDet).getStp() + deltaE + resultParas.get(posReadDet).getPp();

                                measureMeasdets.add(new MeasureMeasdets(MainActivity.device.getId(),
                                        (i / 8) + "",
                                        deltaE,
                                        deltaI,
                                        eb,
                                        ib,
                                        ef,
                                        _if,
                                        Protector.getCurrentTime(),
                                        Protector.getCurrentTime()));
                                resultDets.add(measureMeasdets);
                                deltaE += resultParas.get(posReadDet).getStp();
                            }
                            posReadDet++;
                            connectThread.writeMeasBas(rulersDet.get(0));
                            Protector.appendLog(false, rulersDet.get(0));
                            rulersDet.remove(0);
                        }
                    } else {
                        timesDet++;
                        if (timesDet %3 == 1){
                            noDet = tempMsg.split(",")[1];
                            connectThread.writeLine();
                        }else if (timesDet %3 == 2){
                            timesDet = 0;
                            String arrHex = tempMsg;

                            ArrayList<MeasureMeasdets> measureMeasdets = new ArrayList<>();

                            float pixel = 0;
                            switch (resultParas.get(posReadDet).getCrng()) {
                                case 0:
                                    pixel = 0.1f;
                                    break;
                                case 1:
                                    pixel = 0.01f;
                                    break;
                                case 2:
                                    pixel = 0.001f;
                                    break;
                            }
                            float deltaE = resultParas.get(posReadDet).getStp() + resultParas.get(posReadDet).getDlte();

                            for (int i = 0; i <= arrHex.length() - 8; i += 8) {
                                float ib = pixel * Protector.HexToDecDataMeasdet(arrHex.substring(i, i + 4));
                                float _if = pixel * Protector.HexToDecDataMeasdet(arrHex.substring(i + 4, i + 8));
                                float deltaI = _if - ib;

                                float eb = resultParas.get(posReadDet).getStp() + deltaE;
                                float ef = resultParas.get(posReadDet).getStp() + deltaE + resultParas.get(posReadDet).getPp();

                                measureMeasdets.add(new MeasureMeasdets(MainActivity.device.getId(),
                                        (i / 8) + "",
                                        deltaE,
                                        deltaI,
                                        eb,
                                        ib,
                                        ef,
                                        _if,
                                        Protector.getCurrentTime(),
                                        Protector.getCurrentTime()));
                                resultDets.add(measureMeasdets);
                                deltaE += resultParas.get(posReadDet).getStp();
                            }
                            posReadDet++;

                            for (int i = 0; i < resultListSensors.size(); i++) {
                                presenterFragment4.receivedStoreMeasure(APIUtils.token,
                                        MainActivity.device.getId(),
                                        resultListSensors.get(i).getDatetime(),
                                        String.valueOf(resultListSensors.get(i).getNo()), resultParas.get(i), resultBas.get(i), resultRess.get(i), resultDets.get(i));
                            }
                        }
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
                    posReadDet = 0;
                    timesMeasBas = 0 ;
                    timesMeasPara = 0 ;
                    timesMeasRes = -1 ;
                    timesDet = 0;
                    numMeasres = 0;
                    counterStore = 0;

                    resultListSensors.clear();
                    resultListSensorsTemp.clear();
                    resultDets.clear();
                    rulersDet.clear();
                    resultRess.clear();
                    rulersRes.clear();
                    resultParas.clear();
                    rulersPara.clear();
                    resultBas.clear();
                    rulersBas.clear();

                    resultStart = 0;
                    arrRules.add("*LIST");
                    connectThread.writeList(arrRules.get(0));
                    Protector.appendLog(false, arrRules.get(0));
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
}

