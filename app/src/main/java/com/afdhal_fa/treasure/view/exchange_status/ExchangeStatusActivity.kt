package com.afdhal_fa.treasure.view.exchange_status

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.afdhal_fa.treasure.MainActivity
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Exchange
import com.afdhal_fa.treasure.core.utils.BaseActivity
import com.afdhal_fa.treasure.core.utils.toRupiah
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
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.buttonClose.visibility = View.INVISIBLE
                    binding.textSubTitile.text = getString(R.string.text_loading)
                    binding.imageStatus.visibility = View.INVISIBLE
                    binding.textTitleStatus.visibility = View.INVISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.buttonClose.visibility = View.VISIBLE
                    binding.imageStatus.visibility = View.VISIBLE
                    binding.textSubTitile.visibility = View.VISIBLE
                    binding.textTitleStatus.visibility = View.VISIBLE
                    binding.textSubTitile.text =
                        String.format(
                            "Terima kasih telah melakukan penukaran saldo\nSisa saldo anda %s",
                            it.data?.saldo!!.toInt().toRupiah()
                        )
                }
                is Resource.Error -> {
                    binding.imageStatus.load(R.drawable.ic_error)
                    binding.textTitleStatus.text = getString(R.string.text_failed)
                    binding.textSubTitile.text =
                        String.format("${it.message} %s", it.data?.saldo!!.toInt().toRupiah())

                    binding.progressBar.visibility = View.GONE
                    binding.buttonClose.visibility = View.VISIBLE
                    binding.imageStatus.visibility = View.VISIBLE
                    binding.textSubTitile.visibility = View.VISIBLE
                    binding.textTitleStatus.visibility = View.VISIBLE
                }
            }
        })

        binding.buttonClose.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
            ).also { finish() }
        }
    }

    override fun initViewModel(): ExchangeStatusViewModel {
        return ViewModelProvider(this)[ExchangeStatusViewModel::class.java]
    }
}