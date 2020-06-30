package com.example.utiltool2.ui.ScrollTable.listview;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;

import java.util.ArrayList;
import java.util.List;


public class ScrollTableActivity extends BaseActivity {

    //头部吸顶的右侧滑动视图
    private HScrollView itemView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hscrolltable);
        View head = findViewById(R.id.header);
        head.setBackgroundColor(Color.RED);
        itemView = head.findViewById(R.id.hscrollview);
        //列表视图
        ListView listView = findViewById(R.id.lv_produce);
        listView.setOnTouchListener((v, event) -> {
            itemView.onTouchEvent(event);
            return false;
        });//将touch事件传递给HScrollView
        head.setClickable(true);
        head.setOnTouchListener((v, event) -> {
            itemView.onTouchEvent(event);
            return false;
        });
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

        //数据适配器
        HSAdapter HSAdapter = new HSAdapter(this, itemView, currentData, R.layout.item_hscroll_view);
        listView.setAdapter(HSAdapter);
        listView.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(ScrollTableActivity.this,
                        "点了第" + (position + 1) + "个", Toast.LENGTH_SHORT).show());

    }
}
