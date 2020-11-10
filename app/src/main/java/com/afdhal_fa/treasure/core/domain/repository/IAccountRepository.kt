package com.afdhal_fa.treasure.core.domain.repository

import androidx.lifecycle.LiveData
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.Account
import com.afdhal_fa.treasure.core.domain.model.TrashureAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthCredential

interface IAccountRepository {

    fun signInWithEmail(email: String, password: String): LiveData<Resource<Account>>
    fun signInWithGoogle(googleAuthCredential: AuthCredential): LiveData<Resource<Account>>
    fun signInWithFacebook(facebookAuthCredential: FacebookAuthCredential): LiveData<Resource<Account>>

    fun signUpWithEmail(email: String, password: String): LiveData<Resource<Account>>
    fun signUpWithGoogle(googleAuthCredential: AuthCredential): LiveData<Resource<Account>>
    fun signUpWithFacebook(facebookAuthCredential: FacebookAuthCredential): LiveData<Resource<Account>>

    fun getInfoAccount(): LiveData<Resource<Account>>
    fun getInfoTrashureAccount(): LiveData<Resource<TrashureAccount>>


}