package com.infinity.EBacSens.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.helper.Protector;
import com.infinity.EBacSens.model_objects.BacSetting;

import java.util.ArrayList;

public class AdapteRCVBacSetting extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<BacSetting> arrItem;
    private Context context;

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

    public AdapteRCVBacSetting(Context context, ArrayList<BacSetting> arrItem) {
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
        viewHodler.txtIndex.setText(context.getResources().getString(R.string.microorganism) + " " + (position + 1));
        viewHodler.edtBacName.setText(arrItem.get(position).getBacName());
        viewHodler.edtE1.setText(String.valueOf(arrItem.get(position).getE1()));
        viewHodler.edtE2.setText(String.valueOf(arrItem.get(position).getE2()));
        viewHodler.edtE3.setText(String.valueOf(arrItem.get(position).getE3()));
        viewHodler.edtE4.setText(String.valueOf(arrItem.get(position).getE4()));

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
                if (position < arrItem.size()) {
                    arrItem.get(position).setBacName(viewHodler.edtBacName.getText().toString());
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
                if (position < arrItem.size()) {
                    arrItem.get(position).setE1(Protector.tryParseInt(viewHodler.edtE1.getText().toString()));
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
                if (position < arrItem.size()) {
                    arrItem.get(position).setE2(Protector.tryParseInt(viewHodler.edtE2.getText().toString()));
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
                if (position < arrItem.size()) {
                    arrItem.get(position).setE3(Protector.tryParseInt(viewHodler.edtE3.getText().toString()));
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
                if (position < arrItem.size()) {
                    arrItem.get(position).setE4(Protector.tryParseInt(viewHodler.edtE4.getText().toString()));
                }
            }
        });

        viewHodler.acpPkp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                arrItem.get(position).setPkp(viewHodler.acpPkp.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        viewHodler.acpPkp.setSelection(arrItem.get(position).getPkp());
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        TextView txtIndex;
        EditText edtE1, edtE2, edtE3, edtE4, edtBacName;
        Spinner acpPkp;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtIndex = itemView.findViewById(R.id.item_rcv_bac_setting_txt_index);
            edtBacName = itemView.findViewById(R.id.item_rcv_bac_setting_edt_bac_name);
            edtE1 = itemView.findViewById(R.id.item_rcv_bac_setting_edt_e1);
            edtE2 = itemView.findViewById(R.id.item_rcv_bac_setting_edt_e2);
            edtE3 = itemView.findViewById(R.id.item_rcv_bac_setting_edt_e3);
            edtE4 = itemView.findViewById(R.id.item_rcv_bac_setting_edt_e4);
            acpPkp = itemView.findViewById(R.id.item_rcv_bac_setting_acp_pkp);
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