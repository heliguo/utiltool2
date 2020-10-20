package com.example.utiltool2.ui.explosion;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * author:lgh on 2019-09-03 10:24
 * 将view分解为最小粒子
 */
public class FallingParticleFactory extends ParticleFactory {

    public static final int PART_WH = 10;//小球默认宽高

    @Override
    public Particle[][] generateParticle(Bitmap bitmap, Rect bound) {

        int w = bound.width();
        int h = bound.height();

        int partW_count = w / PART_WH;//横向个数（列）
        int partH_count = h / PART_WH;//纵向个数（行）

        partW_count = partW_count > 0 ? partW_count : 1;
        partH_count = partH_count > 0 ? partH_count : 1;

        int bitmap_part_w = bitmap.getWidth() / partW_count;
        int bitmao_part_h = bitmap.getHeight() / partH_count;

        Particle[][] particles = new Particle[partH_count][partW_count];

        for (int row = 0; row < partH_count; row++) {
            for (int column = 0; column < partW_count; column++) {
                //获取当前粒子颜色
                int color = bitmap.getPixel(column * bitmap_part_w, row * bitmao_part_h);
                float x = bound.left + PART_WH * column;
                float y = bound.top + PART_WH * row;
                //关联粒子对象
                particles[row][column] = new FallingParticle(x, y, color, bound);
            }
        }
        return particles;
    }
}
