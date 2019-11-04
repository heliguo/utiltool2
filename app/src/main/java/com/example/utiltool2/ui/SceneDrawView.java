package com.example.utiltool2.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.utiltool2.R;
import com.example.utiltool2.util.ScreenUtils;

/**
 * author:lgh on 2019-11-04 15:00
 * 现场图，按钮
 */
public class SceneDrawView extends View {

    /**
     * 现场图比例
     */

    //水平杆长
    private static float BRAKE_LENGTH = 170 / 1080;

    private static float LEFT_1_TOP = 360 / 1920;//左1 top

    private static float LEFT_1_LEFT = 200 / 1080;//左1 left

    private static float LEFT_2_TOP = 1220 / 1920;//左2 top

    private static float LEFT_2_LEFT = 390 / 1080;//左2 left

    private static float RIGHT_1_TOP = 360 / 1920;//右1 top

    private static float RIGHT_1_LEFT = 950 / 1080;//右1 left

    private static float RIGHT_2_TOP = 950 / 1080;//右2 top

    private static float RIGHT_2_LEFT = 950 / 1080;//右2 left

    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;


    private Paint textPaint;//文字画笔
    private Paint brakePaint;//抬杆画笔
    private Paint btnPaint;//按钮画笔

    private Bitmap bitmap;


    public SceneDrawView(Context context) {
        this(context, null);
    }

    public SceneDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SceneDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SCREEN_WIDTH = ScreenUtils.getScreenWidth(context);
        SCREEN_HEIGHT = ScreenUtils.getScreenHeight(context);
        initDraw();
    }

    private void initDraw() {
        //将图片转为bitmap
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.monitor);

        //初始化画笔
        textPaint = new Paint();
        //设置是否抗锯齿;设置抗锯齿会使图像边缘更清晰一些，锯齿痕迹不会那么明显。
        textPaint.setAntiAlias(true);
        //设置填充样式
        //Paint.Style 类型：
        //Paint.Style.FILL_AND_STROKE 填充且描边
        //Paint.Style.STROKE 描边
        //Paint.Style.FILL 填充
        //textPaint.setStyle(Paint.Style.STROKE);
        //设置画笔颜色
        textPaint.setColor(Color.RED);
        //设置画笔宽度
        textPaint.setStrokeWidth(8);
        //setShadowLayer(float radius, float dx, float dy, int shadowColor) 设置阴影
        //radius ： 表示阴影的倾斜度
        //dx ： 水平位移
        //dy : 垂直位移
        //shadowColor : 阴影颜色
        //这个方法不支持硬件加速，所以我们要测试时必须先关闭硬件加速。
        //加上setLayerType(LAYER_TYPE_SOFTWARE, null); 并且确保你的最小api8以上。
        //textPaint.setShadowLayer(10,15,15,Color.RED);

        btnPaint = new Paint();//按钮
        btnPaint.setAntiAlias(true);
        btnPaint.setColor(Color.RED);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //背景图
//        canvas.drawBitmap(bitmap, 0, 0, null);//将原图放置在画布上

        //左1杆
        canvas.drawLine(SCREEN_WIDTH * LEFT_1_LEFT, SCREEN_HEIGHT * LEFT_1_TOP,
                SCREEN_WIDTH * (BRAKE_LENGTH + LEFT_1_LEFT), SCREEN_HEIGHT * LEFT_1_TOP, btnPaint);

        //左2杆
        canvas.drawLine(SCREEN_WIDTH * LEFT_2_LEFT, SCREEN_HEIGHT * LEFT_2_TOP,
                SCREEN_WIDTH * (LEFT_2_LEFT - BRAKE_LENGTH), SCREEN_HEIGHT * LEFT_2_TOP, btnPaint);

        //右1杆
        canvas.drawLine(SCREEN_WIDTH * RIGHT_1_LEFT, SCREEN_HEIGHT * RIGHT_1_TOP,
                SCREEN_WIDTH * (RIGHT_1_LEFT - BRAKE_LENGTH), SCREEN_HEIGHT * RIGHT_1_TOP, btnPaint);

        //右2杆
        canvas.drawLine(SCREEN_WIDTH * RIGHT_2_LEFT, SCREEN_HEIGHT * RIGHT_2_TOP,
                SCREEN_WIDTH * (RIGHT_2_LEFT - BRAKE_LENGTH), SCREEN_HEIGHT * RIGHT_2_TOP, btnPaint);
    }
}
