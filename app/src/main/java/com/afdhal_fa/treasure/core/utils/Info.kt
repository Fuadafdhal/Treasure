package com.afdhal_fa.treasure.core.utils

import android.content.Context
import android.widget.Toast
import com.afdhal_fa.treasure.R

fun Context.makeToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}


private fun getDataDrawrable(): MutableMap<String, Int> {
    val operator: MutableMap<String, Int> = HashMap()
    operator["62811"] = R.drawable.logo_telkomsel
    operator["62812"] = R.drawable.logo_telkomsel
    operator["62813"] = R.drawable.logo_telkomsel
    operator["62821"] = R.drawable.logo_telkomsel
    operator["62822"] = R.drawable.logo_telkomsel
    operator["62823"] = R.drawable.logo_telkomsel
    operator["62851"] = R.drawable.logo_telkomsel
    operator["62852"] = R.drawable.logo_telkomsel
    operator["62853"] = R.drawable.logo_telkomsel

    operator["62814"] = R.drawable.logo_indosat
    operator["62815"] = R.drawable.logo_indosat
    operator["62816"] = R.drawable.logo_indosat
    operator["62855"] = R.drawable.logo_indosat
    operator["62856"] = R.drawable.logo_indosat
    operator["62857"] = R.drawable.logo_indosat
    operator["62858"] = R.drawable.logo_indosat

    operator["62817"] = R.drawable.logo_xl
    operator["62818"] = R.drawable.logo_xl
    operator["62819"] = R.drawable.logo_xl
    operator["62859"] = R.drawable.logo_xl
    operator["62877"] = R.drawable.logo_xl
    operator["62878"] = R.drawable.logo_xl
    operator["62879"] = R.drawable.logo_xl

    operator["62831"] = R.drawable.logo_axis
    operator["62833"] = R.drawable.logo_axis
    operator["62835"] = R.drawable.logo_axis
    operator["62836"] = R.drawable.logo_axis
    operator["62837"] = R.drawable.logo_axis
    operator["62838"] = R.drawable.logo_axis
    operator["62839"] = R.drawable.logo_axis

    operator["62881"] = R.drawable.logo_smartfren
    operator["62882"] = R.drawable.logo_smartfren
    operator["62883"] = R.drawable.logo_smartfren
    operator["62884"] = R.drawable.logo_smartfren
    operator["62885"] = R.drawable.logo_smartfren
    operator["62886"] = R.drawable.logo_smartfren
    operator["62887"] = R.drawable.logo_smartfren
    operator["6288"] = R.drawable.logo_smartfren
    operator["0889"] = R.drawable.logo_smartfren

    operator["62893"] = R.drawable.logo_three
    operator["62894"] = R.drawable.logo_three
    operator["62895"] = R.drawable.logo_three
    operator["62896"] = R.drawable.logo_three
    operator["62898"] = R.drawable.logo_three
    operator["62897"] = R.drawable.logo_three
    operator["62899"] = R.drawable.logo_three

    return operator
}

fun cekOperatorDrawrabel(pref: String): Int {
    var result = 0
    val operator: MutableMap<String, Int> = getDataDrawrable()

    for ((key, value) in operator) {
        if (pref.equals(key, true)) {
            result = value
        }
    }
    if (result == 0) {
        result = 0
    }
    return result
}


private fun getDataWithText(): MutableMap<String, String> {
    val operator: MutableMap<String, String> = HashMap()
    operator["62811"] = "Telkomsel"
    operator["62812"] = "Telkomsel"
    operator["62813"] = "Telkomsel"
    operator["62821"] = "Telkomsel"
    operator["62822"] = "Telkomsel"
    operator["62823"] = "Telkomsel"
    operator["62851"] = "Telkomsel"
    operator["62852"] = "Telkomsel"
    operator["62853"] = "Telkomsel"

    operator["62814"] = "Indosat Oreedoo"
    operator["62815"] = "Indosat Oreedoo"
    operator["62816"] = "Indosat Oreedoo"
    operator["62855"] = "Indosat Oreedoo"
    operator["62856"] = "Indosat Oreedoo"
    operator["62857"] = "Indosat Oreedoo"
    operator["62858"] = "Indosat Oreedoo"

    operator["62817"] = "XL"
    operator["62818"] = "XL"
    operator["62819"] = "XL"
    operator["62859"] = "XL"
    operator["62877"] = "XL"
    operator["62878"] = "XL"
    operator["62879"] = "XL"

    operator["62831"] = "Axis"
    operator["62833"] = "Axis"
    operator["62835"] = "Axis"
    operator["62836"] = "Axis"
    operator["62837"] = "Axis"
    operator["62838"] = "Axis"
    operator["62839"] = "Axis"

    operator["62881"] = "Smartfren"
    operator["62882"] = "Smartfren"
    operator["62883"] = "Smartfren"
    operator["62884"] = "Smartfren"
    operator["62885"] = "Smartfren"
    operator["62886"] = "Smartfren"
    operator["62887"] = "Smartfren"
    operator["6288"] = "Smartfren"
    operator["0889"] = "Smartfren"

    operator["62893"] = "Three"
    operator["62894"] = "Three"
    operator["62895"] = "Three"
    operator["62896"] = "Three"
    operator["62898"] = "Three"
    operator["62897"] = "Three"
    operator["62899"] = "Three"

    return operator
}

fun cekOperatorWithText(pref: String): String {
    var result = ""
    val operator: MutableMap<String, String> = getDataWithText()

    for ((key, value) in operator) {
        if (pref.equals(key, true)) {
            result = value
        }
    }
    if (result == "") {
        result = ""
    }
    return result
}

