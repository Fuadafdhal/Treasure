package com.afdhal_fa.treasure.view.exchange_status

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.afdhal_fa.treasure.core.domain.model.Exchange
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
import com.afdhal_fa.treasure.core.network.FirestoreRepositoryExchange
import com.afdhal_fa.treasure.core.vo.Resource

class ExchangeStatusViewModel : ViewModel() {

    fun exchange(mExchange: Exchange): LiveData<Resource<TreasureUser>> =
        FirestoreRepositoryExchange.exchange(mExchange)
}
