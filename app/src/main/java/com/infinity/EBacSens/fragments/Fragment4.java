package com.infinity.EBacSens.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.adapters.AdapteRCVDate;
import com.infinity.EBacSens.adapters.AdapteRCVMenuDraw;
import com.infinity.EBacSens.adapters.AdapteRCVResult;
import com.infinity.EBacSens.model_objects.ChartItem;
import com.infinity.EBacSens.model_objects.Date;
import com.infinity.EBacSens.model_objects.LineChartItem;
import com.infinity.EBacSens.model_objects.Result;
import com.infinity.EBacSens.model_objects.Sensor;
import com.infinity.EBacSens.model_objects.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fragment4 extends Fragment {

    private View view;
    private Activity activity;
    private Context context;

    private LineChart lineChart ;

    private RecyclerView rcvDate;
    private ArrayList<Date> arrDate;
    private AdapteRCVDate adapteRCVDate;

    private RecyclerView rcvResult;
    private ArrayList<Result> arrResult;
    private AdapteRCVResult adapteRCVResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_4, container, false);
        addController();
        addEvents();
        return view;
    }

    private void addEvents() {

    }

    private void addController() {
        lineChart = view.findViewById(R.id.fragment_4_chart);
        lineChart.setData(generateDataLine(1));
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getAxisRight().setDrawAxisLine(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        rcvDate = view.findViewById(R.id.fragment_4_rcv_date);
        rcvDate.setHasFixedSize(true);
        rcvDate.addItemDecoration(new VerticalSpaceItemDecoration(20));
        rcvDate.setLayoutManager(new LinearLayoutManager(context));
        arrDate = new ArrayList<>();
        arrDate.add(new Date("2021/10/09 18:30:03" , true));
        arrDate.add(new Date("2021/10/09 18:30:03" , false));
        arrDate.add(new Date("2021/10/09 18:30:03" , false));
        arrDate.add(new Date("2021/10/09 18:30:03" , false));
        arrDate.add(new Date("2021/10/09 18:30:03" , false));
        arrDate.add(new Date("2021/10/09 18:30:03" , false));
        adapteRCVDate = new AdapteRCVDate(context , arrDate);
        rcvDate.setAdapter(adapteRCVDate);

        rcvResult = view.findViewById(R.id.fragment_4_rcv_result);
        rcvResult.setHasFixedSize(true);
        rcvResult.addItemDecoration(new VerticalSpaceItemDecoration(20));
        rcvResult.setLayoutManager(new LinearLayoutManager(context));
        arrResult = new ArrayList<>();
        arrResult.add(new Result("Sensor" , 1,1,1,0));
        arrResult.add(new Result("Sensor" , 1,1,1,0));
        arrResult.add(new Result("Sensor" , 1,1,1,0));
        arrResult.add(new Result("Sensor" , 1,1,1,0));
        arrResult.add(new Result("Sensor" , 1,1,1,0));
        adapteRCVResult = new AdapteRCVResult(context , arrResult);
        rcvResult.setAdapter(adapteRCVResult);
    }

    private LineData generateDataLine(int cnt) {

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

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            values2.add(new Entry(i, values1.get(i).getY() - 30));
        }

        LineDataSet d2 = new LineDataSet(values2, "New DataSet " + cnt + ", (1)");
        d2.setLineWidth(1f);
        d2.setColor(context.getResources().getColor(R.color.red));
        d2.setDrawValues(false);
        d2.setDrawCircles(false);
        d2.setDrawCircleHole(false);

        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        sets.add(d2);

        return new LineData(sets);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        activity = (Activity) context;
    }
}

