package com.afdhal_fa.treasure.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Nominal(
    val nominal: Int,
    val totalNominal: Int
) : Parcelable