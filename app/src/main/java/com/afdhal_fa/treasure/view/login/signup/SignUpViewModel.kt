package com.afdhal_fa.treasure.view.login.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.utils.LoginValidate

class SignUpViewModel : ViewModel() {
    private val _validate: MutableLiveData<Resource<Validate>> = MutableLiveData()

    var validate: LiveData<Resource<Validate>> = _validate

    fun validate(
        textName: String,
        textEmail: String,
        textPhone: String,
        textPassword: String
    ): Boolean {

        if (textName.isEmpty()) {
            _validate.value =
                Resource.Error("empty", Validate(textName = "Nama tidak boleh kosong"))
        }

        if (textEmail.isEmpty()) {
            _validate.value =
                Resource.Error("empty", Validate(textEmail = "Email tidak boleh kosong"))
        }

        if (!LoginValidate.isValidEmail(textEmail)) {
            _validate.value = Resource.Error("error", Validate(textEmail = "Email tidak benar"))
        }

        if (textPhone.isEmpty()) {
            _validate.value =
                Resource.Error("empty", Validate(textPhone = "Nomor HP tidak boleh kosong"))
        }

        if (textPassword.isEmpty()) {
            _validate.value =
                Resource.Error("empty", Validate(textPassword = "Password tidak boleh kosong"))
        }

        if (!LoginValidate.isValidPassoword(textPassword)) {
            _validate.value =
                Resource.Error(
                    "error",
                    Validate(textPassword = "Password harus memiliki 8 karakter")
                )
        }

        if (
            textName.isEmpty() and textEmail.isEmpty() and textPhone.isEmpty() and textPassword.isEmpty() and
            !LoginValidate.isValidEmail(textEmail) and
            !LoginValidate.isValidPassoword(textPassword)
        ) return false

        return true
    }

}

data class Validate(
    var textName: String? = null,
    var textEmail: String? = null,
    var textPhone: String? = null,
    var textPassword: String? = null
)
