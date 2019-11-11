package com.example.utiltool2.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * author:lgh on 2019-11-08 10:04
 */
public class MyLayout extends LinearLayout {

    private ViewTarget<MyLayout, Drawable> viewTarget;

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //this为当前实例
        viewTarget = new ViewTarget<MyLayout, Drawable>(this) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                MyLayout myLayout = getView();
                myLayout.setImageAsBackground(resource);
            }
        };
    }

    public ViewTarget<MyLayout, Drawable> getTarget() {
        return viewTarget;
    }

    public void setImageAsBackground(Drawable resource) {
        setBackground(resource);
    }

}
