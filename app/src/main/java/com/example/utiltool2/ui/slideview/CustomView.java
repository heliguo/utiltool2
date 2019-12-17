package com.example.utiltool2.ui.slideview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class CustomView extends View {
    private int lastX;
    private int lastY;
    private Scroller mScroller;
    private OnClickListener onClickListener;

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public CustomView(Context context) {
        super(context);
    }


    public boolean onTouchEvent(MotionEvent event) {
        //获取到手指处的横坐标和纵坐标
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lastX = x;
                lastY = y;
                Log.e("````````", "down ");
                break;

            case MotionEvent.ACTION_MOVE:

                int offsetX = x - lastX;
                int offsetY = y - lastY;

                if (offsetX != 0 || offsetY != 0) {
                    //方法1：使用MarginLayoutParams，与点击事件不冲突但不如方法3
//                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                    layoutParams.leftMargin = getLeft() + offsetX;
//                    layoutParams.topMargin = getTop() + offsetY;
//                    setLayoutParams(layoutParams);

                    //方法2：使用layout方法来重新放置它的位置，与点击事件不冲突效果好
//                    layout(getLeft() + offsetX, getTop() + offsetY,
//                            getRight() + offsetX, getBottom() + offsetY);

                    //方法3：使用LayoutParams，与点击事件不冲突效果,好，同方法2
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
                    layoutParams.leftMargin = getLeft() + offsetX;
                    layoutParams.topMargin = getTop() + offsetY;
                    setLayoutParams(layoutParams);

                    //方法4：使用scrollBy，添加点击事件后移动完毕后会执行点击事件
//                    ((View)getParent()).scrollBy(-offsetX,-offsetY);

                    //方法5：使用offset，添加点击事件后移动完毕后会执行点击事件
//                    offsetLeftAndRight(offsetX);//对left和right进行偏移
//                    offsetTopAndBottom(offsetY);//对top和bottom进行偏移

                }
                break;
            case MotionEvent.ACTION_UP:

                //计算移动的距离
                int offsetX1 = x - lastX;
                int offsetY1 = y - lastY;

                if (offsetX1 == 0 && offsetY1 == 0) {
                    onClickListener.onClick(this);
                }
                break;
        }

        return true;//消耗触摸事件
    }

    public void smoothScrollTo(int destX, int destY,int time) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int delta = destX - scrollX;
        int deltaY = destY - scrollY;
        //1000秒内滑向destX
        mScroller.startScroll(scrollX, scrollX, delta, deltaY, time);//与coputeScroll同时使用
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //通过不断的重绘不断的调用computeScroll方法
            invalidate();
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        onClickListener = l;
    }
}