package com.afdhal_fa.treasure.view.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.network.AuthRepository
import com.afdhal_fa.treasure.core.network.FirestoreRepository

class AccountViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    fun checkIfUserIsAuthenticated(): LiveData<Resource<User>> =
        AuthRepository.checkIfUserIsAuthenticatedInFirebase()

    fun getTreasureUserData(uid: String): LiveData<Resource<TreasureUser>> =
        FirestoreRepository.viewTreasureUser(uid)

    fun getUserData(uid: String): LiveData<Resource<User>> = FirestoreRepository.viewUser(uid)
}