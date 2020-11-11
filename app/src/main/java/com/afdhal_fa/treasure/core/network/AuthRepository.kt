package com.afdhal_fa.treasure.core.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.User
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import timber.log.Timber

object AuthRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun signUpWithEmail(
        name: String, email: String, phone: String, password: String
    ): LiveData<Resource<User>> {
        val result: MutableLiveData<Resource<User>> = MutableLiveData()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val isNewUser = authTask.result?.additionalUserInfo?.isNewUser!!
                    val fUser = authTask.result!!.user
                    val account = User(
                        uid = fUser?.uid.toString(),
                        name = name,
                        email = email,
                        phoneNumber = phone,
                        birtdayDate = "",
                        image = "default"
                    )
                    account.isNew = isNewUser
                    result.value = Resource.Success(account)
                } else {
                    Timber.d(authTask.exception?.message.toString())
                    result.value = Resource.Error(authTask.exception?.message.toString(), null)
                }
            }
        return result
    }

    fun signInWithGoogle(googleAuthCredential: AuthCredential): LiveData<Resource<User>> {
        val result: MutableLiveData<Resource<User>> = MutableLiveData()
        result.value = Resource.Loading()
        firebaseAuth.signInWithCredential(googleAuthCredential)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val isNewUser = authTask.result?.additionalUserInfo?.isNewUser!!
                    val fUser = authTask.result?.user
                    if (fUser != null) {
                        val uid = fUser.uid
                        val name = fUser.displayName.toString()
                        val email = fUser.email.toString()
                        val image = fUser.photoUrl.toString()
                        val account = User(
                            uid = uid,
                            name = name,
                            email = email,
                            phoneNumber = "",
                            birtdayDate = "",
                            image = image
                        )
                        account.isNew = isNewUser
                        result.value = Resource.Success(account)
                    }
                } else {
                    result.value = Resource.Error(authTask.exception?.message.toString(), null)
                }
            }
        return result
    }

    fun signInWithFacebook(credential: AuthCredential): MutableLiveData<Resource<User>> {
        val result: MutableLiveData<Resource<User>> = MutableLiveData()

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {
                    val isNewUser = authTask.result?.additionalUserInfo?.isNewUser!!
                    val fUser = authTask.result?.user
                    if (fUser != null) {
                        val uid = fUser.uid
                        val name = fUser.displayName.toString()
                        val email = fUser.email.toString()
                        val image = fUser.photoUrl.toString()
                        val phoneNumber = fUser.phoneNumber.toString()
                        val account = User(
                            uid = uid,
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
                    result.value = Resource.Error(authTask.exception?.message.toString(), null)
                }
            }
        return result
    }


    fun signInWithEmail(
        email: String,
        password: String
    ): LiveData<Resource<User>> {
        val result: MutableLiveData<Resource<User>> = MutableLiveData()
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { authTask ->
            if (authTask.isSuccessful) {
                val account = User()
                account.isAuthenticated = authTask.result?.user != null
                result.value = Resource.Success(account)
            } else {
                result.value = Resource.Error(authTask.exception?.message.toString(), null)
            }
        }
        return result
    }
}
