package com.afdhal_fa.treasure.core.domain.model

data class TrashureAccount(
    val id: String,
    val saldo: Int,
    val level: Int,
    val total: Int,
    val userId: String,
)