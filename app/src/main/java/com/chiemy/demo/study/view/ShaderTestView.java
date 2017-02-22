package com.chiemy.demo.study.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.chiemy.demo.study.R;

/**
 * Created by chiemy on 2017/2/22.
 */
public class ShaderTestView extends View {
    private Paint paint;
    private Bitmap bitmap;
    private float radius;

    public ShaderTestView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg);
        // Shader.TileMode.CLAMP 不足的地方从最后一像素进行拉伸
        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));

        radius = Math.min(bitmap.getWidth(), bitmap.getHeight()) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, radius, paint);
    }
}
