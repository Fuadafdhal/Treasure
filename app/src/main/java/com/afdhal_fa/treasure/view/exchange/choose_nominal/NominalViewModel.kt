package com.afdhal_fa.treasure.view.exchange.choose_nominal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.domain.model.Nominal
import java.util.*

class NominalViewModel : ViewModel() {
    fun getNominal(): LiveData<MutableList<Nominal>> =
        MutableLiveData<MutableList<Nominal>>().apply {
            val items = ArrayList<Nominal>()
            items.add(Nominal(1000, 1500))
            items.add(Nominal(5000, 6000))
            items.add(Nominal(10000, 11000))
            postValue(items)
        }
}
