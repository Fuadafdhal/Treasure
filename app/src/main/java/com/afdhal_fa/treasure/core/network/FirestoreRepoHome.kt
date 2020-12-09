package com.afdhal_fa.treasure.core.network

import androidx.lifecycle.MutableLiveData
import com.afdhal_fa.treasure.core.domain.model.Deposit
import com.afdhal_fa.treasure.core.domain.model.Tips
import com.afdhal_fa.treasure.core.vo.Resource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

object FirestoreRepoHome {
    private val rootRef = FirebaseFirestore.getInstance()
    private val depositRef: CollectionReference =
        rootRef.collection(FirestoreContact.DEPOSITE_TREASURE_CLOUD)
    private val tipsRef: CollectionReference =
        rootRef.collection(FirestoreContact.TIPS_TREASURE_CLOUD)

    fun viewTipsInFirestore() = MutableLiveData<Resource<MutableList<Tips>>>().apply {
        val dataArray = ArrayList<Tips>()
        tipsRef.get().addOnCompleteListener { taskQuery ->
            if (taskQuery.isSuccessful) {
                for (i in taskQuery.result!!.documents) {
                    val document = i.toObject(Tips::class.java)
                    document?.id = i.id
                    Timber.d(document.toString())
                    if (document != null) {
                        dataArray.add(document)
                    }
                }
                postValue(Resource.Success(dataArray))
            } else {
                postValue(Resource.Error(taskQuery.exception?.message.toString()))
            }
        }
    }

    fun viewDepositInFirestore(uid: String) =
        MutableLiveData<Resource<MutableList<Deposit>>>().apply {
            val dataArray = ArrayList<Deposit>()
            depositRef.get().addOnCompleteListener { taskQuery ->
                if (taskQuery.isSuccessful) {
                    for (i in taskQuery.result!!.documents) {
                        val document = i.toObject(Deposit::class.java)
                        document?.id = i.id
                        if (document != null) if (document.uid == uid) dataArray.add(document)
                    }
                    postValue(Resource.Success(dataArray))
                } else {
                    postValue(Resource.Error(taskQuery.exception?.message.toString()))
                }
            }
        }


}