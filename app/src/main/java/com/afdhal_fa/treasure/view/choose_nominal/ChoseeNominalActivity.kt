package com.afdhal_fa.treasure.view.choose_nominal

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.utils.BaseToolbarActivity
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.databinding.ActivityChoseeNominalBinding
import com.afdhal_fa.treasure.view.exchange.ExchangeFragment

class ChoseeNominalActivity : BaseToolbarActivity<NominalViewModel>() {

    private lateinit var binding: ActivityChoseeNominalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chosee_nominal)

        val nominalAdapter = NominalAdapter()

        viewmodel.getNominal().observe(this, {
            nominalAdapter.setItem(it)
        })

        nominalAdapter.onItemClick = { select ->
            makeToast(select.nominal.toString())
            val mIntent = Intent().apply {
                putExtra(ExchangeFragment.INTENT_RESUTL_EXTRA_NOMINAL, select)
            }
            setResult(RESULT_OK, mIntent)
            finish()
        }

        with(binding.rvNominal) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    override fun setToolbar(): Toolbar {
        binding.textTitleToolbar.text = getString(R.string.title_nominal)
        return binding.toolbar
    }

    override fun initViewModel(): NominalViewModel {
        return ViewModelProvider(this)[NominalViewModel::class.java]
    }
}