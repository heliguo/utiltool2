package com.example.utiltool2.ui.ScrollTable;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;


/**
 * @创建者 lgh
 * @创建时间 2019-08-20 16:52
 * @描述 一个视图容器控件 阻止 拦截 ontouch事件传递给其子控件 *
 */

public class InterceptScrollContainer extends LinearLayout {

    public static final String TAG = "InterceptScrollContainer";

    public InterceptScrollContainer(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public InterceptScrollContainer(Context context) {
        super(context);
    }

    //onInterceptTouchEvent是用于拦截手势事件的
    //在触发onTouchEvent()之前对相每个手势事件都会先调用onInterceptTouchEvent

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "ScrollContainer onInterceptTouchEvent");
//        return (ev.getRawY() > 150.0) ? true : false;//返回ture，以后每次事件分发下来，将不会传递给子view了，将由父view处理
        return true;
    }


}
