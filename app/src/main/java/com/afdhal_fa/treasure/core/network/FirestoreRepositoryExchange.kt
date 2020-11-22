package com.afdhal_fa.treasure.core.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.afdhal_fa.treasure.core.domain.model.Exchange
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
import com.afdhal_fa.treasure.core.vo.Resource
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


object FirestoreRepositoryExchange {
    private val rootRef = FirebaseFirestore.getInstance()

    private val exchangeRef: CollectionReference =
        rootRef.collection(FirestoreContact.EXCHANEG_TREASURE_CLOUD)

    private val treasureUserRef: CollectionReference =
        rootRef.collection(FirestoreContact.TREASURE_USER_CLOUD)

    fun exchange(mExchange: Exchange): LiveData<Resource<TreasureUser>> =
        MutableLiveData<Resource<TreasureUser>>().apply {
            val exchange = Exchange(
                mExchange.type,
                mExchange.serviceType,
                mExchange.phoneNumber,
                mExchange.nominal,
                mExchange.totalNominal,
                mExchange.uId
            )
            exchangeRef
                .add(exchange)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        treasureUserRef.get()
                            .addOnCompleteListener { taskTreasure ->
                                if (taskTreasure.isSuccessful) {
                                    for (i in taskTreasure.result?.documents!!) {
                                        val mTreasureUser =
                                            i.toObject(TreasureUser::class.java) as TreasureUser
                                        if (mTreasureUser.uid.equals(mExchange.uId)) {

                                            val idConlleciton = i.id
                                            val saldoUpdate =
                                                mTreasureUser.saldo.minus(mExchange.totalNominal)
                                            mTreasureUser.saldo = saldoUpdate

                                            treasureUserRef.document(idConlleciton)
                                                .update("saldo", saldoUpdate)
                                                .addOnCompleteListener {
                                                    if (it.isSuccessful) {
                                                        postValue(Resource.Success(mTreasureUser))
                                                    } else {
                                                        println("Erorr : ${it.exception?.message}")
                                                    }
                                                }
                                        } else {
                                            postValue(Resource.Error("not same"))
                                        }
                                    }
                                } else {
                                    postValue(Resource.Error(taskTreasure.exception!!.message.toString()))
                                    println(taskTreasure.exception?.message)

                                }
                            }


                    } else {
                        postValue(Resource.Error(it.exception?.message.toString()))
                    }
                }

        }
}


