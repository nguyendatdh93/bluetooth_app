package com.infinity.EBacSens.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.model_objects.ModelRCVDatetime;
import com.infinity.EBacSens.views.ViewAdapterRCVDatetimeListener;

import java.util.ArrayList;

public class AdapteRCVDatetime extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<ModelRCVDatetime> arrItem;
    private final Context context;
    private final ViewAdapterRCVDatetimeListener callback;

    private int posNow = 0;

    public AdapteRCVDatetime(Context context , ArrayList<ModelRCVDatetime> arrItem , ViewAdapterRCVDatetimeListener callback) {
        this.arrItem = arrItem;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_datetime, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;

        viewHodler.txtDatetime.setText(arrItem.get(position).getDatetime());

        if (arrItem.get(position).isSelected()){
            viewHodler.txtDatetime.setTextColor(context.getResources().getColor(R.color.primary_color));
            viewHodler.container.setBackgroundColor(context.getResources().getColor(R.color.bg_menu));
        }else {
            viewHodler.txtDatetime.setTextColor(context.getResources().getColor(R.color.black));
            viewHodler.container.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

        viewHodler.itemView.setOnClickListener(v -> {
            if (posNow != position){
                posNow = position;
                for (int i = 0 ; i < arrItem.size() ; i++){
                    if (arrItem.get(i).isSelected()){
                        arrItem.get(i).setSelected(false);
                        notifyItemChanged(i);
                        break;
                    }
                }
                arrItem.get(position).setSelected(true);
                notifyItemChanged(position);
                callback.onClickRCVDatetime(position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return arrItem.size();

    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        TextView txtDatetime;
        LinearLayout container;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtDatetime = itemView.findViewById(R.id.item_rcv_datetime_txt_datetime);
            container = itemView.findViewById(R.id.item_rcv_datetime_container);
        }
    }
}