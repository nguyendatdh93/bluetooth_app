package com.infinity.EBacSens.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.EBacSens.R;

import java.util.ArrayList;

public class AdapteRCVSettingOfflineItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> arrItem;
    private Context context;

    public AdapteRCVSettingOfflineItem(Context context, ArrayList<String> arrItem) {
        this.arrItem = arrItem;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_sub_setting_offline, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        viewHodler.imgDelete.setOnClickListener(v -> {
            arrItem.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position , arrItem.size());
        });
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        ImageView imgDelete;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            imgDelete = itemView.findViewById(R.id.item_rcv_sub_setting_offline_btn_delete);
        }
    }

}