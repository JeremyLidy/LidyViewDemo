package com.lidy.demo.touch

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.View
import android.widget.OverScroller
import androidx.core.view.GestureDetectorCompat
import com.lidy.demo.Utils

/**
 * 多点触控
 *
 * @author lideyou
 */
class ScalableImageView(context: Context,
                        attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    /**
     * 偏移距离
     */
    private var originalOffsetX: Float = 0f
    private var originalOffsetY: Float = 0f

    private var offsetX: Float = 0f
    private var offsetY: Float = 0f

    /**
     * 大图 小图
     */
    private var smallScale: Float = 0f
    private var bigScale: Float = 0f
    private var big: Boolean = false
    private var currentScale: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    private val bitmap: Bitmap
    private val gestureDetector: GestureDetectorCompat
    private val scroller: OverScroller

    private val scaleDetector: ScaleGestureDetector
    private val gestureListener = HenGestureListener()
    private val henScaleListener = HenScaleListener()
    private val flingRunner = HenFlingRunner()

    private lateinit var scaleAnimator: ObjectAnimator

    init {
        bitmap = Utils.getAvatar(resources, IMAGE_WIDTH.toInt())
        gestureDetector = GestureDetectorCompat(context, gestureListener)
        scroller = OverScroller(context)
        scaleDetector = ScaleGestureDetector(context, henScaleListener)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val result = scaleDetector.onTouchEvent(event)
        return if (!scaleDetector.isInProgress) {
            gestureDetector.onTouchEvent(event)
        } else result
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        originalOffsetX = (width - bitmap.width) / 2f
        originalOffsetY = (height - bitmap.height) / 2f

        // 宽高比 == 图片 和 屏幕宽度
        if (bitmap.width.toFloat() / bitmap.height > width.toFloat() / height) {
            smallScale = width.toFloat() / bitmap.width
            bigScale = height.toFloat() / bitmap.height * OVER_SCALE_FACTOR
        } else {
            smallScale = height.toFloat() / bitmap.height
            bigScale = width.toFloat() / bitmap.width * OVER_SCALE_FACTOR
        }

        currentScale = smallScale
    }

    override fun onDraw(canvas: Canvas) {

        // 移动偏移
        val scaleFraction = (currentScale - smallScale) / (bigScale - smallScale)
        canvas.translate(scaleFraction * offsetX, scaleFraction * offsetY)

        canvas.scale(currentScale, currentScale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)

    }

    private fun getScaleAnimator(): ObjectAnimator {
        if (::scaleAnimator.isLateinit) {
            scaleAnimator = ObjectAnimator.ofFloat(this, "currentScale", 0f)
        }
        scaleAnimator.setFloatValues(smallScale, bigScale)
        return scaleAnimator
    }

    /**
     * 手势的处理
     */
    internal inner class HenGestureListener : SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            // 是否拦截事件
            return true
        }

        override fun onShowPress(e: MotionEvent) {
            // 预按下事件
            super.onShowPress(e)
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            //
            return false
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            // 滚动事件
            if (big) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffsets()
                invalidate()
            }
            return false
        }


        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            //滑动
            if (big) {
                scroller.fling(offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                        -(bitmap.width * bigScale - width).toInt() / 2,
                        (bitmap.width * bigScale - width).toInt() / 2,
                        -(bitmap.height * bigScale - height).toInt() / 2,
                        (bitmap.height * bigScale - height).toInt() / 2)

                postOnAnimation(flingRunner)
            }
            return false
        }

        override fun onDoubleTapEvent(e: MotionEvent): Boolean {
            //双击
            return super.onDoubleTapEvent(e)
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            //
            return false
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            big = !big
            if (big) {
                offsetX = e.x - width / 2f - (e.x - width / 2) * bigScale / smallScale
                offsetY = (e.y - height / 2f
                        - (e.y - height / 2) * bigScale / smallScale)
                fixOffsets()
                getScaleAnimator().start()
            } else {
                getScaleAnimator().reverse()
            }
            return false
        }

        override fun onLongPress(e: MotionEvent) {
            super.onLongPress(e)
        }

    }

    private fun fixOffsets() {
        offsetX = Math.min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = Math.max(offsetX, -(bitmap.width * bigScale - width) / 2)
        offsetY = Math.min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = Math.max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }

    /**
     * 缩放处理
     */
    internal inner class HenScaleListener : OnScaleGestureListener {


        private var initialScale: Float = 0f

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            //缩放操作进行中
            currentScale = initialScale * detector.scaleFactor
            invalidate()
            return false
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            //缩放操作开始前
            initialScale = currentScale
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {
            //缩放操作结束后

        }
    }

    internal inner class HenFlingRunner : Runnable {

        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                postOnAnimation(this)
            }
        }
    }

    companion object {
        val IMAGE_WIDTH = Utils.dp2px(100f)
        private const val OVER_SCALE_FACTOR = 1.5f
    }
}
