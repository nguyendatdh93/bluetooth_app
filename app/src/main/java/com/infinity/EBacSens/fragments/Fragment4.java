package com.infinity.EBacSens.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
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
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.activitys.ListDeviceActivity;
import com.infinity.EBacSens.adapters.AdapteRCVDeviceOnline;
import com.infinity.EBacSens.adapters.AdapteRCVResult;
import com.infinity.EBacSens.adapters.AdapterRCVHistoryMeasure;
import com.infinity.EBacSens.model_objects.Measure;
import com.infinity.EBacSens.model_objects.Result;
import com.infinity.EBacSens.model_objects.VerticalSpaceItemDecoration;
import com.infinity.EBacSens.views.ViewRCVHistoryMeasure;
import com.opencsv.CSVWriter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Fragment4 extends Fragment implements ViewRCVHistoryMeasure {

    private View view;
    private Activity activity;
    private Context context;

    private LineChart lineChart;
    private SeekBar skbProgress;
    private Button btnExportCSV, btnHistoryMeasure;
    private CheckBox ckbBaseRedLine;
    private TextView txtProcess;

    private RecyclerView rcvResult;
    private ArrayList<Result> arrResult;
    private AdapteRCVResult adapteRCVResult;

    private AutoCompleteTextView acpDatetime;
    private List<String> arrDatetime;
    private ArrayAdapter<String> adapterDatetime;

    private ArrayList<Measure> arrMeasure;
    private AdapterRCVHistoryMeasure adapterRCVHistoryMeasure;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_4, container, false);
        addController();
        addEvents();
        return view;
    }

    private void addEvents() {
        btnExportCSV.setOnClickListener(v -> showDialogHistoryMeasure());
        ckbBaseRedLine.setOnCheckedChangeListener((buttonView, isChecked) -> {
            lineChart.setData(generateDataLine(isChecked));
            lineChart.invalidate();
        });

        //btnHistoryMeasure.setOnClickListener(v -> );
    }

    private void showDialogHistoryMeasure() {
        Dialog dialogHistoryMeasure = new Dialog(context);
        dialogHistoryMeasure.setContentView(R.layout.dialog_history_measure);

        RecyclerView rcvHistoryMeasure = dialogHistoryMeasure.findViewById(R.id.dialog_history_measure_rcv);
        rcvHistoryMeasure.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rcvHistoryMeasure.setLayoutManager(linearLayoutManager);

        rcvHistoryMeasure.addItemDecoration(new DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL));
        arrMeasure = new ArrayList<>();
        arrMeasure.add(new Measure("Sensor", "2021/10/09 18:00:06", "102"));
        arrMeasure.add(new Measure("Sensor", "2021/10/09 18:00:06", "102"));
        arrMeasure.add(new Measure("Sensor", "2021/10/09 18:00:06", "102"));
        arrMeasure.add(new Measure("Sensor", "2021/10/09 18:00:06", "102"));
        arrMeasure.add(new Measure("Sensor", "2021/10/09 18:00:06", "102"));

        adapterRCVHistoryMeasure = new AdapterRCVHistoryMeasure(dialogHistoryMeasure.getContext(), arrMeasure, this);
        rcvHistoryMeasure.setAdapter(adapterRCVHistoryMeasure);

        dialogHistoryMeasure.findViewById(R.id.dialog_history_measure_btn_close).setOnClickListener(v -> dialogHistoryMeasure.cancel());
        dialogHistoryMeasure.findViewById(R.id.dialog_history_measure_btn_export).setOnClickListener(v -> {
            dialogHistoryMeasure.cancel();
            exportFileCSV(arrMeasure);
        });

        dialogHistoryMeasure.show();
        Window window = dialogHistoryMeasure.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    }

    private void addController() {
        lineChart = view.findViewById(R.id.fragment_4_chart);
        btnExportCSV = view.findViewById(R.id.fragment_4_btn_csv);
        btnHistoryMeasure = view.findViewById(R.id.fragment_4_btn_history_measure);
        ckbBaseRedLine = view.findViewById(R.id.fragment_4_ckb_base_red_line);
        skbProgress = view.findViewById(R.id.fragment_4_skb_progress);
        skbProgress.setPadding(0, 0, 0, 0);
        acpDatetime = view.findViewById(R.id.fragment_4_acp_name_device);
        txtProcess = view.findViewById(R.id.fragment_4_txt_progress);

        lineChart.setData(generateDataLine(true));
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);

        lineChart.getAxisRight().setDrawLabels(false);
        //lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getLegend().setEnabled(false);
//        lineChart.getAxisRight().setDrawGridLines(false);
//        lineChart.getAxisLeft().setDrawGridLines(false);

        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);

        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxis = lineChart.getAxisLeft();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //yAxis.setLabelCount(5);
