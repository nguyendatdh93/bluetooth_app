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
import com.infinity.EBacSens.model_objects.Measure;
import com.infinity.EBacSens.views.ViewRCVDeviceOnline;

import java.util.ArrayList;

public class AdapterRCVHistoryMeasure extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Measure> arrItem;
    private Context context;

    public AdapterRCVHistoryMeasure(Context context , ArrayList<Measure> arrItem ) {
        this.arrItem = arrItem;
        this.context = context;
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

        viewHodler.txtName.setText(arrItem.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return arrItem.size();

    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        TextView txtName;
        Button btnDelete;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.item_rcv_history_measure_txt_name);
            btnDelete = itemView.findViewById(R.id.item_rcv_history_measure_btn_delete);
        }
    }
}