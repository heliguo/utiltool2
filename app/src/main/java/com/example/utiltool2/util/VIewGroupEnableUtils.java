package com.example.utiltool2.util;

import android.view.View;
import android.view.ViewGroup;

/**
 * author:lgh on 2020-04-03 17:14
 * 设置viewgroup点击失效
 */
public class VIewGroupEnableUtils {

    public static void setViewEnable(ViewGroup vg, boolean enable) {
        vg.setEnabled(enable);
        for (int i = 0; i < vg.getChildCount(); i++) {
            View view = vg.getChildAt(i);
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                setViewEnable(viewGroup, enable);
            } else {
                view.setEnabled(enable);
            }
        }
    }
}
