package com.example.utiltool2.signature;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.utiltool2.R;

/**
 * author:lgh on 2019-12-20 13:23
 */
public class PenStyle extends View {

    private Paint mPaint;
    private int mColor;
    private int mSize;
    private int height;
    private int width;
    private BrushPreset mBrushPrest;

    public PenStyle(Context context) {
        this(context, null);
    }

    public PenStyle(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PenStyle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDraw();
    }

    private void initDraw() {
        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);//防抖动
        mPaint.setColor(getColor(R.color.black));
        mPaint.setStrokeWidth(12);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h != oldh || w != oldw) {
            width = w;
            height = h;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(width / 8, height / 2, width * 7 / 8, height / 2, mPaint);

    }

    private int getColor(int color) {
        return getResources().getColor(color);
    }

    public void setmBrushPreset(BrushPreset mBrushPreset) {
        this.mBrushPrest = mBrushPreset;
        mPaint.setStrokeWidth(mBrushPreset.size);
        mPaint.setColor(mBrushPreset.color);
        if (mBrushPreset.blurStyle != null && mBrushPreset.blurRadius > 0) {
            mPaint.setMaskFilter(new BlurMaskFilter(mBrushPreset.blurRadius,
                    mBrushPreset.blurStyle));
        } else {
            mPaint.setMaskFilter(null);
        }
        invalidate();

    }

    public void setmSize(int mSize) {
        this.mSize = mSize;
        invalidate();
    }

    public int getmColor() {
        return mColor;

    }

    public void setmColor(int mColor) {
        this.mColor = mColor;
        invalidate();
    }

    public int getmSize() {
        return mSize;
    }

    public BrushPreset getmBrushPrest() {
        return mBrushPrest;
    }

}
