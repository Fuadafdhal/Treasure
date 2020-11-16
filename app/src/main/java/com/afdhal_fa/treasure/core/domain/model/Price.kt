package com.afdhal_fa.treasure.core.domain.model

data class Price(
    val id: String = "",
    val title: String = "",
    val image: String = "",
    val price: Long = 0L,
    val type: Int = 0,
)