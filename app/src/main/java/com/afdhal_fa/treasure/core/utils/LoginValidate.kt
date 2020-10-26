package com.afdhal_fa.treasure.core.utils

object LoginValidate {
    fun isValidEmail(email: String) = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidPassoword(password: String) = password.length >= 8

    fun isValidRePassoword(password: String, re_password: String) = password.equals(re_password)
}