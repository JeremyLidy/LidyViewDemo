package com.lidy.demo.filters

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.DrawFilter
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Build.VERSION.SDK
import android.os.Build.VERSION.SDK_INT
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Checkable
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.content.res.*
import com.lidy.demo.R
import com.lidy.demo.Utils

class EventFilterView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), Checkable {


    var color: Int = 0xffff00ff.toInt()
        set(value) {
            if (field != value) {
                field = value
                dotPaint.color = value
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    postInvalidateOnAnimation()
                } else {
                    postInvalidate()
                }
            }
        }

    var selectedTextColor: Int? = null

    var text: CharSequence? = null
        set(value) {
            field = value
            updateContentDescription()
            requestLayout()
        }

    private var showIcons: Boolean = true
        set(value) {
            if (field != value) {
                field = value
                requestLayout()
            }
        }

    private var progress = 0f
        set(value) {
            if (field != value) {
                field = value
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    postInvalidateOnAnimation()
                } else {
                    postInvalidate()
                }
                if (value == 0f || value == 1f) {
                    updateContentDescription()
                }
            }
        }

    private val outlinePaint: Paint

    private val textPaint: TextPaint

    private val dotPaint: Paint

    private val padding: Int

    private val clear: Drawable

    private val touchFeedback: Drawable

    private lateinit var textLayout: StaticLayout

    private var progressAnimator: ValueAnimator? = null

    private val intercp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_slow_in)
    } else {
        AnimationUtils.loadInterpolator(context, android.R.interpolator.fast_out_extra_slow_in)
    }

    @ColorInt
    private val defaultTextColor: Int

    init {

        val a = context.obtainStyledAttributes(attrs,
                R.styleable.EventFilterView)

        outlinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = a.getColorOrThrow(R.styleable.EventFilterView_android_color)
            strokeWidth = a.getDimensionOrThrow(R.styleable.EventFilterView_outlineWidth)
            style = Paint.Style.STROKE
        }

        defaultTextColor = a.getColorOrThrow(R.styleable.EventFilterView_android_color)

        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = defaultTextColor
            textSize = a.getDimensionOrThrow(R.styleable.EventFilterView_android_textSize)
        }

        dotPaint = Paint(Paint.ANTI_ALIAS_FLAG)

        clear = a.getDrawableOrThrow(R.styleable.EventFilterView_clearIcon).apply {
            setBounds(-intrinsicWidth / 2, -intrinsicHeight / 2, intrinsicWidth / 2, intrinsicHeight / 2)
        }

        touchFeedback = a.getDrawableOrThrow(R.styleable.EventFilterView_foreground).apply {
            callback = this@EventFilterView
        }

        padding = a.getDimensionPixelOffsetOrThrow(R.styleable.EventFilterView_android_padding)

        isChecked = a.getBooleanOrThrow(R.styleable.EventFilterView_android_checked)

        showIcons = a.getBooleanOrThrow(R.styleable.EventFilterView_showIcons)

        a.recycle()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            clipToOutline = true
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    override fun isChecked(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toggle() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setChecked(checked: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun createLayout(textWidth: Int) {
        textLayout = if (SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(this.text, 0, text?.length!!, textPaint, textWidth).build()
        } else {
            StaticLayout(text, textPaint, textWidth, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, true)
        }
    }

    private fun updateContentDescription() {

    }

}