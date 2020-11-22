package com.afdhal_fa.treasure.core.network

import androidx.lifecycle.MutableLiveData
import com.afdhal_fa.treasure.core.domain.model.Price
import com.afdhal_fa.treasure.core.vo.Resource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


object FirestoreRepositoryPrice {
    private val rootRef = FirebaseFirestore.getInstance()
    private val pricesRef: CollectionReference =
        rootRef.collection(FirestoreContact.PRICE_TREASURE_CLOUD)

    fun viewPriceInFirestore() = MutableLiveData<Resource<MutableList<Price>>>().apply {
        val dataArray = ArrayList<Price>()
        pricesRef
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (i in task.result?.documents!!) {
                        val mTreasureUser = i.toObject(Price::class.java)
                        if (mTreasureUser != null) {
                            dataArray.add(mTreasureUser)
                        }
                    }
                    postValue(Resource.Success(dataArray))
                } else {
                    postValue(Resource.Error(task.exception!!.message.toString()))
                }
            }
    }

}


