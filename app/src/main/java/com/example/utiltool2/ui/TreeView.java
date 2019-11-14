package com.example.utiltool2.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * author:lgh on 2019-11-13 13:28
 */
public class TreeView extends View {

    private LinkedList<Branch> grawBranches;//存储树枝
    private Bitmap bitmap;//保存上次内容
    private Canvas treeCanvas;//保存
    private Paint paint;

    public TreeView(Context context) {
        this(context, null);
    }

    public TreeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        grawBranches = new LinkedList<>();
        grawBranches.add(getBranches());

    }

    private Branch getBranches() {
        int[][] data = new int[][]{{0, -1, 217, 490, 252, 60, 182, 10, 30, 100},
                {1, 0, 222, 310, 137, 227, 22, 210, 13, 100},
                {2, 1, 132, 245, 116, 240, 76, 205, 2, 40},
                {3, 0, 232, 255, 282, 166, 362, 155, 12, 100},
                {4, 3, 260, 210, 330, 219, 343, 236, 3, 80},
                {5, 0, 217, 91, 219, 58, 216, 27, 3, 40},
                {6, 0, 228, 207, 95, 57, 10, 54, 9, 80},
                {7, 6, 109, 96, 65, 63, 53, 15, 2, 40},
                {8, 6, 180, 155, 117, 125, 77, 140, 4, 60},
                {9, 0, 228, 167, 290, 62, 360, 31, 6, 100},
                {10, 9, 272, 103, 328, 87, 330, 81, 2, 80}};

        int n = data.length;
        Branch[] branchs = new Branch[n];
        //循环数据分组
        for (int i = 0; i < n; i++) {
            branchs[i] = new Branch(data[i]);
            //判断父节点
            int parentId = data[i][1];//********
            if (parentId != -1) {
                branchs[parentId].addChildBranch(branchs[i]);//保存相同父节点分支,非主干也有分支
            }
        }
        return branchs[0];//绘制主干
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        treeCanvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBranches();
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    private void drawBranches() {
        if (!grawBranches.isEmpty()) {
            Iterator<Branch> iterator = grawBranches.iterator();
            LinkedList<Branch> temBranches = null;
            while (iterator.hasNext()) {
                Branch branch = iterator.next();
                treeCanvas.save();
                treeCanvas.translate(getWidth() / 2 - 217, getHeight() - 490);
                if (!branch.grow(treeCanvas, paint, 1)) {
                    iterator.remove();
                    //是否有分支
                    if (branch.childBranch != null) {
                        if (temBranches == null) {
                            temBranches = branch.childBranch;
                        } else {
                            temBranches.addAll(branch.childBranch);
                        }
                    }
                }
                treeCanvas.restore();
            }
            if (temBranches != null) {
                grawBranches.addAll(temBranches);
            }
            if (grawBranches != null) {
                invalidate();
            }

        }
    }
}
