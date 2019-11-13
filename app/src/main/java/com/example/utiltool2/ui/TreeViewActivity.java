package com.example.utiltool2.ui;

import android.os.Bundle;

import com.example.utiltool2.BaseActivity;

/**
 * author:lgh on 2019-11-13 17:01
 */
public class TreeViewActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TreeView(this));
    }
}
