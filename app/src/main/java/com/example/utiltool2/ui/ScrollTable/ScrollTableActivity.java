package com.example.utiltool2.ui.ScrollTable;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;

import java.util.ArrayList;
import java.util.List;


public class ScrollTableActivity extends BaseActivity {

    //头部吸顶的右侧滑动视图
    private HScrollView mHeadStickHS;
    private View        mHeadStickView;
    //列表视图
    private ListView    mListView;
    //数据适配器
    private HSAdapter   mHSAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hscrolltable);
        mHeadStickView = findViewById(R.id.header);
        mHeadStickView.setBackgroundColor(Color.RED);
        mHeadStickHS = findViewById(R.id.hscrollview);
        mListView = findViewById(R.id.lv_produce);
        mListView.setOnTouchListener(new ListViewAndHeadViewTouchListener());//将touch事件传递给HScrollView

        List<ProductData> currentData = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            ProductData data = new ProductData();
            data.setStr1("商品>" + (i + 1));//左侧标题
            data.setStr2("价格>1");
            data.setStr3("价格>2");
            data.setStr4("价格>3");
            data.setStr5("价格>4");
            data.setStr6("价格>5");
            data.setStr7("价格>6");
            currentData.add(data);
        }

        mHSAdapter = new HSAdapter(this, mHeadStickHS, currentData, R.layout.item_hscroll_view);
        mListView.setAdapter(mHSAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ScrollTableActivity.this, "点了第" + (position + 1) + "个", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 用来将头部和列表上面的触摸事件都分发给头部的滑动控件
     */
    public class ListViewAndHeadViewTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mHeadStickHS.onTouchEvent(event);
            return false;
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
