package com.chiemy.demo.study.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.chiemy.demo.study.R;

/**
 * Created by chiemy on 2017/2/28.
 */
public class MeshView extends View {
    // 横向份数
    private int meshWidth = 100;
    // 纵向份数
    private int meshHeight = 100;
    private Bitmap bitmap;
    // 网格交叉点原始坐标, 偶数位为 x 坐标, 奇数位位 y 轴坐标
    private float [] originalVerts;
    // 网格交叉点变换坐标
    private float [] verts;


    public MeshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg);
        float gridWidth = bitmap.getWidth() / meshWidth;
        float gridHeight = bitmap.getHeight() / meshHeight;
        // 交叉点个数
        int count = (meshWidth + 1) * (meshHeight + 1);

        // 第几个点
        int index = 0;
        originalVerts = new float[count * 2];
        verts = new float[count * 2];
        for (int i = 0; i < meshHeight + 1; i++) {
            float y = i * gridHeight;
            for (int j = 0; j < meshWidth + 1; j++) {
                // 第 index 点的 x 轴坐标
                verts[2 * index] = originalVerts[2 * index] = j * gridWidth;
                // 第 index 点的 y 轴坐标
                originalVerts[2 * index + 1] = y;

                float offset = (float) Math.sin(2 * Math.PI * j / meshWidth) * 50;

                verts[2 * index + 1] = y + offset;

                index++;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(0, 200);
        canvas.drawBitmapMesh(bitmap, meshWidth, meshHeight, verts, 0, null, 0, null);
        canvas.restore();
    }

}
