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

import com.daimajia.swipe.SwipeLayout;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.model_objects.FollowSensor;
import com.infinity.EBacSens.model_objects.Sensor;
import com.infinity.EBacSens.views.ViewRCVDevicePaired;

import java.util.ArrayList;

public class AdapteRCVDevicePaired extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<BluetoothDevice> arrItem;
    private ArrayList<FollowSensor> arrFollowItem;
    private Context context;
    private ViewRCVDevicePaired callback;

    public AdapteRCVDevicePaired(Context context, ArrayList<BluetoothDevice> arrItem , ArrayList<FollowSensor> arrFollowItem, ViewRCVDevicePaired callback) {
        this.arrItem = arrItem;
        this.arrFollowItem = arrFollowItem;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_device_paired, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;

        viewHodler.txtName.setText(arrItem.get(position).getName() == null ? arrItem.get(position).getAddress() : arrItem.get(position).getName());
        viewHodler.txtToggle.setTextColor(arrFollowItem.get(position).isToggle() ? context.getResources().getColor(R.color.toggle_text_active) : context.getResources().getColor(R.color.toggle_text_not_active));

        viewHodler.txtToggle.setText(arrFollowItem.get(position).isToggle() ? "Hoạt động" : "Off");
        viewHodler.txtToggle.setBackground(arrFollowItem.get(position).isToggle() ? context.getResources().getDrawable(R.drawable.circle_bg_button_active) : context.getResources().getDrawable(R.drawable.circle_bg_button_not_active));
        viewHodler.container.setBackgroundColor(arrFollowItem.get(position).isSelected() ? context.getResources().getColor(R.color.menu_active) : context.getResources().getColor(R.color.bg_menu));
        viewHodler.containerView.setOnClickListener(v -> callback.onClickRCVDevicePaired(position));
        viewHodler.btnUnpair.setOnClickListener(v -> callback.onUnpairRCVDevicePaired(position));
        viewHodler.container.setShowMode(SwipeLayout.ShowMode.PullOut);
    }

    @Override
    public int getItemCount() {
        return arrItem.size();

    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        TextView txtName, txtToggle;
        Button btnUnpair;
        SwipeLayout container;
        RelativeLayout containerView;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.item_rcv_menu_draw_txt_name);
            txtToggle = itemView.findViewById(R.id.item_rcv_menu_draw_txt_toggle);
            container = itemView.findViewById(R.id.item_rcv_menu_draw_continer);
            btnUnpair = itemView.findViewById(R.id.item_rcv_device_paired_btn_unpair);
            containerView = itemView.findViewById(R.id.item_rcv_device_paired_container_view);
        }
    }
}