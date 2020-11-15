package com.afdhal_fa.treasure.core.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.afdhal_fa.treasure.core.data.Resource
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
import com.afdhal_fa.treasure.core.domain.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


object FirestoreRepository {
    private val rootRef = FirebaseFirestore.getInstance()
    private val usersRef: CollectionReference = rootRef.collection(FirestoreContact.ACCOUNT_CLOUD)
    private val treasureUserRef: CollectionReference =
        rootRef.collection(FirestoreContact.TREASURE_USER_CLOUD)

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


    fun viewTreasureUser(userid: String): LiveData<Resource<TreasureUser>> {
        return MutableLiveData<Resource<TreasureUser>>().apply {
            treasureUserRef
                .get()
                .addOnCompleteListener { uidTask ->
                    if (uidTask.isSuccessful) {
                        for (i in uidTask.result?.documents!!) {
                            val mTreasureUser = i.toObject(TreasureUser::class.java) as TreasureUser
                            if (mTreasureUser.uid.equals(userid)) {
                                postValue(Resource.Success(mTreasureUser))
                            } else {
                                postValue(Resource.Error("not same"))
                            }
                        }
                    } else {
                        postValue(Resource.Error(uidTask.exception!!.message.toString()))
                    }
                }
        }
    }

    fun viewUser(uid: String): LiveData<Resource<User>> {
        return MutableLiveData<Resource<User>>().apply {
            val uidRef: DocumentReference = usersRef.document(uid)
            uidRef.get().addOnCompleteListener { uidTask: Task<DocumentSnapshot?> ->
                if (uidTask.isSuccessful) {
                    val document = uidTask.result
                    if (document?.exists()!!) {
                        val mUser = document.toObject(User::class.java) as User
                        postValue(Resource.Success(mUser))
                    } else {
                        postValue(Resource.Error("Emty Data"))
                    }
                } else {
                    postValue(Resource.Error(uidTask.exception!!.message.toString()))
                }
            }
        }
    }

}