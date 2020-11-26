package com.afdhal_fa.treasure.core.network

import androidx.lifecycle.MutableLiveData
import com.afdhal_fa.treasure.core.domain.model.Tips
import com.afdhal_fa.treasure.core.vo.Resource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

object FirestoreRepoHome {
    private val rootRef = FirebaseFirestore.getInstance()
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
                    println(document.toString())
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
}