package com.infinity.EBacSens.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.data_sqllite.DBManager;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.ItemSettingOffline;
import com.infinity.EBacSens.model_objects.SettingOffline;

import java.util.ArrayList;

public class AdapteRCVSettingOfflineItem extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<SettingOffline> arrParentItem;
    private ArrayList<ItemSettingOffline> arrItem;
    private Context context;
    private DBManager dbManager;

    public AdapteRCVSettingOfflineItem(Context context, ArrayList<SettingOffline> arrParentItem, ArrayList<ItemSettingOffline> arrItem, DBManager dbManager) {
        this.arrParentItem = arrParentItem;
        this.arrItem = arrItem;
        this.context = context;
        this.dbManager = dbManager;
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
            //notifyItemRemoved(position);
            //notifyItemRangeChanged( Math.max(position -1 , 0), arrItem.size() - 1);
            notifyDataSetChanged();
            saveSettingOffline();
        });

        viewHodler.edtDLTCfrom.setText(String.valueOf(arrItem.get(position).getDltc_from()));
        viewHodler.edtDLTCto.setText(String.valueOf(arrItem.get(position).getDltc_to()));
        viewHodler.edtQuantityFrom.setText(String.valueOf(arrItem.get(position).getQuantity_from()));
        viewHodler.edtQuantityTo.setText(String.valueOf(arrItem.get(position).getQuantity_to()));
        viewHodler.edtLevel.setText(String.valueOf(arrItem.get(position).getLevel()));

        viewHodler.btnSave.setOnClickListener(v -> {
            arrItem.get(holder.getAdapterPosition()).setDltc_from(viewHodler.edtDLTCfrom.getText().toString());
            arrItem.get(holder.getAdapterPosition()).setDltc_to(viewHodler.edtDLTCto.getText().toString());
            arrItem.get(holder.getAdapterPosition()).setQuantity_from(Protector.tryParseInt(viewHodler.edtQuantityFrom.getText().toString()));
            arrItem.get(holder.getAdapterPosition()).setQuantity_to(Protector.tryParseInt(viewHodler.edtQuantityTo.getText().toString()));
            arrItem.get(holder.getAdapterPosition()).setLevel(Protector.tryParseInt(viewHodler.edtLevel.getText().toString()));
            saveSettingOffline();
            Toast.makeText(context, "保存しました。", Toast.LENGTH_SHORT).show();
        });

        if(arrItem.size() -1 == position){
            viewHodler.edtDLTCfrom.setVisibility(View.INVISIBLE);
            viewHodler.edtQuantityFrom.setVisibility(View.INVISIBLE);
            viewHodler.txtDltc.setText("≥");
            viewHodler.txtQuantity.setText("≥");
        }else{
            viewHodler.edtDLTCfrom.setVisibility(View.VISIBLE);
            viewHodler.edtQuantityFrom.setVisibility(View.VISIBLE);
            viewHodler.txtDltc.setText("〜");
            viewHodler.txtQuantity.setText("〜");
        }

//        viewHodler.edtDLTCfrom.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                arrItem.get(holder.getAdapterPosition()).setDltc_from(Protector.tryParseFloat(viewHodler.edtDLTCfrom.getText().toString()));
//                saveSettingOffline();
//            }
//        });
//
//        viewHodler.edtDLTCto.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                arrItem.get(holder.getAdapterPosition()).setDltc_to(Protector.tryParseFloat(viewHodler.edtDLTCto.getText().toString()));
//                saveSettingOffline();
//            }
//        });
//
//        viewHodler.edtQuantityFrom.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                arrItem.get(holder.getAdapterPosition()).setQuantity_from(Protector.tryParseInt(viewHodler.edtQuantityFrom.getText().toString()));
//                saveSettingOffline();
//            }
//        });
//
//        viewHodler.edtQuantityTo.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                arrItem.get(holder.getAdapterPosition()).setQuantity_to(Protector.tryParseInt(viewHodler.edtQuantityTo.getText().toString()));
//                saveSettingOffline();
//            }
//        });
//
//        viewHodler.edtLevel.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                arrItem.get(holder.getAdapterPosition()).setLevel(Protector.tryParseInt(viewHodler.edtLevel.getText().toString()));
//                saveSettingOffline();
//            }
//        });
    }

    private void saveSettingOffline() {
        dbManager.updateSettingOffline(arrParentItem);
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        Button btnSave;
        ImageView imgDelete;
        EditText edtLevel, edtDLTCfrom, edtDLTCto, edtQuantityFrom, edtQuantityTo;
        TextView txtDltc , txtQuantity;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            btnSave = itemView.findViewById(R.id.item_rcv_sub_setting_offline_btn_save);
            imgDelete = itemView.findViewById(R.id.item_rcv_sub_setting_offline_btn_delete);
            edtLevel = itemView.findViewById(R.id.item_rcv_sub_setting_offline_edt_level);
            edtDLTCfrom = itemView.findViewById(R.id.item_rcv_sub_setting_offline_edt_dltc_from);
            edtDLTCto = itemView.findViewById(R.id.item_rcv_sub_setting_offline_edt_dltc_to);
            edtQuantityFrom = itemView.findViewById(R.id.item_rcv_sub_setting_offline_edt_quantity_from);
            edtQuantityTo = itemView.findViewById(R.id.item_rcv_sub_setting_offline_edt_quantity_to);
            txtDltc = itemView.findViewById(R.id.item_rcv_sub_setting_offline_txt_dltc);
            txtQuantity = itemView.findViewById(R.id.item_rcv_sub_setting_offline_txt_quantity);
        }
    }

}