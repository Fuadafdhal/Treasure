package com.afdhal_fa.treasure.view.price

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.domain.model.Price
import com.afdhal_fa.treasure.core.network.FirestoreRepositoryPrice
import com.afdhal_fa.treasure.core.vo.Resource

class PriceViewModel : ViewModel() {

    fun viewPriceList(): LiveData<Resource<MutableList<Price>>> =
        FirestoreRepositoryPrice.viewPriceInFirestore()
}