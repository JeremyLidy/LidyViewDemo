package com.lidy.demo.touch;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.widget.OverScroller;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;
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
    private HenGestureListener gestureListener = new HenGestureListener();
    private HenScaleListener henScaleListener = new HenScaleListener();
    private HenFlingRunner flingRunner = new HenFlingRunner();

    private ObjectAnimator scaleAnimator;


    public ScalableImageView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap = Utils.INSTANCE.getAvatar(getResources(), (int) IMAGE_WIDTH);
        gestureDetector = new GestureDetectorCompat(context, gestureListener);
        scroller = new OverScroller(context);
        scaleDetector = new ScaleGestureDetector(context, henScaleListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = scaleDetector.onTouchEvent(event);
        if (!scaleDetector.isInProgress()) {
            return gestureDetector.onTouchEvent(event);
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        originalOffsetX = (getWidth() - bitmap.getWidth()) / 2;
        originalOffsetY = (getHeight() - bitmap.getHeight()) / 2;

        if ((float) bitmap.getWidth() / bitmap.getHeight() > (float) getWidth() / getHeight()) {
            smallScale = (float) getWidth() / bitmap.getWidth();
            bigScale = (float) getHeight() / bitmap.getHeight() * OVER_SCALE_FACTOR;
        } else {
            smallScale = (float) getHeight() / bitmap.getHeight();
            bigScale = (float) getWidth() / bitmap.getWidth() * OVER_SCALE_FACTOR;
        }

        currentScale = smallScale;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        float scaleFraction = (currentScale - smallScale) / (bigScale - smallScale);
        canvas.translate(scaleFraction * offsetX, scaleFraction * offsetY);
        canvas.scale(currentScale, currentScale, getWidth() / 2f, getHeight() / 2f);
        canvas.drawBitmap(bitmap, originalOffsetX,
                originalOffsetY, paint);

    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
    }

    private ObjectAnimator getScaleAnimator() {
        if (scaleAnimator == null) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0);
        }
        scaleAnimator.setFloatValues(smallScale, bigScale);
        return scaleAnimator;
    }

    /**
     * 手势的处理
     */
    class HenGestureListener extends SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //滑动
            scroller.fling((int) offsetX, (int) offsetY, (int) velocityX, (int) velocityY,
                    -(int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                    (int) (bitmap.getWidth() * bigScale - getWidth()) / 2,
                    -(int) (bitmap.getHeight() * bigScale - getHeight()) / 2,
                    (int) (bitmap.getHeight() * bigScale - getHeight()) / 2);

            postOnAnimation(flingRunner);
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            big = !big;
            if (big) {
                offsetX = (e.getX() - getWidth() / 2f)
                        - (e.getX() - getWidth() / 2) * bigScale / smallScale;
                offsetY = e.getY() - getHeight() / 2f
                        - (e.getY() - getHeight() / 2) * bigScale / smallScale;
                getScaleAnimator().start();
            } else {
                getScaleAnimator().reverse();
            }
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

    }

    private void fixOffsets() {
        offsetX = Math.min(offsetX, (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetX = Math.max(offsetX, - (bitmap.getWidth() * bigScale - getWidth()) / 2);
        offsetY = Math.min(offsetY, (bitmap.getHeight() * bigScale - getHeight()) / 2);
        offsetY = Math.max(offsetY, - (bitmap.getHeight() * bigScale - getHeight()) / 2);
    }

    /**
     * 缩放处理
     */
    class HenScaleListener implements OnScaleGestureListener {


        float initialScale;
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            //缩放操作进行中
            currentScale = initialScale * detector.getScaleFactor();
            invalidate();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            //缩放操作开始前
            initialScale = currentScale;
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            //缩放操作结束后

        }
    }

    class HenFlingRunner implements Runnable {

        @Override
        public void run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.getCurrX();
                offsetY = scroller.getCurrY();
                invalidate();
                postOnAnimation(this);
            }
        }
    }
}
