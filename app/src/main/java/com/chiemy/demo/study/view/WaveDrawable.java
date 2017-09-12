package com.chiemy.demo.study.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created: chiemy
 * Date: 9/12/17
 * Description:
 */

public class WaveDrawable extends Drawable {
    private Bitmap bitmap;
    private int alpha;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int waveLength;
    private int waveHeight;

    public WaveDrawable() {

    }

    private void initBitmap() {
        Rect rect = getBounds();
        int width = rect.width();
        int height = rect.height();
        if (width <= 0
                || height <= 0
                || waveLength <= 0
                || waveHeight <= 0) {
            return;
        }
        final int waveCount = (int) Math.ceil((waveLength + width) / waveLength);
        int quarterOfWaveLength = waveLength / 4;
        int amplitude = waveHeight / 2;

        bitmap = Bitmap.createBitmap(waveCount * waveLength, waveHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Path path = new Path();
        path.moveTo(0, amplitude);

        int x = 0;
        int y = -amplitude;
        // 两个贝塞尔曲线组成一个波形
        for (int i = 0; i < waveCount * 2; i++) {
            x += quarterOfWaveLength;
            path.quadTo(x, y, x + quarterOfWaveLength, amplitude);
            x += quarterOfWaveLength;
            y = waveHeight - y;
        }
        path.lineTo(bitmap.getWidth(), waveHeight);
        path.lineTo(0, waveHeight);
        path.close();

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawPath(path, paint);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (bitmap != null) {
            canvas.save();
            canvas.translate(0, bitmap.getHeight());
            canvas.drawBitmap(bitmap, 0, 0, null);
            canvas.restore();
        }
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, bottom);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        waveLength = bounds.width();
        waveHeight = (int) (bounds.height() * 0.2f);
        Log.d("chiemy", "onBoundsChange: " + waveHeight);
        initBitmap();
    }

    @Override
    public void setAlpha(int alpha) {
        this.alpha = alpha;
        paint.setAlpha(this.alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if (paint.getColorFilter() != colorFilter) {
            paint.setColorFilter(colorFilter);
        }
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        if (alpha == 0) {
            return PixelFormat.TRANSPARENT;
        } else if (alpha == 255) {
            return PixelFormat.OPAQUE;
        }
        return PixelFormat.TRANSLUCENT;
    }
}
