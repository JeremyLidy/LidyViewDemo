package com.lidy.demo.layout

import android.content.Context
import android.util.AttributeSet

import androidx.appcompat.widget.AppCompatImageView

/**
 * 测试 measure 自定义范围
 */
class SquareImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val measuredWidth = measuredWidth
        val measuredHeight = measuredHeight

        val size = Math.min(measuredHeight, measuredWidth)
        //保存所测得尺寸
        setMeasuredDimension(size, size)
    }
}
