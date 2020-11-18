package com.afdhal_fa.treasure.core.domain.model

data class Exchange(
    val id: String = "",
    val type: String = "",
    val phoneNumber: String = "",
    val nominal: Int = 0,
    val totalNominal: Int = 0,
    val exchangeUId: String = ""
)