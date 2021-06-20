package com.infinity.EBacSens.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.EBacSens.R;
import com.infinity.EBacSens.model_objects.Graph;

import java.util.ArrayList;

public class AdapteRCVGraph extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ArrayList<Graph> arrItem;
    private final Context context;

    public AdapteRCVGraph(Context context, ArrayList<Graph> arrItem) {
        this.arrItem = arrItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_rcv_graph, parent, false);
        return new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;

        viewHodler.txtName.setText(arrItem.get(position).getName());
//        viewHodler.txtResult.setText(String.valueOf(Math.round(arrItem.get(position).getResult())));
//        viewHodler.txtLevel.setText(String.valueOf(Math.round(arrItem.get(position).getLevel())));
        viewHodler.txtResult.setText(arrItem.get(position).getResult());
        viewHodler.txtLevel.setText(arrItem.get(position).getLevel());
        viewHodler.txtDescription.setText(arrItem.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    static class ViewHodler extends RecyclerView.ViewHolder {
        TextView txtName, txtResult, txtLevel, txtDescription;

        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.item_rcv_graph_txt_name);
            txtResult = itemView.findViewById(R.id.item_rcv_graph_txt_result);
            txtLevel = itemView.findViewById(R.id.item_rcv_graph_txt_level);
            txtDescription = itemView.findViewById(R.id.item_rcv_graph_txt_desciption);
        }
    }
}