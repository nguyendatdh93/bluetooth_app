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
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.infinity.EBacSens.model_objects.Result;
import com.infinity.EBacSens.model_objects.SensorMeasure;
import com.infinity.EBacSens.model_objects.SensorMeasureDetail;
import com.infinity.EBacSens.model_objects.SensorMeasurePage;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.model_objects.VerticalSpaceItemDecoration;
import com.infinity.EBacSens.presenter.PresenterFragment4;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.task.ConnectThread;
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

import static com.infinity.EBacSens.activitys.MainActivity.mBluetoothAdapter;
import static com.infinity.EBacSens.retrofit2.APIUtils.PBAP_UUID;

public class Fragment4 extends Fragment implements ViewFragment4Listener, ViewConnectThread {

    private View view;
    private Activity activity;
    private Context context;
    private RelativeLayout container;

    private SeekBar skbProgress;
    private Button btnExportCSV, btnHistoryMeasure;
    private TextView txtProcess;
    private Spinner spnDatetime;

    private RecyclerView rcvResult;
    private ArrayList<Result> arrResult;
    private AdapteRCVResult adapteRCVResult;

    private RecyclerView rcvGraph;
    private ArrayList<Graph> arrGraph;
    private AdapteRCVGraph adapteRCVGraph;

    private List<String> arrDatetime;
    private ArrayAdapter<String> adapterSpnDatetime;

    private ArrayList<SensorMeasure> arrMeasure;
    private AdapterRCVHistoryMeasure adapterRCVHistoryMeasure;

    private Dialog dialogProcessing;

    private List<SensorMeasurePage.MeasurePage> arrMeasurePage;
    private PresenterFragment4 presenterFragment4;

    private ConnectThread connectThread;

