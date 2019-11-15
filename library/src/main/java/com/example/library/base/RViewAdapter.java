package com.example.library.base;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.library.holder.RViewHolder;
import com.example.library.listener.ItemListener;
import com.example.library.manager.RViewItemManager;
import com.example.library.model.RViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * author:lgh on 2019-11-14 16:16
 */
public class RViewAdapter<T> extends RecyclerView.Adapter<RViewHolder> {

    private RViewItemManager<T> itemStyle;//条目类型管理
    private ItemListener<T> itemListener;//item点击事件监听
    private List<T> datas;//数据源

    //单一布局
    public RViewAdapter(List<T> datas) {
        if (datas == null) this.datas = new ArrayList<>();
        this.datas = datas;
        itemStyle = new RViewItemManager<>();
    }

    //嵌套（多样式布局）
    public RViewAdapter(List<T> datas, RViewItem<T> item) {
        if (datas == null) this.datas = new ArrayList<>();
        this.datas = datas;
        itemStyle = new RViewItemManager<>();

        addItemStyle(item);
    }

    private void addItemStyle(RViewItem<T> item) {
        itemStyle.addStyle(item);
    }

    //是否有多样式布局
    private boolean hasMutiStyle() {
        return itemStyle.getItemViewStylesCount() > 0;
    }

    @Override
    public int getItemViewType(int position) {
        //多样式布局
        if (hasMutiStyle()) return itemStyle.getItemViewType(datas.get(position),position);
        return super.getItemViewType(position);
    }

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
        return datas == null ? 0 : datas.size();
    }
}
