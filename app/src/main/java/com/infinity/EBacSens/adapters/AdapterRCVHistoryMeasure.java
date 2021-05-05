package com.infinity.EBacSens.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.model_objects.SensorMeasure;
import com.infinity.EBacSens.model_objects.SensorSetting;
import com.infinity.EBacSens.views.ViewRCVDeviceOnline;
import com.infinity.EBacSens.views.ViewRCVHistoryMeasure;

import java.util.ArrayList;

public class AdapterRCVHistoryMeasure extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<SensorSetting> arrItem;
    private Context context;
    private ViewRCVHistoryMeasure callback;

    public AdapterRCVHistoryMeasure(Context context, ArrayList<SensorSetting> arrItem, ViewRCVHistoryMeasure callback) {
        this.arrItem = arrItem;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_history_measure, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        viewHodler.txtName.setText(arrItem.get(position).getSetname());
        viewHodler.txtTime.setText(arrItem.get(position).getUpdatedAt());
        //viewHodler.txtResult.setText(arrItem.get(position).get());
        viewHodler.txtResult.setText("123");
        viewHodler.btnDelete.setOnClickListener(v -> callback.onDeleteRCVHistoryMeasure(position));
        viewHodler.btnUse.setOnClickListener(v -> callback.onUseRCVHistoryMeasure(position));
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        TextView txtName , txtTime , txtResult;
        Button btnDelete , btnUse;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.item_rcv_history_measure_txt_name);
            txtTime = itemView.findViewById(R.id.item_rcv_history_measure_txt_time);
            txtResult = itemView.findViewById(R.id.item_rcv_history_measure_txt_result);
            btnDelete = itemView.findViewById(R.id.item_rcv_history_measure_btn_delete);
            btnUse = itemView.findViewById(R.id.item_rcv_history_measure_btn_use);
        }
    }
}