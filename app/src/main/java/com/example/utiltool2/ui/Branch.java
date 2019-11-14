package com.example.utiltool2.ui;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.LinkedList;

/**
 * author:lgh on 2019-11-13 13:48
 */
public class Branch {

    //形状
    private PointF[] pf = new PointF[3];

    //颜色
    private static int branchColor = 0xffff0000;

    //粗细
    private float radius;//30

    //长度
    private int maxLength;//100
    private int currentLength;
    private float part;

    private float grawX, grawY;

    //分支
    LinkedList<Branch> childBranch;

    public Branch(int data[]) {
        //{0,-1,217,490,252,60,182,10,30,100}
        //0 子节点ID，-1 父节点ID，二阶贝塞尔曲线坐标（217,490）、（252,60）、（82,10），粗细 30，长度 100

        pf[0] = new PointF(data[2], data[3]);
        pf[1] = new PointF(data[4], data[5]);
        pf[2] = new PointF(data[6], data[7]);
        radius = data[8];
        maxLength = data[9];
        part = 1f / maxLength;//分成100份

    }

    //外部方法
    public void addChildBranch(Branch branch) {
        if (childBranch == null) {
            childBranch = new LinkedList<>();
        }
        childBranch.add(branch);
    }

    //实现单个树枝动画
    public boolean grow(Canvas canvas, Paint paint, int scaleFactor) {
        if (currentLength < maxLength) {
            //计算
            bezier(part * currentLength);
            //绘制
            draw(canvas, paint, scaleFactor);
            currentLength++;
            radius *= 0.97f;
            return true;
        } else {
            return false;
        }

    }

    //计算
    private void bezier(float t) {

//        Path path;
//        path.quadTo();//点到点
//        path.cubicTo();//三点线
//        PathMeasure pathMeasure = new PathMeasure();
//        pathMeasure.getPosTan();

        float c0 = (float) Math.pow((1 - t), 2);
        float c1 = 2 * t * (1 - t);
        float c2 = (float) Math.pow(t, 2);

        grawX = c0 * pf[0].x + c1 * pf[1].x + c2 * pf[2].x;
        grawY = c0 * pf[0].y + c1 * pf[1].y + c2 * pf[2].y;

    }

    //绘制
    private void draw(Canvas canvas, Paint paint, int scaleFactor) {
        paint.setColor(branchColor);
        canvas.save();//保存上一次绘制内容
        canvas.scale(scaleFactor, scaleFactor);
        canvas.drawCircle(grawX, grawY, radius, paint);
        canvas.restore();//恢复上一次绘制内容
    }


}
