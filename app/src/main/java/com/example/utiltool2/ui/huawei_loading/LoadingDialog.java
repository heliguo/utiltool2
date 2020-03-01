package com.example.utiltool2.ui.huawei_loading;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.utiltool2.R;

/**
 * author:lgh on 2020-01-21 16:09
 */
public class LoadingDialog extends Dialog {


    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    public void setBackgroundColor(@DrawableRes int resId) {
        if (getWindow() != null)
            getWindow().setBackgroundDrawableResource(resId);
    }
}
