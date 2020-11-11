package com.afdhal_fa.treasure.core.domain.model

data class User(
    val uid: String? = "",
    val name: String? = "",
    val email: String? = "",
    val phoneNumber: String? = "",
    val birtdayDate: String? = "",
    val image: String? = "",
) {
    var isAuthenticated: Boolean = false
    var isNew: Boolean = false
    var isCreated: Boolean = false
}
