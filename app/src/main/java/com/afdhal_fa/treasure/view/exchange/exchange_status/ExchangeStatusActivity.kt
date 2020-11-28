package com.afdhal_fa.treasure.view.exchange.exchange_status

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.afdhal_fa.treasure.MainActivity
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Exchange
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
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
            intent.getParcelableExtra<Exchange>(ExchangeFragment.INTENT_EXTRA_EXCHANGE) as Exchange

        viewTreasureUser(mExchange)



        binding.buttonClose.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
            ).also { finish() }
        }
    }

    private fun viewTreasureUser(mExchange: Exchange) {
        viewmodel.getTreasureUser(mExchange.uid).observe(this, {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.buttonClose.visibility = View.INVISIBLE
                    binding.textSubTitile.text = getString(R.string.text_loading)
                    binding.imageStatus.visibility = View.INVISIBLE
                    binding.textTitleStatus.visibility = View.INVISIBLE
                }

                is Resource.Success -> {
                    if (it.data != null) {
                        if (it.data.saldo >= mExchange.totalNominal) {
                            createExchange(mExchange, it.data)
                        } else {
                            binding.imageStatus.load(R.drawable.ic_error)
                            binding.textTitleStatus.text = getString(R.string.text_failed)
                            binding.textSubTitile.text =
                                String.format(
                                    "Saldo tidak cukup sisa saldo %s",
                                    it.data.saldo.toInt().toRupiah()
                                )
                            binding.progressBar.visibility = View.GONE
                            binding.buttonClose.visibility = View.VISIBLE
                            binding.imageStatus.visibility = View.VISIBLE
                            binding.textSubTitile.visibility = View.VISIBLE
                            binding.textTitleStatus.visibility = View.VISIBLE
                        }
                    }
                }

                is Resource.Error -> {
                    binding.imageStatus.load(R.drawable.ic_error)
                    binding.textTitleStatus.text = getString(R.string.text_failed)
                    binding.textSubTitile.text = it.message
                    binding.progressBar.visibility = View.GONE
                    binding.buttonClose.visibility = View.VISIBLE
                    binding.imageStatus.visibility = View.VISIBLE
                    binding.textSubTitile.visibility = View.VISIBLE
                    binding.textTitleStatus.visibility = View.VISIBLE
                }
            }
        })
    }


    private fun createExchange(mExchange: Exchange, mTreasureUser: TreasureUser) {
        viewmodel.createExchange(mExchange).observe(this, {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        updateTreasureUser(mExchange, mTreasureUser)
                    }
                }
                is Resource.Error -> {
                    binding.imageStatus.load(R.drawable.ic_error)
                    binding.textTitleStatus.text = getString(R.string.text_failed)
                    binding.textSubTitile.text = it.message
                    binding.progressBar.visibility = View.GONE
                    binding.buttonClose.visibility = View.VISIBLE
                    binding.imageStatus.visibility = View.VISIBLE
                    binding.textSubTitile.visibility = View.VISIBLE
                    binding.textTitleStatus.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun updateTreasureUser(mExchange: Exchange, mTreasureUser: TreasureUser) {
        viewmodel.updateTreasureUser(mTreasureUser, mExchange.totalNominal)
            .observe(this, {
                when (it) {
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonClose.visibility = View.VISIBLE
                        binding.imageStatus.visibility = View.VISIBLE
                        binding.textSubTitile.visibility = View.VISIBLE
                        binding.textTitleStatus.visibility = View.VISIBLE
                        binding.textSubTitile.text =
                            String.format(
                                "Terima kasih telah melakukan penukaran saldo\nSisa saldo anda %s",
                                it.data!!.saldo.toInt().toRupiah()
                            )
                    }
                    is Resource.Error -> {
                        binding.imageStatus.load(R.drawable.ic_error)
                        binding.textTitleStatus.text = getString(R.string.text_failed)
                        binding.textSubTitile.text = it.message
                        binding.progressBar.visibility = View.GONE
                        binding.buttonClose.visibility = View.VISIBLE
                        binding.imageStatus.visibility = View.VISIBLE
                        binding.textSubTitile.visibility = View.VISIBLE
                        binding.textTitleStatus.visibility = View.VISIBLE
                    }
                }
            })
    }

    override fun initViewModel(): ExchangeStatusViewModel {
        return ViewModelProvider(this)[ExchangeStatusViewModel::class.java]
    }
}