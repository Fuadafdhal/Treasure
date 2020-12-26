package com.afdhal_fa.treasure.core.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.vo.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber


object FirestoreRepositoryAccount {
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

    fun createTreasureUserInFirestoreIfNotExists(uId: String): LiveData<Resource<TreasureUser>> =
        MutableLiveData<Resource<TreasureUser>>().apply {
            val mTreasureUser = TreasureUser(1, 0, 0, uId)
            treasureUserRef.add(mTreasureUser).addOnCompleteListener {
                if (it.isSuccessful) {
                    mTreasureUser.id = it.result!!.id
                    postValue(Resource.Success(mTreasureUser))
                } else {
                    postValue(Resource.Error(it.exception?.message.toString()))
                }
            }
        }

    fun viewTreasureUserInFirestore(userid: String): LiveData<Resource<TreasureUser>> {
        return MutableLiveData<Resource<TreasureUser>>().apply {
            postValue(Resource.Loading())
            treasureUserRef
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (i in task.result?.documents!!) {
                            val mTreasureUser = i.toObject(TreasureUser::class.java) as TreasureUser
                            if (mTreasureUser.uid.equals(userid)) {
                                mTreasureUser.id = i.id
                                postValue(Resource.Success(mTreasureUser))
                            } else {
                                postValue(Resource.Error("Dompet Treasure Anda Tidak Ditemukan"))
                            }
                        }
                    } else {
                        postValue(Resource.Error(task.exception!!.message.toString()))
                    }
                }
        }
    }

    fun updateTreasureUserInFirestore(mTreasureUser: TreasureUser): LiveData<Resource<TreasureUser>> {
        return MutableLiveData<Resource<TreasureUser>>().apply {
            treasureUserRef.document(mTreasureUser.id)
                .update("saldo", mTreasureUser.saldo)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        postValue(Resource.Success(mTreasureUser))
                    } else {
                        Timber.e("Erorr : ${it.exception?.message}")
                        postValue(Resource.Error(it.exception?.message.toString()))
                    }
                }
        }
    }

    fun viewUserInFirestore(uid: String): LiveData<Resource<User>> {
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

    fun updateDataProfileInFirestore(
        uid: String,
        image: String,
        name: String,
        phoneNumber: String,
        birtday: String
    ) = MutableLiveData<Resource<User>>().apply {
        val uidRef: DocumentReference = usersRef.document(uid)
        val mHashMapUser = HashMap<String, Any>().apply {
            put("image", image)
            put("name", name)
            put("phoneNumber", phoneNumber)
            put("birtdayDate", birtday)
        }

        uidRef.get().addOnCompleteListener { uidTask: Task<DocumentSnapshot?> ->
            if (uidTask.isSuccessful) {
                val document = uidTask.result
                if (document?.exists()!!) {
                    uidRef.update(mHashMapUser)
                        .addOnCompleteListener { userCreationTask: Task<Void?> ->
                            if (userCreationTask.isSuccessful) {
                                postValue(Resource.Success(User()))
                            } else {
                                postValue(Resource.Error(uidTask.exception!!.message.toString()))
                            }
                        }
                } else {
                    postValue(Resource.Error(uidTask.exception!!.message.toString()))
                }
            } else {
                postValue(Resource.Error(uidTask.exception!!.message.toString()))
            }
        }
    }
}


