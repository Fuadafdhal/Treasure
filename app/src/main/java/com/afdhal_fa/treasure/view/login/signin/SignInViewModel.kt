package com.afdhal_fa.treasure.view.login.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.network.AuthRepository
import com.google.firebase.auth.AuthCredential

class SignInViewModel : ViewModel() {
    var authResul: LiveData<Resource<User>>? = null

    fun signInWithGoogle(googleAuthCredential: AuthCredential) {
        authResul = AuthRepository.signInWithGoogle(googleAuthCredential)
    }

    fun signInWithEmail(email: String, password: String) {
        authResul = AuthRepository.signInWithEmail(email, password)
    }

    fun signInWithFacebook(authCredential: AuthCredential) {
        authResul = AuthRepository.signInWithFacebook(authCredential)
    }
}