package com.lidy.demo

import android.content.res.Resources
import android.util.TypedValue
import android.graphics.BitmapFactory
import android.graphics.Bitmap


object Utils {

    fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
    }


    fun getAvatar(res: Resources, width: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, R.drawable.dog, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(res, R.drawable.dog, options)
    }

    fun getZForCamera(): Float {
        return -6 * Resources.getSystem().displayMetrics.density
    }
}