    private int positionCSV;
    private SensorMeasure sensorMeasureExport;

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
                showErrorMessage("Device not support Bluetooth");
            }
        });

        spnDatetime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                skbProgress.setProgress(0);
                txtProcess.setText("");
                positionCSV = position;
                showDialogProcessing();
                presenterFragment4.receivedGetDetailMeasure(APIUtils.token, arrMeasurePage.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        showDialogProcessing();
        presenterFragment4.receivedGetMeasurePage(APIUtils.token, MainActivity.device.getId(), 1, 0);
    }

    private void addController() {
        presenterFragment4 = new PresenterFragment4(this);
        container = view.findViewById(R.id.container_fragment_4);
        btnExportCSV = view.findViewById(R.id.fragment_4_btn_csv);
        btnHistoryMeasure = view.findViewById(R.id.fragment_4_btn_history_measure);
        skbProgress = view.findViewById(R.id.fragment_4_skb_progress);
        skbProgress.setEnabled(false);
        skbProgress.setPadding(0, 0, 0, 0);
        txtProcess = view.findViewById(R.id.fragment_4_txt_progress);
        spnDatetime = view.findViewById(R.id.fragment_4_spn_date_time);

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

        arrDatetime = new ArrayList<>();

        adapterSpnDatetime = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrDatetime);
        spnDatetime.setAdapter(adapterSpnDatetime);
        arrMeasure = new ArrayList<>();
        arrMeasurePage = new ArrayList<>();

        initDialogProcessing();
    }

    private void connectSensor() {
        if (MainActivity.device.getMacDevice() != null && mBluetoothAdapter != null) {
            try {
                if (connectThread != null) {
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
        } else {
            showErrorMessage("Device not have mac address");
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
//                        File folder = new File();
//                        boolean success;
//                        if (!folder.exists()) {
//                            success = folder.mkdirs();
//                        }

                        FileOutputStream os = new FileOutputStream(Environment.getExternalStorageDirectory() +
                                File.separator + "EBacSens/ExportResult_" + Protector.getCurrentTime().replace(":", "-") + ".csv");
                        os.write(0xef);
                        os.write(0xbb);
                        os.write(0xbf);

                        CSVWriter writer;
                        try {
                            writer = new CSVWriter(new OutputStreamWriter(os));
                            List<String[]> data = new ArrayList<>();
                            data.add(new String[]{"測定日時", sensorMeasure.getDatetime()});
                            data.add(new String[]{"データ番号", "14"}); // prmid
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
                        showSuccessMessage("Exported");
                    });
                }

                @Override
                public void onPermissionDenied(List<String> deniedPermissions) {
                    showErrorMessage("denied");
                }
            };
            TedPermission.with(context).setPermissionListener(permissionListener).setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).check();
        } else {
            showErrorMessage("Null response");
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
    public void onGetDataMeasurePage(List<SensorMeasurePage.MeasurePage> measurePages) {
        if (measurePages != null) {
            arrMeasurePage.clear();
            //arrDatetime.clear();
            adapterSpnDatetime.clear();
            arrMeasurePage.addAll(measurePages);
            for (int i = 0; i < measurePages.size(); i++) {
                adapterSpnDatetime.add(measurePages.get(i).getDatetime());
            }
            adapterSpnDatetime.notifyDataSetChanged();
            if (arrDatetime.size() > 0) {
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
            for (int i = 0; i < sensorMeasureExport.getMeasureMeasparas().getArrBac().size() && sensorMeasureExport.getMeasureMeasresses().size()-1 > i; i++) {
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
        cancelDialogProcessing();
        showSuccessMessage("Success Stored");
        adapterSpnDatetime.notifyDataSetChanged();
        skbProgress.setProgress(100);
        txtProcess.setText("Success Stored!");
        txtProcess.setTextColor(context.getResources().getColor(R.color.black));
    }

    @Override
    public void onFailStoreMeasure(String error) {
        cancelDialogProcessing();
        showErrorMessage(error);
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
        if (connectThread != null) {
            //txtProcess.setTextColor(context.getResources().getColor(R.color.black));

            connectThread.write("*R,LIST");

            // test result

            ArrayList<BacSetting> bacSettings = new ArrayList<>();
            bacSettings.add(new BacSetting(1, 1, "ex", 1, 1, 1, 1, 1, Protector.getCurrentTime(), Protector.getCurrentTime()));
            arrMeasure.add(0, new SensorMeasure(-1,
                    MainActivity.device.getId(),
                    Protector.getCurrentTime(),
                    "unknown",
                    Protector.getCurrentTime(),
                    Protector.getCurrentTime(),
                    new MeasureMeasbas(4, Protector.getCurrentTime(), 29, 45, Protector.getCurrentTime(), Protector.getCurrentTime()),
                    null,
                    new MeasureMeasparas(4, "ex", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 10, 10, 1, 1, 1, 1, Protector.getCurrentTime(), Protector.getCurrentTime(), bacSettings),
                    null));
            for (int i = 0; i < arrMeasure.size(); i++) {
                arrDatetime.add(0, arrMeasure.get(i).getDatetime());
            }
//            adapterSpnDatetime.notifyDataSetChanged();

            // save to cloud compare by datetime

            SensorSetting sensorSetting = new SensorSetting(1, "ex", 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, Protector.getCurrentTime(), Protector.getCurrentTime(), Protector.getCurrentTime(), bacSettings);
            MeasureMeasbas measureMeasbas = new MeasureMeasbas();
            measureMeasbas.setDatetime(Protector.getCurrentTime());
            measureMeasbas.setPstaterr(1);

            ArrayList<MeasureMeasress> measureMeasresses = new ArrayList<>();
            measureMeasresses.add(new MeasureMeasress(1, "Ex - 01", 1, 1, 0, 1, "1", "2", "1", "2", Protector.getCurrentTime(), Protector.getCurrentTime()));

            ArrayList<MeasureMeasdets> measureMeasdets = new ArrayList<>();
            measureMeasdets.add(new MeasureMeasdets(1, "Ex 1", 1, 1, 1, 1, 1, 1, Protector.getCurrentTime(), Protector.getCurrentTime(), Protector.getCurrentTime()));

            //showDialogProcessing();
            presenterFragment4.receivedStoreMeasure(APIUtils.token, MainActivity.device.getId(), Protector.getCurrentTime(), "06", sensorSetting, measureMeasbas, measureMeasresses, measureMeasdets);
        }
    }
}

