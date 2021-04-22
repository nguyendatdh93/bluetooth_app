package com.infinity.EBacSens.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
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
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.adapters.AdapteRCVDeviceOnline;
import com.infinity.EBacSens.adapters.AdapteRCVResult;
import com.infinity.EBacSens.adapters.AdapterRCVHistoryMeasure;
import com.infinity.EBacSens.model_objects.Measure;
import com.infinity.EBacSens.model_objects.Result;
import com.infinity.EBacSens.model_objects.VerticalSpaceItemDecoration;
import com.infinity.EBacSens.views.ViewRCVHistoryMeasure;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fragment4 extends Fragment implements ViewRCVHistoryMeasure {

    private View view;
    private Activity activity;
    private Context context;

    private LineChart lineChart;
    private SeekBar skbProgress;

    private RecyclerView rcvResult;
    private ArrayList<Result> arrResult;
    private AdapteRCVResult adapteRCVResult;

    private Button btnExportCSV, btnHistoryMeasure;

    private CheckBox ckbBaseRedLine;

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
        btnExportCSV.setOnClickListener(v -> exportFileCSV());
        ckbBaseRedLine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                lineChart.setData(generateDataLine(1, isChecked));
                lineChart.invalidate();
            }
        });

        btnHistoryMeasure.setOnClickListener(v -> showDialogHistoryMeasure());
    }

    private void showDialogHistoryMeasure() {
        Dialog dialogHistoryMeasure = new Dialog(context);
        dialogHistoryMeasure.setContentView(R.layout.dialog_history_measure);

        dialogHistoryMeasure.findViewById(R.id.dialog_history_measure_btn_close).setOnClickListener(v -> dialogHistoryMeasure.cancel());

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

        lineChart.setData(generateDataLine(1, false));
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
        arrResult.add(new Result("Sensor", 1, 1, 1, 0));
        arrResult.add(new Result("Sensor", 1, 1, 1, 0));
        arrResult.add(new Result("Sensor", 1, 1, 1, 0));
        arrResult.add(new Result("Sensor", 1, 1, 1, 0));
        arrResult.add(new Result("Sensor", 1, 1, 1, 0));

        adapteRCVResult = new AdapteRCVResult(context, arrResult);
        rcvResult.setAdapter(adapteRCVResult);
    }

    private void exportFileCSV() {
        String csv = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyCsvFile.csv"); // Here csv file name is MyCsvFile.csv
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(csv));

            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[]{"Country", "Capital"});
            data.add(new String[]{"India", "New Delhi"});
            data.add(new String[]{"United States", "Washington D.C"});
            data.add(new String[]{"Germany", "Berlin"});

            writer.writeAll(data); // data is adding to csv

            writer.close();
            Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(activity, "failed", Toast.LENGTH_SHORT).show();
        }
    }

    private LineData generateDataLine(int cnt, boolean baseline) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            values1.add(new Entry(i, (int) (Math.random() * 65) + 100));
        }

        LineDataSet d1 = new LineDataSet(values1, "New DataSet " + cnt + ", (1)");
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

            LineDataSet d2 = new LineDataSet(values2, "New DataSet " + cnt + ", (1)");
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

