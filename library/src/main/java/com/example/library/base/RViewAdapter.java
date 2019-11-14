package com.example.library.base;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.holder.RViewHolder;
import com.example.library.manager.RViewItemManager;

/**
 * author:lgh on 2019-11-14 16:16
 */
public class RViewAdapter<T> extends RecyclerView.Adapter<RViewHolder> {

    private RViewItemManager<T> itemStyle;//条目类型管理

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
