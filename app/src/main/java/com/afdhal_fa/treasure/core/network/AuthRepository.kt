package com.afdhal_fa.treasure.core.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.Account
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth

object AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential): MutableLiveData<Resource<Account>> {
        val result: MutableLiveData<Resource<Account>> = MutableLiveData()
        result.value = Resource.Loading()
        firebaseAuth.signInWithCredential(googleAuthCredential)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val isNewUser = authTask.result?.additionalUserInfo?.isNewUser!!
                    val firebaseUser = authTask.result?.user
                    if (firebaseUser != null) {
                        val uid = firebaseUser.uid
                        val name = firebaseUser.displayName.toString()
                        val email = firebaseUser.email.toString()
                        val image = firebaseUser.photoUrl.toString()
                        val phoneNumber = firebaseUser.phoneNumber.toString()
                        val account = Account(
                            userId = uid,
                            name = name,
                            email = email,
                            phoneNumber = phoneNumber,
                            birtdayDate = "",
                            image = image
                        )
                        account.isNew = isNewUser
                        result.value = Resource.Success(account)
                    }
                } else {
                    result.value = Resource.Error("Error", null)
                }
            }
        return result
    }

    fun firebaseSignInWithEmail(
        email: String,
        password: String
    ): MutableLiveData<Resource<Account>> {
//        val result: MutableLiveData<Resource<Account>> = MutableLiveData()
        TODO("use $email and $password")
    }

    fun firebaseSignInWithFacebook(authCredential: AuthCredential): MutableLiveData<Resource<Account>> {
        val result: MutableLiveData<Resource<Account>> = MutableLiveData()
        result.postValue(Resource.Loading())
        FirebaseAuth.getInstance().signInWithCredential(authCredential)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val isNewUser = authTask.result?.additionalUserInfo?.isNewUser!!
                    val firebaseUser = authTask.result?.user
                    if (firebaseUser != null) {
                        val uid = firebaseUser.uid
                        val name = firebaseUser.displayName.toString()
                        val email = firebaseUser.email.toString()
                        val image = firebaseUser.photoUrl.toString()
                        val phoneNumber = firebaseUser.phoneNumber.toString()
                        val account = Account(
                            userId = uid,
                            name = name,
                            email = email,
                            phoneNumber = phoneNumber,
                            birtdayDate = "",
                            image = image
                        )
                        account.isNew = isNewUser
                        result.value = Resource.Success(account)
                    }
                } else {
                    result.value = Resource.Error("Error", null)
                }
            }

        return result
    }

    fun firebaseAuthInfo(): LiveData<Boolean> {
        val result: MutableLiveData<Boolean> = MutableLiveData()
        result.value = firebaseAuth.currentUser != null
        return result
    }
}