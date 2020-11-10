package com.afdhal_fa.treasure.view.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.utils.BaseFragment
import kotlinx.android.synthetic.main.fragment_exchange.*
import java.util.*

class ExchangeFragment : BaseFragment<ExchangeViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_exchange, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val exchangeAdapter = ExchangeAdapter()

            val listData = ArrayList<String>()
            listData.add("Pulsa")
            listData.add("Link Aja")
            listData.add("Dana")

            exchangeAdapter.setData(listData)

            with(rvExchange) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = exchangeAdapter
            }
        }
    }

    override fun initViewModel(): ExchangeViewModel {
        return ViewModelProvider(this)[ExchangeViewModel::class.java]
    }
}