package com.afdhal_fa.treasure.view.exchange

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.ExchangeMetode
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.network.AuthRepository
import com.afdhal_fa.treasure.core.vo.Resource
import java.util.*

class ExchangeViewModel : ViewModel() {
    fun exchangeMetode(): LiveData<MutableList<ExchangeMetode>> =
        MutableLiveData<MutableList<ExchangeMetode>>().apply {

            val listData = ArrayList<ExchangeMetode>()

            val mExchangeMetode0 = ExchangeMetode(R.drawable.ic_ponsel, "Pulsa")
            mExchangeMetode0.position = 0

            val mExchangeMetode1 = ExchangeMetode(R.drawable.logo_linkaja, "Link Aja")
            mExchangeMetode0.position = 0

            val mExchangeMetode2 = ExchangeMetode(R.drawable.logo_dana, "Dana")

            mExchangeMetode0.position = 0


            listData.add(mExchangeMetode0)
            listData.add(mExchangeMetode1)
            listData.add(mExchangeMetode2)

            postValue(listData)
        }

    fun checkIfUserIsAuthenticated(): LiveData<Resource<User>> =
        AuthRepository.checkIfUserIsAuthenticatedInFirebase()
}