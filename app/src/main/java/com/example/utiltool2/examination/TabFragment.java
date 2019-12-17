package com.example.utiltool2.examination;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.utiltool2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 lgh
 * @创建时间 2019-08-15 11:15
 * @描述
 */
public class TabFragment extends Fragment {

    private List<String> mDatas;
    private String titles;

    boolean mIsPrepare = false;        //视图还没准备好
    boolean mIsVisible = false;        //不可见
    boolean mIsFirstLoad = true;    //第一次加载


    public static TabFragment getInstance(String titles) {
        //使用bundle传入标题
        TabFragment fragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", titles);
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            titles = bundle.getString("title");
        }
        mIsPrepare = true;//准备好加载
        lazyLoad();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initData();
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new RecycleViewAdapter(recyclerView.getContext(), mDatas));
        return view;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mIsVisible = true;
            lazyLoad();
        } else {
            mIsVisible = false;
        }
    }


    //实际数据与标题无关
    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'D'; i++) {
            mDatas.add(titles + (char) i);
        }
    }

    //懒加载
    private void lazyLoad() {
        //这里进行三个条件的判断，如果有一个不满足，都将不进行加载
        if (!mIsPrepare || !mIsVisible || !mIsFirstLoad) {
            return;
        }
        loadData();
        //数据加载完毕,恢复标记,防止重复加载
        mIsFirstLoad = false;
    }

    private void loadData() {
        //这里进行网络请求和数据装载
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mIsFirstLoad = true;
        mIsPrepare = false;
        mIsVisible = false;
    }
}
