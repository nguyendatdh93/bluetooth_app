package com.infinity.EBacSens.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.data_sqllite.DBManager;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.BacSetting;
import com.infinity.EBacSens.model_objects.ItemSettingOffline;
import com.infinity.EBacSens.model_objects.SettingOffline;

import java.util.ArrayList;

public class AdapteRCVSettingOffline extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<SettingOffline> arrItem;
    private Context context;
    private DBManager dbManager;

    public AdapteRCVSettingOffline(Context context, ArrayList<SettingOffline> arrItem) {
        this.arrItem = arrItem;
        this.context = context;
        dbManager = new DBManager(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_setting_offline, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        viewHodler.txtIndex.setText("微生物" + (position+1));

        viewHodler.rcvSubSetting.setNestedScrollingEnabled(false);
        viewHodler.rcvSubSetting.setLayoutManager(new LinearLayoutManager(context));

        AdapteRCVSettingOfflineItem adapteRCVSettingOfflineItem = new AdapteRCVSettingOfflineItem(context, arrItem, arrItem.get(position).getObject() , dbManager);
        viewHodler.rcvSubSetting.setAdapter(adapteRCVSettingOfflineItem);

        viewHodler.btnAdd.setOnClickListener(v -> {
            arrItem.get(position).getObject().add(new ItemSettingOffline("0","0",0,0,0));
            adapteRCVSettingOfflineItem.notifyItemInserted(arrItem.get(position).getObject().size()-1);
            saveSettingOffline();
        });
    }

    private void saveSettingOffline(){
        dbManager.updateSettingOffline(arrItem);
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        RecyclerView rcvSubSetting;
        Button btnAdd;
        TextView txtIndex;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            btnAdd = itemView.findViewById(R.id.item_rcv_setting_offline_btn_add);
            rcvSubSetting = itemView.findViewById(R.id.item_rcv_sub_setting_offline_btn_add);
            txtIndex = itemView.findViewById(R.id.item_rcv_setting_offline_txt_index);
        }
    }

}