package com.afdhal_fa.treasure.core.utils

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import timber.log.Timber
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
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

    val kursIndonesia = NumberFormat.getCurrencyInstance(localeID) as DecimalFormat
    val formatRp = DecimalFormatSymbols()

    formatRp.currencySymbol = "Rp."
    formatRp.monetaryDecimalSeparator = ','
    formatRp.groupingSeparator = '.'

    kursIndonesia.decimalFormatSymbols = formatRp
    kursIndonesia.isDecimalSeparatorAlwaysShown = false
    kursIndonesia.decimalFormatSymbols = formatRp

    return kursIndonesia.format(this.toDouble()).replace(",00", "")
}

fun String.toRupiahUnFormat(): String {
    return this.replace("Rp".toRegex(), "").replace("[.]".toRegex(), "")
}

//fun String.toRupiahUnFormatRupiah(): String {
//    return if (this.contains(",00")) this.substring(0, this.length - 3) else this
//}

fun ujicoba() {
    val harga = 250_000_000
    val localeId = Locale("id", "ID")
    val kursIndonesia = NumberFormat.getCurrencyInstance(localeId) as DecimalFormat
    val formatRp = DecimalFormatSymbols()

    formatRp.currencySymbol = "Rp."
    formatRp.monetaryDecimalSeparator = ','
    formatRp.groupingSeparator = '.'

    kursIndonesia.decimalFormatSymbols = formatRp
    kursIndonesia.isDecimalSeparatorAlwaysShown = false
    kursIndonesia.decimalFormatSymbols = formatRp

    val result = kursIndonesia.format(harga.toDouble()).replace(",00", "")

    Timber.d(result)
}

//fun Int.length() = when(this) {
//    0 -> 1
//    else -> log10(abs(toDouble())).toInt() + 1
//}