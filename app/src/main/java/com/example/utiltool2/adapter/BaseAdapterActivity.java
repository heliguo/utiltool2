package com.example.utiltool2.adapter;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.rvlibrary.RViewHelper;
import com.example.rvlibrary.SwipeRefreshHelper;
import com.example.rvlibrary.core.RViewCreate;
import com.example.annotationlibrary.InjectManager;
import com.example.utiltool2.R;

import java.util.List;


/**
 * author:lgh on 2019-11-14 11:32
 */
public abstract class BaseAdapterActivity extends AppCompatActivity implements RViewCreate, SwipeRefreshHelper.SwipeRefreshListener {

    protected RViewHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.inject(this);
        helper = new RViewHelper.Builder(this, this).build();
    }


    @Override
    public Context context() {
        return this;
    }

    @Override
    public SwipeRefreshLayout createSwipeRefresh() {
        return findViewById(R.id.swiperefresh);
    }

    @Override
    public RecyclerView createRecyclerView() {
        return findViewById(R.id.recyclerview);
    }

    @Override
    public boolean isSupportPaging() {
        return false;
    }

    public void notifyAdapterDataSetChanged(List datas) {
        helper.notifyAdapterDataSetChanged(datas);
    }

}
