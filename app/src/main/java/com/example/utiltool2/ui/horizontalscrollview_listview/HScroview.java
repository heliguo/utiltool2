package com.example.utiltool2.ui.horizontalscrollview_listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * author:lgh on 2019-10-22 15:05
 */
public class HScroview extends HorizontalScrollView {

    public HScroview(Context context) {
        this(context,null);
    }

    public HScroview(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HScroview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
