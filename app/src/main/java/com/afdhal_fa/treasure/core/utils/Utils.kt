package com.afdhal_fa.treasure.core.utils

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView

fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
).toInt()

fun TextView.setMarginStart(marginStart: Int) {
    val param = this.layoutParams as ViewGroup.MarginLayoutParams
    param.marginStart = marginStart.toDp(context)
    this.layoutParams = param
}