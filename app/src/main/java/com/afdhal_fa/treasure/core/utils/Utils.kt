package com.afdhal_fa.treasure.core.utils

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
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

    val kursIndonesia = NumberFormat.getCurrencyInstance(localeID) as DecimalFormat
    val formatRp = DecimalFormatSymbols()

    formatRp.currencySymbol = "Rp."
    formatRp.monetaryDecimalSeparator = ','
    formatRp.groupingSeparator = '.'

    kursIndonesia.decimalFormatSymbols = formatRp
    kursIndonesia.isDecimalSeparatorAlwaysShown = false
    kursIndonesia.decimalFormatSymbols = formatRp

    return kursIndonesia.format(this.toDouble())
}

fun String.toRupiahUnFormat(): String {
    return this.replace("Rp".toRegex(), "").replace("[.]".toRegex(), "")
}


fun Long.covertToDate(): String {
    val format = SimpleDateFormat("dd / MM / yyyy", Locale.getDefault())
    val mDate = Date(this)
    return format.format(mDate)
}


//fun Int.length() = when(this) {
//    0 -> 1
//    else -> log10(abs(toDouble())).toInt() + 1
//}