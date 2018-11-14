package com.lidy.demo.touch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.widget.OverScroller;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
import com.lidy.demo.R;
import com.lidy.demo.Utils;

/**
 * 多点触控
 *
 * @author lideyou
 */
public class ScalableImageView extends View {


    public static final float IMAGE_WIDTH = Utils.INSTANCE.dp2px(260);
    private static final float OVER_SCALE_FACTOR = 1.5f;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private float originalOffsetX, originalOffsetY;
    private float offsetX, offsetY;

    /**
     * 大图 小图
     */
    private float smallScale, bigScale;
    private boolean big;
    private float currentScale;
    private Bitmap bitmap;
    private GestureDetectorCompat gestureDetector;
    private OverScroller scroller;
    private ScaleGestureDetector scaleDetector;


    public ScalableImageView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.INSTANCE.getAvatar(getResources(), (int) IMAGE_WIDTH);
        gestureDetector = new GestureDetectorCompat(context, new SimpleOnGestureListener() {

        });

        scroller = new OverScroller(context);
        scaleDetector = new ScaleGestureDetector(context, new OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return false;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        originalOffsetX = (getWidth() - bitmap.getWidth()) / 2;
        originalOffsetY = (getHeight() - bitmap.getHeight()) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(bitmap, originalOffsetX,
                originalOffsetY, paint);

    }
}
