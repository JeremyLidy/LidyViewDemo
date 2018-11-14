package com.lidy.demo.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.view.View

/**
 * Bitmap 和 Drawable 互转
 */
class DrawableView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
    }

}
