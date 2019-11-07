package com.example.utiltool2.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * author:lgh on 2019-11-04 15:00
 * 现场图，按钮
 */
public class SceneDrawView extends View {

    /**
     * 现场图比例
     */

    //水平杆长
    private double brakeLength = 170.0 / 1080;

    private double left1Top = 310.0 / 1920;//左1 top

    private double left1Left = 212.0 / 1080;//左1 left

    private double left2Top = 1077.0 / 1920;//左2 top

    private double left2Left = 410.0 / 1080;//左2 left

    private double right1Top = 313.0 / 1920;//右1 top

    private double right1Left = 960.0 / 1080;//右1 left

    private double right2Top = 1075.0 / 1920;//右2 top

    private double right2Left = 955.0 / 1080;//右2 left

    private Paint paint;//抬杆画笔
//    private Paint btnPaint;//按钮画笔
//    private Paint textPaint;//文字画笔

    private List<Region> regions;

    private Path path;

    private float scale1Y = 1;
    private float scale1X = 1;
    private float scale2Y = 1;
    private float scale2X = 1;
    private float scale3Y = 1;
    private float scale3X = 1;
    private float scale4Y = 1;
    private float scale4X = 1;

    private int textSize = 36;//字体大小
    private int offset = 0;//text 左边距

    private int btnColor = 0xFFD3D3D3;//按钮颜色

    private String text1 = "1号摄像头";
    private String text2 = "2号摄像头";
    private String text3 = "3号摄像头";
    private String text4 = "4号摄像头";

    private boolean showTG;//是否显示抬杆

    public SceneDrawView(Context context) {
        this(context, null);
    }

