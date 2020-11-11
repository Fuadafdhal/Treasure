package com.afdhal_fa.treasure.core.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

object FirestoreRepository {
    private val rootRef = FirebaseFirestore.getInstance()
    private val usersRef: CollectionReference = rootRef.collection(FirestoreContact.ACCOUNT_CLOUD)
//    private val Ref: CollectionReference = rootRef.collection(FirestoreContact.ACCOUNT_CLOUD)

    fun createUserInFirestoreIfNotExists(mUser: User): LiveData<Resource<User>> {
        val result: MutableLiveData<Resource<User>> = MutableLiveData()

        val uidRef: DocumentReference = usersRef.document(mUser.uid)

        uidRef.get().addOnCompleteListener { uidTask: Task<DocumentSnapshot?> ->
            if (uidTask.isSuccessful) {
                val document = uidTask.result
                if (!document?.exists()!!) {
                    uidRef.set(mUser)
                        .addOnCompleteListener { userCreationTask: Task<Void?> ->
                            if (userCreationTask.isSuccessful) {
                                mUser.isCreated = true
                                result.setValue(Resource.Success(mUser))
                            } else {
                                result.setValue(Resource.Error(uidTask.exception!!.message.toString()))
                            }
                        }
                } else {
                    result.setValue(Resource.Success(mUser))
                }
            } else {
                result.setValue(Resource.Error(uidTask.exception!!.message.toString()))
            }
        }
        return result
    }
}