package com.afdhal_fa.treasure.core.domain.model

import com.google.firebase.firestore.Exclude

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birtdayDate: String = "",
    val image: String = ""
) {
    @get:Exclude
    var isAuthenticated: Boolean = false

    @get:Exclude
    var isNew: Boolean = false

    @get:Exclude
    var isCreated: Boolean = false
}
