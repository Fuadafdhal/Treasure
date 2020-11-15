package com.afdhal_fa.treasure.view.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.network.AuthRepository
import com.afdhal_fa.treasure.core.network.FirestoreRepository

class AccountViewModel : ViewModel() {

    fun checkIfUserIsAuthenticated(): LiveData<Resource<User>> =
        AuthRepository.checkIfUserIsAuthenticatedInFirebase()

    fun logOutAuthenticated(): LiveData<Resource<User>> =
        AuthRepository.logOutAuthenticatedInFirebase()


    fun getTreasureUserData(uid: String): LiveData<Resource<TreasureUser>> =
        FirestoreRepository.viewTreasureUser(uid)

    fun getUserData(uid: String): LiveData<Resource<User>> = FirestoreRepository.viewUser(uid)
}