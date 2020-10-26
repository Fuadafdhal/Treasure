package com.afdhal_fa.treasure.core.domain.usecase

import androidx.lifecycle.LiveData
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.Account
import com.afdhal_fa.treasure.core.domain.model.TrashureAccount
import com.afdhal_fa.treasure.core.domain.repository.IAccountRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthCredential

class AccountInteractor(private val accountRepository: IAccountRepository) : AccountUseCase {
    override fun getInfoAccount(): LiveData<Resource<Account>> {
        TODO("Not yet implemented")
    }

    override fun getInfoTrashureAccount(): LiveData<Resource<TrashureAccount>> {
        TODO("Not yet implemented")
    }

    override fun signInWithGoogle(googleAuthCredential: AuthCredential) {
        accountRepository.signInWithGoogle(googleAuthCredential)
    }

    override fun signInWithEmail(email: String, password: String) {
        accountRepository.signInWithEmail(email, password)
    }

    override fun signInWithFacebook(facebookAuthCredential: FacebookAuthCredential) {
        accountRepository.signInWithFacebook(facebookAuthCredential)
    }

}