package com.lidy.demo.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.lidy.demo.Utils

/**
 * 仪表盘 自定义 View
 */
class DashboardView(context: Context, attrs: AttributeSet) : View(context, attrs) {


    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var dash = Path()
    private var effect: PathDashPathEffect
    var bounds: RectF

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = Utils.dp2px(2f)
        dash.addRect(0f, 0f, Utils.dp2px(2f), Utils.dp2px(10f), Path.Direction.CW)
        val arc = Path()
//        bounds = RectF(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS)
        bounds = RectF( - RADIUS, - RADIUS,  RADIUS,   RADIUS)
        arc.addArc(bounds, (90 + ANGLE / 2).toFloat(), (360 - ANGLE).toFloat())
        val pathMeasure = PathMeasure(arc, false)
        effect = PathDashPathEffect(dash, (pathMeasure.length - Utils.dp2px(2f)) / 20, 0f, PathDashPathEffect.Style.ROTATE)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds = RectF(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 画线
        canvas.drawArc(bounds, (90 + ANGLE / 2).toFloat(), (360 - ANGLE).toFloat(), false, paint)

        // 画刻度
        paint.pathEffect = effect
        canvas.drawArc(bounds, (90 + ANGLE / 2).toFloat(), (360 - ANGLE).toFloat(), false, paint)
        paint.pathEffect = null

        //画指针
        canvas.drawLine((width / 2).toFloat(), (height / 2).toFloat(),
                (Math.cos(Math.toRadians(getAngleFromMark(5))) * LENGTH + width / 2).toFloat(),
                (Math.sin(Math.toRadians(getAngleFromMark(5))) * LENGTH + height / 2).toFloat(),
                paint)
    }

    private fun getAngleFromMark(mark: Int): Double {
        return (90 + ANGLE / 2 + (360 - ANGLE) / 20 * mark).toDouble()
    }

    companion object {
        const val ANGLE = 120
        val RADIUS = Utils.dp2px(150f)
        val LENGTH = Utils.dp2px(100f)
    }
}
