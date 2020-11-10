package com.afdhal_fa.treasure.view.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.Account
import com.afdhal_fa.treasure.core.network.AuthRepository
import com.google.firebase.auth.AuthCredential

class SignInViewModel : ViewModel() {
    var authResul: LiveData<Resource<Account>>? = null

    fun signInWithGoogle(googleAuthCredential: AuthCredential) {
        authResul = AuthRepository.firebaseSignInWithGoogle(googleAuthCredential)
    }

    fun signInWithEmail(email: String, password: String) {
        authResul = AuthRepository.firebaseSignInWithEmail(email, password)
    }

    fun signInWithFacebook(authCredential: AuthCredential) {
        authResul = AuthRepository.firebaseSignInWithFacebook(authCredential)
    }
}