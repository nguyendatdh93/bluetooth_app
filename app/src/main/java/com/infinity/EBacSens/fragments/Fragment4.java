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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.model_objects.ChartItem;
import com.infinity.EBacSens.model_objects.LineChartItem;

import java.util.ArrayList;
import java.util.List;

public class Fragment4 extends Fragment {

    private View view;
    private Activity activity;
    private Context context;

    private LineChart lineChart ;

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
    }

    private LineData generateDataLine(int cnt) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            values1.add(new Entry(i, (int) (Math.random() * 65) + 40));
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

