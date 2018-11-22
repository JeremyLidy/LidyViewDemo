package com.lidy.demo.viewpage

import android.content.Context
import android.util.AttributeSet
import android.util.EventLog
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.forEach
/**
 * 2个页面的 ViewPager
 */
class TwoPager @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {


    private val overScroll: OverScroller = OverScroller(context)
    private val configuration: ViewConfiguration = ViewConfiguration.get(context)
    private val velocityTracker = VelocityTracker.obtain()

    private val maxVelocity: Int = configuration.scaledMaximumFlingVelocity
    private val minVelocity: Int = configuration.scaledMinimumFlingVelocity


    private var downX: Float = 0f
    private var downY: Float = 0f
    private var downScrollX: Int = 0
    private var scrolling: Boolean = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        val childTop = 0
        var childRight = width
        val childBottom = height

        forEach {
            it.layout(childLeft, childTop, childRight, childBottom)
            childLeft += width
            childRight += height
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        if (ev.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(ev)
        var result = false

        when (ev.actionMasked) {

            MotionEvent.ACTION_DOWN -> {
                scrolling = false
                downX = ev.x
                downY = ev.y
                downScrollX = scrollX
            }

            MotionEvent.ACTION_MOVE -> {
                val dx = downX - ev.x
                if (!scrolling) {
                    // 条件判断 滑动距离 然后 通知父类 获取焦点
                    if (Math.abs(dx) > configuration.scaledPagingTouchSlop) {
                        scrolling = true
                        parent.requestDisallowInterceptTouchEvent(true)
                        result = true
                    }
                }
            }
        }

        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        if (event.actionMasked == event.actionMasked) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)

        when (event.actionMasked) {

            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                downScrollX = scrollX
            }

            MotionEvent.ACTION_MOVE -> {
                var dx = downX - event.x + downScrollX
                if (dx > width) {
                    dx = width.toFloat()
                } else if (dx < 0) {
                    dx = 0f
                }
                scrollTo(dx.toInt(), 0)
            }

            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000, maxVelocity.toFloat())

                val vx = velocityTracker.xVelocity
                val scrollX = scrollX
                val targetPage = if (Math.abs(vx) < minVelocity) {
                    if (scrollX > width / 2) 1 else 0
                } else {
                    if (vx < 0) 1 else 0
                }
                val scrollDistance = if (targetPage == 1) width - scrollX else -scrollX
                overScroll.startScroll(getScrollX(), 0, scrollDistance, 0)
                postInvalidateOnAnimation()
            }

        }


        return true
    }

    override fun computeScroll() {
        if (overScroll.computeScrollOffset()) {
            scrollTo(overScroll.currX, overScroll.currY)
            postInvalidateOnAnimation()
        }

    }

}
