package com.afdhal_fa.treasure.view.login.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.network.AuthRepository
import com.afdhal_fa.treasure.core.network.FirestoreRepositoryAccount
import com.google.firebase.auth.AuthCredential

class SignUpViewModel : ViewModel() {

    fun signUpWithEmail(
        name: String,
        email: String,
        phone: String,
        password: String
    ): LiveData<Resource<User>> = AuthRepository.signUpWithEmail(name, email, phone, password)

    fun signInWithGoogle(credential: AuthCredential): LiveData<Resource<User>> =
        AuthRepository.signInWithGoogle(credential)

    fun signInWithFacebook(credential: AuthCredential): LiveData<Resource<User>> =
        AuthRepository.signInWithFacebook(credential)


    fun createUser(authenticatedUser: User): LiveData<Resource<User>> =
        FirestoreRepositoryAccount.createUserInFirestoreIfNotExists(authenticatedUser)
}
