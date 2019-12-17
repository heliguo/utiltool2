package com.example.utiltool2.exam;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * author:lgh on 2019-12-11 11:22
 */
public class ExamViewPager extends ViewPager {

    private float lastX;
    private float lastY;

    private PagerListener pagerListener;

    public ExamViewPager(@NonNull Context context) {
        this(context, null);
    }

    public ExamViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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

                if (once && offsetX < -25f) {
                    pagerListener.scroll(offsetX, offsetY);
                    once = false;
                }
                if (once && offsetX > 25f) {
                    pagerListener.scroll(offsetX, offsetY);
                    once = false;
                }

                lastX = currentX;
                lastY = currentY;
                break;

            case MotionEvent.ACTION_UP:
                break;
        }

        return false;
    }


}
