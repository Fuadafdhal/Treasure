package com.afdhal_fa.treasure.view.login.forgoten

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.network.AuthRepository
import com.afdhal_fa.treasure.core.vo.Resource

class ForgotenViewModel : ViewModel() {
    fun forgotenPasswordWithEmail(email: String): LiveData<Resource<User>> =
        AuthRepository.forgotenPasswordWithEmail(email)
}
