package com.lidy.demo.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class MatrixView extends View {

    private Matrix matrix;

    public MatrixView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MatrixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        matrix = new Matrix();
        matrix.reset();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.setMatrix(matrix);
    }

    /**
     * 绘制背景
     */
    @Override
    public void onDrawForeground(Canvas canvas) {
        super.onDrawForeground(canvas);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);


    }
}
