package com.chiemy.demo.study.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.chiemy.demo.study.R;

/**
 * Created by chiemy on 2017/2/22.
 */
public class ReflectionTestView extends View {
    private Paint paint;
    private Bitmap bitmap;
    private Bitmap out;
    private Bitmap reflectBitmap;
    private int reflectHeight;

    public ReflectionTestView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setAntiAlias(true);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg);

        Matrix matrix = new Matrix();
        matrix.setScale(1f, -1f);
        reflectBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        reflectHeight = reflectBitmap.getHeight() / 2;

        out = Bitmap.createBitmap(reflectBitmap.getWidth(), reflectHeight, Bitmap.Config.ARGB_8888);
        // dst 为先画上去的内容
        // src 为后画上去的内容
        Canvas canvas = new Canvas(out);
        canvas.drawBitmap(reflectBitmap, 0, 0, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        paint.setShader(new LinearGradient(0, 0, 0, reflectHeight, 0xff000000, 0x00000000, Shader.TileMode.CLAMP));
        canvas.drawRect(0, 0, reflectBitmap.getWidth(), reflectHeight, paint);

        // 或者, 效果相同
        // paint.setShader(new LinearGradient(0, 0, 0, reflectHeight, 0xff000000, 0x00000000, Shader.TileMode.CLAMP));
        // canvas.drawRect(0, 0, reflectBitmap.getWidth(), reflectHeight, paint);
        // paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // canvas.drawBitmap(reflectBitmap, 0, 0, paint);

        paint.setXfermode(null);
        paint.setAlpha(100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawBitmap(out, 0, bitmap.getHeight(), paint);
    }
}
