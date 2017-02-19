package com.chiemy.demo.study.view;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;

/**
 * Created by chiemy on 2017/2/19.
 */
public class RippleDrawable extends Drawable {
    private Paint paint;
    private int alpha = 255;

    private float cx;
    private float cy;
    private float maxRadius;
    private float rippleRadius;

    public RippleDrawable() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    public void setRippleColor(int rippleColor) {
        paint.setColor(rippleColor);
        paint.setAlpha(alpha);
        invalidateSelf();
    }

    private ValueAnimator animator;
    public void onTouch(MotionEvent event) {
        cx = event.getX();
        cy = event.getY();
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(0f, 1f).setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                rippleRadius = maxRadius * value;
                invalidateSelf();
            }
        });
        animator.start();
    }


    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        maxRadius = (float) Math.hypot(bounds.width(), bounds.height());
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(cx, cy, rippleRadius, paint);
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
