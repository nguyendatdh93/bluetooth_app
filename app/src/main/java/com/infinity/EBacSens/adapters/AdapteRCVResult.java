package com.infinity.EBacSens.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.model_objects.Result;

import java.util.ArrayList;

public class AdapteRCVResult extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Result> arrItem;
    private Context context;

    public AdapteRCVResult(Context context , ArrayList<Result> arrItem) {
        this.arrItem = arrItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_result, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;

        viewHodler.txtName.setText(arrItem.get(position).getName());
        viewHodler.txtResult.setText(arrItem.get(position).getResult());
        viewHodler.txtHeightTop.setText(arrItem.get(position).getHeightTop());
        viewHodler.txtBGuA.setText(arrItem.get(position).getBguA());
        viewHodler.txtError.setText(arrItem.get(position).getError());
    }

    @Override
    public int getItemCount() {
        return arrItem.size();

    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        TextView txtName , txtResult , txtHeightTop , txtBGuA , txtError;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.item_rcv_result_txt_name);
            txtResult = itemView.findViewById(R.id.item_rcv_result_txt_result);
            txtHeightTop = itemView.findViewById(R.id.item_rcv_result_txt_height_top);
            txtBGuA = itemView.findViewById(R.id.item_rcv_result_txt_bgua);
            txtError = itemView.findViewById(R.id.item_rcv_result_txt_error);
        }
    }
}