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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.BacSetting;

import java.util.ArrayList;

public class AdapteRCVSettingOffline extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> arrItem;
    private Context context;

    public AdapteRCVSettingOffline(Context context, ArrayList<String> arrItem) {
        this.arrItem = arrItem;
        this.context = context;
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
        viewHodler.btnAdd.setOnClickListener(v -> {

        });

        ArrayList<String> arrSettingOffline = new ArrayList<>();
        arrSettingOffline.add("a");
        arrSettingOffline.add("a");

        viewHodler.rcvSubSetting.setNestedScrollingEnabled(false);
        viewHodler.rcvSubSetting.setLayoutManager(new LinearLayoutManager(context));

        AdapteRCVSettingOffline adapteRCVSettingOffline = new AdapteRCVSettingOffline(context, arrSettingOffline);
        viewHodler.rcvSubSetting.setAdapter(adapteRCVSettingOffline);

    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        RecyclerView rcvSubSetting;
        Button btnAdd;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            btnAdd = itemView.findViewById(R.id.item_rcv_setting_offline_btn_add);
            rcvSubSetting = itemView.findViewById(R.id.item_rcv_sub_setting_offline_btn_add);
        }
    }

}