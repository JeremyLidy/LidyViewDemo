package com.lidy.demo

import android.content.res.Resources
import android.util.TypedValue

object Utils {

    fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
    }

}
