package com.example.utiltool2.ui.explosion;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * author:lgh on 2019-09-03 09:12
 * 粒子控制器 （控制动画进度）
 */
public class ExplosionAnimator extends ValueAnimator {

    public static final int defualt_duration = 1500;

    private ParticleFactory mParticleFactory;//粒子工厂
    private Particle[][] mParticle;//粒子
    private View mContainer;//field
    private Paint mPaint;

    public ExplosionAnimator(ParticleFactory mParticleFactory, View mContainer, Bitmap bitmap, Rect bound) {
        this.mParticleFactory = mParticleFactory;
        this.mContainer = mContainer;
        mPaint = new Paint();
        setFloatValues(0, 1);//动画百分比
        setDuration(defualt_duration);
        mParticle = mParticleFactory.generateParticle(bitmap, bound);
    }
    //绘制 (多个动画可同时)
    public void draw(Canvas canvas){
        if (!isStarted()){
            return;//动画未开始
        }
        //所有粒子开始运动
        for (Particle[] particles : mParticle){
            for (Particle particle:particles){
                particle.advance(canvas,mPaint,(float)getAnimatedValue());
            }
        }
        mContainer.invalidate();
    }

    @Override
    public void start() {
        super.start();
        mContainer.invalidate();
    }
}
