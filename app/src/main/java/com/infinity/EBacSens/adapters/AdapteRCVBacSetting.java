package com.infinity.EBacSens.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.helper.Protector;
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
        viewHodler.txtIndex.setText(context.getResources().getString(R.string.microorganism) + " " + (position+1));
        viewHodler.edtBacName.setText(arrItem.get(position).getBacName());
        viewHodler.edtE1.setText(String.valueOf(arrItem.get(position).getE1()));
        viewHodler.edtE2.setText(String.valueOf(arrItem.get(position).getE2()));
        viewHodler.edtE3.setText(String.valueOf(arrItem.get(position).getE3()));
        viewHodler.edtE4.setText(String.valueOf(arrItem.get(position).getE4()));
        viewHodler.edtPkp.setText(String.valueOf(arrItem.get(position).getPkp()));

        viewHodler.edtBacName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                arrItem.get(position).setBacName(viewHodler.edtBacName.getText().toString());
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
                arrItem.get(position).setE1(Protector.tryParseInt(viewHodler.edtE1.getText().toString()));
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
                arrItem.get(position).setE2(Protector.tryParseInt(viewHodler.edtE2.getText().toString()));
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
                arrItem.get(position).setE3(Protector.tryParseInt(viewHodler.edtE3.getText().toString()));
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
                arrItem.get(position).setE4(Protector.tryParseInt(viewHodler.edtE4.getText().toString()));
            }
        });

        viewHodler.edtPkp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                arrItem.get(position).setPkp(Protector.tryParseInt(viewHodler.edtPkp.getText().toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        TextView txtIndex;
        EditText edtE1 , edtE2 , edtE3 , edtE4 , edtPkp , edtBacName;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtIndex = itemView.findViewById(R.id.item_rcv_bac_setting_txt_index);
            edtBacName = itemView.findViewById(R.id.item_rcv_bac_setting_edt_bac_name);
            edtE1 = itemView.findViewById(R.id.item_rcv_bac_setting_edt_e1);
            edtE2 = itemView.findViewById(R.id.item_rcv_bac_setting_edt_e2);
            edtE3 = itemView.findViewById(R.id.item_rcv_bac_setting_edt_e3);
            edtE4 = itemView.findViewById(R.id.item_rcv_bac_setting_edt_e4);
            edtPkp = itemView.findViewById(R.id.item_rcv_bac_setting_edt_pkp);
        }
    }
}