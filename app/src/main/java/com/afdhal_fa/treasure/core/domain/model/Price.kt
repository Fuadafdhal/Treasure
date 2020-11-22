package com.afdhal_fa.treasure.core.domain.model

import com.google.firebase.firestore.Exclude

data class Price(
    val title: String = "",
    val image: String = "",
    val price: Long = 0L,
    val type: Int = 0,
) {
    @get:Exclude
    val id: String = ""
}