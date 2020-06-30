package com.example.utiltool2.ui.ScrollTable.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.annotationlibrary.annotations.ContentView;
import com.example.annotationlibrary.annotations.InjectView;
import com.example.rvlibrary.RViewHelper;
import com.example.rvlibrary.base.RViewAdapter;
import com.example.rvlibrary.core.RViewCreate;
import com.example.rvlibrary.holder.RViewHolder;
import com.example.rvlibrary.model.RViewItem;
import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;
import com.example.utiltool2.ui.ScrollTable.listview.HScrollView;
import com.example.utiltool2.ui.ScrollTable.listview.ProductData;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_scrolltable)
public class ScrolltableActivity extends BaseActivity implements RViewCreate<ProductData> {

    @InjectView(R.id.scroll_rv)
    RecyclerView mRecyclerView;
    RViewItem<ProductData> item;
    @InjectView(R.id.hscrollview)
    HScrollView mHScrollView;
    @InjectView(R.id.rv_header)
    View        head;
    private RViewAdapter<ProductData> mAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        item = new Item();
        head.setClickable(true);
        head.setOnTouchListener((v, event) -> {
            mHScrollView.onTouchEvent(event);
            return false;
        });
        List<ProductData> currentData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ProductData data = new ProductData();
            data.setStr1("商品>" + (i + 1));
            data.setStr2("价格>1");
            data.setStr3("价格>2");
            data.setStr4("价格>3");
            data.setStr5("价格>4");
            data.setStr6("价格>5");
            data.setStr7("价格>6");
            currentData.add(data);
        }
        mAdapter = new RViewAdapter<>(currentData, item);
        new RViewHelper.Builder<>(this, null).build();
    }

    @Override
    public Context context() {
        return this;
    }

    @Override
    public SwipeRefreshLayout createSwipeRefresh() {
        return null;
    }

    @Override
    public RecyclerView createRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public RViewAdapter<ProductData> createRViewAdapter() {
        return mAdapter;
    }

    @Override
    public boolean isSupportPaging() {
        return false;
    }

}