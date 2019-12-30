package com.example.utiltool2.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * 自适应的ImageView
 */
public class SelfImageView extends AppCompatImageView {

    private BitmapFactory.Options options;

    public SelfImageView(Context context) {
        this(context, null);
    }

    public SelfImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelfImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        options = new BitmapFactory.Options();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("`````", "onMeasure: " + widthMeasureSpec + " gao  " + heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("`````", "onSizeChanged: " + w + " gao  " + h);

    }
}
