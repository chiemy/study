package com.chiemy.demo.study.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by chiemy on 2017/2/19.
 */
public class RippleView extends View {
    private RippleDrawable rippleDrawable;

    public RippleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        rippleDrawable = new RippleDrawable();
        rippleDrawable.setRippleColor(Color.RED);
        rippleDrawable.setAlpha(100);
        // rippleDrawable 调用 invalidateSelf 时会触发 CallBack 的 invalidateDrawable
        // View 实现了 Drawable.Callback 接口, 因此会触发 invalidateDrawable > verifyDrawable
        rippleDrawable.setCallback(this);
    }

    @Override
    public void invalidateDrawable(Drawable drawable) {
        super.invalidateDrawable(drawable);
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == rippleDrawable || super.verifyDrawable(who);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rippleDrawable.setBounds(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rippleDrawable.draw(canvas);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                rippleDrawable.onTouch(event);
                break;
        }
        return super.onTouchEvent(event);
    }
}
