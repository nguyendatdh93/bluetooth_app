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

    public AdapteRCVDeviceOnline(Context context , ArrayList<BluetoothDevice> arrItem , ViewRCVDeviceOnline callback) {
        this.arrItem = arrItem;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_device_online, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;

        viewHodler.txtName.setText(arrItem.get(position).getName() == null ? arrItem.get(position).getAddress() : arrItem.get(position).getName());
        //viewHodler.txtToggle.setTextColor(arrItem.get(position).isToggle() ? context.getResources().getColor(R.color.toggle_text_active) :  context.getResources().getColor(R.color.toggle_text_not_active));

        //viewHodler.txtToggle.setText(arrItem.get(position).isToggle() ? "Hoạt động" : "Off");
        //viewHodler.txtToggle.setBackground(arrItem.get(position).isToggle() ? context.getResources().getDrawable(R.drawable.circle_bg_button_active) : context.getResources().getDrawable(R.drawable.circle_bg_button_not_active));
        //viewHodler.container.setBackgroundColor(arrItem.get(position).isSelected() ? context.getResources().getColor(R.color.menu_active) : context.getResources().getColor(R.color.bg_menu));
        viewHodler.itemView.setOnClickListener(v -> callback.onClickRCVDeviceOnline(position));
        viewHodler.btnConnnect.setOnClickListener(v -> callback.onClickRCVDeviceOnline(position));
    }

    @Override
    public int getItemCount() {
        return arrItem.size();

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
}