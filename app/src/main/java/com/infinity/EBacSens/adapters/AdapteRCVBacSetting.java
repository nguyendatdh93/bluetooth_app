package com.infinity.EBacSens.adapters;

import static com.infinity.EBacSens.helper.Protector.STATUS_NETWORK;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

public class AdapteRCVBacSetting extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<BacSetting> arrItem;
    private Context context;
    private DBManager dbManager;
    private boolean alertName = false;
    private String errorName;
    private boolean alertE1 = false;
    private String errorE1;
    private boolean alertE2 = false;
    private String errorE2;
    private boolean alertE3 = false;
    private String errorE3;
    private boolean alertE4 = false;
    private String errorE4;

    private ArrayList<SettingOffline> settingOfflines;

    public AdapteRCVBacSetting(Context context, ArrayList<BacSetting> arrItem , ArrayList<SettingOffline> settingOfflines) {
        this.arrItem = arrItem;
        this.context = context;
        this.settingOfflines = settingOfflines;
        dbManager = new DBManager(context);
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
        viewHodler.txtIndex.setText(context.getResources().getString(R.string.microorganism) + " " + (holder.getAdapterPosition() + 1));
        viewHodler.edtBacName.setText(arrItem.get(holder.getAdapterPosition()).getBacName());
        viewHodler.edtE1.setText(String.valueOf(arrItem.get(holder.getAdapterPosition()).getE1()));
        viewHodler.edtE2.setText(String.valueOf(arrItem.get(holder.getAdapterPosition()).getE2()));
        viewHodler.edtE3.setText(String.valueOf(arrItem.get(holder.getAdapterPosition()).getE3()));
        viewHodler.edtE4.setText(String.valueOf(arrItem.get(holder.getAdapterPosition()).getE4()));

        if (alertName) {
            alertName = false;
            viewHodler.edtBacName.setError(errorName);
            viewHodler.edtBacName.requestFocus();
        }

        if (alertE1) {
            alertE1 = false;
            viewHodler.edtE1.setError(errorE1);
            viewHodler.edtE1.requestFocus();
        }

        if (alertE2) {
            alertE2 = false;
            viewHodler.edtE2.setError(errorE2);
            viewHodler.edtE2.requestFocus();
        }

        if (alertE3) {
            alertE3 = false;
            viewHodler.edtE3.setError(errorE3);
            viewHodler.edtE3.requestFocus();
        }

        if (alertE4) {
            alertE4 = false;
            viewHodler.edtE4.setError(errorE4);
            viewHodler.edtE4.requestFocus();
        }

        viewHodler.edtBacName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.getAdapterPosition() < arrItem.size()) {
                    arrItem.get(holder.getAdapterPosition()).setBacName(viewHodler.edtBacName.getText().toString());
                }
            }
        });

        viewHodler.edtE1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.getAdapterPosition() < arrItem.size()) {
                    arrItem.get(holder.getAdapterPosition()).setE1(Protector.tryParseInt(viewHodler.edtE1.getText().toString()));
                }
            }
        });

        viewHodler.edtE2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.getAdapterPosition() < arrItem.size()) {
                    arrItem.get(holder.getAdapterPosition()).setE2(Protector.tryParseInt(viewHodler.edtE2.getText().toString()));
                }
            }
        });

        viewHodler.edtE3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.getAdapterPosition() < arrItem.size()) {
                    arrItem.get(holder.getAdapterPosition()).setE3(Protector.tryParseInt(viewHodler.edtE3.getText().toString()));
                }
            }
        });

        viewHodler.edtE4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.getAdapterPosition() < arrItem.size()) {
                    arrItem.get(holder.getAdapterPosition()).setE4(Protector.tryParseInt(viewHodler.edtE4.getText().toString()));
                }
            }
        });

        viewHodler.acpPkp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                arrItem.get(holder.getAdapterPosition()).setPkp(viewHodler.acpPkp.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHodler.acpPkp.setSelection(arrItem.get(position).getPkp());

        if(!STATUS_NETWORK){
            viewHodler.containerSettingOffline.setVisibility(View.VISIBLE);

            viewHodler.txtIndexSettingOffline.setText("※微生物" + (position+1) + "のレベル表示設定");

            viewHodler.rcvSubSettingOffline.setNestedScrollingEnabled(false);
            viewHodler.rcvSubSettingOffline.setLayoutManager(new LinearLayoutManager(context));

            AdapteRCVSettingOfflineItem adapteRCVSettingOfflineItem = new AdapteRCVSettingOfflineItem(context,settingOfflines, settingOfflines.get(position).getObject() , dbManager);
            viewHodler.rcvSubSettingOffline.setAdapter(adapteRCVSettingOfflineItem);

            viewHodler.btnAddSettingOffline.setOnClickListener(v -> {
                settingOfflines.get(position).getObject().add(new ItemSettingOffline("0","0","0","0",0));
                //notifyItemRangeChanged(position, arrItem.size());
                adapteRCVSettingOfflineItem.notifyDataSetChanged();
                //adapteRCVSettingOfflineItem.notifyItemRangeChanged(0 , settingOfflines.get(position).getObject().size()-1);
                saveSettingOffline();
            });

        }
    }

    private void saveSettingOffline(){
        dbManager.updateSettingOffline(settingOfflines);
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        TextView txtIndex;
        EditText edtE1, edtE2, edtE3, edtE4, edtBacName;
        Spinner acpPkp;
        LinearLayout containerSettingOffline;
        TextView txtIndexSettingOffline;
        RecyclerView rcvSubSettingOffline;
        Button btnAddSettingOffline;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtIndex = itemView.findViewById(R.id.item_rcv_bac_setting_txt_index);
            edtBacName = itemView.findViewById(R.id.item_rcv_bac_setting_edt_bac_name);
            edtE1 = itemView.findViewById(R.id.item_rcv_bac_setting_edt_e1);
            edtE2 = itemView.findViewById(R.id.item_rcv_bac_setting_edt_e2);
            edtE3 = itemView.findViewById(R.id.item_rcv_bac_setting_edt_e3);
            edtE4 = itemView.findViewById(R.id.item_rcv_bac_setting_edt_e4);
            acpPkp = itemView.findViewById(R.id.item_rcv_bac_setting_acp_pkp);
            containerSettingOffline = itemView.findViewById(R.id.item_rcv_bac_setting_container_setting_offline);
            txtIndexSettingOffline = itemView.findViewById(R.id.item_rcv_bac_setting_txt_index_setting_offline);
            rcvSubSettingOffline = itemView.findViewById(R.id.item_rcv_bac_setting_rcv_setting_offline);
            btnAddSettingOffline = itemView.findViewById(R.id.item_rcv_bac_setting_btn_add);
        }
    }

    public void alertName(int position, String error) {
        alertName = true;
        errorName = error;
        notifyItemChanged(position);
    }

    public void alertE1(int position, String error) {
        alertE1 = true;
        errorE1 = error;
        notifyItemChanged(position);
    }

    public void alertE2(int position, String error) {
        alertE2 = true;
        errorE2 = error;
        notifyItemChanged(position);
    }

    public void alertE3(int position, String error) {
        alertE3 = true;
        errorE3 = error;
        notifyItemChanged(position);
    }

    public void alertE4(int position, String error) {
        alertE4 = true;
        errorE4 = error;
        notifyItemChanged(position);
    }
}