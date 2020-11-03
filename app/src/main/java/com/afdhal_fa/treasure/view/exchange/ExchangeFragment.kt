package com.afdhal_fa.treasure.view.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.afdhal_fa.treasure.R
import kotlinx.android.synthetic.main.fragment_exchange.*
import java.util.*

class ExchangeFragment : Fragment() {

    private lateinit var viewModel: ExchangeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ExchangeViewModel::class.java)
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

            with(rv_exchange) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = exchangeAdapter
            }
        }
    }
}