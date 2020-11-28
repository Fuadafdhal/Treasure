package com.afdhal_fa.treasure.core.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TreasureUser(
    val level: Int = 0,
    var saldo: Long = 0L,
    val totalTreash: Int = 0,
    val uid: String = "",
) : Parcelable {
    @get:Exclude
    var id: String = ""
}