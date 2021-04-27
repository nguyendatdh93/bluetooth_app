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
import com.infinity.EBacSens.model_objects.BacSetting;
import com.infinity.EBacSens.views.ViewRCVDeviceOnline;

import java.util.ArrayList;

public class AdapteRCVBacSetting extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<BacSetting> arrItem;
    private Context context;

    public AdapteRCVBacSetting(Context context , ArrayList<BacSetting> arrItem) {
        this.arrItem = arrItem;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_bac_setting, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        viewHodler.txtIndex.setText(context.getResources().getString(R.string.microorganism) + " " + arrItem.get(position).getId());
        viewHodler.txtBacName.setText(String.valueOf(arrItem.get(position).getBacName()));
        viewHodler.txtE1.setText(String.valueOf(arrItem.get(position).getE1()));
        viewHodler.txtE2.setText(String.valueOf(arrItem.get(position).getE2()));
        viewHodler.txtE3.setText(String.valueOf(arrItem.get(position).getE3()));
        viewHodler.txtE4.setText(String.valueOf(arrItem.get(position).getE4()));
        viewHodler.txtPkp.setText(String.valueOf(arrItem.get(position).getPkp()));
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        TextView txtIndex , txtE1 , txtE2 , txtE3 , txtE4 , txtPkp , txtBacName;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtIndex = itemView.findViewById(R.id.item_rcv_bac_setting_txt_index);
            txtBacName = itemView.findViewById(R.id.item_rcv_bac_setting_txt_bac_name);
            txtE1 = itemView.findViewById(R.id.item_rcv_bac_setting_txt_e1);
            txtE2 = itemView.findViewById(R.id.item_rcv_bac_setting_txt_e2);
            txtE3 = itemView.findViewById(R.id.item_rcv_bac_setting_txt_e3);
            txtE4 = itemView.findViewById(R.id.item_rcv_bac_setting_txt_e4);
            txtPkp = itemView.findViewById(R.id.item_rcv_bac_setting_txt_pkp);
        }
    }
}