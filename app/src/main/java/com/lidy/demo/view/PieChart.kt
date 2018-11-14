package com.lidy.demo.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.lidy.demo.Utils

/**
 * 饼状图
 */
class PieChart(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var bounds = RectF()
    private val angles = arrayOf(60, 100, 120, 80)
    private val colors = arrayOf(Color.parseColor("#2979FF"), Color.parseColor("#C2185B"),
            Color.parseColor("#009688"), Color.parseColor("#FF8F00"))

    companion object {
        val RADIUS = Utils.dp2px(150f)
        val LENGTH = Utils.dp2px(10f)
        const val PULLED_OUT_INDEX = 2
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bounds.set(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var currentAngle = 0

        angles.forEachIndexed { index, angle ->
            paint.color = colors[index]
            canvas.save()

            // 抽离某一块
            if (index == PULLED_OUT_INDEX){
                canvas.translate((Math.cos(Math.toRadians((currentAngle + angle / 2).toDouble())) * LENGTH).toFloat(),
                        (Math.sin(Math.toRadians((currentAngle + angle / 2).toDouble())) * LENGTH).toFloat())
            }

            canvas.drawArc(bounds, currentAngle.toFloat(), angle.toFloat(), true, paint)
            canvas.restore()
            currentAngle += angle
        }

    }
}
