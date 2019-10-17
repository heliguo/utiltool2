package com.example.utiltool2.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utiltool2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author:lgh on 2019-10-17 14:50
 */
public class RecyclerViewActivity extends AppCompatActivity {

    private MyRecyclerViewAdapter adapter;
    private List<String> list;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initData();
        initView();
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i + "");
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerview);

        //设置ListView
        setListView();
        //设置GridView
//        setGridView();
//        //设置瀑布流
//        setWaterfallView();

    }

    private void setListView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());//默认加载动画
        recyclerView.addItemDecoration(new DividerItemDecoration(RecyclerViewActivity.this, DividerItemDecoration.VERTICAL_LIST));//加分割线
        setListener();
        recyclerView.setAdapter(adapter);
    }


    private void setGridView() {

    }


    private void setWaterfallView() {

    }

    private void setListener() {
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "点击第" + (position + 1) + "条", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                new AlertDialog.Builder(RecyclerViewActivity.this)
                        .setTitle("确认删除吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapter.removeData(position);
                            }
                        })
                        .show();

            }
        });
    }
}
