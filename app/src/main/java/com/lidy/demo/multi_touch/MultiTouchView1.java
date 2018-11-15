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
 *  接力型 多点触控
 *  同一个时刻只有一个 Pointer 起作用，即最新的 Pointer。典型的是 ListView 、RecyclerView
 *  @author lideyou
 */
public class MultiTouchView1 extends View {

    private static final float IMAGE_WIDTH = Utils.INSTANCE.dp2px(100f);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Bitmap bitmap;

    private float downX, downY;
    private float offsetX, offsetY;
    private float originalOffsetX, originalOffsetY;
    private int trackingPointerId;

    public MultiTouchView1(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);

        bitmap = Utils.INSTANCE.getAvatar(getResources(), (int) IMAGE_WIDTH);
    }

    public MultiTouchView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                trackingPointerId = event.getPointerId(0);
                downX = event.getX();
                downY = event.getY();
                originalOffsetX = offsetX;
                originalOffsetY = offsetY;

                break;
            case MotionEvent.ACTION_MOVE:
                // trackingPointerId 是最新的 Pointer Id
                int index = event.findPointerIndex(trackingPointerId);
                offsetX = originalOffsetX + event.getX(index) - downX;
                offsetY = originalOffsetY + event.getY(index) - downY;
                invalidate();

                break;
            case MotionEvent.ACTION_POINTER_DOWN:

                int actionIndex = event.getActionIndex();
                trackingPointerId = event.getPointerId(actionIndex);
                downX = event.getX(trackingPointerId);
                downY = event.getY(trackingPointerId);

                originalOffsetX = offsetX;
                originalOffsetY = offsetY;

                break;
            case MotionEvent.ACTION_POINTER_UP:

                actionIndex = event.getActionIndex();
                int pointerIndex = event.getPointerId(actionIndex);
                if (pointerIndex == trackingPointerId) {
                    int newIndex;
                    if (actionIndex == event.getPointerCount() - 1) {
                        newIndex = event.getPointerCount() - 2;
                    } else {
                        newIndex = event.getPointerCount() - 1;
                    }
                    trackingPointerId = event.getPointerId(newIndex);
                    downX = event.getX(trackingPointerId);
                    downY = event.getY(trackingPointerId);
                    originalOffsetX = offsetX;
                    originalOffsetY = offsetY;
                }

                break;
            default:
                super.onTouchEvent(event);
        }

        return true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint);
    }
}
