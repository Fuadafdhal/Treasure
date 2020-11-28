package com.afdhal_fa.treasure.core.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Exchange(
    var type: String = "",
    var serviceType: String = "",
    var phoneNumber: String = "",
    var nominal: Long = 0,
    var totalNominal: Long = 0,
    val timestamp: Long = 0L,
    val read: Boolean = false,
    val uid: String = ""
) : Parcelable {
    @get:Exclude
    var id: String = ""
}