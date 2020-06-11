package com.example.rvlibrary;

import android.content.Context;
import android.util.Log;

import com.example.rvlibrary.base.RViewAdapter;
import com.example.rvlibrary.core.RViewCreate;

import java.util.List;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * author:lgh on 2019-11-14 15:45
 * 辅助类
 */
public class RViewHelper<T> {

    private Context                                 context;
    private SwipeRefreshLayout                      swipeRefreshLayout;
    private SwipeRefreshHelper                      swipeRefreshHelper;
    private RecyclerView                            recyclerView;
    private RViewAdapter<T>                         adapter;
    private int                                     startPageNumber = 1;
    private boolean                                 isSupportPaging;
    private SwipeRefreshHelper.SwipeRefreshListener swipeRefreshListener;
    private int                                     currentPageNumber;//当前页

    private RViewHelper(Builder<T> builder) {
        this.swipeRefreshLayout = builder.create.createSwipeRefresh();
        this.context = builder.create.context();
        this.adapter = builder.create.createRViewAdapter();
        this.isSupportPaging = builder.create.isSupportPaging();
        this.recyclerView = builder.create.createRecyclerView();
        this.currentPageNumber = this.startPageNumber;
        this.swipeRefreshListener = builder.listener;
        if (swipeRefreshLayout != null) {
            swipeRefreshHelper = SwipeRefreshHelper.createSwipeRefreshHelper(swipeRefreshLayout);
        }
        init();
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (swipeRefreshHelper != null) {
            swipeRefreshHelper.setSwipeRefreshListener(() -> {
                dismissSwipeRefresh();//停止刷新
                //重置页码
                currentPageNumber = startPageNumber;
                if (swipeRefreshListener != null)
                    swipeRefreshListener.OnRefresh();
            });
        }

    }

    private void dismissSwipeRefresh() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public void notifyAdapterDataSetChanged(List<T> datas) {
        //如果首次加载，下拉刷新
        if (currentPageNumber == startPageNumber) {
            adapter.updateDatas(datas);
        } else {
            adapter.addDatas(datas);
        }
        recyclerView.setAdapter(adapter);
        if (isSupportPaging) {
            //支持分页
            Log.e("RViewHelper", "notifyAdapterDataSetChanged: " + "分页功能");
        }
    }

    public static class Builder<T> {
        private RViewCreate<T>                          create;
        private SwipeRefreshHelper.SwipeRefreshListener listener;

        public Builder(RViewCreate<T> create, SwipeRefreshHelper.SwipeRefreshListener listener) {
            this.create = create;
            this.listener = listener;
        }

        public RViewHelper<T> build() {
            return new RViewHelper<>(this);
        }
    }
}
