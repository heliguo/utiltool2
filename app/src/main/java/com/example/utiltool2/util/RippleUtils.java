package com.example.utiltool2.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

/**
 * author:lgh on 2019-12-09 15:26
 * 水波纹效果
 */

public class RippleUtils {
    private static RippleDrawable mRipple;
    private static Drawable mTileBackground;

    private static Drawable newTileBackground(Context context) {
        final int[] attrs = new int[]{android.R.attr.selectableItemBackgroundBorderless};
        final TypedArray ta = context.obtainStyledAttributes(attrs);
        final Drawable d = ta.getDrawable(0);
        ta.recycle();
        return d;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void setRipple(RippleDrawable tileBackground, View v) {
        mRipple = tileBackground;
        updateRippleSize(v);
    }

    //以view的中心为圆心，宽的1/4为半径的ripple范围
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void updateRippleSize(View v) {
        // center the touch feedback on the center of the icon, and dial it down a bit
        if (v.getWidth() != 0) {
            final int cx = v.getWidth() / 2;
            final int cy = v.getHeight() / 2;
            final int rad = (int) (v.getWidth() * .25f);
            Log.d("ripple", "updateRippleSize: rad=" + rad);
            mRipple.setHotspotBounds(cx - rad, cy - rad, cx + rad, cy + rad);
        }
    }

    //对外接口
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static RippleDrawable getRippleDrawable(Context context, View view) {
        mTileBackground = newTileBackground(context);
        if (mTileBackground instanceof RippleDrawable) {
            setRipple((RippleDrawable) mTileBackground, view);
        }
        return mRipple;
    }
}
