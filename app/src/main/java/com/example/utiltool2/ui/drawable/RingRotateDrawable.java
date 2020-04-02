package com.example.utiltool2.ui.drawable;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * author:lgh on 2020-04-02 15:19
 */
public class RingRotateDrawable extends Drawable {
    private Paint mArcPaint;
    private int mRotate;

    public static final String KEY_ROTATE = "ROTATE";

    private ValueAnimator mValueAnimator;

    public RingRotateDrawable(int color) {
        super();
        mArcPaint = new Paint();
        mArcPaint.setColor(color);
        mArcPaint.setStrokeWidth(10);
        mArcPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.rotate(mRotate,getBounds().left+getBounds().width()/2,getBounds().top+getBounds().height()/2);
        RectF rectF = new RectF(getBounds().left+10,getBounds().top+10,getBounds().right-10,getBounds().bottom-10);
        canvas.drawArc(rectF, 0, 330, false, mArcPaint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public void start() {
        PropertyValuesHolder mPropertyRotate = PropertyValuesHolder.ofInt(KEY_ROTATE, 0, 360);
        mValueAnimator = new ValueAnimator();

        mValueAnimator.setValues(mPropertyRotate);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setRepeatCount(1000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRotate = (int) animation.getAnimatedValue(KEY_ROTATE);
                invalidateSelf();
            }
        });
        mValueAnimator.start();
    }
}

