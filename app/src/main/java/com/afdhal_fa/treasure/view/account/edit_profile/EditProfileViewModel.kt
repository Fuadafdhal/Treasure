package com.afdhal_fa.treasure.view.account.edit_profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.network.FirestoreRepositoryAccount

class EditProfileViewModel : ViewModel() {
    fun updatePtofile(
        uid: String,
        image: String,
        name: String,
        phoneNumber: String,
        birtday: String
    ): LiveData<Resource<User>> =
        FirestoreRepositoryAccount.updateDataProfileInFirestore(
            uid,
            image,
            name,
            phoneNumber,
            birtday
        )

}
