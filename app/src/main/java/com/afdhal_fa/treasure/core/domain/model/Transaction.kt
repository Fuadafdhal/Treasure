package com.afdhal_fa.treasure.core.domain.model

data class Transaction(
    var type: String = "",
    var serviceType: String = "",
    var phoneNumber: String = "",
    var nominal: Long = 0,
    var totalNominal: Long = 0,
    val uId: String = ""
)
