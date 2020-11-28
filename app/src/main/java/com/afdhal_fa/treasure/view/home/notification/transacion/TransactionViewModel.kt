package com.afdhal_fa.treasure.view.home.notification.transacion

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.domain.model.Exchange
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.network.AuthRepository
import com.afdhal_fa.treasure.core.network.FirestoreRepositoryAccount
import com.afdhal_fa.treasure.core.network.FirestoreRepositoryExchange
import com.afdhal_fa.treasure.core.vo.Resource

class TransactionViewModel : ViewModel() {
    fun getTransaction(uid: String): LiveData<Resource<MutableList<Exchange>>> =
        FirestoreRepositoryExchange.viewExchangeInFirestore(uid)

    fun checkIfUserIsAuthenticated(): LiveData<Resource<User>> =
        AuthRepository.checkIfUserIsAuthenticatedInFirebase()

    fun getTreasureUser(uid: String): LiveData<Resource<TreasureUser>> =
        FirestoreRepositoryAccount.viewTreasureUserInFirestore(uid)
}