//        xAxis.setLabelCount(7,true);
//        yAxis.setLabelCount(5,true);
        //xAxis.enableAxisLineDashedLine(10,10,0);

        rcvResult = view.findViewById(R.id.fragment_4_rcv_result);
        rcvResult.setHasFixedSize(true);
        rcvResult.setNestedScrollingEnabled(false);
        rcvResult.addItemDecoration(new VerticalSpaceItemDecoration(20));
        rcvResult.setLayoutManager(new LinearLayoutManager(context));
        arrResult = new ArrayList<>();
        arrResult.add(new Result("HC-05", "270", "109.08", "1.57", "0"));
        arrResult.add(new Result("Red line", "810", "12.09", "1.20", "0"));

        adapteRCVResult = new AdapteRCVResult(context, arrResult);
        rcvResult.setAdapter(adapteRCVResult);

        arrDatetime = new ArrayList<>();
        arrDatetime.add("2021/04/09 18:05:05");
        arrDatetime.add("2021/04/10 18:05:05");
        arrDatetime.add("2021/04/11 18:05:05");

        adapterDatetime = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrDatetime);
        acpDatetime.setAdapter(adapterDatetime);
    }

    private void exportFileCSV(ArrayList<Measure> arrMeasure) {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                skbProgress.setProgress(0);
                txtProcess.setVisibility(View.VISIBLE);
                txtProcess.setText("Exporting...");
                txtProcess.setTextColor(context.getResources().getColor(R.color.black));

                Observable.create(emitter -> {
                    String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyCsvFile.csv"); // Here csv file name is MyCsvFile.csv
                    CSVWriter writer;
                    try {
                        writer = new CSVWriter(new FileWriter(csv));

                        List<String[]> data = new ArrayList<String[]>();
                        data.add(new String[]{"Name", "Datetime", "Result"});
                        for (int i = 0; i < arrMeasure.size(); i++) {
                            data.add(new String[]{arrMeasure.get(i).getName(), arrMeasure.get(i).getDatetime(), arrMeasure.get(i).getResult()});
                        }

                        for (int i = 0; i < data.size(); i++) {
                            writer.writeNext(data.get(i));
                            int percent = i * 100 / arrMeasure.size();
                            int[] ii = {percent};
                            emitter.onNext(ii);
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
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    txtProcess.setText( t.getMessage());
                }, () -> {
                    // progress tamom shod
                    skbProgress.setProgress(100);
                    txtProcess.setText("Done!");
                    txtProcess.setTextColor(context.getResources().getColor(R.color.green));
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(activity, "denied", Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(context).setPermissionListener(permissionListener).setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).check();

    }

    private LineData generateDataLine(boolean baseline) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            values1.add(new Entry(i, (int) (Math.random() * 65) + 100));
        }

        LineDataSet d1 = new LineDataSet(values1, "");
        d1.setLineWidth(1f);
        d1.setColor(context.getResources().getColor(R.color.green));
        d1.setDrawValues(false);
        d1.setDrawCircles(false);
        d1.setDrawCircleHole(false);

        ArrayList<ILineDataSet> sets = new ArrayList<>();

        sets.add(d1);

        if (baseline) {
            ArrayList<Entry> values2 = new ArrayList<>();

            values2.add(new Entry(0, 30));
            for (int i = 5; i < values1.size(); i += 5) {
                values2.add(new Entry(i, (int) (Math.random() * 65) + 80));
            }
            values2.add(new Entry(29, 60));

            LineDataSet d2 = new LineDataSet(values2, "");
            d2.setLineWidth(1f);
            d2.setColor(context.getResources().getColor(R.color.red));
            d2.setDrawValues(false);
            d2.setDrawCircles(false);
            d2.setDrawCircleHole(false);

            sets.add(d2);
        }

        return new LineData(sets);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    public void onDeleteRCVHistoryMeasure(int position) {
        Dialog dialogYesNo = new Dialog(context);
        dialogYesNo.setContentView(R.layout.dialog_yes_no);

        dialogYesNo.findViewById(R.id.dialog_yes_no_btn_no).setOnClickListener(v -> dialogYesNo.cancel());
        dialogYesNo.findViewById(R.id.dialog_yes_no_btn_yes).setOnClickListener(v -> {
            arrMeasure.remove(position);
            adapterRCVHistoryMeasure.notifyItemRemoved(position);
            adapterRCVHistoryMeasure.notifyItemRangeChanged(position, arrMeasure.size());
            dialogYesNo.cancel();
        });

        dialogYesNo.show();
        Window window = dialogYesNo.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}

