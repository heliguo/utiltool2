package com.example.utiltool2.ui.explosion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * author:lgh on 2019-09-02 17:07
 * 粒子特效场地 实现特效的地方
 */

public class ExplosionFiled extends View {

    //可以同时执行多个动画
    private ArrayList<ExplosionAnimator> mExplosionAnimators;
    //可以实现不同粒子效果
    private ParticleFactory mParticleFactory;
    //控件点击事件
    private OnClickListener onClickListener;

    public ExplosionFiled(Context context, ParticleFactory particleFactory) {
        super(context);
        mParticleFactory = particleFactory;
        mExplosionAnimators = new ArrayList<>();
        //将动画区域绑定到activity
        attach2Activity();
    }

    private void attach2Activity() {
        ViewGroup decorView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        decorView.addView(this, lp);
    }

    /**
     * @param view 需要实现粒子效果的view
     */
    public void addListener(View view) {
        //判断是布局还是控件，如果是布局则获取其中的控件
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                addListener(viewGroup.getChildAt(i));
            }
        } else {
            view.setClickable(true);//控件的点击事件
            view.setOnClickListener(getOnClickListener());
        }
    }

    private OnClickListener getOnClickListener() {
        if (onClickListener == null) {
            //开启粒子爆炸效果
            //onClickListener = new OnClickListener() {
            //                @Override
            //                public void onClick(View v) {
            //                    //开启粒子爆炸效果
            //                    ExplosionFiled.this.explode(v);
            //                }
            //            };
            onClickListener = ExplosionFiled.this::explode;
        }
        return onClickListener;
    }

    //动画开始入口
    private void explode(final View view) {
        //获取控件位置
        final Rect rect = new Rect();//保存控件的位置信息
        //getGlobalVisibleRect() 是view可见区域相对与屏幕(坐标原点)来说的坐标位置.全局
        //getLocalVisibleRect()是view可见区域想对于自己坐标的位置.本地
        view.getGlobalVisibleRect(rect);
        if (rect.width() == 0 || rect.height() == 0) {
            return;
        }
        //开始动画效果
        //1、抖动缩小
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(150);
        animator.addUpdateListener(animation -> {
            view.setTranslationX((Utils.RANDOM.nextFloat() - 0.5f) * view.getWidth() * 0.05f);
            view.setTranslationY((Utils.RANDOM.nextFloat() - 0.5f) * view.getHeight() * 0.05f);
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束
                //2、粒子效果
                explode2(view, rect);
            }
        });
        // 开启震动动画
        animator.start();

        //3、控件恢复原始大小
    }

    private void explode2(final View view, Rect rect) {
        //获取动画控制器
        final ExplosionAnimator animator = new ExplosionAnimator(mParticleFactory, this, Utils.createBitmapFromView(view),
                rect);
        //添加到动画集合中
        mExplosionAnimators.add(animator);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setClickable(true);
                //恢复
                view.animate().setDuration(150).scaleX(1f).scaleY(1f).alpha(1f).start();
                //移除
                mExplosionAnimators.remove(animator);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setClickable(false);
                //缩小动画 setDuration动画持续时间，scaleX、scaleY尺寸大小（0~1），alpha透明度
                view.animate().setDuration(150).scaleX(0f).scaleX(0f).alpha(0f).start();
            }
        });
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //开启所有动画
        for (ExplosionAnimator explosionAnimator : mExplosionAnimators) {
            explosionAnimator.draw(canvas);
        }
    }
}
