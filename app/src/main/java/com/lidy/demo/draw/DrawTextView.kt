package com.lidy.demo.draw

import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.graphics.Paint.Cap
import android.graphics.Paint.Style
import android.os.Build.VERSION_CODES
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.graphics.blue
import com.lidy.demo.Utils

/**
 * 绘制中心text
 * @author lideyou
 */
class DrawTextView : View {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var fontMetrics = Paint.FontMetrics()

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)

    private val rect = Rect()

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {

        textPaint.textSize = Utils.dp2px(68f)
        textPaint.style = Paint.Style.FILL
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.getFontMetrics(fontMetrics)
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // 绘制环
        paint.style = Style.STROKE
        paint.color = CIRCLE_COLOR
        paint.strokeWidth = RING_WIDTH
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), RADIUS, paint)

        // 绘制进度条
        paint.color = HIGHLIGHT_COLOR
        paint.strokeCap = Cap.ROUND
        canvas.drawArc(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS,
                height / 2 + RADIUS, -70f, 235f, false, paint)

        // 绘制文字居中
        // 方法一: paint.getTextBounds()
        // 方法二: paint.getFontMetrics()
//        textPaint.getTextBounds("goodbye", 0, "goodbye".length, rect)
//        val offset = (rect.top + rect.bottom) / 2
        val offset = (fontMetrics.ascent + fontMetrics.descent) / 2
        canvas.drawText("goodbye", (width / 2).toFloat(), (height / 2 - offset), textPaint)

        // 左对齐 需要 getTextBounds 中的 left 计算
        textPaint.getTextBounds("goodbye", 0, "goodbye".length, rect)
        textPaint.textAlign = Paint.Align.LEFT
        canvas.drawText("goodbye", -rect.left.toFloat(), (rect.bottom - rect.top).toFloat(), textPaint)
    }

    companion object {
        private val RING_WIDTH = Utils.dp2px(20f)
        private val RADIUS = Utils.dp2px(150f)
        private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
        private val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")
    }
}
