package com.afdhal_fa.treasure.core.domain.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp

data class Tips(
    val title: String = "",
    val desc: String = "",
    val image: String = "",
    val timestamp: Long = 0L,
    @ServerTimestamp val timestamps: Timestamp? = null
) {
    @get:Exclude
    var id: String = ""
}