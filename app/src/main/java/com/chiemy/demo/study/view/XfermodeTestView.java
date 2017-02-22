package com.chiemy.demo.study.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.chiemy.demo.study.R;

/**
 * Created by chiemy on 2017/2/22.
 */
public class XfermodeTestView extends View {
    private Paint paint;
    private Bitmap src;
    private Bitmap dst;

    public XfermodeTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 需要关闭硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);

        src = BitmapFactory.decodeResource(getResources(), R.mipmap.timg);

        dst = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(dst);
        canvas.drawRoundRect(new RectF(0, 0, src.getWidth(), src.getHeight()), 30, 30, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(src, 0, 0, paint);
        paint.setXfermode(null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(dst, 100, 100, paint);
    }
}
