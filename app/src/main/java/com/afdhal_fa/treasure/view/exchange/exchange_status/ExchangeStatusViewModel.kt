package com.afdhal_fa.treasure.view.exchange.exchange_status

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.domain.model.Exchange
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
import com.afdhal_fa.treasure.core.network.FirestoreRepositoryAccount
import com.afdhal_fa.treasure.core.network.FirestoreRepositoryExchange
import com.afdhal_fa.treasure.core.vo.Resource

class ExchangeStatusViewModel : ViewModel() {

    fun getTreasureUser(uid: String): LiveData<Resource<TreasureUser>> =
        FirestoreRepositoryAccount.viewTreasureUserInFirestore(uid)

    fun createExchange(mExchange: Exchange): LiveData<Resource<Exchange>> =
        FirestoreRepositoryExchange.createExchangeInFirestore(mExchange)

    fun updateTreasureUser(
        mTreasureUser: TreasureUser,
        totalNominal: Long
    ): LiveData<Resource<TreasureUser>> {
        mTreasureUser.saldo = mTreasureUser.saldo.minus(totalNominal)
        return FirestoreRepositoryAccount.updateTreasureUserInFirestore(mTreasureUser)
    }
}

