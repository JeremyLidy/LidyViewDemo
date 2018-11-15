package com.lidy.demo.camera

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.lidy.demo.Utils

/**
 * 理解 camera
 * @author lideyou
 */
class CameraView : View {

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var camera = Camera()

    private var topFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var bottomFlip = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var flipRotation = 0f
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context,
                attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        camera.setLocation(0f, 0f, Utils.getZForCamera())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //上半部分
        canvas.save()
        canvas.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2)
        canvas.rotate(-flipRotation)
        camera.save()
        camera.rotateX(topFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-IMAGE_WIDTH, -IMAGE_WIDTH, IMAGE_WIDTH, 0f)
        canvas.rotate(flipRotation)
        canvas.translate(-(PADDING + IMAGE_WIDTH / 2), -(PADDING + IMAGE_WIDTH / 2))
        canvas.drawBitmap(Utils.getAvatar(resources, IMAGE_WIDTH.toInt()), PADDING,
                PADDING, paint)
        canvas.restore()

        //下半部分
        canvas.save()
        canvas.translate(PADDING + IMAGE_WIDTH / 2, PADDING + IMAGE_WIDTH / 2)
        canvas.rotate(-flipRotation)
        camera.save()
        camera.rotateX(bottomFlip)
        camera.applyToCanvas(canvas)
        camera.restore()
        canvas.clipRect(-IMAGE_WIDTH, 0f, IMAGE_WIDTH, IMAGE_WIDTH)
        canvas.rotate(flipRotation)
        canvas.translate(-(PADDING + IMAGE_WIDTH / 2), -(PADDING + IMAGE_WIDTH / 2))
        canvas.drawBitmap(Utils.getAvatar(resources, IMAGE_WIDTH.toInt()), PADDING,
                PADDING, paint)
        canvas.restore()
    }


    companion object {
        private val PADDING = Utils.dp2px(100f)
        private val IMAGE_WIDTH = Utils.dp2px(200f)
    }

}
