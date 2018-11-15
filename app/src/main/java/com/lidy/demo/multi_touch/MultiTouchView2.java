package com.lidy.demo.multi_touch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import com.lidy.demo.Utils;

/**
 * 协作型的多点触控
 *
 * @author lideyou
 */
public class MultiTouchView2 extends View {

    private static final float IMAGE_WIDTH = Utils.INSTANCE.dp2px(160f);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Bitmap bitmap;

    private float downX, downY;
    private float offsetX, offsetY;
    private float originalOffsetX, originalOffsetY;

    public MultiTouchView2(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);

        bitmap = Utils.INSTANCE.getAvatar(getResources(), (int) IMAGE_WIDTH);
    }

    public MultiTouchView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float sumX = 0;
        float sumY = 0;
        int pointerCount = event.getPointerCount();
        boolean isPointerUp = event.getActionMasked() == MotionEvent.ACTION_POINTER_UP;

        for (int i = 0; i < pointerCount; i++) {
            if (!(isPointerUp && i == event.getActionIndex())) {
                sumX += event.getX(i);
                sumY += event.getY(i);
            }
        }

        //判断某个手指是否取消按键
        if (isPointerUp) {
            pointerCount -= 1;
        }

        float focusX = sumX / pointerCount;
        float focusY = sumY / pointerCount;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:

                downX = focusX;
                downY = focusY;
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;

                break;
            case MotionEvent.ACTION_MOVE:

                offsetX = originalOffsetX + focusX - downX;
                offsetY = originalOffsetY + focusY - downY;
                invalidate();
                break;

            default:
                return true;
        }
        return true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }
}
