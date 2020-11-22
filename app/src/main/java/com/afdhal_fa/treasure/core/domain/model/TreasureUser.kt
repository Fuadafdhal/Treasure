package com.afdhal_fa.treasure.core.domain.model

data class TreasureUser(
    val level: Int = 0,
    var saldo: Long = 0L,
    val totalTreash: Int = 0,
    val uid: String = "",
)