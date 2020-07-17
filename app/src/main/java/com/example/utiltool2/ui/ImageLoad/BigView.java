package com.example.utiltool2.ui.ImageLoad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

/**
 * author:lgh on 2019-09-17 14:46
 */
public class BigView extends View implements GestureDetector.OnGestureListener, View.OnTouchListener {

    private final Rect mRect;
    private final BitmapFactory.Options mOption;
    private final Scroller mScroller;
    private int mImageWidth;
    private int mImageHeight;
    private BitmapRegionDecoder mDecoder;
    private int mViewHeight;
    private int mViewWidth;
    private float mScale;
    private Bitmap mBitmap;
    private GestureDetector mGestureDetector;

    public BigView(Context context) {
        this(context, null);
    }

    public BigView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 1、设置成员变量
        mRect = new Rect();
        //需要内存复用
        mOption = new BitmapFactory.Options();
        // 手势识别
        mGestureDetector = new GestureDetector(context, this);
        //滚动类
        mScroller = new Scroller(context);
        setOnTouchListener(this);
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
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();

        //确定图片加载区域
        mRect.left = 0;
        mRect.top = 0;
        mRect.right = mImageWidth;//上下
//        mRect.bottom=mImageHeight;//左右
        mScale = mViewWidth / (float) mImageWidth;//缩放比例
//        mScale = mImageHeight/(float)mViewHeight;
        mRect.bottom = (int) (mViewHeight / mScale);
    }

    //4、画出具体内容
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDecoder == null) {
            return;
        }
        //复用内存 复用的bitmap必须与即将解码的bitmap尺寸一致
        mOption.inBitmap = mBitmap;
        //指定解码区域
        mBitmap = mDecoder.decodeRegion(mRect, mOption);
        //得到矩阵缩放
        Matrix matrix = new Matrix();
        matrix.setScale(mScale, mScale);
        canvas.drawBitmap(mBitmap, matrix, null);
    }

    //5、点击事件
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    //6、手按下去
    @Override
    public boolean onDown(MotionEvent e) {
        //如果移动没有停止，强行停止
        if (!mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }
        //继续接收后续事件
        return true;
    }

    /**
     * 处理滑动事件
     *
     * @param e1        开始事件，手指按下获取坐标
     * @param e2        获取当前事件坐标
     * @param distanceX X轴移动距离
     * @param distanceY Y轴移动距离
     * @return 事件传递
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //上下滑动，mRect改变区域
        mRect.offset(0, (int) distanceY);
        //处理移动到达顶部和底部事件
        if (mRect.bottom > mImageHeight) {
            mRect.bottom = mImageHeight;
            mRect.top = mImageHeight - (int) (mViewHeight / mScale);
        }
        if (mRect.top < 0) {
            mRect.top = 0;
            mRect.bottom = (int) (mViewHeight / mScale);
        }
        invalidate();//当前view失效，重新绘制
        return false;
    }

    //8、处理惯性问题
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        mScroller.fling(0, mRect.top, 0, (int) -velocityY, 0, 0, 0,
                mImageHeight - (int) (mViewHeight / mScale));
        return false;
    }

    //9、处理计算结果
    @Override
    public void computeScroll() {
        if (mScroller.isFinished()) {
            return;
        }
        //返回true，表面滑动未结束
        if (mScroller.computeScrollOffset()) {
            mRect.top = mScroller.getCurrY();
            mRect.bottom = mRect.top + (int) (mViewHeight / mScale);
            invalidate();
        }
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }


}
