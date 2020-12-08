package com.afdhal_fa.treasure.view.home.notification

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Exchange
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
import com.afdhal_fa.treasure.core.utils.BaseToolbarActivity
import com.afdhal_fa.treasure.core.utils.setMarginStart
import com.afdhal_fa.treasure.core.utils.toRupiah
import com.afdhal_fa.treasure.databinding.ActivityDetailNotificationBinding

class DetailNotificationActivity : BaseToolbarActivity<DetailNotificationViewModel>() {
    companion object {
        const val EXTRA_INTENT_TREASURE_USER = "extra_treasure_user"
        const val EXTRA_INTENT_TRANSACTION = "extra_transaction"
    }

    private lateinit var binding: ActivityDetailNotificationBinding
    private lateinit var mTreasureUser: TreasureUser
    private lateinit var mExchange: Exchange

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mTreasureUser = intent.getParcelableExtra<TreasureUser>(EXTRA_INTENT_TREASURE_USER)!!
        mExchange = intent.getParcelableExtra<Exchange>(EXTRA_INTENT_TRANSACTION)!!

        binding.textTitle.text = String.format("Penukaran %s", mExchange.type)
        binding.textSubTitile.text =
            when (mExchange.type) {
                "Pulsa" -> {
                    String.format(
                        "Pulsa sebesar %s dengan nomor kartu %s %s berhasil ditukankan." +
                                " Sisa saldo anda sebesar %s",
                        mExchange.nominal,
                        mExchange.serviceType,
                        mExchange.phoneNumber,
                        mTreasureUser.saldo.toInt().toRupiah()
                    )
                }

                "Link Aja", "Dana" -> {
                    String.format(
                        "Saldo %s sebesar %S berhasil ditukarkan." +
                                " Sisa saldo anda sebesar %s",
                        mExchange.type,
                        mExchange.nominal,
                        mExchange.phoneNumber,
                        mTreasureUser.saldo.toInt().toRupiah()

                    )
                }
                else -> {
                    String.format(
                        "Pulsa sebesar %s dengan nomor kartu %s %s berhasil ditukankan." +
                                " Sisa saldo anda sebesar %s",
                        mExchange.nominal,
                        mExchange.serviceType,
                        mExchange.phoneNumber,
                        mTreasureUser.saldo.toInt().toRupiah()
                    )
                }
            }
    }

    override fun setToolbar(): Toolbar {
        binding.appbarLayout.textTitleToolbar.text =
            String.format(getString(R.string.title_detail_transaction), mExchange.type)
        binding.appbarLayout.textTitleToolbar.setMarginStart(72)
        return binding.appbarLayout.toolbar
    }

    override fun setToolbarButtonBack() = true

    override fun initViewModel() =
        ViewModelProvider(this)[DetailNotificationViewModel::class.java]
}