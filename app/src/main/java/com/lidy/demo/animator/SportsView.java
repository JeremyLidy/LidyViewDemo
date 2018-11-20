package com.lidy.demo.animator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.lidy.demo.Utils;

/**
 * @author lideyou
 */
public class SportsView extends View {

    float progress = 0;
    private static final float RADIUS = Utils.dp2px(100f);

    private RectF rectF = new RectF();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public SportsView(Context context) {
        super(context);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SportsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint.setColor(Color.RED);
        paint.setStrokeCap(Cap.ROUND);
        paint.setStrokeWidth(Utils.dp2px(10f));
        paint.setStyle(Style.STROKE);

        //width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS,
        //                height / 2 + RADIUS

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        rectF.left = getWidth() / 2 - RADIUS;
        rectF.top = getHeight() / 2 - RADIUS;
        rectF.right = getWidth() / 2 + RADIUS;
        rectF.bottom = getHeight() / 2 + RADIUS;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(rectF, -60f, 180f, false, paint);

    }
}
