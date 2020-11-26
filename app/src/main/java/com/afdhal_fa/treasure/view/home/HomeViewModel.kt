package com.afdhal_fa.treasure.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.domain.model.Tips
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.network.AuthRepository
import com.afdhal_fa.treasure.core.network.FirestoreRepoHome
import com.afdhal_fa.treasure.core.network.FirestoreRepositoryAccount
import com.afdhal_fa.treasure.core.vo.Resource

class HomeViewModel : ViewModel() {

    fun checkIfUserIsAuthenticated(): LiveData<Resource<User>> =
        AuthRepository.checkIfUserIsAuthenticatedInFirebase()

    fun getTreasureUserData(uid: String): LiveData<Resource<TreasureUser>> =
        FirestoreRepositoryAccount.viewTreasureUserInFirestore(uid)

    fun getTipsTreasure(): LiveData<Resource<MutableList<Tips>>> =
        FirestoreRepoHome.viewTipsInFirestore()
}