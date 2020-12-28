package com.afdhal_fa.treasure.core.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.Exclude
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val birtdayDate: String = "",
    val image: String = ""
) : Parcelable {
    @IgnoredOnParcel
    @get:Exclude
    var isAuthenticated: Boolean = false

    @IgnoredOnParcel
    @get:Exclude
    var isNew: Boolean = false

    @IgnoredOnParcel
    @get:Exclude
    var isCreated: Boolean = false

    @IgnoredOnParcel
    @get:Exclude
    var isReset: Boolean = false
}
