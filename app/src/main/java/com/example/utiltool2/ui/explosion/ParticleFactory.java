package com.example.utiltool2.ui.explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * author:lgh on 2019-09-03 09:09
 *
 *  粒子管理类(粒子工厂) 生成粒子
 */
public abstract class ParticleFactory {

    public abstract Particle[][] generateParticle(Bitmap bitmap, Rect bound);

}
