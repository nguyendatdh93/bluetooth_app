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
import com.infinity.EBacSens.views.ViewRCVDeviceOnline;

import java.util.ArrayList;

public class AdapteRCVDeviceOnline extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<BluetoothDevice> arrItem;
    private Context context;
    private ViewRCVDeviceOnline callback;

    private int TYPE_VIEW_HOLDER = 0 , TYPE_VIEW_SEARCHING = 1;

    public AdapteRCVDeviceOnline(Context context , ArrayList<BluetoothDevice> arrItem , ViewRCVDeviceOnline callback) {
        this.arrItem = arrItem;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_VIEW_HOLDER){
            View view = layoutInflater.inflate(R.layout.item_rcv_device_online, parent, false);
            return new ViewHodler(view);
        }else {
            View view = layoutInflater.inflate(R.layout.item_searhcing, parent, false);
            return new ViewSẻaching(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_VIEW_HOLDER){
            ViewHodler viewHodler = (ViewHodler) holder;
            viewHodler.txtName.setText(arrItem.get(position).getName() == null ? arrItem.get(position).getAddress() : arrItem.get(position).getName());
            viewHodler.itemView.setOnClickListener(v -> callback.onClickRCVDeviceOnline(position));
            viewHodler.btnConnnect.setOnClickListener(v -> callback.onClickRCVDeviceOnline(position));
        }
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    @Override
    public int getItemViewType(int position) {
        return arrItem.get(position) == null ? TYPE_VIEW_SEARCHING : TYPE_VIEW_HOLDER;
    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        TextView txtName;
        Button btnConnnect;
        RelativeLayout container;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.item_rcv_menu_draw_txt_name);
            btnConnnect = itemView.findViewById(R.id.item_rcv_menu_draw_btn_connect);
            container = itemView.findViewById(R.id.item_rcv_menu_draw_continer);
        }
    }

    static class ViewSẻaching extends RecyclerView.ViewHolder {

        public ViewSẻaching(@NonNull View itemView) {
            super(itemView);
        }
    }
}