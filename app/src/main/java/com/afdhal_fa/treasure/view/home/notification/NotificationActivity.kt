package com.afdhal_fa.treasure.view.home.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.utils.VPager
import com.afdhal_fa.treasure.core.utils.VPagerAdapater
import com.afdhal_fa.treasure.view.home.notification.more.MoreNotificationFragment
import com.afdhal_fa.treasure.view.home.notification.transaktion.TransactionFragment
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        val pages = ArrayList<VPager>()
        pages.add(VPager("Transaksi", TransactionFragment()))
        pages.add(VPager("Lainnya", MoreNotificationFragment()))

        val vPagerAdapater = VPagerAdapater(pages, supportFragmentManager)
        vPagerAdapater.setData("transaction", "more")
        pager.adapter = vPagerAdapater

    }
}