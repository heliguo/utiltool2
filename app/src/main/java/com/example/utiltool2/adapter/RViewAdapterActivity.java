package com.example.utiltool2.adapter;

import android.os.Bundle;
import android.widget.TextView;

import com.example.rvlibrary.base.RViewAdapter;
import com.example.rvlibrary.holder.RViewHolder;
import com.example.rvlibrary.model.RViewItem;
import com.example.utiltool2.R;

import java.util.ArrayList;
import java.util.List;


public class RViewAdapterActivity extends BaseAdapterActivity {

    private List<UserInfo> datas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
    }

    protected void initDatas() {
        if (datas.isEmpty()) {
            for (int i = 0; i < 100; i++) {
                datas.add(new UserInfo("account" + i, i + ""));
            }
        }
    }

    @Override
    public void OnRefresh() {

    }

    @Override
    public RViewAdapter createRViewAdapter() {
        return new RViewAdapter<>(datas, new RViewItem<UserInfo>() {
            @Override
            public int getItemLayout() {
                return R.layout.item_list;
            }

            @Override
            public boolean openClick() {
                return true;
            }

            @Override
            public boolean isItemView(UserInfo entity, int position) {
                return true;
            }

            @Override
            public void convert(RViewHolder holder, UserInfo entity, int position) {
                TextView account = holder.getView(R.id.single_account);
                account.setText(entity.getAccount());
                TextView password = holder.getView(R.id.single_password);
                password.setText(entity.getPassword());
            }
        });
    }
}
