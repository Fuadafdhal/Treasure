package com.afdhal_fa.treasure.core.utils

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import java.text.NumberFormat
import java.util.*

fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
).toInt()

fun TextView.setMarginStart(marginStart: Int) {
    val param = this.layoutParams as ViewGroup.MarginLayoutParams
    param.marginStart = marginStart.toDp(context)
    this.layoutParams = param
}

fun Int.toRupiah(): String {
    val localeID = Locale("in", "ID")
    val format = NumberFormat.getCurrencyInstance(localeID)
    return format.format(this.toDouble())
}

fun String.toRupiahUnFormat(): String {
    return this.replace("Rp".toRegex(), "").replace("""[.]""".toRegex(), "")
}

fun String.toRupiahUnFormatRupiah(): String {
    return this.substring(0, this.length - 3)
}

//fun Int.length() = when(this) {
//    0 -> 1
//    else -> log10(abs(toDouble())).toInt() + 1
//}