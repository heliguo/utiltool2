package com.example.rvlibrary.base;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rvlibrary.holder.RViewHolder;
import com.example.rvlibrary.listener.ItemListener;
import com.example.rvlibrary.manager.RViewItemManager;
import com.example.rvlibrary.model.RViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * author:lgh on 2019-11-14 16:16
 */
public class RViewAdapter<T> extends RecyclerView.Adapter<RViewHolder> {

    private RViewItemManager<T> itemStyle;//条目类型管理
    private ItemListener<T>     itemListener;//item点击事件监听
    private List<T>             datas;//数据源

    //单一布局
    public RViewAdapter(List<T> datas) {
        if (datas == null)
            this.datas = new ArrayList<>();
        this.datas = datas;
        itemStyle = new RViewItemManager<>();
    }

    //嵌套（多样式布局）
    public RViewAdapter(List<T> datas, RViewItem<T> item) {
        if (datas == null)
            this.datas = new ArrayList<>();
        this.datas = datas;
        itemStyle = new RViewItemManager<>();

        addItemStyle(item);
    }

    public void addItemStyle(RViewItem<T> item) {
        itemStyle.addStyle(item);
    }

    //是否有多样式布局
    private boolean hasMutiStyle() {
        return itemStyle.getItemViewStylesCount() > 0;
    }

    @Override
    public int getItemViewType(int position) {
        //多样式布局
        if (hasMutiStyle())
            return itemStyle.getItemViewType(datas.get(position), position);
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RViewItem<T> rViewItem = itemStyle.getRViewItem(viewType);
        RViewHolder holder = RViewHolder.createViewHolder(parent.getContext(), parent, rViewItem.getItemLayout());
        if (rViewItem.openClick())
            setListener(holder);
        return holder;
    }

    private void setListener(final RViewHolder holder) {
        holder.getConvertView().setOnClickListener(v -> {
            if (itemListener != null) {
                int position = holder.getAdapterPosition();//当前整个条目类型可点击
                itemListener.onItemClick(v, datas.get(position), position);
            }
        });

        holder.getConvertView().setOnLongClickListener(v -> true);
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder holder, int position) {
        convert(holder, datas.get(position));
    }

    private void convert(RViewHolder holder, T entity) {
        itemStyle.convert(holder, entity, holder.getAdapterPosition());
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }


    /**
     * 监听事件
     *
     * @param itemListener listener
     */
    public void setItemListener(ItemListener<T> itemListener) {
        this.itemListener = itemListener;
    }

    /**
     * 更新所有数据
     *
     * @param datas data
     */
    public void updateDatas(List<T> datas) {
        if (datas == null)
            return;
        this.datas = datas;
        notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param datas data
     */
    public void addDatas(List<T> datas) {
        if (datas == null)
            return;
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

}
