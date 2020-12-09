package com.afdhal_fa.treasure.core.domain.model

import com.google.firebase.database.Exclude

data class Deposit(
    val TrashbagID: String = "",
    val timestamp: Long = 0L,
    val code: String = "",
    val uid: String = ""
) {
    @get:Exclude
    var id: String = ""
}


/*

12836AJFGA
1606576490668
1
OUjfQvwuBDgNUT3Pf5jzA6MoqpV2

code:
1 Prosess
2 OK
3 failled

 */
