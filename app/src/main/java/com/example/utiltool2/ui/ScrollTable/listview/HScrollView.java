package com.example.utiltool2.ui.ScrollTable.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建者 lgh
 * @创建时间 2019-08-20 15:33
 * @描述 观察者模式自定义 HorizonalScrollView
 */
public class HScrollView extends HorizontalScrollView {

    private ScrollViewObserver mObserver;

    public HScrollView(Context context) {
        this(context, null);
    }

    public HScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mObserver = new ScrollViewObserver();
    }

    /**
     * l:滑动之后的x的坐标。
     * t:滑动之后的y的坐标。
     * oldl:滑动之前的x坐标。
     * oldt:滑动之前的y坐标。
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mObserver != null && (l != oldl || t != oldt)) {
            mObserver.notifyOnScrollChanged(l, t, oldl, oldt);//通知所有观察者
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    /**
     * 外部调用API
     */
    public void addOnScrollChangedListener(OnScrollChangedListener listener) {
        mObserver.addOnScrollChangedListener(listener);
    }

    public void removeOnScrollChangedListener(OnScrollChangedListener listener) {
        mObserver.removeOnScrollChangedListener(listener);
    }

    /**
     * 监听接口
     */
    public interface OnScrollChangedListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }


    /**
     * 观察者模式
     * 用于记录OnScrollChangedListener
     * 同时响应OnScrollChangedListener执行onScrollChanged方法
     */

    public static class ScrollViewObserver {
        List<OnScrollChangedListener> mListeners;

        ScrollViewObserver() {
            mListeners = new ArrayList<>();
        }

        void addOnScrollChangedListener(OnScrollChangedListener listener) {
            mListeners.add(listener);
        }

        void removeOnScrollChangedListener(OnScrollChangedListener listener) {
            mListeners.remove(listener);
        }

        void notifyOnScrollChanged(int l, int t, int oldl, int oldt) {
            if (mListeners == null || mListeners.size() == 0) {
                return;
            }
            for (OnScrollChangedListener mListener : mListeners) {
                mListener.onScrollChanged(l, t, oldl, oldt);
            }
        }

    }
}
