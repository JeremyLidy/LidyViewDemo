package com.lidy.demo.animator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.lidy.demo.Utils

/**
 * 省份变换
 */
class ProvinceView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    var province = "北京市"
        set(value) {
            field = value
            invalidate()
        }

    init {
        paint.textSize = Utils.dp2px(30f)
        paint.textAlign = Paint.Align.CENTER

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(province, (width / 2).toFloat(), (height / 2).toFloat(), paint)
    }
}
