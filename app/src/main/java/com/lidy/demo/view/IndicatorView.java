package com.lidy.demo.view;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;
import com.lidy.demo.Utils;

public class IndicatorView extends View {

    private Paint slidePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mDotWidth;
    private int mHeight;

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rectF = new RectF();
    private RectF itemRect;
    private int length = 4;
    private float offset;
    private float itemWidth;
    private int current = 0;

    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    {
        offset = Utils.dp2px(5);

        mDotWidth = (int) Utils.dp2px(150f);
        mHeight = (int) Utils.dp2px((5f));
        itemWidth = (mDotWidth - offset * 2) / length;

        itemRect = getItemRectF(0);

        paint.setColor(Color.RED);
        paint.setStrokeCap(Cap.ROUND);
        paint.setStrokeWidth(Utils.dp2px(5f));
        paint.setStyle(Style.STROKE);

        slidePaint.setColor(Color.BLACK);
        slidePaint.setStrokeCap(Cap.ROUND);
        slidePaint.setStrokeWidth(Utils.dp2px(5f) - 1);
        slidePaint.setStyle(Style.STROKE);

        rectF.left = Utils.dp2px(10);
        rectF.top = Utils.dp2px(10);
        rectF.right = Utils.dp2px(120);
        rectF.bottom = Utils.dp2px(10);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        itemRect = getTargetRect(current);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //背景图
        canvas.drawLine(offset,
                mHeight / 2,
                mDotWidth - offset,
                mHeight / 2, paint
        );
        //画细线
        canvas.drawLine(itemRect.left, itemRect.top, itemRect.right, itemRect.bottom, slidePaint);
    }

    private RectF getItemRectF(int index) {
        RectF rectF = new RectF();
        rectF.left = offset + itemWidth * index;
        rectF.top = mHeight / 2;
        rectF.right = itemWidth * (index + 1) + offset;
        rectF.bottom = mHeight / 2;
        return rectF;
    }

    public void setLenght(int length) {
        this.length = length;
    }

    public void setCurrent(int current) {
        if (this.current != current) {
            this.current = current;
            startAnimation(current);
        }

    }

    private void startAnimation(int current) {
        ObjectAnimator animator = ObjectAnimator
                .ofObject(this, "itemRect", new PointEvaluator(), getItemRectF(current));
        animator.setDuration(400);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    public RectF getTargetRect(int targetRect) {
        current = targetRect;
        return getItemRectF(targetRect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mDotWidth = resolveSizeAndState(mDotWidth, widthMeasureSpec, 0);
        mHeight = resolveSizeAndState(mHeight, heightMeasureSpec, 0);

        setMeasuredDimension(mDotWidth, mHeight);
    }

    public RectF getItemRect() {
        return itemRect;
    }

    public void setItemRect(RectF itemRect) {
        this.itemRect = itemRect;
        invalidate();
    }

    public class PointEvaluator implements TypeEvaluator<RectF> {

        @Override
        public RectF evaluate(float fraction, RectF startValue, RectF endValue) {
            float left = startValue.left + (endValue.left - startValue.left) * fraction;
            float right = startValue.right + (endValue.right - startValue.right) * fraction;
            return new RectF(left, startValue.top, right, startValue.bottom);
        }
    }
}
