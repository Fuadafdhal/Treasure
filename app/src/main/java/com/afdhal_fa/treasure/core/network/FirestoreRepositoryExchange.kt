package com.afdhal_fa.treasure.core.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.afdhal_fa.treasure.core.domain.model.Exchange
import com.afdhal_fa.treasure.core.vo.Resource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber


object FirestoreRepositoryExchange {
    private val rootRef = FirebaseFirestore.getInstance()

    private val exchangeRef: CollectionReference =
        rootRef.collection(FirestoreContact.EXCHANEG_TREASURE_CLOUD)

    fun createExchangeInFirestore(mExchange: Exchange): LiveData<Resource<Exchange>> =
        MutableLiveData<Resource<Exchange>>().apply {
            exchangeRef
                .add(mExchange)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        mExchange.id = it.result!!.id
                        postValue(Resource.Success(mExchange))
                    } else {
                        postValue(Resource.Error(it.exception?.message.toString()))
                    }
                }
        }

    fun viewExchangeInFirestore(uid: String): LiveData<Resource<MutableList<Exchange>>> =
        MutableLiveData<Resource<MutableList<Exchange>>>().apply {
            val dataArray = ArrayList<Exchange>()
            exchangeRef
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        for (i in it.result!!.documents) {
                            val mExchange = i.toObject(Exchange::class.java)
                            if (mExchange != null) {
                                if (mExchange.uid == uid) {
                                    Timber.d(mExchange.toString())
                                    dataArray.add(mExchange)
                                }
                            }
                        }
                        postValue(Resource.Success(dataArray))
                    } else {
                        postValue(Resource.Error(it.exception?.message.toString()))
                    }
                }

        }
}


