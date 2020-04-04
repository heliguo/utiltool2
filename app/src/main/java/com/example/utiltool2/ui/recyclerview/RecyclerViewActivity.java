package com.example.utiltool2.ui.recyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author:lgh on 2019-10-17 14:50
 *
 * 1、computeVerticalScrollExtent()是当前屏幕显示的区域高度
 * 2、computeVerticalScrollOffset() 是当前屏幕之前滑过的距离
 * 3、computeVerticalScrollRange()是整个RecycleView控件的高度
 * 4、recyclerView.getLastVisiblePosition()获取当前可见的最后一个item的position
 *
 *
 */
public class RecyclerViewActivity extends BaseActivity {

    private MyRecyclerViewAdapter adapter;
    private List<String> list;
    private RecyclerView recyclerView;
    private StaggeredHomeAdapter staggeredHomeAdapter;


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
//        setListView();
        //设置GridView
//        setGridView();
//        //设置瀑布流
        setWaterfallView();

    }

    private void setListView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, list);
        recyclerView.setItemAnimator(new DefaultItemAnimator());//默认加载动画
        recyclerView.addItemDecoration(new DividerItemDecoration(RecyclerViewActivity.this, DividerItemDecoration.VERTICAL_LIST));//加分割线
        setListener();
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager linearLayoutManager = recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                //newState分 0,1,2三个状态,2是滚动状态,0是停止
                // SCROLL_STATE_IDLE = 0
                //SCROLL_STATE_DRAGGING = 1
                //SCROLL_STATE_SETTLING = 2
                super.onScrollStateChanged(recyclerView, newState);
                //-1代表顶部,返回true表示没到顶,还可以滑
                //1代表底部,返回true表示没到底部,还可以滑
                boolean b = recyclerView.canScrollVertically(1);
                boolean b1 = recyclerView.canScrollVertically(-1);
                //Return the current number of child views attached to the parent RecyclerView
                int visibleItemCount = linearLayoutManager.getChildCount(); //子数
                //The number of items in the bound adapter
                int totalItemCount = linearLayoutManager.getItemCount(); // item总数


                //当前页面最后一个显示的数据位置
                ((LinearLayoutManager) linearLayoutManager).findLastVisibleItemPosition();
                //当前页面显示的第一个数据位置
                ((LinearLayoutManager) linearLayoutManager).findFirstVisibleItemPosition();
            }
        });
    }


    private void setGridView() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());//默认加载动画
        adapter = new MyRecyclerViewAdapter(this, list);
        setListener();
        recyclerView.setAdapter(adapter);
    }


    private void setWaterfallView() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,
                StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        staggeredHomeAdapter = new StaggeredHomeAdapter(this, list);
        staggeredHomeAdapter.setOnItemClickLitener(new StaggeredHomeAdapter.OnItemClickLitener() {
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
                                staggeredHomeAdapter.removeData(position);
                                staggeredHomeAdapter.notifyDataSetChanged();//必须刷新数据
                            }
                        })
                        .show();
            }
        });
        recyclerView.setAdapter(staggeredHomeAdapter);

    }

    private void setListener() {
        adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "点击第" + (position + 1) + "条", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, final int position) {
//                Toast.makeText(RecyclerViewActivity.this, "长按了第" + (position + 1) + "条", Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(RecyclerViewActivity.this)
                        .setTitle("确认删除吗？")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapter.removeData(position);
                                adapter.notifyDataSetChanged();//必须刷新数据
                            }
                        })
                        .show();

            }
        });
    }
}