    public SceneDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SceneDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDraw();
    }

    private void initDraw() {
        path = new Path();
        paint = new Paint();
        regions = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        Log.d("======>", "onDraw: " + screenWidth + "  " + screenHeight);

        if (showTG) {
            //左1杆
            paint.reset();
            paint.setAntiAlias(true);
            paint.setColor(0xff8B4513);
            paint.setStrokeWidth(10);
            canvas.drawLine((float) (screenWidth * left1Left), (float) (screenHeight * left1Top),
                    (float) (screenWidth * (brakeLength + left1Left * scale1X)), (float) (screenHeight * left1Top * scale1Y), paint);

            //左2杆
            canvas.drawLine((float) (screenWidth * left2Left), (float) (screenHeight * left2Top),
                    (float) (screenWidth * (left2Left - brakeLength * scale2X)), (float) (screenHeight * left2Top * scale2Y), paint);

            //右1杆
            canvas.drawLine((float) (screenWidth * right1Left), (float) (screenHeight * right1Top),
                    (float) (screenWidth * (right1Left - brakeLength * scale3X)), (float) (screenHeight * right1Top * scale3Y), paint);

            //右2杆
            canvas.drawLine((float) (screenWidth * right2Left), (float) (screenHeight * right2Top),
                    (float) (screenWidth * (right1Left - brakeLength * scale4X)), (float) (screenHeight * right2Top * scale4Y), paint);
        }

        RectF rectF = new RectF((float) (screenWidth * left1Left), (float) (screenHeight * left1Top) - 190,
                (float) (screenWidth * (brakeLength + left1Left)) + 20, (float) (screenHeight * left1Top) - 80);
        Rect rect = new Rect();

        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(btnColor);
        path.addRoundRect(rectF, 8, 8, Path.Direction.CCW);
        canvas.drawPath(path, paint);

        rectF.roundOut(rect);
        Region region1 = new Region();
        region1.setPath(path, new Region(rect));
        if (!regions.contains(region1))
            regions.add(region1);
        path.reset();
        rectF.setEmpty();
        rectF = new RectF((float) (screenWidth * (left2Left - brakeLength)) - 20, (float) (screenHeight * left2Top) + 50,
                (float) (screenWidth * left2Left), (float) (screenHeight * left2Top) + 160);
        rect.setEmpty();
        rectF.roundOut(rect);
        path.addRoundRect(rectF, 8, 8, Path.Direction.CCW);
        canvas.drawPath(path, paint);
        Region region2 = new Region();
        region2.setPath(path, new Region(rect));
        if (!regions.contains(region2))
            regions.add(region2);

        path.reset();
        rectF.setEmpty();
        rectF = new RectF((float) (screenWidth * (right1Left - brakeLength)) - 20, (float) (screenHeight * right1Top) - 190,
                (float) (screenWidth * right1Left), (float) (screenHeight * right1Top) - 80);
        rect.setEmpty();
        rectF.roundOut(rect);
        path.addRoundRect(rectF, 8, 8, Path.Direction.CCW);
        canvas.drawPath(path, paint);
        Region region3 = new Region();
        region3.setPath(path, new Region(rect));
        if (!regions.contains(region3))
            regions.add(region3);

        path.reset();
        rectF.setEmpty();
        rectF = new RectF((float) (screenWidth * (right1Left - brakeLength)) - 20, (float) (screenHeight * right2Top) + 50,
                (float) (screenWidth * right1Left), (float) (screenHeight * right2Top) + 160);
        rect.setEmpty();
        rectF.roundOut(rect);
        path.addRoundRect(rectF, 8, 8, Path.Direction.CCW);
        canvas.drawPath(path, paint);
        Region region4 = new Region();
        region4.setPath(path, new Region(rect));
        if (!regions.contains(region4))
            regions.add(region4);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(textSize);
        canvas.drawText(text1, (float) (screenWidth * left1Left) + 10 + offset, (float) (screenHeight * left1Top) - 120, paint);
        canvas.drawText(text2, (float) (screenWidth * (left2Left - brakeLength)) - 10 + offset, (float) (screenHeight * left2Top) + 115, paint);
        canvas.drawText(text3, (float) (screenWidth * (right1Left - brakeLength)) - 10 + offset, (float) (screenHeight * right1Top) - 120, paint);
        canvas.drawText(text4, (float) (screenWidth * (right1Left - brakeLength)) - 10 + offset, (float) (screenHeight * right2Top) + 115, paint);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action != MotionEvent.ACTION_DOWN && action != MotionEvent.ACTION_UP) {
            return super.onTouchEvent(event);
        }
        int x = (int) event.getX();
        int y = (int) event.getY();

        for (int i = 0; i < regions.size(); i++) {
            if (regions.get(i).contains(x, y)) {
                this.setTag(this.getId(), i);
                return super.onTouchEvent(event);
            }
        }
        return false;
    }

    public void setText1(String text1) {
        this.text1 = text1;
        invalidate();
    }

    public void setText2(String text2) {
        this.text2 = text2;
        invalidate();
    }

    public void setText3(String text3) {
        this.text3 = text3;
        invalidate();
    }

    public void setText4(String text4) {
        this.text4 = text4;
        invalidate();
    }

    public void setOffset(int offset) {
        this.offset = offset;
        invalidate();
    }

    public void setBtnColor(int btnColor) {
        this.btnColor = btnColor;
        invalidate();
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setShowTG(boolean showTG) {
        this.showTG = showTG;
    }

    public void startAnimator(final int num) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1.0f);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                switch (num) {
                    case 1:
                        scale1Y = (float) (1 - (float) animation.getAnimatedValue() * 0.3);
                        scale1X = (float) (1 - (float) animation.getAnimatedValue() * 0.9);
                        break;

                    case 2:
                        scale2Y = (float) (1 + (float) animation.getAnimatedValue() * 0.01);
                        scale2X = (float) (1 - (float) animation.getAnimatedValue() * 0.9);
                        break;

                    case 3:
                        scale3Y = (float) (1 - (float) animation.getAnimatedValue() * 0.27);
                        scale3X = (float) (1 - (float) animation.getAnimatedValue() * 1.4);
                        break;

                    case 4:
                        scale4Y = (float) (1 + (float) animation.getAnimatedValue() * 0.01);
                        scale4X = (float) (1 - (float) animation.getAnimatedValue() * 1.25);
                        break;
                }
                invalidate();
            }
        });
        animator.start();

    }

    public void reset(final int num) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1.0f);
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                switch (num) {
                    case 1:
                        scale1Y = (float) (0.7 + (float) animation.getAnimatedValue() * 0.3);
                        scale1X = (float) (0.1 + (float) animation.getAnimatedValue() * 0.9);
                        break;

                    case 2:
                        scale2Y = (float) (1.01 - (float) animation.getAnimatedValue() * 0.01);
                        scale2X = (float) (0.1 + (float) animation.getAnimatedValue() * 0.9);
                        break;

                    case 3:
                        scale3Y = (float) (0.73 + (float) animation.getAnimatedValue() * 0.27);
                        scale3X = (float) (-0.4 + (float) animation.getAnimatedValue() * 1.4);
                        break;

                    case 4:
                        scale4Y = (float) (1.01 - (float) animation.getAnimatedValue() * 0.01);
                        scale4X = (float) (-0.25 + (float) animation.getAnimatedValue() * 1.25);
                        break;
                }
                invalidate();
            }
        });
        animator.start();
    }

}
