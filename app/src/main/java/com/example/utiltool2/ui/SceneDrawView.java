package com.example.utiltool2.ui;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * author:lgh on 2019-11-04 15:00
 * 现场图，按钮
 */
public class SceneDrawView extends View {

    private static final String TAG = "SceneDrawView";

    private Paint paint;//抬杆画笔

    private List<Region> regions;

    private RectF rectF;
    private Rect rect;
    private Region region1;
    private Region region2;
    private Region region3;
    private Region region4;
    private Region region;

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

    private int screenWidth;
    private int screenHeight;

    private Rect mRect;
    private BitmapFactory.Options mOption;
    private int mImageWidth;
    private int mImageHeight;
    private BitmapRegionDecoder mDecoder;
    private float mScaleX;
    private float mScaleY;
    private Bitmap mBitmap;
    private Matrix matrix;

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
        rectF = new RectF();
        rect = new Rect();
        region1 = new Region();
        region2 = new Region();
        region3 = new Region();
        region4 = new Region();
        region = new Region();
        // 1、设置成员变量
        mRect = new Rect();
        //需要内存复用
        mOption = new BitmapFactory.Options();
        matrix = new Matrix();
    }

    //2、设置图片，得到图片信息，加载部分图片
    public void setImage(InputStream is) {
        //获取图片宽和高，不能将整个图片加载
        mOption.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, mOption);
        mImageWidth = mOption.outWidth;
        mImageHeight = mOption.outHeight;

        //开始复用
        mOption.inMutable = true;
        //设置格式GBR565
        mOption.inPreferredConfig = Bitmap.Config.RGB_565;

        mOption.inJustDecodeBounds = false;

        //创建区域解码器
        try {
            mDecoder = BitmapRegionDecoder.newInstance(is, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        requestLayout();
    }

    //3、测量Imageview缩放比例
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
        int mViewWidth = getMeasuredWidth();
        int mViewHeight = getMeasuredHeight();

        //确定图片加载区域
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = mImageWidth;//上下
        mRect.bottom = mImageHeight;//左右
        mScaleX = mViewWidth / (float) mImageWidth;//左右缩放比例
        mScaleY = mViewHeight / (float) mImageHeight;//上下缩放比例
    }

    //组件大小发生变化时的回调
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (oldh != h) {
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            if (windowManager != null) {
                windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            }
            screenWidth = displayMetrics.widthPixels;
            screenHeight = displayMetrics.heightPixels;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mDecoder != null) {
            //复用内存 复用的bitmap必须与即将解码的bitmap尺寸一致
            mOption.inBitmap = mBitmap;
            //指定解码区域
            mBitmap = mDecoder.decodeRegion(mRect, mOption);
            //得到矩阵缩放
            matrix.setScale(mScaleX, mScaleY);
            canvas.drawBitmap(mBitmap, matrix, null);
        }

        //左2 left
        double left2Left = 410.0 / 1080;
        //水平杆长
        double brakeLength = 170.0 / 1080;
        //左1 top
        double left1Top = 310.0 / 1920;
        //左1 left
        double left1Left = 212.0 / 1080;
        //左2 top
        double left2Top = 1077.0 / 1920;
        //右1 top
        double right1Top = 313.0 / 1920;
        //右1 left
        double right1Left = 960.0 / 1080;
        //右2 top
        double right2Top = 1075.0 / 1920;
        //右2 left
        double right2Left = 955.0 / 1080;

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

        path.reset();//很有必要

        //1 rectF
        rectF.set((float) (screenWidth * left1Left), (float) (screenHeight * left1Top) - 190,
                (float) (screenWidth * (brakeLength + left1Left)) + 20, (float) (screenHeight * left1Top) - 80);

        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(btnColor);
        path.addRoundRect(rectF, 8, 8, Path.Direction.CCW);
        canvas.drawPath(path, paint);

        //1 region
        rectF.roundOut(rect);
        region.setEmpty();
        region.set(rect);
        region1.setPath(path, region);
        if (!regions.contains(region1))
            regions.add(region1);

        //2 rectF
        path.reset();
        rectF.setEmpty();
        rectF.set((float) (screenWidth * (left2Left - brakeLength)) - 20, (float) (screenHeight * left2Top) + 50,
                (float) (screenWidth * left2Left), (float) (screenHeight * left2Top) + 160);
        path.addRoundRect(rectF, 8, 8, Path.Direction.CCW);
        canvas.drawPath(path, paint);

        //2 region
        rect.setEmpty();
        rectF.roundOut(rect);
        region.setEmpty();
        region.set(rect);
        region2.setPath(path, region);
        if (!regions.contains(region2))
            regions.add(region2);

        //3 rectF
        path.reset();
        rectF.setEmpty();
        rectF.set((float) (screenWidth * (right1Left - brakeLength)) - 20, (float) (screenHeight * right1Top) - 190,
                (float) (screenWidth * right1Left), (float) (screenHeight * right1Top) - 80);
        path.addRoundRect(rectF, 8, 8, Path.Direction.CCW);
        canvas.drawPath(path, paint);

        //3 region
        rect.setEmpty();
        rectF.roundOut(rect);
        region.setEmpty();
        region.set(rect);
        region3.setPath(path, region);
        if (!regions.contains(region3))
            regions.add(region3);

        //4 rectF
        path.reset();
        rectF.setEmpty();
        rectF.set((float) (screenWidth * (right2Left - brakeLength)) - 20, (float) (screenHeight * right2Top) + 50,
                (float) (screenWidth * right2Left), (float) (screenHeight * right2Top) + 160);
        path.addRoundRect(rectF, 8, 8, Path.Direction.CCW);
        canvas.drawPath(path, paint);

        //4 region
        rect.setEmpty();
        rectF.roundOut(rect);
        region.setEmpty();
        region.set(rect);
        region4.setPath(path, region);
        if (!regions.contains(region4))
            regions.add(region4);

        //text
        paint.reset();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setTextSize(textSize);
        canvas.drawText(text1, (float) (screenWidth * left1Left) + 10 + offset, (float) (screenHeight * left1Top) - 120, paint);
        canvas.drawText(text2, (float) (screenWidth * (left2Left - brakeLength)) - 10 + offset, (float) (screenHeight * left2Top) + 115, paint);
        canvas.drawText(text3, (float) (screenWidth * (right1Left - brakeLength)) - 10 + offset, (float) (screenHeight * right1Top) - 120, paint);
        canvas.drawText(text4, (float) (screenWidth * (right1Left - brakeLength)) - 10 + offset, (float) (screenHeight * right2Top) + 115, paint);

    }

    @SuppressLint("ClickableViewAccessibility")
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

    //清理内存
    public void clear() {
        rect.setEmpty();
        rectF.setEmpty();
        region.setEmpty();
        region1.setEmpty();
        region2.setEmpty();
        region3.setEmpty();
        region4.setEmpty();
        regions.clear();
        paint.reset();
        path.reset();
        mRect.setEmpty();
        matrix.reset();
        mDecoder.recycle();
        mOption = null;
        mBitmap.recycle();
        clearAnimation();
    }

}
