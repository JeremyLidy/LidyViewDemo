package com.lidy.demo.camera

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.lidy.demo.Utils

/**
 * 写
 */
class CameraAnimationView : View {

    internal var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var camera = Camera()

    constructor(context: Context,
                attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        camera.rotateX(45f)
        camera.setLocation(0f, 0f, Utils.getZForCamera())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //上半部分
        // 这个部分需要倒着画
        canvas.save()
        canvas.translate((100 + 600 / 2).toFloat(), (100 + 600 / 2).toFloat())
        canvas.rotate(-20f)
        canvas.clipRect(-600, -600, 600, 0)
        canvas.rotate(20f)
        canvas.translate((-(100 + 600 / 2)).toFloat(), (-(100 + 600 / 2)).toFloat())
        canvas.drawBitmap(Utils.getAvatar(resources, 600), 100f,
                100f, paint)
        canvas.restore()

        //下半部分
        canvas.save()
        canvas.translate((100 + 600 / 2).toFloat(), (100 + 600 / 2).toFloat())
        canvas.rotate(-20f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(-600, 0, 600, 600)
        canvas.rotate(20f)
        canvas.translate((-(100 + 600 / 2)).toFloat(), (-(100 + 600 / 2)).toFloat())
        canvas.drawBitmap(Utils.getAvatar(resources, 600), 100f,
                100f, paint)
        canvas.restore()
    }
}

