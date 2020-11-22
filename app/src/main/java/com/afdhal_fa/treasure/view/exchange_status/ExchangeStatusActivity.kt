package com.afdhal_fa.treasure.view.exchange_status

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.afdhal_fa.treasure.core.domain.model.Exchange
import com.afdhal_fa.treasure.core.utils.BaseActivity
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.core.vo.Resource
import com.afdhal_fa.treasure.databinding.ActivityExchangeStatusBinding
import com.afdhal_fa.treasure.view.exchange.ExchangeFragment

class ExchangeStatusActivity : BaseActivity<ExchangeStatusViewModel>() {

    private lateinit var binding: ActivityExchangeStatusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mExchange =
            intent.getParcelableExtra<Exchange>(ExchangeFragment.INTENT_EXTRA_EXCHANGE)

        println(mExchange)

        viewmodel.exchange(mExchange!!).observe(this, {
            when (it) {
                is Resource.Success -> {
                    makeToast("Success")
                }
                is Resource.Error -> {
                    makeToast("Error : ${it.message}")
                }
            }
        })
    }

    override fun initViewModel(): ExchangeStatusViewModel {
        return ViewModelProvider(this)[ExchangeStatusViewModel::class.java]
    }
}