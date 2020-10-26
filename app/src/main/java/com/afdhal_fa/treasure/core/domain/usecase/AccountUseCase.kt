package com.afdhal_fa.treasure.core.domain.usecase

import androidx.lifecycle.LiveData
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.Account
import com.afdhal_fa.treasure.core.domain.model.TrashureAccount
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthCredential

interface AccountUseCase {
    fun getInfoAccount(): LiveData<Resource<Account>>
    fun getInfoTrashureAccount(): LiveData<Resource<TrashureAccount>>
    fun signInWithGoogle(googleAuthCredential: AuthCredential)
    fun signInWithEmail(email: String, password: String)
    fun signInWithFacebook(facebookAuthCredential: FacebookAuthCredential)
}