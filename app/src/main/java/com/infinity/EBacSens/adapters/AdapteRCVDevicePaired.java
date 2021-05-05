package com.infinity.EBacSens.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.infinity.EBacSens.R;
import com.infinity.EBacSens.model_objects.FollowSensor;
import com.infinity.EBacSens.model_objects.SensorInfor;
import com.infinity.EBacSens.presenter.PresenterAdapterRCVDevicePaired;
import com.infinity.EBacSens.retrofit2.APIUtils;
import com.infinity.EBacSens.views.ViewAdapterRCVDevicePairedListener;
import com.infinity.EBacSens.views.ViewRCVDevicePaired;

import java.util.ArrayList;

public class AdapteRCVDevicePaired extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ViewAdapterRCVDevicePairedListener {

    private ArrayList<SensorInfor> arrItem;
    private ArrayList<FollowSensor> arrFollowItem;
    private Context context;
    private ViewRCVDevicePaired callback;

    private final int VIEW_TYPE_LOAD_MORE = 1;
    private final int VIEW_TYPE_ITEM = 0;

    private boolean onLoadMore = false, onLoadEnd = false;
    private int offset = 0;
    private final int limit = 5;

    private PresenterAdapterRCVDevicePaired presenterAdapterRCVDevicePaired;

    public AdapteRCVDevicePaired(Context context, ArrayList<SensorInfor> arrItem, ArrayList<FollowSensor> arrFollowItem, ViewRCVDevicePaired callback) {
        this.arrItem = arrItem;
        this.arrFollowItem = arrFollowItem;
        this.context = context;
        this.callback = callback;

        presenterAdapterRCVDevicePaired = new PresenterAdapterRCVDevicePaired(this);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_ITEM) {
            View view = layoutInflater.inflate(R.layout.item_rcv_device_paired, parent, false);
            return new ViewHodler(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_searhcing, parent, false);
            return new ViewholderLoading(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {
            ViewHodler viewHodler = (ViewHodler) holder;
            viewHodler.txtName.setText(arrItem.get(position).getName());
            viewHodler.txtToggle.setTextColor(arrFollowItem.get(position).isToggle() ? context.getResources().getColor(R.color.toggle_text_active) : context.getResources().getColor(R.color.toggle_text_not_active));

            viewHodler.txtToggle.setText(arrFollowItem.get(position).isToggle() ? context.getResources().getString(R.string.connected) : "Off");
            viewHodler.txtToggle.setBackground(arrFollowItem.get(position).isToggle() ? context.getResources().getDrawable(R.drawable.circle_bg_button_active) : context.getResources().getDrawable(R.drawable.circle_bg_button_not_active));
            //viewHodler.container.setBackgroundColor(arrFollowItem.get(position).isSelected() ? context.getResources().getColor(R.color.menu_active) : context.getResources().getColor(R.color.bg_menu));
            viewHodler.containerView.setOnClickListener(v -> callback.onClickRCVDevicePaired(position));
            //viewHodler.btnUnpair.setOnClickListener(v -> callback.onUnpairRCVDevicePaired(position));
            viewHodler.btnUnpair.setOnClickListener(v -> presenterAdapterRCVDevicePaired.receivedDeleteSettingSensor(APIUtils.token , arrItem.get(position).getId() , position));
            viewHodler.container.setShowMode(SwipeLayout.ShowMode.PullOut);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (arrItem.get(position).getId() == -1) {
            return VIEW_TYPE_LOAD_MORE;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return arrItem.size();
    }

    @Override
    public void onGetData(ArrayList<SensorInfor> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            for (SensorInfor item : arrayList) {
                arrItem.add(item);
                arrFollowItem.add(new FollowSensor(false , false));
                notifyItemInserted(arrItem.size() - 1);
            }
            offset += limit;
        } else {
            onLoadEnd = true;
        }
        //
        onLoadEnd = true;
    }

    @Override
    public void onLoaded() {
        onLoadMore = false;
        notifyItemRemoved(arrItem.size() - 1);
        arrItem.remove(arrItem.size() - 1);
    }

    @Override
    public void onLoadMore() {
        if (!onLoadMore && !onLoadEnd) {
            onLoadMore = true;
            arrItem.add(new SensorInfor(-1, null, null, -1 , -1 , null , null , null));
            notifyItemInserted(arrItem.size() - 1);
            presenterAdapterRCVDevicePaired.receivedGetData(limit, offset);
        }
    }

    @Override
    public void onSuccessDeleteSettingSensor(int position) {
        callback.onUnpairRCVDevicePaired(position);
    }

    @Override
    public void onFailDeleteSettingSensor(String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
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

    static class ViewholderLoading extends RecyclerView.ViewHolder {
        ViewholderLoading(@NonNull View itemView) {
            super(itemView);
        }
    }
}