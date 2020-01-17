package com.example.utiltool2.ui.recyclerview;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author:lgh on 2020-01-15 09:33
 */
public class TestRecyclerView extends RecyclerView {

    public TestRecyclerView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected float getTopFadingEdgeStrength() {
        return super.getTopFadingEdgeStrength();
    }

    @Override
    protected float getBottomFadingEdgeStrength() {
        return super.getBottomFadingEdgeStrength();
    }
}
