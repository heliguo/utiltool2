package com.example.utiltool2.ui.explosion;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import androidx.annotation.ColorInt;

/**
 * author:lgh on 2019-09-03 10:22
 * 绘制下落的粒子
 */
public class FallingParticle extends Particle {

    float radius = FallingParticleFactory.PART_WH;
    float alpha = 1.0f;
    Rect mBound;//控件区(获取改变的x y)

    public FallingParticle(float cx, float cy, @ColorInt int color, Rect bound) {
        super(cx, cy, color);
        mBound = bound;
    }

    /**
     * @param factor 动画百分比
     */
    @Override
    protected void calculate(float factor) {
        //位置的改变
        cx = cx + factor * Utils.RANDOM.nextInt(mBound.width()) * ((Utils.RANDOM.nextFloat() - 0.5f));
        cy = cy + factor * Utils.RANDOM.nextInt(mBound.height() / 2);
        radius = radius - factor * Utils.RANDOM.nextInt(2);
        alpha = (1f - factor) * (1 + Utils.RANDOM.nextFloat());
    }

    @Override
    protected void draw(Canvas canvas, Paint paint) {
        paint.setColor(color);
        paint.setAlpha((int) (Color.alpha(color) * alpha));
        canvas.drawCircle(cx, cy, radius, paint);

    }
}
