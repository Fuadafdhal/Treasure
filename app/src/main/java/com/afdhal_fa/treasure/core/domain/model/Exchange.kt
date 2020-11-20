package com.afdhal_fa.treasure.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Exchange(
    val id: String = "",
    var type: String = "",
    var serviceType: String = "",
    var phoneNumber: String = "",
    var nominal: Int = 0,
    var totalNominal: Int = 0,
    val exchangeUId: String = ""
) : Parcelable