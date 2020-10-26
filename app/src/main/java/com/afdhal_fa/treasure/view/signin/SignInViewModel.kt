package com.afdhal_fa.treasure.view.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.Account
import com.afdhal_fa.treasure.core.network.AuthRepository
import com.google.firebase.auth.AuthCredential

class SignInViewModel : ViewModel() {
    var authenticatedUserLiveData: LiveData<Resource<Account>>? = null
    fun signInWithGoogle(googleAuthCredential: AuthCredential) {
        authenticatedUserLiveData = AuthRepository.firebaseSignInWithGoogle(googleAuthCredential)
    }

    fun signInWithEmail(email: String, password: String) {
        TODO("use $email and $password")
    }

    fun signInWithFacebook(authCredential: AuthCredential) {
        authenticatedUserLiveData = AuthRepository.firebaseSignInWithFacebook(authCredential)
    }
}