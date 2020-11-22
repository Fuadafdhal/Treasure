package com.afdhal_fa.treasure.view.exchange

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Nominal
import com.afdhal_fa.treasure.core.utils.BaseToolbarFragment
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.core.vo.Resource
import com.afdhal_fa.treasure.databinding.FragmentExchangeBinding
import com.afdhal_fa.treasure.view.exchange.choose_nominal.ChoseeNominalActivity
import com.afdhal_fa.treasure.view.exchange.exchange_confirmation.ExchangeConfirmationActivity
import com.afdhal_fa.treasure.view.login.LoginActivity

class ExchangeFragment : BaseToolbarFragment<ExchangeViewModel>() {
    private var _binding: FragmentExchangeBinding? = null
    private val binding get() = _binding!!
    private lateinit var exchangeAdapter: ExchangeAdapter

    companion object {
        const val INTENT_REQUEST_CODE_NOMINAL = 100
        const val INTENT_RESUTL_EXTRA_NOMINAL = "extra_result_nominal"
        const val INTENT_RESUTL_EXTRA_POSITION = "extra_result_position"
        const val INTENT_EXTRA_EXCHANGE = "extra_extra_exchange"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExchangeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            exchangeAdapter = ExchangeAdapter()
            checkIfUserIsAuthenticated()

            exchangeAdapter.onItemClick = { selectedData ->
                startActivityForResult(
                    Intent(activity, ChoseeNominalActivity::class.java)
                        .putExtra(INTENT_RESUTL_EXTRA_POSITION, selectedData.position),
                    INTENT_REQUEST_CODE_NOMINAL
                )
            }
            exchangeAdapter.onExchangeClick = { resultData ->
                startActivity(
                    Intent(activity, ExchangeConfirmationActivity::class.java)
                        .putExtra(INTENT_EXTRA_EXCHANGE, resultData)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }

            viewmodel.exchangeMetode().observe(viewLifecycleOwner, {
                exchangeAdapter.setItem(it)
            })

            with(binding.rvExchange) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = exchangeAdapter
            }
        }

    }

    private fun checkIfUserIsAuthenticated() {
        viewmodel.checkIfUserIsAuthenticated().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.isAuthenticated!!) {
                        exchangeAdapter.setUser(it.data)
                    }
                }
                is Resource.Error -> {
                    context?.makeToast("Error")
                    if (!it.data?.isAuthenticated!!) {
                        updateUI(it.data.isAuthenticated)
                    }
                }
            }
        })
    }

    private fun updateUI(boolean: Boolean) {
        if (boolean) {
            startActivity(Intent(this.context, LoginActivity::class.java))
        }
    }

    override fun initViewModel(): ExchangeViewModel {
        return ViewModelProvider(this)[ExchangeViewModel::class.java]
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == INTENT_REQUEST_CODE_NOMINAL && resultCode == RESULT_OK && data != null) {
            val resultData = data.getParcelableExtra<Nominal>(INTENT_RESUTL_EXTRA_NOMINAL)
            val resultPosition = data.getIntExtra(INTENT_RESUTL_EXTRA_POSITION, 0)
            resultData?.let {
                exchangeAdapter.setNominal(it, resultPosition)
            }
        }
    }

    override fun setToolbar(): Toolbar {
        binding.includeAppbar.textTitleToolbar.text = getString(R.string.title_exchange)
        return binding.includeAppbar.toolbar
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}