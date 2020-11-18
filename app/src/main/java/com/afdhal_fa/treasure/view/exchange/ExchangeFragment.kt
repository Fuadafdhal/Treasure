package com.afdhal_fa.treasure.view.exchange

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.ExchangeMetode
import com.afdhal_fa.treasure.core.utils.BaseFragment
import com.afdhal_fa.treasure.view.choose_nominal.ChoseeNominalActivity
import kotlinx.android.synthetic.main.fragment_exchange.*
import java.util.*

class ExchangeFragment : BaseFragment<ExchangeViewModel>() {
    companion object {
        const val INTENT_REQUEST_CODE_NOMINAL = 100
        const val INTENT_RESUTL_EXTRA_NOMINAL = "extra_result_nominal"
    }

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

            exchangeAdapter.onItemClick = { selectedData ->
                startActivityForResult(
                    Intent(activity, ChoseeNominalActivity::class.java), INTENT_REQUEST_CODE_NOMINAL
                )
            }

            val listData = ArrayList<ExchangeMetode>()
            listData.add(ExchangeMetode(R.drawable.ic_ponsel, "Pulsa"))
            listData.add(ExchangeMetode(R.drawable.logo_linkaja, "Link Aja"))
            listData.add(ExchangeMetode(R.drawable.logo_dana, "Dana"))

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == INTENT_REQUEST_CODE_NOMINAL && resultCode == RESULT_OK && data != null) {
            TODO()
        }
    }
}