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
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.util.Log;
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
import com.infinity.EBacSens.model_objects.Graph;
import com.infinity.EBacSens.model_objects.MeasureMeasbas;
import com.infinity.EBacSens.model_objects.MeasureMeasdets;
import com.infinity.EBacSens.model_objects.MeasureMeasparas;
import com.infinity.EBacSens.model_objects.MeasureMeasress;
import com.infinity.EBacSens.model_objects.Result;
import com.infinity.EBacSens.model_objects.SensorMeasure;
import com.infinity.EBacSens.model_objects.SensorMeasureDetail;
import com.infinity.EBacSens.model_objects.SensorMeasurePage;
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
import java.io.FileWriter;
import java.io.IOException;
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
    private ArrayAdapter<String> adapterDatetime;

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
            }else {
                showErrorMessage("Device not support Bluetooth");
            }
        });

        spnDatetime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                skbProgress.setProgress(0);
                txtProcess.setVisibility(View.GONE);
                positionCSV = position;
                showDialogProcessing();
                presenterFragment4.receivedGetDetailMeasure(APIUtils.token, arrMeasurePage.get(position).getSensorId());
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
        skbProgress.setPadding(0, 0, 0, 0);
        txtProcess = view.findViewById(R.id.fragment_4_txt_progress);
        spnDatetime = view.findViewById(R.id.fragment_4_spn_date_time);

        rcvResult = view.findViewById(R.id.fragment_4_rcv_result);
        rcvResult.setHasFixedSize(true);
        rcvResult.setNestedScrollingEnabled(false);
        rcvResult.setLayoutManager(new LinearLayoutManager(context));
        arrResult = new ArrayList<>();
        arrResult.add(new Result("HC-05", "270", "109.08", "1.57", "0"));
        arrResult.add(new Result("Red line", "810", "12.09", "1.20", "0"));

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

        adapterDatetime = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrDatetime);
        spnDatetime.setAdapter(adapterDatetime);
        arrMeasure = new ArrayList<>();
        arrMeasurePage = new ArrayList<>();

        adapterDatetime.notifyDataSetChanged();

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

    private void exportFileCSV(SensorMeasure sensorMeasure) {
        if (sensorMeasure != null){
            PermissionListener permissionListener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    skbProgress.setProgress(0);
                    txtProcess.setVisibility(View.VISIBLE);
                    txtProcess.setText("Exporting...");
                    txtProcess.setTextColor(context.getResources().getColor(R.color.black));

                    Observable.create(emitter -> {
                        File folder = new File(Environment.getExternalStorageDirectory() +
                                File.separator + "EBacSens");
                        boolean success;
                        if (!folder.exists()) {
                            success = folder.mkdirs();
                        }

                        String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/EBacSens/ExportResult_" + Protector.getCurrentTime() + ".csv"); // Here csv file name is MyCsvFile.csv
                        CSVWriter writer;
                        try {
                            writer = new CSVWriter(new FileWriter(csv));

                            List<String[]> data = new ArrayList<>();
                            data.add(new String[]{"測定日時" + sensorMeasure.getDatetime()});
                            data.add(new String[]{"データ番号,014"});
                            data.add(new String[]{"設定名,Test3"});
                            data.add(new String[]{"測定数,5"});
                            data.add(new String[]{"レンジ,3mA"});
                            data.add(new String[]{"平衡電位1,1000"});
                            data.add(new String[]{"平衡時間1,30"});
                            data.add(new String[]{"平衡電位2,1000"});
                            data.add(new String[]{"平衡時間2,30"});
                            data.add(new String[]{"平衡電位3,1000"});
                            data.add(new String[]{"平衡時間3,0"});
                            data.add(new String[]{"平衡電位4,1000"});
                            data.add(new String[]{"平衡時間4,0"});
                            data.add(new String[]{"平衡電位5,1000"});
                            data.add(new String[]{"平衡時間5,0"});
                            data.add(new String[]{"開始電位,1000"});
                            data.add(new String[]{"終了電位,0"});
                            data.add(new String[]{"パルス振幅,1000"});
                            data.add(new String[]{"ΔE,1000"});
                            data.add(new String[]{"パルス幅,999"});
                            data.add(new String[]{"パルス期間,1000"});
                            data.add(new String[]{"ベース電流サンプル時間下限,0"});
                            data.add(new String[]{"ベース電流サンプル時間上限,1"});
                            data.add(new String[]{"ファラデー電流サンプル時間下限,8"});
                            data.add(new String[]{"ファラデー電流サンプル時間上限,9"});
                            data.add(new String[]{"微生物1,obj1"});
                            data.add(new String[]{"E1ベースライン検索開始電位,0"});
                            data.add(new String[]{"E2ベースライン検索開始電位,0"});
                            data.add(new String[]{"E3ベースライン検索終了電位,0"});
                            data.add(new String[]{"E4ベースライン検索終了電位,0"});
                            data.add(new String[]{"ピーク位置,上"});
                            data.add(new String[]{"微生物2,obj2"});
                            data.add(new String[]{"E1ベースライン検索開始電位,0"});
                            data.add(new String[]{"E2ベースライン検索開始電位,0"});
                            data.add(new String[]{"E3ベースライン検索終了電位,0"});
                            data.add(new String[]{"E4ベースライン検索終了電位,0"});
                            data.add(new String[]{"ピーク位置,上"});
                            data.add(new String[]{"測定対象物名,obj1,obj2,obj3,"});
                            data.add(new String[]{"ピーク電位,----,----,----,----,----,"});
                            data.add(new String[]{"ピーク高さ,----.-,----.-,----.-,----.-,----.-,"});
                            data.add(new String[]{"バックグランド,----.-,----.-,----.-,----.-,----.-,"});
                            data.add(new String[]{"ベースラインポイント1 X,----,----,----,----,----,"});
                            data.add(new String[]{"ベースラインポイント1 Y,----.-,----.-,----.-,----.-,----.-,"});
                            data.add(new String[]{"ベースラインポイント2 X,----,----,----,----,----,"});
                            data.add(new String[]{"ベースラインポイント2 Y,----.-,----.-,----.-,----.-,----.-,"});
                            data.add(new String[]{"エラー番号,1,1,1,1,1,"});
                            data.add(new String[]{"No,E(mV),DeltaI(uA),Eb,Ib,Ef,If"});

                            for (int i = 0; i < data.size(); i++) {
                                writer.writeNext(data.get(i));
                                int percent = i * 100 / data.size();
                                int[] ii = {percent};
                                emitter.onNext(ii);
                                SystemClock.sleep(1);
                            }

//                        List<String[]> data = new ArrayList<>();
//                        data.add(new String[]{"Name", "Datetime", "Result"});
//                        for (int i = 0; i < arrMeasure.size(); i++) {
//                            data.add(new String[]{arrMeasure.get(i).getName(), arrMeasure.get(i).getDatetime(), arrMeasure.get(i).getResult()});
//                        }
//
//                        for (int i = 0; i < data.size(); i++) {
//                            writer.writeNext(data.get(i));
//                            int percent = i * 100 / data.size();
//                            int[] ii = {percent};
//                            emitter.onNext(ii);
//                            SystemClock.sleep(1);
//                        }

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
        }else {
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
            arrDatetime.clear();
            arrMeasurePage.addAll(measurePages);
            for (int i = 0; i < measurePages.size(); i++) {
                arrDatetime.add(measurePages.get(i).getDatetime());
            }
            adapterDatetime.notifyDataSetChanged();
        }
        cancelDialogProcessing();
    }

    @Override
    public void onGetDataMeasureDetail(SensorMeasureDetail sensorMeasureDetail) {
        arrGraph.clear();
        if (sensorMeasureDetail != null && sensorMeasureDetail.getSensorMeasure().getMeasureMeasresses() != null) {
            sensorMeasureExport = sensorMeasureDetail.getSensorMeasure();
            for (int i = 0; i < sensorMeasureDetail.getSensorMeasure().getMeasureMeasresses().size(); i++) {
                arrGraph.add(new Graph(
                        sensorMeasureDetail.getSensorMeasure().getMeasureMeasresses().get(0).getName(),
                        (sensorMeasureDetail.getSensorMeasure().getMeasureMeasresses().get(0).getDltc() / sensorMeasureDetail.getSensorMeasure().getMeasureMeasresses().get(0).getPkpot()),
                        sensorMeasureDetail.getSensorMeasure().getMeasureMeasresses().get(0).getBgc(),
                        "Decription"));
            }
        }
        adapteRCVGraph.notifyDataSetChanged();
        cancelDialogProcessing();
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
    }

    @Override
    public void onConnected() {
        cancelDialogProcessing();
        if (connectThread != null) {
            connectThread.run();
        }
        MainActivity.device.setStatusConnect(1);
        skbProgress.setProgress(0);
        txtProcess.setVisibility(View.VISIBLE);
        txtProcess.setText("Progressing...");
        txtProcess.setTextColor(context.getResources().getColor(R.color.black));
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
            connectThread.write("*R,LIST");

            // test result

            //new MeasureMeasdets(4, 4, null, null,null,null,null,null,null,null, "2021-05-04T07:49:24.000000Z", null),
            //new MeasureMeasress(4, 4, null,1,1,1,1, null, null, null, null, "2021-05-04T07:49:24.000000Z", "2021-05-04T07:49:24.000000Z")));
            List<Integer> integers = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                integers.add(i);
            }
            arrMeasure.add(0 , new SensorMeasure(-1,
                    MainActivity.device.getId(),
                    "2021-05-04 08:38:12",
                    null,
                    "2021-05-04:49:22:00",
                    "2021-05-04:49:22:00",
                    new MeasureMeasbas(4, 4, "2021-05-04 09:18:24", 29, 45, "2021-05-04T07:49:24.000000Z" , "2021-05-04T07:49:24.000000Z"),
                    null,
                    new MeasureMeasparas(4, 4, "{\"setname\":\"name1\",\"bacs\":5,\"crng\":1,\"eqp1\":857}", "2021-05-04T07:49:24.000000Z", "2021-05-04T07:49:24.000000Z", new MeasureMeasparas.CastedSettings("name1", 5, 1, 875)),
                    null));
            for (int i = 0; i < arrMeasure.size(); i++) {
                arrDatetime.add(0 , arrMeasure.get(i).getDatetime());
            }
            adapterDatetime.notifyDataSetChanged();

            skbProgress.setProgress(100);
            txtProcess.setVisibility(View.VISIBLE);
            txtProcess.setText("Done!");
            txtProcess.setTextColor(context.getResources().getColor(R.color.green));
        }
    }
}

