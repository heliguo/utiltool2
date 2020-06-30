package com.example.utiltool2.ui.ScrollTable.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;


/**
 * @创建者 lgh
 * @创建时间 2019-08-20 16:52
 * @描述 一个视图容器控件 阻止 拦截 ontouch事件传递给其子控件 *
 */

public class InterceptScrollContainer extends LinearLayout {

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

        return true;
    }


}
