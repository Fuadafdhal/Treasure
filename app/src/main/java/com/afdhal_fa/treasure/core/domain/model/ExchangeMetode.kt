package com.afdhal_fa.treasure.core.domain.model

import com.google.firebase.firestore.Exclude

data class ExchangeMetode(
    val icon: Int,
    val title: String,
) {
    @get:Exclude
    var position: Int = 0
}