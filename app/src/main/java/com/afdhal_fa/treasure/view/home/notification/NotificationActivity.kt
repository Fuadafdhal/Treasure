package com.afdhal_fa.treasure.view.home.notification

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.utils.BaseToolbarActivity
import com.afdhal_fa.treasure.core.utils.VPager
import com.afdhal_fa.treasure.core.utils.VPagerAdapater
import com.afdhal_fa.treasure.core.utils.setMarginStart
import com.afdhal_fa.treasure.databinding.ActivityNotificationBinding
import com.afdhal_fa.treasure.view.home.notification.more.MoreNotificationFragment
import com.afdhal_fa.treasure.view.home.notification.transacion.TransactionFragment

class NotificationActivity : BaseToolbarActivity<NotificationViewModel>() {
    private lateinit var binding: ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pages = ArrayList<VPager>()
        pages.add(VPager("Transaksi", TransactionFragment()))
        pages.add(VPager("Lainnya", MoreNotificationFragment()))
        val vPagerAdapater = VPagerAdapater(pages, supportFragmentManager)
        vPagerAdapater.setData("transaction", "more")
        binding.pager.adapter = vPagerAdapater
        binding.tabs.setupWithViewPager(binding.pager)
    }

    override fun setToolbar(): Toolbar {
        binding.includeAppbar.textTitleToolbar.text = getString(R.string.title_notification)
        binding.includeAppbar.textTitleToolbar.setMarginStart(72)
        return binding.includeAppbar.toolbar
    }

    override fun setToolbarButtonBack() = true

    override fun initViewModel() =
        ViewModelProvider(this)[NotificationViewModel::class.java]
}