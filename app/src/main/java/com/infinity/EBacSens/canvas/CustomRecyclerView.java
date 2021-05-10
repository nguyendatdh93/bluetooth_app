package com.infinity.EBacSens.canvas;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.infinity.EBacSens.R;

import org.jetbrains.annotations.NotNull;

public class CustomRecyclerView extends RecyclerView {
    public CustomRecyclerView(@NonNull @NotNull Context context) {
        super(context);
    }

    public CustomRecyclerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        heightSpec = MeasureSpec.makeMeasureSpec((int) getResources().getDimension(R.dimen._150sdp), MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightSpec);
    }
}
