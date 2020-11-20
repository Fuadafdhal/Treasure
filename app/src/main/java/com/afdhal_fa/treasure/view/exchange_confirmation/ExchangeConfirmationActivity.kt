package com.afdhal_fa.treasure.view.exchange_confirmation

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Exchange
import com.afdhal_fa.treasure.core.utils.BaseToolbarActivity
import com.afdhal_fa.treasure.core.utils.toRupiah
import com.afdhal_fa.treasure.databinding.ActivityExchangeConfirmationBinding
import com.afdhal_fa.treasure.view.exchange.ExchangeFragment

class ExchangeConfirmationActivity : BaseToolbarActivity<ConfirmationViewModel>() {

    private lateinit var binding: ActivityExchangeConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExchangeConfirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent =
            intent.getParcelableExtra<Exchange>(ExchangeFragment.INTENT_RESUTL_EXTRA_POSITION)

        intent?.let {

            binding.imageExchangeType.load(
                when (it.type) {
                    "Pulsa" -> {
                        R.drawable.ic_ponsel
                    }

                    "Link Aja" -> {
                        R.drawable.logo_linkaja
                    }

                    "Dana" -> {
                        R.drawable.logo_dana
                    }
                    else -> {
                        R.drawable.ic_ponsel
                    }
                }
            )

            binding.textTitleExchangeType.text = it.type
            binding.textTitleNominalType.text = it.type

            binding.textServiceType.text = it.serviceType
            binding.textPhone.text = it.phoneNumber
            binding.textNominalType.text = it.nominal.toRupiah()
            binding.textPrice.text = it.totalNominal.toRupiah()
        }

    }

    override fun setToolbar(): Toolbar {
        return binding.includeAppbar.toolbar
    }

    override fun initViewModel(): ConfirmationViewModel {
        return ViewModelProvider(this)[ConfirmationViewModel::class.java]
    }

}