package com.lidy.demo.drag;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import com.lidy.demo.R;

/**
 * 拖拽 并且 放下 动画效果
 */
public class DragUpDownLayout extends FrameLayout {

    View view;
    ViewDragHelper dragHelper;
    ViewDragHelper.Callback dragListener = new DragListener();
    ViewConfiguration viewConfiguration;

    public DragUpDownLayout(@NonNull Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        dragHelper = ViewDragHelper.create(this, dragListener);
        viewConfiguration = ViewConfiguration.get(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        view = findViewById(R.id.view);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return dragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private class DragListener extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == view;
        }

        /**
         * 设置纵向移动
         */
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            if (Math.abs(yvel) > viewConfiguration.getScaledMinimumFlingVelocity()) {
                if (yvel > 0) {
                    dragHelper.settleCapturedViewAt(0, getHeight() - releasedChild.getHeight());
                } else {
                    dragHelper.settleCapturedViewAt(0, 0);
                }
            } else {
                if (releasedChild.getTop() < getHeight() - releasedChild.getBottom()) {
                    dragHelper.settleCapturedViewAt(0, 0);
                } else {
                    dragHelper.settleCapturedViewAt(0, getHeight() - releasedChild.getHeight());
                }
            }
            postInvalidateOnAnimation();
        }
    }

}
