package com.afdhal_fa.treasure.view.login.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.network.AuthRepository
import com.afdhal_fa.treasure.core.network.FirestoreRepositoryAccount
import com.afdhal_fa.treasure.core.vo.Resource
import com.google.firebase.auth.AuthCredential

class SignInViewModel : ViewModel() {

    fun signInWithEmail(email: String, password: String): LiveData<Resource<User>> =
        AuthRepository.signInWithEmail(email, password)


    fun signInWithGoogle(googleAuthCredential: AuthCredential): LiveData<Resource<User>> =
        AuthRepository.signInWithGoogle(googleAuthCredential)


    fun signInWithFacebook(authCredential: AuthCredential): LiveData<Resource<User>> =
        AuthRepository.signInWithFacebook(authCredential)

    fun createUser(authenticatedUser: User): LiveData<Resource<User>> =
        FirestoreRepositoryAccount.createUserInFirestoreIfNotExists(authenticatedUser)

    fun createTreasureUser(uId: String): LiveData<Resource<TreasureUser>> =
        FirestoreRepositoryAccount.createTreasureUserInFirestoreIfNotExists(uId)

}