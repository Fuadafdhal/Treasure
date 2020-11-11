package com.afdhal_fa.treasure.core.domain.repository

import androidx.lifecycle.LiveData
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.TrashureAccount
import com.afdhal_fa.treasure.core.domain.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthCredential

interface IAccountRepository {

    fun signInWithEmail(email: String, password: String): LiveData<Resource<User>>
    fun signInWithGoogle(googleAuthCredential: AuthCredential): LiveData<Resource<User>>
    fun signInWithFacebook(facebookAuthCredential: FacebookAuthCredential): LiveData<Resource<User>>

    fun signUpWithEmail(email: String, password: String): LiveData<Resource<User>>
    fun signUpWithGoogle(googleAuthCredential: AuthCredential): LiveData<Resource<User>>
    fun signUpWithFacebook(facebookAuthCredential: FacebookAuthCredential): LiveData<Resource<User>>

    fun getInfoAccount(): LiveData<Resource<User>>
    fun getInfoTrashureAccount(): LiveData<Resource<TrashureAccount>>


}