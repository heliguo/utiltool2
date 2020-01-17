package com.example.utiltool2.exam;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.HashMap;
import java.util.Map;

/**
 * author:lgh on 2019-12-11 11:22
 */
public class ExamViewPager extends ViewPager {

    private Map<Integer, View> viewMap;
    private View leftView;
    private View rightView;

    private float lastX;
    private float lastY;

    //变化的梯度
    private float mTrans;
    //缩放的值
    private float mScale;
    //缩放的最小值，从0.5 到 1
    private static final float MIN_SCALE = 0.5f;

    private PagerListener pagerListener;

    public ExamViewPager(@NonNull Context context) {
        this(context, null);
    }

    public ExamViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        viewMap = new HashMap<>();
    }

    public void setPagerListener(PagerListener pagerListener) {
        this.pagerListener = pagerListener;
    }

    private boolean once;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lastX = ev.getX();
                lastY = ev.getY();
                once = true;
                break;
            case MotionEvent.ACTION_MOVE:

                float currentX = ev.getX();
                float currentY = ev.getY();

                float offsetX = currentX - lastX;
                float offsetY = currentY - lastY;

                if (once && offsetX < -25f && Math.abs(offsetX) > Math.abs(offsetY)) {
                    if (pagerListener != null)
                        pagerListener.scroll(offsetX, offsetY);
                    once = false;
                }
                if (once && offsetX > 25f && Math.abs(offsetX) > Math.abs(offsetY)) {
                    if (pagerListener != null)
                        pagerListener.scroll(offsetX, offsetY);
                    once = false;
                }

                lastX = currentX;
                lastY = currentY;
                break;

            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    public void setViewForPosition(View view, int position) {
        viewMap.put(position, view);
    }

    public void removeViewFromPosition(int position) {
        viewMap.remove(position);
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        leftView = viewMap.get(position);
        rightView = viewMap.get(position + 1);
        animView(leftView, rightView, offset, offsetPixels);
        super.onPageScrolled(position, offset, offsetPixels);
    }

    private void animView(View leftView, View rightView, float offset, int offsetPixels) {
        //从0页到1页， offset： 0-1
        //从0 逐渐变到1
        mScale = (1 - MIN_SCALE) * offset + MIN_SCALE;
        mTrans = -getWidth() - getPageMargin() + offsetPixels;
        if (rightView != null) {
            rightView.setScaleX(mScale);
            rightView.setScaleY(mScale);
            rightView.setTranslationX(mTrans);
        }
        if (leftView != null) {
            //让其保持在顶部绘制
            leftView.bringToFront();

        }

    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);

    }
}